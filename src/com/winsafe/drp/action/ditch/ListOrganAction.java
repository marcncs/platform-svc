package com.winsafe.drp.action.ditch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap; 
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan; 
import com.winsafe.drp.dao.CountryArea;
import com.winsafe.drp.dao.OrganForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class ListOrganAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 15;
		super.initdata(request);
		String Province = request.getParameter("Province"); //省
		String City = request.getParameter("City"); //市
		try {
			Map<String, Object> param = new LinkedHashMap<String, Object>();
//			String Condition = " o.sysid like '" + users.getOrgansysid()
//					+ "%' ";
			String Condition = ""; 
			if(DbUtil.isDealer(users)) {
				Condition = " o.parentid ='"+users.getMakeorganid()+"' ";
			} else {
				Condition = DbUtil.getWhereCondition(users,"o");
			}
			

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Organ" };
			String whereSql = EntityManager.getTmpWhereSqlForSql(map, tablename, param);
			String whereLinkMan = "";
			String linkman = (String)map.get("LinkMan"); 
			String blur = DbUtil.getOrBlurForSql(map, tmpMap, param, "ID", "OrganName",
					"OMobile", "OECode");
			if(!StringUtil.isEmpty(linkman)){
				whereLinkMan = " and id in (select cid from Olinkman where name like ?) ";
				param.put("olinkname", "%"+linkman+"%");
			}
			whereSql = whereSql + blur + Condition +whereLinkMan;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppOrgan ao = new AppOrgan();
			CountryAreaService aca = new CountryAreaService();

			List<Map<String,String>> menuls = ao.getOrganList(request, pagesize, whereSql, param);
			List dpt = new ArrayList();
			for (int i = 0; i < menuls.size(); i++) {
				OrganForm rmf = new OrganForm();
				Map<String,String> organMap = menuls.get(i);
				MapUtil.mapToObject(organMap, rmf);
				
				rmf.setProvincename(aca.getCountryAreaName(rmf.getProvince()));
				rmf.setCityname(aca.getCountryAreaName(rmf.getCity()));
				rmf.setAreasname(aca.getCountryAreaName(rmf.getAreas()));
				if (!rmf.getParentid().equals("0")) {
					rmf.setParentidname(ao.getOrganByID(rmf.getParentid())
							.getOrganname());
				}
				dpt.add(rmf);
			}

			List list0 = aca.getProvince();
			ArrayList cals = new ArrayList();
			for (int a = 0; a < list0.size(); a++) {
				CountryArea ca = new CountryArea();
				Object ob[] = (Object[]) list0.get(a);
				ca.setId(Integer.valueOf(ob[0].toString()));
				ca.setAreaname(ob[1].toString());
				ca.setParentid(Integer.valueOf(ob[2].toString()));
				ca.setRank(Integer.valueOf(ob[3].toString()));
				cals.add(ca);
			}
			request.setAttribute("cals", cals);
			
			CountryAreaService cas = new CountryAreaService();
			List citysList = new ArrayList();
			List areasList = new ArrayList();
			if(!StringUtil.isEmpty(Province)){
				citysList = cas.getCountryAreaByParentid(Integer.valueOf(Province));
			}
			if(!StringUtil.isEmpty(City)){
				areasList = cas.getCountryAreaByParentid(Integer.valueOf(City));
			}
			request.setAttribute("Citys", citysList);
			request.setAttribute("Areass", areasList);
			
			request.setAttribute("dpt", dpt);

			request.setAttribute("Province", request.getParameter("Province"));
			request.setAttribute("CityName", aca
					.getCountryAreaName(getInt("City")));
			request.setAttribute("City", request.getParameter("City"));
			request.setAttribute("AreasName", aca
					.getCountryAreaName(getInt("Areas")));
			request.setAttribute("Areas", request.getParameter("Areas"));

			return mapping.findForward("listorgan");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
