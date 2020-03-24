package com.winsafe.drp.action.aftersale;

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
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.AppWithdraw;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class SelectWithdrawProductAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		String cid = request.getParameter("cid");
		if ( cid == null ){
			cid = (String)request.getSession().getAttribute("cid");
		}
		request.getSession().setAttribute("cid", cid);
		 super.initdata(request);
		try {
			String strCondition = " v.productid=p.id and p.wise=0 and v.cid='"+cid+"' ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "ViewWithdrawProduct", "Product" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			

			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String blur = DbUtil.getOrBlur(map, tmpMap, "v.productid","v.productname","v.specmode");

			whereSql = whereSql + blur + leftblur+strCondition;
			whereSql = DbUtil.getWhereSql(whereSql); 

//			Object obj[] = (DbUtil.setPager(request,
//					"ViewWithdrawProduct as v,Product as p", whereSql, pagesize));
//			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
//			whereSql = (String) obj[1];

			AppWithdraw ap = new AppWithdraw();
//			List pls = ap.getViewWithdrawProduct(request,pagesize, whereSql);
			ArrayList sls = new ArrayList();

//			for (int i = 0; i < pls.size(); i++) {
//				ViewWithdrawProduct sod = (ViewWithdrawProduct)pls.get(i);
//				SaleOrderDetailForm pf = new SaleOrderDetailForm();				
//				pf.setProductid(sod.getProductid());
//				pf.setProductname(sod.getProductname());
//				pf.setSpecmode(sod.getSpecmode());
//				pf.setBatch(sod.getBatch());
//				pf.setUnitid(sod.getUnitid());
//				pf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit", Integer.valueOf(sod.getUnitid().toString())));
//				pf.setUnitprice(sod.getUnitprice());
//				pf.setTaxunitprice(sod.getTaxunitprice());
//				pf.setQuantity(sod.getQuantity());
//				pf.setDiscount(sod.getDiscount());
//				pf.setTaxrate(sod.getTaxrate());
//				pf.setSubsum(sod.getSubsum());				
//				sls.add(pf);
//			}

			// 产品结构
			AppProductStruct appProductStruct = new AppProductStruct();
			List uls = appProductStruct.getProductStructCanUse();
//			String brand = Internation.getSelectTagByKeyAllDB("Brand", "Brand", true);
//	        request.setAttribute("brand", brand);

			request.setAttribute("uls", uls);
			request.setAttribute("sls", sls);
			return mapping.findForward("selectproduct");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
