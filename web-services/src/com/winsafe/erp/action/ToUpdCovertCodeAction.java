package com.winsafe.erp.action;


import java.util.List;
import java.util.Map; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.erp.dao.AppPrepareCode;
import com.winsafe.erp.dao.AppProductPlan;
import com.winsafe.erp.dao.AppUnitInfo;
import com.winsafe.erp.pojo.PrepareCode;
import com.winsafe.erp.pojo.ProductPlan;
import com.winsafe.erp.pojo.UnitInfo;

public class ToUpdCovertCodeAction extends BaseAction {

	private AppProductPlan appProductPlan = new AppProductPlan();
	private AppProduct appProduct = new AppProduct();
	private static Logger logger = Logger.getLogger(ToUpdCovertCodeAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		int pageSize = 15;
		initdata(request);
		try {
			String planid = request.getParameter("ID");
			AppPrepareCode auv = new AppPrepareCode();      	
	    	String where = " where isuse=1 and tcode != 't' and productPlanId="+planid; 
	    	String KeyWord = request.getParameter("KeyWord");
	    	if(!StringUtil.isEmpty(KeyWord)){
	    		where += " and (code like '%"+KeyWord+"%' or tcode like '%"+KeyWord+"%')";
	    	}
	    	String isRelease = request.getParameter("isRelease");
	    	if(!StringUtil.isEmpty(isRelease)){
	    		if(isRelease.equals("0")){
	    			where += " and (isRelease is null or isRelease=0)  ";
	    		}else{
		    		where += " and isRelease = 1 ";
	    		}
	    	}
	    	
	    	List<Map<String,String>> codelist = auv.getCodeListForUpdCovertCode(request, pageSize); 
	    	request.setAttribute("codelist", codelist);
	        
	        Integer realnum = auv.countReleaseCode(where);
	        request.setAttribute("realnum", realnum);
	        
	        ProductPlan plan = appProductPlan.getProductPlanByID(Integer.parseInt(planid));
	        request.setAttribute("plan", plan);
	        
	        AppUnitInfo appUnitInfo = new AppUnitInfo();
	        UnitInfo unitInfo = appUnitInfo.getUnitInfoByOidAndPid(plan.getOrganId(),plan.getProductId());
	        request.setAttribute("needCovertCode", unitInfo.getNeedCovertCode());
	        
			Product product = appProduct.getProductByID(plan.getProductId());
			request.setAttribute("product", product);
			
	        if(!StringUtil.isEmpty(plan.getTemp())&&plan.getTemp().equals("已生成")){
	        	request.setAttribute("printflag", 1);
	        }else{
	        	request.setAttribute("printflag", 0);
	        }
	        
	        if(plan.getCloseFlag()==null){
	        	plan.setCloseFlag(0);
	        }
	    	
//			List<Map<String,String>> covertCode = appProductPlan.getCovertCodeById(request, pageSize);
			
//			request.setAttribute("covertCodes", covertCode);
			request.setAttribute("plan", plan);
			  
		} catch (Exception e) {
			logger.error("", e);
		}
		return mapping.findForward("toUpd");
	}

}
