package com.winsafe.drp.action.sales;

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
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectLoanOutProductStockpileAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		
		 
		try {
			String strCondition = "  p.useflag=1 and p.wise=0 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Product" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			//String blur = DbUtil.getBlur(map, tmpMap, "ProductName");
			 String blur = DbUtil.getOrBlur(map, tmpMap, "ProductName","PYCode","SpecMode");
			 String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			whereSql = whereSql + blur + leftblur + strCondition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			Object obj[] = (DbUtil.setPager(request,
					"Product as p", whereSql, pagesize));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppProduct ap = new AppProduct();
//			List pls = ap.getProductnew(pagesize, whereSql, tmpPgInfo);
			ArrayList sls = new ArrayList();			

			Product ps = null;
//			for (int i = 0; i < pls.size(); i++) {
//				ps = (Product)pls.get(i);
//				ProductForm pf = new ProductForm();
//				pf.setId(ps.getId());				
//				pf.setProductname(ps.getProductname());
//				pf.setSpecmode(ps.getSpecmode());
//				pf.setCountunit(ps.getCountunit());
//				pf.setCountunitname(Internation.getStringByKeyPositionDB("CountUnit", 
//						ps.getCountunit()));			
//				sls.add(pf);
//			}
			
			String brand = Internation.getSelectTagByKeyAllDB("Brand", "Brand", true);
	        request.setAttribute("brand", brand);
//	      产品结构
			AppProductStruct appProductStruct = new AppProductStruct();
			List uls = appProductStruct.getProductStructCanUse();
			
			request.setAttribute("sls", sls);
			request.setAttribute("uls", uls);
			return mapping.findForward("selectproduct");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
