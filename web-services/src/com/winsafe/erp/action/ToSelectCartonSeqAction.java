package com.winsafe.erp.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger; 
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.erp.dao.AppCartonSeq;
import com.winsafe.erp.dao.AppProductPlan;
import com.winsafe.erp.pojo.ProductPlan;
import com.winsafe.erp.services.CartonSeqServices;
import com.winsafe.sap.metadata.YesOrNo;

public class ToSelectCartonSeqAction extends BaseAction {

	private AppProductPlan appProductPlan = new AppProductPlan();
	private static Logger logger = Logger.getLogger(ToSelectCartonSeqAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppCartonSeq appCs = new AppCartonSeq();
		CartonSeqServices css = new CartonSeqServices();
		initdata(request);
		int pageSize = 15;
		try {
			String id = request.getParameter("ID");

			ProductPlan productPlan = appProductPlan.getProductPlanByID(Integer.valueOf(id));
			
			if((productPlan.getCartonSeqFlag() == null 
					|| productPlan.getCartonSeqFlag() == YesOrNo.NO.getValue())
					&& (productPlan.getCloseFlag() == null
					|| productPlan.getCloseFlag() != 1)) {
				List<String> seqList = appCs.getUsableSeqByProductId(productPlan.getProductId());
				
				if(seqList.size() < productPlan.getBoxnum()) {
					request.setAttribute("result", "没有足够的箱序号可关联");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
				
				request.setAttribute("range", css.getUsableSeqRangeString(seqList));
				request.setAttribute("qty", productPlan.getBoxnum());
				request.setAttribute("planId", productPlan.getId());
				return mapping.findForward("success");
			} else {
				AppProduct appProduct = new AppProduct();
				Product product = appProduct.getProductByID(productPlan.getProductId());
				List<Map<String,String>> codelist = appCs.getCartonSeq(request, pageSize, productPlan.getPrintJobId()); 
				request.setAttribute("codelist", codelist);
				request.setAttribute("product", product);
				request.setAttribute("plan", productPlan);
				//获取当前可用范围
				List<String> seqList = appCs.getUsableSeqByProductId(productPlan.getProductId());
				request.setAttribute("range", css.getUsableSeqRangeString(seqList));
				return mapping.findForward("update");
			}
			
		} catch (Exception e) {
			logger.error("", e);
		}
		return mapping.findForward("success");
	}

}
