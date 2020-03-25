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
import com.winsafe.drp.dao.AppCIntegral;
import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.CountryArea;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectVocationOrderCustomerAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		
//	    Long userid = users.getUserid();
	    AppCustomer ac = new AppCustomer();
	    
		try {
			
			String Condition = " c.makeorganid like '"+users.getMakeorganid()+"%' and isdel=0 and rate=5 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Customer" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String blur = DbUtil.getOrBlur(map, tmpMap, "CName", "CID", "OfficeTel", "Mobile");			

			whereSql = whereSql + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			Object obj[] = (DbUtil.setPager(request, "Customer as c ",
					whereSql, pagesize));

			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];
//			List pls = ac.searchCustomer2(pagesize, whereSql, tmpPgInfo);
			ArrayList sls = new ArrayList();

			Customer c = null;			
			AppCountryArea aca = new AppCountryArea();
			String province = "";
			String city = "";
			String areas = "";
			AppOrgan appo = new AppOrgan();
			AppCIntegral aci = new AppCIntegral();
//			for (int i = 0; i < pls.size(); i++) {
//				c = (Customer) pls.get(i);
//				CustomerForm cu = new CustomerForm();
//				cu.setCid(c.getCid());
//				cu.setCname(c.getCname());			
//				cu.setSpecializeid(c.getSpecializeid());
//				if (c.getProvince() > 0) {
//					province = aca.getAreaByID(
//							Long.valueOf(c.getProvince().toString()))
//							.getAreaname();
//				}
//				if (c.getCity() > 0) {
//					city = aca
//							.getAreaByID(Long.valueOf(c.getCity().toString()))
//							.getAreaname();
//				}
//				if (c.getAreas() > 0) {
//					areas = aca.getAreaByID(
//							Long.valueOf(c.getAreas().toString()))
//							.getAreaname();
//				}
//				cu.setProvincename(province);
//				cu.setCityname(city);
//				cu.setAreasname(areas);
//				cu.setPaymentmode(c.getPaymentmode());
//				cu.setTransportmode(c.getTransportmode());
//				cu.setMembersexname(Internation.getStringByKeyPosition("Sex",
//						request, c.getMembersex(), "global.sys.SystemResource"));
//					cu.setMobile(c.getMobile());
//					cu.setOfficetel(c.getOfficetel());
//					cu.setRate(c.getRate());
//					cu.setRatename(Internation.getStringByKeyPositionDB("PricePolicy",
//							c.getRate()));
//					cu.setIsactivatename(Internation.getStringByKeyPosition("YesOrNo",
//						request, c.getIsactivate(), "global.sys.SystemResource"));
//					cu.setMakeorganid(c.getMakeorganid());
//					cu.setMakeorganidname(appo.getOrganByID(c.getMakeorganid()).getOrganname());
//				cu.setTickettitle(c.getTickettitle());
//				cu.setIntegral(aci.getCIntegralByCID(c.getCid()));
//				sls.add(cu);
//			}

			List list = aca.getProvince();
			ArrayList cals = new ArrayList();

			for (int i = 0; i < list.size(); i++) {
				CountryArea ca = new CountryArea();
				Object ob[] = (Object[]) list.get(i);
//				ca.setId(Long.valueOf(ob[0].toString()));
				ca.setAreaname(ob[1].toString());
//				ca.setParentid(Long.valueOf(ob[2].toString()));
				ca.setRank(Integer.valueOf(ob[3].toString()));
				cals.add(ca);
			}
			request.setAttribute("cals", cals);

			request.setAttribute("sls", sls);
			return mapping.findForward("selectcustomer");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
