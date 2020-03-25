package com.winsafe.drp.action.report;

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

import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppSaleOrderDetail;
import com.winsafe.drp.dao.CustomerAreasObject;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class CustomerAreasAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		try{
			String pid = request.getParameter("pid");
			String b = request.getParameter("b");
			String e = request.getParameter("e");
			String Condition = " so.id = sod.soid and so.isendcase=1 ";
			Map map = new HashMap();
			map.put("BeginDate", b);
			map.put("EndDate", e);
			map.put("ProductID", pid);
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = {"SaleOrder","SaleOrderDetail"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"MakeDate");
			
			whereSql = whereSql +  timeCondition +Condition;
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			AppSaleOrderDetail asod = new AppSaleOrderDetail();
			AppCountryArea aca = new AppCountryArea();
			AppCustomer ac = new AppCustomer();
			List cls = asod.getCustomerAreas(whereSql);
			ArrayList als = new ArrayList();
			for(int i=0;i<cls.size();i++){
				CustomerAreasObject cao = new CustomerAreasObject();
				Object[] o = (Object[])cls.get(i);
				cao.setCid(o[0].toString());
				cao.setCname(String.valueOf(o[1]));
				String province = "";
	            String city = "";
	            String areas = "";
	            int provinceid = 0;
	            int cityid = 0;
	            int areasid = 0;
	            provinceid = ac.getCustomer(cao.getCid()).getProvince();
	            cityid = ac.getCustomer(cao.getCid()).getCity();
	            areasid = ac.getCustomer(cao.getCid()).getAreas();
	            if (provinceid>0) {
	                province = aca.getAreaByID(Integer.valueOf(provinceid)).getAreaname();
	            }
	            if (cityid >0) {
	                city = aca.getAreaByID(Integer.valueOf(cityid)).getAreaname();
	            }
	            if (areasid>0) {
	                areas = aca.getAreaByID(Integer.valueOf(areasid)).
	                       getAreaname();
	            }
				cao.setProvince(province);
				cao.setCity(city);
				cao.setAreas(areas);
				cao.setQuantity(Double.valueOf(o[2].toString()));
				als.add(cao);
			}
			
			request.setAttribute("als", als);
			return mapping.findForward("customerareas");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
