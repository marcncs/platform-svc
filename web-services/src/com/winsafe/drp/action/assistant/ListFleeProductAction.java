package com.winsafe.drp.action.assistant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFleeProduct;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListFleeProductAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		int pagesize = 20;
		super.initdata(request);

		try {

			// String Condition =
			// " ( makeid="+users.getMakedeptid()+this.getOrVisitOrgan("makeorganid")+") ";
			String Condition = " ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "FleeProduct" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap, "MakeDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "QueryID", "productid", "specmode",
					"cartoncode");

			whereSql = whereSql + timeCondition + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppFleeProduct asb = new AppFleeProduct();
			List pils = asb.getFleeProduct(request, pagesize, whereSql);

			request.setAttribute("alsb", pils);

			DBUserLog.addUserLog(request, "[列表]");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
