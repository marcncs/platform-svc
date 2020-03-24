package com.winsafe.drp.action.purchase;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppApproveFlowService;
import com.winsafe.drp.dao.AppProviderProduct;
import com.winsafe.drp.dao.AppPurchaseInquire;
import com.winsafe.drp.dao.AppPurchaseOrder;
import com.winsafe.drp.dao.AppPurchaseOrderDetail;
import com.winsafe.drp.dao.PurchaseOrder;
import com.winsafe.drp.dao.PurchaseOrderDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class MakePurchaseOrderAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MakeCode mc = new MakeCode();
		AppPurchaseOrderDetail apbd = new AppPurchaseOrderDetail();
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		//Connection conn = null;
		try {
		
			PurchaseOrder pb = new PurchaseOrder();
		      String pbid = mc.getExcIDByRandomTableName("purchase_order",2,"PO");
		      pb.setId(pbid);
		      String ppid = request.getParameter("piid");
		      pb.setPpid(ppid);
		      String providerid = request.getParameter("pid");
		      pb.setPid(providerid);
		      pb.setPlinkman(request.getParameter("plinkman"));
		      pb.setPaymentmode(Integer.parseInt(request.getParameter("paymentmode")));
		      pb.setTotalsum(0.00);
		      pb.setReceivedate(DateUtil.StringToDate(request.getParameter("receivedate")));
		      pb.setReceiveaddr(request.getParameter("receiveaddr"));
		      pb.setIsrefer(0);
		      pb.setApprovestatus(0);	
		      pb.setMakeid(userid);
		      pb.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
		      pb.setRemark("采购询价转采购订单");
		      pb.setIsendcase(0);
		      pb.setIsblankout(0);

			String[] strproductid = request.getParameterValues("productid");
			String[] strproductname= request.getParameterValues("productname");
			String[] strspecmode = request.getParameterValues("specmode");
			String[] strunitid = request.getParameterValues("unitid");
			String[] strunitprice = request.getParameterValues("unitprice");
			String[] strquantity = request.getParameterValues("quantity");
			String[] strincomequantity = request.getParameterValues("incomequantity");
			String[] strsubsum= request.getParameterValues("subsum");
			
			String productid;
			Double price, quantity,subsum,incomequantity;
			Double totalsum = 0.00;

			AppApproveFlowService apfs = new AppApproveFlowService();
			List detaillist = apfs.getFlowDetail("PO");
			//Session 
			//
			

			AppProviderProduct app = new AppProviderProduct();
//			AppPurchaseOrder apa = new AppPurchaseOrder();
//		      String add = apa.addPurchaseOrder(pb);
			
			for (int i = 0; i < strproductid.length; i++) {
				PurchaseOrderDetail pbd = new PurchaseOrderDetail();
				pbd.setId(Integer.valueOf(mc.getExcIDByRandomTableName("purchase_order_detail",0,"")));
				pbd.setPoid(pbid);
				productid =strproductid[i];
				pbd.setProductid(productid);
				pbd.setProductname(strproductname[i]);
				pbd.setSpecmode(strspecmode[i]);
				pbd.setUnitid(Integer.valueOf(strunitid[i]));
				price = Double.valueOf(strunitprice[i]);
//				if(price ==null){
//					price = 0.00;
//				}
				pbd.setUnitprice(Double.valueOf(strunitprice[i]));
				quantity = Double.valueOf(strquantity[i]);
				incomequantity = Double.valueOf(strincomequantity[i]);
				pbd.setQuantity(quantity);
				pbd.setIncomequantity(incomequantity);
				subsum = price * quantity;
				pbd.setSubsum(subsum);
				totalsum += subsum;

				
				apbd.addPurchaseOrderDetail(pbd);
				
//				AppPurchasePlanDetail apad = new AppPurchasePlanDetail(); 
//				String upd = apad.updPurchasePlanDetailChangeQuantity(ppid,productid,quantity);
				

			}
			pb.setTotalsum(totalsum);
			
			//Integer iscomplete = Integer.valueOf(request.getParameter("iscomplete"));
			
			AppPurchaseInquire appl = new AppPurchaseInquire();
			appl.updIsComplete(ppid,1);
			
			AppPurchaseOrder apb = new AppPurchaseOrder();
			apb.addPurchaseOrder(pb);
			
		    apfs.referApproveFlow(detaillist, pbid);
			
			request.setAttribute("result", "databases.add.success");

			//DBUserLog.addUserLog(userid,"生成采购订单");
			

			return mapping.findForward("make");
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			//
			//  ConnectionEntityManager.close(conn);
		}
		return new ActionForward(mapping.getInput());
	}

}
