package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppQuestions;
import com.winsafe.drp.dao.Questions;
import com.winsafe.hbm.util.RequestTool;

/**
 * @author : jerry
 * @version : 2009-8-8 上午11:58:02
 * www.winsafe.cn
 */
public class ToUpdQuestionsAction extends Action {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			Integer id = RequestTool.getInt(request, "ID");
			AppQuestions appQuestions = new AppQuestions();
			Questions questions =appQuestions.findByID(id);
			request.setAttribute("questions", questions);
			return mapping.findForward("toupd");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return new ActionForward(mapping.getInput());
	}

}
