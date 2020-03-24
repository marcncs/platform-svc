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
import com.winsafe.drp.dao.AppPurchaseBill;
import com.winsafe.drp.dao.AppPurchasePlan;
import com.winsafe.drp.dao.PurchaseBill;
import com.winsafe.drp.dao.PurchaseBillForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListPurchaseBillAllAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);
		try {
			String visitorgan = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				visitorgan = this.getOrVisitOrgan("pb.makeorganid");
			}

			String Condition = " (pb.makeid=" + userid + " " + visitorgan
					+ ") ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "PurchaseBill" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" ReceiveDate");
			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");
			whereSql = whereSql + blur + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppPurchaseBill apb = new AppPurchaseBill();
			List<PurchaseBill> pbls = apb.getPurchaseBill(request, pagesize, whereSql);
			List<PurchaseBillForm> alpb = new ArrayList<PurchaseBillForm>();
			for (int i = 0; i < pbls.size(); i++) {
				PurchaseBillForm pbf = new PurchaseBillForm();
				PurchaseBill o = (PurchaseBill) pbls.get(i);
				pbf.setId(o.getId());
				pbf.setPname(o.getPname());
				pbf.setTotalsum(o.getTotalsum());
				pbf.setMakeorganid(o.getMakeorganid());
				pbf.setMakedate(o.getMakedate());
				pbf.setMakeid(o.getMakeid());
				pbf.setMakedeptid(o.getMakedeptid());
				pbf.setReceivedate(o.getReceivedate());
				pbf.setIsaudit(o.getIsaudit());
				pbf.setIsratify(o.getIsratify());
				pbf.setPaymode(o.getPaymode());
				alpb.add(pbf);
			}

			
			AppPurchasePlan apa = new AppPurchasePlan();
			int wic = apa.waitInputPurchasePlanCount();

			request.setAttribute("alpb", alpb);
			request.setAttribute("wic", wic);
			request.setAttribute("IsAudit", map.get("IsAudit"));
			request.setAttribute("PID", request.getParameter("PID"));
			request.setAttribute("BeginDate", request.getParameter("BeginDate"));
			request.setAttribute("EndDate", request.getParameter("EndDate"));
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("MakeDeptID", request.getParameter("MakeDeptID"));
			request.setAttribute("IsRatify", map.get("IsRatify"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));

			 DBUserLog.addUserLog(userid,2,"产品采购>>列表所有采购单");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
