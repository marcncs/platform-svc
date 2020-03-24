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
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppPeddleOrder;
import com.winsafe.drp.dao.AppPeddleOrderDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.PeddleOrder;
import com.winsafe.drp.dao.PeddleOrderDetail;
import com.winsafe.drp.dao.PeddleOrderDetailForm;
import com.winsafe.drp.dao.PeddleOrderForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.NumToChRMB;

public class PrintPeddleOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		
		try {
			String reportSource = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/reports/PeddleOrder.jasper");
			JasperReport report = (JasperReport) JRLoader.loadObject(reportSource);

			AppPeddleOrder asl = new AppPeddleOrder();			
			AppUsers au = new AppUsers();
			AppDept ad = new AppDept();
			AppCustomer ac = new AppCustomer();
			PeddleOrder so = asl.getPeddleOrderByID(id);
			PeddleOrderForm sof = new PeddleOrderForm();

			sof.setId(id);
			sof.setCid(so.getCid());
			sof.setCname(ac.getCustomer(so.getCid()).getCname());
			sof.setCmobile(so.getCmobile());
			sof.setReceiveman(so.getReceiveman());
			//sof.setReceivetel(so.getReceivetel());
//			sof.setPaymentmodename(Internation.getStringByKeyPosition(
//					"PaymentMode", request, so.getPaymentmode(),
//			"global.sys.SystemResource"));
//			sof.setSaletypename(Internation.getStringByKeyPositionDB("SaleType",so.getSaletype()));
			//sof.setSaledeptname(ad.getDeptByID(so.getSaledept()).getDeptname());
			//sof.setTransportmodename(Internation.getStringByKeyPositionDB("TransportMode",so.getTransportmode()));
//			sof.setTransitname(Internation.getStringByKeyPositionDB("Transit",
//					so.getTransit()));
			//sof.setConsignmentdate(so.getConsignmentdate().toString());
			//sof.setMakedatename(so.getMakedate().toString().substring(0,10));
			//sof.setTransportaddr(so.getTransportaddr());
//			sof.setMakeidname(au.getUsersByid(so.getMakeid()).getRealname());
			sof.setRemark(so.getRemark());
			sof.setTotalsum(so.getTotalsum());
			AppInvoiceConf aic = new AppInvoiceConf();
			AppOrgan ao = new AppOrgan();
			Organ o = ao.getOrganByID(users.getMakeorganid());

			Map parameters = new HashMap();	
			parameters.put("title", "");
			parameters.put("id", sof.getId());
			parameters.put("cname", sof.getCname());
			parameters.put("cmobile",sof.getCmobile());
			parameters.put("linkman", sof.getReceiveman());
			//parameters.put("receivetel", sb.getTel());
			//parameters.put("requiredate", String.valueOf(sb.getRequiredate()).substring(0,10));
			//parameters.put("receiveaddr", sb.getReceiveaddr());			
			parameters.put("remark", sof.getRemark());
			parameters.put("totalsum", sof.getTotalsum());
			parameters.put("totalsumcapital", NumToChRMB.numToRMB(sof
					.getTotalsum().toString()));
//			parameters.put("makeidname", au.getUsersByID(so.getMakeid()).getRealname());
			parameters.put("makedate", DateUtil.formatDateTime(so.getMakedate()).substring(0,10));
			parameters.put("paymentmodename", Internation.getStringByPayPositionDB(so.getPaymentmode()));
			parameters.put("invmsg", aic.getInvoiceConfById(so.getInvmsg()).getIvname());
			if(so.getInvmsg()>0){
				parameters.put("tickettitle", "");
			}else{
			parameters.put("tickettitle","" );
			}
			parameters.put("makeorganidname", ao.getOrganByID(so.getMakeorganid()).getOrganname());
			
			if ( so.getPrinttimes() == null || so.getPrinttimes() == 0 ){
				so.setPrinttimes(1);
			}
			parameters.put("printtime", so.getPrinttimes());
			parameters.put("item1", "");
			parameters.put("item2", "");
			//parameters.put("printman", users.getRealname());
			
			AppPeddleOrderDetail asld = new AppPeddleOrderDetail();
			List<PeddleOrderDetail> sals = asld.getPeddleOrderDetailObjectByPOID(id);
			ArrayList als = new ArrayList();
			int rows = 1;
			for (PeddleOrderDetail ttd :  sals) {
				PeddleOrderDetailForm sldf = new PeddleOrderDetailForm();			
				sldf.setProductname(ttd.getProductid()+"|"+ttd.getProductname());			
				sldf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit", 
						Integer.parseInt(ttd.getUnitid().toString())));
				sldf.setUnitprice(ttd.getTaxunitprice());
				sldf.setQuantity(ttd.getQuantity());	
				sldf.setSubsum(ttd.getSubsum());
				sldf.setRows(rows);
				als.add(sldf);
				rows ++;
			}

			
//			JasperPrint print = JasperFillManager.fillReport(report,
//					parameters, new JRBeanCollectionDataSource(als));
//			JRHtmlExporter exporter = new JRHtmlExporter();
//			ByteArrayOutputStream oStream = new ByteArrayOutputStream();
//
//			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
//			exporter.setParameter(
//					JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
//					Boolean.FALSE);
//			exporter
//					.setParameter(JRExporterParameter.CHARACTER_ENCODING, "GBK");
//			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);
//
//			exporter.exportReport();
//
//			byte[] bytes = oStream.toByteArray();
//			if (bytes != null && bytes.length > 0) {
//				response.setContentType("text/html");
//				response.setContentLength(bytes.length);
//				ServletOutputStream ouputStream = response.getOutputStream();
//				ouputStream.write(bytes, 0, bytes.length);
//				ouputStream.flush();
//				ouputStream.close();
//			}
//			JasperPrintManager.printReport(print, true);
			
			
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
			JasperPrintManager.printReport(print, true);
			
			
			//return mapping.findForward("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
