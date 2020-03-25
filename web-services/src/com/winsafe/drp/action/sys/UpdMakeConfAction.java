package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.dao.MakeConf;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.RequestTool;

public class UpdMakeConfAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			String tablename = request.getParameter("tablename");
			AppMakeConf air = new AppMakeConf();
			MakeConf w = air.getMakeConfByID(tablename);
			MakeConf oldW = (MakeConf)BeanUtils.cloneBean(w);
			w.setTablename(tablename);
			w.setCurrentvalue(RequestTool.getLong(request,"currentvalue"));
			w.setRunmode(RequestTool.getInt(request, "runmode"));
			w.setProfix(request.getParameter("profix"));
			w.setExtent(RequestTool.getInt(request,"extent"));
			w.setChname(request.getParameter("chname"));
			air.updMakeConf(w);

			request.setAttribute("result", "databases.upd.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid,11, "基础设置>>修改编号规则,编号："+tablename, oldW, w);

			return mapping.findForward("updResult");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return mapping.findForward("updResult");
	}
}
