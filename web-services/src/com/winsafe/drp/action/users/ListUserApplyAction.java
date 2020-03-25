package com.winsafe.drp.action.users;

import java.io.IOException;
import java.util.ArrayList;
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
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UserApply;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListUserApplyAction extends BaseAction {

	private AppUsers appUsers = new AppUsers();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// 初始化
		initdata(request);
		int pagesize = 10;
		try {
			String Condition = " 1=1 ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "UserApply" };
			String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
			String timeCondition = DbUtil.getTimeCondition4(map, tmpMap,
					"u.makeDate");
			String blur = DbUtil.getOrBlur2(map, tmpMap, "u.name", "u.mobile",
					"u.organName");
			whereSql = whereSql + timeCondition + blur + Condition;

			whereSql = DbUtil.getWhereSql(whereSql);

			List<Object[]> uaList = appUsers.getUserApplyList(request,
					pagesize, whereSql);
			List<UserApply> usList = new ArrayList<UserApply>();
			for(Object[] obj : uaList) {
				UserApply ua = (UserApply)obj[0];
				ua.setProvinceName((String)obj[1]);
				ua.setCityName((String)obj[2]);
				ua.setAreasName((String)obj[3]);
				usList.add(ua);
			}
			
			request.setAttribute("usList", usList);

			DBUserLog.addUserLog(userid, "系统管理", "用户审核>>列表用户");
			return mapping.findForward("usersList");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
