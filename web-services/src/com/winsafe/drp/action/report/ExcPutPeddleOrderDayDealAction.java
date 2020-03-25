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

import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppPeddleOrder;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PeddleOrder;
import com.winsafe.drp.dao.PeddleOrderForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ExcPutPeddleOrderDayDealAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		try {
			String paymode = request.getParameter("paymode");
			String paymodename = "";
			String condition = " so.isblankout=0 ";
			//现金
			if ( paymode != null && "0".equals(paymode) ){
				condition = condition +" and so.paymentmode in(0,1)";
				paymodename = "现金";
			}//挂帐
			else if ( paymode != null && "1".equals(paymode) ){
				condition = condition +" and so.paymentmode in(3,4,5,6)";
				paymodename = "挂帐";
			}//支票
			else if ( paymode != null && "2".equals(paymode) ){
				condition = condition +" and so.paymentmode=2";
				paymodename = "支票";
			}
			String begindate = request.getParameter("BeginDate");			
			String enddate = request.getParameter("EndDate");
			
			
			Map map = new HashMap(request.getParameterMap());
			if ( null == begindate ){
				begindate = DateUtil.getCurrentDateString();
				map.put("BeginDate", begindate);	
				
			}
			if ( null == enddate ){
				enddate = DateUtil.getCurrentDateString();
				map.put("EndDate", enddate);			
			}
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "PeddleOrder", };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
		
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap," MakeDate");
			whereSql = whereSql  + timeCondition + condition; 
			whereSql = DbUtil.getWhereSql(whereSql);	


			AppPeddleOrder appo = new AppPeddleOrder();			
			List list = appo.searchPeddleOrder(whereSql);			
			List newlist = new ArrayList();
			double allsum = appo.getPeddleOrderTotal(whereSql);
			AppUsers au = new AppUsers();
			for (int d = 0; d < list.size(); d++) {
				PeddleOrderForm pof = new PeddleOrderForm();
				PeddleOrder o = (PeddleOrder)list.get(d);
				pof.setId(o.getId());
				pof.setCid(o.getCid());
				pof.setCname(o.getCname());
				pof.setCmobile(o.getCmobile());
				pof.setMakeidname(au.getUsersByid(o.getMakeid().intValue()).getRealname());
				pof.setTotalsum(o.getTotalsum());
				newlist.add(pof);
			}			
			
			request.setAttribute("newlist", newlist);
			request.setAttribute("allsum", DataFormat.currencyFormat(allsum));
			AppOrgan ao = new AppOrgan();
			String makeorganid = request.getParameter("MakeOrganID");
			if ( makeorganid != null && !makeorganid.equals("") ){
				request.setAttribute("MakeOrganID", ao.getOrganByID(makeorganid).getOrganname());
			}
			String MakeID = request.getParameter("MakeID");
			if ( MakeID != null && !MakeID.equals("") ){
				request.setAttribute("MakeID", au.getUsersByid(Integer.valueOf(MakeID)).getRealname());
			}

			request.setAttribute("begindate", begindate);
			request.setAttribute("enddate", enddate);
			request.setAttribute("CName", request.getParameter("CName"));
			request.setAttribute("paymode", paymodename);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
