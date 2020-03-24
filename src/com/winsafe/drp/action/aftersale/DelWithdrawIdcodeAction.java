package com.winsafe.drp.action.aftersale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppWithdrawIdcode;

public class DelWithdrawIdcodeAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			AppWithdrawIdcode asb = new AppWithdrawIdcode();
			String ids = request.getParameter("ids");			
			String[] id = ids.split(",");			
			if ( id != null ){
				for (int i=0; i<id.length; i++ ){
					asb.delWithdrawIdcodeById(Long.valueOf(id[i]));
				}
			}

			request.setAttribute("result", "databases.del.success");
//			UsersBean users = UserManager.getUser(request);
//			DBUserLog.addUserLog(users.getUserid(), 6, "销售退货>>删除条码,条码:"
//					+ sb.getIdcode(), sb);

			return mapping.findForward("success");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

}
