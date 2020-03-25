package com.winsafe.drp.action.ditch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

/**
 * @author : jerry
 * @version : 2009-8-26 下午03:45:59 www.winsafe.cn
 */
public class ListRatifyOrganWithdrawAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);
		try {

			String Condition = "ow.isaudit = 1 and  ow.porganid = '"
					+ users.getMakeorganid() + "'";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "OrganWithdraw" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppOrganWithdraw appS = new AppOrganWithdraw();
			List lists = appS.getOrganWithdrawAll(request, pagesize, whereSql);
			request.setAttribute("list", lists);
			request.setAttribute("isshow", "yes");
			request.setAttribute("MakeOrganID", request
					.getParameter("MakeOrganID"));
			request.setAttribute("IsAudit", map.get("IsAudit"));
			request.setAttribute("POrganID", request.getParameter("POrganID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("MakeDeptID", request
					.getParameter("MakeDeptID"));
			request.setAttribute("IsRatify", map.get("IsRatify"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			request.setAttribute("IsAffirm", map.get("IsAffirm"));
			request.setAttribute("IsComplete", map.get("IsComplete"));
			DBUserLog.addUserLog(userid, 4, "渠道管理>>列表渠道发票!");
			return mapping.findForward("list");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}