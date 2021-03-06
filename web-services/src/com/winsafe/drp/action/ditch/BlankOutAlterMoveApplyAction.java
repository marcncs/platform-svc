package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AlterMoveApply;
import com.winsafe.drp.dao.AppAlterMoveApply;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

/**
 * @author : jerry
 * @version : 2009-8-24 下午06:23:34 www.winsafe.cn
 */
public class BlankOutAlterMoveApplyAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			AppAlterMoveApply appAMA = new AppAlterMoveApply();
			AlterMoveApply alterma = appAMA.getAlterMoveApplyByID(id);

			alterma.setIsblankout(1);
			alterma.setBlankoutid(userid);
			alterma.setBlankoutdate(DateUtil.getCurrentDate());
			appAMA.updAlterMoveApply(alterma);

			request.setAttribute("result", "databases.operator.success");
			DBUserLog.addUserLog(userid, 4, "渠道管理>>作废订购申请,编号：" + id);

			return mapping.findForward("success");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
