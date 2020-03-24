package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMoveApply;
import com.winsafe.drp.dao.MoveApply;
import com.winsafe.drp.dao.MoveApplyForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintListMoveApplyAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			String ISAUDIT = request.getParameter("ISAUDIT");
			String Condition = "";
			if (ISAUDIT.equals("yes")) {
				Condition = users.getMakeorganid() + " in (select parentid from Organ where id = ma.outorganid) and ma.outorganid in (select visitorgan from UserVisit as uv where  uv.userid=" + userid + ") ";
			} else {
				Condition = " (ma.makeorganid='" + users.getMakeorganid()+ "'"+
				" or ma.outorganid in (select visitorgan from UserVisit as uv where  uv.userid=" + userid + "))";
			}

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "MoveApply" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MoveDate");
			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");
			whereSql = whereSql + timeCondition + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppMoveApply aama = new AppMoveApply();

			List sals = aama.getMoveApply(whereSql);
			ArrayList als = new ArrayList();
			for (int i = 0; i < sals.size(); i++) {
				MoveApplyForm saf = new MoveApplyForm();
				MoveApply o = (MoveApply) sals.get(i);
				saf.setId(o.getId());
				saf.setMovedate(o.getMovedate());
				saf.setOutorganid(o.getOutorganid());
				saf.setIsaudit(o.getIsaudit());
				saf.setIsratify(o.getIsratify());
				saf.setMakeorganid(o.getMakeorganid());
				saf.setIsblankout(o.getIsblankout());
				saf.setMakeid(o.getMakeid());
				saf.setInorganid(o.getInorganid());

				als.add(saf);
			}

			request.setAttribute("als", als);
			if (ISAUDIT.equals("no")) {
				DBUserLog.addUserLog(userid, 4, "渠道管理>>打印转仓申请");
			} else {
				DBUserLog.addUserLog(userid, 4, "渠道管理>>打印转仓申请审核");
			}
			request.setAttribute("ISAUDIT", ISAUDIT);
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
