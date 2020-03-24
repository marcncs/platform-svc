package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCodeUnit;
import com.winsafe.drp.dao.CodeUnit;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.RequestTool;

public class UpdCodeUnitAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ucode");
			AppCodeUnit appcs = new AppCodeUnit();
			CodeUnit cu = appcs.getCodeUnitByID(id);
			CodeUnit oldcu = (CodeUnit)BeanUtils.cloneBean(cu);
			cu.setUnitid(RequestTool.getInt(request, "unitid"));
			cu.setUname(request.getParameter("uname"));
			cu.setRemark(request.getParameter("remark"));

			
			appcs.updCodeUnit(cu);

			
			request.setAttribute("result", "databases.upd.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11, "条码规则设置>>修改标识位规则,编号:"+cu.getUcode(), oldcu, cu);// 日志
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return new ActionForward(mapping.getInput());
	}
}
