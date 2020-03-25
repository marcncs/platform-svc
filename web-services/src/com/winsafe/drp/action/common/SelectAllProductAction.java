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
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class SelectAllProductAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		 super.initdata(request);
		try {
			String strCondition = " p.useflag=1 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			// String sql = "select * from product where " + strCondition;
			String[] tablename = { "Product" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String blur = DbUtil.getOrBlur(map, tmpMap, "ID","ProductName","ProductNameEn","PYCode","SpecMode","NCcode");

			whereSql = whereSql + leftblur + blur + strCondition;
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppProduct ap = new AppProduct();
			List pls = ap.getSelectProduct(request,pagesize, whereSql);
			ArrayList sls = new ArrayList();

			for (int i = 0; i < pls.size(); i++) {
				ProductForm pf = new ProductForm();
				Product o = (Product) pls.get(i);
				pf.setId(o.getId());
				pf.setProductname(o.getProductname());
				pf.setSpecmode(o.getSpecmode());
				pf.setBrand(o.getBrand());
//				pf.setBrandname(HtmlSelect.getResourceName(request, "Brand", o.getBrand()));
				pf.setCountunit(o.getCountunit());
				pf.setCountunitname(HtmlSelect.getResourceName(request,
						"CountUnit", o.getCountunit()));
				//pf.setStandardsale(o.getStandardsale());
				pf.setCost(o.getCost());
				sls.add(pf);
			}

		
			request.setAttribute("sls", sls);
			return mapping.findForward("selectproduct");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
