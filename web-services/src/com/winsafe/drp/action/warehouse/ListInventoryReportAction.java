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
import com.winsafe.drp.dao.AppInventoryReport;
import com.winsafe.drp.dao.ProductStockpileForm;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ListInventoryReportAction extends BaseAction {
	AppInventoryReport air = new AppInventoryReport();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);
		
		String isshowBatch = request.getParameter("isShowbatch");
		
		 String orderSql = request.getParameter("orderbySql");
		  String orderSqlName = request.getParameter("orderbySqlShowName");
		
		try {
			String condition = " s.productid=p.id and ps.structcode=p.psid ";
			condition += " and s.warehouseid in (select wv.wid from  Warehouse_Visit  wv where wv.userid=" + userid + ")";
			String isdeleteSql = " and s.isdelete = 0 ";
			WarehouseService aw = new WarehouseService();
			String[] tablename = { "InventoryReport" };
			String whereSql = getWhereSql(tablename);
			if(map.get("RecordDate")==null){
				map.put("RecordDate", DateUtil.getYestDay());
			}
			String timeCondition = "";
			if(map.get("RecordDate")!=null&&map.get("RecordDate").toString()!=""){
				timeCondition = DbUtil.getTimeCondition3(map, tmpMap, "RecordDate");
			}
			
			String blur = getKeyWordCondition("s.batch");
	        String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			whereSql = whereSql +leftblur +  timeCondition + blur  + condition + isdeleteSql;
			whereSql = DbUtil.getWhereSql(whereSql);

			List sals = new ArrayList();
			if(isshowBatch!=null){
				sals = air.getInventoryReportByPage(request, pagesize, whereSql,orderSql);
			}else{
				sals = air.getInventoryReportByPageNoBatch(request, pagesize, whereSql,orderSql);
			}
			
			
			//如果查询的是当天时间
			if(map.get("RecordDate")!=null&&map.get("RecordDate").toString()!=""){
				String recordDate = map.get("RecordDate").toString();
				String currentDate = DateUtil.getCurrentDateString();
				if(recordDate.equals(currentDate)){
					String currentDateWhereSql = getWhereSql(tablename) + leftblur + blur + condition;
					if(isshowBatch!=null){
						sals = air.getCurrentInventoryReportByPage(request, pagesize, currentDateWhereSql, orderSql);
					}else{
						sals = air.getCurrentInventoryReportByPageNoBatch(request, pagesize, currentDateWhereSql,orderSql);
					}
				}
			}
			
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
				psf.setPsspecmode(p.get("specmode").toString());
				psf.setSortName(p.get("sortname").toString());
				if(isshowBatch!=null){
					psf.setBatch(p.get("batch").toString());
				}
				Integer sunit = Integer.valueOf(p.get("countunit").toString());
				psf.setCountunit(sunit);
				psf.setDate(p.get("recorddate").toString().substring(0, 10));
				psf.setStockpile(Double.valueOf(p.get("quantity").toString()));
				
				alp.add(psf);
			}
			
			Map<String,String> orderColumnMap = new HashMap<String, String>();
	      	generateMap(orderColumnMap);
			
			request.setAttribute("orderSql", orderSql);
			request.setAttribute("orderSqlName", orderSqlName);
			request.getSession().setAttribute("orderColumnMap", orderColumnMap);

			request.setAttribute("als", alp);
			request.setAttribute("alw", wls);

			DBUserLog.addUserLog(userid, 7, "库存管理>>查询历史库存报表");

			request.setAttribute("BeginDate", map.get("BeginDate"));
			request.setAttribute("EndDate", map.get("EndDate"));
			request.setAttribute("RecordDate", map.get("RecordDate"));
			request.setAttribute("isshowBatch", isshowBatch);

			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
			String result = "databases.settlement.noexist";
			request.setAttribute("result", result);
			return new ActionForward("/sys/lockrecordclose.jsp");
		}

	}
	
	
	public void getCurrentDateInventory(){
		
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
		  orderColumnMap.put("recorddate", "日期");
		  //orderColumnMap.put("nccode", "产品内部编号");
		 // orderColumnMap.put("p.psid", "产品类别");
	}
}
