package com.winsafe.drp.action.users;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.BaseResource;

public class ToImportUserTargetAction extends Action {
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppBaseResource appbr = new AppBaseResource();
		try{
			//查询指标类型
		    List<BaseResource> typelist = appbr.getBaseResource("TargetType");
	        request.setAttribute("typelist", typelist);
	        return mapping.findForward("toimportusertarget");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}	
}
