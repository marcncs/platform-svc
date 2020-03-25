package com.winsafe.drp.action.purchase;

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
import com.winsafe.drp.dao.AppPurchaseBill;
import com.winsafe.drp.dao.AppPurchaseBillDetail;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Provider;
import com.winsafe.drp.dao.PurchaseBill;
import com.winsafe.drp.dao.PurchaseBillDetail;
import com.winsafe.drp.dao.PurchaseBillDetailForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class PurchaseBillAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		try {
			ServletContext context = request.getSession().getServletContext();
			String reportSource = context
					.getRealPath("/WEB-INF/reports/PurchaseBill.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppPurchaseBill aps = new AppPurchaseBill();
			OrganService os = new OrganService();
			PurchaseBill pb = aps.getPurchaseBillByID(id);

			Organ organ = os.getOrganByID(pb.getMakeorganid());
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("organtitle", organ.getOrganname());

			String path = "";
			if (organ.getLogo().equals("")) {
				path = context.getRealPath("images\\logo.jpg");
			} else {
				path = context.getRealPath(organ.getLogo());
			}
			parameters.put("img", path);
			parameters.put("showimg", true);

			AppProvider appP = new AppProvider();
			Provider p = appP.getProviderByID(pb.getPid());
			parameters.put("pid", pb.getPid());
			parameters.put("pname", pb.getPname());
			parameters.put("paddr", p.getAddr() == null ? "" : p.getAddr());
			parameters.put("plinkman", pb.getPlinkman());
			parameters.put("ptel", pb.getTel());
			parameters.put("ptel1", p.getMobile());

			AppOlinkMan appO = new AppOlinkMan();
			List<Olinkman> list = appO.getOlinkmanByCid(organ.getId());
			Olinkman olinkman = list.get(0);

			parameters.put("wname", organ.getOrganname());
			parameters.put("waddr", organ.getOaddr());
			parameters.put("wlinkman", olinkman.getName());
			parameters.put("wtel", olinkman.getOfficetel());

			parameters.put("oname", organ.getOrganname());
			parameters.put("oaddr", organ.getOaddr());
			parameters.put("olinkman", olinkman.getName());
			parameters.put("otel", olinkman.getOfficetel());
			parameters.put("otel1", olinkman.getMobile());

			parameters.put("billtype", "Purchase Order 采购订单");
			parameters.put("billno", pb.getId());
			parameters.put("makedate", DateUtil.formatDate(pb.getMakedate()));

			UsersService us = new UsersService();
			Users user = us.getUsersByid(pb.getMakeid());

			parameters.put("linkman", user.getRealname());
			parameters.put("tel", user.getOfficetel() + "/" + user.getMobile());
			parameters.put("fax", organ.getOfax());
			parameters.put("email", user.getEmail());

			AppPurchaseBillDetail apbd = new AppPurchaseBillDetail();
			List<PurchaseBillDetail> pbls = apbd
					.getPurchaseBillDetailByPbID(id);
			List<PurchaseBillDetailForm> als = new ArrayList<PurchaseBillDetailForm>();
			double totalsum = 0.00;
			for (int i = 0; i < pbls.size(); i++) {
				PurchaseBillDetailForm sldf = new PurchaseBillDetailForm();
				PurchaseBillDetail o = (PurchaseBillDetail) pbls.get(i);
				sldf.setProductid(o.getProductid());
				sldf.setProductname(o.getProductname() + "|" + o.getSpecmode());
				sldf.setUnitname(HtmlSelect.getResourceName(request,
						"CountUnit", o.getUnitid()));
				sldf.setUnitprice(o.getUnitprice());
				sldf.setQuantity(o.getQuantity());
				sldf.setSubsum(o.getSubsum());
				totalsum += sldf.getSubsum();
				als.add(sldf);
			}
			parameters.put("totalsum", totalsum);
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
			DBUserLog.addUserLog(userid, 2, "打印采购订单!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
