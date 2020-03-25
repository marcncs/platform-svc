package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPhoneBookSort;
import com.winsafe.drp.dao.PhoneBookSort;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;

public class AddPhoneBookSortAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sortname = request.getParameter("sortname");
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		
		try {
			AppPhoneBookSort apbs = new AppPhoneBookSort();
			
			if(apbs.getPhoneBookSortBySortName(sortname,userid)){
				request.setAttribute("result", "添加失败!该组名已经存在!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			PhoneBookSort pbs = new PhoneBookSort();
			pbs.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"phone_book_sort", 0, "")));
			pbs.setSortname(sortname);
			pbs.setUserid(userid);

			
			apbs.addPhoneBookSort(pbs);

			request.setAttribute("result", "databases.add.success");

			DBUserLog.addUserLog(userid, 0, "我的办公桌>>新增电话本类别,编号：" + pbs.getId());
			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
