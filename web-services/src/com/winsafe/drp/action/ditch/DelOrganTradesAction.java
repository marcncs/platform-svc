package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganTrades;
import com.winsafe.drp.dao.AppOrganTradesDetail;
import com.winsafe.drp.dao.OrganTrades;
import com.winsafe.drp.util.DBUserLog;

/**
 * @author : jerry
 * @version : 2009-9-3 上午10:33:45 www.winsafe.cn
 */
public class DelOrganTradesAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			AppOrganTrades appAMA = new AppOrganTrades();
			AppOrganTradesDetail appAMAD = new AppOrganTradesDetail();
			OrganTrades alterma = appAMA.getOrganTradesByID(id);
			if (alterma.getIsaudit() == 1) {
				String result = "databases.record.nodel";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			appAMAD.deleteByPIID(id);
			appAMA.delete(alterma);

			DBUserLog.addUserLog(userid, 4, "渠道管理>>删除渠道换货单,编号：" + id, alterma);
			request.setAttribute("result", "databases.del.success");

		} catch (Exception ex) {
			ex.printStackTrace();
			request.setAttribute("result", "databases.del.fail");
		}
		return mapping.findForward("success");
	}
}
