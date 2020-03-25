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
import com.winsafe.drp.dao.AppCustomerSort;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.SaleOrder;
import com.winsafe.drp.dao.SaleOrderForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class SelectSaleOrderAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		// String keyword = request.getParameter("KeyWord");
		String cid = request.getParameter("cid");
		request.setAttribute("cid", cid);
		super.initdata(request);
		try {

			//String Condition = " so.makeorganid like '"+users.getMakeorganid()+"%' and so.cid='"+cid+"' and so.ismaketicket=0 ";
			String Condition = " (so.makeid="+userid+" "+getOrVisitOrgan("so.makeorganid")+") and so.cid='"+cid+"' and so.ismaketicket=0 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = {"SaleOrder"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
			"MakeDate");
			String blur = DbUtil.getBlur(map, tmpMap, "ID");

			whereSql = whereSql + blur + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			AppSaleOrder aso = new AppSaleOrder();
			List pls = aso.getSaleOrderAll(request, pagesize, whereSql);
			ArrayList sls = new ArrayList();

			for (int i = 0; i < pls.size(); i++) {
				SaleOrderForm sof = new SaleOrderForm();
				SaleOrder o = (SaleOrder) pls.get(i);
				sof.setId(o.getId());
				sof.setTotalsum(o.getTotalsum());
				sof.setMakedate(String.valueOf(o.getMakedate()).substring(0,10));
				sls.add(sof);
			}
			AppCustomerSort appcs = new AppCustomerSort();
			List sortlist = appcs.getSortByUserid(userid);

			request.setAttribute("sls", sls);
			request.setAttribute("sortlist", sortlist);
			return mapping.findForward("selectso");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
