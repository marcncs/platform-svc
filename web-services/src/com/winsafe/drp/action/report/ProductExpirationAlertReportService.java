package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ReportForm;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.ReportServices;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager2.PageQuery;
import common.Logger;

public class ProductExpirationAlertReportService extends ReportServices{
	private static Logger logger = Logger.getLogger(ProductExpirationAlertReportService.class);
	// 根据条件查询
	public List<ReportForm> queryReport(HttpServletRequest request, int pageSize, Map paraMap, UsersBean users) throws Exception {

		List<ReportForm> resultList = new ArrayList<ReportForm>();
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(" \r\n select organid,warehouseid,batch,productid,production_date,expiry_date,stockpile,p.productname productname,p.productnameen productNameen,p.packsizenameen packSizeNameEn,p.mCode mcode,p.specmode specmode,  ");
		sbBuffer.append(" \r\n p.matericalchdes matericalchdes,p.matericalendes matericalendes,pstr.sortname sortname,o.organname organname,o.oecode,ca.areaname areaname,r.sortname rsortname from (  ");
		sbBuffer.append(" \r\n select max(w.makeorganid) organid,w.id warehouseid,psa.batch batch,psa.productid productid,max(pj.production_date) production_date,max(pj.expiry_date) expiry_date,sum(f.xquantity * psa.stockpile) stockpile  ");
		sbBuffer.append(" \r\n from	product_stockpile_all psa inner join warehouse w on w.id = psa.warehouseid ");
		if(DbUtil.isDealer(users)) { 
			sbBuffer.append("\r\n and w.id in (select wv.warehouse_Id from rule_user_wh wv where wv.user_Id="+users.getUserid()+")");
		} 
		if (!StringUtil.isEmpty((String) paraMap.get("MakeOrganID"))) {
			sbBuffer.append(" \r\n and w.makeorganid = '" + paraMap.get("MakeOrganID") + "' "); // 机构条件
		}
		if (!StringUtil.isEmpty((String) paraMap.get("WarehouseID"))) {
			sbBuffer.append(" \r\n and w.id = '" + paraMap.get("WarehouseID") + "' "); // 仓库条件
		}
		if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
			sbBuffer.append(" \r\n and psa.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"' and specmode= '"+(String)paraMap.get("packSizeName")+"') ");
		} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
			sbBuffer.append(" \r\n and psa.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"') ");
		}
		if (!StringUtil.isEmpty((String) paraMap.get("Batch"))) {
			sbBuffer.append(" \r\n and psa.batch = '" + paraMap.get("Batch") + "' "); // 批号条件
		}
		sbBuffer.append(" \r\n left join F_UNIT f on f.productid = psa.productid and f.funitid = psa.countunit ");
		sbBuffer.append(" \r\n left join print_job pj on pj.batch_number = psa.batch and pj.product_id = psa.productid  ");
		sbBuffer.append(" \r\n group by w.id,psa.batch, psa.productid  ");
		if (!StringUtil.isEmpty((String) paraMap.get("validate"))) {
			sbBuffer.append(" \r\n having (SELECT round(to_number(\"TO_DATE\"(max(expiry_date), 'yyyy-MM-dd')-SYSDATE)) FROM dual) < " + (String) paraMap.get("validate") + " "); // 有效日
		}
		sbBuffer.append(" \r\n ) irs left join product p on irs.productid = p.id     ");
		sbBuffer.append(" \r\n left join product_struct pstr on p.psid = pstr.structcode    ");
		sbBuffer.append(" \r\n inner join organ o on o.id = organid   ");
		if(!DbUtil.isDealer(users)) {
			sbBuffer.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
		if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
			sbBuffer.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
		}
		sbBuffer.append(" \r\n left join country_area ca on ca.id = o.province  ");
		sbBuffer.append(" \r\n left join region_area ra on ra.areaid = ca.id    ");
		sbBuffer.append(" \r\n left join region r on r.regioncode = ra.regioncodeid  ");
		sbBuffer.append(" \r\n where 1=1 ");
		if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
			sbBuffer.append(" \r\n and r.regioncode =  '" + paraMap.get("region") + "'"); // 大区条件
		}
		sbBuffer.append(" \r\n and p.USEFLAG = 1 "); // 产品
		sbBuffer.append(" \r\n and irs.warehouseid not in (select id from WAREHOUSE where USEFLAG = 0) "); // 仓库
		String sql = sbBuffer.toString();
		List<Map> list = new ArrayList<Map>();
		Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息

		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql);
		}else {
			list = PageQuery.jdbcSqlserverQuery(request, "expiry_date", sql, pageSize);
		}
		
		for (int i = 0; i < list.size(); i++) {
			ReportForm rf = new ReportForm();
			Map p = (Map) list.get(i);
			//设置大区
			if (p.get("rsortname") != null) {
				rf.setArea(p.get("rsortname").toString());
			}
			//设置省份
			if (p.get("areaname") != null) {
				rf.setProvince(p.get("areaname").toString());
			}
			//设置机构编号
			if (p.get("organid") != null) {
				rf.setOrganid(p.get("organid").toString());
			}
			//设置机构名称
			if (p.get("organname") != null) {
				rf.setOrganname(p.get("organname").toString());
			}
			//设置产品编号
			if (p.get("productid") != null) {
				rf.setProductid(p.get("productid").toString());
			}
			//设置产品名称
			if (p.get("productname") != null) {
				rf.setProductname(p.get("productname").toString());
			}
			//设置产品英文
			if (p.get("productnameen") != null) {
				rf.setProductNameen(p.get("productnameen").toString());
			}
			//设置规格英文
			if (p.get("packsizenameen") != null) {
				rf.setPackSizeNameEn(p.get("packsizenameen").toString());
			}
			//设置内部编码
			if (p.get("mcode") != null) {
				rf.setMcode(p.get("mcode").toString());
			}
			//设置规格
			if (p.get("specmode") != null) {
				rf.setSpecmode(p.get("specmode").toString());
			}
			//设置中文名
			if (p.get("matericalchdes") != null) {
				rf.setMcodechinesename(p.get("matericalchdes").toString());
			}
			//设置英文名
			if (p.get("matericalendes") != null) {
				rf.setMcodeenglishname(p.get("matericalendes").toString());
			}
			//设置产品类别
			if (p.get("sortname") != null) {
				rf.setProductsort(p.get("sortname").toString());
			}
			// 获取仓库编号
			if (p.get("warehouseid") != null) {
				rf.setWarehouseid(p.get("warehouseid").toString());
			}
			// 获取批号
			if (p.get("batch") != null) {
				rf.setBatch(p.get("batch").toString());
			}
			// 获取生产日期
			if (p.get("production_date") != null) {
				rf.setProductdate(p.get("production_date").toString());
			}
			// 获取结束日期
			if (p.get("expiry_date") != null) {
				rf.setExpirydate(p.get("expiry_date").toString());
			}
			// 获取机构内部编码
			if (p.get("oecode") != null) {
				rf.setOecode(p.get("oecode").toString());
			}
			rf.setReportdate(DateUtil.getCurrentTimestamp());
			
			Product product = appProduct.loadProductById(rf.getProductid());
			Double stockpile = Double.valueOf(p.get("stockpile").toString());
			if(StringUtil.isEmpty((String)paraMap.get("countByUnit"))) {
				if(checkRate(product.getId(),product.getSunit(),Constants.DEFAULT_UNIT_ID, funitMap)){
					// 获取库存数量
					stockpile = changeUnit(product.getId(), product.getSunit(), stockpile, Constants.DEFAULT_UNIT_ID,funitMap);
					rf.setStockpile(decimalFormat.format(stockpile));
					rf.setCountunit(Constants.DEFAULT_UNIT_ID);
				}
			} else {
				if(product != null && product.getBoxquantity() != null && product.getBoxquantity() != 0d) {
					// 获取库存数量
					stockpile = ArithDouble.mul(stockpile, product.getBoxquantity());
					rf.setStockpile(decimalFormat.format(stockpile));
					rf.setCountunit(product.getCountunit());
				}
			}
			
			// 获取有效期天数
			if (rf.getExpirydate() != null) {
				rf.setValidate(String.valueOf(DateUtil.calculatedays(DateUtil.getCurrentDateYMD(), rf.getExpirydate())));
			}
			resultList.add(rf);
		}
		return resultList;
	}
	
}
