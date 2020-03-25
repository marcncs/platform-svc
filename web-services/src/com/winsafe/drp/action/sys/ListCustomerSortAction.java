package com.winsafe.drp.action.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCustomerSort;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListCustomerSortAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		int pagesize = 10;

		try {
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			// String sql = "select * from warehouse ";
			String[] tablename = { "CustomerSort" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			// String timeCondition = DbUtil.getTimeCondition(map, tmpMap, "
			// ReferDate"); 
			// String blur = DbUtil.getBlur(map, tmpMap, " ReportContent");
			// 
			// whereSql = whereSql + ; 

			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = (DbUtil.setPager(request, "CustomerSort", whereSql,
					pagesize));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppCustomerSort appcs = new AppCustomerSort();
			List cslist = appcs.searchCustomerSort(pagesize, whereSql,
					tmpPgInfo);

			request.setAttribute("cslist", cslist);

			DBUserLog.addUserLog(userid,11, "基础设置>>列表客户分类");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
