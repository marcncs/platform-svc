package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPurchaseIncomeIdcode;
import com.winsafe.drp.dao.PurchaseIncomeIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddPurchaseIncomeIdcodeiAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		AppPurchaseIncomeIdcode app = new AppPurchaseIncomeIdcode();
		
		super.initdata(request);try{
			
			String billid = request.getParameter("billid");
			String productid = request.getParameter("prid");
			int unitid = RequestTool.getInt(request, "unitid");
			String[] warehousebit = request.getParameterValues("warehousebit");
			String[] batch = request.getParameterValues("batch");
			String[] producedate = request.getParameterValues("producedate");
			double[] quantity = RequestTool.getDoubles(request, "quantity");
			PurchaseIncomeIdcode pi = null;
			app.delPurchaseIncomeIdcodeByPid(productid, billid);
			for ( int i=0; i<warehousebit.length; i++ ){
				pi = new PurchaseIncomeIdcode();
				pi.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("purchase_income_idcode", 0, "")));
				pi.setPiid(billid);
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
				app.addPurchaseIncomeIdcode(pi);
			}
			 request.setAttribute("result", "databases.add.success");
			 UsersBean users = UserManager.getUser(request);
			 DBUserLog.addUserLog(users.getUserid(),7, "入库>>采购入库>>分配仓位,产品编号:"+productid);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
