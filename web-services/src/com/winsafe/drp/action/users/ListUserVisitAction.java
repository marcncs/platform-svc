package com.winsafe.drp.action.users;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UserVisit;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.Pager;

public class ListUserVisitAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		//dao所需类
		AppUserVisit auv = new AppUserVisit();
		int pagesize = 20;
		//初始化
		initdata(request);
		String userid = request.getParameter("userid");
		try {
			//使用连接查询
			String whereSql = " , UserVisit as u where o.id=u.visitorgan  and  u.userid="+userid+") ";
			
			List vulist = auv.getUVOrgan(request, pagesize, whereSql);

			request.setAttribute("vulist", vulist);
			UsersBean users = UserManager.getUser(request);
			if (users != null) {
				Integer uid = users.getUserid();
				DBUserLog.addUserLog(uid, "系统管理", "用户管理>>列表用户管辖权限");
			}
			request.setAttribute("uid", userid);
			return mapping.findForward("uv");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
