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

public class ListSaleIndentAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;

		super.initdata(request);
		try {

			String Condition = " (si.makeid="+userid+" "+getOrVisitOrgan("si.makeorganid")+") ";
			String[] tablename = { "SaleIndent" };
			String whereSql = getWhereSql2(tablename);

			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppSaleIndent asl = new AppSaleIndent();
			List pils = asl.getSaleIndent(request,pagesize, whereSql);

			ArrayList also = new ArrayList();
			SaleIndent o = null;
			for (int i = 0; i < pils.size(); i++) {
				SaleIndentForm sof = new SaleIndentForm();
				o = (SaleIndent) pils.get(i);
				sof.setId(o.getId());
				sof.setCustomerbillid(o.getCustomerbillid());
				sof.setCid(o.getCid());
				sof.setCname(o.getCname());
				sof.setMakedate(o.getMakedate());
				sof.setConsignmentdate(String.valueOf(o.getConsignmentdate())
						.substring(0, 10));
				sof.setTotalsum(Double.valueOf(o.getTotalsum()));
				sof.setIsaudit( o.getIsaudit());
				sof.setIsendcase(o.getIsendcase());
				sof.setTransportmodename(Internation.getStringByKeyPosition(
						"OrderStatus", request, o.getTransportmode(),
						"global.sys.SystemResource"));
				sof.setMakeid(o.getMakeid());
				also.add(sof);
			}
			
			request.setAttribute("also", also);
			request.setAttribute("CID", request.getParameter("CID"));
			request.setAttribute("IsAudit", map.get("IsAudit"));
			request.setAttribute("IsEndcase", map.get("IsEndcase"));
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeDeptID", request.getParameter("MakeDeptID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("BeginDate", request.getParameter("BeginDate"));
			request.setAttribute("EndDate", request.getParameter("EndDate"));
			DBUserLog.addUserLog(userid,6, "列表销售订单");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
