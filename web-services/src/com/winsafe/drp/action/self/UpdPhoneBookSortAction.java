package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPhoneBookSort;
import com.winsafe.drp.dao.PhoneBookSort;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class UpdPhoneBookSortAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String strid = request.getParameter("id");
		Integer id = Integer.valueOf(strid);
		String sortname = request.getParameter("sortname");
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		try {
			AppPhoneBookSort apbs = new AppPhoneBookSort();
			if(apbs.getPhoneBookSortBySortName(sortname,userid)){
				request.setAttribute("result", "修改失败!该组名已经存在!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			PhoneBookSort pbs = apbs.getPhoneBookSortByID(id);
			PhoneBookSort oldpbs = (PhoneBookSort) BeanUtils.cloneBean(pbs);
			apbs.updPhoneBookSort(id, sortname);
			request.setAttribute("result", "databases.upd.success");
			
			DBUserLog.addUserLog(userid, 0, "我的办公桌>>修改电话本结构,编号："+id,oldpbs,pbs);
			return mapping.findForward("updresult");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
