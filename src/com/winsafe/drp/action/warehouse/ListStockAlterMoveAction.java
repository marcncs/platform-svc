package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
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
import com.winsafe.drp.dao.StockAlterMoveForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class ListStockAlterMoveAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		int pagesize = 20;
		AppStockAlterMove asa = new AppStockAlterMove();

		try{
			Map<String, Object> param = new LinkedHashMap<String, Object>();
			//条件
			Map map = new HashMap(request.getParameterMap());
			String productname = (String)map.remove("ProductName");
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "StockAlterMove" };
			String whereSql = EntityManager.getTmpWhereSqlForSql(map, tablename, param);
			
			String timeCondition = DbUtil.getTimeConditionForSql(map, tmpMap," MakeDate", param);
			//关键字条件
			String blur = DbUtil.getOrBlurForSql(map, tmpMap, param, "KeysContent");
			//权限条件
			String Condition ="";
			if(DbUtil.isDealer(users)) {
				Condition = " sm.outwarehouseid in (select wv.warehouse_Id from  Rule_User_Wh wv where  wv.user_Id=?)  " +
				" and  (sm.inwarehouseid in (select wv.wid from  Warehouse_Visit wv where wv.userid=?) or sm.receiveorganid in (select oppOrganId from S_Transfer_Relation where  organizationId=?) or sm.inwarehouseid is null) and";
				param.put("wv.user_Id", userid);
				param.put("wv.userid", userid);
				param.put("organizationId", users.getMakeorganid());
			} else {
				Condition = "("+DbUtil.getWhereCondition(users, "outo") + " or "+DbUtil.getWhereCondition(users, "ino")+") and ";
			}
			
//			String Condition = "( sm.outOrganId in (select visitorgan from UserVisit as uv where  uv.userid=" + userid + ") or sm.outOrganId in (select visitorgan from OrganVisit as ov where  ov.userid=" + userid + "))";
			
			whereSql = whereSql + timeCondition + blur + Condition; 
			
			if(!StringUtil.isEmpty(productname)) {
				whereSql = whereSql + " exists (select productname from Stock_Alter_Move_Detail smd where smd.samid = sm.id and smd.productname = ?) and";
				param.put("smd.productname", productname);
			}
			if(!StringUtil.isEmpty((String)map.get("isNoBill"))) {
				if("1".equals(map.get("isNoBill"))) {
					whereSql = whereSql + " (select isNoBill from Take_Ticket where billno = sm.id) = 1 and ";
				} else {
					whereSql = whereSql + " (select isNoBill from Take_Ticket where billno = sm.id) is null and ";
				}
			}
			whereSql = DbUtil.getWhereSql(whereSql); 
			whereSql = whereSql.replace("isaudit", "sm.isaudit");
			List<Map<String,String>> sals = asa.getStockAlterMoveList(request, pagesize, whereSql, param);
			
			ArrayList als = new ArrayList();
			for (int i = 0; i < sals.size(); i++) {
				Map<String,String> o = sals.get(i);
				StockAlterMoveForm saf = new StockAlterMoveForm();
				MapUtil.mapToObject(o, saf);
				als.add(saf);
			}

			request.setAttribute("als", als);
			DBUserLog.addUserLog(request, "列表");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
