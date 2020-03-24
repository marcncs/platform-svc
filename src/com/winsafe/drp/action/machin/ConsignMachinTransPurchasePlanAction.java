package com.winsafe.drp.action.machin;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPurchasePlan;
import com.winsafe.drp.dao.AppPurchasePlanDetail;
import com.winsafe.drp.dao.PurchasePlan;
import com.winsafe.drp.dao.PurchasePlanDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class ConsignMachinTransPurchasePlanAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String billno = request.getParameter("id");
			String plandate = DateUtil.getCurrentDateString();// request.getParameter("plandate");
			String requiredate = request.getParameter("requiredate");
			String advicedate = request.getParameter("advicedate");
			int plandept = RequestTool.getInt(request, "plandept");
			int planid = RequestTool.getInt(request, "planid");
			String remark = request.getParameter("remark");

			
			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request, "unitid");
			double unitprice[] = RequestTool.getDoubles(request, "unitprice");
			double quantity[] = RequestTool.getDoubles(request,
					"advicequantity");

			String productid;
			String productname, specmode, requireexplain;

			PurchasePlan pp = new PurchasePlan();
			String ppid = MakeCode.getExcIDByRandomTableName("purchase_plan",
					2, "PP");
			pp.setId(ppid);
			pp.setBillno(billno);
			String tmpplandate = plandate.replace('-', '/');
			if (tmpplandate != null && tmpplandate.trim().length() > 0) {

				pp.setPlandate(new Date(tmpplandate));
			}
			pp.setPlandept(plandept);
			pp.setPlanid(planid);
			pp.setMakeorganid(users.getMakeorganid());
			pp.setMakedeptid(users.getMakedeptid());
			pp.setMakeid(userid);
			pp.setMakedate(DateUtil.getCurrentDate());
			pp.setIsaudit(0);
			pp.setAuditid(0);
			pp.setIsratify(0);
			pp.setRatifyid(0);
			pp.setIscomplete(0);
			pp.setRemark("委外加工单生成采购计划," + remark);

			AppPurchasePlan apa = new AppPurchasePlan();
			apa.addPurchasePlan(pp);

			AppPurchasePlanDetail apad = new AppPurchasePlanDetail();
			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				specmode = strspecmode[i];

				// requiredate = strrequiredate[i];
				// advicedate = stradvicedate[i];
				// requireexplain = strrequireexplain[i];
				if (quantity[i] > 0) {
					PurchasePlanDetail ppd = new PurchasePlanDetail();
					ppd.setId(Integer.valueOf(MakeCode
							.getExcIDByRandomTableName("purchase_plan_detail",
									0, "")));
					ppd.setPpid(ppid);
					ppd.setProductid(productid);
					ppd.setProductname(productname);
					ppd.setSpecmode(specmode);
					ppd.setUnitid(unitid[i]);
					ppd.setUnitprice(unitprice[i]);
					ppd.setQuantity(quantity[i]);
					ppd.setChangequantity(Double.valueOf(0));
					ppd.setRequiredate(DateUtil.StringToDate(requiredate));
					ppd.setAdvicedate(DateUtil.StringToDate(advicedate));
					// ppd.setRequireexplain(requireexplain);
					// pad.setSubsum(subsum);
					apad.addPurchasePlanDetail(ppd);
				}
			}

			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, 3, "委外加工>>委外加工单单转采购计划,编号：" + billno);

			return mapping.findForward("trans");
		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
