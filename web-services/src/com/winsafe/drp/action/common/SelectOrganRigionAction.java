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
import com.winsafe.drp.dao.AppRuleUserWH;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class SelectOrganRigionAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		UsersBean users = UserManager.getUser(request);

		String MakeOrganID = request.getParameter("MakeOrganID");

		super.initdata(request);
		try {
			String type = request.getParameter("type");
			String oid = request.getParameter("OID");
			String Condition1 = "";
			if ("rw".equals(type)) {
				// 获取管辖仓库（设置仓库）rule_user_wh的所属机构信息
				Condition1 = " o.isrepeal=0 and  o.id in (select w.makeorganid from Warehouse as w,RuleUserWh as wv where w.useflag=1 and wv.activeFlag=1 and w.id=wv.warehouseId and wv.userId="
						+ userid + ") ";

			} else if ("vw".equals(type)) {
				// 获取业务往来仓库（许可）warehouse_visit的所属机构信息

				Condition1 = " o.isrepeal=0 and  o.id in (select w.makeorganid from Warehouse as w,WarehouseVisit as wv where w.useflag=1 and w.id=wv.wid and wv.userid="
						+ userid + ") ";

			} else if ("1".equals(type)) {
				// 获取当前用户的所属机构

				Condition1 = " o.isrepeal=0 and  o.id = "
						+ users.getMakeorganid() + " ";

			} else {
				// isrepeal:是否撤销
				Condition1 = " o.isrepeal=0 and o.sysid like '"
						+ users.getOrgansysid() + "%' ";
				if (oid != null) {
					Condition1 = " o.isrepeal=0 ";
				}
			}

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Organ" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String blur = DbUtil.getOrBlur(map, tmpMap, "ID", "OrganName",
					"OMobile", "OECode", "SysID");
			whereSql = whereSql + blur + Condition1;
			whereSql = DbUtil.getWhereSql(whereSql);

			CountryAreaService aca = new CountryAreaService();
			AppOrgan ao = new AppOrgan();
			
			List menuls = ao.getOrgan(request, pagesize, whereSql);
			
			List mList=ao.getAllOrgan();
			
			
			
			
		   //  List mList= ao.getAllOrgan();
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
				if (MakeOrganID != null && MakeOrganID.length() > 0) {
					String[] MakeOrganIDs = MakeOrganID.split(",");
					for (int j = 0; j < MakeOrganIDs.length; j++) {
						if (String.valueOf(rmf.getId()).equals(MakeOrganIDs[j])) {
							rmf.setIsCheck(1);
							break;
						}
					}
				}
				rmf.setRank(o.getRank());
				dpt.add(rmf);
			}

			List list = aca.getProvinceObj();
			request.setAttribute("OID", oid);
			request.setAttribute("cals", list);
			request.setAttribute("dpt", dpt);
			request.setAttribute("MakeOrganID",MakeOrganID);
			
			

			return mapping.findForward("success");
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
