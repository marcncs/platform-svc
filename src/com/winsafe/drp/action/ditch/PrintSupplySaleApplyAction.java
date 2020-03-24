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
import com.winsafe.drp.dao.AppSupplySaleApply;
import com.winsafe.drp.dao.AppSupplySaleApplyDetail;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.SupplySaleApply;
import com.winsafe.drp.dao.SupplySaleApplyDetail;
import com.winsafe.drp.dao.SupplySaleApplyDetailForm;
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

public class PrintSupplySaleApplyAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		UsersBean users = UserManager.getUser(request);
		super.initdata(request);try{
			ServletContext context = request.getSession().getServletContext();
			String reportSource = context.getRealPath("/WEB-INF/reports/SupplySaleApply.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppSupplySaleApply asb = new AppSupplySaleApply();
			SupplySaleApply ar = asb.getSupplySaleApplyByID(id);
			OrganService os = new OrganService();
			WarehouseService aw = new WarehouseService();
			Map<String, Object> parameters = new HashMap<String, Object>();

			Organ o = os.getOrganByID(ar.getMakeorganid());
			Organ outo = os.getOrganByID(ar.getOutorganid());
			Organ ino = os.getOrganByID(ar.getInorganid());
			Organ makeo = os.getOrganByID(users.getMakeorganid());
			parameters.put("organtitle",makeo.getOrganname());
			parameters.put("title", "");
			
			String path = "";
			if (makeo.getLogo().equals("")) {
				path = context.getRealPath("images\\logo.jpg");
			} else {
				path = context.getRealPath(makeo.getLogo());
			}
			parameters.put("img", path);

			parameters.put("billtype", "Consignment Request 代销申请单");
			parameters.put("billno", id);
			parameters.put("movedate", DateUtil.formatDate(ar.getMovedate()));
			Warehouse wh = aw.getWarehouseByID(ar.getInwarehouseid());
			parameters.put("inwarehouseidname", wh.getWarehousename());
			UsersService us = new UsersService();
			Users  u = us.getUsersByid(ar.getMakeid());
			parameters.put("linkman",u.getRealname());
			parameters.put("tel", u.getOfficetel()+"/"+u.getMobile());
			parameters.put("fax", ino.getOfax());
			parameters.put("email", u.getEmail());
			String paymode = HtmlSelect.getNameByOrder(request, "PayMode", ar
					.getPaymentmode());
			parameters.put("paymentmodename", paymode);
			String invmsg = HtmlSelect.getNameByOrder(request, "InvoiceType",
					ar.getInvmsg());
			parameters.put("invmsg", invmsg);
			String transportmode = HtmlSelect.getResourceName(request,
					"TransportMode", ar.getTransportmode());
			parameters.put("transportmode", transportmode);

			AppOlinkMan appO = new AppOlinkMan();
			List<Olinkman> list = appO.getOlinkmanByCid(outo.getId());
			Olinkman olinkman = list.get(0);
			parameters.put("pid", outo.getId());
			parameters.put("pname", outo.getOrganname());
			parameters.put("paddr", outo.getOaddr());
			parameters.put("plinkman", olinkman.getName());
			parameters.put("ptel", olinkman.getOfficetel());
			parameters.put("pmobile", olinkman.getMobile());
			
			
			parameters.put("wname", wh.getWarehousename());
			parameters.put("waddr", wh.getWarehouseaddr());
			parameters.put("wlinkman", wh.getUsername());
			parameters.put("wtel", wh.getWarehousetel());

//			list = appO.getOlinkmanByCid(ino.getId());
//			olinkman = list.get(0);
//			parameters.put("oname", ino.getOrganname());
//			parameters.put("oaddr", ar.getTransportaddr());
//			parameters.put("olinkman", olinkman.getName());
//			parameters.put("otel", olinkman.getOfficetel());
//			parameters.put("omobile", olinkman.getMobile());

			list = appO.getOlinkmanByCid(o.getId());
			olinkman = list.get(0);
			parameters.put("sname", o.getOrganname());
			parameters.put("saddr", o.getOaddr());
			parameters.put("slinkman", olinkman.getName());
			parameters.put("stel", olinkman.getOfficetel());
			parameters.put("smobile", olinkman.getMobile());

			parameters.put("printb", "");
			parameters.put("printc","");
			parameters.put("movecause", ar.getMovecause());
			parameters.put("remark", ar.getRemark());
			String numRMB = "";
			if (ar.getTotalsum() != null) {
				numRMB = NumToChRMB.numToRMB(ar.getTotalsum().toString());
			} else {
				numRMB = NumToChRMB.numToRMB("0");
			}
			parameters.put("totalsumcapital", numRMB);
			parameters.put("ptotalsum", ar.getTotalsum());

			if (ar.getStotalsum() != null) {
				numRMB = NumToChRMB.numToRMB(ar.getStotalsum().toString());
			} else {
				numRMB = NumToChRMB.numToRMB("0");
			}
			parameters.put("stotalsumcapital", numRMB);
			parameters.put("stotalsum", ar.getStotalsum());

			parameters.put("remark", ar.getRemark());
			if (ar.getPrinttimes() == null || ar.getPrinttimes() == 0) {
				ar.setPrinttimes(1);
			}
			ar.setPrinttimes(ar.getPrinttimes() + 1);
			asb.update(ar);

			AppSupplySaleApplyDetail asbd = new AppSupplySaleApplyDetail();
			List<SupplySaleApplyDetail> sbls = asbd.getSupplySaleAplyBySSID(id);
			List<SupplySaleApplyDetailForm> als = new ArrayList<SupplySaleApplyDetailForm>();

			for (SupplySaleApplyDetail ard : sbls) {
				SupplySaleApplyDetailForm sldf = new SupplySaleApplyDetailForm();
				sldf.setProductid(ard.getProductid());
				sldf.setProductname(ard.getProductname() + " | "
						+ ard.getSpecmode());
				sldf.setUnitidname(HtmlSelect.getResourceName(request,
						"CountUnit", ard.getUnitid()));
				sldf.setSunitprice(ard.getSunitprice());
				sldf.setPunitprice(ard.getPunitprice());
				sldf.setQuantity(ard.getQuantity());
				sldf.setAlreadyquantity(ard.getAlreadyquantity());
				sldf.setSsubsum(ard.getSsubsum());
				sldf.setPsubsum(ard.getPsubsum());
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
			DBUserLog.addUserLog(userid, 4, "打印代销申请!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
