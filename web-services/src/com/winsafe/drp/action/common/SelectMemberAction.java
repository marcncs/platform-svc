package com.winsafe.drp.action.common;

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
import com.winsafe.drp.dao.AppMemberGrade;
import com.winsafe.drp.dao.AppObjIntegral;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.CustomerForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.drp.server.OrganService;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class SelectMemberAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
	
	    AppCustomer ac = new AppCustomer();
	    
		super.initdata(request);
		try{
			
			String Condition = " c.makeorganid like '"+users.getMakeorganid()+"%' and isdel=0 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Customer" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String blur = DbUtil.getOrBlur(map, tmpMap, "CName", "CID", "OfficeTel", "Mobile");			

			whereSql = whereSql + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			List pls = ac.searchCustomer(request, pagesize, whereSql);
			ArrayList sls = new ArrayList();

			Customer c = null;			
			CountryAreaService aca = new CountryAreaService();
			AppMemberGrade amg = new AppMemberGrade();
			OrganService appo = new OrganService();
			AppObjIntegral aoi = new AppObjIntegral();
			for (int i = 0; i < pls.size(); i++) {
				c = (Customer) pls.get(i);
				CustomerForm cu = new CustomerForm();
				cu.setCid(c.getCid());
				cu.setCname(c.getCname());			
				cu.setSpecializeid(c.getSpecializeid());	
				cu.setProvincename(aca.getCountryAreaName(c.getProvince()));
				cu.setCityname(aca.getCountryAreaName(c.getCity()));
				cu.setAreasname(aca.getCountryAreaName(c.getAreas()));
				cu.setTransportmode(c.getTransportmode());
				cu.setMembersexname(Internation.getStringByKeyPosition("Sex",
						request, c.getMembersex(), "global.sys.SystemResource"));
					cu.setMobile(c.getMobile());
					cu.setOfficetel(c.getOfficetel());
					cu.setRate(c.getRate());
					cu.setRatename(amg.getMemberGradeByID(c.getRate()).getGradename());
					cu.setIsactivatename(Internation.getStringByKeyPosition("YesOrNo",
						request, c.getIsactivate(), "global.sys.SystemResource"));
					cu.setMakeorganid(c.getMakeorganid());
					cu.setMakeorganidname(appo.getOrganName(c.getMakeorganid()));
				cu.setTickettitle(c.getTickettitle());
				cu.setIntegral(aoi.getBalance(c.getCid(),1));
				cu.setDetailaddr(c.getDetailaddr());
				sls.add(cu);
			}

			List list = aca.getProvinceObj();
			
			request.setAttribute("cals", list);

			request.setAttribute("sls", sls);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
