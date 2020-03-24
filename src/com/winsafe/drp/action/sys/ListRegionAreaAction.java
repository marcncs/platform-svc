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
import com.winsafe.drp.dao.AppRegionArea;
import com.winsafe.drp.dao.RegionArea;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListRegionAreaAction extends BaseAction {
	
	AppRegionArea appRegionArea = new AppRegionArea();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		initdata(request);
		int pageSize = 15;
		
		try {
			String pid = request.getParameter("OtherKey");
			String condition = "";
			if (pid != null && pid.length() > 1) {
				condition = " p.regioncodeid = '" + pid + "' and p.areaid = ca.id ";
			} else {
				condition = "1=2 ";
			}
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "RegionArea" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "ca.id","ca.areaname");
			whereSql = whereSql + blur + condition;
			whereSql = DbUtil.getWhereSql(whereSql);
			List resultList = appRegionArea.getRegionList(request, pageSize, whereSql);
			List<RegionArea> regionAreas = new ArrayList<RegionArea>();
			for(int i=0;i<resultList.size();i++){
				RegionArea ra = new RegionArea();
				Object[] ob = (Object[]) resultList.get(i);
				ra.setId((Long)ob[0]);
				ra.setAreaid((String)ob[1]);
				ra.setAreaname((String)ob[2]);
				ra.setRegioncodeid((String)ob[3]);
				regionAreas.add(ra);
			}
			DBUserLog.addUserLog(request, "列表");
			request.setAttribute("regionAreas", regionAreas);
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			request.setAttribute("OtherKey", request.getParameter("OtherKey"));
			return mapping.findForward("list");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
