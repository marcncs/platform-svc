package com.winsafe.drp.action.warehouse;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class ListStockMoveReceiveAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ListStockMoveReceiveAction.class);
	private AppStockMove asm = new AppStockMove();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20; 
		super.initdata(request);
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		try {
			
			
			// 查询条件
			Map map = new HashMap(request.getParameterMap());
			String id = (String)map.remove("ID");
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "StockMove" };
			String whereSql = EntityManager.getTmpWhereSqlForSql(map, tablename, param);
			// 时间条件
			String timeCondition = DbUtil.getTimeConditionForSql(map, tmpMap,
					" MoveDate", param);
			// 关键字条件
			String blur = DbUtil.getOrBlur2ForSql(map, tmpMap, param, "sm.KeysContent","sm.id");
			//权限条件
			String Condition =" sm.isshipment=1 and sm.isblankout=0 ";
			if(DbUtil.isDealer(users)) {
				Condition += " and sm.inwarehouseid in (select wv.warehouse_Id from  Rule_User_Wh wv where  wv.user_Id=?)";
				param.put("wv.user_Id", userid);
			} else {
				Condition += " and ("+DbUtil.getWhereCondition(users, "outo") + " or "+DbUtil.getWhereCondition(users, "ino")+") and ";
			}
			if(!StringUtil.isEmpty(id)) {
				Condition = Condition + " and id = '" + id.trim() +"'";
				param.put("id.trim()", id.trim());
			}
			whereSql = whereSql + timeCondition +blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			List sals = asm.getStockMoveList(request, pagesize, whereSql, param);
			
			request.setAttribute("als", sals);
			DBUserLog.addUserLog(request, "列表");
			return mapping.findForward("list");
		} catch (Exception e) {
			logger.error("", e);
		}
		return new ActionForward(mapping.getInput());
	}
}
