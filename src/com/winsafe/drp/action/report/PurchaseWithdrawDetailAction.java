package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPurchaseWithdrawDetail;
import com.winsafe.drp.dao.DetailReportForm;
import com.winsafe.drp.dao.PurchaseWithdrawDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class PurchaseWithdrawDetailAction extends BaseAction{
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
			String Condition = " pw.id=pwd.pwid and  ( pw.isendcase=1 and pw.isblankout=0 " + visitorgan+ " )";

			String[] tablename = { "PurchaseWithdraw", "PurchaseWithdrawDetail" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition(" MakeDate");
			String blur = getKeyWordCondition("PName", "ProductName",
					"ProductID", "PID", "MakeDate", "MakeOrganID");
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			
			AppPurchaseWithdrawDetail asod=new AppPurchaseWithdrawDetail(); 
			List sumobj = asod.getTotalSubum(whereSql);
		     
	        List sodls = asod.getDetailReport(request,pagesize, whereSql);
	        ArrayList alsod=new ArrayList();
	        Double totalsum=0.00;
	        for(int d = 0;d<sodls.size();d++){
	        	DetailReportForm sodf = new DetailReportForm();
	        	Object[] o = (Object[])sodls.get(d);
	        	String pid = (String)o[0];
	        	String pname = (String)o[1];
	        	String makedate = DateUtil.formatDateTime((Date)o[2]);
	        	PurchaseWithdrawDetail pbd = (PurchaseWithdrawDetail)o[3];
	        	sodf.setMakedate(makedate);
	        	sodf.setPid(pid);
	        	sodf.setOname(pname);
	        	sodf.setBillid(pbd.getPwid());
	        	sodf.setProductid(pbd.getProductid());
	        	sodf.setProductname(pbd.getProductname());
	        	sodf.setSpecmode(pbd.getSpecmode());
	        	sodf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit",		                  
	                    pbd.getUnitid()));
	        	sodf.setUnitprice(pbd.getUnitprice());
	        	sodf.setQuantity(pbd.getQuantity());
	        	sodf.setSubsum(pbd.getSubsum());
	        	totalsum += sodf.getSubsum();
	        	alsod.add(sodf);
	        }
		       
			request.setAttribute("totalsum", totalsum);
			request.setAttribute("alsod", alsod);
		
			request.setAttribute("sumobj", sumobj);
		      

			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("PID", request.getParameter("PID"));
			request.setAttribute("PName", request.getParameter("PName"));
			request.setAttribute("ProductName", request.getParameter("ProductName"));
			DBUserLog.addUserLog(userid, 10,"报表分析>>采购>>列表采购退货明细");
	      return mapping.findForward("success");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
