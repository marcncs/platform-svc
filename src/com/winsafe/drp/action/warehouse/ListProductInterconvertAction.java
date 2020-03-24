package com.winsafe.drp.action.warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductInterconvert;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListProductInterconvertAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);
		try {
			String Condition = " (sm.makeid=" + userid + " "
					+ getOrVisitOrgan("sm.makeorganid") + ") ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "ProductInterconvert" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MoveDate");
			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");
			whereSql = whereSql + timeCondition + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppWarehouse aw = new AppWarehouse();
			AppProductInterconvert asa = new AppProductInterconvert();

			List sals = asa.getProductInterconvert(request, pagesize, whereSql);
			List whls = aw.getCanUseWarehouseByOid(users.getMakeorganid());

			request.setAttribute("alw", whls);
			request.setAttribute("als", sals);
			request.setAttribute("OutWarehouseID", request.getParameter("OutWarehouseID"));
			request.setAttribute("IsAudit", map.get("IsAudit"));
			request.setAttribute("IsComplete", map.get("IsComplete"));
			request.setAttribute("InWarehouseID", request.getParameter("InWarehouseID"));
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeDeptID", request.getParameter("MakeDeptID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			DBUserLog.addUserLog(userid, 7,"商品互转>>列表商品互转");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
