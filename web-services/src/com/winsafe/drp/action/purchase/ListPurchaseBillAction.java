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
import com.winsafe.drp.dao.PurchaseBill;
import com.winsafe.drp.dao.PurchaseBillForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListPurchaseBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);
		String strpid = request.getParameter("pid");

		if (strpid == null) {
			strpid = (String) request.getSession().getAttribute("pid");
		}
		String pid = strpid;
		request.getSession().setAttribute("pid", pid);
		
		try {
			String Condition = " pb.pid='" + pid + "' ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "PurchaseBill" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" ReceiveDate");
			whereSql = whereSql + timeCondition +Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppPurchaseBill apb = new AppPurchaseBill();
			List pbls = apb.getPurchaseBill(request, pagesize, whereSql);
			ArrayList alpb = new ArrayList();
			for (int i = 0; i < pbls.size(); i++) {
				PurchaseBillForm pbf = new PurchaseBillForm();
				PurchaseBill o = (PurchaseBill) pbls.get(i);
				pbf.setId(o.getId());
				pbf.setPname(o.getPname());
				pbf.setTotalsum(o.getTotalsum());
				pbf.setMakedate(o.getMakedate());
				pbf.setReceivedate(o.getReceivedate());
				pbf.setIsaudit(o.getIsaudit());
				pbf.setIsratify(o.getIsratify());
				alpb.add(pbf);
			}


			request.setAttribute("alpb", alpb);
		
			request.setAttribute("IsAudit", map.get("IsAudit"));
			request.setAttribute("PID", pid);
			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("MakeID", request.getParameter("MakeID"));
			request.setAttribute("MakeDeptID", request.getParameter("MakeDeptID"));
			request.setAttribute("IsRatify", map.get("IsRatify"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			 DBUserLog.addUserLog(userid,2,"列表采购单");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
