package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProductIntegral;
import com.winsafe.drp.dao.ProductIntegral;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.MakeCode;

public class SetProductIntegralAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		try {
			//String pid = (String) request.getSession().getAttribute("pid");
			
			String productid = request.getParameter("productid");
			String productname = request.getParameter("productname");
			
			String strunitid[] = request.getParameterValues("unitid");
			String strsalesort[] = request.getParameterValues("salesort");
			String strintegral[] = request.getParameterValues("integral");
			String strintegralrate[] = request.getParameterValues("integralrate");
			
			Integer unitid;
			Double integral;
			Double integralrate;
			
			AppProductIntegral api = new AppProductIntegral();
			
			api.delProductIntegralBPID(productid);
			
			for (int i = 0; i < strunitid.length; i++) {
				unitid = Integer.valueOf(strunitid[i]);
				integral = Double.valueOf(strintegral[i]);
				integralrate =  Double.valueOf(strintegralrate[i]);

				ProductIntegral sod = new ProductIntegral();
				sod.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("product_integral", 0,"")));
				sod.setProductid(productid);
				sod.setUnitid(unitid);
				sod.setSalesort(Integer.valueOf(strsalesort[i]));
				sod.setIntegral(integral);
				sod.setIntegralrate(integralrate/100);
				
				api.addProductIntegral(sod);
			}
			

			request.setAttribute("result", "databases.add.success");
			//DBUserLog.addUserLog(userid, "新增产品积分");

			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
