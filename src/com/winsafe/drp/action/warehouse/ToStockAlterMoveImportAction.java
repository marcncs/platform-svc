package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.erp.dao.AppBillImportConfig;

public class ToStockAlterMoveImportAction extends BaseAction {
	
	private AppOrgan appOrgan = new AppOrgan();
	private AppWarehouse appWarehouse = new AppWarehouse();
	private AppBillImportConfig abic = new AppBillImportConfig();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		Organ organ = appOrgan.getOrganByID_Isrepeal(users.getMakeorganid());
		Warehouse warehouse = appWarehouse.getWarehouseByOID(users.getMakeorganid());
		List<String> tempNo = abic.getTemplateNoByOrganId(users.getMakeorganid());
		
		request.setAttribute("organid", users.getMakeorganid());
		if(organ != null) {
			request.setAttribute("oname", organ.getOrganname());
		}
		if(warehouse != null) {
			request.setAttribute("outwarehouseid", warehouse.getId());
			request.setAttribute("wname", warehouse.getWarehousename());
		}
		if(tempNo != null && tempNo.size() == 1) {
			request.setAttribute("templateNo", tempNo.get(0));
		}
		request.setAttribute("titleRowNo", 1);
		request.setAttribute("dataRowNo", 3);
		return mapping.findForward("success");

	}
}
