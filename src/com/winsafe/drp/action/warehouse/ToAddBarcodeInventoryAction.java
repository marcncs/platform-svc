package com.winsafe.drp.action.warehouse;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Warehouse;

public class ToAddBarcodeInventoryAction extends BaseAction {
	AppWarehouse appw = new AppWarehouse();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	Vector tree = null;
	initdata(request);
		try {
			//页面初始化时，默认机构和仓库
			request.setAttribute("MakeOrganID", users.getMakeorganid());
			request.setAttribute("oname", users.getMakeorganname());
			appw.getEnableWarehouseByvisit(request,users.getUserid(),users.getMakeorganid());
			return mapping.findForward("success");
	} catch (Exception e) {
		e.printStackTrace();
	}
	return new ActionForward(mapping.getInput());
	}

}