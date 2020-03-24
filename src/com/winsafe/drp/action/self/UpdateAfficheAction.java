package com.winsafe.drp.action.self;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.Affiche;
import com.winsafe.drp.dao.AppAffiche;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.BeanCopy;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;

public class UpdateAfficheAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			// 获取id
			Integer id = Integer.valueOf(request.getParameter("id"));
			// 获取主题
			String affichetitle = request.getParameter("affichetitle");
			// 获取内容
			String affichecontent = request.getParameter("affichecontent");
			// 是否发布
			String isPublish = request.getParameter("isPublish");
			String affichetype = request.getParameter("affichetype");
			String ebdDate = request.getParameter("endDate");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();

			AppAffiche appAffiche = new AppAffiche();
			
			Affiche af = appAffiche.getAfficheByID(id);
			
			af.setAffichetitle(affichetitle);
			af.setAffichecontent(affichecontent);
			af.setMakeorganid(users.getMakeorganid());
			af.setMakeid(userid);
			af.setIsPublish(isPublish);
			af.setAffichetype(Integer.valueOf(affichetype));
			if(!StringUtil.isEmpty(ebdDate)) {
				af.setEndDate(DateUtil.StringToDate(ebdDate));
			}
			if (af.getIsPublish().equals("1")) {
				af.setMakedate(new Date());
			}
			appAffiche.updAffiche(af);

			request.setAttribute("result", "databases.upd.success");
			DBUserLog.addUserLog(request, "公告编号" + af.getId());
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}