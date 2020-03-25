package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganSafetyIntercalate;
import com.winsafe.drp.dao.OrganSafetyIntercalate;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class UpdOrganSafetyAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			Integer id = Integer.valueOf(request.getParameter("ID"));
			AppOrganSafetyIntercalate aoi = new AppOrganSafetyIntercalate();
			OrganSafetyIntercalate ws = aoi.getOrganSafetyIntercalateByID(id);
			OrganSafetyIntercalate oldws = (OrganSafetyIntercalate) BeanUtils
					.cloneBean(ws);

			ws.setSafetyh(Double.valueOf(request.getParameter("Safetyh")));
			ws.setSafetyl(Double.valueOf(request.getParameter("Safetyl")));

			aoi.updOrganSafetyIntercalate(ws);

			request.setAttribute("result", "databases.upd.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11, "机构设置>>修改仓库报警,编号：" + ws.getId(),
					oldws, ws);

			return mapping.findForward("updResult");
		} catch (Exception e) {
			e.printStackTrace();

		} finally {

		}
		return mapping.findForward("updResult");
	}
}
