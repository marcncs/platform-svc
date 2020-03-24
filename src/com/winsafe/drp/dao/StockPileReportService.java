package com.winsafe.drp.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.NumberUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class StockPileReportService {

	public List getAgeingTotalByPageNoBatch(HttpServletRequest request,
			int pagesize, String whereSql, String orderby, String ageingSql)
			throws Exception {
		String sql = "select specmode,SORTNAME,WAREHOUSEID,PRODUCTID,PRODUCTNAME,COUNTUNIT,NCCODE,color,sum(QUANTITY) QUANTITY  from ("
				+ "select ps.sortname,  s.warehouseid, s.productid, p.productname, p.specmode, p.countunit, p.nccode , "
				+ ageingSql
				+ " , sum(STOCKPILE+PREPAREOUT) quantity "
				+ " from product_stockpile_all s, product p, product_struct ps "
				+ whereSql
				+ " group by ps.sortname, s.warehouseid, s.productid, p.productname,p.specmode,p.countunit, p.nccode, ceil(sysdate-to_date(batch,'yyyymmdd')) "
				+ ")group by specmode, SORTNAME,WAREHOUSEID,PRODUCTID,PRODUCTNAME,COUNTUNIT,NCCODE,color";
		if (!StringUtil.isEmpty(orderby)) {
			return PageQuery
					.jdbcSqlserverQuery(request, orderby, sql, pagesize);
		} else {
			return PageQuery.jdbcSqlserverQuery(request, "warehouseid", sql,
					pagesize);
		}
	}

	public List getAgeingTotalByPage(HttpServletRequest request, int pagesize,
			String whereSql, String orderby, String ageingSql) throws Exception {
		String sql = "select batch, specmode,SORTNAME,WAREHOUSEID,PRODUCTID,PRODUCTNAME,COUNTUNIT,NCCODE,color,sum(QUANTITY) QUANTITY  from ("
				+ "select ps.sortname, s.batch,  s.warehouseid, s.productid, p.productname, p.specmode, p.countunit, p.nccode , "
				+ ageingSql
				+ " , sum(STOCKPILE+PREPAREOUT) quantity "
				+ " from product_stockpile_all s, product p, product_struct ps "
				+ whereSql
				+ " group by ps.sortname, s.batch, s.warehouseid, s.productid, p.productname,p.specmode,p.countunit, p.nccode, ceil(sysdate-to_date(batch,'yyyymmdd')) "
				+ ")group by batch, specmode, SORTNAME,WAREHOUSEID,PRODUCTID,PRODUCTNAME,COUNTUNIT,NCCODE,color";
		if (!StringUtil.isEmpty(orderby)) {
			return PageQuery
					.jdbcSqlserverQuery(request, orderby, sql, pagesize);
		} else {
			return PageQuery.jdbcSqlserverQuery(request, "warehouseid", sql,
					pagesize);
		}
	}

	public List getAgeingTotalNoBatch(String whereSql, String ageingSql)
			throws Exception {
		String sql = "select specmode,SORTNAME,WAREHOUSEID,PRODUCTID,PRODUCTNAME,COUNTUNIT,NCCODE,color,sum(QUANTITY) QUANTITY  from ("
				+ "select ps.sortname,  s.warehouseid, s.productid, p.productname, p.specmode, p.countunit, p.nccode , "
				+ ageingSql
				+ " , sum(STOCKPILE+PREPAREOUT) quantity "
				+ " from product_stockpile_all s, product p, product_struct ps "
				+ whereSql
				+ " group by ps.sortname, s.warehouseid, s.productid, p.productname,p.specmode,p.countunit, p.nccode, ceil(sysdate-to_date(batch,'yyyymmdd')) "
				+ ")group by specmode, SORTNAME,WAREHOUSEID,PRODUCTID,PRODUCTNAME,COUNTUNIT,NCCODE,color";
		return EntityManager.jdbcquery(sql);
	}

	public List getAgeingTotal(String whereSql, String ageingSql)
			throws Exception {
		String sql = "select batch, specmode,SORTNAME,WAREHOUSEID,PRODUCTID,PRODUCTNAME,COUNTUNIT,NCCODE,color,sum(QUANTITY) QUANTITY  from ("
				+ "select ps.sortname, s.batch,  s.warehouseid, s.productid, p.productname, p.specmode, p.countunit, p.nccode , "
				+ ageingSql
				+ " , sum(STOCKPILE+PREPAREOUT) quantity "
				+ " from product_stockpile_all s, product p, product_struct ps "
				+ whereSql
				+ " group by ps.sortname, s.batch, s.warehouseid, s.productid, p.productname,p.specmode,p.countunit, p.nccode, ceil(sysdate-to_date(batch,'yyyymmdd')) "
				+ ")group by batch, specmode, SORTNAME,WAREHOUSEID,PRODUCTID,PRODUCTNAME,COUNTUNIT,NCCODE,color";
		return EntityManager.jdbcquery(sql);
	}

	public void setPSFBySdas(List sdas, ProductStockpileForm psf, Map p)
			throws Exception {
		String warehouseid = p.get("warehouseid").toString();
		String productid = p.get("productid").toString();
		Double avgstockpile = p.get("avgstockpile") == null ? null : Double
				.valueOf(p.get("avgstockpile").toString());
		// 日均库存数
		psf.setStockpile(avgstockpile);
		psf.setSalesAvg(0d);
		for (Object object : sdas) {
			Map map = (Map) object;
			String warehouseid1 = map.get("warehouseid").toString();
			String productid1 = map.get("productid").toString();
			if (warehouseid.equals(warehouseid1)
					&& productid.equals(productid1)) {
				Object salescount = map.get("salescount");
				if (salescount != null
						&& Double.valueOf(salescount.toString()) != 0) {
					// 产品销售量
					psf.setSalesAvg(Double.valueOf(salescount.toString()));
					// 周转率
					psf.setTurnoverRate(NumberUtil.formatDouble(Double
							.valueOf(salescount.toString()), avgstockpile)
							+ "%");
				}
			}
		}
	}

	public String getColor(Map<String, StockPileAgeing> map, int ageing)
			throws Exception {
		Iterator<StockPileAgeing> it = map.values().iterator();
		while (it.hasNext()) {
			StockPileAgeing spa = it.next();
			if (spa.getTagMinValue() <= ageing
					&& spa.getTagMaxValue() >= ageing) {
				return spa.getTagColor();
			}
		}
		return null;
	}

	public String getSql(Map<String, StockPileAgeing> map, String field) {
		StringBuffer sb = new StringBuffer();
		sb.append("(case ");
		Set<String> set = map.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String key = it.next();
			StockPileAgeing s = map.get(key);
			sb.append(" when ");
			sb.append(s.getTagMinValue() + "<=" + field);
			sb.append(" and ");
			sb.append(s.getTagMaxValue() + ">=" + field);
			sb.append(" then '" + s.getTagColor() + "'");
		}
		sb.append(" else '' end ) color");
		return sb.toString();
	}

	public void setPsfStockPileDays(List sdas, ProductStockpileForm psf, Map p)
			throws Exception {
		String warehouseid = p.get("warehouseid").toString();
		String productid = p.get("productid").toString();
		Double quantoty = Double.valueOf(p.get("quantity").toString());
		for (Object object : sdas) {
			Map map = (Map) object;
			String warehouseid1 = map.get("warehouseid").toString();
			String productid1 = map.get("productid").toString();
			if (warehouseid.equals(warehouseid1)
					&& productid.equals(productid1)) {
				Object sda = map.get("sda");
				if (sda != null && Double.valueOf(sda.toString()) != 0) {
					Double Dsda = Double.valueOf(sda.toString());
					psf.setSalesAvg(NumberUtil.formatDouble(Dsda));
					psf.setStockPileDays(NumberUtil.formatDouble(quantoty
							/ Dsda));
				}
			}
		}
	}
}
