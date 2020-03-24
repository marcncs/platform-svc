package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganScan;
import com.winsafe.drp.dao.OrganScan;
import com.winsafe.drp.dao.ScanConf;

public class ToUpdOrganScanAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String id=request.getParameter("ID");
		AppOrganScan ar=new AppOrganScan();
		OrganScan role=ar.getOrganScanByID(Integer.valueOf(id));
		ScanConf sc = ar.getScanConfByID(role.getScb());
		request.setAttribute("os",role);
		request.setAttribute("sc",sc);
		return mapping.findForward("toupd");	
	}
}
