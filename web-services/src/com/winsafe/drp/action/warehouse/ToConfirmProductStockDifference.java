package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AmountInventory;
import com.winsafe.drp.dao.AppAmountInventory;
import com.winsafe.drp.dao.AppBarcodeInventory;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.BarcodeInventory;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Warehouse;

/**
 * 处理复核数据盘点
 * 
 * @author mengnan.xie
 * 
 */
public class ToConfirmProductStockDifference extends BaseAction {
	private Logger logger = Logger
			.getLogger(ToConfirmProductStockDifference.class);
	private AppAmountInventory api = new AppAmountInventory();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);

		try {
			AppOrgan appo = new AppOrgan();
			AppWarehouse appw = new AppWarehouse();
			String id = request.getParameter("ID");

			AmountInventory ai = api.getAmountInventoryByID(id);
			request.setAttribute("ai", ai);
			Organ organ = appo.getOrganByID(ai.getOrganid());
			request.setAttribute("oname", organ.getOrganname());
			Warehouse warehouse = appw.getWarehouseByID(ai.getWarehouseid());
			request.setAttribute("wname", warehouse.getWarehousename());

		} catch (Exception e) {
			logger.error("ConfirmProductStockDifference  error:", e);
			throw e;
		}
		return mapping.findForward("audit");
	}
}