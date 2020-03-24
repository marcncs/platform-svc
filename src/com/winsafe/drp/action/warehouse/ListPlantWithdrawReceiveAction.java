package com.winsafe.drp.action.warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

/**
 * @author : jerry
 * @version : 2009-8-26 下午03:45:59 www.winsafe.cn
 */
public class ListPlantWithdrawReceiveAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		int pagesize = 20;
		AppOrganWithdraw appS = new AppOrganWithdraw();
		
		try{
			// 查询条件
			/*String Condition = "ow.id like 'PW%' " +
			" and ow.isblankout=0 " +
			" and ow.inwarehouseid  in (select wv.warehouseId from  RuleUserWh as wv where wv.userId="+userid+") ";
			*/
			//权限条件 
			String Condition =" ow.id like 'PW%' and ow.isblankout=0 and ";
			if(DbUtil.isDealer(users)) {
				Condition += " ow.inwarehouseid in (select wv.warehouse_Id from  Rule_User_Wh wv where  wv.user_Id="+userid+") ";
				
			} else {
				Condition += DbUtil.getWhereCondition(users, "ino");
//				Condition += "("+DbUtil.getWhereCondition(users, "outo") + " or "+DbUtil.getWhereCondition(users, "ino")+") ";
			}
			Map map = new HashMap(request.getParameterMap());
			String id = (String)map.remove("ID");
			if(!StringUtil.isEmpty(id)) {
				Condition = Condition + " and id = '" + id.trim() +"'";
			}
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "OrganWithdraw" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");
			whereSql = whereSql + timeCondition + blur+ Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			List lists = appS.getOrganWithdrawAllList(request, pagesize, whereSql);
			request.setAttribute("list", lists);
			DBUserLog.addUserLog(request, "列表");
			return mapping.findForward("list");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}