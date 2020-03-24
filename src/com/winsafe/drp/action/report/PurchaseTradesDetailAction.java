package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPurchaseTradesDetail;
import com.winsafe.drp.dao.DetailReportForm;
import com.winsafe.drp.dao.PurchaseTrades;
import com.winsafe.drp.dao.PurchaseTradesDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class PurchaseTradesDetailAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try{
			int pagesize = 20;
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}
			String Condition = " pt.id=ptd.ptid and isreceive=1 and pt.isblankout=0 " + visitorgan+ " ";

			String[] tablename = { "PurchaseTrades", "PurchaseTradesDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition(" MakeDate");
			
			whereSql = whereSql + timeCondition  + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			
			AppPurchaseTradesDetail asod=new AppPurchaseTradesDetail(); 
			double sumobj = asod.getTotalSubum(whereSql);
		     
	        List sodls = asod.getDetailReport(request,pagesize, whereSql);
	        ArrayList alsod=new ArrayList();
	        Double totalsum=0.00;
	        for(int d = 0;d<sodls.size();d++){
	        	DetailReportForm sodf = new DetailReportForm();
	        	Object[] o = (Object[])sodls.get(d);
	        	PurchaseTrades pt = (PurchaseTrades)o[0];
	   
	        	PurchaseTradesDetail pbd = (PurchaseTradesDetail)o[1];
	        	sodf.setMakedate(DateUtil.formatDate(pt.getMakedate()));
	        	sodf.setPid(pt.getProvideid());
	        	sodf.setOname(pt.getProvidename());
	        	sodf.setBillid(pbd.getPtid());
	        	sodf.setProductid(pbd.getProductid());
	        	sodf.setProductname(pbd.getProductname());
	        	sodf.setSpecmode(pbd.getSpecmode());
	        	sodf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit",		                  
	                    pbd.getUnitid()));
	        	sodf.setQuantity(pbd.getQuantity());
	        	totalsum += sodf.getQuantity();
	        	alsod.add(sodf);
	        }
		       
			request.setAttribute("totalqt", totalsum);
			request.setAttribute("alsod", alsod);
		
			request.setAttribute("sumobj", sumobj);
		      

			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("ProvideID", request.getParameter("ProvideID"));
			request.setAttribute("ProvideName", request.getParameter("ProvideName"));
			request.setAttribute("ProductName", request.getParameter("ProductName"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>采购>>列表采购换货明细");
	      return mapping.findForward("success");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
