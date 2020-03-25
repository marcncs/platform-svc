package com.winsafe.drp.action.self;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppAffiche;
import com.winsafe.drp.dao.AppSuggestionBox;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListSuggestionAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request);
		AppSuggestionBox appSuggestionBox = new AppSuggestionBox();

		int pageSize = 10;
		try {
			UsersBean usersBean = UserManager.getUser(request);
			Integer userid = usersBean.getUserid();

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "SuggestionBox" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap, " MakeDate");

			String blur = DbUtil.getBlur(map, tmpMap, "suggestionMsg");
			whereSql = whereSql + timeCondition + blur;
			whereSql = DbUtil.getWhereSql(whereSql);

			List listview = appSuggestionBox.selectSuggestionBox(request, pageSize, whereSql);
			request.setAttribute("aaList", listview);
			DBUserLog.addUserLog(request, "列表");// 日志
			return mapping.findForward("list");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("list");
	}

}
