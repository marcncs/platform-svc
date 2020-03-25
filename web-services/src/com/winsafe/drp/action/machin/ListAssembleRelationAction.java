package com.winsafe.drp.action.machin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppAssembleRelation;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListAssembleRelationAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);

		try {

			String Condition = " (ar.makeid='" + userid + "' "
					+ getOrVisitOrgan("ar.makeorganid") + ") ";
			String[] tablename = { "AssembleRelation" };
			String whereSql = getWhereSql2(tablename);
			String blur = getKeyWordCondition("ID", "KeysContent");
			String timeCondition = getTimeCondition("MakeDate");
			whereSql = whereSql + blur + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppAssembleRelation asb = new AppAssembleRelation();
			List pils = asb.getAssembleRelation(request, pagesize, whereSql);

			request.setAttribute("alsb", pils);
			DBUserLog.addUserLog(userid, 3, "列表组装关系设置");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
