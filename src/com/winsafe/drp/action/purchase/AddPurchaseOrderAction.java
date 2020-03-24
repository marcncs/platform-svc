package com.winsafe.drp.action.purchase;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppApproveFlowService;
import com.winsafe.drp.dao.AppPurchaseOrder;
import com.winsafe.drp.dao.AppPurchaseOrderDetail;
import com.winsafe.drp.dao.PurchaseOrder;
import com.winsafe.drp.dao.PurchaseOrderDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddPurchaseOrderAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataValidate dv = new DataValidate();
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		String pid = request.getParameter("pid");
		if (pid == null || pid.equals("null") || pid.equals("")) {
			String result = "databases.add.fail";
			request.setAttribute("result", result);
			return new ActionForward("/sys/lockrecord.jsp");
		}
		String plinkman = request.getParameter("plinkman");
		Integer purchasedept = Integer.valueOf(request.getParameter("purchasedept"));
		Integer purchaseid = Integer.valueOf(request.getParameter("purchaseid"));
		Integer paymentmode = Integer.parseInt(request
				.getParameter("paymentmode"));
		String receivedate = request.getParameter("receivedate");
		String receiveaddr = request.getParameter("receiveaddr");
		String remark = request.getParameter("remark");
		String strtotalsum = request.getParameter("totalsum");
		String tel = request.getParameter("tel");
		String batch = "";
		Double totalsum;

		
		String strproductid[] = request.getParameterValues("productid");
		String strproductname[] = request.getParameterValues("productname");
		String strspecmode[] = request.getParameterValues("specmode");
		String strunitid[] = request.getParameterValues("unitid");
		String strunitprice[] = request.getParameterValues("unitprice");
		String strquantity[] = request.getParameterValues("quantity");		
		String strsubsum[] = request.getParameterValues("subsum");
		String productid;
		Integer unitid;
		Double unitprice, quantity, subsum;
		String productname, specmode;

		if (dv.IsDouble(strtotalsum)) {
			totalsum = Double.valueOf(strtotalsum);
		} else {
			totalsum = Double.valueOf(0.00);
		}

		AppApproveFlowService apfs = new AppApproveFlowService();
		List detaillist = apfs.getFlowDetail("PO");
		//Session 
		//Connection //
		

		try {
			PurchaseOrder pb = new PurchaseOrder();
			String poid = MakeCode.getExcIDByRandomTableName("purchase_order", 2,
					"PO");
			pb.setId(poid);
			pb.setPpid("");
			pb.setPid(pid);
			pb.setPlinkman(plinkman);
			pb.setTel(tel);
			pb.setBatch(batch);
			pb.setPurchasedept(purchasedept);
			pb.setPurchaseid(purchaseid);
			pb.setPaymentmode(paymentmode);
			pb.setTotalsum(totalsum);
			pb.setReceivedate(DateUtil.StringToDate(receivedate));
			pb.setReceiveaddr(receiveaddr);
			pb.setIsrefer(0);
			pb.setApprovestatus(0);
			pb.setMakeid(userid);
			pb.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			pb.setRemark(remark);
			pb.setIsendcase(0);
			pb.setIsblankout(0);
			pb.setIschange(0);

			AppPurchaseOrder apa = new AppPurchaseOrder();
			 apa.addPurchaseOrder(pb);

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
				if (dv.IsDouble(strquantity[i])) {
					quantity = Double.valueOf(strquantity[i]);
				} else {
					quantity = Double.valueOf(0);
				}
				
				PurchaseOrderDetail pbd = new PurchaseOrderDetail();
				pbd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"purchase_order_detail", 0, "")));
				pbd.setPoid(poid);
				pbd.setProductid(productid);
				pbd.setProductname(productname);
				pbd.setSpecmode(specmode);
				pbd.setUnitid(unitid);
				pbd.setUnitprice(unitprice);
				pbd.setQuantity(quantity);
				pbd.setIncomequantity(0d);
				pbd.setSubsum(subsum);

				AppPurchaseOrderDetail apbd = new AppPurchaseOrderDetail();
				 apbd.addPurchaseOrderDetail(pbd);
			}
			
		    apfs.referApproveFlow(detaillist, poid);
			
			request.setAttribute("result", "databases.add.success");

			//DBUserLog.addUserLog(userid, "新增采购单");

			return mapping.findForward("addresult");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}

		return new ActionForward(mapping.getInput());
	}
}
