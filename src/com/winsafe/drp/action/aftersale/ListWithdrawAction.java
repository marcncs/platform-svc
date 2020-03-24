package com.winsafe.drp.action.aftersale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppWithdraw;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListWithdrawAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);
		try {
  	
	    	String Condition = " (w.makeid="+userid+" "+getOrVisitOrgan("w.makeorganid")+") ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Withdraw" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String blur = DbUtil.getOrBlur(map, tmpMap, "KeysContent");
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + timeCondition + blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppWithdraw asl = new AppWithdraw();
			List pils = asl.getWithdraw(request,pagesize, whereSql);


			request.setAttribute("also", pils);
			request.setAttribute("CID", request.getParameter("CID"));
			request.setAttribute("IsAudit", map.get("IsAudit"));
			request.setAttribute("IsBlankOut", request.getParameter("IsBlankOut"));
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeDeptID", request.getParameter("MakeDeptID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("BeginDate", request.getParameter("BeginDate"));
			request.setAttribute("EndDate", request.getParameter("EndDate"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			DBUserLog.addUserLog(userid, 6,"零售管理>>列表退货单");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
