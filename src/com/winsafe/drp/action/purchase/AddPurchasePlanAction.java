package com.winsafe.drp.action.purchase;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPurchasePlan;
import com.winsafe.drp.dao.AppPurchasePlanDetail;
import com.winsafe.drp.dao.PurchasePlan;
import com.winsafe.drp.dao.PurchasePlanDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddPurchasePlanAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		try {
			PurchasePlan pp = new PurchasePlan();
			String ppid = MakeCode.getExcIDByRandomTableName("purchase_plan",
					2, "PP");
			pp.setId(ppid);
			pp.setBillno(request.getParameter("billno"));
			pp.setPurchasesort(RequestTool.getInt(request, "purchasesort"));
			pp.setPlandate(RequestTool.getDate(request, "plandate"));
			pp.setPlandept(RequestTool.getInt(request, "plandept"));
			pp.setPlanid(RequestTool.getInt(request, "planid"));
			pp.setMakeorganid(users.getMakeorganid());
			pp.setMakedeptid(users.getMakedeptid());
			pp.setMakedate(DateUtil.getCurrentDate());
			pp.setMakeid(userid);
			pp.setIsaudit(0);
			pp.setAuditid(0);
			pp.setIsratify(0);
			pp.setRatifyid(0);
			pp.setIscomplete(0);
			pp.setRemark(request.getParameter("remark"));
			StringBuffer keyscontent = new StringBuffer();
			keyscontent.append(pp.getId()).append(",");
			
			
			String productid[] = request.getParameterValues("productid");
			String productname[] = request.getParameterValues("productname");
			String specmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request, "unitid");
			double unitprice[] = RequestTool.getDoubles(request, "unitprice");
			double quantity[] = RequestTool.getDoubles(request, "quantity");
			Date requiredate[] = RequestTool.getDates(request, "requiredate");
			Date advicedate[] = RequestTool.getDates(request, "advicedate");
			String requireexplain[] = request.getParameterValues("requireexplain");

			AppPurchasePlanDetail apad = new AppPurchasePlanDetail();
			double totalsum=0.00;
			if (productid != null) {
				PurchasePlanDetail[] ppds = new PurchasePlanDetail[productid.length];
				for (int i = 0; i < productid.length; i++) {					
					PurchasePlanDetail ppd = new PurchasePlanDetail();
					ppd.setId(Integer.valueOf(MakeCode
							.getExcIDByRandomTableName("purchase_plan_detail",
									0, "")));
					ppd.setPpid(ppid);
					ppd.setProductid(productid[i]);
					ppd.setProductname(productname[i]);
					ppd.setSpecmode(specmode[i]);
					ppd.setUnitid(unitid[i]);
					ppd.setUnitprice(unitprice[i]);
					ppd.setQuantity(quantity[i]);
					ppd.setChangequantity(0d);
					ppd.setRequiredate(requiredate[i]);
					ppd.setAdvicedate(advicedate[i]);
					ppd.setRequireexplain(requireexplain[i]);
					ppds[i] = ppd;
					totalsum+=ppd.getQuantity()*ppd.getUnitprice();
				}
				apad.addPurchasePlanDetail(ppds);
			}

			AppPurchasePlan apa = new AppPurchasePlan();
			pp.setTotalsum(totalsum);
			pp.setKeyscontent(keyscontent.toString());
			apa.addPurchasePlan(pp);

			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, 2, "采购管理>>新增采购计划，编号：" + pp.getId());

			return mapping.findForward("addresult");
		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
