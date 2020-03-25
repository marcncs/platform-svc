package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductIntegral;
import com.winsafe.drp.dao.ProductIntegral;
import com.winsafe.drp.dao.ProductIntegralForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ProductIntegralAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String productid = request.getParameter("PID");
		request.getSession().setAttribute("pid", productid);
		try {
			AppProductIntegral api = new AppProductIntegral();

			AppProduct ap = new AppProduct();
			
			List pls = api.getProductIntegralByPID(productid);
			ArrayList apls = new ArrayList();
			for (int i = 0; i < pls.size(); i++) {

				ProductIntegralForm pf = new ProductIntegralForm();
				ProductIntegral p = (ProductIntegral) pls.get(i);

				if (p != null) {
					pf.setId(p.getId());
					pf.setProductid(p.getProductid());
					pf.setProductidname(ap.getProductByID(p.getProductid())
							.getProductname());
					pf.setUnitidname(Internation.getStringByKeyPositionDB(
							"CountUnit", p.getUnitid()));
					pf.setSalesortname(Internation.getStringByKeyPositionDB(
							"SaleSort", p.getSalesort()));
					pf.setIntegral(p.getIntegral());
					pf.setIntegralrate(p.getIntegralrate() * 100);
					
				}
				apls.add(pf);
			}

			request.setAttribute("apls", apls);

			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			//DBUserLog.addUserLog(userid, "存货详情");
			return mapping.findForward("integral");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
