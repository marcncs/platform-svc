package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppSystemResource;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.SystemResource;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.BaseResourceService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.RequestTool;

public class UpdSystemResourceAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		try {
			AppSystemResource abr = new AppSystemResource();			
			SystemResource br= abr.getSystemResourceByid(RequestTool.getLong(request, "id"));
			SystemResource oldbr = (SystemResource)BeanUtils.cloneBean(br);
			br.setTagname(request.getParameter("tagname").trim());
			br.setTagkey(request.getParameter("tagsubkey").trim());
			br.setTagvalue(request.getParameter("tagsubvalue").trim());
		     br.setMemo(request.getParameter("memo").trim());
			
			abr.updSystemResource(br);
			
			
			request.setAttribute("result", "databases.upd.success");
		      UsersBean users = UserManager.getUser(request);
		      int userid = users.getUserid();
		      DBUserLog.addUserLog(userid,11,"基础设置>>修改资源,编号:"+br.getId(), oldbr, br);
			return mapping.findForward("updResult");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return mapping.findForward("updResult");
	}
}
