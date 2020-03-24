package com.winsafe.drp.action.sys;

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

public class ToUpdScannerWarhouseAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		AppWarehouse aw = new AppWarehouse();
		AppScannerWarehouse asw = new AppScannerWarehouse();
		Warehouse wh = new Warehouse();

		try {
			String type = request.getParameter("type");
			String swid = request.getParameter("swid");
			String warhouseid = request.getParameter("wid");
			wh = aw.getWhById(warhouseid);
			request.setAttribute("warhouseid", warhouseid);
			request.setAttribute("organid", wh.getMakeorganid());
			if (type.equals("ADD")) {
				request.setAttribute("type", "ADD");
				return mapping.findForward("toadd");
			} else if (type.equals("EDIT")) {
				ScannerWarehouse sw = asw.getSWBWAS(swid, warhouseid);
				request.setAttribute("sw", sw);
				request.setAttribute("type", "EDIT");
				return mapping.findForward("toupd");
			} else if (type.equals("DELETE")) {
				ScannerWarehouse sw = asw.getSWBWAS(swid, warhouseid);
				asw.delScannerWarehouseBySW(sw.getScannerid(), sw.getWarehouseid());
				request.setAttribute("result", "databases.del.success");
				return mapping.findForward("todel");
			}
			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
