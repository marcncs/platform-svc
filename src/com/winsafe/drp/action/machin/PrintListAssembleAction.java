package com.winsafe.drp.action.machin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppAssemble;
import com.winsafe.drp.dao.Assemble;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintListAssembleAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String Condition = " (a.makeid='" + userid + "' "
					+ getOrVisitOrgan("a.makeorganid") + ") ";

			String[] tablename = { "Assemble" };
			String whereSql = getWhereSql2(tablename);
			String timeCondition = getTimeCondition("CompleteIntendDate");
			whereSql = whereSql + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppAssemble api = new AppAssemble();
			List<Assemble> pils = api.getAssemble(whereSql);
			request.setAttribute("alpi", pils);

			 DBUserLog.addUserLog(userid,3,"生产组装>>打印组装单");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
