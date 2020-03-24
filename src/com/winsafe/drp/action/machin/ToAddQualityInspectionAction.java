package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action; 
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward; 
import org.apache.struts.action.ActionMapping;

/**
 * @author : jerry
 * @version : 2009-9-4 下午03:09:40
 * www.winsafe.cn
 */
public class ToAddQualityInspectionAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("upload");
	}
}
