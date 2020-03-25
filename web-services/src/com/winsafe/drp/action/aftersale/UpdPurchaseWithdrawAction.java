package com.winsafe.drp.action.aftersale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchaseWithdraw;
import com.winsafe.drp.dao.AppPurchaseWithdrawDetail;
import com.winsafe.drp.dao.PurchaseWithdraw;
import com.winsafe.drp.dao.PurchaseWithdrawDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.MakeCode;

public class UpdPurchaseWithdrawAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		AppPurchaseWithdraw aso = new AppPurchaseWithdraw();

		try {
			PurchaseWithdraw dp = aso.getPurchaseWithdrawByID(id);
			PurchaseWithdraw olddp = (PurchaseWithdraw) BeanUtils.cloneBean(dp);
			String pid = request.getParameter("pid");
			if (pid == null || pid.equals("null") || pid.equals("")) {
				String result = "databases.upd.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			String linkman = request.getParameter("plinkman");
			String tel = request.getParameter("tel");
			String warehouseid = request.getParameter("warehouseid");
			String withdrawcause = request.getParameter("withdrawcause");
			String strtotalsum = request.getParameter("totalsum");
			Double totalsum;
			if (DataValidate.IsDouble(strtotalsum)) {
				totalsum = Double.valueOf(strtotalsum);
			} else {
				totalsum = Double.valueOf(0.00);
			}

			dp.setPid(pid);
			dp.setPlinkman(linkman);
			dp.setTel(tel);
			dp.setWarehouseid(warehouseid);
			dp.setWithdrawcause(withdrawcause);
			dp.setTotalsum(totalsum);

			
			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			String strunitid[] = request.getParameterValues("unitid");
			String strunitprice[] = request.getParameterValues("unitprice");
			String strquantity[] = request.getParameterValues("quantity");
			String strsubsum[] = request.getParameterValues("subsum");
			String productid;
			String productname, specmode, batch;
			Integer unitid;
			Double quantity;
			Double unitprice, subsum;

			StringBuffer keyscontent = new StringBuffer();

			AppPurchaseWithdrawDetail asld = new AppPurchaseWithdrawDetail();
			asld.delPurchaseWithdrawDetailByPWID(id);
			PurchaseWithdrawDetail sod = null;
			if (strproductid != null) {
				PurchaseWithdrawDetail[] purchasewd = new PurchaseWithdrawDetail[strproductid.length];
				for (int i = 0; i < strproductid.length; i++) {
					productid = strproductid[i];
					productname = strproductname[i];
					specmode = strspecmode[i];
					unitid = Integer.valueOf(strunitid[i]);
					batch = "";
					if (DataValidate.IsDouble(strunitprice[i])) {
						unitprice = Double.valueOf(strunitprice[i]);
					} else {
						unitprice = Double.valueOf(0.00);
					}
					if (DataValidate.IsDouble(strsubsum[i])) {
						subsum = Double.valueOf(strsubsum[i]);
					} else {
						subsum = Double.valueOf(0.00);
					}
					if (DataValidate.IsDouble(strquantity[i])) {
						quantity = Double.valueOf(strquantity[i]);
					} else {
						quantity = Double.valueOf(0);
					}

					sod = new PurchaseWithdrawDetail();
					sod.setId(Integer.valueOf(MakeCode
							.getExcIDByRandomTableName(
									"purchase_withdraw_detail", 0, "")));
					sod.setPwid(id);
					sod.setProductid(productid);
					sod.setProductname(productname);
					sod.setSpecmode(specmode);
					sod.setBatch(batch);
					sod.setUnitid(unitid);
					sod.setUnitprice(unitprice);
					sod.setQuantity(quantity);
					sod.setSubsum(subsum);
					sod.setTakequantity(0d);
					purchasewd[i] = sod;

				}
				asld.addPurchaseWithdrawDetail(purchasewd);
			}
			dp.setKeyscontent(keyscontent.toString());
			aso.updPurchaseWithdraw(dp);

			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(userid, 2, "采购管理>>修改采购退货,编号：" + id, olddp, dp);

			return mapping.findForward("updresult");
		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
