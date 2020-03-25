package com.winsafe.drp.action.ditch;

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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppOrganTrades;
import com.winsafe.drp.dao.AppOrganTradesDetail;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganTrades;
import com.winsafe.drp.dao.OrganTradesDetail;
import com.winsafe.drp.dao.OrganTradesDetailForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.NumToChRMB;

public class PrintOrganTradesReceiveAction extends BaseAction {

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		super.initdata(request);
		try {
			ServletContext context = request.getSession().getServletContext();
			String reportSource = context
					.getRealPath("/WEB-INF/reports/OrganTrades.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppOrganTrades asb = new AppOrganTrades();
			OrganTrades ar = asb.getOrganTradesByID(id);
			OrganService os = new OrganService();
			WarehouseService aw = new WarehouseService();

			Map<String, Object> parameters = new HashMap<String, Object>();
			Organ o = os.getOrganByID(ar.getMakeorganid());
			Organ porgan = os.getOrganByID(ar.getPorganid());
			Organ makeo = os.getOrganByID(users.getMakeorganid());
			parameters.put("organtitle", makeo.getOrganname());
			parameters.put("title", "");
			String path = "";
			if (makeo.getLogo().equals("")) {
				path = context.getRealPath("images\\logo.jpg");
			} else {
				path = context.getRealPath(makeo.getLogo());
			}
			parameters.put("img", path);
			parameters.put("isshow", false);

			parameters.put("billtype", "Channel/Distributors’ SWAP 渠道换货签收");
			parameters.put("billno", id);
			parameters.put("movedate", DateUtil.formatDate(ar.getMakedate()));
			UsersService us = new UsersService();
			Users u = us.getUsersByid(ar.getMakeid());
			parameters.put("linkman", u.getRealname());
			parameters.put("tel", u.getOfficetel() + "/" + u.getMobile());
			parameters.put("fax", porgan.getOfax());
			parameters.put("email", u.getEmail());

			AppOlinkMan appO = new AppOlinkMan();
			List<Olinkman> list = appO.getOlinkmanByCid(porgan.getId());
			Olinkman outlinkman = list.get(0);
			parameters.put("pid", ar.getPorganid());
			parameters.put("pname", ar.getPorganname());
			parameters.put("paddr", ar.getTransportaddr());
			parameters.put("plinkman", outlinkman.getName());
			parameters.put("ptel", outlinkman.getOfficetel());
			parameters.put("pmobile", outlinkman.getMobile());
			String warehouse = aw.getWarehouseName(ar.getPoutwarehouseid());
			parameters.put("outwarehouseid", "供方出货仓库   " + warehouse);
			String inwarehouse = aw.getWarehouseName(ar.getInwarehouseid());
			parameters.put("inwarehouseid", "供方换入仓库    " + inwarehouse);

			list = appO.getOlinkmanByCid(o.getId());
			Olinkman olinkman = list.get(0);
			parameters.put("oname", o.getOrganname());
			parameters.put("oaddr", ar.getRtransportaddr());
			parameters.put("olinkman", ar.getRlinkman());
			parameters.put("otel", ar.getRtel());
			parameters.put("omobile", olinkman.getMobile());

			String routwarehouse = aw.getWarehouseName(ar.getOutwarehouseid());
			parameters.put("routwarehouseid", "收方出货仓库" + routwarehouse);
			parameters.put("rinwarehouseid", "收方换入仓库" + routwarehouse);

			parameters.put("printb", "");
			parameters.put("printc", "");
			parameters.put("movecause", ar.getWithdrawcause());
			String numRMB = "";
			if (ar.getTotalsum() != null) {
				numRMB = NumToChRMB.numToRMB(ar.getTotalsum().toString());
			} else {
				numRMB = NumToChRMB.numToRMB("0");
			}
			parameters.put("totalsumcapital", numRMB);
			parameters.put("totalsum", ar.getTotalsum());

			parameters.put("remark", "");
			UsersBean users = UserManager.getUser(request);
			if (ar.getPrinttimes() == null || ar.getPrinttimes() == 0) {
				ar.setPrinttimes(1);
			}

			ar.setPrinttimes(ar.getPrinttimes() + 1);
			asb.update(ar);

			AppOrganTradesDetail asbd = new AppOrganTradesDetail();
			List sbls = asbd.getOrganTradesDetailByotid(id);
			ArrayList als = new ArrayList();
			OrganTradesDetail ard = null;

			for (int i = 0; i < sbls.size(); i++) {
				OrganTradesDetailForm sldf = new OrganTradesDetailForm();
				ard = (OrganTradesDetail) sbls.get(i);
				sldf.setProductid(ard.getProductid());
				sldf.setProductname(ard.getProductname() + " | "
						+ ard.getSpecmode());
				sldf.setUnitidname(HtmlSelect.getResourceName(request,
						"CountUnit", ard.getUnitid()));
				sldf.setBatch(ard.getBatch());
				sldf.setUnitprice(ard.getUnitprice());
				sldf.setQuantity(ard.getQuantity());
				sldf.setCanquantity(ard.getCanquantity());
				sldf.setTakequantity(ard.getTakequantity());
				sldf.setPtakequantity(ard.getPtakequantity());
				sldf.setSubsum(ard.getSubsum());
				als.add(sldf);
			}

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
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 4, "打印渠道换货");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
