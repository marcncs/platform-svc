package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchaseOrder;
import com.winsafe.drp.dao.AppPurchaseOrderDetail;
import com.winsafe.drp.dao.PurchaseOrder;
import com.winsafe.drp.dao.PurchaseOrderDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class UpdPurchaseOrderAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataValidate dv = new DataValidate();
		MakeCode mc = new MakeCode();
		String pid = request.getParameter("pid");
		String id = request.getParameter("ID");

		AppPurchaseOrder apb = new AppPurchaseOrder();
		//Connection conn = null;
		
		try {
			
		if (pid == null || pid.equals("null") || pid.equals("")) {
			String result = "databases.upd.fail";
			request.setAttribute("result", "databases.upd.success");
			return new ActionForward("/sys/lockrecord.jsp");
		}

		PurchaseOrder pb = apb.getPurchaseOrderByID(id);
		pb.setPid(pid);
		pb.setPlinkman(request.getParameter("plinkman"));
		pb.setTel(request.getParameter("tel"));
		pb.setReceiveaddr(request.getParameter("receiveaddr"));
		pb.setReceivedate(DateUtil.StringToDate(request
				.getParameter("receivedate")));
		pb.setPaymentmode(Integer.valueOf(request.getParameter("paymentmode")));
		pb.setBatch("");
		pb.setRemark(request.getParameter("remark"));

		String strtotalsum = request.getParameter("totalsum");
		Double totalsum;
		if (dv.IsDouble(strtotalsum)) {
			totalsum = Double.valueOf(strtotalsum);
		} else {
			totalsum = Double.valueOf(0.00);
		}
		pb.setTotalsum(totalsum);

		
		String strproductid[] = request.getParameterValues("productid");
		String strproductname[] = request.getParameterValues("productname");
		String strspecmode[] = request.getParameterValues("specmode");
		String strunitid[] = request.getParameterValues("unitid");
		String strunitprice[] = request.getParameterValues("unitprice");
		String strquantity[] = request.getParameterValues("quantity");
		String strincomequantity[] = request.getParameterValues("incomequantity");
		String strsubsum[] = request.getParameterValues("subsum");
		String productid;
		Integer unitid;
		Double unitprice, subsum, quantity, incomequantity;
		String productname, specmode;

		if (dv.IsDouble(strtotalsum)) {
			totalsum = Double.valueOf(strtotalsum);
		} else {
			totalsum = Double.valueOf(0.00);
		}

			//Session 
			//
			
			
			AppPurchaseOrderDetail apbd = new AppPurchaseOrderDetail();
			apbd.delPurchaseOrderDetailByPoID(id);
			apb.updPurchaseOrder(pb);

			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				specmode = strspecmode[i];
				unitid = Integer.valueOf(strunitid[i]);
				if (dv.IsDouble(strunitprice[i])) {
					unitprice = Double.valueOf(strunitprice[i]);
				} else {
					unitprice = Double.valueOf(0.00);
				}
				if (dv.IsDouble(strsubsum[i])) {
					subsum = Double.valueOf(strsubsum[i]);
				} else {
					subsum = Double.valueOf(0.00);
				}
				if (dv.Isdouble(strquantity[i])) {
					quantity = Double.valueOf(strquantity[i]);
				} else {
					quantity = Double.valueOf(0);
				}
				if (dv.Isdouble(strincomequantity[i])) {
					incomequantity = Double.valueOf(strincomequantity[i]);
				} else {
					incomequantity = Double.valueOf(0);
				}

				PurchaseOrderDetail pbd = new PurchaseOrderDetail();
				pbd.setId(Integer.valueOf(mc.getExcIDByRandomTableName(
						"purchase_order_detail", 0, "")));
				pbd.setPoid(id);
				pbd.setProductid(productid);
				pbd.setProductname(productname);
				pbd.setSpecmode(specmode);
				pbd.setUnitid(unitid);
				pbd.setUnitprice(unitprice);
				pbd.setQuantity(quantity);
				pbd.setIncomequantity(incomequantity);
				pbd.setSubsum(subsum);
				apbd.addPurchaseOrderDetail(pbd);
			}

			
			request.setAttribute("result", "databases.upd.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			//DBUserLog.addUserLog(userid, "修改采购单");
			
			return mapping.findForward("updresult");
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			//
			
		}

		return new ActionForward(mapping.getInput());
	}
}
