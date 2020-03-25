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

public class SelectChildOrganAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20; 

		super.initdata(request);
		try{
			String oid = request.getParameter("OID");
			System.out.println(oid);
			String Condition = " o.isrepeal=0 and o.parentid="+oid;

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Organ" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String blur = DbUtil.getOrBlur(map, tmpMap, "ID", "OrganName",
					"OMobile", "OECode","SysID");
			whereSql = whereSql + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			CountryAreaService aca = new CountryAreaService();
			AppOrgan ao = new AppOrgan();
			List menuls = ao.getOrgan(request, pagesize, whereSql);
			List dpt = new ArrayList();

			for (int i = 0; i < menuls.size(); i++) {
				OrganForm rmf = new OrganForm();
				Organ o = (Organ) menuls.get(i);
				rmf.setId(o.getId());
				rmf.setOecode(o.getOecode());
				rmf.setOrganname(o.getOrganname());
				rmf.setOtel(o.getOtel());
				rmf.setOmobile(o.getOmobile());
				rmf.setOaddr(o.getOaddr());
				rmf.setOfax(o.getOfax());
				rmf.setOemail(o.getOemail());
				rmf.setRate(o.getRate());
				rmf.setPrompt(o.getPrompt());
				rmf.setProvincename(aca.getCountryAreaName(o.getProvince()));
				rmf.setCityname(aca.getCountryAreaName(o.getCity()));
				rmf.setAreasname(aca.getCountryAreaName(o.getAreas()));
				rmf.setParentid(o.getParentid());
				if (!o.getParentid().equals("0")) {
					rmf.setParentidname(ao.getOrganByID(o.getParentid())
							.getOrganname());
				}
				rmf.setRank(o.getRank());
				dpt.add(rmf);
			}
			List list = aca.getProvinceObj();
			request.setAttribute("OID", oid);
			request.setAttribute("cals", list);
			request.setAttribute("dpt", dpt);

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
