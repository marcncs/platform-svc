package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppScannerWarehouse;
import com.winsafe.drp.dao.ScannerWarehouse;
import com.winsafe.drp.util.DBUserLog;

public class ToDelScannerWarhouseAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		AppScannerWarehouse asw = new AppScannerWarehouse();
		super.initdata(request);
		try {
			String warhouseid = request.getParameter("wid");
			String type = request.getParameter("type");
			String swid = request.getParameter("swid");

			request.setAttribute("warhouseid", warhouseid);
			if (type.equals("ADD")) {
				request.setAttribute("type", "ADD");
				return mapping.findForward("toadd");
			} else if (type.equals("EDIT")) {
				ScannerWarehouse sw = asw.getSWBWAS(swid, warhouseid);
				request.setAttribute("sw", sw);
				request.setAttribute("type", "EDIT");
				return mapping.findForward("toadd");
			} else if (type.equals("DELETE")) {
				ScannerWarehouse sw = asw.getSWBWAS(swid, warhouseid);
				asw.delScannerWarehouseBySW(sw.getScannerid(), sw.getWarehouseid());
				request.setAttribute("result", "databases.del.success");
				DBUserLog.addUserLog(request, "仓库编号：" + warhouseid + "采集器编号" + swid);
				return mapping.findForward("todel");
			}
			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
