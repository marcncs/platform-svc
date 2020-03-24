package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOtherIncomeIdcode;

public class DelOtherIncomeIdcodeAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);try{

			AppOtherIncomeIdcode asb = new AppOtherIncomeIdcode();
			String ids = request.getParameter("ids");	
			String[] id = ids.split(",");			
			if ( id != null ){
				for (int i=0; i<id.length; i++ ){
					asb.delOtherIncomeIdcodeById(Long.valueOf(id[i]));
				}
			}

			request.setAttribute("result", "databases.del.success");
//			UsersBean users = UserManager.getUser(request);
//			DBUserLog.addUserLog(users.getUserid(), 7, "库存盘点>>盘盈单>>删除条码,条码:"
//					+ sb.getIdcode(), sb);

			return mapping.findForward("success");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

}
