package com.winsafe.drp.action.report;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.util.DBUserLog;

public class UpdateAlarmByRemarkAction extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			String id = request.getParameter("id");
			String remark = request.getParameter("remark");
			AppWarehouse aw = new AppWarehouse();
			Warehouse warehouse = aw.getWarehouseByID(id);
			if(null != warehouse){
				warehouse.setRemark(remark);
				aw.updWarehouse(warehouse);
				request.setAttribute("result", "databases.add.success");
			}else{
				request.setAttribute("result", "databases.add.fail");
			}
			return mapping.findForward("updResult");
	}
}
