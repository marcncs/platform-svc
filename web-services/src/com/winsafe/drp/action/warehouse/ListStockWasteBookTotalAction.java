package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppStockWasteBook;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpileForm;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListStockWasteBookTotalAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);
		Map<String,Double> boxUnitMap  = new HashMap<String, Double>();
		Map<String,Double> scatterUnitMap  = new HashMap<String, Double>();
		 
		Map<String,Product> pMap = new HashMap<String, Product>();
		AppProduct ap =new AppProduct();
		
		String isshowBatch = request.getParameter("isShowbatch");
		
		 String orderSql = request.getParameter("orderbySql");
		  String orderSqlName = request.getParameter("orderbySqlShowName");
		
		try {
			String condition = " s.productid=p.id ";
			condition += " and s.warehouseid in (select wv.wid from  Warehouse_Visit  wv where wv.userid=" + userid + ")";
			// String MakeOrganID = request.getParameter("MakeOrganID");
			WarehouseService aw = new WarehouseService();
			// if (MakeOrganID != null && !"".equals(MakeOrganID)) {
			// String ss =
			// getWarehouseId(aw.getWarehouseListByOID(MakeOrganID));
			// condition += " and s.warehouseid in(" + ss + ") ";
			// }
			
//			Map map = new HashMap(request.getParameterMap());
//			Map tmpMap = EntityManager.scatterMap(map);
//			
			String[] tablename = { "StockWasteBook" };
			String whereSql = getWhereSql(tablename);
//
			String timeCondition = getTimeCondition("RecordDate");
			
//			String[] tablename = { "StockWasteBook" };
//			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

//			String timeCondition = DbUtil.getTimeCondition(map, tmpMap, " RecordDate");
			
			String blur = getKeyWordCondition("s.batch");
	        String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			whereSql = whereSql +leftblur +  timeCondition + blur  + condition;
//			whereSql = whereSql + timeCondition + blur + condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppStockWasteBook aswb = new AppStockWasteBook();
			
			List sals = new ArrayList();
			if(isshowBatch!=null){
				sals = aswb.getStockWasteBookTotal(request, pagesize, whereSql,orderSql);
			}else{
				if(orderSql!=null){
					orderSql = orderSql.replaceAll("batch", "productid");
				}
				
				sals = aswb.getStockWasteBookTotalNoBatch(request, pagesize, whereSql,orderSql);
			}

			//request.setAttribute("als", sals);

			List wls = aw.getEnableWarehouseByVisit(userid);

			List alp = new ArrayList();
			AppFUnit af = new AppFUnit();
			for (int i = 0; i < sals.size(); i++) {
				ProductStockpileForm psf = new ProductStockpileForm();
				Map p = (Map) sals.get(i);

				psf.setWarehouseid(p.get("warehouseid").toString());
				psf.setWarehourseidname(aw.getWarehouseName(p.get("warehouseid").toString()));
				String productid = p.get("productid").toString();
				psf.setProductid(p.get("productid").toString());
				psf.setBarcode(p.get("nccode")==null?"":p.get("nccode").toString());
				psf.setPsproductname(p.get("productname").toString());
				psf.setPsspecmode(p.get("specmode")+"");
				if(isshowBatch!=null){
					psf.setBatch(p.get("batch").toString());
				}
				Integer sunit = Integer.valueOf(p.get("countunit").toString());
				psf.setCountunit(sunit);
				
				psf.setSortName(p.get("sortname").toString());
				
//				psf.setPrepareout(af.getStockpileQuantity2(productid, sunit, Double.valueOf(p.get("inquantity").toString())));
//				psf.setStockpile(af.getStockpileQuantity2(productid, sunit, Double.valueOf(p.get("outquantity").toString())));
//				psf.setAllstockpile(af.getStockpileQuantity2(productid, sunit, Double.valueOf(p.get("quantity").toString())));

				
				//每箱最小单位(支)
	        	Double Xquantity = boxUnitMap.get(productid);
	        	if(Xquantity == null){
	        		Xquantity = af.getXQuantity(productid, 1);
	        		boxUnitMap.put(productid, Xquantity);
	        	}
	            
//	          //每小包装kg数
//	        	Double scaq = scatterUnitMap.get(productid);
//	        	if(scaq == null){
//	        		Product product = pMap.get(productid);
//	        		if(product==null){
//	        			product = ap.getProductByID(productid);
//	        			if(product==null){
//	        				//说明产品不存在
//	        				product = new Product();
//	        			}
//	        			pMap.put(productid, product);
//	        		}
//	        		scaq = af.getXQuantity(productid, product.getScatterunitid());
//	        		scatterUnitMap.put(productid, scaq);
//	        	}
	        	
	        	//收入箱
	        	psf.setPrepareout((double)((Double)ArithDouble.div(Double.valueOf(p.get("inquantity").toString()), Xquantity)).intValue());
//	        	Double other = ArithDouble.sub(Double.valueOf(p.get("inquantity").toString()), ArithDouble.mul(Xquantity, psf.getPrepareout()));
	        	//收入EA
	        	//psf.setScatterNum(ArithDouble.div(other, scaq));
	        	
	        	//支出箱
				psf.setStockpile((double)((Double)ArithDouble.div(Double.valueOf(p.get("outquantity").toString()), Xquantity)).intValue());
//				other = ArithDouble.sub(Double.valueOf(p.get("outquantity").toString()), ArithDouble.mul(Xquantity, psf.getStockpile()));
				//支出EA
				//psf.setSquantity(ArithDouble.div(other, scaq));
	        	
				
				alp.add(psf);
			}
			
			Map<String,String> orderColumnMap = new HashMap<String, String>();
	      	generateMap(orderColumnMap);
			
			request.setAttribute("orderSql", orderSql);
			request.setAttribute("orderSqlName", orderSqlName);
			request.getSession().setAttribute("orderColumnMap", orderColumnMap);

			request.setAttribute("als", alp);
			request.setAttribute("alw", wls);

			DBUserLog.addUserLog(userid, 7, "库存管理>>查询库存报表");

			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("isshowBatch", isshowBatch);

			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
			String result = "databases.settlement.noexist";
			request.setAttribute("result", result);
			return new ActionForward("/sys/lockrecordclose.jsp");
		}

	}

	private String getWarehouseId(List wlist) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < wlist.size(); i++) {
			Warehouse w = (Warehouse) wlist.get(i);
			if (i == 0) {
				sb.append("'").append(w.getId()).append("'");
			} else {
				sb.append(",'").append(w.getId()).append("'");
			}
		}
		return sb.toString();
	}
	
	
	private void generateMap(Map<String,String> orderColumnMap) {
		  orderColumnMap.put("warehouseid", "仓库编号");
		  orderColumnMap.put("batch", "批次");
		  orderColumnMap.put("nccode", "产品内部编号");
		 // orderColumnMap.put("p.psid", "产品类别");
	}
}
