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
import com.winsafe.drp.dao.AppOrganTrades;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

/**
 * @author : jerry
 * @version : 2009-8-26 下午03:45:59 www.winsafe.cn
 */
public class PrintListOrganTradesAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String isshow = request.getParameter("isshow");
			String Condition = "";
			if (isshow.equals("yes")) {
				Condition = "ot.isaudit = 1 and ot.porganid = '"
						+ users.getMakeorganid() + "'";
			} else {
				Condition = "ot.makeorganid = '" + users.getMakeorganid() + "'";
			}

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "OrganTrades" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppOrganTrades appS = new AppOrganTrades();
			List lists = appS.getOrganTradesAll(whereSql);
			request.setAttribute("list", lists);
			request.setAttribute("isshow", isshow);
			if (isshow.equals("yes")) {
				DBUserLog.addUserLog(userid, 4, "渠道管理>>打印渠道换货审核!");
			} else {
				DBUserLog.addUserLog(userid, 4, "渠道管理>>打印渠道换货!");
			}
			
			return mapping.findForward("toprint");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}