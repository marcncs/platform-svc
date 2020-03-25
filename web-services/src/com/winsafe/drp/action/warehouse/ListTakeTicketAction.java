package com.winsafe.drp.action.warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeBill;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListTakeTicketAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		UsersBean users = UserManager.getUser(request);
		super.initdata(request);try{
			String billno = request.getParameter("billno");
			if ( null == billno ){
				billno = (String)request.getSession().getAttribute("billno");				
			}
			request.getSession().setAttribute("billno", billno);
			String Condition=" billno='"+billno+"' " ;

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "TakeTicket" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			AppTakeTicket asl = new AppTakeTicket();			
			
			asl.updTakeReadStatusById(billno);
			List pils = asl.getTakeTicket(request, pagesize, whereSql);

			///单据是否查看?
			AppTakeBill atb = new AppTakeBill();			
			atb.updIsRead(billno, userid);
				
			request.setAttribute("also", pils);
			saveToken(request);
			DBUserLog.addUserLog(users.getUserid(), 7, "列表检货小票");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
