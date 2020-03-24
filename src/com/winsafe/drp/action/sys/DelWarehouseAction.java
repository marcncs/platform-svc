package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;

public class DelWarehouseAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		try {
			
			
			AppProductStockpileAll appps = new AppProductStockpileAll();
			
			
			if ( appps.geProductStockpileAllByWid(id) >0 ){
				request.setAttribute("result", "该仓库已被使用，不能删除！");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			WarehouseService us = new WarehouseService();
			Warehouse u = us.getWarehouseByID(id);
			
	
			us.delWarehouse(id);

			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(request, "删除编号：" + id, u);

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("success");
	}
}
