package com.winsafe.drp.action.sys;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import common.Logger;

public class ListWarehouseAction extends BaseAction {
	
	private static  Logger logger = Logger.getLogger(ListWarehouseAction.class);
	private AppWarehouse awr = new AppWarehouse();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		int pagesize = 10;
		String oid = request.getParameter("OID");
		Warehouse wh = new Warehouse();
		Olinkman ol = new Olinkman();
		AppOlinkMan aol = new AppOlinkMan();
		
		try {
			String condition = " w.makeorganid='" + oid + "' ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Warehouse" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "ID", "WarehouseName","NCcode");
			whereSql = whereSql + blur + condition;
			whereSql = DbUtil.getWhereSql(whereSql);
			List<Warehouse> wrls = awr.getWarehouse(request, pagesize, whereSql);

			request.setAttribute("wls", wrls);
			request.setAttribute("OID", oid);
			DBUserLog.addUserLog(userid, "系统管理", "机构设置,机构编号:" + oid + ">>仓库设置>>列表仓库");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
