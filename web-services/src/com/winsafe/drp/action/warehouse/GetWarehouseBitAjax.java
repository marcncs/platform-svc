package com.winsafe.drp.action.warehouse;

	import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.server.WarehouseService;

	public class GetWarehouseBitAjax extends BaseAction{
		public ActionForward execute(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
			super.initdata(request);
			try{
				AppWarehouse awh = new AppWarehouse();

				WarehouseService aw = new WarehouseService();
				String productid = request.getParameter("productid");
				String warehouseid = request.getParameter("warehouseid");
				String stockpile = request.getParameter("stockpile");
				String strCondition = " where ps.productid='"+productid+"' and ps.warehouseid=" + warehouseid;
				
				AppProductStockpile aps = new AppProductStockpile();
				AppFUnit af = new AppFUnit();
				AppProduct ap = new AppProduct();
				
				ProductStockpile psp = new ProductStockpile();
				psp = aps.getProductStockpileByPIDWID(strCondition);
				String warehousebit = psp.getWarehousebit();
//				List ls = aps.getProductStockpileWithWbByPIDUId(productid,userid);
				List ls = awh.getAllWarehouseBitByWidWbid(warehouseid, warehousebit);
//				getProductStockpileByPIDWID

				Product p = ap.getProductByID(productid);
				
				JSONArray fulist = new JSONArray();
				JSONObject unitobj; 
	
				for (int i = 0; i < ls.size(); i++) {
					Object[] ob = (Object[]) ls.get(i);
//					double stockpile = Double.valueOf(ob[0].toString());
					unitobj = new JSONObject();
					unitobj.put("wname", aw.getWarehouseName(ob[0].toString()));
					unitobj.put("stockpile",stockpile);
					if(ob[2] == null ||(ob[2].toString().equals(""))){
						String warehouseBitName = "默认仓位";
						unitobj.put("warehouseBitName", warehouseBitName);
					}else{
						unitobj.put("warehouseBitName", ob[2].toString());
					}
					fulist.put(unitobj);

				}

				
				JSONObject json = new JSONObject();			
				json.put("pslist", fulist);		
				json.put("total", 0);	
				response.setContentType("text/html; charset=UTF-8");
			    response.setHeader("Cache-Control", "no-cache");
			    PrintWriter out = response.getWriter();
			    out.print(json.toString());
			    out.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}

	}

