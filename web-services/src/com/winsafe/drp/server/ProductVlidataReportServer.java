package com.winsafe.drp.server;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.ProductVlidateReportForm;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.JProperties;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sap.metadata.YesOrNo;

public class ProductVlidataReportServer {
	// 日分类方式
	private final static String CATATYPE_DAY = "日";
	// 月分类方式
	private final static String CATATYPE_MONTH = "月";
	// 年分类方式
	private final static String CATATYPE_YEAR = "年";
	
	public List<ProductVlidateReportForm> queryReport(HttpServletRequest request, int pageSize,ProductVlidateReportForm form,String pvQueryCount,UsersBean users) throws Exception{
		List<ProductVlidateReportForm> resultList = new ArrayList<ProductVlidateReportForm>();
		Date endDate = Dateutil.StringToDate(form.getEndDate());
		// 结束日期加一天
		endDate = Dateutil.addDay2Date(1, endDate);
//		form.setEndDate(Dateutil.formatDate(endDate));
		Properties systemPro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
		
		StringBuffer sql = new StringBuffer();
		sql.append(" \r\n select q.pro_number idcode");
		sql.append(" \r\n ,q.finddt queryDate");
		sql.append(" \r\n ,q.telnumber queryAddr");
		sql.append(" \r\n ,q.chktrue chkTrue");
		sql.append(" \r\n ,q.findtype findTypeId");
		sql.append(" \r\n ,q.querynum queryCount");
		sql.append(" \r\n ,q.productid productId");
		sql.append(" \r\n  from query q");
		sql.append(" \r\n  where q.isfirstQuery = 1 ");
		// 产品条件
		if(!StringUtil.isEmpty(form.getProductName()) && !StringUtil.isEmpty(form.getPackSizeName())) { //产品条件
			sql.append(" \r\n and q.productId in (select id from product where productname = '"+form.getProductName()+"' and specmode= '"+form.getPackSizeName()+"') ");
		} else if(!StringUtil.isEmpty(form.getProductName()) && StringUtil.isEmpty(form.getPackSizeName())) { //产品条件
			sql.append(" \r\n and q.productId in (select id from product where productname = '"+form.getProductName()+"') ");
		}
//		if(!StringUtil.isEmpty(form.getProductId())){
//			sql.append(" \r\n and q.productId ='" + form.getProductId() + "' ");
//		}
		sql.append(" \r\n  and q.querynum >= " + systemPro.getProperty("productValiQueryCount"));
		sql.append(" \r\n  and EXISTS (select id from query where pro_number=q.pro_number ");
		sql.append(" \r\n and FINDDT >=to_date('" + form.getBeginDate() + "','yyyy-MM-dd hh24:mi:ss') ");
		sql.append(" \r\n and FINDDT < to_date('" + Dateutil.formatDate(endDate) + "','yyyy-MM-dd hh24:mi:ss'))");
		
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString());
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request, "idcode", sql.toString(), pageSize);
		}
		for(Map map : list){
			ProductVlidateReportForm pvForm = new ProductVlidateReportForm();
			//将Map中对应的值赋值给实例
			MapUtil.mapToObject(map, pvForm);
			//转化日期格式
			pvForm.setQueryDate(new Timestamp(pvForm.getQueryDate().getTime()));
			//
			resultList.add(pvForm);
		}
		
		return resultList;
	}
	/**
	 * 查找清单数据
	 */
	public List<ProductVlidateReportForm> queryDetailReport(HttpServletRequest request, int pageSize,String idcode,UsersBean users) throws Exception{
		List<ProductVlidateReportForm> resultList = new ArrayList<ProductVlidateReportForm>();
		
		StringBuffer sql = new StringBuffer();
		sql.append(" \r\n select pro_number idcode");
		sql.append(" \r\n ,finddt queryDate");
		sql.append(" \r\n ,telnumber queryAddr");
		sql.append(" \r\n ,chktrue chkTrue");
		sql.append(" \r\n ,findtype findTypeId");
		sql.append(" \r\n ,querynum queryCount");
		sql.append(" \r\n ,productid productId");
		sql.append(" \r\n  from query ");
		sql.append(" \r\n  where 1 = 1 ");
		sql.append(" \r\n and pro_number = '" + idcode + "'");
		
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString());
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request, "idcode", sql.toString(), pageSize);
		}
		for(Map map : list){
			ProductVlidateReportForm pvForm = new ProductVlidateReportForm();
			//将Map中对应的值赋值给实例
			MapUtil.mapToObject(map, pvForm);
			//转化日期格式
			pvForm.setQueryDate(new Timestamp(pvForm.getQueryDate().getTime()));
			resultList.add(pvForm);
		}
		return resultList;
	}
	/**
	 * 查找图表的数据
	 */
	public Map<String, String> queryChartReport(ProductVlidateReportForm form,UsersBean users) throws Exception{
		// 分类轴
		Map<String, String> resultMap = new HashMap<String, String>();
		// 开始时间
		Date beginDate = Dateutil.StringToDate(form.getBeginDate());
		// 结束时间
		Date endDate = Dateutil.StringToDate(form.getEndDate());
		// 开始年份
		int beginYear = Dateutil.getYear(beginDate);
		// 结束年份
		int endYear = Dateutil.getYear(endDate);
		// 开始月份
		int beginMonth = Dateutil.getMonth(beginDate);
		// 结束月份
		int endMonth = Dateutil.getMonth(endDate);
		if(beginYear != endYear){
			// 按照年份分类汇总数据
			resultMap = queryYearChartReport(form, users);
		}else if(beginMonth != endMonth){
			// 按照月份分类汇总数据
			resultMap = queryMonthChartReport(form, users);
		}else {
			// 按照日分类汇总数据
			resultMap = queryDayChartReport(form, users);
		}
		
		return resultMap;
	}
	
	
	/**
	 * 查找图表的数据,按照日汇总数据
	 */
	public Map<String, String> queryDayChartReport(ProductVlidateReportForm form,UsersBean users) throws Exception{
		// 分类轴
		Map<String, String> resultMap = new HashMap<String, String>();
		List<String> categories = new ArrayList<String>();
		Date beginDate = Dateutil.StringToDate(form.getBeginDate());
		Date endDate = Dateutil.StringToDate(form.getEndDate());
		// 结束日期加一天
		endDate = Dateutil.addDay2Date(1, endDate);
		form.setEndDate(Dateutil.formatDate(endDate));
		// 计算开始与结束之间的天数
		Integer days = Dateutil.getDiffDays(beginDate, endDate);
		for(int i=0 ; i<days ; i++){
			categories.add(Dateutil.formatDate(beginDate));
			beginDate = Dateutil.addDay2Date(1, beginDate);
		}
		String categoriesStr = "";
		for(String cate : categories){
			Date date = Dateutil.StringToDate(cate);
			categoriesStr += ",'" + Dateutil.formatDate(date, "dd") + "'";
		}
		if(categoriesStr.length() > 0){
			categoriesStr = "[" + categoriesStr.substring(1) + "]";
		}
		resultMap.put("categories", categoriesStr);

		// 所有查询的总数
		int[] totleData = new int[categories.size()];
		StringBuffer totleSqlBuff = new StringBuffer();
		totleSqlBuff.append(" \r\n select count(*) cnt,TO_CHAR(FINDDT,'YYYY-MM-DD') dateStr from query ");
		totleSqlBuff.append(" \r\n where FINDDT >=to_date('" + form.getBeginDate() + "','yyyy-MM-dd hh24:mi:ss') ");
		totleSqlBuff.append(" \r\n and FINDDT < to_date('" + form.getEndDate() + "','yyyy-MM-dd hh24:mi:ss') ");
		// 产品条件
		if(!StringUtil.isEmpty(form.getProductName()) && !StringUtil.isEmpty(form.getPackSizeName())) { //产品条件
			totleSqlBuff.append(" \r\n and productId in (select id from product where productname = '"+form.getProductName()+"' and specmode= '"+form.getPackSizeName()+"') ");
		} else if(!StringUtil.isEmpty(form.getProductName()) && StringUtil.isEmpty(form.getPackSizeName())) { //产品条件
			totleSqlBuff.append(" \r\n and productId in (select id from product where productname = '"+form.getProductName()+"') ");
		}
		totleSqlBuff.append(" \r\n GROUP BY TO_CHAR(FINDDT,'YYYY-MM-DD') ");
		List<Map<String,String>> totleList =  EntityManager.jdbcquery(totleSqlBuff.toString());
		Map<String, Integer> totleMap = changeToMap(totleList);
		// 查询为真的总数
		int[] trueData = new int[categories.size()];
		StringBuffer trueSqlBuff = new StringBuffer();
		trueSqlBuff.append(" \r\n select count(*) cnt,TO_CHAR(FINDDT,'YYYY-MM-DD') dateStr from query where CHKTRUE=1  ");
		trueSqlBuff.append(" \r\n and FINDDT >=to_date('" + form.getBeginDate() + "','yyyy-MM-dd hh24:mi:ss') ");
		trueSqlBuff.append(" \r\n and FINDDT < to_date('" + form.getEndDate() + "','yyyy-MM-dd hh24:mi:ss') ");
		// 产品条件
		if(!StringUtil.isEmpty(form.getProductName()) && !StringUtil.isEmpty(form.getPackSizeName())) { //产品条件
			trueSqlBuff.append(" \r\n and productId in (select id from product where productname = '"+form.getProductName()+"' and specmode= '"+form.getPackSizeName()+"') ");
		} else if(!StringUtil.isEmpty(form.getProductName()) && StringUtil.isEmpty(form.getPackSizeName())) { //产品条件
			trueSqlBuff.append(" \r\n and productId in (select id from product where productname = '"+form.getProductName()+"') ");
		}
		trueSqlBuff.append(" \r\n GROUP BY TO_CHAR(FINDDT,'YYYY-MM-DD') ");
		List<Map<String,String>> trueList =  EntityManager.jdbcquery(trueSqlBuff.toString());
		Map<String, Integer> trueMap = changeToMap(trueList);
		// 查询结果与分类轴对应
		for(int i=0 ; i<categories.size() ; i++){
			String cate = categories.get(i);
			Integer totleTmp = totleMap.get(cate);
			if(totleTmp != null){
				totleData[i] = totleTmp;
			}
			Integer trueTmp = trueMap.get(cate);
			if(trueTmp != null){
				trueData[i] = trueTmp;
			}
		}
		
		resultMap.put("totleData", Arrays.toString(totleData));
		resultMap.put("trueData", Arrays.toString(trueData));
		resultMap.put("cateType", CATATYPE_DAY);
		
		return resultMap;
	}
	
	/**
	 * 查找图表的数据,按照月汇总数据
	 */
	public Map<String, String> queryMonthChartReport(ProductVlidateReportForm form,UsersBean users) throws Exception{
		// 分类轴
		Map<String, String> resultMap = new HashMap<String, String>();
		List<String> categories = new ArrayList<String>();
		// 开始日期
		Date beginDate = Dateutil.StringToDate(form.getBeginDate());
		// 结束日期
		Date endDate = Dateutil.StringToDate(form.getEndDate());
		// 计算开始与结束之间的月数
		// 开始月份
		Integer beginMonth = Dateutil.getMonth(beginDate);
		// 结束月份
		Integer endMonth = Dateutil.getMonth(endDate);
		
		Integer months = endMonth - beginMonth + 1;
		for(int i=0 ; i<months ; i++){
			categories.add(Dateutil.formatDate(beginDate,"yyyy-MM"));
			beginDate = Dateutil.addMonth2Date(1, beginDate);
		}
		// 结束日期加一天
		endDate = Dateutil.addDay2Date(1, endDate);
		form.setEndDate(Dateutil.formatDate(endDate,"yyyy-MM-dd"));
		String categoriesStr = "";
		for(String cate : categories){
			categoriesStr += ",'" + cate + "'";
		}
		if(categoriesStr.length() > 0){
			categoriesStr = "[" + categoriesStr.substring(1) + "]";
		}
		resultMap.put("categories", categoriesStr);

		// 所有查询的总数
		int[] totleData = new int[categories.size()];
		StringBuffer totleSqlBuff = new StringBuffer();
		totleSqlBuff.append(" \r\n select count(*) cnt,TO_CHAR(FINDDT,'YYYY-MM') dateStr from query ");
		totleSqlBuff.append(" \r\n where FINDDT >=to_date('" + form.getBeginDate() + "','yyyy-MM-dd hh24:mi:ss') ");
		totleSqlBuff.append(" \r\n and FINDDT < to_date('" + form.getEndDate() + "','yyyy-MM-dd hh24:mi:ss') ");
		// 产品条件
		if(!StringUtil.isEmpty(form.getProductName()) && !StringUtil.isEmpty(form.getPackSizeName())) { //产品条件
			totleSqlBuff.append(" \r\n and productId in (select id from product where productname = '"+form.getProductName()+"' and specmode= '"+form.getPackSizeName()+"') ");
		} else if(!StringUtil.isEmpty(form.getProductName()) && StringUtil.isEmpty(form.getPackSizeName())) { //产品条件
			totleSqlBuff.append(" \r\n and productId in (select id from product where productname = '"+form.getProductName()+"') ");
		}
		totleSqlBuff.append(" \r\n GROUP BY TO_CHAR(FINDDT,'YYYY-MM') ");
		List<Map<String,String>> totleList =  EntityManager.jdbcquery(totleSqlBuff.toString());
		Map<String, Integer> totleMap = changeToMap(totleList);
		// 查询为真的总数
		int[] trueData = new int[categories.size()];
		StringBuffer trueSqlBuff = new StringBuffer();
		trueSqlBuff.append(" \r\n select count(*) cnt,TO_CHAR(FINDDT,'YYYY-MM') dateStr from query where CHKTRUE=1  ");
		trueSqlBuff.append(" \r\n and FINDDT >=to_date('" + form.getBeginDate() + "','yyyy-MM-dd hh24:mi:ss') ");
		trueSqlBuff.append(" \r\n and FINDDT < to_date('" + form.getEndDate() + "','yyyy-MM-dd hh24:mi:ss') ");
		// 产品条件
		if(!StringUtil.isEmpty(form.getProductName()) && !StringUtil.isEmpty(form.getPackSizeName())) { //产品条件
			trueSqlBuff.append(" \r\n and productId in (select id from product where productname = '"+form.getProductName()+"' and specmode= '"+form.getPackSizeName()+"') ");
		} else if(!StringUtil.isEmpty(form.getProductName()) && StringUtil.isEmpty(form.getPackSizeName())) { //产品条件
			trueSqlBuff.append(" \r\n and productId in (select id from product where productname = '"+form.getProductName()+"') ");
		}
		trueSqlBuff.append(" \r\n GROUP BY TO_CHAR(FINDDT,'YYYY-MM') ");
		List<Map<String,String>> trueList =  EntityManager.jdbcquery(trueSqlBuff.toString());
		Map<String, Integer> trueMap = changeToMap(trueList);
		// 查询结果与分类轴对应
		for(int i=0 ; i<categories.size() ; i++){
			String cate = categories.get(i);
			Integer totleTmp = totleMap.get(cate);
			if(totleTmp != null){
				totleData[i] = totleTmp;
			}
			Integer trueTmp = trueMap.get(cate);
			if(trueTmp != null){
				trueData[i] = trueTmp;
			}
		}
		
		resultMap.put("totleData", Arrays.toString(totleData));
		resultMap.put("trueData", Arrays.toString(trueData));
		resultMap.put("cateType", CATATYPE_MONTH);
		
		return resultMap;
	}
	
	
	/**
	 * 查找图表的数据,按照年汇总数据
	 */
	public Map<String, String> queryYearChartReport(ProductVlidateReportForm form,UsersBean users) throws Exception{
		// 分类轴
		Map<String, String> resultMap = new HashMap<String, String>();
		List<String> categories = new ArrayList<String>();
		Date beginDate = Dateutil.StringToDate(form.getBeginDate());
		Date endDate = Dateutil.StringToDate(form.getEndDate());
		// 开始年份
		Integer beginYear = Dateutil.getYear(beginDate);
		// 结束年份
		Integer endYear = Dateutil.getYear(endDate);
		// 计算开始与结束之间的年数
		int years = endYear - beginYear + 1;
		for(int i=0 ; i<years ; i++){
			categories.add(Dateutil.formatDate(beginDate,"yyyy"));
			beginDate = Dateutil.addYear2Date(1, beginDate);
		}
		String categoriesStr = "";
		for(String cate : categories){
			categoriesStr += ",'" + cate + "'";
		}
		if(categoriesStr.length() > 0){
			categoriesStr = "[" + categoriesStr.substring(1) + "]";
		}
		resultMap.put("categories", categoriesStr);
		// 结束日期加一天
		endDate = Dateutil.addDay2Date(1, endDate);
		form.setEndDate(Dateutil.formatDate(endDate));
		// 所有查询的总数
		int[] totleData = new int[categories.size()];
		StringBuffer totleSqlBuff = new StringBuffer();
		totleSqlBuff.append(" \r\n select count(*) cnt,TO_CHAR(FINDDT,'YYYY') dateStr from query ");
		totleSqlBuff.append(" \r\n where FINDDT >=to_date('" + form.getBeginDate() + "','yyyy-MM-dd hh24:mi:ss') ");
		totleSqlBuff.append(" \r\n and FINDDT < to_date('" + form.getEndDate() + "','yyyy-MM-dd hh24:mi:ss') ");
		// 产品条件
		if(!StringUtil.isEmpty(form.getProductName()) && !StringUtil.isEmpty(form.getPackSizeName())) { //产品条件
			totleSqlBuff.append(" \r\n and productId in (select id from product where productname = '"+form.getProductName()+"' and specmode= '"+form.getPackSizeName()+"') ");
		} else if(!StringUtil.isEmpty(form.getProductName()) && StringUtil.isEmpty(form.getPackSizeName())) { //产品条件
			totleSqlBuff.append(" \r\n and productId in (select id from product where productname = '"+form.getProductName()+"') ");
		}
		totleSqlBuff.append(" \r\n GROUP BY TO_CHAR(FINDDT,'YYYY') ");
		List<Map<String,String>> totleList =  EntityManager.jdbcquery(totleSqlBuff.toString());
		Map<String, Integer> totleMap = changeToMap(totleList);
		// 查询为真的总数
		int[] trueData = new int[categories.size()];
		StringBuffer trueSqlBuff = new StringBuffer();
		trueSqlBuff.append(" \r\n select count(*) cnt,TO_CHAR(FINDDT,'YYYY') dateStr from query where CHKTRUE=1  ");
		trueSqlBuff.append(" \r\n and FINDDT >=to_date('" + form.getBeginDate() + "','yyyy-MM-dd hh24:mi:ss') ");
		trueSqlBuff.append(" \r\n and FINDDT < to_date('" + form.getEndDate() + "','yyyy-MM-dd hh24:mi:ss') ");
		// 产品条件
		if(!StringUtil.isEmpty(form.getProductName()) && !StringUtil.isEmpty(form.getPackSizeName())) { //产品条件
			trueSqlBuff.append(" \r\n and productId in (select id from product where productname = '"+form.getProductName()+"' and specmode= '"+form.getPackSizeName()+"') ");
		} else if(!StringUtil.isEmpty(form.getProductName()) && StringUtil.isEmpty(form.getPackSizeName())) { //产品条件
			trueSqlBuff.append(" \r\n and productId in (select id from product where productname = '"+form.getProductName()+"') ");
		}
		trueSqlBuff.append(" \r\n GROUP BY TO_CHAR(FINDDT,'YYYY') ");
		List<Map<String,String>> trueList =  EntityManager.jdbcquery(trueSqlBuff.toString());
		Map<String, Integer> trueMap = changeToMap(trueList);
		// 查询结果与分类轴对应
		for(int i=0 ; i<categories.size() ; i++){
			String cate = categories.get(i);
			Integer totleTmp = totleMap.get(cate);
			if(totleTmp != null){
				totleData[i] = totleTmp;
			}
			Integer trueTmp = trueMap.get(cate);
			if(trueTmp != null){
				trueData[i] = trueTmp;
			}
		}
		
		resultMap.put("totleData", Arrays.toString(totleData));
		resultMap.put("trueData", Arrays.toString(trueData));
		resultMap.put("cateType", CATATYPE_YEAR);
		
		return resultMap;
	}
	
	private Map<String, Integer> changeToMap(List<Map<String,String>> list){
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		for(Map<String,String> map : list){
			String dataStr = map.get("datestr");
			String cnt = map.get("cnt");
			resultMap.put(dataStr, Integer.valueOf(cnt));
		}
		return resultMap;
	}
}
