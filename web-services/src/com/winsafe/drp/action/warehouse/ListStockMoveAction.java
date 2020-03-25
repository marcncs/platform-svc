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
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class ListStockMoveAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		int pagesize = 10; 
		initdata(request);
		AppStockMove asa = new AppStockMove();
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		try{ 
			//查询条件
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "StockMove"};
			String whereSql = EntityManager.getTmpWhereSqlForSql(map, tablename, param);
			//时间条件
			String timeCondition = DbUtil.getTimeConditionForSql(map, tmpMap," sm.MoveDate", param);
			//关键字条件
			String blur = DbUtil.getBlurForSql(map, tmpMap, "sm.KeysContent", param);
			//权限条件
			String Condition = "";
			if(DbUtil.isDealer(users)) {
				Condition += " sm.outwarehouseid in (select wv.warehouse_Id from  Rule_User_Wh wv where  wv.user_Id=?) and ";
				param.put("wv.user_Id", userid);
			} else { 
				Condition += "("+DbUtil.getWhereCondition(users, "outo") + " or "+DbUtil.getWhereCondition(users, "ino")+") and ";
			}
			whereSql = whereSql + timeCondition + blur + Condition; 
			if(!StringUtil.isEmpty((String)map.get("isNoBill"))) {
				if("1".equals(map.get("isNoBill"))) {
					whereSql = whereSql + " (select isNoBill from Take_Ticket where billno = sm.id) = 1 and ";
				} else {
					whereSql = whereSql + " (select isNoBill from Take_Ticket where billno = sm.id) is null and ";
				}
			}
			whereSql = whereSql.replace("isaudit", "sm.isaudit");
			whereSql = DbUtil.getWhereSql(whereSql); 

			List sals = asa.getStockMoveList(request, pagesize, whereSql, param);

			request.setAttribute("als", sals);
			DBUserLog.addUserLog(request, "列表");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
