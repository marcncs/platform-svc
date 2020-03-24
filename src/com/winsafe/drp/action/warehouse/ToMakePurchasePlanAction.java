package com.winsafe.drp.action.warehouse;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPurchasePlan;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.dao.AppSaleOrderDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PurchasePlan;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class ToMakePurchasePlanAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MakeCode mc = new MakeCode();
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();

		super.initdata(request);try{
			AppUsers au = new AppUsers();
//			Long plandept = users.getMakedeptid();
			String soid = request.getParameter("SOID");

			
			PurchasePlan pp = new PurchasePlan();
			String ppid = mc
					.getExcIDByRandomTableName("purchase_plan", 2, "PP");
			pp.setId(ppid);
			pp.setPurchasesort(1);
			pp.setPlandate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
//			pp.setPlandept(plandept);
//			pp.setPlanid(userid);
			pp.setIsaudit(0);
//			pp.setAuditid(Long.valueOf(0));
			pp.setIsratify(0);
//			pp.setRatifyid(Long.valueOf(0));
			pp.setIscomplete(0);
			pp.setRemark("销售订单生成采购计划");

			AppPurchasePlan apa = new AppPurchasePlan();
			apa.addPurchasePlan(pp);

			
			AppSaleOrder aso = new AppSaleOrder();
			AppSaleOrderDetail asod = new AppSaleOrderDetail();
//			List ls = asod.getSaleOrderDetailBySOID(soid);


			
			String productid;
			String productname, specmode;
			Integer unitid;
			Double unitprice, quantity;
			Date requiredate = aso.getSaleOrderByID(soid).getConsignmentdate();

//			for (int i = 0; i < ls.size(); i++) {
//				Object[] o = (Object[]) ls.get(i);
//				productid = o[2].toString();
//				productname = o[3].toString();
//				specmode = o[4].toString();
//				unitid = Integer.valueOf(o[5].toString());
//				unitprice = 0.00;
//				quantity = Double.valueOf(o[7].toString());
//
//				PurchasePlanDetail ppd = new PurchasePlanDetail();
//				ppd.setId(Long.valueOf(mc.getExcIDByRandomTableName(
//						"purchase_plan_detail", 0, "")));
//				ppd.setPpid(ppid);
//				ppd.setProductid(productid);
//				ppd.setProductname(productname);
//				ppd.setSpecmode(specmode);
//				ppd.setUnitid(unitid);
//				ppd.setUnitprice(unitprice);
//				ppd.setQuantity(quantity);
//				ppd.setChangequantity(Double.valueOf(0));
//				ppd.setRequiredate(requiredate);
//				ppd.setAdvicedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
//				ppd.setRequireexplain("订单生成的采购计划");
//
//				AppPurchasePlanDetail apad = new AppPurchasePlanDetail();
//				apad.addPurchasePlanDetail(ppd);
//			}
//			
//			aso.updIsCompleteControl(soid);
			
			
			
			request.setAttribute("result", "databases.add.success");

			return mapping.findForward("topurchase");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return new ActionForward(mapping.getInput());
	}

}
