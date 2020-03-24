package com.winsafe.drp.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.PDProductScanningRatioReport;
import com.winsafe.drp.dao.ProductStruct;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.DbUtil; 
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class PDProductScanningRatioReportService extends ReportServices{
	private static Logger logger = Logger.getLogger(PDProductScanningRatioReportService.class);
//	private AppTakeTicketIdcode atti = new AppTakeTicketIdcode();
	private WarehouseService warehouseService = new WarehouseService();
	
	
	// 根据条件查询
	public List<PDProductScanningRatioReport> queryReport(HttpServletRequest request, int pageSize,Map paraMap,UsersBean users) throws Exception{
		
		
		//对于结束日期增加一天
		if(!StringUtil.isEmpty((String)paraMap.get("endDate"))){
			String endDateStr = (String)paraMap.get("endDate");
			Date endDate = Dateutil.StringToDate(endDateStr);
			endDate = Dateutil.addDay2Date(1, endDate);
			paraMap.put("endDate", Dateutil.formatDate(endDate));
		}
		
		List<PDProductScanningRatioReport> resultList = new ArrayList<PDProductScanningRatioReport>();
		StringBuffer sql = new StringBuffer();
		
		String selectGroup = null;
		String select = null;
		String selectOW = null;
		String groupBy = null;
		
//		sql.append("\r\n  select * from (");
		
		if("1".equals((String)paraMap.get("countMode"))){//按机构
			selectGroup = " \r\n select max(areaname) as areaName,max(regionName) as regionName,organid,max(organname) as organname,max(oecode) as oecode,max(province) as province,sum(realquantity) as totalquantity,sum(quantity) codequantity  from (";
//			select = " \r\n select country.areaname,r.sortname regionName,o.id as organid,o.organname,o.oecode,o.province,realquantity,tti.quantity from TAKE_TICKET tt";
//			selectOW = " \r\n select country.areaname,r.sortname regionName,o.id as organid,o.organname,o.oecode,o.province,realquantity,tti.quantity from TAKE_TICKET tt";
			select = " \r\n select max(country.areaname) areaname, max(r.sortname) regionName,max(o.id) organid,max(o.organname) organname, max(oecode) as oecode,max(province) as province,max(realquantity) realquantity, sum(tti.quantity) quantity from TAKE_TICKET tt  ";
			selectOW = " \r\n select max(country.areaname) areaname, max(r.sortname) regionName,max(o.id) organid,max(o.organname) organname, max(oecode) as oecode,max(province) as province,max(realquantity) realquantity, sum(tti.quantity) quantity  from TAKE_TICKET tt ";
			groupBy = " \r\n ) GROUP BY organid";
		} else if("0".equals((String)paraMap.get("countMode"))) {//按单据
			selectGroup = " \r\n select max(areaname) as areaName,max(regionName) as regionName,organid,max(organname) as organname,max(oecode) as oecode,max(province) as province,productid,max(realquantity) as totalquantity, ttid, BILLNO,warehouseid,max(mCode) mCode,max(productName) productName,max(specMode) specMode,max(productNameen) productNameen,max(packSizeNameEn) packSizeNameEn,max(pid) pid,max(psid) psid,max(sunit) sunit,sum(quantity) codequantity  from ("; 
			select = " \r\n select country.areaname,r.sortname regionName,o.id as organid,o.organname,o.oecode,o.province,ttd.productid,realquantity, tt.id as ttid, tt.BILLNO,tt.warehouseid,ttd.unitid,p.mCode,p.productName,p.specMode,p.productNameen,p.packSizeNameEn,p.id pid,p.psid,p.sunit,tti.quantity from TAKE_TICKET tt";
			selectOW = " \r\n select country.areaname,r.sortname regionName,o.id as organid,o.organname,o.oecode,o.province,ttd.productid,realquantity, tt.id as ttid, tt.BILLNO,tt.inwarehouseid as warehouseid,ttd.unitid,p.mCode,p.productName,p.specMode,p.productNameen,p.packSizeNameEn,p.id pid,p.psid,p.sunit,tti.quantity from TAKE_TICKET tt";
			groupBy = " \r\n ) GROUP BY organid,productid,ttid, BILLNO,warehouseid";
		} else {//按仓库
			selectGroup = " \r\n select max(areaname) as areaName,max(regionName) as regionName,organid,max(organname) as organname,max(oecode) as oecode,max(province) as province,sum(realquantity) as totalquantity, warehouseid,sum(quantity) codequantity  from (";
//			select = " \r\n select country.areaname,r.sortname regionName,o.id as organid,o.organname,o.oecode,o.province,realquantity, tt.warehouseid,tti.quantity from TAKE_TICKET tt";
			select = " \r\n select max(country.areaname) areaname, max(r.sortname) regionName,max(o.id) organid,max(o.organname) organname, max(oecode) as oecode,max(province) as province,max(realquantity) realquantity, max(tt.warehouseid) warehouseid,sum(tti.quantity) quantity from TAKE_TICKET tt ";
//			selectOW = " \r\n select country.areaname,r.sortname regionName,o.id as organid,o.organname,o.oecode,o.province,realquantity, tt.inwarehouseid,tti.quantity as warehouseid from TAKE_TICKET tt";
			selectOW = " \r\n select max(country.areaname) areaname, max(r.sortname) regionName,max(o.id) organid,max(o.organname) organname, max(oecode) as oecode,max(province) as province,max(realquantity) realquantity, max(tt.inwarehouseid) warehouseid,sum(tti.quantity) quantity  from TAKE_TICKET tt ";
			groupBy = " \r\n ) GROUP BY organid, warehouseid";
		}
		sql.append(selectGroup);
		//发货单,调拨单,工厂退回单
		sql.append(select);
		sql.append(" \r\n join ORGAN o on o.id = tt.oid  and o.isrepeal = 0 and tt.isaudit = 1");
		if(DbUtil.isDealer(users)) {
			sql.append(" \r\n join RULE_USER_WH wv on wv.warehouse_Id = tt.warehouseid and wv.user_Id="+users.getUserid());
		} else {
			sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
//		if(!StringUtil.isEmpty((String)paraMap.get("IsAudit"))){
//			sql.append(" \r\n and tt.isaudit = " + paraMap.get("IsAudit")); //是否复核
//		}
		if(!StringUtil.isEmpty((String)paraMap.get("BSort"))){
			sql.append(" \r\n and tt.bsort = " + paraMap.get("BSort")); //单据类型
		}
		if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
			sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
		}
//		sql.append(" \r\n and tt.warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+")");
		if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
			sql.append(" \r\n and tt.oid = '" + paraMap.get("inOrganId") + "' "); //机构条件
		}
		if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
			sql.append(" \r\n and tt.warehouseid = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
		}
		if(!StringUtil.isEmpty((String)paraMap.get("beginDate"))){
			sql.append(" \r\n and tt.auditdate >=to_date('" + paraMap.get("beginDate") + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		if(!StringUtil.isEmpty((String)paraMap.get("endDate"))){
			sql.append(" \r\n and tt.auditdate < to_date('" + paraMap.get("endDate") + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
//		if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
//			sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + paraMap.get("region") + "')"); //机构条件 
//		}
		sql.append(" \r\n join TAKE_TICKET_DETAIL ttd on ttd.ttid = tt.id");
		sql.append(" \r\n join PRODUCT p on ttd.productid = p.id and p.cartonscanning = 1");
		sql.append(" \r\n and p.USEFLAG = 1");
		if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and p.productname = '"+(String)paraMap.get("ProductName")+"' and p.specmode= '"+(String)paraMap.get("packSizeName")+"'");
		} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
			sql.append(" \r\n and p.productname = '"+(String)paraMap.get("ProductName")+"'");
		}
		sql.append(" \r\n LEFT JOIN TAKE_TICKET_IDCODE tti on ttd.ttid = tti.ttid  and ttd.productId = tti.productId");
		sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id  ");
		sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
		sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
		
		if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
			sql.append(" \r\n where r.regioncode = '" + (String)paraMap.get("region") + "' "); 
		}
		
		if(!"0".equals((String)paraMap.get("countMode"))) {
			 sql.append(" \r\n GROUP BY tt.id,ttd.productid ");
		}
		
		if(StringUtil.isEmpty((String)paraMap.get("BSort")) || "7".equals(paraMap.get("BSort"))){
			//渠道退货单
			sql.append(" \r\n UNION ALL");
			sql.append(selectOW);
			sql.append(" \r\n join ORGAN o on o.id = tt.inoid and o.isrepeal = 0 and tt.isaudit = 1 and tt.bsort = 7");
			if(DbUtil.isDealer(users)) {
				sql.append(" \r\n join RULE_USER_WH wv on wv.warehouse_Id = tt.inwarehouseid and wv.user_Id="+users.getUserid());
			} else {
				sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
			}
//			if(!StringUtil.isEmpty((String)paraMap.get("IsAudit"))){
//				sql.append(" \r\n and tt.isaudit = " + paraMap.get("IsAudit")); //是否复核
//			}
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
//			sql.append(" \r\n and tt.inwarehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+")");
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and tt.inoid = '" + paraMap.get("inOrganId") + "' "); //机构条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
				sql.append(" \r\n and tt.inwarehouseid = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("beginDate"))){
				sql.append(" \r\n and tt.auditdate >=to_date('" + paraMap.get("beginDate") + "','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("endDate"))){
				sql.append(" \r\n and tt.auditdate < to_date('" + paraMap.get("endDate") + "','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
			}
//			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
//				sql.append(" \r\n and o.province is not null and o.province in (select areaid from region_area where regioncodeid =  '" + paraMap.get("region") + "')"); //机构条件 
//			}
			sql.append(" \r\n join TAKE_TICKET_DETAIL ttd on ttd.ttid = tt.id");
			sql.append(" \r\n join PRODUCT p on ttd.productid = p.id and p.cartonscanning = 1");
			sql.append(" \r\n and p.USEFLAG = 1");
			if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and p.productname = '"+(String)paraMap.get("ProductName")+"' and p.specmode= '"+(String)paraMap.get("packSizeName")+"'");
			} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
				sql.append(" \r\n and p.productname = '"+(String)paraMap.get("ProductName")+"'");
			}
			sql.append(" \r\n LEFT JOIN TAKE_TICKET_IDCODE tti on ttd.ttid = tti.ttid  and ttd.productId = tti.productId");
			sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id  ");
			sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
			sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n where r.regioncode = '" + (String)paraMap.get("region") + "' "); 
			}
			if(!"0".equals((String)paraMap.get("countMode"))) {
				 sql.append(" \r\n GROUP BY tt.id,ttd.productid ");
			}
		}
		if( "1".equals((String)paraMap.get("countMode")) || "2".equals((String)paraMap.get("countMode"))) {
			if("1".equals((String)paraMap.get("countMode"))){//按机构
				sql.append("\r\n UNION ALL");
				sql.append("\r\n select country.areaname,r.sortname regionName,o.id as organid,o.organname,o.oecode,o.province,0,0  from ORGAN o");
			} else if("2".equals((String)paraMap.get("countMode"))) {//按仓库
				sql.append("\r\n UNION ALL");
				sql.append("\r\n select country.areaname,r.sortname regionName,o.id as organid,o.organname,o.oecode,o.province,0,w.id,0 from ORGAN o");
				sql.append("\r\n join warehouse w on o.id = w.makeorganid and w.useflag = 1");
				if(!StringUtil.isEmpty((String)paraMap.get("inWarehouseId"))){
					sql.append(" \r\n and w.id = '" + paraMap.get("inWarehouseId") + "' "); //仓库条件
				}
			} 
			sql.append(" \r\n LEFT JOIN COUNTRY_AREA country on o.province=country.id  ");
			sql.append(" \r\n LEFT JOIN (SELECT max(REGIONCODEID) regioncodeid,max(AREAID) areaid  FROM REGION_AREA GROUP BY AREAID) ra on ra.areaid=country.id  ");
			sql.append(" \r\n LEFT JOIN REGION r on r.regioncode=ra.regioncodeid  ");
			sql.append(" \r\n where o.isrepeal = 0"); 
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				sql.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
			}
			if(!StringUtil.isEmpty((String)paraMap.get("inOrganId"))){
				sql.append(" \r\n and o.id = '" + paraMap.get("inOrganId") + "' "); //机构条件
			}
			if(!StringUtil.isEmpty((String)paraMap.get("region"))) {
				sql.append(" \r\n and r.regioncode = '" + (String)paraMap.get("region") + "' "); 
			}
		}
		
		sql.append(groupBy); 
//		if("1".equals((String)paraMap.get("countMode"))){//按机构
//			sql.append("\r\n  ) ");
////			sql.append("\r\n  ) temp1 left join (");
////			sql.append("\r\n select oid, sum(codequantity) codequantity from (");
////			sql.append("\r\n select tt.oid,sum(tti.quantity) codequantity from TAKE_TICKET tt ");
////			sql.append("\r\n join TAKE_TICKET_IDCODE tti on tt.id = tti.ttid ");
////			sql.append("\r\n GROUP BY tt.oid");
////			sql.append("\r\n UNION ALL");
////			sql.append("\r\n select tt.inoid,sum(tti.quantity) codequantity from TAKE_TICKET tt ");
////			sql.append("\r\n join TAKE_TICKET_IDCODE tti on tt.id = tti.ttid and tt.bsort = 7");
////			sql.append("\r\n GROUP BY tt.inoid) temp_0 GROUP BY oid");
////			sql.append("\r\n ) temp2 on temp1.organid = temp2.oid");
//		} else if("2".equals((String)paraMap.get("countMode"))) {//按仓库
//			sql.append("\r\n  ) ");
//		} else {
//			sql.append("\r\n  ) temp1 left join (");
//			sql.append("\r\n  select ttid,sum(quantity) codequantity from TAKE_TICKET_IDCODE GROUP BY ttid ");
//			sql.append("\r\n  ) temp2 on temp1.ttid = temp2.ttid");
//		}
		
		System.out.println(sql.toString());
		
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString());
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request, "organid", sql.toString(), pageSize);
		}
		
		Integer bsort = null;
		String bsortStr = (String)paraMap.get("BSort");
		if(!StringUtil.isEmpty(bsortStr)) {
			bsort = Integer.parseInt(bsortStr);
		}
		
		if(list != null && list.size()>0){
//			Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
//			Map<Integer, String> provinceMap = appCountryArea.getProvinceMap();
			Map<String, String> warehouseNameMap = new HashMap<String, String>();
			for(Map map : list){
				Double codeQuantity = 0d, totalQuantity = 0d;
				
				PDProductScanningRatioReport report = new PDProductScanningRatioReport();
				
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, report);
				
//				report.setOrganId((String)map.get("organid"));
//				
//				report.setOrganName((String)map.get("organname"));
//				report.setOecode((String)map.get("oecode"));
				
//				if(!StringUtil.isEmpty((String)map.get("province"))) {
//					report.setAreaName(provinceMap.get(Integer.parseInt((String)map.get("province"))));
//				}
//				List<String> regionNames = appRegion.getRegionSortNameByAreaId((String)map.get("province"));
//				if(regionNames != null && regionNames.size() > 0) {
//					report.setRegionName(regionNames.get(0));
//				}
				if(!StringUtil.isEmpty((String)map.get("warehouseid"))) {
					if(warehouseNameMap.containsKey(map.get("warehouseid"))) {
						report.setWarehouseName(warehouseNameMap.get(map.get("warehouseid")));
					} else {
						report.setWarehouseName(warehouseService.getWarehouseName((String)map.get("warehouseid")));
						warehouseNameMap.put((String)map.get("warehouseid"), report.getWarehouseName());
					}
					
				}
				
				if("0".equals((String)paraMap.get("countMode"))) {
					Map<String,ProductStruct> pStructMap = appProductStruct.getAllMap();
//					//获取所有产品类型信息
//					Product product = appProduct.getProductByID((String)map.get("productid"));
//					report.setmCode(product.getmCode());
//					report.setProductName(product.getProductname());
//					report.setSpecMode(product.getSpecmode());
//					report.setProductNameen(product.getProductnameen());
//					report.setPackSizeNameEn(product.getPackSizeNameEn());
					//设置产品类别
//					if(pStructMap.get(product.getPsid()) != null) {
//						report.setSortName(pStructMap.get(product.getPsid()).getSortname());
//					} 
					if(pStructMap.get(report.getPsid()) != null) {
						report.setSortName(pStructMap.get(report.getPsid()).getSortname());
					} 
//					report.setBillNo((String)map.get("billno"));
					
//					String codeQuantityStr = atti.getTotalkgquantityByttidAndpid((String)map.get("ttid"),(String)map.get("productid"));
					String codeQuantityStr = (String)map.get("codequantity");
					if(!StringUtil.isEmpty(codeQuantityStr)) {
						codeQuantity = Double.valueOf(codeQuantityStr);
					}
					String totalQuantityStr = (String)map.get("totalquantity");
					if(!StringUtil.isEmpty(totalQuantityStr)) {
						totalQuantity = Double.valueOf(totalQuantityStr);
					}
					report.setIoQuantity(totalQuantity);
					
					//检查单位是否可以正常转化,如不能则不转化
//					if(checkRate(product.getId(),Constants.DEFAULT_UNIT_ID,product.getSunit(), funitMap)){
//						totalQuantity = changeUnit(product.getId(), Constants.DEFAULT_UNIT_ID, totalQuantity, product.getSunit(),funitMap);
//					}
					
					if(totalQuantity != 0d) {
						double rate = ArithDouble.div(codeQuantity, totalQuantity, 4);
						String srate = Double.toString(ArithDouble.mul(rate, 100))+"%";
						report.setRatio(srate);
					}
					
					//检查单位是否可以正常转化,如不能则不转化
//					if(checkRate(product.getId(), product.getSunit(),Constants.DEFAULT_UNIT_ID, funitMap)){
//						codeQuantity = changeUnit(product.getId(), product.getSunit(), codeQuantity, Constants.DEFAULT_UNIT_ID,funitMap);
//					}
					report.setIdcodeQuantity(codeQuantity);
				} else {
					String totalQuantityStr = (String)map.get("totalquantity");
					if(!StringUtil.isEmpty(totalQuantityStr)) {
						totalQuantity = Double.valueOf(totalQuantityStr);
					}
					String codeQuantityStr = "0";
					if(totalQuantity != 0) {
						codeQuantityStr = (String)map.get("codequantity");
//						codeQuantityStr = atti.getTotalQuantityByOidWidAndDate(report.getOrganId(), (String)map.get("warehouseid"), (String)paraMap.get("beginDate"), (String)paraMap.get("endDate"), bsort);
					}
					if(!StringUtil.isEmpty(codeQuantityStr)) {
						codeQuantity = Double.valueOf(codeQuantityStr);
					}
					report.setIoQuantity(totalQuantity);
					report.setIdcodeQuantity(codeQuantity);
					if(totalQuantity != 0d) {
						double rate = ArithDouble.div(codeQuantity, totalQuantity, 4);
						String srate = Double.toString(ArithDouble.mul(rate, 100))+"%";
						report.setRatio(srate);
					}
				}
				
				resultList.add(report);
			}
		}
		return resultList;
	}
	
}
