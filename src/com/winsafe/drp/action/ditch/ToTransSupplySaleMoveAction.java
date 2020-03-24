package com.winsafe.drp.action.ditch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSupplySaleApply;
import com.winsafe.drp.dao.AppSupplySaleApplyDetail;
import com.winsafe.drp.dao.SupplySaleApply;
import com.winsafe.drp.dao.SupplySaleApplyDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class ToTransSupplySaleMoveAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		super.initdata(request);
		try {
			AppSupplySaleApply asm = new AppSupplySaleApply();
			SupplySaleApply sm = asm.getSupplySaleApplyByID(id);

			AppSupplySaleApplyDetail asmd = new AppSupplySaleApplyDetail();
			List<SupplySaleApplyDetail> smdls = asmd
					.getSupplySaleAplyNoTransBySSID(id);

			request.setAttribute("als", smdls);
			request.setAttribute("smf", sm);
			DBUserLog.addUserLog(userid, 4, "渠道管理>>代销申请转渠道代销,编号：" + id);
			return mapping.findForward("totrans");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
