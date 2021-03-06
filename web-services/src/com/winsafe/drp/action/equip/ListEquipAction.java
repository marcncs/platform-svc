package com.winsafe.drp.action.equip;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppEquip;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListEquipAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);
		try {

			String Condition = " (e.makeid=" + userid + " "
					+ this.getOrVisitOrgan("e.makeorganid") + ") ";

			String[] tablename = { "Equip" };
			String whereSql = getWhereSql2(tablename);

			String timeCondition = getTimeCondition("EquipDate");
			String blur = getKeyWordCondition("ID", "KeysContent");
			whereSql = whereSql + blur + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppEquip asl = new AppEquip();
			List pils = asl.getEquip(request, pagesize, whereSql);

			request.setAttribute("also", pils);

			DBUserLog.addUserLog(userid, 8, "列表配送单");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
