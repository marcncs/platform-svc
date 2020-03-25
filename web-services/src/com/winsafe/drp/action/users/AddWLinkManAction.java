package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppWlinkMan;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Wlinkman;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.BeanCopy;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddWLinkManAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppWlinkMan appWinkMan = new AppWlinkMan();

		try {

			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			Wlinkman ol = new Wlinkman();
			BeanCopy bc = new BeanCopy();
			bc.copy(ol, request);
			ol.setMakedate(DateUtil.getCurrentDate());
			ol.setMakeid(userid);
			ol.setMakeorganid(users.getMakeorganid());
			appWinkMan.addWlinkman(ol);
			
			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(request, "新增仓库联系人"+ol.getId());
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

}
