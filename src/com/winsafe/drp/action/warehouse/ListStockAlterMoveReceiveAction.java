package com.winsafe.drp.action.warehouse;

import java.util.HashMap;
import java.util.LinkedHashMap; 
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
import com.winsafe.hbm.util.StringUtil;

public class ListStockAlterMoveReceiveAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		int pagesize = 20;
		initdata(request);
		AppStockAlterMove asa = new AppStockAlterMove();

		try {
			Map<String, Object> param = new LinkedHashMap<String, Object>();
			
			
			Map map = new HashMap(request.getParameterMap());
			String id = (String)map.remove("ID");
			String mcode = (String)map.remove("mcode");
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "StockAlterMove" };
//			String[] aliasTablename = { "sm" };
//			String whereSql = EntityManager.getTmpWhereSqlAlias(map, tablename,aliasTablename);
			String whereSql = EntityManager.getTmpWhereSqlForSql(tmpMap, tablename, param);

			String timeCondition = DbUtil.getTimeConditionForSql(map, tmpMap,"MoveDate", param);
			String blur = DbUtil.getBlurForSql(map, tmpMap, "KeysContent", param);
			String Condition = "";
			if(!StringUtil.isEmpty(id)) {
				Condition = Condition + " and id = ?";
				param.put("id.trim()", id.trim());
			}
			whereSql = whereSql + timeCondition + blur + Condition; 
			if(!StringUtil.isEmpty(mcode)) {
//				whereSql = whereSql + " and exists (select smd.samid from StockAlterMoveDetail smd where sm.id = smd.samid and smd.nccode = '" + mcode.trim() + "')";
				whereSql = whereSql + " exists (select smd.samid from Stock_Alter_Move_Detail smd where sm.id = smd.samid and smd.nccode = ?)";
				param.put("mcode.trim()", mcode.trim());
			}
			whereSql = DbUtil.getWhereSql(whereSql); 	
			whereSql = whereSql.replaceFirst("where", "and");
			
			List sals = asa.getStockAlterMoveBySql(request, pagesize, whereSql,users, param);
			
			request.setAttribute("als", sals);

			DBUserLog.addUserLog(request, "列表");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
