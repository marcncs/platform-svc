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
import com.winsafe.drp.keyretailer.dao.AppSBonusDetail;
import com.winsafe.drp.keyretailer.pojo.SBonusDetail;
import com.winsafe.drp.keyretailer.service.SBonusService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListSBonusDetailAction extends BaseAction{
//	private static Logger logger = Logger.getLogger(ListSBonusDetailAction.class);
	
	private AppSBonusDetail appSBonusDetail = new AppSBonusDetail();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		initdata(request);
		
		String condition = SBonusService.getWhereCondition(users);
		
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String[] tablename = { "SBonusDetail" };
		String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
		String timeCondition = DbUtil.getTimeCondition4(map, tmpMap," sbd.makeDate");
		String blur = DbUtil.getOrBlur(map, tmpMap, "sbd.productName","sbd.spec","sbd.remark");
		whereSql = whereSql + timeCondition + blur + condition;
		whereSql = DbUtil.getWhereSql(whereSql);
		List<Map<String,String>> sBonusDetails = appSBonusDetail.getSBonusDetail(request, pagesize, whereSql);
		
		request.setAttribute("sBonusDetails", sBonusDetails);
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}
		
}
