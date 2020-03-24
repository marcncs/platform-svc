package com.winsafe.drp.action.warehouse;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.StringUtil;

public class AjaxCheckStockpile extends BaseAction {
	
	private static Logger logger = Logger.getLogger(AjaxCheckStockpile.class);
	
    private AppWarehouse aw = new AppWarehouse();
    private AppProductStockpileAll appPsp = new AppProductStockpileAll();
    private AppFUnit appFunit = new AppFUnit();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
			logger.debug("stockAlterMove check stockpile use ajax!");
			StringBuffer errorMsg = new StringBuffer();
			//获取出库仓库
			String outWarehouseId = request.getParameter("outwarehouseid");
			if (StringUtil.isEmpty(outWarehouseId)) {
				outWarehouseId = request.getParameter("warehouseid");
			}
			Warehouse outWarehouse = aw.getWarehouseByID(outWarehouseId);
			
			// 如果仓库属性[是否负库存]为0,则检查库存
			if(outWarehouse.getIsMinusStock() == null || outWarehouse.getIsMinusStock() == 0){
				String[] strproductid = request.getParameterValues("productid");
				String[] strproductname = request.getParameterValues("productname");
				String[] strunitid = request.getParameterValues("unitid");
				String[] strquantity = request.getParameterValues("quantity");
				
				for (int i = 0; i < strproductid.length; i++) {
					String productid = strproductid[i];
					Integer unitid = Integer.valueOf(strunitid[i]);
					Double quantity = Double.valueOf(strquantity[i]);
					String productname = strproductname[i];
					// 库存数量
					Double stockQuantity = appPsp.getProductStockpileAllByProductID(productid, Long.valueOf(outWarehouseId));
					//单位换算
					Double realQuantity = appFunit.getQuantity(productid, unitid, quantity);
					if(stockQuantity < realQuantity){
						errorMsg.append("产品[ " + productid + "   " + productname +" ]库存不足 \r\n");
					}
				}
			}
			// 以json数据返回
			JSONObject jsonObject = new JSONObject();
			if(errorMsg.length() > 0){
				jsonObject.put("returnCode", "-1");
				jsonObject.put("returnMsg", errorMsg.toString());
			}else {
				jsonObject.put("returnCode", "0");
				jsonObject.put("returnMsg", "");
			}
			ResponseUtil.write(response, jsonObject.toString());
			
			return null;
		
	}
}
