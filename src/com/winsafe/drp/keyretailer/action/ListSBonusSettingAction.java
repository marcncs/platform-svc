package com.winsafe.drp.keyretailer.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.dao.AppSBonusSetting;
import com.winsafe.drp.keyretailer.pojo.SBonusSetting;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListSBonusSettingAction extends BaseAction{
//	private static Logger logger = Logger.getLogger(ListSBonusSettingAction.class);
	
	private AppSBonusSetting appSBonusSetting = new AppSBonusSetting();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		initdata(request);
		
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String[] tablename = { "SBonusSetting" };
		String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
		String timeCondition = DbUtil.getTimeCondition4(map, tmpMap," modifiedDate");
		String blur = DbUtil.getOrBlur(map, tmpMap, "year","month","productName","spec");
		whereSql = whereSql + timeCondition + blur;
		whereSql = DbUtil.getWhereSql(whereSql);
		List<SBonusSetting> sBonusSettings = appSBonusSetting.getSBonusSetting(request, pagesize, whereSql);
		AppBaseResource appBr = new AppBaseResource();
		Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
		for(SBonusSetting sbs : sBonusSettings) {
			sbs.setCountUnitName(countUnitMap.get(sbs.getCountUnit()));
		}
		
		request.setAttribute("sBonusSettings", sBonusSettings);
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}
		
}
