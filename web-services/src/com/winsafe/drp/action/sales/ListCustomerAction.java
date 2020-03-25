package com.winsafe.drp.action.sales;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppCustomerSort;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.CountryArea;
import com.winsafe.drp.dao.CustomerForm;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

//import java.text.SimpleDateFormat;

public class ListCustomerAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int pagesize = 10;
		try {
			
			  initdata(request);

			String Condition = " c.kind=1 and c.isdel=0 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = {"Customer"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
			String blur = DbUtil.getOrBlur(map, tmpMap, "CName", "CPYCode");
			whereSql = whereSql + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = DbUtil.setDynamicPager(request,
					"Customer as c", whereSql, pagesize,
					"CustomerCondition");
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];
			AppCustomer appcustomer = new AppCustomer();
			List usList = appcustomer.searchCustomerNew(pagesize, whereSql,
					tmpPgInfo);
			ArrayList customerList = new ArrayList();
			for (int t = 0; t < usList.size(); t++) {
				CustomerForm cf = new CustomerForm();
				// customer=(Customer)usList.get(t);
				Object[] ob = (Object[]) usList.get(t);
				cf.setCid(String.valueOf(ob[0]));
				cf.setCname(String.valueOf(ob[1]));
				cf.setYauldname(Internation.getStringByKeyPosition("Yauld",
						request, Integer.parseInt(ob[2].toString()),
						"global.sys.SystemResource"));
				cf.setOfficetel(String.valueOf(ob[3]));
				cf.setCustomertypename(Internation.getStringByKeyPosition(
						"CustomerType", request, Integer.parseInt(ob[4]
								.toString()), "global.sys.SystemResource"));
				cf.setCustomerstatusname(Internation.getStringByKeyPosition(
						"CustomerStatus", request, Integer.parseInt(ob[5]
								.toString()), "global.sys.SystemResource"));
				cf.setSourcename(Internation.getStringByKeyPositionDB("Source",
						Integer.valueOf(ob[6].toString())));
				cf.setMakedate(ob[7].toString().substring(0, 10));

				customerList.add(cf);

			}
			String customertypeselect = Internation.getSelectTagByKeyAll(
					"CustomerType", request, "CustomerType", true, null);
			String customerstatusselect = Internation.getSelectTagByKeyAll(
					"CustomerStatus", request, "CustomerStatus", true, null);
			String vocationselect = Internation.getSelectTagByKeyAllDB(
					"Vocation", "Vocation", true);

			request.setAttribute("customertypeselect", customertypeselect);
			request.setAttribute("customerstatusselect", customerstatusselect);
			request.setAttribute("vocationselect", vocationselect);

			AppCountryArea aca = new AppCountryArea();
			List list = aca.getProvince();

			ArrayList cals = new ArrayList();

			for (int i = 0; i < list.size(); i++) {
				CountryArea ca = new CountryArea();
				Object ob[] = (Object[]) list.get(i);
				ca.setId(Integer.valueOf(ob[0].toString()));
				ca.setAreaname(ob[1].toString());
				ca.setParentid(Integer.valueOf(ob[2].toString()));
				ca.setRank(Integer.valueOf(ob[3].toString()));
				cals.add(ca);
			}

			AppUsers au = new AppUsers();
			List uls = au.getIDAndLoginName();
			ArrayList als = new ArrayList();
			for (int u = 0; u < uls.size(); u++) {
				UsersBean ubs = new UsersBean();
				Object[] ub = (Object[]) uls.get(u);
				ubs.setUserid(Integer.valueOf(ub[0].toString()));
				ubs.setRealname(ub[2].toString());
				als.add(ubs);
			}
			AppCustomerSort appcs = new AppCustomerSort();
			List sortlist = appcs.getSortByUserid(userid);
			String isactivateselect = Internation.getSelectTagByKeyAll(
					"YesOrNo", request, "IsActivate", true, null);

			request.setAttribute("als", als);
			request.setAttribute("isactivateselect", isactivateselect);
			request.setAttribute("sortlist", sortlist);

			request.setAttribute("cals", cals);
			request.setAttribute("usList", customerList);

			DBUserLog.addUserLog(userid, "列表客户");
			return mapping.findForward("customerlist");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
