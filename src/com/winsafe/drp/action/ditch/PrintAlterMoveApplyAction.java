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
import com.winsafe.drp.dao.AlterMoveApply;
import com.winsafe.drp.dao.AlterMoveApplyDetail;
import com.winsafe.drp.dao.AlterMoveApplyDetailForm;
import com.winsafe.drp.dao.AppAlterMoveApply;
import com.winsafe.drp.dao.AppAlterMoveApplyDetail;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
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

public class PrintAlterMoveApplyAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		super.initdata(request);
		try {
			ServletContext context = request.getSession().getServletContext();
			String reportSource = context
					.getRealPath("/WEB-INF/reports/AlterMoveApply.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppAlterMoveApply asb = new AppAlterMoveApply();
			AlterMoveApply ar = asb.getAlterMoveApplyByID(id);
			OrganService os = new OrganService();
			AppOlinkMan appO = new AppOlinkMan();

			Map<String, Object> parameters = new HashMap<String, Object>();

			Organ o = os.getOrganByID(users.getMakeorganid());
			parameters.put("organtitle", o.getOrganname());
			parameters.put("title", "");

			String path = "";
			if (o.getLogo().equals("")) {
				path = context.getRealPath("images\\logo.jpg");
			} else {
				path = context.getRealPath(o.getLogo());
			}
			parameters.put("img", path);

			Organ outo = os.getOrganByID(ar.getOutorganid());
			List<Olinkman> list = appO.getOlinkmanByCid(ar.getOutorganid());
			Olinkman outlinkman = list.get(0);
			parameters.put("billtype", "Purchase Requisition 订购申请单");
			parameters.put("billno", id);
			parameters.put("movedate", DateUtil.formatDate(ar.getMovedate()));
			WarehouseService ws = new WarehouseService();
			Warehouse wh = ws.getWarehouseByID(ar.getInwarehouseid());
			parameters.put("inwarehouseidname", wh.getWarehousename());
			UsersService us = new UsersService();
			Users u = us.getUsersByid(ar.getMakeid());

			parameters.put("linkman", u.getRealname());
			parameters.put("tel", u.getOfficetel() + "/" + u.getMobile());
			parameters.put("fax", outo.getOfax());
			parameters.put("email", u.getEmail());

			String paymode = HtmlSelect.getNameByOrder(request, "PayMode", ar
					.getPaymentmode());
			parameters.put("paymentmodename", paymode);
			String invmsg = HtmlSelect.getNameByOrder(request, "InvoiceType",
					ar.getInvmsg());
			parameters.put("invmsg", invmsg);
			String transportmode = HtmlSelect.getNameByOrder(request,
					"TransportMode", ar.getTransportmode());
			parameters.put("transportmode", transportmode);

			parameters.put("pid", ar.getOutorganid());
			parameters.put("pname", outo.getOrganname());
			parameters.put("paddr", outo.getOaddr());
			parameters.put("plinkman", outlinkman.getName());
			parameters.put("ptel", outlinkman.getOfficetel());
			parameters.put("pmobile", outlinkman.getMobile());

			parameters.put("wname", wh.getWarehousename());
			parameters.put("waddr", wh.getWarehouseaddr());
			parameters.put("wlinkman", wh.getUsername());
			parameters.put("wtel", wh.getWarehousetel());

			Organ makeo = os.getOrganByID(ar.getMakeorganid());
			list = appO.getOlinkmanByCid(makeo.getId());
			Olinkman olinkman = list.get(0);
			parameters.put("oname", makeo.getOrganname());
			parameters.put("oaddr", ar.getTransportaddr());
			parameters.put("olinkman", olinkman.getName());
			parameters.put("otel", olinkman.getOfficetel());
			parameters.put("omobile", olinkman.getMobile());

			parameters.put("printb", "");
			parameters.put("printc", "");
			parameters.put("movecause", ar.getMovecause());
			parameters.put("remark", ar.getRemark());

			AppAlterMoveApplyDetail asbd = new AppAlterMoveApplyDetail();
			List<AlterMoveApplyDetail> sbls = asbd
					.getAlterMoveApplyDetailByAmID(id);
			List<AlterMoveApplyDetailForm> als = new ArrayList<AlterMoveApplyDetailForm>();
			double totalsum = 0.00;
			for (AlterMoveApplyDetail ard : sbls) {
				AlterMoveApplyDetailForm sldf = new AlterMoveApplyDetailForm();
				sldf.setProductid(ard.getProductid());
				sldf.setProductname(ard.getProductname() + " | "
						+ ard.getSpecmode());
				sldf.setUnitidname(HtmlSelect.getResourceName(request,
						"CountUnit", ard.getUnitid()));
				sldf.setUnitprice(ard.getUnitprice());
				sldf.setQuantity(ard.getQuantity());
				sldf.setAlreadyquantity(ard.getAlreadyquantity());
				sldf.setCanquantity(ard.getCanquantity());
				sldf.setSubsum(ard.getSubsum());
				totalsum += sldf.getSubsum();
				als.add(sldf);
			}
			String numRMB = NumToChRMB.numToRMB(String.valueOf(totalsum));
			parameters.put("totalsumcapital", numRMB);
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
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 4, "打印订购申请");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
