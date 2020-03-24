package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIntegralOrder;
import com.winsafe.drp.dao.IntegralOrder;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintListIntegralOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {

			String Condition = " (io.makeid=" + userid + " "
					+ getOrVisitOrgan("io.makeorganid")
					+ getOrVisitOrgan("io.equiporganid") + ") ";

			String[] tablename = { "IntegralOrder" };
			String whereSql = getWhereSql2(tablename);

			String timeCondition = getTimeCondition("MakeDate");
			String blur = getKeyWordCondition("KeysContent");

			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppIntegralOrder asl = new AppIntegralOrder();
			List<IntegralOrder> pils = asl.searchIntegralOrder(whereSql);

			request.setAttribute("also", pils);

			DBUserLog.addUserLog(userid, 6, "零售管理>>打印积分换购");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
