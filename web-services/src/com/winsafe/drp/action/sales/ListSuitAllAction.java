package com.winsafe.drp.action.sales;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSuit;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.RequestTool;


public class ListSuitAllAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		Integer objsort = RequestTool.getInt(request, "objsort");
		
		int pagesize = 5;
		initdata(request);
		try {
			String visitorgan = "";
			if(users.getVisitorgan()!=null&&users.getVisitorgan().length()>0){
				visitorgan = getOrVisitOrgan("s.makeorganid");
			}

			String Condition = " (s.makeid="+userid+" "+visitorgan+" )";
			Condition += " and s.objsort = " +objsort;
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Suit" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap, "MakeDate"); 
			String blur = DbUtil.getOrBlur(map, tmpMap, "ID","CID","CName");
			// 
			whereSql = whereSql + timeCondition + blur +Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppSuit apps = new AppSuit();

			List usList = apps.searchSuit(request,pagesize, whereSql);
			
			
			request.setAttribute("objsort", objsort);
			request.setAttribute("hList", usList);

			DBUserLog.addUserLog(userid,5,"会员/积分管理>>列表投诉抱怨");
			return mapping.findForward("list");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
