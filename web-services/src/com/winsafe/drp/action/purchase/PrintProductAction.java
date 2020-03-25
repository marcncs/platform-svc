package com.winsafe.drp.action.purchase;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.Product;
import com.winsafe.hbm.util.Internation;

public class PrintProductAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		try {
			String reportSource = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/reports/Product.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppProduct aps = new AppProduct();
			AppProductStruct ps = new AppProductStruct();
			Product p = aps.getProductByID(id);
			
			Map parameters = new HashMap();		
			parameters.put("id", p.getId());
			parameters.put("specmode", p.getProductname()+" "+p.getSpecmode());
			parameters.put("grade", "一级");
			parameters.put("barcode", p.getBarcode());
			parameters.put("brand", Internation.getStringByKeyPositionDB("Brand",p.getBrand()));
			parameters.put("psidname", ps.getProductStructById(p.getPsid()).getSortname());
			parameters.put("unitname", Internation.getStringByKeyPositionDB("CountUnit", p.getCountunit()));		
			

			JasperPrint print = JasperFillManager.fillReport(report,
					parameters);

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

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
