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

import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppPurchaseInquire;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PurchaseInquire;
import com.winsafe.drp.dao.PurchaseInquireForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListPurchaseInquireForPlanAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 5;

		String strppid = request.getParameter("ppid");
		if (strppid == null) {
			strppid = (String) request.getSession().getAttribute("ppid");
		}
		String ppid = strppid;

		String isauditselect = Internation.getSelectTagByKeyAll("YesOrNo",
				request, "IsAudit", true, null);

		try {
			String Condition = " pi.ppid='" + ppid + "'";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "PurchaseInquire" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			StringBuffer buf = new StringBuffer();

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = (DbUtil
					.setDynamicPager(request, "PurchaseInquire as pi",
							whereSql, pagesize, "subCondition"));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppUsers au = new AppUsers();
			AppPurchaseInquire api = new AppPurchaseInquire();
			AppProvider ap = new AppProvider();
			List pils = api.getPurchaseInquire(request, pagesize, whereSql);
			ArrayList alpi = new ArrayList();
			for (int i = 0; i < pils.size(); i++) {
				PurchaseInquireForm pif = new PurchaseInquireForm();
				PurchaseInquire o = (PurchaseInquire) pils.get(i);
				pif.setId(o.getId());
				pif.setInquiretitle(o.getInquiretitle());
				pif.setProvidename(ap.getProviderByID(o.getPid()).getPname());
				pif.setPlinkman(o.getPlinkman());
				pif.setMakedate(o.getMakedate());
				pif.setValiddate(o.getValiddate());
				pif.setMakeid(o.getMakeid());
				pif.setIsaudit(o.getIsaudit());
				alpi.add(pif);
			}

			request.setAttribute("alpi", alpi);
			request.setAttribute("isauditselect", isauditselect);
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
