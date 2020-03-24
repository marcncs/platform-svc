package com.winsafe.drp.action.yun;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductFeedback;
import com.winsafe.drp.dao.ProductFeedback;
import com.winsafe.drp.util.WfLogger;

public class ProductFeedbackDetailAction extends BaseAction {

	private AppProductFeedback apf = new AppProductFeedback();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			Integer id = Integer.valueOf(request.getParameter("ID"));
			ProductFeedback feedback = apf.getProductFeedbackById(id);
			
			// 返回页面值
			request.setAttribute("picUrl", feedback.getPicUrl());

			return mapping.findForward("list");
		} catch (Exception e) {
			WfLogger.error("", e);
		}
		return mapping.findForward("list");
	}
}
