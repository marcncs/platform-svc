package com.winsafe.drp.action.common;

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
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.hbm.util.DbUtil;

public class SelectYunProductAction extends BaseAction { 
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);
		try {
			
			Map<String,Object> param = new HashMap<>();
			String Condition = "  id not in (select id from PopularProduct) and standardName is not null and useflag=1  ";
			

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Product" };
			String whereSql = EntityManager.getTmpWhereSqlForHql(map, tablename, param);
			String blur = DbUtil.getOrBlur2ForHql(map, tmpMap, param, "productname");

			whereSql = whereSql + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppProduct ap = new AppProduct();
			List<Product> pls = ap.getProduct(request, pagesize, whereSql, param);
			
			request.setAttribute("sls", pls);
			
			return mapping.findForward("selectproduct");
		} catch (Exception e) {
			WfLogger.error("",e);
		}

		return new ActionForward(mapping.getInput());
	}
}
