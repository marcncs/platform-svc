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
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.HtmlSelect;

public class GetStockQuantityAjax extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		super.initdata(request);
		try{

			WarehouseService aw = new WarehouseService();
			String productid = request.getParameter("productid");
			AppProductStockpileAll aps = new AppProductStockpileAll();
			AppFUnit af = new AppFUnit();
			AppProduct ap = new AppProduct();
			List ls = aps.getProductStockpileAllByPIDUId(productid,userid);
			Product p = ap.getProductByID(productid);
			
			JSONArray fulist = new JSONArray();
			JSONObject unitobj; 
			double total = 0.00;
			// 换算为件数
			String unitname = HtmlSelect.getResourceName(request, "CountUnit", Constants.DEFAULT_UNIT_ID);
			for (int i = 0; i < ls.size(); i++) {
				Object[] ob = (Object[]) ls.get(i);
				double stockpile = Double.valueOf(ob[0].toString());
				unitobj = new JSONObject();
				// 库存默认显示为件
//				unitobj.put("stockpile", af.getStockpileQuantity(request, productid, p.getSunit(), p.getCountunit(), stockpile));
				
				unitobj.put("stockpile", af.getDivQuantity(productid, Constants.DEFAULT_UNIT_ID, stockpile ) + "  " +unitname);
				unitobj.put("wname", aw.getWarehouseName(ob[1].toString()));
				unitobj.put("batch", ob[2].toString());
				fulist.put(unitobj);
				total +=stockpile;
			}
			
			JSONObject json = new JSONObject();			
			json.put("pslist", fulist);			
			// 库存默认显示为件
			json.put("total", af.getDivQuantity(productid, Constants.DEFAULT_UNIT_ID, total ) + "   " + unitname);
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
