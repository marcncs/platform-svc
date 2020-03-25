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
import com.winsafe.drp.dao.AppLargess;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListLargessAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		
		int pagesize = 10;
		  initdata(request);
		try {
			Integer objsort  = Integer.valueOf(request.getParameter("objsort"));
			
			
			
			String Condition = " l.objsort = "+objsort +" and l.makeorganid = '"+users.getMakeorganid()+"'";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Largess" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			whereSql = whereSql + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			AppLargess appl = new AppLargess();
			List usList = appl.getLargess(request, pagesize, whereSql);
			request.setAttribute("objsort", objsort);
			request.setAttribute("hList", usList);

			DBUserLog.addUserLog(userid, 5, "赠品>>列表赠品");
			return mapping.findForward("list");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
