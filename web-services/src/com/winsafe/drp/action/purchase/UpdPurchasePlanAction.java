package com.winsafe.drp.action.purchase;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
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

public class UpdPurchasePlanAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {

			AppPurchasePlan apa = new AppPurchasePlan();
			String id = request.getParameter("ID");
			PurchasePlan pp = apa.getPurchasePlanByID(id);
			PurchasePlan oldpp = (PurchasePlan) BeanUtils.cloneBean(pp);
			pp.setBillno(request.getParameter("billno"));
			pp.setPlandate(DateUtil.StringToDate(request
					.getParameter("plandate")));
			pp.setPurchasesort(Integer.valueOf(request
					.getParameter("purchasesort")));
			pp.setPlandept(Integer.valueOf(request.getParameter("plandept")));
			pp.setPlanid(Integer.valueOf(request.getParameter("planid")));
			pp.setRemark(request.getParameter("remark"));

			
			String productid[] = request.getParameterValues("productid");
			String productname[] = request.getParameterValues("productname");
			String specmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request, "unitid");
			double unitprice[] = RequestTool.getDoubles(request, "unitprice");
			double quantity[] = RequestTool.getDoubles(request, "quantity");
			Date requiredate[] = RequestTool.getDates(request, "requiredate");
			Date advicedate[] = RequestTool.getDates(request, "advicedate");
			String requireexplain[] = request.getParameterValues("requireexplain");

			
			StringBuffer keyscontent = new StringBuffer();
			keyscontent.append(pp.getId());

			AppPurchasePlanDetail apad = new AppPurchasePlanDetail();
			apad.delPurchasePlanDetailByPpID(id);
			double totalsum=0.00;
			if (productid != null) {
				PurchasePlanDetail[] ppds = new PurchasePlanDetail[productid.length];
				for (int i = 0; i < productid.length; i++) {					
					PurchasePlanDetail ppd = new PurchasePlanDetail();
					ppd.setId(Integer.valueOf(MakeCode
							.getExcIDByRandomTableName("purchase_plan_detail",
									0, "")));
					ppd.setPpid(id);
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
			pp.setTotalsum(totalsum);
			pp.setKeyscontent(keyscontent.toString());

			apa.updPurchasePlan(pp);
			request.setAttribute("result", "databases.upd.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 5, "采购管理>>修改采购计划,编号：" + pp.getId(),
					oldpp, pp);

			return mapping.findForward("updresult");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
