package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AlterMoveApply;
import com.winsafe.drp.dao.AppAlterMoveApply;
import com.winsafe.drp.dao.AppAlterMoveApplyDetail;
import com.winsafe.drp.util.DBUserLog;

/**
 * @author : jerry
 * @version : 2009-8-24 下午03:40:40 www.winsafe.cn
 */
public class DelAlterMoveApplyAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			AppAlterMoveApply appAMA = new AppAlterMoveApply();
			AppAlterMoveApplyDetail appAMAD = new AppAlterMoveApplyDetail();
			AlterMoveApply alterma = appAMA.getAlterMoveApplyByID(id);
			if (alterma.getIsaudit() == 1) {
				String result = "databases.record.nodel";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			appAMAD.delAlterMoveApplyDetailByAmid(id);
			appAMA.delAlterMoveApply(alterma);

			DBUserLog.addUserLog(userid, 4, "渠道管理>>删除订购申请,编号：" + id, alterma);
			request.setAttribute("result", "databases.del.success");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mapping.findForward("success");
	}
}
