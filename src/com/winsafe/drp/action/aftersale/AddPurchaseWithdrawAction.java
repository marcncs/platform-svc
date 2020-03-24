package com.winsafe.drp.action.aftersale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddPurchaseWithdrawAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		try {
			String pid = request.getParameter("pid");
			if (pid == null || pid.equals("null") || pid.equals("")) {
				String result = "databases.add.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			String pname = request.getParameter("pname");
			String linkman = request.getParameter("plinkman");
			String tel = request.getParameter("tel");
			String warehouseid = request.getParameter("warehouseid");
			String withdrawcause = request.getParameter("withdrawcause");
			// String remark = request.getParameter("remark");
			String strtotalsum = request.getParameter("totalsum");
			Double totalsum;

			
			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			// String strbatch[] = request.getParameterValues("batch");
			String strunitid[] = request.getParameterValues("unitid");
			String strunitprice[] = request.getParameterValues("unitprice");
			String strquantity[] = request.getParameterValues("quantity");
			String strsubsum[] = request.getParameterValues("subsum");
			String productid;
			String productname, specmode, batch;
			Integer unitid;
			Double quantity;
			Double unitprice, subsum;

			if (DataValidate.IsDouble(strtotalsum)) {
				totalsum = Double.valueOf(strtotalsum);
			} else {
				totalsum = Double.valueOf(0.00);
			}

			PurchaseWithdraw dp = new PurchaseWithdraw();
			String id = MakeCode.getExcIDByRandomTableName("purchase_withdraw",
					2, "PW");
			dp.setId(id);
			dp.setPid(pid);
			dp.setPname(pname);
			dp.setPlinkman(linkman);
			dp.setTel(tel);
			dp.setWarehouseid(warehouseid);
			dp.setWithdrawcause(withdrawcause);
			dp.setTotalsum(totalsum);
			dp.setMakeorganid(users.getMakeorganid());
			dp.setMakedeptid(users.getMakedeptid());
			dp.setMakeid(userid);
			dp.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			dp.setIsaudit(0);
			dp.setAuditid(Integer.valueOf(0));
			dp.setIsendcase(0);
			dp.setIsblankout(0);
			dp.setTakestatus(0);

			StringBuffer keyscontent = new StringBuffer();

			AppPurchaseWithdraw asl = new AppPurchaseWithdraw();
			AppPurchaseWithdrawDetail asld = new AppPurchaseWithdrawDetail();
			PurchaseWithdrawDetail sod = null;
			if (strproductid != null) {
				PurchaseWithdrawDetail[] purchewd = new PurchaseWithdrawDetail[strproductid.length];
				for (int i = 0; i < strproductid.length; i++) {
					productid = strproductid[i];
					productname = strproductname[i];
					specmode = strspecmode[i];
					batch = "";
					unitid = Integer.valueOf(strunitid[i]);
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
					if (DataValidate.Isdouble(strquantity[i])) {
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
					purchewd[i] = sod;

				}
				asld.addPurchaseWithdrawDetail(purchewd);
			}
			dp.setKeyscontent(keyscontent.toString());

			asl.addPurchaseWithdraw(dp);

			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, 2, "采购管理>>新增采购退货,编号：" + id);

			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return new ActionForward(mapping.getInput());
	}
}
