package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIncomeLog;
import com.winsafe.drp.dao.IncomeLog;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.RequestTool;

public class UpdIncomeLogAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String id = request.getParameter("id");

			AppIncomeLog ai = new AppIncomeLog();
			IncomeLog il = ai.getIncomeLogByID(id);
			IncomeLog oldW = (IncomeLog)BeanUtils.cloneBean(il);

			il.setDrawee(request.getParameter("drawee"));
			il.setFundattach(RequestTool.getInt(request, "fundattach"));
			il.setIncomesum(RequestTool.getDouble(request, "incomesum"));
			il.setBillnum(request.getParameter("billnum"));
			il.setRemark(request.getParameter("remark"));

			ai.updIncomeLog(il);

			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(userid, 9, "收款管理>>修改收款记录,编号：" + id,oldW,il);

			return mapping.findForward("updresult");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
