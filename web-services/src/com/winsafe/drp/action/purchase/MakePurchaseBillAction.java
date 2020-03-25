package com.winsafe.drp.action.purchase;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppProviderProduct;
import com.winsafe.drp.dao.AppPurchaseBill;
import com.winsafe.drp.dao.AppPurchaseBillDetail;
import com.winsafe.drp.dao.AppPurchasePlan;
import com.winsafe.drp.dao.AppPurchasePlanDetail;
import com.winsafe.drp.dao.PurchaseBill;
import com.winsafe.drp.dao.PurchaseBillDetail;
import com.winsafe.drp.dao.PurchasePlanDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class MakePurchaseBillAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		try {

			PurchaseBill pb = new PurchaseBill();
			String pbid = MakeCode.getExcIDByRandomTableName("purchase_bill",
					2, "PO");
			pb.setId(pbid);
			String ppid = request.getParameter("ppid");
			pb.setPpid(ppid);
			pb.setPid(request.getParameter("providerid"));
			pb.setPname(request.getParameter("pname"));
			pb.setPlinkman(request.getParameter("plinkman"));
			pb.setTel(request.getParameter("tel"));
			pb.setPurchasedept(RequestTool.getInt(request, "purchasedept"));
			pb.setPurchaseid(RequestTool.getInt(request, "purchaseid"));
			pb.setPaymode(RequestTool.getInt(request, "paymode"));
			pb.setInvmsg(RequestTool.getInt(request, "invmsg"));
			pb.setIsmaketicket(0);
			pb.setTotalsum(0.00);
			pb.setReceivedate(RequestTool.getDate(request, "receivedate"));
			pb.setReceiveaddr(request.getParameter("receiveaddr"));
			pb.setIsaudit(0);
			pb.setAuditid(0);
			pb.setIsratify(0);
			pb.setKeyscontent(pb.getId()+","+pb.getPid()+","+pb.getPname()+","+pb.getTel());
			
			AppProvider appProvider = new AppProvider();
			pb.setPrompt(appProvider.getProviderPrompt(pb.getPid()));
			pb.setRatifyid(0);
			pb.setMakeorganid(users.getMakeorganid());
			pb.setMakedeptid(users.getMakedeptid());
			pb.setMakeid(userid);
			pb.setMakedate(DateUtil.getCurrentDate());
			pb.setRemark("采购计划转采购单");
			pb.setIstransferadsum(0);

			String[] productid = request.getParameterValues("pid");
			String[] productname = request.getParameterValues("productname");
			String[] specmode = request.getParameterValues("specmode");
			int[] unitid = RequestTool.getInts(request, "unitid");
			double[] unitprice = RequestTool.getDoubles(request, "unitprice");
			double[] quantity = RequestTool.getDoubles(request, "purchasequantity");

			Double totalsum = 0.00;

			AppProviderProduct app = new AppProviderProduct();
			AppPurchasePlanDetail apad = new AppPurchasePlanDetail();

			

			List podlist = apad.getPurchasePlanDetailByPaID(ppid);
			PurchasePlanDetail pod = null;
			int totalquantity = 0;
			int totalincomequantity = 0;
			for (int i = 0; i < podlist.size(); i++) {
				pod = (PurchasePlanDetail) podlist.get(i);
				totalquantity += pod.getQuantity();
				totalincomequantity += pod.getChangequantity();
			}

			AppPurchaseBillDetail apbd = new AppPurchaseBillDetail();

			for (int i = 0; i < productid.length; i++) {
				PurchaseBillDetail pbd = new PurchaseBillDetail();
				pbd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"purchase_bill_detail", 0, "")));
				pbd.setPbid(pbid);
				pbd.setProductid(productid[i]);
				pbd.setProductname(productname[i]);
				pbd.setSpecmode(specmode[i]);
				pbd.setUnitid(unitid[i]);
				//price = app.getUnitpriceByProvideIDProductID(pvid, productid[i]);
	
				pbd.setUnitprice(unitprice[i]);
				pbd.setQuantity(quantity[i]);
				pbd.setSubsum(pbd.getUnitprice()*pbd.getQuantity());
				totalsum += pbd.getSubsum();
				totalincomequantity += quantity[i];

				apbd.addPurchaseBillDetail(pbd);

				
				apad.updPurchasePlanDetailChangeQuantity(ppid, pbd.getProductid(),
						pbd.getQuantity());

			}
			pb.setTotalsum(totalsum);

			
			AppPurchasePlan appl = new AppPurchasePlan();
			if (totalquantity == totalincomequantity) {
				appl.updIsComplete(ppid, 1);
			}

			
			AppPurchaseBill apb = new AppPurchaseBill();
			
			apb.addPurchaseBill(pb);

			request.setAttribute("result", "databases.add.success");

			DBUserLog.addUserLog(userid, 2, "采购计划转采购订单,编号：" + ppid);

			return mapping.findForward("make");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
