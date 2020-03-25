package com.winsafe.drp.action.finance;

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
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.dao.ReceivableForm;
import com.winsafe.drp.dao.ReceivableObject;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ListReceivableAllAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);

		try {
			String gz = request.getParameter("GreatZero");
			if (gz == null || gz.equals("")) {
				gz = "0";
			}

			String Condition = " (r.makeid='" + userid + "' "
					+ getOrVisitOrgan("r.makeorganid") + ") ";

			if (gz.equals("1")) {
				Condition += " and (r.receivablesum-r.alreadysum)!=0 and ";
			}

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Receivable" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "ID", "BillNo");
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + timeCondition + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppReceivable ar = new AppReceivable();
			String receivabledescribe = "";
			List pbls = ar.getReceivable(request, pagesize, whereSql);
			ArrayList alpl = new ArrayList();
			AppReceivableObject aro = new AppReceivableObject();
			
			for (int i = 0; i < pbls.size(); i++) {
				ReceivableForm rf = new ReceivableForm();
				Receivable o = (Receivable) pbls.get(i);
				rf.setId(o.getId());
				rf.setRoid(o.getRoid());
				ReceivableObject ro = aro.getReceivableObjectByIDOrgID(o
						.getRoid(), o.getMakeorganid());
				if (ro != null) {
					rf.setReceivableobjectname(ro.getPayer());
				} else {
					rf.setReceivableobjectname("");
				}
				rf.setSettlementsum(o.getReceivablesum());
				rf.setReceivablesum(o.getReceivablesum() - o.getAlreadysum());
				rf.setBillno(o.getBillno());
				if (rf.getBillno().length() > 0) {
					rf.setBn(rf.getBillno().substring(0, 2));
				} else {
					rf.setBn("");
				}
				receivabledescribe = o.getReceivabledescribe();
				rf.setReceivabledescribe(receivabledescribe.length() > 20 ? receivabledescribe
								.substring(0, 17)
								+ "..."
								: receivabledescribe);
				rf.setMakeorganid(o.getMakeorganid());
				rf.setMakedate(String.valueOf(o.getMakedate()).substring(
								0, 10));
				rf.setAwakedate(o.getAwakedate());
				int a = 0;
				if (o.getAwakedate() != null) {
					a = DateUtil.getDayDifference(DateUtil
							.getCurrentDateString(), DateUtil.formatDate(o
							.getAwakedate()));
				}
				if (a > 0) {
					rf.setOverage(a);
				}

				alpl.add(rf);
			}

			request.setAttribute("alpl", alpl);

			 DBUserLog.addUserLog(userid,9,"账务管理>>列表应收款");
			return mapping.findForward("receivableall");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
