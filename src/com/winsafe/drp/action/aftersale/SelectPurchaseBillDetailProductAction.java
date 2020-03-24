package com.winsafe.drp.action.aftersale;

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

import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.AppPurchaseBillDetail;
import com.winsafe.drp.dao.PurchaseBillDetail;
import com.winsafe.drp.dao.PurchaseBillDetailForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectPurchaseBillDetailProductAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		
		String pid = request.getParameter("pid");	
		if ( pid == null ){
			pid = (String)request.getSession().getAttribute("pid");
		}
		request.getSession().setAttribute("pid", pid);
		 
		try {
			String strCondition = " s.id=d.pbid and d.productid=p.id and p.wise=0 and s.pid='"+pid+"' ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			// String sql = "select * from product where " + strCondition;
			String[] tablename = { "PurchaseBill", "PurchaseBillDetail","Product" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			//String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			//String blur = DbUtil.getBlur(map, tmpMap, "ProductName");
			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String blur = DbUtil.getOrBlur(map, tmpMap, "p.productname","p.specmode");

			whereSql = whereSql + blur + leftblur +strCondition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			Object obj[] = (DbUtil.setPager(request,
					"PurchaseBill as s,PurchaseBillDetail as d,Product as p", whereSql, pagesize));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppPurchaseBillDetail ap = new AppPurchaseBillDetail();
			//AppPurchaseBill appb = new AppPurchaseBill();
			List pls = ap.getPurchaseBillDetail(pagesize, whereSql, tmpPgInfo);
			ArrayList sls = new ArrayList();

			for (int i = 0; i < pls.size(); i++) {
				PurchaseBillDetail sod = (PurchaseBillDetail)pls.get(i);
				PurchaseBillDetailForm pf = new PurchaseBillDetailForm();				
				pf.setProductid(sod.getProductid());
				pf.setProductname(sod.getProductname());
				pf.setSpecmode(sod.getSpecmode());
				//pf.setBatch(appb.getPurchaseBillByID(sod.getPbid()).getBatch());
				pf.setUnitid(sod.getUnitid());
				pf.setUnitname(Internation.getStringByKeyPositionDB("CountUnit", sod.getUnitid()));
				pf.setUnitprice(sod.getUnitprice());
				pf.setQuantity(sod.getQuantity());				
				pf.setSubsum(sod.getSubsum());				
				sls.add(pf);
			}

			// 产品结构
			AppProductStruct appProductStruct = new AppProductStruct();
			List uls = appProductStruct.getProductStructCanUse();
			String brand = Internation.getSelectTagByKeyAllDB("Brand", "Brand", true);
	        request.setAttribute("brand", brand);
	        request.setAttribute("uls", uls);

			request.setAttribute("uls", uls);
			request.setAttribute("sls", sls);
			return mapping.findForward("selectproduct");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
