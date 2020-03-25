package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganPrice;
import com.winsafe.drp.dao.AppOrganPriceHistory;
import com.winsafe.drp.dao.OrganPrice;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;

public class SetOrganPriceAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		try {
			String productid = request.getParameter("productid");
			String productname = request.getParameter("productname");

			
			String strunitid[] = request.getParameterValues("unitid");
			String strpolicyid[] = request.getParameterValues("policyid");
			String strunitprice[] = request.getParameterValues("unitprice");

			Integer pricepolicy;
			Integer countunit;
			Double unitprice;

			AppOrganPrice aop = new AppOrganPrice();
			AppOrganPriceHistory apoph = new AppOrganPriceHistory();
			
			aop.delOrganPriceByOIDPID(users.getMakeorganid(),productid);
			
			for (int i = 0; i < strunitid.length; i++) {
				countunit = Integer.valueOf(strunitid[i]);
				pricepolicy = Integer.valueOf(strpolicyid[i]);
				if (DataValidate.IsDouble(strunitprice[i])) {
					unitprice = Double.valueOf(strunitprice[i]);
				} else {
					unitprice = Double.valueOf(0.00);
				}

				OrganPrice sod = new OrganPrice();
				//sod.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("organ_price", 0,"")));
				sod.setOrganid(users.getMakeorganid());
				sod.setProductid(productid);
				sod.setUnitid(countunit);
				sod.setPolicyid(pricepolicy);
				sod.setUnitprice(unitprice);				
				aop.addOrganPrice(sod);
				
				
//	             OrganPriceHistory oph = new OrganPriceHistory();
//	             oph.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("organ_price_history",0,"")));
//	             oph.setOrganid(users.getMakeorganid());
//	             oph.setProductid(productid);
//	             oph.setUnitid(countunit);
//	             oph.setPolicyid(pricepolicy);
//	             oph.setUnitprice(unitprice);
//	             oph.setUserid(userid);
//	             oph.setModifydate(DateUtil.getCurrentDate());
//	             apoph.addOrganPriceHistory(oph);
			}

			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, 11, "商品资料>>设置机构价格");

			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
