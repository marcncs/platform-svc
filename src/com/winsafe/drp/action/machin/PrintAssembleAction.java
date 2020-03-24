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

import com.winsafe.drp.dao.AppAssemble;
import com.winsafe.drp.dao.AppAssembleDetail;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Assemble;
import com.winsafe.drp.dao.AssembleDetail;
import com.winsafe.drp.dao.AssembleDetailForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class PrintAssembleAction extends Action {

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		try {
			String reportSource = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/reports/Assemble.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppAssemble asb = new AppAssemble();			
			Assemble ar = asb.getAssembleByID(id);
			
			
			AppUsers au = new AppUsers();			
			AppDept ad = new AppDept();
			

			Map parameters = new HashMap();
			parameters.put("billtype", "组装关系表");
			parameters.put("id", id);
			parameters.put("arproductid", ar.getAproductid());
			parameters.put("arproductname",ar.getAproductname());
			parameters.put("arspecmode", ar.getAspecmode());
			String unitname = HtmlSelect.getResourceName(request, "CountUnit", ar.getAunitid());
			parameters.put("arunitid", unitname);
			parameters.put("aquantity", ar.getAquantity());
			parameters.put("cquantity", ar.getCquantity());
			String deptname = ad.getDeptByID(ar.getAdept())==null?"":ad.getDeptByID(ar.getAdept()).getDeptname();
			parameters.put("adept", deptname);
			parameters.put("completeintenddate", DateUtil.formatDate(ar.getCompleteintenddate()));
			
			String makename = au.getUsersByID(ar.getMakeid()).getRealname();
			parameters.put("makeidname", makename);
			parameters.put("makedate", DateUtil.formatDate(ar.getMakedate()));
			parameters.put("remark", ar.getRemark());
			
			
			
			
			AppAssembleDetail asbd = new AppAssembleDetail();
			List sbls = asbd.getAssembleDetailByAid(id);
			ArrayList als = new ArrayList();	
			AssembleDetail ard = null;

			for (int i = 0; i < sbls.size(); i++) {
				AssembleDetailForm sldf = new AssembleDetailForm();
				ard = (AssembleDetail) sbls.get(i);
				sldf.setProductid(ard.getProductid());
				sldf.setProductname(ard.getProductid()+" "+ard.getProductname()+""+ard.getSpecmode());
				sldf.setUnitidname(HtmlSelect.getResourceName(request, "CountUnit",ard.getUnitid()));
				sldf.setQuantity(ard.getQuantity());
				sldf.setAlreadyquantity(ard.getAlreadyquantity());
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
