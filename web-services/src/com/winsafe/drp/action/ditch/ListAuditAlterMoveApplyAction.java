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
import com.winsafe.drp.dao.AppAlterMoveApply;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

/**
 * @author : jerry
 * @version : 2009-8-24 上午11:16:31 www.winsafe.cn
 */
public class ListAuditAlterMoveApplyAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);
		try {

			String Condition = "ama.outorganid = '" + users.getMakeorganid()
					+ "' and ama.isaudit =1 and ama.isblankout = 0 ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "AlterMoveApply" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MoveDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "ID", "KeysContent");
			whereSql = whereSql + blur + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppAlterMoveApply appA = new AppAlterMoveApply();
			List lista = appA.getAlterMoveApply(request, pagesize, whereSql);

			request.setAttribute("list", lista);
			request.setAttribute("ISAUDIT", "yes");

			DBUserLog.addUserLog(userid, 4, "渠道管理>>列表订购申请!");
			return mapping.findForward("list");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}

}
