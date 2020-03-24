package com.winsafe.drp.action.newreport;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.hibernate.HibernateException;
import org.hibernate.Session; 

import com.winsafe.drp.dao.SalesConsumMonthReportForm;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.ReportServices;
import com.winsafe.drp.server.SalesConsumeInventoryReportService;
import com.winsafe.drp.service.report.SalesConsumptionInventoryMonthlyVolumeReportService;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.BasePage;
import com.winsafe.hbm.util.pager2.Page;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.hbm.util.pager2.PageResult;

import common.Logger;

/**
 * 销售消耗库存月数量报表 Create Time 2015-10-8 下午02:00:23
 * 
 * @param scrForm
 * @return
 * @author Jacky.Chen
 */
public class SalesMonthlyReportService  extends ReportServices{
	private static Logger logger = Logger
			.getLogger(SalesMonthlyReportService.class);
	private SalesConsumeInventoryReportService scirs=new SalesConsumeInventoryReportService();
	private SalesConsumptionInventoryMonthlyVolumeReportService service = new SalesConsumptionInventoryMonthlyVolumeReportService();
	
	/**
	 * @param request
	 * @param pageSize
	 * @param queryForm
	 * @param users
	 * @return
	 * @throws Exception
	 */
	public List<SalesConsumMonthReportForm> queryReport(HttpServletRequest request, int pageSize,SalesConsumMonthReportForm queryForm,UsersBean users) throws Exception{
		
		SalesConsumInventoryMonthlySummaryData scimsData = new SalesConsumInventoryMonthlySummaryData();
		List<Map<String,String>> result = null;
		
		if(pageSize < 0) {
			result =  queryResult(queryForm, users);
		} else {
			result =  queryResult(queryForm, request, pageSize, users);
		}
		
		Map<String,SalesConsumMonthReportForm> resultListMap = new LinkedHashMap<String, SalesConsumMonthReportForm>(); 
		if (result != null && result.size() > 0) {
			
			Calendar inventoryDate = Calendar.getInstance();
			inventoryDate.setTime(DateUtil.StringToDate(queryForm.getBeginDate()));
			String fromDate = queryForm.getBeginDate().substring(0,7).replace("-", "");
			String toDate = queryForm.getEndDate().substring(0,7).replace("-", "");
			
			Map<String, Double> monthEndInventoryMap = scirs.getMonthBeginInventoryMap(queryForm.getProductName(), queryForm.getPackSizeName(), queryForm.getRegion(), queryForm.getOrganId(), queryForm.getWarehouseId(), inventoryDate, fromDate, toDate, users);
			for (Map map : result) {
				SalesConsumMonthReportForm scForm = new SalesConsumMonthReportForm();
				// 将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, scForm);
				String key = scForm.getProductId() + "_" + scForm.getOrganId() + "_" + scForm.getWarehouseId() + "_" +scForm.getYear() + "_" + scForm.getMonth();
				String resultKey = scForm.getProductId() + "_" + scForm.getOrganId()+ "_" + scForm.getWarehouseId();
				
				//获取计量单位单价
				scForm.setValue(scirs.getPrductRollingPrice(scForm.getYear(),scForm.getMonth(), scForm.getProductId(), scForm.getOrganId()));
				//设置销售
				scForm.setSalesVolume(ArithDouble.mul(scForm.getSalesVolume(), scForm.getBoxQuantity()));
				scForm.setSalesValue(ArithDouble.mul(scForm.getSalesVolume(), scForm.getValue()));
				setMonthlyData(key, scimsData.getSalesVolume(), scForm.getSalesVolume());
				setMonthlyData(key, scimsData.getSalesValue(),scForm.getSalesValue());
				//设置消耗
				scForm.setComsunptionVolume(ArithDouble.mul(scForm.getComsunptionVolume(), scForm.getBoxQuantity()));
				scForm.setComsunptionValue(ArithDouble.mul(scForm.getComsunptionVolume(), scForm.getValue()));
				setMonthlyData(key, scimsData.getConsumptionVolume(), scForm.getComsunptionVolume());
				setMonthlyData(key, scimsData.getConsumptionValue(), scForm.getComsunptionValue());
				//设置其他消耗
				scForm.setOtherConsumVolume(ArithDouble.mul(scForm.getOtherConsumVolume(), scForm.getBoxQuantity()));
				scForm.setOtherConsumValue(ArithDouble.mul(scForm.getOtherConsumVolume(), scForm.getValue()));
				setMonthlyData(key, scimsData.getOtherConsumptionVolume(), scForm.getOtherConsumVolume());
				setMonthlyData(key, scimsData.getOtherConsumptionValue(), scForm.getOtherConsumValue());
				
				service.setDataList(scForm, scimsData, queryForm);
				
				resultListMap.put(resultKey, scForm);
			}
			//设置期末库存
			for(SalesConsumMonthReportForm scForm : resultListMap.values()) {
				Double initalEndInventory = monthEndInventoryMap.get(scForm.getProductId()+"_"+scForm.getOrganId()+"_"+scForm.getWarehouseId());
				if(initalEndInventory == null) {
					initalEndInventory = 0d;
				}
				service.setInventory(scForm, initalEndInventory);
			}
		}
		return new ArrayList<SalesConsumMonthReportForm>(resultListMap.values());
	}
	
	private List<Map<String, String>> queryResult(
			SalesConsumMonthReportForm queryForm,
			UsersBean users) throws Exception {
		Calendar c = Calendar.getInstance();
		c.setTime(Dateutil.StringToDate(queryForm.getBeginDate()));
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		c.set(Calendar.DAY_OF_MONTH, 0);
		String lastMonthDate = Dateutil.formatDate(c.getTime()) + " 23:59:59";
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" \r\n select ");
		sql.append(" \r\n max(oregion.sortname) regionName,");
		sql.append(" \r\n max(oprovince.areaname) province,");
		sql.append(" \r\n sch.ORGANID organId,");
		sql.append(" \r\n max(o.oecode) oecode, ");
		sql.append(" \r\n max(o.organname) organName, ");
		sql.append(" \r\n max(p.mcode) mCode ,");
		sql.append(" \r\n max(p.productname) productName, ");
		sql.append(" \r\n sch.PRODUCTID productId , ");
		sql.append(" \r\n sch.WAREHOUSEID warehouseId , ");
		sql.append(" \r\n max(w.warehousename) warehouseName , ");
		sql.append(" \r\n max(p.productnameen) productNameen , ");
		sql.append(" \r\n max(p.matericalchdes) matericalChDes, ");
		sql.append(" \r\n max(p.matericalendes) matericalEnDes, ");
		sql.append(" \r\n max(p.packsizenameen) packSizeNameEn,");
		sql.append(" \r\n max(p.boxquantity) boxQuantity,");
		sql.append(" \r\n SUM(sch.SALES_VOLUME) salesVolume,");
		sql.append(" \r\n SUM(sch.COMSUMPTION_VOLUME + sch.other_consum_volume) comsunptionVolume,");
		sql.append(" \r\n SUM(sch.other_consum_volume) otherConsumVolume,");
		sql.append(" \r\n sch.YEAR year, ");
		sql.append(" \r\n sch.MONTH month ");
		
		sql.append(" \r\n FROM (select organId,productId,warehouseId,year,month,sum(SALES_VOLUME) as SALES_VOLUME,sum(COMSUMPTION_VOLUME) as COMSUMPTION_VOLUME,sum(other_consum_volume) as other_consum_volume");
		sql.append(" \r\n FROM SALES_CONSUM_HISTORY");
		sql.append(" \r\n WHERE 1=1 ");
		//日期条件
		if (!StringUtil.isEmpty(queryForm.getBeginDate())) {
			sql.append("\r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) >= '"+queryForm.getBeginDate().substring(0,7).replace("-", "")+"'");
		}
		if (!StringUtil.isEmpty(queryForm.getEndDate())) {
			sql.append("\r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) <= '"+queryForm.getEndDate().substring(0,7).replace("-", "")+"'");
		}
		sql.append("\r\n group by organid,warehouseid, productid, year, month ");
		sql.append("\r\n union");
		sql.append("\r\n select ps.organId,ps.productId, ps.warehouseId,"+year+","+month+",0,0,0 from ( ");
		sql.append(scirs.getInventorySql(queryForm.getProductName(), queryForm.getPackSizeName(), queryForm.getRegion(), queryForm.getOrganId(), queryForm.getWarehouseId(), lastMonthDate, users));
		sql.append("\r\n having sum(ps.stockpile) <> 0 ) sch");
		
		sql.append(" \r\n join product p on p.id=sch.PRODUCTID ");
		sql.append(" \r\n and p.USEFLAG = 1 ");//过滤不可用产品
		if (!StringUtil.isEmpty(queryForm.getWarehouseId())) {
			sql.append(" \r\n and sch.warehouseid = '"
					+ queryForm.getWarehouseId() + "' "); // 仓库条件
		}
		if (!StringUtil.isEmpty(queryForm.getOrganId())) {
			sql.append(" \r\n and sch.ORGANID = '" + queryForm.getOrganId() + "' "); // 机构条件
		}
		if (!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { // 产品条件
			sql.append(" \r\n and sch.PRODUCTID in (select id from product where productname = '"
							+ queryForm.getProductName() +"' and specmode= '"+queryForm.getPackSizeName()+"') ");
		} else if (!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { // 产品条件
			sql.append(" \r\n and sch.PRODUCTID in (select id from product where productname = '"
							+ queryForm.getProductName() + "') ");
		}
		
		sql.append(" \r\n join warehouse w on w.id=sch.warehouseId and w.USEFLAG = 1");
		sql.append(" \r\n join organ o on o.id=sch.ORGANID "); 
		if(DbUtil.isDealer(users)) {
			sql.append("\r\n and sch.warehouseId in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+")");
		} else {
			sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
		// 大区条件
		if (!StringUtil.isEmpty(queryForm.getRegion())) {
			sql.append(" \r\n join country_area oprovince on oprovince.id=o.province ");
			sql.append(" \r\n join region_area oregionarea on oregionarea.areaid=oprovince.id ");
			sql.append(" \r\n join region oregion on oregion.regioncode=oregionarea.regioncodeid  ");
			sql.append(" \r\n and oregion.regioncode = '" + queryForm.getRegion() + "'");
		} else {
			sql.append(" \r\n left join country_area oprovince on oprovince.id=o.province ");
			sql.append(" \r\n left join region_area oregionarea on oregionarea.areaid=oprovince.id ");
			sql.append(" \r\n left join region oregion on oregion.regioncode=oregionarea.regioncodeid  ");
		}
		sql.append(" \r\n GROUP BY sch.ORGANID,sch.PRODUCTID,sch.WAREHOUSEID, SCH.year, sch.month ");
		sql.append(" \r\n ORDER BY NLSSORT(regionName,'NLS_SORT = SCHINESE_PINYIN_M'),NLSSORT(province,'NLS_SORT = SCHINESE_PINYIN_M'),NLSSORT(organName,'NLS_SORT = SCHINESE_PINYIN_M'), NLSSORT(productNameen,'NLS_SORT = SCHINESE_PINYIN_M')");
		System.out.println(sql.toString());
		return EntityManager.jdbcquery(sql.toString());
	}

	private List<Map<String, String>> queryResult(
			SalesConsumMonthReportForm queryForm, HttpServletRequest request, int pageSize, UsersBean users) throws Exception {
		Calendar c = Calendar.getInstance();
		c.setTime(Dateutil.StringToDate(queryForm.getBeginDate()));
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		c.set(Calendar.DAY_OF_MONTH, 0);
		String lastMonthDate = Dateutil.formatDate(c.getTime()) + " 23:59:59";
		
		StringBuffer temp1_1SQL = new StringBuffer();
		temp1_1SQL.append(" \r\n select PRODUCTID, ORGANID, WAREHOUSEID  from SALES_CONSUM_HISTORY ");
		temp1_1SQL.append(" \r\n where 1=1 and PRODUCTID not in (select id from product where USEFLAG = 0) ");
		if (!StringUtil.isEmpty(queryForm.getWarehouseId())) {
			temp1_1SQL.append(" \r\n and warehouseid = '"
					+ queryForm.getWarehouseId() + "' "); // 仓库条件
		}
		if (!StringUtil.isEmpty(queryForm.getOrganId())) {
			temp1_1SQL.append(" \r\n and ORGANID = '" + queryForm.getOrganId() + "' "); // 机构条件
		}
		if (!StringUtil.isEmpty(queryForm.getProductName()) && !StringUtil.isEmpty(queryForm.getPackSizeName())) { // 产品条件
			temp1_1SQL.append(" \r\n and PRODUCTID in (select id from product where productname = '"
							+ queryForm.getProductName() +"' and specmode= '"+queryForm.getPackSizeName()+"') ");
		} else if (!StringUtil.isEmpty(queryForm.getProductName()) && StringUtil.isEmpty(queryForm.getPackSizeName())) { // 产品条件
			temp1_1SQL.append(" \r\n and PRODUCTID in (select id from product where productname = '"
							+ queryForm.getProductName() + "') ");
		}
		// 大区条件
		if (!StringUtil.isEmpty(queryForm.getRegion())) {
			if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
				temp1_1SQL.append(" \r\n and ORGANID in (select id from organ where organtype = 2 and organmodel = 1 and province in (select areaid from region_area where regioncodeid =  '" + queryForm.getRegion() + "'))");
			} else {
				temp1_1SQL.append(" \r\n and ORGANID in (select id from organ where province in (select areaid from region_area where regioncodeid =  '" + queryForm.getRegion() + "'))");
			}
		}
		//日期条件
		if (!StringUtil.isEmpty(queryForm.getBeginDate())) {
			temp1_1SQL.append("\r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) >= '"+queryForm.getBeginDate().substring(0,7).replace("-", "")+"'");
		}
		if (!StringUtil.isEmpty(queryForm.getEndDate())) {
			temp1_1SQL.append("\r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) <= '"+queryForm.getEndDate().substring(0,7).replace("-", "")+"'");
		}
		//管辖权限
		if(DbUtil.isDealer(users)) {
			temp1_1SQL.append("\r\n and warehouseId in (select wv.warehouse_Id from RULE_USER_WH wv where wv.user_Id="+users.getUserid()+")");
		}
		temp1_1SQL.append(" \r\n GROUP BY PRODUCTID, ORGANID, WAREHOUSEID ");
		
		temp1_1SQL.append("\r\n 			    UNION ");
		temp1_1SQL.append("\r\n 			    select ps.productId,ps.organId, ps.warehouseId from ( ");
		temp1_1SQL.append(scirs.getInventorySql(queryForm.getProductName(), queryForm.getPackSizeName(), queryForm.getRegion(), queryForm.getOrganId(), queryForm.getWarehouseId(), lastMonthDate, users));
		temp1_1SQL.append("\r\n                 having sum(ps.stockpile) <> 0");
		
		BasePage bp = new BasePage(request, pageSize);
		int totalCount = PageQuery.getJdbcCount(temp1_1SQL.toString());
		Page page = new Page(bp.getPageNo(), bp.getPageSite(), totalCount);
		
		StringBuffer sql = new StringBuffer();
		
		sql.append(" \r\n select * from (");
		sql.append(" \r\n select ");
		sql.append(" \r\n max(oregion.sortname) regionName,");
		sql.append(" \r\n max(oprovince.areaname) province,");
		sql.append(" \r\n temp1.ORGANID organId,");
		sql.append(" \r\n temp1.WAREHOUSEID warehouseId,");
		sql.append(" \r\n max(w.warehousename) warehouseName , ");
		sql.append(" \r\n max(o.oecode) oecode, ");
		sql.append(" \r\n max(o.organname) organName, ");
		sql.append(" \r\n max(p.mcode) mCode ,");
		sql.append(" \r\n max(p.productname) productName, ");
		sql.append(" \r\n temp1.PRODUCTID productId , ");
		sql.append(" \r\n max(p.productnameen) productNameen , ");
		sql.append(" \r\n max(p.matericalchdes) matericalChDes, ");
		sql.append(" \r\n max(p.matericalendes) matericalEnDes, ");
		sql.append(" \r\n max(p.packsizenameen) packSizeNameEn,");
		sql.append(" \r\n max(p.boxquantity) boxQuantity,");
		sql.append(" \r\n SUM(sch.SALES_VOLUME) salesVolume,");
		sql.append(" \r\n SUM(sch.COMSUMPTION_VOLUME + sch.other_consum_volume) comsunptionVolume,");
		sql.append(" \r\n SUM(sch.other_consum_volume) otherConsumVolume,");
		sql.append(" \r\n case WHEN sch.YEAR is null then "+year+" else sch.YEAR end year, ");
		sql.append(" \r\n case WHEN sch.MONTH is null then "+month+" else sch.MONTH end month ");
//		sql.append(" \r\n sch.MONTH month ");
		
		sql.append(" \r\n FROM  ");
		sql.append(" \r\n (SELECT * FROM (  ");
		sql.append(" \r\n select ROW_NUMBER() Over(order by PRODUCTID, ORGANID) r, temp1_1.* FROM ");
		sql.append(" \r\n ("+temp1_1SQL.toString()+") temp1_1 ");
		sql.append(" \r\n ) where r> "+page.getStartIndex()+" and r<= "+page.getLastIndex()+") temp1  ");
		sql.append(" \r\n join product p on p.id=temp1.PRODUCTID ");
		sql.append(" \r\n join organ o on o.id=temp1.ORGANID "); 
		if(!DbUtil.isDealer(users)) {
			sql.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
		sql.append(" \r\n join warehouse w on w.id=temp1.warehouseId and w.USEFLAG = 1 ");
		sql.append(" \r\n left join SALES_CONSUM_HISTORY sch on temp1.PRODUCTID = sch.PRODUCTID and temp1.ORGANID = sch.ORGANID and temp1.warehouseid = sch.warehouseid ");
		sql.append(" \r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) >= '"+queryForm.getBeginDate().substring(0,7).replace("-", "")+"'");
		sql.append(" \r\n and (year || case when month < 10 THEN '0'||month ELSE to_char(month) END) <= '"+queryForm.getEndDate().substring(0,7).replace("-", "")+"'");
		sql.append(" \r\n left join country_area oprovince on oprovince.id=o.province ");
		sql.append(" \r\n left join region_area oregionarea on oregionarea.areaid=oprovince.id ");
		sql.append(" \r\n left join region oregion on oregion.regioncode=oregionarea.regioncodeid  ");
		sql.append(" \r\n GROUP BY temp1.ORGANID,temp1.PRODUCTID,temp1.WAREHOUSEID, case WHEN sch.YEAR is null then "+year+" else sch.YEAR end, case WHEN sch.MONTH is null then "+month+" else sch.MONTH end )");
		sql.append(" \r\n ORDER BY NLSSORT(regionName,'NLS_SORT = SCHINESE_PINYIN_M'),NLSSORT(province,'NLS_SORT = SCHINESE_PINYIN_M'),NLSSORT(organName,'NLS_SORT = SCHINESE_PINYIN_M'), NLSSORT(productNameen,'NLS_SORT = SCHINESE_PINYIN_M'),WAREHOUSEID, year,month");
		
		System.out.println(sql.toString());
		
		if(request == null && pageSize == 0){
			List<Map> list =  EntityManager.jdbcquery(sql.toString());
		}else {
			Statement stmt = null;
			ResultSet rs = null;
			List<Map> list =  new ArrayList<Map>();
			try{	
				Session session = HibernateUtil.currentSession();
				stmt = session.connection().createStatement();
				rs = stmt.executeQuery(sql.toString());
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
		return null;
	}

	private void setMonthlyData(String key, Map<String, Double> salesVolume, Double newValue) {
		Double oldValue = salesVolume.get(key);
		if(oldValue == null) {
			salesVolume.put(key, newValue);
		} else {
			salesVolume.put(key, oldValue + newValue);
		}
		
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
	
	public static void main(String[] args) {
		String beginDate = "2015-09-10";
		String endDate = "2015-12-10";
//		Map<String, SalesConsumInventoryMonthlySummaryData> monthlyDataMap = new SalesMonthlyReportService()
		
	}
}
