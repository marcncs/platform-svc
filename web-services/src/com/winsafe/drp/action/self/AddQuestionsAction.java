package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

/**
 * @author : jerry
 * @version : 2009-8-8 上午11:36:10 www.winsafe.cn
 */
public class AddQuestionsAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		try {
			Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"questions", 0, ""));
			Questions questions = new Questions();

			questions.setId(id);
			questions.setTitle(RequestTool.getString(request, "title"));
			questions.setContent(RequestTool.getString(request, "content"));
			questions.setMakedate(DateUtil.getCurrentDate());
			questions.setMakedeptid(users.getMakedeptid());
			questions.setMakeid(userid);
			questions.setMakeorganid(users.getMakeorganid());
			
			AppQuestions appQuestions = new AppQuestions();
			
			appQuestions.save(questions);
			DBUserLog.addUserLog(userid, 0, "我的办公桌>>新增常见问题  ,编号：" + id);
			request.setAttribute("result", "databases.add.success");
			return mapping.findForward("success");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}

}
