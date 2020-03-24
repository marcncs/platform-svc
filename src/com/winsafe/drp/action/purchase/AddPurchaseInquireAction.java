package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProviderProduct;
import com.winsafe.drp.dao.AppPurchaseInquire;
import com.winsafe.drp.dao.AppPurchaseInquireDetail;
import com.winsafe.drp.dao.PurchaseInquire;
import com.winsafe.drp.dao.PurchaseInquireDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddPurchaseInquireAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		try {
			String inquiretitle = request.getParameter("inquiretitle");
			String strpid = request.getParameter("pid");
			String pid = strpid;
			if (pid == null || pid.equals("null") || pid.equals("")) {
				String result = "databases.add.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			String plinkman = request.getParameter("plinkman");
			String ppid = request.getParameter("ppid");
			Integer validdate = Integer.valueOf(request
					.getParameter("validdate"));
			String remark = request.getParameter("remark");
			// String strtotalsum = request.getParameter("totalsum");
			double totalsum = 0.00;

			
			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			String strunitid[] = request.getParameterValues("unitid");
			String strunitprice[] = request.getParameterValues("unitprice");
			String strquantity[] = request.getParameterValues("quantity");
			String strsubsum[] = request.getParameterValues("subsum");
			String productid;
			Integer unitid;
			Double unitprice, quantity;
			String productname, specmode;

			PurchaseInquire pi = new PurchaseInquire();
			Integer piid = Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"purchase_inquire", 0, ""));
			pi.setId(piid);
			pi.setPpid(ppid);
			pi.setInquiretitle(inquiretitle);
			pi.setPid(pid);
			pi.setPlinkman(plinkman);
			pi.setMakedate(DateUtil.getCurrentDate());
			pi.setIsaudit(0);
			pi.setAuditid(Integer.valueOf(0));
			pi.setValiddate(validdate);
			pi.setMakeorganid(users.getMakeorganid());
			pi.setMakedeptid(users.getMakedeptid());
			pi.setMakeid(userid);
			pi.setRemark(remark);
			pi.setIscaseend(0);

			StringBuffer keyscontent = new StringBuffer();

			AppProviderProduct app = new AppProviderProduct();
			AppPurchaseInquireDetail apid = new AppPurchaseInquireDetail();
			if (strproductid != null) {
				for (int i = 0; i < strproductid.length; i++) {
					productid = strproductid[i];
					productname = strproductname[i];
					specmode = strspecmode[i];
					unitid = Integer.valueOf(strunitid[i]);
					if (DataValidate.IsDouble(strunitprice[i])) {
						unitprice = Double.valueOf(strunitprice[i]);
					} else {
						unitprice = Double.valueOf(0.00);
					}

					if (DataValidate.IsDouble(strquantity[i])) {
						quantity = Double.valueOf(strquantity[i]);
					} else {
						quantity = Double.valueOf(0);
					}

					PurchaseInquireDetail puid = new PurchaseInquireDetail();
					puid.setId(Integer.valueOf(MakeCode
							.getExcIDByRandomTableName(
									"purchase_inquire_detail", 0, "")));
					puid.setPiid(piid);
					puid.setProductid(productid);
					puid.setProductname(productname);
					puid.setSpecmode(specmode);
					puid.setUnitid(unitid);
					puid.setUnitprice(unitprice);
					puid.setQuantity(quantity);
					puid.setSubsum(Double.valueOf(strsubsum[i]));
					totalsum += puid.getSubsum();

					app.UpdProviderProduct(unitprice, pid, productid);
					apid.addPurchaseInquireDetail(puid);
				}
			}

			AppPurchaseInquire api = new AppPurchaseInquire();
			pi.setKeyscontent(keyscontent.toString());
			api.addPurchaseInquire(pi);

			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, 2, "采购管理>>新增采购查价,编号：" + pi.getId());

			return mapping.findForward("addresult");
		} catch (Exception e) {
			request.setAttribute("result", "databases.add.fail");
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
