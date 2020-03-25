package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.MakeConf;
import com.winsafe.drp.dao.ProductStruct;
import com.winsafe.drp.service.ProductService;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.MakeCode; 

public class ToAddProductAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String psid = request.getParameter("PSID");
		try {
			initdata(request);
			ProductService productService = new ProductService();
			AppProductStruct appProductStruct = new AppProductStruct();
			ProductStruct ps = appProductStruct.getProductStructById(psid);

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

			request.setAttribute("psid", psid);
			request
					.setAttribute("psidname", ps != null ? ps.getSortname()
							: "");
			request.setAttribute("psidnameEn", ps != null ? ps.getSortnameen()
					: "");
			request.setAttribute("funitid", funitid);
			request.setAttribute("productTypes", productService
					.getProductTypes(users.getUserid()));

			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
