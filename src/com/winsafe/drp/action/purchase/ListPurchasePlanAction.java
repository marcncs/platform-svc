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

public class ListPurchasePlanAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;

		super.initdata(request);
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = " or instr(pp.makeorganid,'"
						+ users.getVisitorgan() + "')>=1";
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
			List pals = apa.searchPurchasePlan(request, pagesize, whereSql);
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
			request.setAttribute("IsAudit", map.get("IsAudit"));
			request.setAttribute("IsRatify", map.get("IsRatify"));
			request.setAttribute("BeginDate", request.getParameter("BeginDate"));
			request.setAttribute("EndDate", request.getParameter("EndDate"));
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("PlanID", request.getParameter("PlanID"));
			
			
			
			DBUserLog.addUserLog(userid, 5, "产品采购>>列表采购计划");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
