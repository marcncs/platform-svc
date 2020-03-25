package com.winsafe.drp.action.sys;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.CountryArea;


public class GetCountryAreaAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AppCountryArea aca = new AppCountryArea();
		String parentids = request.getParameter("parentids");
		String type  = request.getParameter("type");
		
		String cids = request.getParameter("cids");
		List<String> list =  new ArrayList<String>();
		if(cids!=null &&  !"".equals(cids)){
			String[] arr = cids.split(",");
			for(int i=0;i<arr.length;i++){
				list.add(arr[i]);
			}
		}
		
		
		List<CountryArea> countryareaList = aca.getCountryAreas(parentids);
		request.setAttribute("countryareaList", countryareaList);
		request.setAttribute("list", list);
		
		if("0".equals(type)){
			return mapping.findForward("provinceList");
		}else if("1".equals(type)){
			return mapping.findForward("cityList");
		}else{
			return mapping.findForward("areaList");
		}
		
		
	}
}

