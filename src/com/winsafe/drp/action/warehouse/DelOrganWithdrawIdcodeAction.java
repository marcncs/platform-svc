package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganWithdrawIdcode;

public class DelOrganWithdrawIdcodeAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);try{

			AppOrganWithdrawIdcode asb = new AppOrganWithdrawIdcode();
			String ids = request.getParameter("ids");	
			String[] id = ids.split(",");			
			if ( id != null ){
				for (int i=0; i<id.length; i++ ){
					asb.delOrganWithdrawIdcodeById(Long.valueOf(id[i]));
				}
			}


			request.setAttribute("result", "databases.del.success");
//			UsersBean users = UserManager.getUser(request);
//			DBUserLog.addUserLog(users.getUserid(), 7, "入库>>渠道退货签收>>删除条码,条码:"
//					+ sb.getIdcode(), sb);

			return mapping.findForward("success");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

}
