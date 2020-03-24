package com.winsafe.drp.action.machin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStruct;

/**
 * @author : jerry
 * @version : 2009-9-4 下午05:08:07 www.winsafe.cn
 */
public class DetailSapPrepareCodeAction extends BaseAction {
	
	private AppProduct appProduct = new AppProduct();
	private AppProductStruct appProductStruct = new AppProductStruct();
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try{
			initdata(request);
			Map map = new HashMap(request.getParameterMap());
			String proId = request.getParameter("ID");
			Product product = appProduct.getProductByID(proId);
			if(product != null) {
				ProductStruct ps = appProductStruct.getProductStructById(product.getPsid());
				request.setAttribute("ps", ps);
			}
			request.setAttribute("dtl", map);
			request.setAttribute("pro", product);
			
			return mapping.findForward("detail");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
