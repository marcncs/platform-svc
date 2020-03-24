package com.winsafe.drp.action.machin;

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
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppOtherShipmentBill;
import com.winsafe.drp.dao.AppOtherShipmentBillAll;
import com.winsafe.drp.dao.AppOtherShipmentBillDAll;
import com.winsafe.drp.dao.AppOtherShipmentBillDetail;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.OtherShipmentBill;
import com.winsafe.drp.dao.OtherShipmentBillAll;
import com.winsafe.drp.dao.OtherShipmentBillAllForm;
import com.winsafe.drp.dao.OtherShipmentBillDAllForm;
import com.winsafe.drp.dao.OtherShipmentBillDetailForm;
import com.winsafe.drp.dao.OtherShipmentBillForm;
import com.winsafe.hbm.util.Internation;

public class OtherShipmentBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		super.initdata(request);try{
			String reportSource = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/reports/OtherShipmentBill.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppOtherShipmentBillAll asb = new AppOtherShipmentBillAll();
			OtherShipmentBillAll sb = new OtherShipmentBillAll();
			sb = asb.getOtherShipmentBillByID(id);
			OtherShipmentBillAllForm sbf = new OtherShipmentBillAllForm();
			
			AppUsers au = new AppUsers();
			AppCustomer ac = new AppCustomer();
			AppDept ad = new AppDept();
			AppWarehouse aw = new AppWarehouse();

			sbf.setId(id);
			sbf.setShipmentsortname(Internation.getStringByKeyPosition("ShipmentSort",
		              request,
		              sb.getShipmentsort(), "global.sys.SystemResource"));
//			sbf.setShipmentdeptname((ad.getDeptByID(sb.getShipmentdept()).getDeptname()));
			sbf.setRequiredate(String.valueOf(sb.getRequiredate()));
			sbf.setWarehouseidname(aw.getWarehouseByID(sb.getWarehouseid()).getWarehousename());
			sbf.setMakeidname(au.getUsersByid(sb.getMakeid()).getRealname());
			sbf.setMakedate(sb.getMakedate().toString());
			sbf.setTotalsum(sb.getTotalsum());
			sbf.setRemark(sb.getRemark());

			Map parameters = new HashMap();
			parameters.put("title", Internation.getStringByKeyPosition("PrinterName",
		            request,0, "global.sys.SystemResource"));
			parameters.put("billtype", Internation.getStringByKeyPosition("PrinterName",
		            request,8, "global.sys.SystemResource"));
			parameters.put("id", id);
			parameters.put("shipmentsortname", sbf.getShipmentsortname());
			parameters.put("shipmentdeptname", sbf.getShipmentdeptname());
			parameters.put("warehouseidname", sbf.getWarehouseidname());
			parameters.put("requiredate", sbf.getRequiredate().substring(0,10));
			parameters.put("makeidname", sbf.getMakeidname());
			parameters.put("makedate", String.valueOf(sbf.getMakedate()).substring(0,10));
			parameters.put("totalsum", sbf.getTotalsum());
			parameters.put("remark", sbf.getRemark());

			AppOtherShipmentBillDAll asbd = new AppOtherShipmentBillDAll();
			List sbls = asbd.getOtherShipmentBillDetailBySbID(id);
			ArrayList als = new ArrayList();
			AppProduct ap = new AppProduct();

			for (int i = 0; i < sbls.size(); i++) {
				OtherShipmentBillDAllForm sldf = new OtherShipmentBillDAllForm();
				Object[] o = (Object[]) sbls.get(i);
				sldf.setProductid(o[2].toString());
				sldf.setProductname(ap.getProductByID(sldf.getProductid())
						.getProductname());
				sldf.setSpecmode(String.valueOf(o[4]));
				//padf.setUnitid(Integer.valueOf(o[3].toString()));
				sldf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit",
						Integer.parseInt(o[5].toString())));
				sldf.setBatch(String.valueOf(o[6]));
				sldf.setUnitprice(Double.valueOf(o[7].toString()));
				sldf.setQuantity(Double.valueOf(o[8].toString()));
				sldf.setSubsum(Double.valueOf(o[9].toString()));
				als.add(sldf);
			}

			JasperPrint print = JasperFillManager.fillReport(report,
					parameters, new JRBeanCollectionDataSource(als));
			
	/*		
			JRHtmlExporter exporter = new JRHtmlExporter();
			  ByteArrayOutputStream oStream = new ByteArrayOutputStream();
			  super.initdata(request);try{
				  JasperPrint print = JasperFillManager.fillReport(report,
							parameters, new JRBeanCollectionDataSource(als));
				  
			   exporter.setParameter(
			     JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
			     Boolean.FALSE);
			   exporter
			     .setParameter(JRExporterParameter.JASPER_PRINT, print);
			   exporter
			     .setParameter(JRExporterParameter.CHARACTER_ENCODING, "GBK");
			   exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);
			   exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "../servlets/image?image=");

			   exporter.exportReport();
			   byte[] bytes = oStream.toByteArray();
			   
			   if (bytes != null && bytes.length > 0) {
					response.setContentType("text/html");
					response.setContentLength(bytes.length);
					ServletOutputStream ouputStream = response
							.getOutputStream();
					ouputStream.write(bytes, 0, bytes.length);
					ouputStream.flush();
					ouputStream.close();
			   }
			  // return bytes;
			  } catch (Exception e) {
			   //throw new DemoException("Report Export Failed.");
			  }
*/
			
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
			//return mapping.findForward("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
