package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppVocationOrder;
import com.winsafe.drp.dao.AppVocationOrderDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.VocationOrder;
import com.winsafe.drp.dao.VocationOrderDetail;
import com.winsafe.drp.dao.VocationOrderDetailForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.NumToChRMB;

public class PrintVocationOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		
		try {
			String reportSource = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/reports/VocationOrder.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppVocationOrder aps = new AppVocationOrder();
			AppUsers au = new AppUsers();
			AppCustomer ac = new AppCustomer();
			VocationOrder sb = aps.getVocationOrderByID(id);
			AppWarehouse aw = new AppWarehouse();
			// AppSaleOrder apso = new AppSaleOrder();
			// SaleOrder saleorder =apso.getSaleOrderByID(sb.getId());

			AppInvoiceConf aic = new AppInvoiceConf();

			AppOrgan ao = new AppOrgan();
			Organ o = ao.getOrganByID(sb.getMakeorganid());

			String billtype = "行业销售单";

			Map parameters = new HashMap();
			parameters.put("title", "");
			parameters.put("billtype", billtype);
			parameters.put("id", sb.getId());
			parameters.put("cid",sb.getCid());
			parameters.put("cname",sb.getCname());
			parameters.put("cmobile",sb.getCmobile());
			parameters.put("receiveman",sb.getReceiveman());
			parameters.put("receivemobile",sb.getReceivemobile());
			parameters.put("receivetel",sb.getReceivetel());
			parameters.put("paymentmodename", Internation
					.getStringByPayPositionDB(sb.getPaymentmode()));
			parameters.put("invmsg", aic.getInvoiceConfById(sb.getInvmsg())
					.getIvname());
			if (sb.getInvmsg() > 0) {
				parameters.put("tickettitle", sb.getTickettitle());
			} else {
				parameters.put("tickettitle", "");
			}
			parameters.put("transportmode", Internation
					.getStringByKeyPositionDB("TransportMode", sb
							.getTransportmode()));
			parameters.put("transportaddr", sb.getTransportaddr());
			parameters.put("remark", sb.getRemark());
			parameters.put("totalsum", sb.getTotalsum());
			parameters.put("totalsumcapital", NumToChRMB.numToRMB(sb
					.getTotalsum().toString()));
//			parameters.put("makeidname", au.getUsersByID(sb.getMakeid())
//					.getRealname());
			parameters.put("makedate", DateUtil
					.formatDateTime(sb.getMakedate()).substring(0, 10));
			parameters.put("makeorganidname", o.getOrganname());
			parameters.put("otel", o.getOtel());

			if (sb.getPrinttimes() == null || sb.getPrinttimes() == 0) {
				sb.setPrinttimes(1);
			}
			parameters.put("printtimes", sb.getPrinttimes());
			parameters.put("item1", "");
			parameters.put("item2", "");
			// parameters.put("printman", users.getRealname());

			sb.setPrinttimes(sb.getPrinttimes() + 1);
			// aps.updShipmentBill(sb);

			AppVocationOrderDetail apbd = new AppVocationOrderDetail();
			List<VocationOrderDetail> pbls = apbd
					.getVocationOrderDetailObjectBySOID(id);
			ArrayList als = new ArrayList();
			int rows = 1;
			for (VocationOrderDetail ttd : pbls) {
				VocationOrderDetailForm sldf = new VocationOrderDetailForm();
				sldf.setProductname(ttd.getProductid() + "|"
						+ ttd.getProductname());
				sldf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", Integer.parseInt(ttd.getUnitid()
								.toString())));
				sldf.setUnitprice(ttd.getUnitprice());
				sldf.setQuantity(ttd.getQuantity());
				sldf.setSubsum(ttd.getSubsum());
				sldf.setRows(rows);
				als.add(sldf);
				rows++;
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

			
			// JasperPrintManager.printReport(print, true);
			// return mapping.findForward("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
