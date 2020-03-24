package com.winsafe.drp.action.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppScannerWarehouse;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ToListScannerWarhouseAction extends BaseAction {

	private AppWarehouse aw = new AppWarehouse();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.initdata(request);
		String warhouseid = request.getParameter("ID");
		String warehouse = request.getParameter("outwarehouseid");
		String scannerid = request.getParameter("scanneridSearch");
		String MakeOrganID = request.getParameter("organid");
		String whereSql = " where 1=1 ";
		int pageSize = 10;
		try {
			String Condition = " and warehouseid in (select ruw.warehouseId from RuleUserWh ruw where ruw.userId = "
				+ userid + " and activeFlag = 1)";
			String strCondition = "";
			if (MakeOrganID != null && !"".equals(MakeOrganID)) {
				String ss = getWarehouseId(aw.getWarehouseListByOID(MakeOrganID));
				strCondition += " and f.warehouseid in(" + ss + ") ";
			}
			if (warehouse != null && !"".equals(warehouse)) {
				strCondition += " and f.warehouseid = '" + warehouse + "' ";
			}
			if (scannerid != null && !"".equals(scannerid)) {
				strCondition += " and f.scannerid = '" + scannerid + "' ";
			}
			whereSql = whereSql + Condition + strCondition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppScannerWarehouse asw = new AppScannerWarehouse();
			List scanwarhouses = asw.selectWareHouse(request, pageSize, whereSql);

			request.setAttribute("organid", MakeOrganID);
			request.setAttribute("warhouseid", warhouseid);
			request.setAttribute("scanwarhouses", scanwarhouses);
			request.setAttribute("warehouseidSearch", warehouse);
			DBUserLog.addUserLog(request,"列表");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

	private String getWarehouseId(List wlist) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < wlist.size(); i++) {
			Warehouse w = (Warehouse) wlist.get(i);
			if (i == 0) {
				sb.append("'").append(w.getId()).append("'");
			} else {
				sb.append(",'").append(w.getId()).append("'");
			}
		}
		return sb.toString();
	}
}