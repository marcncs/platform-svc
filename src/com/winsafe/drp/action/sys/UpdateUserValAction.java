package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.UpkeyForm;
import com.winsafe.drp.util.DBUserLog;

public class UpdateUserValAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception { 
		String userId = request.getParameter("userid");
		String path = request.getSession().getServletContext().getRealPath("/").toString();
		UpkeyForm upkey = (UpkeyForm)form;
		if(upkey.getKeyfile().getFileName()==null||"".equals(upkey.getKeyfile().getFileName())){
			request.setAttribute("result", "请选择正确文件");
			return mapping.findForward("finish");
		}
		
		byte[] keys=upkey.getKeyfile().getFileData();
		
		try{
		// 显示到期内容
		String valdate = "";
		request.setAttribute("result", "有效期更新完毕，有效期限为"+valdate);
		// 写日志
		try{
		DBUserLog.addUserLog(Integer.valueOf(userId), 11, "有效期已更新至:"+valdate+" 编号:"+userId);
		}catch (Exception e){
		//DBUserLog.addUserLog(1, 11, "有效期已更新至:"+valdate+" 编号:"+1);
		}
		return mapping.findForward("finish");}
		catch (Exception e){
			request.setAttribute("result", "系统错误，请联系管理员");
			return mapping.findForward("finish");
		}
	}
}
