package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppICode;
import com.winsafe.drp.dao.ICode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;

public class AddICodeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String lcode = request.getParameter("lcode");
			AppICode appcs = new AppICode();
			if ( appcs.getICodeByLcode(lcode) != null ){
				request.setAttribute("result", lcode+"物流码前缀已经存在！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			
			ICode cu = new ICode();
			cu.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("i_code",0, "")));
			cu.setProductid(request.getParameter("pid"));
			cu.setLcode(lcode);

			
			appcs.addICode(cu);

			
			request.setAttribute("result", "databases.add.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11, "产品资料>>新增物流码前缀,编号:"+cu.getLcode());// 日志
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return new ActionForward(mapping.getInput());
	}
}
