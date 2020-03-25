package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPhoneBook;
import com.winsafe.drp.dao.PhoneBook;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.ValidatePhoneBook;
import com.winsafe.drp.util.DBUserLog;

public class UpdPhoneBookAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ValidatePhoneBook vpb = (ValidatePhoneBook) form;
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		try {
			Integer id = vpb.getId();
			String name = vpb.getName();
			Integer sex = vpb.getSex();
			String phone = vpb.getPhone();
			String mobile = vpb.getMobile();
			String email = vpb.getEmail();
			String qq = vpb.getQq();
			String msn = vpb.getMsn();
			String tmpbirthday = vpb.getBirthday();
			if (tmpbirthday.trim() == null || tmpbirthday.trim().length() <= 0) {
				tmpbirthday = "";
			}
			String birthday = tmpbirthday;
			String addr = vpb.getAddr();
			Integer sortid = vpb.getSortid();
			String remark = vpb.getRemark();

			AppPhoneBook apb = new AppPhoneBook();
			PhoneBook pb = apb.getPhoneBookByID(id);
			PhoneBook oldpb = (PhoneBook) BeanUtils.cloneBean(pb);
			apb.updPhoneBook(id, name, sex, phone, mobile, email, qq, msn,
					birthday, addr, remark, sortid, userid);

			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(userid, 0, "我的办公桌>>修改电话本,编号：" + id, oldpb, pb);

			return mapping.findForward("updresult");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
