package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProviderProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProviderProduct;
import com.winsafe.drp.dao.ProviderProductForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class SelectProviderProductAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		String pvid = request.getParameter("pvid");

		try {
			//String strCondition = " pp.providerid='" + pid+"' and p.id =pp.productid and p.useflag=1 ";
			String strCondition = " pp.providerid='" + pvid+"' and p.id =pp.productid and p.useflag=1 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			//String sql = "select * from product where " + strCondition;
			String[] tablename = { "ProviderProduct","Product" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String blur = DbUtil.getBlur(map, tmpMap, "ProductName");
			whereSql = whereSql + leftblur + blur + strCondition;
			whereSql = DbUtil.getWhereSql(whereSql); 

//			Object obj[] = (DbUtil.setDynamicPager(request, "ProviderProduct as pp,Product as p",
//					whereSql, pagesize, "ppsubCondition"));
//			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
//			whereSql = (String) obj[1];

			AppProviderProduct app = new AppProviderProduct();
			List pls = app.getProviderProductByProvideID(request, pagesize, whereSql);
			ArrayList sls = new ArrayList();

			for (int i = 0; i < pls.size(); i++) {
				ProviderProductForm ppf = new ProviderProductForm();
				Object object[] = (Object[])pls.get(i);
				Product o = (Product) object[0];
				ProviderProduct pp = (ProviderProduct) object[1];
				//ppf.setId();
				ppf.setProductid(o.getId());
				ppf.setPvproductname(o.getProductname());
				ppf.setPvspecmode(o.getSpecmode());
				ppf.setCountunit(pp.getCountunit());
				ppf.setUnitname(Internation.getStringByKeyPositionDB("CountUnit", 
						pp.getCountunit()));
				ppf.setBarcode("");
				ppf.setPrice(pp.getPrice());
				ppf.setPricedate("");
				sls.add(ppf);
			}
//					
//				  AppProductStruct appProductStruct = new AppProductStruct();
//					List uls = appProductStruct.getProductStructCanUse();
//			
//				  request.setAttribute("uls",uls);
			request.setAttribute("pvid", pvid);
			request.setAttribute("sls", sls);
			return mapping.findForward("selectproduct");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
