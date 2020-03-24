package com.winsafe.drp.action.assistant;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.ws.Fwnumber;

public class ListFWNReportAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {

			FindFWNReport findFWNReport = new FindFWNReport();
			String whereSql  = findFWNReport.getWhereSql(request);
			List<Fwnumber> list = findFWNReport.getFWNReport(whereSql);
			request.setAttribute("list", list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("list");

	}
	
	

	
}
