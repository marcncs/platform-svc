package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

/**
 * @author : jerry
 * @version : 2009-8-24 下午03:13:03 www.winsafe.cn
 */
public class ToAddAlterMoveApplyAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);

		String makeorganid = users.getMakeorganid();
		AppOrgan os = new AppOrgan();
		Organ og = os.getOrganByID(makeorganid);
		String parentOrganid = og.getParentid();
		if (parentOrganid.equals("0")) {
			parentOrganid = makeorganid;
		}
		request.setAttribute("parentOrganid", parentOrganid);
		request.setAttribute("organid", makeorganid);
		request.setAttribute("oaddr", og.getOaddr());
		return mapping.findForward("add");
	}
}
