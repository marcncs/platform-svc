package com.winsafe.erp.action;


import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.JProperties;
import com.winsafe.erp.dao.AppProductPlan;
import com.winsafe.erp.dao.AppUnitInfo;
import com.winsafe.erp.pojo.ProductPlan;
import com.winsafe.erp.pojo.ProductPlanForm;
import com.winsafe.erp.pojo.UnitInfo;

public class ToUpdProductPlanAction extends BaseAction {

	private AppProductPlan appProductPlan = new AppProductPlan();
	private AppProduct appProduct = new AppProduct();
	private static Logger logger = Logger.getLogger(ToUpdProductPlanAction.class);
	private AppOrgan appOrgan = new AppOrgan();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		initdata(request);
		try {
			String id = request.getParameter("ID");

			ProductPlan productPlan = appProductPlan.getProductPlanByID(Integer.valueOf(id));
			ProductPlanForm ProductPlanForm = new ProductPlanForm();
			
			
			Organ organ = appOrgan.getOrganByID_Isrepeal(productPlan.getOrganId());
			if(organ != null) {
				ProductPlanForm.setOrganname(organ.getOrganname());
			}
			
			Product product  =  appProduct.getProductByID(productPlan.getProductId());
			ProductPlanForm.setApprovalFlag(productPlan.getApprovalFlag());
			ProductPlanForm.setApprovalMan(productPlan.getApprovalMan());
			ProductPlanForm.setBoxnum(productPlan.getBoxnum());
			ProductPlanForm.setCopys(productPlan.getCopys());
			ProductPlanForm.setId(productPlan.getId());
			ProductPlanForm.setMbatch(productPlan.getMbatch());
			ProductPlanForm.setMcode(product.getmCode());
			ProductPlanForm.setOrganId(productPlan.getOrganId());
			ProductPlanForm.setPackDate(productPlan.getPackDate());
			ProductPlanForm.setPackDateString(Dateutil.formatDate(productPlan.getPackDate()));

			ProductPlanForm.setPbatch(productPlan.getPbatch());
			ProductPlanForm.setProDate(productPlan.getProDate());
			ProductPlanForm.setProDateString(Dateutil.formatDate(productPlan.getProDate()));
			ProductPlanForm.setProductId(productPlan.getProductId());
			ProductPlanForm.setPONO(productPlan.getPONO());
			ProductPlanForm.setTemp(productPlan.getTemp());
			ProductPlanForm.setCodeFrom(productPlan.getCodeFrom());
			ProductPlanForm.setCodeTo(productPlan.getCodeTo());
			if (product != null) {
				ProductPlanForm.setProductname(product.getProductname());
			} 
			request.setAttribute("ProductPlanForm", ProductPlanForm);
			request.setAttribute("product", product);
			
			AppUnitInfo appu = new AppUnitInfo();
			UnitInfo unitinfo = appu.getUnitInfoByOidAndPid(organ.getId(),productPlan.getProductId());
			request.setAttribute("abc", unitinfo.getUnitCount());
			request.setAttribute("ncc", unitinfo.getNeedCovertCode());
			
		} catch (Exception e) {
			logger.error("", e);
		}
		return mapping.findForward("success");
	}

}
