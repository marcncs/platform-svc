package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppPeddleOrder;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PeddleOrder;
import com.winsafe.drp.dao.PeddleOrderForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class PeddleOrderDayDealAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			int pagesize = 20;
			String paymode = request.getParameter("paymode");
			String condition = " so.isblankout=0 ";
			//现金
			if ( paymode != null && "0".equals(paymode) ){
				condition = condition +" and so.paymentmode in(0,1)";
			}//挂帐
			else if ( paymode != null && "1".equals(paymode) ){
				condition = condition +" and so.paymentmode in(3,4,5,6)";
			}//支票
			else if ( paymode != null && "2".equals(paymode) ){
				condition = condition +" and so.paymentmode=2";
			}
			String begindate = request.getParameter("BeginDate");			
			String enddate = request.getParameter("EndDate");
			
			
			Map map = new HashMap(request.getParameterMap());
			if ( null == begindate || "".equals(begindate) ){
				begindate = DateUtil.getCurrentDateString();
				map.put("BeginDate", begindate);	
				
			}
			if ( null == enddate || "".equals(enddate) ){
				enddate = DateUtil.getCurrentDateString();
				map.put("EndDate", enddate);			
			}
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "PeddleOrder", };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
		
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap," MakeDate");
			whereSql = whereSql  + timeCondition + condition; 
			whereSql = DbUtil.getWhereSql(whereSql);
			
			Object obj[] = DbUtil.setDynamicPager(request, "PeddleOrder as so",
					whereSql, pagesize, "PeddleOrderDayDealReport");
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

		

			AppPeddleOrder appo = new AppPeddleOrder();			
			List list = appo.searchPeddleOrder(pagesize, whereSql, tmpPgInfo);			
			List newlist = new ArrayList();
			double allsum = appo.getPeddleOrderTotal(whereSql);
			double totalsum = 0.00;
			AppUsers au = new AppUsers();
			for (int d = 0; d < list.size(); d++) {
				PeddleOrderForm pof = new PeddleOrderForm();
				PeddleOrder o = (PeddleOrder)list.get(d);
				pof.setId(o.getId());
				pof.setCid(o.getCid());
				pof.setCname(o.getCname());
				pof.setCmobile(o.getCmobile());
//				pof.setMakeidname(au.getUsersByid(o.getMakeid()).getRealname());
				pof.setTotalsum(o.getTotalsum());
				totalsum += pof.getTotalsum();
				newlist.add(pof);
			}	
			
			
			request.setAttribute("newlist", newlist);
			request.setAttribute("xjtotalsum", DataFormat.currencyFormat(totalsum));			
			request.setAttribute("allsum", DataFormat.currencyFormat(allsum));
			

			AppOrgan ao = new AppOrgan();
			List alos = ao.getOrganToDown(users.getMakeorganid());
			

			List als = au.getIDAndLoginNameByOID2(users.getMakeorganid());
			
			request.setAttribute("als", als);
			request.setAttribute("alos", alos);
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("begindate", begindate);
			request.setAttribute("enddate", enddate);
			request.setAttribute("ProductName", request.getParameter("ProductName"));
			request.setAttribute("CName", request.getParameter("CName"));
			request.setAttribute("paymode", paymode);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
