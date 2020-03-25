package com.winsafe.drp.action.warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.action.common.ListBaseIdcodeDetailAction;
import com.winsafe.drp.dao.AppBarcodeInventory;
import com.winsafe.drp.dao.AppBarcodeInventoryIdcode;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.BarcodeInventory;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class ListBarcodeDetailAction extends ListBaseIdcodeDetailAction {

	protected List getIdcodeList(HttpServletRequest request, String prid, String billid)
			throws Exception {
		String batch = request.getParameter("batch");
		if (batch == null) {
			batch = (String) request.getSession().getAttribute("batch");
		}
		request.getSession().setAttribute("batch", batch);
		int pagesize = 20;
		AppBarcodeInventoryIdcode abii = new AppBarcodeInventoryIdcode();
		String Condition = " osid='" + billid + "' and productid='" + prid + "' and isidcode=1 and batch_='"+batch+"' ";
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String[] tablename = { "BarcodeInventoryIdcode" };
		String whereSql = EntityManager.getTmpWhereSql(map, tablename);
		// String timeCondition = DbUtil.getTimeCondition(map,
		// tmpMap,"CreateDate");
		String blur = DbUtil.getOrBlur(map, tmpMap, "IDCode", "Batch");
		whereSql = whereSql + blur + Condition;
		whereSql = DbUtil.getWhereSql(whereSql);
		AppBarcodeInventory abi = new AppBarcodeInventory();
		BarcodeInventory bi = abi.getBarcodeInventoryByID(billid);
		Integer isAudit = bi.getIsaudit();
		request.setAttribute("isAudit", isAudit);
		AppTakeTicket appTakeTicket = new AppTakeTicket();
		TakeTicket tt = appTakeTicket.getTakeTicketById(billid);
		return abii.searchBarcodeInventoryIdcode(request, pagesize, whereSql);
	}
}