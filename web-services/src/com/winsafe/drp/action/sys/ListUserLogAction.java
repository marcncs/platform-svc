package com.winsafe.drp.action.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppLeftMenu;
import com.winsafe.drp.dao.AppUserLog;
import com.winsafe.drp.dao.UserLog;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class ListUserLogAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		int pagesize = 20;
		initdata(request);
		AppLeftMenu appLeftMenu = new AppLeftMenu();
		
		try {
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "UserLog" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" LogTime");
			String blur = DbUtil.getBlur(map, tmpMap, "Detail");
			whereSql = whereSql + blur + timeCondition;
			request.getParameter("modeltype");
			whereSql = DbUtil.getWhereSql(whereSql);

			AppUserLog aul = new AppUserLog();
			List<UserLog> ulls = aul.getUserLog(request, pagesize, whereSql);
			for(UserLog userLog : ulls){
				userLog.setModifycontentAbbr(StringUtil.abbreviate(userLog.getModifycontent(), 50));
			}
			//查询所有最小模块
			List modelList = appLeftMenu.getMinLeftMenu();
			
			request.setAttribute("uls", ulls);
			request.setAttribute("modelTypeList", modelList);

			// DBUserLog.addUserLog(userid, "列表用户日志");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
