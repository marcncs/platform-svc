package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppServiceAgreement;
import com.winsafe.drp.dao.ServiceAgreement;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class DelServiceAgreementAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			Integer id = Integer.valueOf(request.getParameter("ID"));
			AppServiceAgreement ah = new AppServiceAgreement();
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			ServiceAgreement sa = ah.getServiceAgreementByID(id);
			if(!sa.getMakeid().equals(userid) && userid.intValue() !=1){
				request.setAttribute("result", "databases.del.nosuccess");
				return mapping.findForward("del");
			}
			ah.delServiceAgreement(id);

			request.setAttribute("result", "databases.del.success");
			DBUserLog.addUserLog(userid, 0, "我的办公桌>>删除服务预约,编号:" + id, sa);

			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
