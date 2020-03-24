package com.winsafe.drp.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.PDGoodsTransferReport;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStruct;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sap.pojo.PrintJob;

public class PDGoodsTransferReportService extends ReportServices{
	private static Logger logger = Logger.getLogger(PDGoodsTransferReportService.class);
	
	private OrganService organService = new OrganService();
	private WarehouseService warehouseService = new WarehouseService();
	
	// 根据条件查询
	public List<PDGoodsTransferReport> queryReport(HttpServletRequest request, int pageSize,Map paraMap,UsersBean users) throws Exception{
		
		
		//对于结束日期增加一天
		if(!StringUtil.isEmpty((String)paraMap.get("endDate"))){
			String endDateStr = (String)paraMap.get("endDate");
			Date endDate = Dateutil.StringToDate(endDateStr);
			endDate = Dateutil.addDay2Date(1, endDate);
			paraMap.put("endDate", Dateutil.formatDate(endDate));
		}
		
		List<PDGoodsTransferReport> resultList = new ArrayList<PDGoodsTransferReport>();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" \r\n select o.id,max(o.organname) as outorganname,max(o.oecode) as outoecode,max(o.province) as province,ma.makedate,ma.inorganid,swb.productid,swb.batch,swb.billcode,sum(CYCLEINQUANTITY) as quantity,ma.outwarehouseid,ma.inwarehouseid,max(swb.recorddate) as indate, max(sm.shipmentdate) as outdate,max(ma.movetype) moveType from MOVE_APPLY ma");
		sql.append(" \r\n join ORGAN o on ma.outorganid = o.id and ma.ISRATIFY = 1 ");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n and ma.outwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+")");
		} else { 
			sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
//		if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
//			sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
//		}
		if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
			sql.append(" \r\n and ma.outorganid = '" + paraMap.get("inOrganId") + "' "); //机构条件
		}
		if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and ma.outwarehouseid = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
		}
		if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
			sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + paraMap.get("region") + "')"); //机构条件 
		}
		sql.append(" \r\n join STOCK_WASTE_BOOK swb on ma.id = swb.billcode");
		if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"' and specmode= '"+(String)paraMap.get("packSizeName")+"') ");
		} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and swb.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"') ");
		}
		sql.append(" \r\n and swb.productid not in (select id from product where USEFLAG = 0) ");
		sql.append(" \r\n join STOCK_MOVE sm on ma.id = sm.id ");
		if(!StringUtil.isEmpty((String)paraMap.get("beginDate"))){
			sql.append(" \r\n and sm.RECEIVEDATE >=to_date('" + paraMap.get("beginDate") + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty((String)paraMap.get("endDate"))){
			sql.append(" \r\n and sm.RECEIVEDATE < to_date('" + paraMap.get("endDate") + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		sql.append(" \r\n GROUP BY o.id,ma.makedate,ma.inorganid,swb.productid,swb.batch,swb.billcode,ma.outwarehouseid,ma.inwarehouseid ");
		
		
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString());
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request, "id,productid", sql.toString(), pageSize);
		}
		if(list != null && list.size()>0){
			Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			Map<Integer, String> provinceMap = appCountryArea.getProvinceMap();
			Map<String,ProductStruct> pStructMap = appProductStruct.getAllMap(); //获取所有产品类型信息
			for(Map map : list){
				Double receiveQuantity = Double.valueOf((String)map.get("quantity"));
				PDGoodsTransferReport report = new PDGoodsTransferReport();
				//大区
				List<String> regionNames = appRegion.getRegionSortNameByAreaId((String)map.get("province"));
				if(regionNames != null && regionNames.size() > 0) {
					report.setRegionName(regionNames.get(0));
				}
				//省份
				if(!StringUtil.isEmpty((String)map.get("province"))) {
					report.setAreaName(provinceMap.get(Integer.parseInt((String)map.get("province"))));
				}
				report.setOutOrganId((String)map.get("id"));
				report.setOutOrganName((String)map.get("outorganname"));
				report.setOutOecode((String)map.get("outoecode"));
				report.setInDate((String)map.get("indate"));
				report.setOutDate((String)map.get("outdate"));
				report.setMoveType((String)map.get("movetype"));
				
				Product product = appProduct.getProductByID((String)map.get("productid"));
				report.setmCode(product.getmCode());
				report.setProductName(product.getMatericalChDes());
				report.setProductNameen(product.getMatericalEnDes());
				report.setPackSizeName(product.getSpecmode());
				report.setProductChnName(product.getProductname());
				report.setProductEngName(product.getProductnameen());
				report.setPackSizeNameEn(product.getPackSizeNameEn());
				
				
				//设置产品类别
				if(pStructMap.get(product.getPsid()) != null) {
					report.setSortName(pStructMap.get(product.getPsid()).getSortname());
				} 
				
				report.setBatch((String)map.get("batch"));
				
				report.setMoveApplyId((String)map.get("billcode"));
				report.setMoveApplyIdDate((String)map.get("makedate"));
				report.setInWarehouseName(warehouseService.getWarehouseName((String)map.get("inwarehouseid")));
				report.setOutWarehouseName(warehouseService.getWarehouseName((String)map.get("outwarehouseid")));
				
				PrintJob printJob= appPrintJob.getByPidAndBatch((String)map.get("productid"), (String)map.get("batch"));
				if(printJob != null) {
					report.setProduceDate(DateUtil.formatDate(DateUtil.StringToDate(printJob.getProductionDate(), "yyyyMMdd"), "yyyy-MM-dd"));
					report.setExpiryDate(DateUtil.formatDate(DateUtil.StringToDate(printJob.getExpiryDate(), "yyyyMMdd"), "yyyy-MM-dd"));
				}
				
				report.setInOrganId((String)map.get("inorganid"));
				Organ inOrgan = organService.getOrganByID((String)map.get("inorganid"));
				if(inOrgan != null) {
					report.setInOrganName(inOrgan.getOrganname());
					report.setInOecode(inOrgan.getOecode());
				}
				report.setInOrganName(organService.getOrganName((String)map.get("inorganid")));
				//设置数量与单位
				if(StringUtil.isEmpty((String)paraMap.get("countByUnit"))) {
					//检查单位是否可以正常转化,如不能则不转化
					if(checkRate(product.getId(),product.getSunit(),Constants.DEFAULT_UNIT_ID, funitMap)){
						receiveQuantity = changeUnit(product.getId(), product.getSunit(), receiveQuantity, Constants.DEFAULT_UNIT_ID,funitMap);
					}
					report.setUnit(countUnitMap.get(Constants.DEFAULT_UNIT_ID));
				} else {
					//转换成计量单位
					if(product.getBoxquantity() != null && product.getBoxquantity() != 0d) {
						receiveQuantity = ArithDouble.mul(receiveQuantity, product.getBoxquantity());
					}
					report.setUnit(countUnitMap.get(product.getCountunit()));
				}
				
				
				report.setOutQuantity(decimalFormat.format(receiveQuantity));
				report.setInQuantity(decimalFormat.format(receiveQuantity));
				
				resultList.add(report);
			}
		}
		return resultList;
	}
	
}
