package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
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
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class UpdProductIncomeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			String piid = request.getParameter("ID");
			AppProductIncome api = new AppProductIncome();
			ProductIncome pi = api.getProductIncomeByID(piid);
			ProductIncome oldP = (ProductIncome)BeanUtils.cloneBean(pi);

			pi.setId(piid);
			pi.setWarehouseid(request.getParameter("warehouseid"));
			pi.setHandwordcode(request.getParameter("handwordcode"));
			pi.setIncomedate(RequestTool.getDate(request, "incomedate"));
			pi.setIncomesort(RequestTool.getInt(request,"productincomesort"));
			pi.setRemark(request.getParameter("remark"));
			//pi.setBatch(request.getParameter("batch"));
			pi.setKeyscontent(pi.getId()+","+pi.getHandwordcode());
			
			String productid[] = request.getParameterValues("productid");
			String productname[] = request.getParameterValues("productname");
			String specmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request, "unitid");
			String batch[] = request.getParameterValues("batch");
			double quantity[] = RequestTool.getDoubles(request, "quantity");
			double costprice[] = RequestTool.getDoubles(request, "costprice");

			AppProductIncomeDetail apid = new AppProductIncomeDetail();
			apid.delProductIncomeDetailByPbID(piid);
			api.updProductIncomeByID(pi);
//			WarehouseBitDafService wbds = new WarehouseBitDafService("product_income_idcode","piid",pi.getWarehouseid());
//			wbds.del(pi.getId(), productid);
			

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
				pid.setBatch(batch[i]);
				pid.setCostsum(pid.getQuantity()*pid.getCostprice());

				apid.addProductIncomeDetail(pid);
//				wbds.add(pi.getId(), pid.getProductid(), pid.getUnitid(), pid.getQuantity());
			}

			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(userid, 7, "产成品入库>>修改产成品入库,编号："+piid,oldP,pi);
			return mapping.findForward("updresult");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return new ActionForward(mapping.getInput());
	}
}
