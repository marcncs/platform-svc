package com.winsafe.sap.action;

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
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganForm;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class SelectOrganAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// dao所需类
		CountryAreaService cas = new CountryAreaService();
		AppOrgan ao = new AppOrgan();

		// 初始化
		initdata(request);
		int pagesize = 20;
		String url = "success";
		try {
			String Province = request.getParameter("Province"); // 省
			String City = request.getParameter("City"); // 市
			String type = request.getParameter("type");
			String oid = request.getParameter("OID");
			// isrepeal:是否撤销
			String condition = "";
			if ("rw".equals(type)) {
				// 获取管辖机构信息
				condition = " o.isrepeal=0 and  o.id in (select visitorgan from UserVisit as uv where  uv.userid=" + userid + ") ";

			} else if ("vw".equals(type)) {
				// 获取业务往来机构信息
				condition = " o.isrepeal=0 and  o.id in (select visitorgan from OrganVisit as ov where  ov.userid=" + userid + ") ";

			} else if ("1".equals(type)) {
				// 获取当前用户的所属机构

				condition = " o.isrepeal=0 and  o.id = "
						+ users.getMakeorganid() + " ";

			} else if ("from".equals(type)){
				// from  PD BKD
				condition = " o.organModel in (1,2)  and o.organType=2 and o.isrepeal=0 ";
				url = "keyretailer";
				request.setAttribute("msgb", "请选择出货客户");
			} else if ("to".equals(type)){
				// to BKD BKR
				condition = " o.organModel in (2,3)  and o.organType=2 and o.isrepeal=0  and o.id in (select organId from  SBonusAccount)";
				url = "keyretailer";
				request.setAttribute("msgb", "请选择进货机构");
			} else {
				condition = " o.isrepeal=0 and organType = 1 and (organModel = 1 or organModel = 2) and o.id in (select visitorgan from UserVisit as uv where  uv.userid=" + userid + ") ";
			} 

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Organ" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String blur = DbUtil.getOrBlur(map, tmpMap, "id", "organname",
					"omobile", "oecode", "sysid");
			whereSql = whereSql + blur + condition;
			whereSql = DbUtil.getWhereSql(whereSql);

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
				rmf.setProvincename(cas.getCountryAreaName(o.getProvince()));
				rmf.setCityname(cas.getCountryAreaName(o.getCity()));
				rmf.setAreasname(cas.getCountryAreaName(o.getAreas()));
				rmf.setParentid(o.getParentid());
				if (!o.getParentid().equals("0")) {
					rmf.setParentidname(ao.getOrganByID(o.getParentid())
							.getOrganname());
				}
				rmf.setRank(o.getRank());
				rmf.setOrganModel(o.getOrganModel());
				//rmf.setPlantType(o.getPlantType());
				dpt.add(rmf);
			}

			List list = cas.getProvinceObj();
			request.setAttribute("OID", oid);
			request.setAttribute("cals", list);

			List citysList = new ArrayList();
			List areasList = new ArrayList();
			if (!StringUtil.isEmpty(Province)) {
				citysList = cas.getCountryAreaByParentid(Integer
						.valueOf(Province));
			}
			if (!StringUtil.isEmpty(City)) {
				areasList = cas.getCountryAreaByParentid(Integer.valueOf(City));
			}
			request.setAttribute("Citys", citysList);
			request.setAttribute("Areass", areasList);

			request.setAttribute("dpt", dpt);
			
			request.setAttribute("type", type);
			request.setAttribute("OID", oid);

			return mapping.findForward(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

	public String getOrganInfoByRuleUserWH(Integer userid) throws Exception {
		String organs = "";
		AppRuleUserWH aru = new AppRuleUserWH();
		List<Warehouse> warehouseList = aru.queryRuleUserWh(userid);
		if (null != warehouseList && !warehouseList.isEmpty()) {
			for (Warehouse warehouse : warehouseList) {
				organs += warehouse.getMakeorganid() + ",";
			}
			String[] oIds = organs.split(",");
			organs = StringUtil.getString(oIds, 0, true);
		}
		return organs;
	}

	public String getOrganInfoByWarehuseVisit(Integer userid) throws Exception {
		String organs = "";
		AppWarehouse aw = new AppWarehouse();
		List<Warehouse> warehouseList = aw.getEnableWarehouseByVisit(userid);
		if (null != warehouseList && !warehouseList.isEmpty()) {
			for (Warehouse warehouse : warehouseList) {
				organs += warehouse.getMakeorganid() + ",";
			}
			String[] oIds = organs.split(",");
			organs = StringUtil.getString(oIds, 0, true);
		}
		return organs;
	}
}
