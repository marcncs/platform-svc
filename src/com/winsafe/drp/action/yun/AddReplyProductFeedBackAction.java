package com.winsafe.drp.action.yun;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductFeedback;
import com.winsafe.drp.dao.ProductFeedback;
import com.winsafe.sap.metadata.YesOrNo;

public class AddReplyProductFeedBackAction extends BaseAction {

	private AppProductFeedback apf = new AppProductFeedback();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		String ppid = request.getParameter("PPID");
		String reply = request.getParameter("reply");
		
		try { 
			ProductFeedback productFeedback = apf.getProductFeedbackById(Integer.valueOf(ppid));
			productFeedback.setReply(reply);
			productFeedback.setReplied(YesOrNo.YES.getValue());
			productFeedback.setReplyTime(new Date());
			apf.updProductFeedBack(productFeedback);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("result", "databases.upd.success");
		return mapping.findForward("updResult");
	}
}
