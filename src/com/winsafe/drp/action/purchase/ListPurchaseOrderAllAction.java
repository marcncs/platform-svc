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

import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppPurchaseInquire;
import com.winsafe.drp.dao.AppPurchaseOrder;
import com.winsafe.drp.dao.PurchaseOrder;
import com.winsafe.drp.dao.PurchaseOrderForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListPurchaseOrderAllAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;

		String isblankoutselect = "";
		isblankoutselect = Internation.getSelectTagByKeyAll("YesOrNo", request,
				"IsBlankOut", true, null);
		String approvestatusselect = Internation.getSelectTagByKeyAll(
				"ApproveStatus", request, "ApproveStatus", true, null);

		try {
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "PurchaseOrder" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" ReceiveDate");
			whereSql = whereSql + timeCondition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = (DbUtil.setDynamicPager(request,
					"PurchaseOrder as po", whereSql, pagesize, "subCondition"));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppPurchaseOrder apb = new AppPurchaseOrder();
			AppCustomer apv = new AppCustomer();
			List pbls = apb.searchPurchaseOrder(pagesize, whereSql, tmpPgInfo);
			ArrayList alpb = new ArrayList();
			PurchaseOrder bill = null;
			for (int i = 0; i < pbls.size(); i++) {
				bill = (PurchaseOrder) pbls.get(i);
				PurchaseOrderForm pbf = new PurchaseOrderForm();
				pbf.setId(bill.getId());
				pbf.setProvidename(apv.getCustomer(bill.getPid()).getCname());
				pbf.setTotalsum(bill.getTotalsum());
				pbf.setMakedate(DateUtil.formatDate(bill.getMakedate()));
				pbf.setReceivedate(DateUtil.formatDate(bill.getReceivedate()));
				pbf.setIsrefername(Internation.getStringByKeyPosition(
						"IsRefer", request, bill.getIsrefer(),
						"global.sys.SystemResource"));
				pbf.setPaymentmodename(Internation.getStringByPayPositionDB(bill.getPaymentmode()));
				pbf.setApprovestatusname(Internation.getStringByKeyPosition(
						"ApproveStatus", request, bill.getApprovestatus(),
						"global.sys.SystemResource"));
				pbf.setIsblankoutname(Internation.getStringByKeyPosition(
						"YesOrNo", request, bill.getIsblankout(),
						"global.sys.SystemResource"));
				alpb.add(pbf);
			}

			
			AppPurchaseInquire apa = new AppPurchaseInquire();
			int wic = apa.waitInputPurchaseInquireCount();

			request.setAttribute("alpb", alpb);
			request.setAttribute("isblankoutselect", isblankoutselect);
			request.setAttribute("approvestatusselect", approvestatusselect);
			request.setAttribute("wic", wic);
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			//DBUserLog.addUserLog(userid, "列表所有采购订单");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
