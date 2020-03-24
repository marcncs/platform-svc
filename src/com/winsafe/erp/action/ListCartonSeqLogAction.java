package com.winsafe.erp.action;

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
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.erp.dao.AppCartonSeq;
import com.winsafe.hbm.util.DbUtil;

public class ListCartonSeqLogAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ListCartonSeqLogAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int pagesize = 10;
		initdata(request);
		AppCartonSeq appCartonSeq = new AppCartonSeq();
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String Condition = DbUtil.getWhereCondition(users, "o");
		
		String[] tablename = { "CartonSeqLog" };
		// 查询条件
		String whereSql = EntityManager.getTmpWhereSqlAlias(map, tablename, new Object[]{"csl"});
		// 关键字查询条件
		String blur = DbUtil.getOrBlur(map, tmpMap, "o.organname", "p.mcode", "p.productname", "csl.range","p.specmode");
		whereSql = whereSql + blur + Condition;
		whereSql = DbUtil.getWhereSql(whereSql);

		List<Map<String,String>> aqcs = appCartonSeq.getCartonSeqLog(request, pagesize, whereSql);
		
		request.setAttribute("aqcs", aqcs);
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}

}
