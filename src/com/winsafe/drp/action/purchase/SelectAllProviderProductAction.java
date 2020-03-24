package com.winsafe.drp.action.purchase;

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
import com.winsafe.drp.dao.ProviderProductForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectAllProviderProductAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		initdata(request);
		try {
			String Condition = " p.wise=0 and p.useflag=1 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Product" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String blur = DbUtil.getOrBlur(map, tmpMap, "id","ProductName","SpecMode", "PYCode");
			whereSql = whereSql + leftblur + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppProduct app = new AppProduct();
			List pls = app.getSingleProduct(request, pagesize, whereSql);
			ArrayList sls = new ArrayList();

			for (int i = 0; i < pls.size(); i++) {
				ProviderProductForm ppf = new ProviderProductForm();
				Object[] o = (Object[]) pls.get(i);
				// ppf.setId(Long.valueOf(o[0].toString()));
				ppf.setProductid(o[0].toString());
				ppf.setPvproductname(o[1].toString());
				ppf.setPvspecmode(o[2].toString());
				ppf.setCountunit(Integer.parseInt(o[4].toString()));
				ppf.setUnitname(Internation.getStringByKeyPositionDB(
						"CountUnit", Integer.parseInt(o[4].toString())));
				ppf.setBarcode(String.valueOf(o[6]));
				ppf.setPrice(Double.valueOf(0.00));
				// ppf.setPricedate((Date)o[8]);
				sls.add(ppf);
			}

			AppProductStruct appProductStruct = new AppProductStruct();
			List uls = appProductStruct.getProductStructCanUse();

			request.setAttribute("uls", uls);
			// request.setAttribute("pid", pid);
			request.setAttribute("sls", sls);

			return mapping.findForward("selectproduct");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
