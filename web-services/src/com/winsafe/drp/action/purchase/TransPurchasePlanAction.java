package com.winsafe.drp.action.purchase;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchasePlan;
import com.winsafe.drp.dao.AppPurchasePlanDetail;
import com.winsafe.drp.dao.AppSaleIndent;
import com.winsafe.drp.dao.AppSaleIndentDetail;
import com.winsafe.drp.dao.PurchasePlan;
import com.winsafe.drp.dao.PurchasePlanDetail;
import com.winsafe.drp.dao.SaleIndent;
import com.winsafe.drp.dao.SaleIndentDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class TransPurchasePlanAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		String siid = request.getParameter("siid");

		try {
			AppSaleIndent apsi = new AppSaleIndent();
			SaleIndent si = apsi.getSaleIndentByID(siid);

			PurchasePlan pp = new PurchasePlan();
			String ppid = MakeCode.getExcIDByRandomTableName("purchase_plan",
					2, "PP");
			pp.setId(ppid);
			pp.setBillno(siid);

			pp.setPlandate(DateUtil.getCurrentDate());
			pp.setIsaudit(0);
			pp.setAuditid(0);
			pp.setIsratify(0);
			pp.setRatifyid(0);
			pp.setIscomplete(0);
			pp.setRemark("销售订单转采购计划");

			AppSaleIndentDetail apsid = new AppSaleIndentDetail();
			List sidlist = apsid.getSaleIndentDetailBySIID(siid);
			AppPurchasePlanDetail apad = new AppPurchasePlanDetail();
			for (int i = 0; i < sidlist.size(); i++) {
				SaleIndentDetail sid = (SaleIndentDetail) sidlist.get(i);
				PurchasePlanDetail ppd = new PurchasePlanDetail();
				ppd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"purchase_plan_detail", 0, "")));
				ppd.setPpid(ppid);
				ppd.setProductid(sid.getProductid());
				ppd.setProductname(sid.getProductname());
				ppd.setSpecmode(sid.getSpecmode());
				ppd.setUnitid(sid.getUnitid().intValue());
				ppd.setUnitprice(sid.getUnitprice());
				ppd.setQuantity(sid.getQuantity());
				ppd.setChangequantity(0d);
				ppd.setRequiredate(DateUtil.getCurrentDate());
				ppd.setAdvicedate(DateUtil.getCurrentDate());
				ppd.setRequireexplain("");
				// pad.setSubsum(subsum);
				apad.addPurchasePlanDetail(ppd);
			}
			AppPurchasePlan apa = new AppPurchasePlan();
			apa.addPurchasePlan(pp);
			
			
//			si.setIspurchaseplan(1);
			apsi.updSaleIndent(si);

			request.setAttribute("result", "databases.add.success");
			//DBUserLog.addUserLog(userid, "销售订单转采购计划");

			return mapping.findForward("addresult");
		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
