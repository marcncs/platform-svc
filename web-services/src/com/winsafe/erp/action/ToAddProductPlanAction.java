package com.winsafe.erp.action;


import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.util.JProperties;
import com.winsafe.erp.dao.AppUnitInfo;
import com.winsafe.erp.pojo.UnitInfo;

public class ToAddProductPlanAction extends BaseAction{
	private AppOrgan appOrgan = new AppOrgan();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		Organ organ = appOrgan.getOrganByID_Isrepeal(users.getMakeorganid());
		if(organ != null) {
			if(organ.getOrganType()!=null&&organ.getOrganModel()!=null){
				if(organ.getOrganType()==1&&(organ.getOrganModel()==1||organ.getOrganModel()==2)){
					request.setAttribute("organid", users.getMakeorganid());
					request.setAttribute("oname", organ.getOrganname());
				}
			}
		}
		
		return mapping.findForward("success");
	}
		
}
