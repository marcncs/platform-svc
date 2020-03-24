package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppQuestions;
import com.winsafe.drp.dao.AppRespond;
import com.winsafe.drp.dao.Questions;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.RequestTool;

/**
 * @author : jerry
 * @version : 2009-8-8 上午11:45:38
 * www.winsafe.cn
 */
public class DelQuestionsAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		try {
			Integer id = RequestTool.getInt(request, "ID");
			AppQuestions appQuestions = new AppQuestions();
			Questions questions = appQuestions.findByID(id);
			
			AppRespond appRespond = new AppRespond();

			appRespond.deleteByQID(id);
			
			
			appQuestions.delete(questions);
			DBUserLog.addUserLog(userid, 0, "我的办公桌>>删除常见问题  ,编号：" + id,questions);
			request.setAttribute("result", "databases.del.success");
			return mapping.findForward("success");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
	
}
