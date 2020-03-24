package com.winsafe.drp.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.SalesConsumeInventoryReportService;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DbUtil; 
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.BasePage;
import com.winsafe.hbm.util.pager2.Page;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.hbm.util.pager2.PageResult;

public class AppSalesConsumptionInventoryMonthlyVolumeReport {

	/**
	 * 查询SalesConsumptionInventoryMonthlyVolumeReport
	 * @param request
	 * @param users
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked") 
	public List<Map> getSalesConsumptionInventoryMonthlyVolumeReport(HttpServletRequest request, UsersBean users) throws Exception {
		//生成根据页面条件生成whereSql
		SalesConsumeInventoryReportService scirs = new SalesConsumeInventoryReportService();
		StringBuffer whereSql = new StringBuffer();
		Map paraMap = new HashMap(request.getParameterMap());
		//开始日期调整到本月月初
		if(!StringUtil.isEmpty((String)paraMap.get("beginDate"))){
			String beginDateStr = (String)paraMap.get("beginDate");
			Date beginDate = Dateutil.StringToDate(beginDateStr);
			Calendar c = Calendar.getInstance();
			c.setTime(beginDate);
			c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);
			paraMap.put("beginDate", Dateutil.formatDate(c.getTime()));
			paraMap.put("year", c.get(Calendar.YEAR)+"");
			paraMap.put("month", (c.get(Calendar.MONTH) + 1)+"");
			c.set(Calendar.DAY_OF_MONTH, 0);
			paraMap.put("lastMonthDate", Dateutil.formatDate(c.getTime()) + " 23:59:59");
			whereSql.append("\r\n and temp.thedate >=to_date('").append(((String)paraMap.get("beginDate")).trim()).append("','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		//对于结束日期调整到下个月月初
		if(!StringUtil.isEmpty((String)paraMap.get("endDate"))){
			String endDateStr = (String)paraMap.get("endDate");
			Date endDate = Dateutil.StringToDate(endDateStr);
			Calendar c = Calendar.getInstance();
			c.setTime(endDate);
			c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);
			c.add(Calendar.MONTH, 1);
			paraMap.put("endDate", Dateutil.formatDate(c.getTime()));
			whereSql.append("\r\n and temp.thedate < to_date('").append(((String)paraMap.get("endDate")).trim()).append("','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		//区域
		if(!StringUtil.isEmpty((String)paraMap.get("region"))){
			whereSql.append(" \r\n and temp.regioncode = '").append(((String)paraMap.get("region")).trim()).append("' "); 
		}
		//机构条件
		if(!StringUtil.isEmpty((String)paraMap.get("organId"))){
			whereSql.append(" \r\n and temp.organId = '").append(((String)paraMap.get("organId")).trim()).append("' "); 
		}
		//仓库条件
		if(!StringUtil.isEmpty((String)paraMap.get("warehouseId"))){
			whereSql.append(" \r\n and temp.warehouseId = '").append(((String)paraMap.get("warehouseId")).trim()).append("' "); 
		}
		//产品条件
		if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) {
			whereSql.append("\r\n and temp.productName = '").append(((String)paraMap.get("ProductName")).trim()).append("' and temp.specmode= '").append(((String)paraMap.get("packSizeName")).trim()).append("' ");
		} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) {
			whereSql.append("\r\n and temp.productName = '").append(((String)paraMap.get("ProductName")).trim()).append("' ");
		}
		
//		//管辖权限
//		whereSql.append("\r\n and temp.warehouseId in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+")");
		
		StringBuffer sb = new StringBuffer();
		sb.append("\r\n select * from ( ");
		sb.append("\r\n select oregion.regioncode, oregion.sortname as regionName, oprovince.id as oProvinceId, oprovince.areaname as oProvinceName, o.oecode, w.Id as warehouseId, w.warehousename as warehouseName, o.id as organId, o.organname, p.id as productid, p.mcode, p.matericalchdes, p.matericalendes, p.productname, p.productnameen, p.packsizeNameEn, temp1.year, temp1.month, temp1.YEAR||'/'||temp1.month as displayDate, to_date((temp1.YEAR||'-'||temp1.month), 'yyyy-mm') as thedate, temp1.sales_volume * p.boxquantity as salesVolume, temp1.comsumption_volume * p.boxquantity as comsumptionVolume, temp1.lastYearSalesVolume * p.boxquantity as lastYearSalesVolume, temp1.lastYearComsumptionVolume * p.boxquantity as lastYearComsumptionVolume,temp1.other_comsumption_volume * p.boxquantity as otherComsumptionVolume,  temp1.lastYearOtherComsumptionVolume * p.boxquantity as lastYearOtherComsumptionVolume ");
		sb.append("\r\n from ( ");
		sb.append("\r\n 	select sch.*, sch.year-1 as lastYear, nvl(lastYearSch.sales_volume,0) as lastYearSalesVolume, nvl(lastYearSch.comsumption_volume,0) as lastYearComsumptionVolume, nvl(lastYearSch.other_comsumption_volume,0) as lastYearOtherComsumptionVolume ");
		sb.append("\r\n 	from "); 
		sb.append("\r\n 	( ");
		sb.append("\r\n 	    select organid,warehouseid, productid, year, month, sum(sales_volume) as sales_volume, sum(comsumption_volume) as comsumption_volume, sum(other_comsumption_volume) as other_comsumption_volume  from (");
		sb.append("\r\n 		select organid,warehouseid, productid, year, month, sum(sales_volume) as sales_volume, sum(comsumption_volume+OTHER_CONSUM_VOLUME) as comsumption_volume, sum(OTHER_CONSUM_VOLUME) as other_comsumption_volume ");
		sb.append("\r\n 		from SALES_CONSUM_HISTORY ");
		sb.append("\r\n         where 1=1 ");
		//仓库条件 
		if(!StringUtil.isEmpty((String)paraMap.get("warehouseId"))){
			sb.append(" \r\n and warehouseId = '").append(((String)paraMap.get("warehouseId")).trim()).append("' "); 
		}
		//管辖权限
		if(DbUtil.isDealer(users)) {
			sb.append("\r\n and warehouseId in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+")");
		}
		sb.append("\r\n 		group by organid,warehouseid, productid, year, month ");
		sb.append("\r\n 			    UNION ");
		sb.append("\r\n 			    select ps.organId, ps.warehouseId, ps.productId, "+paraMap.get("year")+","+paraMap.get("month")+",0,0,0 from ( ");
		sb.append(scirs.getInventorySql((String)paraMap.get("ProductName"), (String)paraMap.get("packSizeName"), (String)paraMap.get("region"), (String)paraMap.get("organId"), (String)paraMap.get("warehouseId"), (String)paraMap.get("lastMonthDate"), users));
		sb.append("\r\n                 having sum(ps.stockpile) <> 0");
		sb.append("\r\n 	) GROUP BY productId, organId, warehouseId ,year,month ");
		sb.append("\r\n 	) sch ");
		sb.append("\r\n 	 left join ( ");
		sb.append("\r\n 		select organid,warehouseid, productid, year, month, sum(sales_volume) as sales_volume, sum(comsumption_volume+OTHER_CONSUM_VOLUME) as comsumption_volume, sum(OTHER_CONSUM_VOLUME) as other_comsumption_volume ");
		sb.append("\r\n 		from SALES_CONSUM_HISTORY ");
		sb.append("\r\n         where 1=1 ");
		//仓库条件
		if(!StringUtil.isEmpty((String)paraMap.get("warehouseId"))){
			sb.append(" \r\n and warehouseId = '").append(((String)paraMap.get("warehouseId")).trim()).append("' "); 
		}
		//管辖权限
		if(DbUtil.isDealer(users)) {
			sb.append("\r\n and warehouseId in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+")");
		}
		sb.append("\r\n 		group by organid,warehouseid, productid, year, month ");
		sb.append("\r\n 	) lastYearSch on sch.productid=lastYearSch.productid and SCH.organid=lastYearSch.organid and SCH.year=lastYearSch.year+1 and SCH.month=lastYearSch.month and SCH.warehouseid = lastYearSch.warehouseid");
		sb.append("\r\n ) temp1 "); 
		sb.append("\r\n join product p on p.id=temp1.PRODUCTID "); 
		sb.append("\r\n join warehouse w on w.id=temp1.warehouseid and w.USEFLAG = 1"); 
		sb.append("\r\n join organ o on o.id=temp1.organId "); 
		if(!DbUtil.isDealer(users)) {
			sb.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
		sb.append("\r\n left join country_area oprovince on oprovince.id=o.province "); 
		sb.append("\r\n left join region_area oregionarea on oregionarea.areaid=oprovince.id "); 
		sb.append("\r\n left join region oregion on oregion.regioncode=oregionarea.regioncodeid "); 
		sb.append("\r\n where p.USEFLAG = 1 "); //过滤不可用的产品
		sb.append("\r\n ) temp");
		sb.append("\r\n where 1=1 ").append(whereSql);
		sb.append("\r\n order by NLSSORT(regionName,'NLS_SORT = SCHINESE_PINYIN_M'),NLSSORT(oProvinceName,'NLS_SORT = SCHINESE_PINYIN_M'),NLSSORT(organname,'NLS_SORT = SCHINESE_PINYIN_M'), NLSSORT(productnameen,'NLS_SORT = SCHINESE_PINYIN_M')");
		System.out.println(sb.toString());
		List<Map> list = EntityManager.jdbcquery(sb.toString());
		return list;
	}
	
	/**
	 * 分页查询SalesConsumptionInventoryMonthlyVolumeReport
	 * @param request
	 * @param pageSize
	 * @param users
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getSalesConsumptionInventoryMonthlyVolumeReport(HttpServletRequest request, int pageSize, UsersBean users) throws Exception {
		SalesConsumeInventoryReportService scirs = new SalesConsumeInventoryReportService();
		//生成根据页面条件生成whereSql
 		StringBuffer whereSql = new StringBuffer();
		StringBuffer dateWhereSql = new StringBuffer();
		Map paraMap = new HashMap(request.getParameterMap());
		//开始日期调整到本月月初
		if(!StringUtil.isEmpty((String)paraMap.get("beginDate"))){
			String beginDateStr = (String)paraMap.get("beginDate");
			Date beginDate = Dateutil.StringToDate(beginDateStr);
			Calendar c = Calendar.getInstance();
			c.setTime(beginDate);
			c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);
			paraMap.put("beginDate", Dateutil.formatDate(c.getTime()));
			paraMap.put("year", c.get(Calendar.YEAR)+"");
			paraMap.put("month", (c.get(Calendar.MONTH) + 1)+"");
			c.set(Calendar.DAY_OF_MONTH, 0);
			paraMap.put("lastMonthDate", Dateutil.formatDate(c.getTime()) + " 23:59:59");
			dateWhereSql.append("\r\n and temp2_1.thedate >=to_date('").append(((String)paraMap.get("beginDate")).trim()).append("','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		
		//对于结束日期调整到下个月月初
		if(!StringUtil.isEmpty((String)paraMap.get("endDate"))){
			String endDateStr = (String)paraMap.get("endDate");
			Date endDate = Dateutil.StringToDate(endDateStr);
			Calendar c = Calendar.getInstance();
			c.setTime(endDate);
			c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);
			c.add(Calendar.MONTH, 1);
			paraMap.put("endDate", Dateutil.formatDate(c.getTime()));
			dateWhereSql.append("\r\n and temp2_1.thedate < to_date('").append(((String)paraMap.get("endDate")).trim()).append("','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		}
		//区域
		if(!StringUtil.isEmpty((String)paraMap.get("region"))){
			whereSql.append(" \r\n and oregion.regioncode = '").append(((String)paraMap.get("region")).trim()).append("' "); 
		}
		//机构条件
		if(!StringUtil.isEmpty((String)paraMap.get("organId"))){
			whereSql.append(" \r\n and o.id = '").append(((String)paraMap.get("organId")).trim()).append("' "); 
		}
		if(!DbUtil.isDealer(users)) {
			whereSql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
//		//仓库条件
//		if(!StringUtil.isEmpty((String)paraMap.get("warehouseId"))){
//			whereSql.append(" \r\n and w.id = '").append(((String)paraMap.get("warehouseId")).trim()).append("' "); 
//		}
		//产品条件
		if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) {
			whereSql.append("\r\n and p.productName = '").append(((String)paraMap.get("ProductName")).trim()).append("' and p.specmode= '").append(((String)paraMap.get("packSizeName")).trim()).append("' ");
		} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) {
			whereSql.append("\r\n and p.productName = '").append(((String)paraMap.get("ProductName")).trim()).append("' ");
		}
		
		StringBuffer temp1_1SQL = new StringBuffer();
		temp1_1SQL.append("\r\n 			select oregion.regioncode, oregion.sortname as regionName, oprovince.id as oProvinceId, oprovince.areaname as oProvinceName, o.oecode, w.Id as warehouseId, w.warehousename as warehouseName, o.id as organId, o.organname, p.id as productid, p.mcode, p.matericalchdes, p.matericalendes, p.productname, p.productnameen, p.packsizeNameEn ");
		temp1_1SQL.append("\r\n 			from ( ");
		temp1_1SQL.append("\r\n 				select organid,warehouseid, productid ");
		temp1_1SQL.append("\r\n 				from SALES_CONSUM_HISTORY "); 
		temp1_1SQL.append("\r\n 				where 1=1 "); 
		//仓库条件
		if(!StringUtil.isEmpty((String)paraMap.get("warehouseId"))){
			temp1_1SQL.append(" \r\n and warehouseid = '").append(((String)paraMap.get("warehouseId")).trim()).append("' "); 
		}
		//管辖权限
		if(DbUtil.isDealer(users)) {
			temp1_1SQL.append("\r\n             and warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+")");
		}
		temp1_1SQL.append("\r\n 				and to_date((YEAR||'-'||month), 'yyyy-mm')>=to_date('").append(((String)paraMap.get("beginDate")).trim()).append("','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件 
		temp1_1SQL.append("\r\n 				and to_date((YEAR||'-'||month), 'yyyy-mm')< to_date('").append(((String)paraMap.get("endDate")).trim()).append("','yyyy-MM-dd hh24:mi:ss') "); //结束时间条件
		temp1_1SQL.append("\r\n 				group by organid,warehouseid, productid ");
		temp1_1SQL.append("\r\n 			    UNION ");
		temp1_1SQL.append("\r\n 			    select ps.organId, ps.warehouseId, ps.productId from ( ");
		temp1_1SQL.append(scirs.getInventorySql((String)paraMap.get("ProductName"), (String)paraMap.get("packSizeName"), (String)paraMap.get("region"), (String)paraMap.get("organId"), (String)paraMap.get("warehouseId"), (String)paraMap.get("lastMonthDate"), users));
		temp1_1SQL.append("\r\n                 having sum(ps.stockpile) <> 0");
		temp1_1SQL.append("\r\n 			) temp1_1_1 ");
		temp1_1SQL.append("\r\n 			join product p on p.id=temp1_1_1.PRODUCTID "); 
		temp1_1SQL.append("\r\n 			join organ o on o.id=temp1_1_1.organid "); 
		temp1_1SQL.append("\r\n 			join warehouse w on w.id=temp1_1_1.warehouseid and w.USEFLAG = 1"); 
		temp1_1SQL.append("\r\n 			left join country_area oprovince on oprovince.id=o.province "); 
		temp1_1SQL.append("\r\n 			left join region_area oregionarea on oregionarea.areaid=oprovince.id "); 
		temp1_1SQL.append("\r\n 			left join region oregion on oregion.regioncode=oregionarea.regioncodeid ");
		temp1_1SQL.append("\r\n         	where 1=1 and p.USEFLAG = 1 ").append(whereSql);
		
		BasePage bp = new BasePage(request, pageSize);
		int totalCount = PageQuery.getJdbcCount(temp1_1SQL.toString());
		Page page = new Page(bp.getPageNo(), bp.getPageSite(), totalCount);
		
		StringBuffer sb = new StringBuffer();
		sb.append("\r\n select temp1.*, case when temp2.year is null then "+paraMap.get("year")+" else temp2.year end as year, case when temp2.month is null then "+paraMap.get("month")+" else temp2.month end as month, case when temp2.displayDate is null then "+paraMap.get("year")+"||'/'||"+paraMap.get("month")+" else temp2.displayDate end as displayDate, case when temp2.thedate is null then to_date("+paraMap.get("year")+"||'-'||"+paraMap.get("month")+", 'yyyy-mm') else temp2.thedate end as thedate, (temp2.salesVolume * p.boxquantity) salesVolume, (temp2.comsumptionVolume * p.boxquantity) comsumptionVolume, (temp2.lastYearSalesVolume * p.boxquantity) lastYearSalesVolume, (temp2.lastYearComsumptionVolume * p.boxquantity) lastYearComsumptionVolume, (temp2.otherComsumptionVolume * p.boxquantity) otherComsumptionVolume,(temp2.lastYearOtherComsumptionVolume * p.boxquantity) lastYearOtherComsumptionVolume ");
		sb.append("\r\n from ( ");
		sb.append("\r\n 	SELECT * ");
		sb.append("\r\n 	FROM ( ");
		sb.append("\r\n 		select ROW_NUMBER() Over(order by organid, warehouseid,  productid) r, temp1_1.* ");
		sb.append("\r\n 		from ( ");
		//sb.append("\r\n 			select oregion.regioncode, oregion.sortname as regionName, oprovince.id as oProvinceId, oprovince.areaname as oProvinceName, o.oecode, o.id as organId, o.organname, w.Id as warehouseId, w.warehousename as warehouseName, p.id as productid, p.mcode, p.matericalchdes, p.matericalendes, p.productname, p.productnameen, p.packsizeNameEn ");
		//sb.append("\r\n 			from ( ");
		//sb.append("\r\n 				select organid, warehouseid, productid ");
		//sb.append("\r\n 				from SALES_CONSUM_HISTORY "); 
		//sb.append("\r\n 				group by organid, warehouseid, productid ");
		//sb.append("\r\n 			) temp1_1_1 ");
		//sb.append("\r\n 			join product p on p.id=temp1_1_1.PRODUCTID "); 
		//sb.append("\r\n 			join warehouse w on w.id=temp1_1_1.warehouseid "); 
		//sb.append("\r\n 			join organ o on o.id=w.makeorganid "); 
		//sb.append("\r\n 			left join country_area oprovince on oprovince.id=o.province "); 
		//sb.append("\r\n 			left join region_area oregionarea on oregionarea.areaid=oprovince.id "); 
		//sb.append("\r\n 			left join region oregion on oregion.regioncode=oregionarea.regioncodeid ");
		//sb.append("\r\n         	where 1=1 ").append(whereSql);
		sb.append(temp1_1SQL);
		sb.append("\r\n 		) temp1_1 "); 
		sb.append("\r\n 	) ");
		sb.append("\r\n 	where r>").append(page.getStartIndex()).append(" and r<=").append(page.getLastIndex()).append(" ");
		sb.append("\r\n ) temp1 ");
		sb.append("\r\n left join ");
		sb.append("\r\n ( ");
		sb.append("\r\n 	select temp2_1.* ");
		sb.append("\r\n 	from ( "); 
		sb.append("\r\n 		select temp2_1_1.organid,temp2_1_1.warehouseid,  temp2_1_1.productid, temp2_1_1.year, temp2_1_1.month, temp2_1_1.YEAR||'/'||temp2_1_1.month as displayDate, to_date((temp2_1_1.YEAR||'-'||temp2_1_1.month), 'yyyy-mm') as thedate, temp2_1_1.sales_volume as salesVolume, temp2_1_1.comsumption_volume as comsumptionVolume, temp2_1_1.lastYearSalesVolume, temp2_1_1.lastYearComsumptionVolume, temp2_1_1.other_comsumption_volume as otherComsumptionVolume,temp2_1_1.lastYearOtherComsumptionVolume   "); 
		sb.append("\r\n 		from "); 
		sb.append("\r\n 		( "); 
		sb.append("\r\n 			select sch.*, sch.year-1 as lastYear, nvl(lastYearSch.sales_volume,0) as lastYearSalesVolume, nvl(lastYearSch.comsumption_volume,0) as lastYearComsumptionVolume,nvl(lastYearSch.other_comsumption_volume,0) as lastYearOtherComsumptionVolume "); 
		sb.append("\r\n 			from "); 
		sb.append("\r\n 			( "); 
		sb.append("\r\n 				select organid,warehouseid, productid, year, month, sum(sales_volume) as sales_volume, sum(comsumption_volume+OTHER_CONSUM_VOLUME) as comsumption_volume, sum(OTHER_CONSUM_VOLUME) as other_comsumption_volume "); 
		sb.append("\r\n 				from SALES_CONSUM_HISTORY "); 
		sb.append("\r\n 				where 1=1 "); 
		//仓库条件
		if(!StringUtil.isEmpty((String)paraMap.get("warehouseId"))){
			sb.append(" \r\n and warehouseid = '").append(((String)paraMap.get("warehouseId")).trim()).append("' "); 
		}
		//管辖权限
		if(DbUtil.isDealer(users)) {
			sb.append("\r\n             and warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+")");
		}
		sb.append("\r\n 				group by organid,warehouseid, productid, year, month "); 
		sb.append("\r\n 			) sch "); 
		sb.append("\r\n 			left join ( "); 
		sb.append("\r\n 				select organid,warehouseid, productid, year, month, sum(sales_volume) as sales_volume, sum(comsumption_volume+OTHER_CONSUM_VOLUME) as comsumption_volume, sum(OTHER_CONSUM_VOLUME) as other_comsumption_volume "); 
		sb.append("\r\n 				from SALES_CONSUM_HISTORY "); 
		sb.append("\r\n 				where 1=1 "); 
		//仓库条件
		if(!StringUtil.isEmpty((String)paraMap.get("warehouseId"))){
			sb.append(" \r\n and warehouseid = '").append(((String)paraMap.get("warehouseId")).trim()).append("' "); 
		}
		//管辖权限
		if(DbUtil.isDealer(users)) {
			sb.append("\r\n             and warehouseid in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+")");
		}
		sb.append("\r\n 				group by organid,warehouseid, productid, year, month "); 
		sb.append("\r\n 			) lastYearSch "); 
		sb.append("\r\n 			on  SCH.organid=lastYearSch.organid and SCH.warehouseid=lastYearSch.warehouseid and  sch.productid=lastYearSch.productid and SCH.year=lastYearSch.year+1 and SCH.month=lastYearSch.month "); 
		sb.append("\r\n 		) temp2_1_1 ");
		sb.append("\r\n 	) temp2_1 ");
		sb.append("\r\n 	where 1=1 "); 
		//sb.append("\r\n 	and temp2_1.thedate >=to_date('2015-08-01','yyyy-MM-dd') "); 
		//sb.append("\r\n 	and temp2_1.thedate < to_date('2015-11-01','yyyy-MM-dd') "); 
		sb.append(dateWhereSql);
		sb.append("\r\n ) temp2 "); 
		sb.append("\r\n on temp1.organid=temp2.organid and temp1.productid=temp2.productid  and temp1.warehouseid=temp2.warehouseid");
		sb.append("\r\n join product p on p.id = temp1.productid ");
		sb.append("\r\n order by NLSSORT(temp1.regionName,'NLS_SORT = SCHINESE_PINYIN_M'),NLSSORT(temp1.oProvinceName,'NLS_SORT = SCHINESE_PINYIN_M'),NLSSORT(temp1.organname,'NLS_SORT = SCHINESE_PINYIN_M'), NLSSORT(temp1.productnameen,'NLS_SORT = SCHINESE_PINYIN_M')");
		System.out.println(sb.toString());
		Statement stmt = null;
		ResultSet rs = null;
		List list = null;
		try{	
			Session session = HibernateUtil.currentSession();
			stmt = session.connection().createStatement();
			rs = stmt.executeQuery(sb.toString());
			list = converResultSetToList(rs);

		}catch ( Exception e ){
			throw new Exception ("PageQuery getJdbcSQLServerPageList error:"+e.getMessage());
		}finally{
			if ( rs != null ){
				rs.close();
			}
			if ( stmt != null ){
				stmt.close();
			}
		}
		PageResult pr = new PageResult(page, list);
		bp.setPage(pr.getPage());
		return pr.getContent();
	}
	
	@SuppressWarnings("unchecked")
	private List converResultSetToList(ResultSet rs) throws SQLException{
		List list = new ArrayList();
		ResultSetMetaData meteData = rs.getMetaData();
		rs.setFetchSize(50);
		int columnCount = meteData.getColumnCount();
		Map map = null;
		while ( rs.next() ){
			map = new HashMap();
			for ( int i = 1 ; i <= columnCount ; i++ ){
				map.put(meteData.getColumnName(i).toLowerCase(), rs.getString(i));
			}
			list.add(map);
		}
		return list;
	}
	
	/*@SuppressWarnings("unchecked")
	public List<Map> getSalesConsumptionInventoryMonthlyVolumeReport(HttpServletRequest request, int pageSize, UsersBean users) throws Exception {
		//生成根据页面条件生成whereSql
		StringBuffer whereSql = new StringBuffer();
		Map paraMap = new HashMap(request.getParameterMap());
		//开始日期调整到本月月初
		if(!StringUtil.isEmpty((String)paraMap.get("beginDate"))){
			String beginDateStr = (String)paraMap.get("beginDate");
			Date beginDate = Dateutil.StringToDate(beginDateStr);
			Calendar c = Calendar.getInstance();
			c.setTime(beginDate);
			c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);
			paraMap.put("beginDate", Dateutil.formatDate(c.getTime()));
			whereSql.append("\r\n and temp.thedate >=to_date('").append(((String)paraMap.get("beginDate")).trim()).append("','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		//对于结束日期调整到下个月月初
		if(!StringUtil.isEmpty((String)paraMap.get("endDate"))){
			String endDateStr = (String)paraMap.get("endDate");
			Date endDate = Dateutil.StringToDate(endDateStr);
			Calendar c = Calendar.getInstance();
			c.setTime(endDate);
			c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);
			c.add(Calendar.MONTH, 1);
			paraMap.put("endDate", Dateutil.formatDate(c.getTime()));
			whereSql.append("\r\n and temp.thedate < to_date('").append(((String)paraMap.get("endDate")).trim()).append("','yyyy-MM-dd hh24:mi:ss') "); //开始时间条件
		}
		//区域
		if(!StringUtil.isEmpty((String)paraMap.get("region"))){
			whereSql.append(" \r\n and temp.regioncode = '").append(((String)paraMap.get("region")).trim()).append("' "); 
		}
		//机构条件
		if(!StringUtil.isEmpty((String)paraMap.get("organId"))){
			whereSql.append(" \r\n and temp.organId = '").append(((String)paraMap.get("organId")).trim()).append("' "); 
		}
		//仓库条件
		if(!StringUtil.isEmpty((String)paraMap.get("warehouseId"))){
			whereSql.append(" \r\n and temp.warehouseId = '").append(((String)paraMap.get("warehouseId")).trim()).append("' "); 
		}
		//产品条件
		if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) {
			whereSql.append("\r\n and temp.productName = '").append(((String)paraMap.get("ProductName")).trim()).append("' and temp.specmode= '").append(((String)paraMap.get("packSizeName")).trim()).append("' ");
		} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) {
			whereSql.append("\r\n and temp.productName = '").append(((String)paraMap.get("ProductName")).trim()).append("' ");
		}
		
		//管辖权限
		whereSql.append("\r\n and temp.warehouseId in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+")");
		
		StringBuffer sb = new StringBuffer();
		sb.append("\r\n select * from ( ");
		sb.append("\r\n select oregion.regioncode, oregion.sortname as regionName, oprovince.id as oProvinceId, oprovince.areaname as oProvinceName, o.oecode, o.id as organId, o.organname, w.Id as warehouseId, w.warehousename as warehouseName, p.id as productid, p.mcode, p.matericalchdes, p.matericalendes, p.productname, p.productnameen, p.packsizeNameEn, temp1.year, temp1.month, temp1.YEAR||'/'||temp1.month as displayDate, to_date((temp1.YEAR||'-'||temp1.month), 'yyyy-mm') as thedate, temp1.sales_volume as salesVolume, temp1.comsumption_volume as comsumptionVolume, temp1.lastYearSalesVolume, temp1.lastYearComsumptionVolume ");
		sb.append("\r\n from ( ");
		sb.append("\r\n 	select sch.*, sch.year-1 as lastYear, nvl(lastYearSch.sales_volume,0) as lastYearSalesVolume, nvl(lastYearSch.comsumption_volume,0) as lastYearComsumptionVolume ");
		sb.append("\r\n 	from "); 
		sb.append("\r\n 	( ");
		sb.append("\r\n 		select organid, warehouseid, productid, year, month, sum(sales_volume) as sales_volume, sum(comsumption_volume) as comsumption_volume ");
		sb.append("\r\n 		from SALES_CONSUM_HISTORY ");
		sb.append("\r\n 		group by organid, warehouseid, productid, year, month ");
		sb.append("\r\n 	) sch ");
		sb.append("\r\n 	 left join ( ");
		sb.append("\r\n 		select organid, warehouseid, productid, year, month, sum(sales_volume) as sales_volume, sum(comsumption_volume) as comsumption_volume ");
		sb.append("\r\n 		from SALES_CONSUM_HISTORY ");
		sb.append("\r\n 		group by organid, warehouseid, productid, year, month ");
		sb.append("\r\n 	) lastYearSch on sch.productid=lastYearSch.productid and SCH.organid=lastYearSch.organid and SCH.WAREHOUSEID=lastYearSch.WAREHOUSEID and SCH.year=lastYearSch.year+1 and SCH.month=lastYearSch.month ");
		sb.append("\r\n ) temp1 "); 
		sb.append("\r\n left join product p on p.id=temp1.PRODUCTID "); 
		sb.append("\r\n left join warehouse w on w.id=temp1.warehouseid "); 
		sb.append("\r\n left join organ o on o.id=w.makeorganid "); 
		sb.append("\r\n left join country_area oprovince on oprovince.id=o.province "); 
		sb.append("\r\n left join region_area oregionarea on oregionarea.areaid=oprovince.id "); 
		sb.append("\r\n left join region oregion on oregion.regioncode=oregionarea.regioncodeid "); 
		sb.append("\r\n ) temp");
		sb.append("\r\n where 1=1 ").append(whereSql);
		
		BasePage bp = new BasePage(request, pageSize);
		int totalCount = PageQuery.getJdbcCount(sb.toString());
		Page page = new Page(bp.getPageNo(), bp.getPageSite(), totalCount);
		
		List<Map> list = PageQuery.jdbcSqlserverQuery(request, "regioncode, oProvinceId, organId, warehouseId, productid, thedate", sb.toString(), pageSize);
		return list;
	}*/
}
