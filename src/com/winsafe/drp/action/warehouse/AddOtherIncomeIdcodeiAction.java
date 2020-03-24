package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOtherIncomeIdcode;
import com.winsafe.drp.dao.OtherIncomeIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddOtherIncomeIdcodeiAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		AppOtherIncomeIdcode app = new AppOtherIncomeIdcode();
		
		super.initdata(request);try{
			
			String billid = request.getParameter("billid");
			String productid = request.getParameter("prid");
			int unitid = RequestTool.getInt(request, "unitid");
			String pbatch = request.getParameter("pbatch");
			String[] warehousebit = request.getParameterValues("warehousebit");
			String[] batch = request.getParameterValues("batch");
			String[] producedate = request.getParameterValues("producedate");
			double[] quantity = RequestTool.getDoubles(request, "quantity");
			OtherIncomeIdcode pi = null;
			app.delOtherIncomeIdcodeByPid(productid, billid, pbatch);
			for ( int i=0; i<warehousebit.length; i++ ){
				pi = new OtherIncomeIdcode();
				pi.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("other_income_idcode", 0, "")));
				pi.setOiid(billid);
				pi.setProductid(productid);
				pi.setIsidcode(0);
				pi.setWarehousebit(warehousebit[i]);
				pi.setBatch(pbatch);
				pi.setProducedate(producedate[i]);
				pi.setValidate("");
				pi.setUnitid(unitid);
				pi.setQuantity(quantity[i]);
				pi.setPackquantity(0d);
				pi.setMakedate(DateUtil.getCurrentDate());
				app.addOtherIncomeIdcode(pi);
			}
			 request.setAttribute("result", "databases.add.success");
			 UsersBean users = UserManager.getUser(request);
			 DBUserLog.addUserLog(users.getUserid(),7, "库存盘点>>盘盈单>>分配仓位,产品编号:"+productid);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
