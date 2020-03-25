package com.winsafe.drp.action.assistant;

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
import com.winsafe.drp.dao.AppOIntegral;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.CountryArea;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectMultiOrganAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		UsersBean users = UserManager.getUser(request);
//	    Long userid = users.getUserid();
	    AppOrgan ac = new AppOrgan();
	    
		try {
			
			//String Condition = " c.makeorganid like '"+users.getMakeorganid()+"%' and isdel=0 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Organ" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String blur = DbUtil.getOrBlur(map, tmpMap, "ID", "OrganName", "OTel");			

			whereSql = whereSql + blur;
			whereSql = DbUtil.getWhereSql(whereSql); 

			Object obj[] = (DbUtil.setPager(request, "Organ as o ",
					whereSql, pagesize));

			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];
//			List pls = ac.getOrgan(pagesize, whereSql, tmpPgInfo);
			ArrayList sls = new ArrayList();

			Organ c = null;			
			AppCountryArea aca = new AppCountryArea();
			String province = "";
			String city = "";
			String areas = "";
			AppOIntegral aoi = new AppOIntegral();
//			for (int i = 0; i < pls.size(); i++) {
//				c = (Organ) pls.get(i);
//				OrganForm cu = new OrganForm();
//				cu.setId(c.getId());
//				cu.setOrganname(c.getOrganname());
//				cu.setOtel(c.getOtel());
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
//				cu.setOaddr(c.getOaddr());
//				cu.setIntegral(aoi.getOIntegralByOID(c.getId()));
//				sls.add(cu);
//			}

			List list = aca.getProvince();// 只能在该用户负责的区域添加
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
			
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
