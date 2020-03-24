package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPhoneBook;
import com.winsafe.drp.dao.AppPhoneBookSort;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

/**
 * @author : jerry
 * @version : 2009-7-29 上午11:08:37
 * www.winsafe.cn
 */
public class DelPhoneBokSortAction extends Action{

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			Integer sortid = Integer.valueOf(request.getParameter("ID"));
			AppPhoneBookSort appPhoneBookSort = new AppPhoneBookSort();
			AppPhoneBook appPhoneBook = new AppPhoneBook();
			int count = appPhoneBook.isCount(sortid);
			if(count > 0 ){
				request.setAttribute("result", "组中有电话薄信息存在!该组不能删除!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			
			DBUserLog.addUserLog(userid,0, "我的办公桌>>删除组,编号："+sortid);
			appPhoneBookSort.delPhoneBookSort(sortid);
			

			request.setAttribute("result", "databases.del.success");

			return mapping.findForward("success");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
