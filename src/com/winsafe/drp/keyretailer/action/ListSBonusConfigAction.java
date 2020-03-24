package com.winsafe.drp.keyretailer.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.dao.AppSBonusConfig;
import com.winsafe.drp.keyretailer.pojo.SBonusConfig;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListSBonusConfigAction extends BaseAction{
//	private static Logger logger = Logger.getLogger(ListSBonusSettingAction.class);
	
	private AppSBonusConfig appSBonusConfig = new AppSBonusConfig();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		initdata(request);
		
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String[] tablename = { "SBonusConfig" };
		String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
		whereSql = DbUtil.getWhereSql(whereSql);
		List<SBonusConfig> sBonusConfigs = appSBonusConfig.getSBonusConfig(request, pagesize, whereSql);
		
		request.setAttribute("sBonusConfigs", sBonusConfigs);
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}
}
