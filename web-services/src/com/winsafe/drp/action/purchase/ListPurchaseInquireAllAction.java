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
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppPurchaseInquire;
import com.winsafe.drp.dao.PurchaseInquire;
import com.winsafe.drp.dao.PurchaseInquireForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListPurchaseInquireAllAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 5;

		super.initdata(request);
		try {

			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = " or instr(pi.makeorganid,'" + users.getVisitorgan()
						+ "')>=1";
			}

			String Condition = " (pi.makeid=" + userid + " " + visitorgan
					+ ") ";

			// String Condition = "pi.makeid like '"+userid+"%' ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "PurchaseInquire" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "KeysContent");
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + blur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppPurchaseInquire api = new AppPurchaseInquire();
			AppProvider ap = new AppProvider();
			List pils = api.getPurchaseInquire(request, pagesize, whereSql);
			ArrayList alpi = new ArrayList();
			for (int i = 0; i < pils.size(); i++) {
				PurchaseInquireForm pif = new PurchaseInquireForm();
				PurchaseInquire o = (PurchaseInquire) pils.get(i);
				pif.setId(o.getId());
				pif.setInquiretitle(o.getInquiretitle());
				pif.setProvidename(ap.getProviderByID(o.getPid())==null?"":ap.getProviderByID(o.getPid()).getPname());
				pif.setPlinkman(o.getPlinkman());
				pif.setMakeorganid(o.getMakeorganid());
				pif.setMakedeptid(o.getMakedeptid());
				pif.setMakedate(o.getMakedate());
				pif.setValiddate(o.getValiddate());
				pif.setMakeid(o.getMakeid());
				pif.setIsaudit(o.getIsaudit());
				alpi.add(pif);
			}

			request.setAttribute("alpi", alpi);
			request.setAttribute("IsAudit", map.get("IsAudit"));
			request.setAttribute("PID", request.getParameter("PID"));
			request.setAttribute("BeginDate", request.getParameter("BeginDate"));
			request.setAttribute("EndDate", request.getParameter("EndDate"));
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("MakeDeptID", request.getParameter("MakeDeptID"));

			DBUserLog.addUserLog(userid,2,"采购管理>>列表所有采购询价");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
