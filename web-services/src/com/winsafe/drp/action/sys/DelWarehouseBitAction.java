package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppStockWasteBook;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.WarehouseBit;
import com.winsafe.drp.util.DBUserLog;

public class DelWarehouseBitAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			int id = Integer.valueOf(request.getParameter("ID"));
			AppStockWasteBook appswb = new AppStockWasteBook();
			AppWarehouse app = new AppWarehouse();
			WarehouseBit wb = app.getWarehouseBitByid(id);
			int count = appswb.getStockWasteBookByWarehousebit(wb.getWid(), wb.getWbid());
			if ( count > 0 ){
				request.setAttribute("result", wb.getWbid()+"仓位已经在系统中使用,不能删除!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			app.delWarehouseBitByid(id);
 
			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11, "仓库设置>>删除仓位",wb);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("");
	}
}
