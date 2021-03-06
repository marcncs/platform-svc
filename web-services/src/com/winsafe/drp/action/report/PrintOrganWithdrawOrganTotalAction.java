package com.winsafe.drp.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintOrganWithdrawOrganTotalAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getAndVisitOrgan("makeorganid");
			}

			String[] tablename = { "OrganWithdraw" };
			String whereSql = getWhereSql(tablename);
			String timeCondition = getTimeCondition("MakeDate");
			String Condition = " (iscomplete=1 and isblankout=0  "
					+ visitorgan + ")";
			String blur = getKeyWordCondition("POrganID","POrganName","PLinkman","Tel","MakeOrganID");
			whereSql = whereSql + timeCondition +blur+ Condition; 

			whereSql = DbUtil.getWhereSql(whereSql); 

			
			AppOrganWithdraw aso = new AppOrganWithdraw();
			List list = aso.getOrganWithdrawOrganTotal(whereSql);
			DBUserLog.addUserLog(userid, 10,"报表分析>>打印渠道退货按机构汇总");
			request.setAttribute("list", list);
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
