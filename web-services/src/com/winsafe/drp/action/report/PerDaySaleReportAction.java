package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppSaleOrderDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PerDaySaleReportForm;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class PerDaySaleReportAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		int pagesize=10;
		AppSaleOrderDetail asbd = new AppSaleOrderDetail();
		String begindate = request.getParameter("BeginDate");
		String enddate = request.getParameter("EndDate");
		String makeid = request.getParameter("MakeID");
		try{
			String condition = " so.id=sod.soid and so.isendcase=1 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = {"SaleOrder","SaleOrderDetail"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"MakeDate");

			whereSql = whereSql +timeCondition+ condition ; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = DbUtil.setExtraPager(request, "Sale_Order_Detail as sod,Sale_Order as so ",
					whereSql, pagesize," sod.productid ");
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];
			List rs = asbd.getPerDaySaleReport(whereSql, pagesize, tmpPgInfo);
			ArrayList rls = new ArrayList();
			AppProduct ap = new AppProduct();
			String productid;
			double subsum,totalsum=0.00;
			for(int r=0;r<rs.size();r++){
				PerDaySaleReportForm rf = new PerDaySaleReportForm();
				Object[] ob = (Object[]) rs.get(r);
				productid = ob[0].toString();
				rf.setProductid(productid);
				rf.setProductname(ap.getProductByID(productid).getProductname());
				rf.setUnitname(Internation.getStringByKeyPositionDB("CountUnit",			           
			            ap.getProductByID(productid).getCountunit()));
				rf.setQuantity(Double.valueOf(ob[1].toString()));
				subsum = Double.valueOf(ob[2].toString());
				rf.setSubsum(subsum);
				totalsum = totalsum + subsum;
				rls.add(rf);
			}
			
			
			AppUsers au = new AppUsers();
		    List uls = au.getAllUsers();
		    ArrayList auls = new ArrayList();
		    for(int u=0;u<uls.size();u++){
		    	UsersBean ub = new UsersBean(); 
		    	Users ob = (Users)uls.get(u);
		    	ub.setUserid(ob.getUserid());
		    	ub.setRealname(ob.getRealname());
		    	auls.add(ub);
		    }

		    request.setAttribute("begindate", begindate);
		    request.setAttribute("enddate", enddate);
		    request.setAttribute("makeid", makeid);
		    request.setAttribute("totalsum", totalsum);
		    request.setAttribute("rls", rls);
		    request.setAttribute("auls", auls);
		    return mapping.findForward("dayreport");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return new ActionForward(mapping.getInput());
	}

}
