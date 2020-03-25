package com.winsafe.drp.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppBKDReturnSSCCCodeReport {

	@SuppressWarnings("unchecked")
	public List<Map> getBKDReturnSSCCCodeReport(HttpServletRequest request, int pageSize, UsersBean users) throws Exception {
		//生成根据页面条件生成whereSql
		StringBuffer whereSql = new StringBuffer();
		Map paraMap = new HashMap(request.getParameterMap());
		//开始日期
		if(!StringUtil.isEmpty((String)paraMap.get("beginDate"))){
			whereSql.append("\r\n and temp.auditDate >=to_date('").append(((String)paraMap.get("beginDate")).trim()).append("','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		//对于结束日期增加一天
		if(!StringUtil.isEmpty((String)paraMap.get("endDate"))){
			String endDateStr = (String)paraMap.get("endDate");
			Date endDate = Dateutil.StringToDate(endDateStr);
			endDate = Dateutil.addDay2Date(1, endDate);
			paraMap.put("endDate", Dateutil.formatDate(endDate));
			whereSql.append("\r\n and temp.auditDate < to_date('").append(((String)paraMap.get("endDate")).trim()).append("','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		//区域
		if(!StringUtil.isEmpty((String)paraMap.get("region"))){
			whereSql.append(" \r\n and temp.inRegionCode = '").append(((String)paraMap.get("region")).trim()).append("' "); 
		}
		//机构条件
		if(!StringUtil.isEmpty((String)paraMap.get("organId"))){
			whereSql.append(" \r\n and temp.inOrganId = '").append(((String)paraMap.get("organId")).trim()).append("' "); 
		}
		//仓库条件
		if(!StringUtil.isEmpty((String)paraMap.get("warehouseId"))){
			whereSql.append(" \r\n and temp.inWarehouseId = '").append(((String)paraMap.get("warehouseId")).trim()).append("' "); 
		}
		//产品条件
		if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) {
			whereSql.append("\r\n and temp.productName = '").append(((String)paraMap.get("ProductName")).trim()).append("' and temp.specmode= '").append(((String)paraMap.get("packSizeName")).trim()).append("' ");
		} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) {
			whereSql.append("\r\n and temp.productName = '").append(((String)paraMap.get("ProductName")).trim()).append("' ");
		}
		//批次
		if(!StringUtil.isEmpty((String)paraMap.get("batch"))){
			whereSql.append("\r\n and temp.batch = '").append(((String)paraMap.get("batch")).trim()).append("' ");
		}
		//箱码
		if(!StringUtil.isEmpty((String)paraMap.get("idcode"))){
			whereSql.append("\r\n and temp.idcode = '").append(((String)paraMap.get("idcode")).trim()).append("' ");
		}
		//管辖权限
		if(DbUtil.isDealer(users)) {
			whereSql.append("\r\n and temp.inWarehouseId in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+")");
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("\r\n select * from (");
		sb.append("\r\n select inOrgan.id as inOrganId, inOrgan.oecode as inOrganOecode, inOrgan.organname as inOrganName, tt.oname as outOrganName, inRegion.regioncode as inRegionCode, inRegion.sortname as inRegionName, inProvince.areaname as inProvinceName, inWarehouse.id as inWarehouseId, inWarehouse.warehousename as inWarehouseName, tt.billNo, tt.auditDate, p.id as productId, p.mcode, p.matericalchdes, p.matericalendes, p.productName, p.productNameEn, p.specmode, p.packsizeNameEn, ttd.UNITID as unitId, ttd.REALQUANTITY as sUnitQuantity, p.sunit as sUnit, p.countUnit as countUnit,p.boxquantity as boxQuantity, (ttd.REALQUANTITY * f.xquantity * p.boxquantity) as countUnitQuantity, tti.batch, to_date(tti.producedate, 'yyyy-mm-dd') as productionDate, to_date(tti.producedate, 'yyyy-mm-dd')+p.expirydays as expiryDate, tti.idcode "); 
		sb.append("\r\n from TAKE_TICKET tt left join Take_ticket_detail ttd on tt.id=ttd.ttid ");
		sb.append("\r\n left join take_ticket_idcode tti on ttd.ttid=tti.ttid and ttd.productid=tti.productid ");
		sb.append("\r\n left join product p on ttd.productid=p.id ");
		sb.append("\r\n left join organ inOrgan on tt.inoid=inOrgan.id ");
		sb.append("\r\n left join warehouse inWarehouse on tt.inwarehouseid=inWarehouse.id and inWarehouse.USEFLAG = 1");
		sb.append("\r\n left join COUNTRY_AREA inProvince on inProvince.id=inOrgan.province ");
		sb.append("\r\n left join REGION_AREA inRegionArea on inRegionArea.areaid=inProvince.id ");
		sb.append("\r\n left join REGION inRegion on inRegion.regioncode=inRegionArea.regioncodeid ");
		sb.append(" \r\n LEFT JOIN F_UNIT f on ttd.productid = f.productid and ttd.unitid = f.funitid ");
		sb.append("\r\n where ");
		sb.append("\r\n tt.bsort=7 and tt.billno like 'OW%' ");//PD退货的单据
		sb.append("\r\n and tt.isaudit=1 ");//已复核单据
		sb.append("\r\n and idcode is not null ");//有的详情没有上传箱码，需要排除
		sb.append("\r\n and p.USEFLAG = 1");//过滤不可用的产品
		if(!DbUtil.isDealer(users)) {
			sb.append(" \r\n and "+DbUtil.getWhereCondition(users, "inOrgan"));
		}
		sb.append("\r\n ) temp");
		sb.append("\r\n where 1=1 ").append(whereSql);
		sb.append("\r\n ORDER BY NLSSORT(inRegionName,'NLS_SORT = SCHINESE_PINYIN_M'),NLSSORT(inProvinceName,'NLS_SORT = SCHINESE_PINYIN_M'),NLSSORT(inOrganName,'NLS_SORT = SCHINESE_PINYIN_M'), NLSSORT(productNameEn,'NLS_SORT = SCHINESE_PINYIN_M')");
		List<Map> list = null;
		if(pageSize <= 0){
			list = EntityManager.jdbcquery(sb.toString());
		}else {
			list = PageQuery.jdbcSqlserverQuery(request, "billNo", sb.toString(), pageSize);
		}
		return list;
	}
}
