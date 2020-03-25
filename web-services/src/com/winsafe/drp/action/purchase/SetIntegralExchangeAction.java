package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppIntegralExchange;
import com.winsafe.drp.dao.IntegralExchange;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.MakeCode;

public class SetIntegralExchangeAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		try {
			String productid = request.getParameter("productid");
			String productname = request.getParameter("productname");

			
			String strunitid[] = request.getParameterValues("unitid");
			String strunitintegral[] = request.getParameterValues("unitintegral");

			//Long pricepolicy;
			Integer countunit;
			Double unitintegral;

			AppIntegralExchange app = new AppIntegralExchange();
			
			app.delIntegralExchangeByProductID(productid);
			
			for (int i = 0; i < strunitid.length; i++) {
				countunit = Integer.valueOf(strunitid[i]);
				if (DataValidate.IsDouble(strunitintegral[i])) {
					unitintegral = Double.valueOf(strunitintegral[i]);
				} else {
					unitintegral = Double.valueOf(0.00);
				}

				IntegralExchange sod = new IntegralExchange();
				sod.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("integral_exchange", 0,"")));
				sod.setProductid(productid);
				sod.setUnitid(countunit);
				sod.setUnitintegral(unitintegral);
				
				app.addIntegralExchange(sod);
			}

			request.setAttribute("result", "databases.add.success");
			//DBUserLog.addUserLog(userid, "设置积分兑换");

			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
