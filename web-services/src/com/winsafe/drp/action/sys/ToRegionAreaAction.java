package com.winsafe.drp.action.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class ToRegionAreaAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pageSize = 15;
		AppCountryArea aca = new AppCountryArea();
		String psid = request.getParameter("PSID");
		try {
			String condition = " c.rank = 0 and not exists (select id from RegionArea where regioncodeid = '"+psid+"' and areaid = c.id)";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String whereSql = " where ";
			String blur = DbUtil.getOrBlur(map, tmpMap, "c.id","c.areaname");
			whereSql = whereSql + blur + condition;
			whereSql = DbUtil.getWhereSql(whereSql);
			List ca =  aca.getCountryAreas(request, pageSize, whereSql);
			request.setAttribute("PSID", psid);
			request.setAttribute("ca", ca);
			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
