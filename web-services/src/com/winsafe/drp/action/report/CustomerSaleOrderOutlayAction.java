package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.CustomerSaleOrderOutlay;
import com.winsafe.drp.dao.CustomerSaleOrderOutlayForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DbUtil;

public class CustomerSaleOrderOutlayAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		 UsersBean users = UserManager.getUser(request);
	      Integer userid = users.getUserid();
	      try{
	    	  String cid=request.getParameter("CID");
	    	  String begindate = request.getParameter("BeginDate");
	    	  String enddate = request.getParameter("EndDate");
	    	  String Condition_s="";
	    	  String Condition_o="";
	    	  
//	    	  Map map = new HashMap(request.getParameterMap());
//		      Map tmpMap = EntityManager.scatterMap(map);
		      
//		      String[] tablename={"SaleOrder","SaleOrderDetail","Outlay"};
//		      String whereSql = EntityManager.getTmpWhereSql(map, tablename);
	    	  
	    	  String whereSql_s =" where ";
	    	  String whereSql_o =" WHERE ";
	    	  if(cid!=null &&!cid.equals("")){
	    		  Condition_s +=" so.cid='"+cid+"' and ";
	    		  Condition_o +=" o.customerid='"+cid+"' and ";
	    	  }
	    	  if(begindate!=null && !begindate.equals("")){
	    		  Condition_s +=" so.makedate>='"+begindate+"' and ";
	    		  Condition_o +=" o.makedate>='"+begindate+"' and ";
	    	  }
	    	  if(enddate !=null && !enddate.equals("")){
	    		  Condition_s +=" so.makedate<='"+enddate+"' and ";
	    		  Condition_o +=" o.makedate<='"+enddate+"' and ";
	    	  }

//		      String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
//		          " RecordDate");
		      whereSql_s = whereSql_s + Condition_s +"so.id=sod.soid"; 
		      whereSql_o = whereSql_o + Condition_o;
		     // System.out.println("---"+whereSql_s);
		      whereSql_s = DbUtil.getWhereSql(whereSql_s); 
		      whereSql_o = DbUtil.getWhereSql(whereSql_o);
		      
		      AppCustomer ac = new AppCustomer();
		      AppSaleOrder aso = new AppSaleOrder();

		      List sals = aso.getCustomerSaleOrderOutlay(whereSql_s,whereSql_o);
		      ArrayList als = new ArrayList();
		      //System.out.println("---VVVV-"+sals.size());
		      for (int i = 0; i < sals.size(); i++) {
		    	  CustomerSaleOrderOutlayForm csoof = new CustomerSaleOrderOutlayForm();
		    	  CustomerSaleOrderOutlay o = (CustomerSaleOrderOutlay) sals.get(i);
		    	  csoof.setCid(o.getCid());
		    	  csoof.setCidname(ac.getCustomer(o.getCid()).getCname());
		    	  csoof.setMakedate(o.getMakedate());
		    	  csoof.setSubsum(o.getSubsum());
		    	  csoof.setTotaloutlay(o.getTotaloutlay());

		        als.add(csoof);
		      }
		      request.setAttribute("als", als);
		      return mapping.findForward("list");
	      }catch(Exception e){
	    	  e.printStackTrace();
	      }
		return new ActionForward(mapping.getInput());
	}

}
