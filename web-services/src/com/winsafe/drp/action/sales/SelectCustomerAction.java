package com.winsafe.drp.action.sales;

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
import com.winsafe.drp.dao.AppCustomerSort;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectCustomerAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		// String keyword = request.getParameter("KeyWord");
		
		initdata(request);

		try {

			String Condition = " c.makeorganid like '"+users.getMakeorganid()+"%' and isdel=0 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "Customer" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String blur = DbUtil.getOrBlur(map, tmpMap, "CName", "CPYCode", "OfficeTel", "Mobile");

			whereSql = whereSql + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			Object obj[] = (DbUtil.setPager(request, "Customer as c ",
					whereSql, pagesize));

			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];
			AppCustomer ac = new AppCustomer();
			List sls = ac.searchCustomer(request, pagesize, whereSql);
//			ArrayList sls = new ArrayList();
//
//			Customer c = null;
//			for (int i = 0; i < pls.size(); i++) {
//				c = (Customer) pls.get(i);
//				CustomerUser cu = new CustomerUser();
//				cu.setCid(c.getCid());
//				cu.setCname(c.getCname());
//				cu.setOfficetel(c.getOfficetel());
//				sls.add(cu);
//			}
			AppCustomerSort appcs = new AppCustomerSort();
			List sortlist = appcs.getSortByUserid(userid);

			request.setAttribute("sls", sls);
			request.setAttribute("sortlist", sortlist);
			return mapping.findForward("selectcustomer");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
