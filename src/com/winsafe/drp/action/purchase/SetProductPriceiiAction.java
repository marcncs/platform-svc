package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProductPriceii;
import com.winsafe.drp.dao.ProductPriceii;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class SetProductPriceiiAction extends Action {

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
			double frate[] = RequestTool.getDoubles(request,"frate");

			Integer pricepolicy;
			Integer countunit;
			Double unitprice;

			AppProductPriceii app = new AppProductPriceii();
			
			app.delProductPriceiiByProductID(productid);
			
			for (int i = 0; i < strunitid.length; i++) {
				countunit = Integer.valueOf(strunitid[i]);
				pricepolicy = Integer.valueOf(strpolicyid[i]);
				if (DataValidate.IsDouble(strunitprice[i])) {
					unitprice = Double.valueOf(strunitprice[i]);
				} else {
					unitprice = Double.valueOf(0.00);
				}

				ProductPriceii sod = new ProductPriceii();
				sod.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("product_priceii", 0,"")));
				sod.setProductid(productid);
				sod.setUnitid(countunit);
				sod.setPolicyid(pricepolicy);
				sod.setUnitprice(unitprice);
				sod.setFrate(frate[i]/100);
				app.addProductPriceii(sod);
				
				
//				ProductPriceiiHistory pph = new ProductPriceiiHistory();
//				pph.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("product_price_history", 0,"")));
//				pph.setProductid(productid);
//				pph.setUnitid(countunit);
//				pph.setPolicyid(pricepolicy);
//				pph.setUnitprice(unitprice);
//				pph.setUserid(userid);
//				pph.setModifydate(DateUtil.getCurrentDate());
//				aph.addProductPriceiiHistory(pph);
				
			}


			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, 11, "产品资料>>设置经销商产品价格,产品编号:"+productid);

			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
