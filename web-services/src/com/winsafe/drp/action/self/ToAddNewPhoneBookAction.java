package com.winsafe.drp.action.self;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPhoneBookSort;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ToAddNewPhoneBookAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			AppPhoneBookSort apbs = new AppPhoneBookSort();
			String sex = Internation.getSelectTagByKeyAll("Sex", request,
					"sex", false, null);
			List sort = apbs.getPhoneBookSort(userid);

			request.setAttribute("sex", sex);
			request.setAttribute("sort", sort);
			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
