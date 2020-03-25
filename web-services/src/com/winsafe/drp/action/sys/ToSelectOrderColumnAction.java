package com.winsafe.drp.action.sys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;

public class ToSelectOrderColumnAction extends BaseAction {
@Override
public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	//String orderMap = request.getParameter("param");
	Map orderMap = (Map)request.getSession().getAttribute("orderColumnMap");
	request.setAttribute("orderMap", orderMap);
	 return mapping.findForward("toselect");
}
}
