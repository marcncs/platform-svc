package com.winsafe.drp.action.finance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;

public class ListPeddleOrderCheckAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		

		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		super.initdata(request);try{
			AppOrgan ao = new AppOrgan();
			List ols = ao.getOrganToDown(users.getMakeorganid());
			
			AppUsers au = new AppUsers();
			List uls = au.getIDAndLoginNameByOID2(users.getMakeorganid());
			
			request.setAttribute("ols", ols);
			request.setAttribute("uls", uls);
			request.setAttribute("currentdate", DateUtil.getCurrentDateString());
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
