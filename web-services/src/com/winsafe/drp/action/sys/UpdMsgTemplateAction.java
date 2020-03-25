package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppMsgTemplate;
import com.winsafe.drp.dao.MsgTemplate;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.RequestTool;

public class UpdMsgTemplateAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int id = Integer.valueOf(request.getParameter("id"));
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		try {
			AppMsgTemplate aw = new AppMsgTemplate();
			MsgTemplate mt = aw.getMsgTemplateById(id);
			MsgTemplate oldmt = (MsgTemplate)BeanUtils.cloneBean(mt);
			
			mt.setTemplatename(request.getParameter("templatename"));
			mt.setTemplatecontent(request.getParameter("templatecontent"));
			mt.setIsuse(RequestTool.getInt(request, "isuse"));
			mt.setUpdid(userid);
			mt.setUpddate(DateUtil.getCurrentDate());
			
			aw.updMsgTemplate(mt);
			
			request.setAttribute("result", "databases.upd.success");
			DBUserLog.addUserLog(userid, 12, "修改短信模版，编号："+id, oldmt, mt);

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return mapping.findForward("success");
	}

}
