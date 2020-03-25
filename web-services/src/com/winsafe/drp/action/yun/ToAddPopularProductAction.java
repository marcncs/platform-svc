package com.winsafe.drp.action.yun;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.dao.MakeConf;
import com.winsafe.drp.service.ProductService;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.MakeCode;

public class ToAddPopularProductAction extends BaseAction {

	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		try {
			initdata(request); 
			ProductService productService = new ProductService();

			String funitid = Internation.getSelectTagByKeyAllDB("CountUnit",
					"funitid", false);

			AppMakeConf amc = new AppMakeConf();
			MakeConf mc = amc.getMakeConfByID("product");
			String productid = "";
			String isread = "";
			if (mc.getRunmode() == 1) {
				productid = MakeCode
						.getExcIDByRandomTableName("product", 4, "");
				isread = "readonly";
			}

			request.setAttribute("mc", mc);
			request.setAttribute("productid", productid);
			request.setAttribute("isread", isread);

			request.setAttribute("psidname", null);
			request.setAttribute("psidnameEn", null);
			request.setAttribute("funitid", funitid);
			request.setAttribute("productTypes", productService
					.getProductTypes(users.getUserid()));

			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward();
	}
}
