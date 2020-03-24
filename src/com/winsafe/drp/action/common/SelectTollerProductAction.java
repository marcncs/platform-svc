package com.winsafe.drp.action.common;

import java.util.HashMap;
import java.util.List;  
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class SelectTollerProductAction extends BaseAction { 
	private static Logger logger = Logger.getLogger(SelectTollerProductAction.class);
	private AppFUnit appFUnit = new AppFUnit();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);
		try {
			
			String oid = request.getParameter("oid");
			if(StringUtil.isEmpty(oid)) {
				oid = users.getMakeorganid();
			}
			String Condition = " id in (select productid from OrganProduct where organid='"+oid+"') " ;

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Product" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String blur = DbUtil.getOrBlur2(map, tmpMap, "id", "productname",
					"pycode", "specmode", "mCode", "productnameen");

			whereSql = whereSql + leftblur + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppProduct ap = new AppProduct();
			List<Product> pls = ap.getProduct(request, pagesize, whereSql);
			
			if(pls != null && pls.size() > 0) {
				Map<String,FUnit> funitMap = appFUnit.getAllMapByUnitId(Constants.DEFAULT_UNIT_ID);
				for(Product product : pls) {
					//设置包装比例关系
					FUnit funit = funitMap.get(product.getId());
					if(funit != null) {
						product.setPackingRatio(funit.getXquantity().intValue());
					}
				}
			}
			
			request.setAttribute("sls", pls);
			
			request.setAttribute("KeyWordLeft", request
					.getParameter("KeyWordLeft"));
			return mapping.findForward("selectproduct");
		} catch (Exception e) {
			logger.error("",e);
		}

		return new ActionForward(mapping.getInput());
	}
}
