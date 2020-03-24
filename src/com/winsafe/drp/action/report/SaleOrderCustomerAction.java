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
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.CountryArea;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganForm;
import com.winsafe.drp.dao.SaleOrder;
import com.winsafe.drp.dao.SaleOrderForm;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DbUtil;

public class SaleOrderCustomerAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try{			
			String Condition=" so.isendcase=1 and so.isblankout=0 " ;
			 Map map = new HashMap(request.getParameterMap());			
		      Map tmpMap = EntityManager.scatterMap(map);
		     
		      String[] tablename={"SaleOrder"};
		      String whereSql = EntityManager.getTmpWhereSql(map, tablename);
		      String blur = DbUtil.getOrBlur(map, tmpMap, "CName","CMobile");

		      String timeCondition = DbUtil.getTimeCondition(map, tmpMap," MakeDate");
		      whereSql = whereSql + blur + timeCondition + Condition; 
		      whereSql = DbUtil.getWhereSql(whereSql); 
		      
			List str= new ArrayList();
			
			AppCountryArea aca = new AppCountryArea();
			AppSaleOrder aso = new AppSaleOrder();
			AppOrgan ao = new AppOrgan();
			List pils = aso.getSaleOrderCustomer(whereSql);
			AppUsers au = new AppUsers();
			double allsum = 0.00;
			SaleOrderForm sof = null;
		      for (int i = 0; i < pils.size(); i++) {
		        sof = new SaleOrderForm();		    	
		        SaleOrder o = (SaleOrder) pils.get(i);
		        sof.setId(o.getId());
		        if(o.getProvince()>0){
		        sof.setProvincename(aca.getAreaByID(
						Integer.valueOf(o.getProvince().toString())).getAreaname());
		        }else{
		        	sof.setProvincename("");
		        }
		        if(o.getCity()>0){
		        	sof.setCityname(aca.getAreaByID(Integer.valueOf(o.getCity().toString()))
						.getAreaname());
		        }else{
		        	sof.setCityname("");
		        }
		        if(o.getAreas()>0){
		        	sof.setAreasname(aca.getAreaByID(Integer.valueOf(o.getAreas().toString()))
						.getAreaname());
		        }else{
		        	sof.setAreasname("");
		        }
		        sof.setCid(o.getCid());
		        sof.setCname(o.getCname());
		        sof.setTotalsum(o.getTotalsum());
		        sof.setMakeorganidname(ao.getOrganByID(o.getMakeorganid()).getOrganname());
		        sof.setMakedate(String.valueOf(o.getMakedate()));
		        sof.setMakeidname(au.getUsersByid(o.getMakeid()).getRealname());
		        str.add(sof);	     
		        allsum += sof.getTotalsum();
		      }			
		      
		      //AppCountryArea aca = new AppCountryArea();
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

				List uls = au.getIDAndLoginName();
				ArrayList als = new ArrayList();
				for (int u = 0; u < uls.size(); u++) {
					UsersBean ubs = new UsersBean();
					Object[] ub = (Object[]) uls.get(u);
					ubs.setUserid(Integer.valueOf(ub[0].toString()));
					ubs.setRealname(ub[2].toString());
					als.add(ubs);
				}
				
//				AppOrgan ao = new AppOrgan();
			     List ols = ao.getAllOrgan();
			     ArrayList alos = new ArrayList();
			     
			     for(int o=0;o<ols.size();o++){
			    	 OrganForm ub = new OrganForm();
			    	 Organ of = (Organ)ols.get(o);
			    	 ub.setId(of.getId());
			    	 ub.setOrganname(of.getOrganname());
			    	 alos.add(ub);
			     }
			     
			     request.setAttribute("alos",alos);
				

				request.setAttribute("als", als);
				request.setAttribute("cals", cals);
				request.setAttribute("allsum", DataFormat.currencyFormat(allsum));

		      request.setAttribute("str", str);
		      return mapping.findForward("saleordertotal");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
