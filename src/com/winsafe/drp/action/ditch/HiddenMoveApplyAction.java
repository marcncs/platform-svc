package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMoveApply;
import com.winsafe.drp.util.DBUserLog;

/**
 * @author : jerry
 * @version : 2009-9-1 下午06:52:31 www.winsafe.cn
 */
public class HiddenMoveApplyAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try{
			String id = request.getParameter("ID");
			AppMoveApply appSsa = new AppMoveApply();
			appSsa.IsTrans(id, 1);
			DBUserLog.addUserLog(userid, 4, "渠道管理>>隐藏转仓申请,编号：" + id);
			request.setAttribute("result", "databases.operator.success");
			return mapping.findForward("success");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
