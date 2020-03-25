package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.util.DBUserLog;

public class ToAddTransferLogAction extends BaseAction {
	Logger logger = Logger.getLogger(ToAddTransferLogAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 初始化
		initdata(request);
		try {
			request.setAttribute("organid", users.getMakeorganid());
			request.setAttribute("orgname", users.getMakeorganname());
			AppWarehouse aw = new AppWarehouse();
			Warehouse w = aw.getRuleUserWarehouse(users.getMakeorganid(), users.getUserid());
			if(w != null) {
				request.setAttribute("warehouseid", w.getId());
				request.setAttribute("owname", w.getWarehousename());
			}
			DBUserLog.addUserLog(request, "[列表]");
			return mapping.findForward("success");
		} catch (Exception e) {
			logger.error("", e);
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
