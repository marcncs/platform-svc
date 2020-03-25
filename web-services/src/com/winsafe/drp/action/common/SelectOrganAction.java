package com.winsafe.drp.action.common;

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
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganForm;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.metadata.ApplyUserType;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.drp.metadata.ValidateStatus;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.StringUtil;

public class SelectOrganAction extends BaseAction {
	
	private static Logger logger = Logger.getLogger(SelectOrganAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception { 
		// dao所需类
		CountryAreaService cas = new CountryAreaService();
		AppOrgan ao = new AppOrgan();

		// 初始化
		initdata(request);
		int pagesize = 20;

		try {
			String Province = request.getParameter("Province"); // 省
			String City = request.getParameter("City"); // 市
			String type = request.getParameter("type");
			String oid = request.getParameter("OID"); 
			String userType = request.getParameter("userType"); 
			String i18nFlag = request.getParameter("i18nFlag");
			// isrepeal:是否撤销
			String condition = " o.isrepeal=0 and "; 
			
			if(DbUtil.isDealer(users)) {//渠道用户
				if ("rw".equals(type)) {
					// 获取管辖机构信息
					condition += DbUtil.getUserVisitSqlCondition(userid, "o");
				} else if ("vw".equals(type)) {
					// 获取业务往来机构信息
					condition += DbUtil.getOrganVisitSqlCondition(userid, "o");
				}else if ("pw".equals(type)) {
					// 获取工厂退回机构信息
					condition += " o.organType = 1 ";
				}else if("toller".equals(type)){
					condition += " o.organType = 1 and (o.organModel = 1 or o.organModel = 2) and " + DbUtil.getUserVisitSqlCondition(userid, "o");
				}
				else if ("1".equals(type)) {
					// 获取当前用户的所属机构
					condition += " o.id = "+ users.getMakeorganid() + " ";
				}else if ("pvw".equals(type)) {
					// 获取与当前机构有业务往来的机构
					condition += " o.id in  ( select makeorganid from Users u where u.userid in ( select userid from Organ_Visit where visitorgan ='" + users.getMakeorganid() + "')) ";
					Organ organ = ao.getOrganByID(users.getMakeorganid());
					if(organ.getOrganType() != null && organ.getOrganType()==2 
							&& organ.getOrganModel() != null && organ.getOrganModel() == 1){
						condition += " or o.organType = 1 ";
					}
					
				} else if("rvw".equals(type)) {
					// 获取管辖与业务往来机构信息
					condition += " (o.organModel=1 or o.organModel=2)  and ( "+DbUtil.getUserVisitSqlCondition(userid, "o")+" or "+DbUtil.getOrganVisitSqlCondition(userid, "o")+") ";

				} else if ("vwar".equals(type)) {  
					String outOid = request.getParameter("outOid");
					// 获取业务往来机构信息和进货机构
					condition += " o.id <> '"+outOid+"' and (("+DbUtil.getOrganVisitSqlCondition(userid, "o")+" and o.parentid='"+outOid+"' ) or o.id in (select organizationId from S_Transfer_Relation where  oppOrganId='" + outOid + "') )";
					if(OrganType.Dealer.getValue().equals(users.getOrganType())) {
						condition += " and o.validate_status="+ValidateStatus.PASSED.getValue()+" ";
					}
				} else {
					condition += " o.sysid like '"	+ users.getOrgansysid() + "%' ";
				}
			} else {//BCS内部人员或分装厂用户
				if("toller".equals(type)){
					condition += " o.organType = 1 and (o.organModel = 1 or o.organModel = 2) and " + DbUtil.getUserVisitSqlCondition(userid, "o");
				} else {
					if(!StringUtil.isEmpty(userType)) {
						ApplyUserType autype = ApplyUserType.parseByValue(Integer.valueOf(userType));
						if(autype == ApplyUserType.TD
								|| autype == ApplyUserType.WH) {
							condition += " o.organType = 2 and o.organModel = 1 and ";
						} else if(autype == ApplyUserType.BKD) {
							condition += " o.organType = 2 and o.organModel = 2 and ";
						} else {
							condition += " o.organType = 2 and o.organModel not in (1,2) and ";
						}
					}
					condition += " ("+DbUtil.getWhereCondition(users, "o")+")";
				}
				
			}

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Organ" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String blur = DbUtil.getOrBlur(map, tmpMap, "o.ID", "o.OrganName",
					"o.OMobile", "o.OECode", "o.SysID");
			whereSql = whereSql + blur + condition;
			whereSql = whereSql.replace("province=", "o.province=").replace("city=", "o.city=").replace("areas=", "o.areas=");
			whereSql = DbUtil.getWhereSql(whereSql);
			logger.debug("userinfo:"+users.getLoginname()+users.getOrganType());
			logger.debug("where condition:"+whereSql);
//			List menuls = ao.getOrgan(request, pagesize, whereSql);
			List<Map<String,String>> menuls = ao.getSelectOrgan(request, pagesize, whereSql);
			List dpt = new ArrayList();

			for (int i = 0; i < menuls.size(); i++) {
				OrganForm rmf = new OrganForm();
				Map<String,String> organ = menuls.get(i);
				MapUtil.mapToObject(organ, rmf);
				if(!StringUtil.isEmpty(rmf.getOmobile())) {
					rmf.setOmobile(Encrypt.getSecret(rmf.getOmobile(), 2));
				}
				/*Organ o = (Organ) menuls.get(i);
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
				rmf.setRank(o.getRank());*/
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

			if ("true".equals(i18nFlag)) {
				return mapping.findForward("successI18n");
			} else {
				return mapping.findForward("success");
			}
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
