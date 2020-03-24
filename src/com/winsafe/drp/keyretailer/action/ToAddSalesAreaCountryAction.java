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
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.dao.AppSalesAreaCountry;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class ToAddSalesAreaCountryAction extends BaseAction{
	private AppSalesAreaCountry appSalesAreaCountry = new AppSalesAreaCountry();
	private CountryAreaService cas = new CountryAreaService();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		int pagesize = 10;
		initdata(request);
		
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		
		String blur = DbUtil.getOrBlur(map, tmpMap, "CA.AREANAME","CA2.AREANAME","CA3.AREANAME");
		if(!StringUtil.isEmpty(blur)) {
			blur = " and " + blur;
		}
		blur = DbUtil.getWhereSql(blur);
		
		List<Map<String,String>> users = appSalesAreaCountry.getCountryAresToAdd(request, pagesize, blur);
		
		request.setAttribute("areaList", users);
		List list0 = cas.getProvinceObj();

		request.setAttribute("cals", list0);
		
		String province = request.getParameter("province"); //省
		List citysList = new ArrayList();
		if(!StringUtil.isEmpty(province)){
			citysList = cas.getCountryAreaByParentid(Integer.valueOf(province));
		}
		request.setAttribute("citys", citysList);
		
		return mapping.findForward("toadd");
	}
		
}
