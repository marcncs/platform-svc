package com.winsafe.drp.action.users;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppRegion;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.Region;

public class ToAddUserTargetAction extends Action {
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppBaseResource appbr = new AppBaseResource();
		AppRegion appr = new AppRegion();
		try{
			//查询指标类型
		    List<BaseResource> typelist = appbr.getBaseResource("TargetType");
	        request.setAttribute("typelist", typelist);
	        //查询办事处信息
	        List<Region> bslist = appr.getRegionByType("2");
	        //查询大区信息
	        List<Region> dqlist = appr.getRegionByType("1");
	        request.setAttribute("bslist", bslist);
	        request.setAttribute("dqlist", dqlist);
	        return mapping.findForward("toaddusertarget");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}	
}
