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
import com.winsafe.erp.dao.AppBillImportConfig;
import com.winsafe.hbm.util.DbUtil;

public class ListBillImportConfigAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ListBillImportConfigAction.class);

	private AppBillImportConfig appBillImportConfig = new AppBillImportConfig();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int pagesize = 10;
		initdata(request);

		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String[] tablename = { "BillImportConfig" };
		// 查询条件
		String whereSql = EntityManager.getTmpWhereSql(map, tablename);
		// 关键字查询条件
		String blur = DbUtil.getOrBlur(map, tmpMap, "id", "templateNo", "fieldName", "columnName", "defaultValue");
		whereSql = whereSql + blur;
		whereSql = DbUtil.getWhereSql(whereSql);

		List configs = appBillImportConfig.getBillImportConfig(request, pagesize, whereSql);
		request.setAttribute("configs", configs);
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}

}
