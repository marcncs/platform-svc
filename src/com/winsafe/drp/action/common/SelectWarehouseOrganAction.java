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
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class SelectWarehouseOrganAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 初始化
		int pagesize = 20; 
		initdata(request);
		CountryAreaService cas = new CountryAreaService();
		AppOrgan ao = new AppOrgan();
		
		String Province = request.getParameter("Province"); //省
		String City = request.getParameter("City"); //市
		String Condition = " wv.wid=w.id and w.makeorganid=o.id and wv.userid="+users.getUserid()+" ";

		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String[] tablename = { "Organ" };
		String whereSql = EntityManager.getTmpWhereSql(map, tablename);
		whereSql = whereSql.replace("province", "o.province").replace("city", "o.city").replace("areas", "o.areas");
		String blur = DbUtil.getOrBlur(map, tmpMap, "o.ID", "o.OrganName",
				"o.OMobile", "o.OECode");
		whereSql = whereSql + blur + Condition;
		whereSql = DbUtil.getWhereSql(whereSql);

		List menuls = ao.getWarehouseOrgan(request, pagesize, whereSql);
		List dpt = new ArrayList();

		for (int i = 0; i < menuls.size(); i++) {
			OrganForm rmf = new OrganForm();
			Map o = (Map) menuls.get(i);
			rmf.setId(String.valueOf(o.get("id")));
			rmf.setOecode(String.valueOf(o.get("oecode")));
			rmf.setOrganname(String.valueOf(o.get("organname")));
			rmf.setOtel(String.valueOf(o.get("otel")));
			rmf.setOmobile(String.valueOf(o.get("omobile")));
			rmf.setOaddr(String.valueOf(o.get("oaddr")));
			rmf.setOfax(String.valueOf(o.get("ofax")));
			rmf.setOemail(String.valueOf(o.get("oemail")));
			rmf.setProvincename(cas.getCountryAreaName(Integer.valueOf(String.valueOf(o.get("province")))));
			rmf.setCityname(cas.getCountryAreaName(Integer.valueOf(String.valueOf(o.get("city")))));
			rmf.setAreasname(cas.getCountryAreaName(Integer.valueOf(String.valueOf(o.get("areas")))));
			rmf.setParentid(String.valueOf(o.get("parentid")));
			if (!rmf.getParentid().equals("0")) {
				Organ o1 = ao.getOrganByID(rmf.getParentid());
				rmf.setParentidname(o1==null?"":o1.getOrganname());
			}
			dpt.add(rmf);
		}

		List list = cas.getProvinceObj();
		request.setAttribute("cals", list);
		request.setAttribute("dpt", dpt);
		
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

		return mapping.findForward("success");
	}
}
