package com.winsafe.drp.action.sales;

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
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.ShipmentBillForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListShipmentBillByCustomerAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		String strCid = request.getParameter("Cid");
		if (strCid == null) {
			strCid = (String) request.getSession().getAttribute("cid");
		}
		String cid = strCid;
		String isauditselect = Internation.getSelectTagByKeyAll("YesOrNo",
				request, "IsAudit", true, null);

		
		initdata(request);
		try {

			String Condition = " sb.cid='" + cid + "' ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "ShipmentBill" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" RequireDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = DbUtil.setDynamicPager(request,
					"ShipmentBill as sb ", whereSql, pagesize, "subCondition");
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppCustomer ac = new AppCustomer();
			AppShipmentBill asb = new AppShipmentBill();
			List pils = asb.searchShipmentBill(request,pagesize, whereSql);
			ArrayList alsb = new ArrayList();
			ShipmentBill sb = null;
			for (int i = 0; i < pils.size(); i++) {
				sb = (ShipmentBill) pils.get(i);
				ShipmentBillForm sbf = new ShipmentBillForm();
				sbf.setId(sb.getId());
				Customer c = ac.getCustomer(sb.getCid());
				if (c != null) {
					sbf.setCname(c.getCname());
				}
				sbf.setLinkman(sb.getLinkman());
				sbf.setTel(sb.getTel());
				sbf.setTransportmodename(Internation.getStringByKeyPositionDB(
						"TransportMode", sb.getTransportmode()));
				sbf.setRequiredate(DateUtil.formatDate(sb.getRequiredate()));
				sbf.setIsauditname(Internation.getStringByKeyPosition(
						"YesOrNo", request, sb.getIsaudit(),
						"global.sys.SystemResource"));
				alsb.add(sbf);
			}

			
			AppSaleOrder apa = new AppSaleOrder();
			int wic = apa.waitInputSaleShipmentCount();

			request.setAttribute("wic", wic);
			request.setAttribute("alsb", alsb);
			request.setAttribute("isauditselect", isauditselect);

			DBUserLog.addUserLog(userid, "列表销售出库单");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
