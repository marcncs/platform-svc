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
import com.winsafe.drp.dao.ProductForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class PrintFindPriceAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String strorganid = request.getParameter("equiporganid");
			String organid = "";
			if (strorganid != null && strorganid.length() > 0) {
				organid = strorganid;
			} else {
				organid = users.getMakeorganid();
			}

			String strCondition = " p.id=op.productid and op.policyid=1 and organid='"
					+ organid + "' and p.useflag=1 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Product", "OrganPrice" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String priceCondition = DbUtil.getPriceCondition(map, tmpMap,
					"UnitPrice");
			String blur = DbUtil.getOrBlur(map, tmpMap, "p.ID","op.ID","ProductName",
					"PYCode", "SpecMode");
			whereSql = whereSql + leftblur + blur + priceCondition
					+ strCondition;
			whereSql = DbUtil.getWhereSql(whereSql);
			AppProduct ap = new AppProduct();
			List pls = ap.findProductPrice(whereSql);
			ArrayList sls = new ArrayList();
			Double pricei = 0d;
			Double priceii = 0d;

			for (int i = 0; i < pls.size(); i++) {
				ProductForm pf = new ProductForm();
				Object[] o = (Object[]) pls.get(i);
				pf.setId(String.valueOf(o[0]));
				pf.setProductname(o[1].toString());
				pf.setSpecmode(String.valueOf(o[2]));
				pf.setCountunit(Integer.valueOf(o[3].toString()));
				pf.setCountunitname(HtmlSelect.getResourceName(request,
						"CountUnit", Integer.parseInt(o[3].toString())));
				pricei = ap.getProductPriceByOIDPIDRate(users.getMakeorganid(),
						pf.getId(), pf.getCountunit(), 0);
				pf.setPricei(pricei);
				priceii = Double.valueOf(o[4].toString());
				pf.setPriceii(priceii);
				sls.add(pf);
			}
			DBUserLog.addUserLog(userid, 6, "零售管理>>打印零售查价!");
			request.setAttribute("sls", sls);
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();

		}
		return new ActionForward(mapping.getInput());
	}
}
