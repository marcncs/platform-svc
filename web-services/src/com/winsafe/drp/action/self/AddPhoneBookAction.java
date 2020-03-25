package com.winsafe.drp.action.self;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.winsafe.hbm.util.MakeCode;

public class AddPhoneBookAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		ValidatePhoneBook vpb = (ValidatePhoneBook) form;

		try {
			PhoneBook pb = new PhoneBook();
			pb.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"phone_book", 0, "")));
			pb.setName(vpb.getName());
			pb.setSex(vpb.getSex());
			pb.setPhone(vpb.getPhone());
			pb.setMobile(vpb.getMobile());
			pb.setEmail(vpb.getEmail());
			pb.setQq(vpb.getQq());
			pb.setMsn(vpb.getMsn());
			String birthday = vpb.getBirthday().replace('-', '/');
			if (birthday != null && birthday.trim().length() > 0) {
				pb.setBirthday(new Date(birthday));
			}
			pb.setAddr(vpb.getAddr());
			pb.setSortid(vpb.getSortid());
			pb.setRemark(vpb.getRemark());
			pb.setUserid(1);

			AppPhoneBook apb = new AppPhoneBook();
			apb.addPhoneBook(pb);

			request.setAttribute("result", "databases.add.success");

			DBUserLog.addUserLog(userid, 0, "我的办公桌>>新增电话本,编号：" + pb.getId());
			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return new ActionForward(mapping.getInput());
	}
}
