package com.winsafe.drp.action.purchase;

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
import com.winsafe.drp.dao.AppPurchasePlan;
import com.winsafe.drp.dao.PurchasePlan;
import com.winsafe.drp.dao.PurchasePlanForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class PrintPurchasePlanAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = getOrVisitOrgan("pp.makeorganid");
			}

			String Condition = " (pp.makeid=" + userid + " " + visitorgan
					+ ") ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "PurchasePlan" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" PlanDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "KeysContent");
			whereSql = whereSql + blur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppPurchasePlan apa = new AppPurchasePlan();
			List pals = apa.searchPurchasePlan(whereSql);
			ArrayList alpa = new ArrayList();
			for (int i = 0; i < pals.size(); i++) {
				PurchasePlanForm ppf = new PurchasePlanForm();
				PurchasePlan o = (PurchasePlan) pals.get(i);
				ppf.setId(o.getId());
				ppf.setPurchasesort(o.getPurchasesort());
				ppf.setPlandate(o.getPlandate());
				ppf.setMakeorganid(o.getMakeorganid());
				ppf.setMakedeptid(o.getMakedeptid());
				ppf.setMakeid(o.getMakeid());
				ppf.setMakedate(o.getMakedate());
				ppf.setPlanid(o.getPlanid());
				ppf.setIscomplete(o.getIscomplete());
				ppf.setIsaudit(o.getIsaudit());
				ppf.setIsratify(o.getIsratify());

				alpa.add(ppf);
			}

			
			request.setAttribute("alpa", alpa);
			DBUserLog.addUserLog(userid, 5, "产品采购>>打印采购计划");
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
