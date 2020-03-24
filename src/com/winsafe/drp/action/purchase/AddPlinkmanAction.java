package com.winsafe.drp.action.purchase;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPlinkman;
import com.winsafe.drp.dao.Plinkman;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;

public class AddPlinkmanAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			String pid = (String) request.getSession().getAttribute("pid");
			if (pid == null || pid.equals("null") || pid.equals("")) {
				String result = "databases.upd.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}

			Plinkman pl = new Plinkman();
			Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"plinkman", 0, ""));
			pl.setId(id);
			pl.setPid(pid);
			pl.setName(request.getParameter("name"));
			pl.setSex(Integer.valueOf(request.getParameter("sex")));
			// pl.setBirthday(vpl.getBirthday());
			String birthday = request.getParameter("birthday")
					.replace('-', '/');
			if (birthday != null && birthday.trim().length() > 0) {
				pl.setBirthday(new Date(birthday));
			}
			pl.setDepartment(request.getParameter("department"));
			pl.setDuty(request.getParameter("duty"));
			pl.setOfficetel(request.getParameter("officetel"));
			pl.setMobile(request.getParameter("mobile"));
			pl.setHometel(request.getParameter("hometel"));
			pl.setQq(request.getParameter("qq"));
			pl.setEmail(request.getParameter("email"));
			pl.setMsn(request.getParameter("msn"));
			pl.setIsmain(Integer.valueOf(request.getParameter("ismain")));

			AppPlinkman apl = new AppPlinkman();
			apl.addPlinkman(pl);
			request.setAttribute("result", "databases.add.success");

			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 2, "供应商资料>>新增供应商联系人,编号:" + id);

			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}
}
