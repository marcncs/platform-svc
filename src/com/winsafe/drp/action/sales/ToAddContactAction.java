package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.hbm.util.RequestTool;

public class ToAddContactAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {


		Integer objsort = RequestTool.getInt(request, "objsort");
		try {
			AppBaseResource abr = new AppBaseResource();
			List<BaseResource> uls = abr.getBaseResource("SelectContact");
			request.setAttribute("als", uls);
			request.setAttribute("objsort", objsort);
			return mapping.findForward("toadd");

		} catch (Exception e) {
			e.printStackTrace();
		} 
		return mapping.getInputForward();
	}

}
