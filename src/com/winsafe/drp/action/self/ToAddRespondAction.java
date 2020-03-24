package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppQuestions;
import com.winsafe.drp.dao.Questions;

/**
 * @author : jerry
 * @version : 2009-8-8 下午03:34:14 www.winsafe.cn
 */
public class ToAddRespondAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			Integer qid = Integer.valueOf(request.getSession().getAttribute(
					"QID").toString());

			AppQuestions appQuestions = new AppQuestions();
			Questions questions = appQuestions.findByID(qid);
			request.setAttribute("title", questions.getTitle());
			return mapping.findForward("toadd");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
