package com.winsafe.drp.action.users;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppUserGroup;

/**
 * @author jelli
 *
 */
public class ListUserGroupAppAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

		String groupId=request.getParameter("groupId");
		try {
			initdata(request);
			AppUserGroup aba=new AppUserGroup();
			List<Map<String, String>> als=aba.getUserGroupAppByGroupId(groupId);
			request.setAttribute("appList", als);
			return mapping.findForward("listGroupApp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
 

}
