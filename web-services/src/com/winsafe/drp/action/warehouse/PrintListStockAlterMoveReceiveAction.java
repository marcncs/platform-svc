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
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintListStockAlterMoveReceiveAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);

		try {		
			Map<String,Object> param = new HashMap<>(); 
			String Condition =  " sm.receiveorganid=:receiveorganid and sm.isshipment=1 and sm.isblankout=0 ";
			param.put("receiveorganid", users.getMakeorganid());
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "StockAlterMove" };
			String whereSql = EntityManager.getTmpWhereSqlForHql(map, tablename, param);

			String timeCondition = DbUtil.getTimeConditionForHql(map, tmpMap,
					" MoveDate", param);
			String blur = DbUtil.getBlurForHql(map, tmpMap, "KeysContent", param);
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			
			AppStockAlterMove asa = new AppStockAlterMove();
			List sals = asa.getStockAlterMove(whereSql, param);


			request.setAttribute("als", sals);

			DBUserLog.addUserLog(request,"列表");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
