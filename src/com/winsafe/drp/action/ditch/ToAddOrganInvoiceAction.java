package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.OrganService;

/**
 * @author : jerry
 * @version : 2009-8-24 下午03:13:03
 * www.winsafe.cn
 */
public class ToAddOrganInvoiceAction extends BaseAction {

	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		OrganService os = new OrganService();
		String parentid = os.getOrganByID(users.getMakeorganid()).getParentid();
		request.setAttribute("parentid", parentid);
		request.setAttribute("parentName", 	os.getOrganName(parentid));
		request.setAttribute("makeorganid", users.getMakeorganid());
		return mapping.findForward("add");
	}
}
