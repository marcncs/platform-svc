package com.winsafe.drp.action.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.Warehouse;

public class WarehouseDetailAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		AppWarehouse aw = new AppWarehouse();

		try {
			Warehouse w = aw.getWarehouseByID(id);

			AppWarehouseVisit asla = new AppWarehouseVisit();
			List slrv = asla.getWarehouseVisitWID2(id);

			request.setAttribute("rvls", slrv);
			request.setAttribute("wf", w);

			return mapping.findForward("detail");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// HibernateUtil.closeSession();
		}

		return mapping.getInputForward();
	}

}
