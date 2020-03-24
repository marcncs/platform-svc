package com.winsafe.drp.action.aftersale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppPurchaseWithdraw;
import com.winsafe.drp.dao.AppPurchaseWithdrawDetail;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Provider;
import com.winsafe.drp.dao.PurchaseWithdraw;
import com.winsafe.drp.dao.PurchaseWithdrawDetail;
import com.winsafe.drp.dao.PurchaseWithdrawDetailForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.NumToChRMB;

public class PurchaseWithdrawAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		try {
			ServletContext context = request.getSession().getServletContext();
			String reportSource = context
					.getRealPath("/WEB-INF/reports/PurchaseWithdraw.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppPurchaseWithdraw app = new AppPurchaseWithdraw();
			PurchaseWithdraw pp = app.getPurchaseWithdrawByID(id);
			Map<String, Object> parameters = new HashMap<String, Object>();

			OrganService os = new OrganService();
			Organ organ = os.getOrganByID(pp.getMakeorganid());
			parameters.put("organtitle", organ.getOrganname());
			parameters.put("title", "");

			String path = "";
			if (organ.getLogo().equals("")) {
				path = context.getRealPath("images\\logo.jpg");
			} else {
				path = context.getRealPath(organ.getLogo());
			}
			parameters.put("img", path);

			AppProvider appP = new AppProvider();
			Provider p = appP.getProviderByID(pp.getPid());
			parameters.put("billtype", "Return from Customers 采购退货单");
			parameters.put("billno", pp.getId());
			parameters.put("makedate", DateUtil.formatDate(pp.getMakedate()));

			UsersService us = new UsersService();
			Users u = us.getUsersByid(pp.getMakeid());
			parameters.put("linkman", u.getRealname());
			parameters.put("tel", u.getOfficetel() + "/" + u.getMobile());
			parameters.put("fax", organ.getOfax());
			parameters.put("email", u.getEmail());
			WarehouseService appw = new WarehouseService();
			Warehouse wh = appw.getWarehouseByID(pp.getWarehouseid());
			parameters.put("warehouse", wh.getWarehousename());

			parameters.put("pid", pp.getPid());
			parameters.put("pname", pp.getPname());
			parameters.put("paddr", p.getAddr() == null ? "" : p.getAddr());
			parameters.put("plinkman", pp.getPlinkman());
			parameters.put("ptel", pp.getTel());
			parameters.put("pmobile", p.getMobile());

			parameters.put("wname", wh.getWarehousename());
			parameters.put("waddr", wh.getWarehouseaddr());
			parameters.put("wlinkman", wh.getUsername());
			parameters.put("wtel", wh.getWarehousetel());

			AppOlinkMan appO = new AppOlinkMan();
			List<Olinkman> list = appO.getOlinkmanByCid(organ.getId());
			Olinkman olinkman = list.get(0);
			parameters.put("oname", organ.getOrganname());
			parameters.put("oaddr", organ.getOaddr());
			parameters.put("olinkman", olinkman.getName());
			parameters.put("otel", olinkman.getOfficetel());
			parameters.put("omobile", olinkman.getMobile());

			parameters.put("printb", "");
			parameters.put("printc", "");

			AppPurchaseWithdrawDetail appd = new AppPurchaseWithdrawDetail();
			List<PurchaseWithdrawDetail> sals = appd
					.getPurchaseWithdrawDetailByPWID(id);
			List<PurchaseWithdrawDetailForm> als = new ArrayList<PurchaseWithdrawDetailForm>();
			double totalsum = 0.00;
			for (int i = 0; i < sals.size(); i++) {
				PurchaseWithdrawDetailForm ppdf = new PurchaseWithdrawDetailForm();
				PurchaseWithdrawDetail o = (PurchaseWithdrawDetail) sals.get(i);
				ppdf.setProductid(o.getProductid());
				ppdf.setProductname(o.getProductname() + " | "
						+ o.getSpecmode());
				String unitname = HtmlSelect.getResourceName(request,
						"CountUnit", o.getUnitid());
				ppdf.setBatch(o.getBatch());
				ppdf.setUnitidname(unitname);
				ppdf.setUnitprice(o.getUnitprice());
				ppdf.setQuantity(o.getQuantity());
				ppdf.setSubsum(o.getSubsum());
				totalsum += ppdf.getSubsum();
				als.add(ppdf);
			}

			parameters.put("totalsum", totalsum);
			String numRMB = NumToChRMB.numToRMB(String.valueOf(totalsum));
			parameters.put("totalsumcapital", numRMB);

			JasperPrint print = JasperFillManager.fillReport(report,
					parameters, new JRBeanCollectionDataSource(als));

			{
				byte[] bytes = JasperExportManager.exportReportToPdf(print);
				if (bytes != null && bytes.length > 0) {
					response.setContentType("application/pdf");
					response.setContentLength(bytes.length);
					ServletOutputStream ouputStream = response
							.getOutputStream();
					ouputStream.write(bytes, 0, bytes.length);
					ouputStream.flush();
					ouputStream.close();
				}
			}
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 2, "打印采购退货!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
