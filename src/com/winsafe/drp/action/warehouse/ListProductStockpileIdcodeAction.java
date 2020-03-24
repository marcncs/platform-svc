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
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class ListProductStockpileIdcodeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.initdata(request);
		try {
			int pagesize = 10;
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Idcode" };

			String Batch = (String) map.remove("Batch");
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String Condition = " (isuse=1 or quantity>0 ) and batch='" + Batch + "'";
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap, " MakeDate");
			String blur = DbUtil
					.getOrBlur(map, tmpMap, "ProduceDate", "StartNo", "EndNo", "IDCode");

			whereSql = whereSql + timeCondition + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppIdcode ap = new AppIdcode();
			List idlist = ap.searchIdcode(request, pagesize, whereSql);

			request.setAttribute("list", idlist);

			request.setAttribute("WarehouseID", request.getParameter("WarehouseID"));
			request.setAttribute("ProductID", request.getParameter("ProductID"));
			request.setAttribute("Batch", request.getParameter("Batch"));

			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
