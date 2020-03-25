package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.dao.AppOrganWithdrawDetail;
import com.winsafe.drp.dao.OrganWithdraw;
import com.winsafe.drp.util.DBUserLog;

/**
 */
public class DelPlantWithdrawAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			AppOrganWithdraw appAMA = new AppOrganWithdraw();
			AppOrganWithdrawDetail appAMAD = new AppOrganWithdrawDetail();
			OrganWithdraw alterma = appAMA.getOrganWithdrawByID(id);
			// 单据是否存在
			if(alterma == null){
				request.setAttribute("result", "databases.record.delete");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			// 单据是否复核
			if (alterma.getIsaudit() == 1) {
				String result = "databases.record.nodel";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			appAMAD.deleteByPIID(id);
			appAMA.delete(alterma);

			DBUserLog.addUserLog(request,"编号：" + id, alterma);
			request.setAttribute("result", "databases.del.success");

		} catch (Exception ex) {
			ex.printStackTrace();
			request.setAttribute("result", "databases.del.fail");
		}
		return mapping.findForward("success");
	}
}
