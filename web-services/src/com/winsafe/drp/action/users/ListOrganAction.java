package com.winsafe.drp.action.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.OrganForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.dao.AppSalesAreaCountry;
import com.winsafe.drp.metadata.ValidateStatus;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil; 

public class ListOrganAction extends BaseAction {
	private OrganService ao = new OrganService();
	private CountryAreaService cas = new CountryAreaService();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		int pagesize = 15;
		initdata(request);
		String Province = request.getParameter("Province"); //省
		String City = request.getParameter("City"); //市
		String organid=request.getParameter("CompanyID");
		String linkman=request.getParameter("linkman");
		String validateStatus=request.getParameter("ValidateStatus");

		try {
			
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Organ" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = "";
			String createTimeCondition = DbUtil.getTimeConditionForSql(map, tmpMap,
				"creationTime", param); 
			if(!StringUtil.isEmpty(createTimeCondition)) {
				createTimeCondition = "("+createTimeCondition.substring(0,createTimeCondition.lastIndexOf("and"))+")";
			}
			String modificationTimeCondition = DbUtil.getTimeConditionForSql(map, tmpMap,
            	"modificationTime", param); 
			if(!StringUtil.isEmpty(modificationTimeCondition)) {
				modificationTimeCondition = "("+modificationTimeCondition.substring(0,modificationTimeCondition.lastIndexOf("and"))+")";
			}
			
			if(!StringUtil.isEmpty(createTimeCondition) && !StringUtil.isEmpty(modificationTimeCondition)) {
				timeCondition = "("+createTimeCondition+" or " + modificationTimeCondition+") and ";
			} else if(!StringUtil.isEmpty(createTimeCondition) || !StringUtil.isEmpty(modificationTimeCondition)){
				timeCondition = createTimeCondition + modificationTimeCondition +" and ";
			}
			String blur = DbUtil.getOrBlurForSql(map, tmpMap, param, "ID", "OrganName",
					"OMobile", "OECode","bigRegionName","officeName");
			
			String Condition = "";
			if(DbUtil.isDealer(users)) {
				Condition = " o.parentid ='"+users.getMakeorganid()+"' ";
			} else {
				Condition += DbUtil.getWhereCondition(users, "o");
			}
			if(organid!=null&&organid!=""){
				Condition +="and o.id = ?";
				param.put("organid", organid);
			}
			if(!StringUtil.isEmpty(linkman)){
				Condition +="and ( (o.omobile like ?) or exists (select id from Olinkman where cid=o.id and (name like ? or mobile like ?) ) or exists (select id from Warehouse w where makeorganid = o.id and (username like ? or warehousetel like ? or exists ( select id from Wlinkman where warehouseid = w.id and (name like ? or mobile like ?) ))) ) ";
				param.put(UUID.randomUUID().toString(), "%"+linkman+"%");
				param.put(UUID.randomUUID().toString(), "%"+linkman+"%");
				param.put(UUID.randomUUID().toString(), "%"+linkman+"%");
				param.put(UUID.randomUUID().toString(), "%"+linkman+"%");
				param.put(UUID.randomUUID().toString(), "%"+linkman+"%");
				param.put(UUID.randomUUID().toString(), "%"+linkman+"%");
				param.put(UUID.randomUUID().toString(), "%"+linkman+"%");
			}
			
			whereSql = whereSql + timeCondition+blur + Condition;
			if(!StringUtil.isEmpty(validateStatus)) {
				ValidateStatus status = ValidateStatus.parseByValue(Integer.valueOf(validateStatus));
				if(ValidateStatus.NOT_AUDITED == status) {
					whereSql += " and (validate_status is null or validate_status= "+ status.getValue()+") ";// whereSql.replace("validatestatus", "validate_status");
				} else {
					whereSql += " and validate_status="+ status.getValue();
				}
			}
			whereSql = DbUtil.getWhereSql(whereSql);

			List<Map<String,String>> menuls = ao.getOrganList(request, pagesize, whereSql, param);
			List dpt = new ArrayList();
			
			AppSalesAreaCountry asac = new AppSalesAreaCountry();
			Map<String, String[]> areaNameMap = asac.getCountrySalesAreaNamesMap();

			for (int i = 0; i < menuls.size(); i++) {
				OrganForm rmf = new OrganForm();
				Map<String,String> o = menuls.get(i);
				MapUtil.mapToObject(o, rmf);
				rmf.setValidatestatus(!StringUtil.isEmpty(o.get("validate_status")) ? Integer.valueOf(o.get("validate_status")):null);
				rmf.setProvincename(cas.getCountryAreaName(rmf.getProvince()==null?0:rmf.getProvince()));
				rmf.setCityname(cas.getCountryAreaName(rmf.getCity()));
				rmf.setAreasname(cas.getCountryAreaName(rmf.getAreas()));
				rmf.setParentidname(ao.getOrganName(rmf.getParentid()));
				
				//设置销售区域属性
				if(rmf.getOrganType() != null && rmf.getOrganModel() != null && rmf.getOrganType() == 2 && rmf.getOrganModel() != 1 && rmf.getOrganModel() != 2) {
					if(rmf.getAreas() != null) {
						String areaNames[] = areaNameMap.get(rmf.getAreas().toString());
						if(areaNames != null) {
							rmf.setFirstAreaName(areaNames[2]);
							rmf.setSecondAreaName(areaNames[1]);
							rmf.setThirdAreaName(areaNames[0]);
						}
					}
				}
				dpt.add(rmf);
			}
			
			List list0 = cas.getProvinceObj();

			request.setAttribute("cals", list0);
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
			WfLogger.error("", e);
		}
		return new ActionForward(mapping.getInput());
	}
}
