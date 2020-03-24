package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductIncome;
import com.winsafe.drp.dao.AppProductIncomeDetail;
import com.winsafe.drp.dao.ProductIncome;
import com.winsafe.drp.dao.ProductIncomeDetail;
import com.winsafe.drp.server.WarehouseBitDafService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class ExportProductIncomeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			ProductIncome pi = new ProductIncome();
			String piid = MakeCode.getExcIDByRandomTableName("product_income",
					2, "PE");
			pi.setId(piid);
			pi.setWarehouseid(request.getParameter("warehouseid"));
			pi.setHandwordcode(request.getParameter("handwordcode"));
			pi.setIncomedate(RequestTool.getDate(request, "incomedate"));
			pi.setIncomesort(RequestTool.getInt(request, "productincomesort"));
			pi.setRemark(request.getParameter("remark"));
			pi.setIsaudit(0);
			pi.setAuditid(0);
			pi.setMakeorganid(users.getMakeorganid());
			pi.setMakedeptid(users.getMakedeptid());
			pi.setMakeid(userid);
			pi.setMakedate(DateUtil.getCurrentDate());
			pi.setKeyscontent(pi.getId()+","+pi.getHandwordcode());

			
			String productid[] = request.getParameterValues("productid");
			String productname[] = request.getParameterValues("productname");
			String specmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request,"unitid");
			double quantity[] = RequestTool.getDoubles(request,"quantity");
			double costprice[] = RequestTool.getDoubles(request,"costprice");

			AppProductIncomeDetail apid = new AppProductIncomeDetail();
			WarehouseBitDafService wbds = new WarehouseBitDafService("product_income_idcode","piid",pi.getWarehouseid());
			for (int i = 0; i < productid.length; i++) {
				ProductIncomeDetail pid = new ProductIncomeDetail();
				pid.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"product_income_detail", 0, "")));
				pid.setPiid(piid);
				pid.setProductid(productid[i]);
				pid.setProductname(productname[i]);
				pid.setSpecmode(specmode[i]);
				pid.setUnitid(unitid[i]);
				pid.setQuantity(quantity[i]);
				pid.setCostprice(costprice[i]);
				pid.setCostsum(pid.getQuantity()*pid.getCostprice());

				apid.addProductIncomeDetail(pid);
				wbds.add(pi.getId(), pid.getProductid(), pid.getUnitid(), pid.getQuantity());

			}

			AppProductIncome api = new AppProductIncome();
			api.addProductIncome(pi);

			request.setAttribute("result", "databases.add.success");

			DBUserLog.addUserLog(userid, 7,"生产装配>>导入产成品入库,编号："+piid);
			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}

