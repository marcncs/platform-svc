package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMoveApply;
import com.winsafe.drp.dao.MoveApply;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;

public class BlankOutMoveApplyAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		super.initdata(request);try{
			String aaid = request.getParameter("AAID");
			AppMoveApply api = new AppMoveApply();
			MoveApply pi = api.getMoveApplyByID(aaid);

			if(pi.getIstrans()==1){
				String result = "databases.operator.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			pi.setIsblankout(1);
			pi.setBlankoutid(userid);
			pi.setBlankoutdate(Dateutil.getCurrentDate());
			api.updMoveApply(pi);

			request.setAttribute("result", "databases.operator.success");
			DBUserLog.addUserLog(userid,4, "渠道管理>>作废转仓申请单,编号：" + aaid);

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
