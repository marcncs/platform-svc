package com.winsafe.drp.action.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.entity.AppFactory;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class SelectListOrganAction extends BaseAction {
	private static Logger logger = Logger.getLogger(SelectListOrganAction.class);
	
	private OrganService ao = new OrganService();
	private CountryAreaService cas = new CountryAreaService();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String oid = request.getParameter("OID"); 
		String id = request.getParameter("SID"); 
		int pagesize = 15;
		initdata(request);
		String Province = request.getParameter("Province"); //省
		String City = request.getParameter("City"); //市
		
		try {
			String Condition = " o.sysid like '"
					+ ao.getOrganByID(users.getMakeorganid()).getSysid()
					+ "%' " + " and o.id!='"+oid+"'";
			
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Organ" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String blur = DbUtil.getOrBlur(map, tmpMap, "ID", "OrganName",
					"OMobile", "OECode","bigRegionName","officeName");
			whereSql = whereSql + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			List menuls = ao.getOrgansb(request, pagesize, whereSql);
			List dpt = new ArrayList();

			for (int i = 0; i < menuls.size(); i++) {
				OrganForm rmf = new OrganForm();
				Organ o = (Organ) menuls.get(i);
				rmf.setId(o.getId());
				rmf.setOecode(o.getOecode());
				rmf.setOrganname(o.getOrganname());
				rmf.setIsrepeal(o.getIsrepeal());
				rmf.setProvincename(cas.getCountryAreaName(o.getProvince()==null?0:o.getProvince()));
				rmf.setCityname(cas.getCountryAreaName(o.getCity()));
				rmf.setAreasname(cas.getCountryAreaName(o.getAreas()));
				rmf.setParentid(o.getParentid());
				rmf.setParentidname(ao.getOrganName(o.getParentid()));
				rmf.setRank(o.getRank());
				//区域信息
				rmf.setBigRegionId(o.getBigRegionId());
				rmf.setBigRegionName(o.getBigRegionName());
				rmf.setOfficeId(o.getOfficeId());
				rmf.setOfficeName(o.getOfficeName());
				rmf.setOrganType(o.getOrganType());
				rmf.setOrganModel(o.getOrganModel());
				dpt.add(rmf);
			}

			List list0 = cas.getProvinceObj();

			request.setAttribute("cals", list0);
			request.setAttribute("OID", oid);
			request.setAttribute("sid", id);
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
			request.setAttribute("CityName", cas
					.getCountryAreaName(getInt("City")));
			request.setAttribute("City", request.getParameter("City"));
			request.setAttribute("AreasName", cas
					.getCountryAreaName(getInt("Areas")));
			request.setAttribute("Areas", request.getParameter("Areas"));
			DBUserLog.addUserLog(userid, "系统管理", "机构设置>>列表机构");
			return mapping.findForward("listorgan");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
