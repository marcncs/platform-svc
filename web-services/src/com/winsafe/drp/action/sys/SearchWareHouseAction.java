package com.winsafe.drp.action.sys;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppScannerWarehouse;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.ScannerWarehouse;
import com.winsafe.drp.dao.Warehouse;

public class SearchWareHouseAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		Vector tree = null;
		int pagesize = 10;
		AppWarehouse awh = new AppWarehouse();
		Warehouse wh = new Warehouse();
	 	super.initdata(request);
		try {
			String sql = " where 1 = 1 ";
//			String id = request.getParameter("idSearch");
			ScannerWarehouse sw  = new ScannerWarehouse();
			AppScannerWarehouse asw = new AppScannerWarehouse();
			//获取采集器信息
//			List list = asw.selectWareHouse(sql);
			List lst = awh.selectWareHouse(sql);
			request.setAttribute("WareHouse",lst);
			return mapping.findForward("success");
	} catch (Exception e) {
		e.printStackTrace();
	}
	return new ActionForward(mapping.getInput());
	}
}
