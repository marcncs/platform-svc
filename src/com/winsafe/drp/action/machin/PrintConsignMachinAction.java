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

import com.winsafe.drp.dao.AppConsignMachin;
import com.winsafe.drp.dao.AppConsignMachinDetail;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.ConsignMachin;
import com.winsafe.drp.dao.ConsignMachinDetail;
import com.winsafe.drp.dao.ConsignMachinDetailForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.NumToChRMB;

public class PrintConsignMachinAction extends Action {

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		try {
			String reportSource = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/reports/ConsignMachin.jasper");
			JasperReport report = (JasperReport) JRLoader
					.loadObject(reportSource);

			AppConsignMachin asb = new AppConsignMachin();			
			ConsignMachin ar = asb.getConsignMachinByID(id);
			
			
			AppUsers au = new AppUsers();			
			AppProvider ap = new AppProvider();

			Map parameters = new HashMap();
			parameters.put("billtype", "委外加工单");
			parameters.put("id", id);
			String pname =ap.getProviderByID(ar.getPid()).getPname();
			parameters.put("pname", pname);
			parameters.put("linkman", ar.getPlinkman());
			parameters.put("tel", ar.getTel());
			parameters.put("arproductid", ar.getCproductid());
			parameters.put("arproductname",ar.getCproductname());
			parameters.put("arspecmode", ar.getCspecmode());
			String unitname = HtmlSelect.getResourceName(request, "CountUnit", ar.getCunitid());
			parameters.put("arunitid", unitname);
			parameters.put("aquantity", ar.getCquantity());
			parameters.put("cquantity", ar.getCompletequantity());
			parameters.put("cdate", DateUtil.formatDate(ar.getCompleteintenddate()));
			parameters.put("price", ar.getCunitprice());
			String paymode = HtmlSelect.getNameByOrder(request, "PayMode", ar.getPaymode());
			parameters.put("paymode", paymode);
			String makename = au.getUsersByID(ar.getMakeid()).getRealname();
			parameters.put("makeidname", makename);
			parameters.put("makedate", DateUtil.formatDate(ar.getMakedate()));
			parameters.put("remark", ar.getRemark());
			
			String numRMB="";
			if(ar.getCtotalsum()==null){
				 numRMB =NumToChRMB.numToRMB(ar.getCtotalsum().toString());
			}else{
				numRMB =NumToChRMB.numToRMB("0");
			}
			parameters.put("totalsumcapital", numRMB);
			parameters.put("totalsum", ar.getCtotalsum());
			
			
			
			AppConsignMachinDetail asbd = new AppConsignMachinDetail();
			List sbls = asbd.getConsignMachinDetailBySIID(id);
			ArrayList als = new ArrayList();	
			ConsignMachinDetail ard = null;

			for (int i = 0; i < sbls.size(); i++) {
				ConsignMachinDetailForm sldf = new ConsignMachinDetailForm();
				ard = (ConsignMachinDetail) sbls.get(i);
				sldf.setProductid(ard.getProductid());
				sldf.setProductname(ard.getProductid()+" "+ard.getProductname()+""+ard.getSpecmode());
				sldf.setUnitidname(HtmlSelect.getResourceName(request, "CountUnit",ard.getUnitid()));
				sldf.setQuantity(ard.getQuantity());
				sldf.setAlreadyquantity(ard.getAlreadyquantity());
				sldf.setUnitprice(ard.getUnitprice());
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
