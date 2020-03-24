/**
 * 
 */
package com.winsafe.drp.action.users;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCallCenterEvent;

/**
 * @author jelli
 * 
 */
public class ListUserCallEventAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			String muid = request.getParameter("muid");
			AppCallCenterEvent aba = new AppCallCenterEvent();
			List als = aba.getUserCallEvent(Integer.valueOf(muid));

			request.setAttribute("muid", muid);
			request.setAttribute("als", als);

			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
