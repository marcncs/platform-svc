package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.AppSaleOrderDetail;
import com.winsafe.drp.dao.SaleOrder;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.entity.HibernateUtil;

public class CheckSaleOrderAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			String soid = request.getParameter("soid");
			String strid[] = request.getParameterValues("id");
			String strquantity[] = request.getParameterValues("quantity");
			Long id;
			Double quantity;

			AppSaleOrderDetail appsod = new AppSaleOrderDetail();
			HibernateUtil.currentSession(true);
			double totalsum = 0.00;
			for (int i = 0; i < strid.length; i++) {
				id = Long.valueOf(strid[i]);
				if (DataValidate.IsDouble(strquantity[i])) {
					quantity = Double.valueOf(strquantity[i]);
				} else {
					quantity = Double.valueOf(0.00);
				}
//				SaleOrderDetail sod = (SaleOrderDetail) appsod
//						.getSaleOrderDetailByID(id);
//				sod.setQuantity(quantity);
//				sod.setSubsum(DataFormat.countPrice(quantity, sod
//						.getUnitprice(), sod.getDiscount(), sod.getTaxrate()));
//				totalsum += sod.getSubsum();
//				appsod.updSaleOrderDetail(sod);
			}

			AppSaleOrder appso = new AppSaleOrder();
			SaleOrder so = appso.getSaleOrderByID(soid);
			if (so != null) {
				so.setTotalsum(totalsum);
				appso.updSaleOrder(so);
			}

			
			request.setAttribute("result", "databases.upd.success");
			
//			Long userid = users.getUserid();
//			DBUserLog.addUserLog(userid, "核对销售单");

			HibernateUtil.commitTransaction();
			if (soid != null) {
				return new ActionForward(
						"/warehouse/auditTakeBillAction.do?id=" + soid);
			}

			return mapping.findForward("check");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession();
		}
		return null;
	}

}
