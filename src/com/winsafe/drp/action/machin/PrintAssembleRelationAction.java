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

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppAssembleRelation;
import com.winsafe.drp.dao.AppAssembleRelationDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AssembleRelation;
import com.winsafe.drp.dao.AssembleRelationDetail;
import com.winsafe.drp.dao.AssembleRelationDetailForm;
import com.winsafe.drp.dao.AssembleRelationForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.NumToChRMB;

public class PrintAssembleRelationAction extends Action {

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		try {
			String reportSource = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/reports/AssembleRelation.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppAssembleRelation asb = new AppAssembleRelation();			
			AssembleRelation ar = asb.getAssembleRelationByID(id);
			AssembleRelationForm arf = new AssembleRelationForm();
			
			AppUsers au = new AppUsers();			

			arf.setId(id);
			arf.setArproductid(ar.getArproductid());
			arf.setArproductname(ar.getArproductname());
			arf.setArspecmode(ar.getArspecmode());
			arf.setArunitid(ar.getArunitid());
			arf.setArunitidname(HtmlSelect.getResourceName(request, "CountUnit", ar.getArunitid()));
			arf.setArquantity(ar.getArquantity());
			arf.setRemark(ar.getRemark());
			arf.setMakeidname(au.getUsersByid(ar.getMakeid()).getRealname());
			arf.setMakedate(ar.getMakedate());

			Map parameters = new HashMap();
			parameters.put("billtype", "组装关系表");
			parameters.put("id", id);
			parameters.put("arproductid", arf.getArproductid());
			parameters.put("arproductname",arf.getArproductname());
			parameters.put("arspecmode", arf.getArspecmode());
			parameters.put("arunitid", arf.getArunitidname());
			parameters.put("arquantity", arf.getArquantity());
			parameters.put("makeidname", arf.getMakeidname());
			parameters.put("makedate", DateUtil.formatDate(arf.getMakedate()));
			parameters.put("remark", arf.getRemark());
			
			String numRMB =NumToChRMB.numToRMB("0");
			
			parameters.put("totalsumcapital", numRMB);
			parameters.put("totalsum", 0.00);
			
			
			AppAssembleRelationDetail asbd = new AppAssembleRelationDetail();
			List sbls = asbd.getAssembleRelationDetailBySIID(id);
			ArrayList als = new ArrayList();	
			AssembleRelationDetail ard = null;

			for (int i = 0; i < sbls.size(); i++) {
				AssembleRelationDetailForm sldf = new AssembleRelationDetailForm();
				ard = (AssembleRelationDetail) sbls.get(i);
				sldf.setProductid(ard.getProductid());
				sldf.setProductname(ard.getProductid()+" "+ard.getProductname()+ard.getSpecmode());
				sldf.setUnitidname(HtmlSelect.getResourceName(request, "CountUnit",ard.getUnitid()));
				sldf.setUnitprice(ard.getUnitprice());
				sldf.setQuantity(ard.getQuantity());
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
			//return mapping.findForward("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
