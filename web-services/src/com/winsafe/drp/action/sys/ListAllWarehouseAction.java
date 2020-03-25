package com.winsafe.drp.action.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.JProperties;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.StringUtil;

import common.Logger;

public class ListAllWarehouseAction extends BaseAction {
	
	private static  Logger logger = Logger.getLogger(ListAllWarehouseAction.class);
	private AppWarehouse awr = new AppWarehouse();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		int pagesize = 10;
		String Condition = "";
		//仓库管辖权限条件
		if(DbUtil.isDealer(users)) {
			Condition = " w.id in (select wv.warehouse_Id from  Rule_User_Wh wv where  wv.user_Id="+userid+") "; 
		} else {
			Condition += DbUtil.getWhereCondition(users, "o");
		}
		
		try {
			// 是否只让PD维护仓库联系人
			Properties sysPro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
			if ("1".equals(sysPro.getProperty("enableWarehouseLinkManOnlyForPD"))) {
				Condition = Condition + "and w.makeorganid in (select id from Organ where organType = 2 and organModel = 1)";
			}
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Warehouse" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "w.ID", "w.WarehouseName","w.NCcode");
			whereSql = whereSql + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);
			List<Map<String,String>> wrls = awr.getWarehouseList(request, pagesize, whereSql);
			for(Map<String,String> resultMap : wrls) {
				if(!StringUtil.isEmpty(resultMap.get("warehousetel"))) {
					resultMap.put("warehousetel", Encrypt.getSecret(resultMap.get("warehousetel"), 2)); 
				}
			}
			request.setAttribute("wls", wrls);
			DBUserLog.addUserLog(request, "列表");
			return mapping.findForward("list");
		} catch (Exception e) {
			logger.error("查看仓库联系人设置列表发生异常：",e);
		}
		return new ActionForward(mapping.getInput());
	}
}
