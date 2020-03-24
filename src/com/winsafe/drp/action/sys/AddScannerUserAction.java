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
 * 新增 修改 采集器配置
* @Title: AddScannerUserAction.java
* @author: wenping 
* @CreateTime: Jul 9, 2012 1:37:13 PM
* @version:
 */
public class AddScannerUserAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String uid= request.getParameter("uid");
			String scannerid= request.getParameter("scanner");
			AppScannerUser asu = new AppScannerUser();
			
			ScannerUser scanneruser= new ScannerUser();
			scanneruser.setScanner(scannerid);
			scanneruser.setUserid(uid);
			if(request.getParameter("type").equals("ADD")){
				ScannerUser su = asu.getScanner(scannerid);
				if(su!=null){
					request.setAttribute("result", "该采集器已经存在！");
					return new ActionForward("/sys/lockrecord2.jsp");
				}
				//新增配置
				asu.InsertScannerUser(scanneruser);
			}else{
				//修改
				String suid= request.getParameter("suid");
				ScannerUser su = asu.getScannerUserById(Integer.valueOf(suid));
				if(!su.getScanner().equals(scannerid)){
					ScannerUser s = asu.getScanner(scannerid);
					if(s!=null){
						request.setAttribute("result", "该采集器已经存在！");
						return new ActionForward("/sys/lockrecord2.jsp");
					}
				}
				asu.updScannerUser(Integer.parseInt(suid), scannerid, uid);
			}
			
			
			List scanusers = asu.getScannersByUserID(uid);
			AppUsers au = new AppUsers();
			String uname= au.getUsersByid(Integer.parseInt(uid)).getRealname();
			request.setAttribute("scanusers", scanusers);
			request.setAttribute("uname", uname);
			request.setAttribute("result", "databases.operator.success");
			DBUserLog.addUserLog(userid, 11, "采集器配置>>新增,修改");
			return mapping.findForward("success1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
