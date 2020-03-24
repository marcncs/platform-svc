package com.winsafe.drp.action.ditch.productreturn;

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

public class AddProductReturnAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		String makeorganid = users.getMakeorganid();
		AppOrgan os = new AppOrgan();
		Organ og = os.getOrganByID(makeorganid);
		String parentOrganid =og.getParentid() ;
		if(parentOrganid.equals("0")){
			parentOrganid = makeorganid;
		}
		request.setAttribute("parentOrganid", parentOrganid);
		request.setAttribute("makeorganid", makeorganid);
		return mapping.findForward("add");
	}
}
