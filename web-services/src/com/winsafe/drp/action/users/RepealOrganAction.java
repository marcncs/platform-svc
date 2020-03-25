package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

public class RepealOrganAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		try {
			String id = request.getParameter("ID");
			AppOrgan ao = new AppOrgan();
			Organ o = ao.getOrganByID(id);

			if (o.getIsrepeal() == 1) {
				request.setAttribute("result", "datebases.record.returnoperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			o.setIsrepeal(1);
			o.setRepealid(userid);
			o.setRepealdate(DateUtil.getCurrentDate());
			ao.updOrgan(o);

			request.setAttribute("result", "databases.operator.success");
			DBUserLog.addUserLog(userid, "系统管理", "撤消机构,编号：" + id);
//			DBUserLog.addUserLog(request, "撤消机构,编号：" + id);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
