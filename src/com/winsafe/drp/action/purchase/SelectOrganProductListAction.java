package com.winsafe.drp.action.purchase;
 
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganProduct; 
import com.winsafe.drp.dao.Organ; 

public class SelectOrganProductListAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//得到产品id
		String productIds = request.getParameter("IDS");
		//同步至页面.模态用
		request.setAttribute("organProductId", productIds);
		AppOrganProduct appOrganProduct = new AppOrganProduct();
		Set<Organ> notExistOrganList = appOrganProduct.getNotExistOrgan(productIds);
		request.setAttribute("notExistOrganList", notExistOrganList);
		return mapping.findForward("list");
		  
	}
}
