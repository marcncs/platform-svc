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
import com.winsafe.drp.dao.AppSupplySaleApply;
import com.winsafe.drp.dao.AppSupplySaleMove;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

/**
 * @author : jerry
 * @version : 2009-8-26 下午03:45:59 www.winsafe.cn
 */
public class ListSupplySaleMoveAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);
		try {

			String Condition = "ssa.makeorganid = '" + users.getMakeorganid()
					+ "'";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "SupplySaleMove" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MoveDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "ID", "KeysContent");
			whereSql = whereSql + blur + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppSupplySaleMove appS = new AppSupplySaleMove();
			List lists = appS.getSupplySaleMoveAll(request, pagesize, whereSql);
			AppSupplySaleApply assa = new AppSupplySaleApply();
			int count = assa.getIsTrans(users.getMakeorganid());
			request.setAttribute("wi", count);
			request.setAttribute("list", lists);
			request.setAttribute("IsAudit", map.get("IsAudit"));
			request
					.setAttribute("BeginDate", request
							.getParameter("BeginDate"));
			request.setAttribute("EndDate", request.getParameter("EndDate"));
			request.setAttribute("IsRatify", map.get("IsRatify"));
			request.setAttribute("MakeOrganID", request
					.getParameter("MakeOrganID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("MakeDeptID", request
					.getParameter("MakeDeptID"));
			request
					.setAttribute("InOrganID", request
							.getParameter("InOrganID"));
			request.setAttribute("SupplyOrganID", request
					.getParameter("SupplyOrganID"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			DBUserLog.addUserLog(userid, 4, "渠道管理>>列表渠道代销!");
			return mapping.findForward("list");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
