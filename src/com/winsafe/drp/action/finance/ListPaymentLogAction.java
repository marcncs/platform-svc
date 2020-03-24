package com.winsafe.drp.action.finance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPaymentLog;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListPaymentLogAction
    extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);
		try {
			String[] tablename = { "PaymentLog" };
			String whereSql = getWhereSql2(tablename); 
			String timeCondition = getTimeCondition("MakeDate");
			String blur = getKeyWordCondition("KeysContent");
			whereSql = whereSql + blur + timeCondition;
			whereSql = DbUtil.getWhereSql(whereSql); 
			AppPaymentLog apl = new AppPaymentLog();
			List slls = apl.getPaymentLog(request,pagesize, whereSql);
			request.setAttribute("arls", slls);
			DBUserLog.addUserLog(userid,9,"账务管理>>列表付款记录"); 
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
