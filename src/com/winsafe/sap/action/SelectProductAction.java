package com.winsafe.sap.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.pojo.SBonusTarget;
import com.winsafe.hbm.util.DbUtil;

public class SelectProductAction
    extends BaseAction {
	//DAO所需类
	AppProduct ap = new AppProduct();
	private AppBaseResource appBr = new AppBaseResource();

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
		//初始化
		int pagesize = 15;
		super.initdata(request);
		
		try {
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Product" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String blur = DbUtil.getOrBlur(map, tmpMap, "id", "ProductName",
					"mCode");
			whereSql = whereSql + leftblur + blur;
			whereSql = DbUtil.getWhereSql(whereSql);

			List<Product> list = ap.getProduct(request, pagesize, whereSql);
			
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			for(Product sbs : list) {
				sbs.setCountUnitName(countUnitMap.get(sbs.getCountunit()));
			}

			request.setAttribute("sls", list);
			return mapping.findForward("selectproduct");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);

  }
}
