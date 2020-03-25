package com.winsafe.drp.action.aftersale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppSaleTradesIdcode;
import com.winsafe.drp.dao.SaleTradesIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddSaleTradesIdcodeiAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		AppSaleTradesIdcode app = new AppSaleTradesIdcode();
		
		try {
			
			String billid = request.getParameter("billid");
			String productid = request.getParameter("prid");
			int unitid = RequestTool.getInt(request, "unitid");
			String[] warehousebit = request.getParameterValues("warehousebit");
			String[] batch = request.getParameterValues("batch");
			String[] producedate = request.getParameterValues("producedate");
			double[] quantity = RequestTool.getDoubles(request, "quantity");
			SaleTradesIdcode pi = null;
			app.delSaleTradesIdcodeByPid(productid, billid);
			for ( int i=0; i<warehousebit.length; i++ ){
				pi = new SaleTradesIdcode();
				pi.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("sale_trades_idcode", 0, "")));
				pi.setStid(billid);
				pi.setProductid(productid);
				pi.setIsidcode(0);
				pi.setWarehousebit(warehousebit[i]);
				pi.setBatch(batch[i]);
				pi.setProducedate(producedate[i]);
				pi.setValidate("");
				pi.setUnitid(unitid);
				pi.setQuantity(quantity[i]);
				pi.setPackquantity(0d);
				pi.setMakedate(DateUtil.getCurrentDate());
				app.addSaleTradesIdcode(pi);
			}
			 request.setAttribute("result", "databases.add.success");
			 UsersBean users = UserManager.getUser(request);
			 DBUserLog.addUserLog(users.getUserid(),6, "销售换货>>分配仓位,产品编号:"+productid);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
