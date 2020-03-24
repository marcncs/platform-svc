package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;

public class ToImportOtherOutcomeProductAction extends BaseAction {
	private Logger logger = Logger.getLogger(ToImportOtherOutcomeProductAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.initdata(request);
		try {
			String incomeSort = request.getParameter("incomeSort");
			if("0".equals(incomeSort)) {
				request.setAttribute("title", "其他出库导入");
			} else if("5".equals(incomeSort)){
				request.setAttribute("title", "红冲出库导入");
			}
			return mapping.findForward("success");
		} catch (Exception e) {
			logger.error("",e);
		}
		return new ActionForward(mapping.getInput());
	}
}