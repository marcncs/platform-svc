package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProductIntegral;
import com.winsafe.drp.dao.AppProductPrice;
import com.winsafe.drp.dao.AppProductPriceHistory;
import com.winsafe.drp.dao.ProductIntegral;
import com.winsafe.drp.dao.ProductPrice;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.MakeCode;

public class SetProductPriceAction extends Action {

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

			AppProductPrice app = new AppProductPrice();
			AppProductIntegral api = new AppProductIntegral();
			AppProductPriceHistory aph = new AppProductPriceHistory();
			
			app.delProductPriceByProductID(productid);
			
			for (int i = 0; i < strunitid.length; i++) {
				countunit = Integer.valueOf(strunitid[i]);
				pricepolicy = Integer.valueOf(strpolicyid[i]);
				if (DataValidate.IsDouble(strunitprice[i])) {
					unitprice = Double.valueOf(strunitprice[i]);
				} else {
					unitprice = Double.valueOf(0.00);
				}

				ProductPrice sod = new ProductPrice();
				sod.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("product_price", 0,"")));
				sod.setProductid(productid);
				sod.setUnitid(countunit);
				sod.setPolicyid(pricepolicy);
				sod.setUnitprice(unitprice);				
				app.addProductPrice(sod);
				
				
//				ProductPriceHistory pph = new ProductPriceHistory();
//				pph.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("product_price_history", 0,"")));
//				pph.setProductid(productid);
//				pph.setUnitid(countunit);
//				pph.setPolicyid(pricepolicy);
//				pph.setUnitprice(unitprice);
//				pph.setUserid(userid);
//				pph.setModifydate(DateUtil.getCurrentDate());
//				aph.addProductPriceHistory(pph);
				
				
				if ( pricepolicy == 1 ){
					
					api.delProductIntegralByPIDUIDSSID(sod.getProductid(), sod.getUnitid(), 0);
					
					ProductIntegral pi = new ProductIntegral();
					pi.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("product_integral", 0,"")));
					pi.setProductid(sod.getProductid());
					pi.setUnitid(sod.getUnitid());
					pi.setSalesort(0);
					pi.setIntegral(sod.getUnitprice());
					pi.setIntegralrate(1d);
					api.addProductIntegral(pi);
				}
			}


			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, 11, "产品资料>>设置零售产品价格,产品编号:"+productid);

			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
