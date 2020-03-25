package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetailBatchBit;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpileForm;
import com.winsafe.drp.dao.ProductStruct;
import com.winsafe.drp.dao.TakeTicketDetailBatchBit;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListRepairByTaketicketAction extends BaseAction{
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化参数
		super.initdata(request);
		//分页
		int pageSize=15;
		//初始化各查询类
		AppWarehouse aw = new AppWarehouse();
		AppProduct ap = new AppProduct();
		AppTakeTicket att  = new AppTakeTicket();
		AppProductStruct aps = new AppProductStruct();
		AppProductStockpile apsa = new AppProductStockpile();
		try {
			//页面接收两个表中的属性值
			String[] tablename = { "TakeTicketDetailBatchBit", "WarehouseVisit" };
			
			//获取页面检索条件中的参数
			String productID = request.getParameter("ProductID");
			String outOrganID = request.getParameter("MakeOrganID");
			String keyWordLeft = request.getParameter("KeyWordLeft");
			String outWarehouseID = request.getParameter("WarehouseID");
			String isShowAssistQuantity = request.getParameter("isShowAssistQuantity");
			String orderSql = request.getParameter("orderbySql");
			String orderSqlName = request.getParameter("orderbySqlShowName");
			
			String whereSql1 = getWhereSql(tablename);
			//String whereSql2 = getWhereSql(tablename);
			//制单日期
			//String timeCondition = getTimeCondition("tt.makedate");
			String Condition = " tt.warehouseid = wv.wid and wv.userid=1 and tt.id = ttb.ttid and tt.IsAudit=1  and w.id=tt.warehouseid " +
					" and pstr.StructCode=p.PSID and p.id=ttb.productid ";
			String Condition1 = " and sam.id = tt.billno and sam.IsComplete = 0 and sam.isaudit = 1 ";
			
			if(null != outOrganID && !"".equals(outOrganID)){
				Condition1 += " and w.makeorganid = '"+outOrganID+"' ";
			}else{
				Condition1 += " and w.makeorganid = '"+users.getMakeorganid()+"' ";
			}
			if(null != outWarehouseID && !"".equals(outWarehouseID)){
				Condition1 += " and w.id = '"+outWarehouseID+"' ";
			}
			
			if(null != keyWordLeft && !"".equals(keyWordLeft)){
				Condition1 += " and p.psid = '"+keyWordLeft+"' ";
			}
			if(null != productID && !"".equals(productID)){
				Condition1 += " and ttb.productid = '"+productID+"' ";
			}
			
			//String Condition2 = " and sm.id = tt.billno and sm.IsComplete = 0 and sm.isaudit = 1 ";
			//绑定关键字
			String blur =DbUtil.getOrBlur(map, tmpMap, "w.warehousename","p.productname","p.nccode");
			whereSql1 = whereSql1 + blur+ Condition + Condition1; 
			whereSql1 = DbUtil.getWhereSql(whereSql1); 
			//whereSql2 = whereSql2 + blur+ Condition + Condition2; 
			//whereSql2 = DbUtil.getWhereSql(whereSql2); 
			//查询集合
			List ttlistBysam = att.getRepairListBySAM(request, pageSize, whereSql1, orderSql);
			//查询集合
			//List ttlistBysm = att.getRepairListBySM(request, pageSize, whereSql2);
			
			List alp = new ArrayList();
			
			for (int i = 0; i < ttlistBysam.size(); i++) {
				Map p = (Map) ttlistBysam.get(i);
				ProductStockpileForm psf = new ProductStockpileForm();
				Warehouse warehouse = aw.getWarehouseByID(p.get("warehouseid").toString());
				if(null != warehouse){
					psf.setWarehouseid(warehouse.getId());
					psf.setWarehourseidname(warehouse.getWarehousename());
				}
				Product product = ap.getProductByID(p.get("productid").toString());
				if(null != product){
					psf.setProductid(product.getId());
					psf.setNccode(product.getNccode());
					psf.setPsproductname(product.getProductname());
					psf.setPsspecmode(product.getSpecmode());
					ProductStruct pst = aps.getProductStructById(product.getPsid());
					if(null != pst){
						psf.setSortName(pst.getSortname());
					}
				}
				
				Double quantity, boxQuantity, scatterQuantity;
				//在途库存
				String q = (String) p.get("quantity");
				if(null == q){
					quantity = 0.0;
				}else{
					quantity = Double.valueOf(q);
				}
				//在途箱库存
				String b = (String) p.get("boxquantity");
				if(null == b){
					boxQuantity = 0.0;
				}else{
					boxQuantity = Double.valueOf(b);
				}
				String s = (String) p.get("scatterquantity");
				if(null == s){
					scatterQuantity = 0.0;
				}else{
					scatterQuantity = Double.valueOf(s);
				}
				
				double stockpile = apsa.getProductStockpileByProductID(product.getId(), 
						Long.valueOf(warehouse.getId()));
				psf.setStockpile(stockpile);
				psf.setRepairstockpile(quantity);
				psf.setTotalstockpile(psf.getStockpile() + psf.getRepairstockpile());
				
				if("1".equals(isShowAssistQuantity)){
					//箱到KG转换数
					double xtsQuantity = new AppFUnit().getXQuantity(psf.getProductid(), 2);
	    			//散到KG转换数
	    			double stsQuantity = new AppFUnit().getXQuantity(psf.getProductid(), product.getScatterunitid());
	    			if(psf.getStockpile() <= 0.0){
	    				psf.setAssistBoxStockpile(0.0);
						psf.setAssistEAStockpile(0.0);
	    			}else{
	    				//得到整箱数
		    			double box = ((Double)ArithDouble.div(psf.getStockpile(), xtsQuantity));
						//得到散数
						double tqu = ArithDouble.sub(psf.getStockpile(), ArithDouble.mul(xtsQuantity, (double)box));
						tqu = (Double)ArithDouble.div(tqu, stsQuantity);
						psf.setAssistBoxStockpile(box);
						if(tqu <= 0.0){
							psf.setAssistEAStockpile(0.0);
		    			}else{
		    				psf.setAssistEAStockpile(tqu);
		    			}
	    			}
				}
				
				alp.add(psf);
			}
			
			/*for (int i = 0; i < ttlistBysm.size(); i++) {
				Map p = (Map) ttlistBysm.get(i);
				ProductStockpileForm psf = new ProductStockpileForm();
				psf.setWarehouseid(p.get("warehouseid").toString());
				psf.setWarehourseidname(p.get("warehousename").toString());
				psf.setSortName(p.get("sortname").toString());
				psf.setNccode(p.get("nccode").toString());
				psf.setPsproductname(p.get("productname").toString());
				psf.setPsspecmode(p.get("specmode").toString());

				//在途库存
				Double quantity = Double.valueOf(p.get("quantity").toString());
				//在途箱库存
				Double boxQuantity = Double.valueOf(p.get("boxquantity").toString());
				//在途散库存
				Double scatterQuantity = Double.valueOf(p.get("scatterquantity").toString());

				double stockpile = aps.getProductStockpileByProductID(p.get("productid").toString(), 
						Long.valueOf(p.get("warehouseid").toString()));
				psf.setStockpile(stockpile);
				psf.setRepairstockpile(quantity);
				psf.setTotalstockpile(psf.getStockpile() + psf.getRepairstockpile());
				alp.add(psf);
			}*/
			
			request.setAttribute("alp", alp);
			
			Map<String,String> orderColumnMap = new HashMap<String, String>();
	      	generateMap(orderColumnMap);
			
			request.getSession().setAttribute("orderColumnMap", orderColumnMap);
			request.setAttribute("orderSql", orderSql);
			request.setAttribute("orderSqlName", orderSqlName);

			DBUserLog.addUserLog(userid, 7, "列表补货报表");
			
			request.setAttribute("KeyWordLeft", request.getParameter("KeyWordLeft"));
			request.setAttribute("ProductID", request.getParameter("ProductID"));
			request.setAttribute("ProductName", request.getParameter("ProductName"));
			request.setAttribute("BeginDate", request.getParameter("BeginDate"));
			request.setAttribute("EndDate", request.getParameter("EndDate"));
			request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
			request.setAttribute("OName", request.getParameter("OName"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			request.setAttribute("sstruts", request.getParameter("sstruts"));
			request.setAttribute("isShowAssistQuantity", request.getParameter("isShowAssistQuantity"));
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	private void generateMap(Map<String,String> orderColumnMap) {
		  orderColumnMap.put("productid", "产品内部编号");
		  orderColumnMap.put("warehouseid", "仓库编号");
	}
	
}
