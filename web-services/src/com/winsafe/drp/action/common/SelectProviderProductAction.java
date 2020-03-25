package com.winsafe.drp.action.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProviderProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProviderProduct;
import com.winsafe.drp.dao.ProviderProductForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class SelectProviderProductAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		String pid = request.getParameter("pid");
		

		try {
			String strCondition = " pp.providerid='" + pid
					+ "' and p.id =pp.productid and p.useflag=1 ";
//			strCondition += " and exists (select d.productid from PurchaseIncome as pi, PurchaseIncomeDetail as d "+
//			"where pi.id=d.piid and pi.isaudit=1  "+
//			"and pi.provideid='"+pid+"' and p.id=d.productid) ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "ProviderProduct", "Product" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String blur = DbUtil.getOrBlur(map, tmpMap, "p.id","ProductName","PYCode","SpecMode","NCcode");
			whereSql = whereSql + leftblur + blur + strCondition;
			whereSql = DbUtil.getWhereSql(whereSql); 

		

			AppProviderProduct app = new AppProviderProduct();
			List pls = app.getProviderProductByProvideID(request,pagesize, whereSql);
			ArrayList sls = new ArrayList();

			for (int i = 0; i < pls.size(); i++) {
				ProviderProductForm ppf = new ProviderProductForm();
				Object[] o = (Object[]) pls.get(i);
				Product p = (Product) o[0];
				ProviderProduct pp = (ProviderProduct) o[1];				
				ppf.setId(pp.getId());
				ppf.setProductid(p.getId());
				ppf.setPvproductname(p.getProductname());
				ppf.setPvspecmode(p.getSpecmode());
				//ppf.setBrandname(HtmlSelect.getResourceName(request, "Brand", p.getBrand()));
				ppf.setCountunit(pp.getCountunit());
				ppf.setUnitname(HtmlSelect.getResourceName(request,
						"CountUnit", pp.getCountunit()));
				ppf.setBarcode(p.getBarcode());
				ppf.setPrice(pp.getPrice());
				ppf.setPricedate(DateUtil.formatDateTime(pp.getPricedate()));
				sls.add(ppf);
			}
			
			request.setAttribute("pid", pid);
			request.setAttribute("sls", sls);
			return mapping.findForward("selectproduct");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
