package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppQuestions;
import com.winsafe.drp.dao.Questions;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.RequestTool;

/**
 * @author : jerry
 * @version : 2009-8-8 上午11:43:08 www.winsafe.cn
 */
public class UpdQuestionsAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		try {
			Integer id = RequestTool.getInt(request, "id");
			AppQuestions appQuestions = new AppQuestions();
			Questions questions = appQuestions.findByID(id);
			Questions oldquestions = (Questions) BeanUtils.cloneBean(questions);
			

			questions.setTitle(RequestTool.getString(request, "title"));
			questions.setContent(RequestTool.getString(request, "content"));
			questions.setMakedate(DateUtil.getCurrentDate());
			questions.setMakedeptid(users.getMakedeptid());
			questions.setMakeid(userid);
			questions.setMakeorganid(users.getMakeorganid());

			appQuestions.update(questions);
			DBUserLog.addUserLog(userid, 0, "我的办公桌>>修改常见问题  ,编号：" + id,oldquestions,questions);
			request.setAttribute("result", "databases.upd.success");
			return mapping.findForward("success");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
