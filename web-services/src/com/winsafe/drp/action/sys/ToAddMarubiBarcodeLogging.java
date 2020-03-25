package com.winsafe.drp.action.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.hbm.util.DateUtil;

public class ToAddMarubiBarcodeLogging extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			super.initdata(request);
			AppOrganProduct aop = new AppOrganProduct();
			Map<String, String> marubiProductMap = new HashMap<String, String>();
			String Condition = " where op.organid='" + users.getMakeorganid() + "' and op.productid = p.id and p.useflag=1 and p.wise=0 ";
			List<Product> productList = aop.getOrganProductObj(Condition);
			for (Product product : productList) {
				marubiProductMap.put(product.getId(), product.getProductname());
			}
			//返回产品信息
			request.getSession().setAttribute("marubiProductMap", marubiProductMap);
			//返回页面日期
			request.setAttribute("date", DateUtil.getCurrentDateTime());
			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
