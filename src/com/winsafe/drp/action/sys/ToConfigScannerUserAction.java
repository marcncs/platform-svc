package com.winsafe.drp.action.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppScannerUser;
import com.winsafe.drp.dao.AppScannerWarehouse;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.util.DBUserLog;

/**
 * 为用户配置采集器
* @Title: ToConfigScannerUserAction.java
* @author: wenping 
* @CreateTime: Jul 9, 2012 11:36:13 AM
* @version:
 */
public class ToConfigScannerUserAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			//得到用户ID
			String uid= request.getParameter("ID");

			AppUsers au = new AppUsers();
			String uname= au.getUsersByid(userid).getRealname();
			AppScannerUser asu = new AppScannerUser();
//			List scanusers = asu.getScannersByUserID(uid);
			List scanusers = asu.getScanners();
			request.setAttribute("uid", userid);
			request.setAttribute("scanusers", scanusers);
			request.setAttribute("uname", uname);

			DBUserLog.addUserLog(userid, 11, "用户管理>>采集器配置");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
