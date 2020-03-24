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
import com.winsafe.erp.dao.AppApplyQrCode;
import com.winsafe.hbm.util.DbUtil;

public class ListApplyQrCodeAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ListApplyQrCodeAction.class);
	private AppApplyQrCode appApplyQrCode = new AppApplyQrCode();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int pagesize = 10;
		initdata(request);
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String Condition = DbUtil.getWhereCondition(users, "o");
		
		String[] tablename = { "ApplyQrCode" };
		// 查询条件
		String whereSql = EntityManager.getTmpWhereSqlAlias(map, tablename, new Object[]{"aqc"});
		// 关键字查询条件
		String blur = DbUtil.getOrBlur(map, tmpMap, "aqc.id", "aqc.productId");
		whereSql = whereSql + blur + Condition;
		whereSql = DbUtil.getWhereSql(whereSql);

		List<Map<String,String>> aqcs = appApplyQrCode.getApplyQrCodeBySql(request, pagesize, whereSql);
		//计算要生成的总箱数
		for(Map<String,String> aqc : aqcs) {
			Integer quantity = Integer.parseInt(aqc.get("quantity"));
			Integer redundance = Integer.parseInt(aqc.get("redundance"));
			Integer totalQty = quantity + quantity*redundance/100;
			aqc.put("totalqty", totalQty.toString());
		}
		request.setAttribute("aqcs", aqcs);
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}

}
