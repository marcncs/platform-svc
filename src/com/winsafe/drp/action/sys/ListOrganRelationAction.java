package com.winsafe.drp.action.sys;

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
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.STransfelationForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.dao.AppSTransferRelation;
import com.winsafe.drp.keyretailer.pojo.STransferRelation;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.drp.server.OrganService;
import com.winsafe.hbm.util.DbUtil;

public class ListOrganRelationAction extends BaseAction {
	
	private AppSTransferRelation app = new AppSTransferRelation();
	private CountryAreaService cas = new CountryAreaService();
	private OrganService ao = new OrganService();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		int pagesize = 10;
		String oid = request.getParameter("OID");
		String organModel = request.getParameter("organModel");
		try {
			String condition = " p.organizationId='" + oid + "' ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "STransferRelation" };
			// 查询条件
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			// 关键字查询条件
			String blur = DbUtil.getOrBlur(map, tmpMap, "organizationId", "oppOrganId");
			whereSql = whereSql + blur + condition;
			whereSql = DbUtil.getWhereSql(whereSql);
			List list = app.getSTransferRelation(request, pagesize, whereSql);

			List dpt = new ArrayList();

			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				STransferRelation ss = (STransferRelation) obj[0];
				Organ o = (Organ) obj[1];
				STransfelationForm rmf = new STransfelationForm();
				rmf.setSequence(String.valueOf(ss.getId()));
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
			
			request.setAttribute("dpt", dpt);
			request.setAttribute("OID", oid);
			request.setAttribute("organModel", organModel);
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
