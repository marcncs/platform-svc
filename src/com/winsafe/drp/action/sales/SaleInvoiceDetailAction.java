package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleInvoice;
import com.winsafe.drp.dao.AppSaleInvoiceDetail;
import com.winsafe.drp.dao.SaleInvoice;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.RequestTool;

public class SaleInvoiceDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer id = RequestTool.getInt(request,"ID");
		super.initdata(request);
		try {
			AppSaleInvoice asi = new AppSaleInvoice();			
//			AppUsers au = new AppUsers();
//			AppCustomer ac = new AppCustomer();
			SaleInvoice si = asi.getSaleInvoiceByID(id);
//			SaleInvoiceForm sif = new SaleInvoiceForm();
//			sif.setId(si.getId());
//			sif.setCid(si.getCid());
//			sif.setCname(ac.getCustomer(si.getCid()).getCname());
//			sif.setInvoicecode(si.getInvoicecode());
//			sif.setInvoicecontent(si.getInvoicecontent());
//			sif.setInvoicetypename(Internation.getStringByKeyPosition(
//					"InvoiceType", request,si.getInvoicetype(),
//					"global.sys.SystemResource"));
//			sif.setMakeinvoicedate(String.valueOf(si.getMakeinvoicedate()));
//			sif.setInvoicedate(String.valueOf(si.getInvoicedate()));
//			sif.setInvoicesum(si.getInvoicesum());
//			sif.setMakename(au.getUsersByid(si.getMakeid()).getRealname());
//			sif.setMakedate(si.getMakedate());
//			sif.setUpdatename(au.getUsersByid(si.getUpdateid())
//					.getRealname());
//			sif.setLastdate(si.getLastdate());
//			sif.setMemo(si.getMemo());
//			sif.setIsaudit(si.getIsaudit());
//			sif.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
//		            request,
//		            si.getIsaudit(), "global.sys.SystemResource"));
//
//		      if(si.getAuditid()>0){
//		    	  sif.setAuditidname(au.getUsersByid(si.getAuditid()).getRealname());
//		      }else{
//		    	  sif.setAuditidname("");
//		      }
//		      sif.setAuditdate(si.getAuditdate());
//		      sif.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
//		            request,
//		            si.getIsaudit(), "global.sys.SystemResource"));

			AppSaleInvoiceDetail asld = new AppSaleInvoiceDetail();
			List sals = asld.getSaleInvoiceDetailObjectBySIID(id);
//			ArrayList als = new ArrayList();
//			for (int i = 0; i < sals.size(); i++) {
//				SaleInvoiceDetailForm sidf = new SaleInvoiceDetailForm();
//				SaleInvoiceDetail o = (SaleInvoiceDetail) sals.get(i);
//				sidf.setId(o.getId());
//				sidf.setSiid(o.getSiid());
//				sidf.setSoid(o.getSoid());
//				sidf.setSubsum(o.getSubsum());
//				sidf.setMakedate(String.valueOf(o.getMakedate()).substring(0,
//						10));
//				als.add(sidf);
//			}

			request.setAttribute("als", sals);
			request.setAttribute("sif", si);

			DBUserLog.addUserLog(userid,6,"销售发票>>销售发票详情,编号："+id); 
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
