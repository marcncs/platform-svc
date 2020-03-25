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

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.ProductStruct;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.WarehouseProdcutStockpileAll;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

/**
* 库存保质期预警报表
* @Title: ProtectDateByProductStockpileAllAction.java
* @author: WEILI 
* @CreateTime: 2013-04-28
* @version: 1.0
 */
public class ProtectDateByProductStockpileAllAction extends BaseAction{
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化参数
		super.initdata(request);
		//分页
		int pageSize=15;
		AppProduct ap = new AppProduct();
		AppWarehouse aw = new AppWarehouse();
		AppProductStruct aps = new AppProductStruct();
		AppProductStockpile apsa = new AppProductStockpile();
		AppFUnit af  =new AppFUnit();
		List<WarehouseProdcutStockpileAll> wpsaList = new ArrayList<WarehouseProdcutStockpileAll>();
		//获取页面检索条件中的参数
		String productID = request.getParameter("ProductID");
		String outOrganID = request.getParameter("MakeOrganID");
		String keyWordLeft = request.getParameter("KeyWordLeft");
		String outWarehouseID = request.getParameter("WarehouseID");
		
		String orderSql = request.getParameter("orderbySql");
		String orderSqlName = request.getParameter("orderbySqlShowName");
		
		String beginDate = request.getParameter("BeginDate");
		String endDate = request.getParameter("EndDate");
		
		String isFZfunitid = request.getParameter("isFZfunitid");
		
		map = request.getParameterMap();
		tmpMap = EntityManager.scatterMap(map);
		String blur = DbUtil.getOrBlur(map, tmpMap, "w.warehousename", "w.id");
		
		String whereSql = " where 1=1 and ";
		whereSql += blur;
		if(null != outOrganID && !"".equals(outOrganID)){
			whereSql += " w.makeorganid = '"+outOrganID+"' and ";
		}else{
			whereSql += " w.makeorganid = '"+users.getMakeorganid()+"' and ";
		}
		if(null != outWarehouseID && !"".equals(outWarehouseID)){
			whereSql += " w.id = '"+outWarehouseID+"' and";
		}
		whereSql = DbUtil.getWhereSql(whereSql); 
		//根据当前用户的所属机构查询所有仓库
		List<Warehouse> warehouseList = aw.getWarehouseByNoPage(whereSql);
		
		String warehouseString = getWarehouseIDBYList(warehouseList);
		
		WarehouseProdcutStockpileAll wpsa = null;
		//获取产品预警期
		BaseResource br1 = new AppBaseResource().getBaseResourceValue("ProductAlermDate", 0);
		BaseResource br2 = new AppBaseResource().getBaseResourceValue("ProductAlermDate", 1);
		
		String pWhereSql = " where ps.productid = p.id and ps.warehouseid in ("+warehouseString+") ";
		if(null != keyWordLeft && !"".equals(keyWordLeft)){
			pWhereSql += " and p.psid = '"+keyWordLeft+"' ";
		}
		if(null != productID && !"".equals(productID)){
			pWhereSql += " and ps.productid = '"+productID+"' ";
		}
		
		//根据仓库ID查询所有库存量
		List<ProductStockpile> psList = apsa.getProductStockpileAllListByWID(request, pageSize, pWhereSql, orderSql);
		for (ProductStockpile ps : psList) {
			wpsa = new WarehouseProdcutStockpileAll();
			Warehouse warehouse = aw.getWarehouseByID(ps.getWarehouseid());
			wpsa.setWarehouseId(warehouse.getId());
			wpsa.setWarehouseName(warehouse.getWarehousename());
			

			
			Product p = ap.getProductByID(ps.getProductid());
			if(null != p){
				wpsa.setProductId(p.getId());
				wpsa.setProductNccode(p.getNccode());
				wpsa.setProductName(p.getProductname());
				wpsa.setProductSpecmode(p.getSpecmode());
				wpsa.setProductProtectDate(p.getProtectDate());
				ProductStruct pst = aps.getProductStructById(p.getPsid());
				if(null != pst){
					wpsa.setProductStruct(pst.getSortname());
				}else{
					wpsa.setProductStruct("");
				}
			}else{
				wpsa.setProductNccode("");
				wpsa.setProductName("");
				wpsa.setProductSpecmode("");
			}
			wpsa.setPsid(ps.getId());
			wpsa.setRemark(ps.getRemark());
			wpsa.setProduceDate(ps.getProducedate());
			wpsa.setStockpile(ps.getStockpile() + ps.getPrepareout());
			wpsa.setUnitId(ps.getCountunit());
			//获取产品预警期
			BaseResource brByUnit = new AppBaseResource().getBaseResourceValue("CountUnit", wpsa.getUnitId());
			wpsa.setUnitidName(brByUnit.getTagsubvalue());
			//如果生产日期为空
			if(null == wpsa.getProduceDate() || "".equals(wpsa.getProduceDate())){
				wpsa.setDaoqiDate("");
				wpsa.setDaoqiDateByDay("");
				wpsa.setStockpilestruts(0);
			}else{
				wpsa.setDaoqiDate(DateUtil.formatDateByAdd(wpsa.getProduceDate(), 
						Integer.valueOf(wpsa.getProductProtectDate())));
				wpsa.setDaoqiDateByDay(String.valueOf(DateUtil.formatDateByDiffByTwoStringDate(
						DateUtil.getCurrentDateString(), wpsa.getDaoqiDate())));
				/**
				 * 判断库存状态
				 */
				//如果产品预警期-到期天数大于等于0
				if(Integer.valueOf(wpsa.getDaoqiDateByDay()) >= Integer.valueOf(br2.getTagsubvalue())){
					wpsa.setStockpilestruts(2);
				}
				if(Integer.valueOf(wpsa.getDaoqiDateByDay()) < Integer.valueOf(br1.getTagsubvalue())){
					wpsa.setStockpilestruts(0);
				}
				if(Integer.valueOf(wpsa.getDaoqiDateByDay()) > Integer.valueOf(br1.getTagsubvalue()) &&
						Integer.valueOf(wpsa.getDaoqiDateByDay()) < Integer.valueOf(br2.getTagsubvalue())){
					wpsa.setStockpilestruts(1);
				}
			}
			if("1".equals(isFZfunitid)){
				//箱到KG转换数
				double xtsQuantity = af.getXQuantity(wpsa.getProductId(), 2);
    			//散到KG转换数
    			double stsQuantity = af.getXQuantity(wpsa.getProductId(), p.getScatterunitid());
    			//得到整箱数
    			double b = ((Double)ArithDouble.div(wpsa.getStockpile(), xtsQuantity));
				//得到散数
				double tqu = ArithDouble.sub(wpsa.getStockpile(), ArithDouble.mul(xtsQuantity, (double)b));
				tqu = (Double)ArithDouble.div(tqu, stsQuantity);
				wpsa.setFuzhuBoxStockpile(b);
				wpsa.setFuzhuEAStockpile(tqu);
			}
			wpsaList.add(wpsa);
		}
		//获取检索条件中的库存状态并判断集合中的数据
		List<WarehouseProdcutStockpileAll> wList = new ArrayList<WarehouseProdcutStockpileAll>();
		String stockpilestruts = request.getParameter("sstruts");
		if(null != stockpilestruts && !"".equals(stockpilestruts)){
			if(!"-1".equals(stockpilestruts)){
				for (WarehouseProdcutStockpileAll wpsall : wpsaList) {
					//如果页面传值的库存状态与集合中的数据相等
					if(stockpilestruts.equals(String.valueOf(wpsall.getStockpilestruts()))){
						wList.add(wpsall);
					}
				}
			}else{
				wList = wpsaList;
			}
		}else{
			wList = wpsaList;
		}
		/*List<WarehouseProdcutStockpileAll> wpsList = new ArrayList<WarehouseProdcutStockpileAll>();
		if(null != beginDate && !"".equals(beginDate) && null != endDate && !"".equals(endDate)){
			for (WarehouseProdcutStockpileAll wpsall : wList) {
				//如果页面传值的产品类别与集合中的数据相等
				if(wpsall.getDaoqiDate().compareTo(beginDate) > 0 && 
						wpsall.getDaoqiDate().compareTo(endDate) < 0){
					wpsList.add(wpsall);
				}
			}
		}else{
			wpsList = wList;
		}*/
		
		Map<String,String> orderColumnMap = new HashMap<String, String>();
      	generateMap(orderColumnMap);
		
		request.setAttribute("wpsaList", wList);
		request.getSession().setAttribute("orderColumnMap", orderColumnMap);
		request.setAttribute("orderSql", orderSql);
		request.setAttribute("orderSqlName", orderSqlName);
		
		AppProductStruct appProductStruct = new AppProductStruct();
		List uls = appProductStruct.getProductStructCanUse();
		request.setAttribute("uls",uls);
		request.setAttribute("KeyWordLeft", request.getParameter("KeyWordLeft"));
		request.setAttribute("ProductID", request.getParameter("ProductID"));
		request.setAttribute("ProductName", request.getParameter("ProductName"));
		request.setAttribute("BeginDate", request.getParameter("BeginDate"));
		request.setAttribute("EndDate", request.getParameter("EndDate"));
		request.setAttribute("MakeOrganID", request.getParameter("MakeOrganID"));
		request.setAttribute("OName", request.getParameter("OName"));
		request.setAttribute("KeyWord", request.getParameter("KeyWord"));
		request.setAttribute("sstruts", request.getParameter("sstruts"));
		request.setAttribute("isFZfunitid", request.getParameter("isFZfunitid"));
		DBUserLog.addUserLog(userid,  7,"仓库管理>>库存保质期预警报表");
		return mapping.findForward("success");
	}
	
	private void generateMap(Map<String,String> orderColumnMap) {
		  orderColumnMap.put("ps.stockpile+ps.prepareout", "实际库存");
		  orderColumnMap.put("p.productname", "产品名称");
		  orderColumnMap.put("p.nccode", "产品内部编号");
		  orderColumnMap.put("p.psid", "产品类别");
		  orderColumnMap.put("ps.warehouseid", "仓库编号");
	}
	
	public String getWarehouseIDBYList(List<Warehouse> warehouseList){
		StringBuffer sbtake = new StringBuffer();
		if(warehouseList.size() == 1){
			sbtake.append("'");
			sbtake.append(warehouseList.get(0).getId());
			sbtake.append("'");
		}else if(warehouseList.size() > 1){
			for (int i = 0; i < warehouseList.size(); i++) {
				if(i != warehouseList.size()-1){
					sbtake.append("'");
					sbtake.append(warehouseList.get(i).getId());
					sbtake.append("'");
					sbtake.append(",");
				}else{
					sbtake.append("'");
					sbtake.append(warehouseList.get(i).getId());
					sbtake.append("'");
				}
			}
		}else{
			//暂无逻辑
		}
		return sbtake.toString();
	}
}
