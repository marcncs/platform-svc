package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchasePlan;
import com.winsafe.drp.dao.PurchasePlan;
import com.winsafe.drp.dao.PurchasePlanForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectPurchasePlanAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		try {
			String findCondition = " pp.isratify=1 and pp.iscomplete=0 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "PurchasePlan" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" PlanDate");
			whereSql = whereSql + timeCondition + findCondition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = (DbUtil.setPager(request, " PurchasePlan as pp ",
					whereSql, pagesize));

			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppPurchasePlan app = new AppPurchasePlan();
			List pals = app.searchPurchasePlan(request,pagesize, whereSql);
			ArrayList alpa = new ArrayList();

			for (int i = 0; i < pals.size(); i++) {
				PurchasePlanForm ppf = new PurchasePlanForm();
				PurchasePlan o = (PurchasePlan) pals.get(i);
				ppf.setId(o.getId());
				ppf.setPurchasesort(o.getPurchasesort());
				ppf.setPlandate(o.getPlandate());
				ppf.setPlandept(o.getPlandept());
				ppf.setPlanid(o.getPlanid());

				alpa.add(ppf);
			}

			request.setAttribute("alpa", alpa);

			return mapping.findForward("toinput");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
