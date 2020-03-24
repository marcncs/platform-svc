package com.winsafe.drp.action.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppScannerUser;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.ScannerUser;
import com.winsafe.drp.util.DBUserLog;

/**
 * 为用户配置采集器
* @Title: ToAddScannerUserAction.java
* @author: wenping 
* @CreateTime: Jul 9, 2012 1:16:14 PM
* @version:
 */
public class ToAddScannerUserAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			
			//用户ID
			String uid= request.getParameter("uid");
			String type= request.getParameter("type");
			//ScannerUser ID 
			String suid= request.getParameter("suid");
			
			AppUsers au = new AppUsers();
			AppScannerUser asu = new AppScannerUser();
			String uname= au.getUsersByid(Integer.parseInt(uid)).getRealname();
			
			request.setAttribute("uname", uname);
			request.setAttribute("uid", uid);
			
			if(type.equals("ADD")){
				request.setAttribute("type", "ADD");
				
				DBUserLog.addUserLog(userid, 11, "采集器配置>>新增");				
				return mapping.findForward("toadd");
				
			}else if(type.equals("EDIT")){
				
				ScannerUser su = asu.getScannerUserById(Integer.parseInt(suid));
				request.setAttribute("su", su);
				request.setAttribute("type", "EDIT");
				
				DBUserLog.addUserLog(userid, 11, "采集器配置>>修改");
				return mapping.findForward("toadd");
				
			}else if(type.equals("DELETE")){
				asu.delScannerUserById(Integer.parseInt(suid));
				
				DBUserLog.addUserLog(userid, 11, "采集器配置>>删除");
				
				List scanusers = asu.getScannersByUserID(uid);
				request.setAttribute("scanusers", scanusers);
				request.setAttribute("result", "databases.operator.success");
				return mapping.findForward("success");
			}
		
			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
