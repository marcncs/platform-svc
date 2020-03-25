package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DbUtil;

public class ToCustomerReportAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			String Condition = " c.isdel=0 and makeorganid = '"+users.getMakeorganid()+"'";
			String[] tablename = { "Customer" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppCustomer ac = new AppCustomer();
			int count = ac.getCustomerCount(whereSql);

			AppUsers au = new AppUsers();
			List us = au.getIDAndLoginName();
			List uls = new ArrayList();
			for (int i = 0; i < us.size(); i++) {
				UsersBean ub = new UsersBean();
				Object[] o = (Object[]) us.get(i);
				ub.setUserid(Integer.valueOf(o[0].toString()));
				ub.setRealname(o[2].toString());
				uls.add(ub);
			}
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("uls", uls);
			request.setAttribute("count", count);
			return mapping.findForward("listCount");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
