package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleIndent;
import com.winsafe.drp.dao.SaleIndent;
import com.winsafe.drp.dao.SaleIndentForm;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class PrintListSaleIndentAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {

			String Condition = " (si.makeid=" + userid + " "
					+ getOrVisitOrgan("si.makeorganid") + ") ";
			String[] tablename = { "SaleIndent" };
			String whereSql = getWhereSql2(tablename);

			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppSaleIndent asl = new AppSaleIndent();
			List<SaleIndent> pils = asl.getSaleIndent(whereSql);

			List<SaleIndentForm> also = new ArrayList<SaleIndentForm>();
			for (SaleIndent o : pils) {
				SaleIndentForm sof = new SaleIndentForm();

				sof.setId(o.getId());
				sof.setCustomerbillid(o.getCustomerbillid());
				sof.setCid(o.getCid());
				sof.setCname(o.getCname());
				sof.setMakedate(o.getMakedate());
				sof.setConsignmentdate(String.valueOf(o.getConsignmentdate())
						.substring(0, 10));
				sof.setTotalsum(Double.valueOf(o.getTotalsum()));
				sof.setIsaudit(o.getIsaudit());
				sof.setIsendcase(o.getIsendcase());
				sof.setTransportmodename(Internation.getStringByKeyPosition(
						"OrderStatus", request, o.getTransportmode(),
						"global.sys.SystemResource"));
				sof.setMakeid(o.getMakeid());
				also.add(sof);
			}

			request.setAttribute("also", also);

			DBUserLog.addUserLog(userid, 6, "打印销售订单");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
