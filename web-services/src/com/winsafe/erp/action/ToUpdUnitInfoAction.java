package com.winsafe.erp.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.erp.dao.AppUnitInfo;
import com.winsafe.erp.pojo.UnitInfo;
import com.winsafe.erp.pojo.UnitInfoForm;
import com.winsafe.hbm.util.StringUtil; 

public class ToUpdUnitInfoAction extends BaseAction {

	private AppUnitInfo appUnitInfo = new AppUnitInfo();
	private AppProduct appProduct = new AppProduct();
	private static Logger logger = Logger.getLogger(ToUpdUnitInfoAction.class);
	private AppOrgan appOrgan = new AppOrgan();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		initdata(request);
		try {
			String id = request.getParameter("ID");

			UnitInfo UnitInfo = appUnitInfo.getUnitInfoByID(Integer.valueOf(id));
			UnitInfoForm UnitInfoForm = new UnitInfoForm();
			
			
			Organ organ = appOrgan.getOrganByID_Isrepeal(UnitInfo.getOrganId());
			request.setAttribute("organid", users.getMakeorganid());
			if(organ != null) {
				UnitInfoForm.setOrganName(organ.getOrganname());
			}
			
			Product product  =  appProduct.getProductByID(UnitInfo.getProductId());
			
			UnitInfoForm.setId(UnitInfo.getId());
			UnitInfoForm.setOrganId(UnitInfo.getOrganId());
			UnitInfoForm.setProductId(UnitInfo.getProductId());
			UnitInfoForm.setUnitCount(UnitInfo.getUnitCount());
			UnitInfoForm.setUnitId(UnitInfo.getUnitId());
			UnitInfoForm.setLabelType(UnitInfo.getLabelType());
			UnitInfoForm.setNeedRepackage(UnitInfo.getNeedRepackage());
			UnitInfoForm.setNeedCovertCode(UnitInfo.getNeedCovertCode());
			if(!StringUtil.isEmpty(UnitInfo.getCodeSeq())) {
				UnitInfoForm.setCodeSeq(UnitInfo.getCodeSeq().substring(0, 3));
			}
			 
			if (product != null) {
				UnitInfoForm.setProductName(product.getProductname());
			}
			
			request.setAttribute("UnitInfoForm", UnitInfoForm);
		} catch (Exception e) {
			logger.error("", e);
		}
		return mapping.findForward("success");
	}

}
