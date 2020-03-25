package com.winsafe.drp.action.ditch;

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
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil; 
import com.winsafe.hbm.util.StringUtil;

/**
 * @author : jerry
 */
public class ListOrganWithdrawAction extends BaseAction {
	
	private Logger logger = Logger.getLogger(ListOrganWithdrawAction.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		int pagesize = 20;
		initdata(request);
		AppOrganWithdraw appS = new AppOrganWithdraw();
		
		try {
			Map<String, Object> param = new LinkedHashMap<String, Object>();
			//查询条件
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "OrganWithdraw" }; 
			String whereSql = EntityManager.getTmpWhereSqlForSql(map, tablename, param);
			String timeCondition = DbUtil.getTimeConditionForSql(map, tmpMap,
					" MakeDate", param);
			String blur = DbUtil.getOrBlurForSql(map, tmpMap, param, "ow.ID", "ow.KeysContent");
			//权限条件
			String Condition =" ow.id like 'OW%' and ";
			if(DbUtil.isDealer(users)) {
				Condition += " ow.inwarehouseid in (select wv.warehouse_Id from  Rule_User_Wh wv where  wv.user_Id=?) " +
					"  and ow.warehouseid in (select wv.wid from  Warehouse_Visit wv where wv.userid=?) and ";
				param.put("wv.user_Id", userid);
				param.put("wv.userid", userid);
			} else { 
				Condition += "("+DbUtil.getWhereCondition(users, "outo") + " or "+DbUtil.getWhereCondition(users, "ino")+") and ";
			}
			whereSql = whereSql + timeCondition + blur + Condition;
			if(!StringUtil.isEmpty((String)map.get("isNoBill"))) {
				if("1".equals(map.get("isNoBill"))) {
					whereSql = whereSql + " (select isNoBill from Take_Ticket where billno = ow.id) = 1 and ";
				} else {
					whereSql = whereSql + " (select isNoBill from Take_Ticket where billno = ow.id) is null and ";
				}
			}
			whereSql = DbUtil.getWhereSql(whereSql);
			whereSql = whereSql.replace("isaudit", "ow.isaudit");
			List lists = appS.getOrganWithdrawAllList(request, pagesize, whereSql, param);
			// 返回页面的值
			request.setAttribute("list", lists);
			
			//记录日志
			DBUserLog.addUserLog(request, "列表");
			return mapping.findForward("list");
		} catch (Exception ex) {
			logger.error("", ex);
		}
		return super.execute(mapping, form, request, response);
	}
}