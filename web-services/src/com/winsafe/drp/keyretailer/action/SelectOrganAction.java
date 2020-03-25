package com.winsafe.drp.keyretailer.action;

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
import com.winsafe.drp.dao.OrganForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.service.SBonusService;
import com.winsafe.drp.metadata.ValidateStatus;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Encrypt;
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

		try {
			String Province = request.getParameter("Province"); // 省
			String City = request.getParameter("City"); // 市
			String oid = request.getParameter("OID");
			String type = request.getParameter("type");
			// isrepeal:是否撤销
			String condition = "";
			if("targetFrom".equals(type)) {
				if(users.getOrganType() != null && users.getOrganType() == 2) {//经销商人员
					condition = " o.isrepeal=0 and o.id in (select VISITORGAN from USER_VISIT where USERID ="+users.getUserid()+") ";
				} else {//其他人员
					condition = " o.isrepeal=0 and "+ SBonusService.getWhereCondition(users);
				}
			} else if("targetTo".equals(type)) {
				condition = " o.isrepeal=0 and o.iskeyretailer=1 and (o.id in (select ORGANIZATIONID from S_TRANSFER_RELATION where OPPORGANID = '"+oid+"') or o.parentid = '"+oid+"') and o.validate_status="+ValidateStatus.PASSED.getValue()+" ";
			} else {
				condition = " o.isrepeal=0 and (o.id in (select VISITORGAN from USER_VISIT where USERID ="+users.getUserid()+") or "+ SBonusService.getWhereCondition(users)+")";
			}

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Organ" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String blur = DbUtil.getOrBlur(map, tmpMap, "o.ID", "o.OrganName",
					"o.OMobile", "o.OECode", "o.SysID");
			whereSql = whereSql + blur + condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			List<Map<String,String>> menuls = ao.getKeyRetailerOrgan(request, pagesize, whereSql);
			List dpt = new ArrayList();

			for (int i = 0; i < menuls.size(); i++) {
				OrganForm rmf = new OrganForm();
				Map<String,String> o = menuls.get(i);
				MapUtil.mapToObject(o, rmf);
				if(!StringUtil.isEmpty(rmf.getOmobile())) {
					rmf.setOmobile(Encrypt.getSecret(rmf.getOmobile(), 2));
				}
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

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
