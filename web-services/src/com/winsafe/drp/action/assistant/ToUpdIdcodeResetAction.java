package com.winsafe.drp.action.assistant;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppIdcodeReset;
import com.winsafe.drp.dao.AppIdcodeResetDetail;
import com.winsafe.drp.dao.IdcodeReset;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class ToUpdIdcodeResetAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("ID");
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		try {
			AppIdcodeReset appr = new AppIdcodeReset();			
			IdcodeReset r = appr.getIdcodeResetById(id);
			if ( r.getIsaudit() == 1 ){
				request.setAttribute("result", "databases.record.approvestatus");
				return new ActionForward("/sys/lockrecord.jsp");
			}
			
			
			AppIdcodeResetDetail aprp = new AppIdcodeResetDetail();
			List rplist = aprp.getIdcodeResetDetailByIrid(id);
			
					

			request.setAttribute("r", r);
			request.setAttribute("rplist", rplist);
			return mapping.findForward("toupd");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}

}
