package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCodeRule;
import com.winsafe.drp.dao.CodeRule;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.RequestTool;

public class UpdCodeRuleAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			AppCodeRule appcs = new AppCodeRule();
			String ucode = request.getParameter("ucode");
			int[] startno = RequestTool.getInts(request, "startno");
			int[] lno = RequestTool.getInts(request, "lno");
			
			CodeRule cu = null;
			for ( int i=0; i<startno.length; i ++ ){
				cu = appcs.getCodeRuleByUcodeCrp(ucode, i);
				cu.setStartno(startno[i]);
				cu.setLno(lno[i]);
				appcs.updCodeRule(cu);
			}
			
			request.setAttribute("result", "databases.upd.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11, "条码规则设置>>修改条形码规则,标志位:"+ucode);// 日志
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return new ActionForward(mapping.getInput());
	}
}
