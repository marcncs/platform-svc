package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ReportForm;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.ReportServices;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class InventoryReportService extends ReportServices{
	private static Logger logger = Logger.getLogger(InventoryReportService.class);
	// 根据条件查询
	public List<ReportForm> queryReport(HttpServletRequest request, int pageSize, Map paraMap, UsersBean users) throws Exception {
		// 对于结束日期增加一天
		if (!StringUtil.isEmpty(paraMap.get("endDate").toString())) {
			String endDateStr = paraMap.get("endDate").toString();
			Date endDate = Dateutil.StringToDate(endDateStr);
			endDate = Dateutil.addDay2Date(1, endDate);
			paraMap.put("endDate", Dateutil.formatDate(endDate));
		}

		List<ReportForm> resultList = new ArrayList<ReportForm>();
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(" \r\n select organid,warehouseid,osid,productid,auditdate,unitprice,quantity,takequantity,irs.remark,p.productname productname,p.productnameen productNameen,p.packsizenameen packSizeNameEn,p.mCode mcode,p.specmode specmode, ");
		sbBuffer.append(" \r\n p.matericalchdes matericalchdes,p.matericalendes matericalendes,pstr.sortname sortname,o.organname organname,o.oecode,ca.areaname areaname,r.sortname rsortname from ( ");
		sbBuffer.append(" \r\n select max(w.makeorganid) organid,max(w.id) warehouseid,aid.osid,aid.productid productid,max(aid.remark) remark,max(ai.auditdate) auditdate,sum(f.xquantity * unitprice) unitprice,sum(f.xquantity * quantity) quantity,sum(f.xquantity * takequantity) takequantity ");
		sbBuffer.append(" \r\n from (warehouse w inner join amount_inventory ai on w.id=ai.warehouseid) inner join amount_inventory_detail aid on ai.id=aid.osid ");
		if(DbUtil.isDealer(users)) {
			sbBuffer.append("\r\n and w.id in (select wv.warehouse_Id from rule_user_wh wv where wv.user_Id="+users.getUserid()+")");
		} else {
			sbBuffer.append(" join organ o on o.id=w.makeorganid ");
			sbBuffer.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
		if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
			sbBuffer.append(" \r\n and ai.auditdate >=to_date('" + paraMap.get("beginDate") + "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
		}
		if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
			sbBuffer.append(" \r\n and ai.auditdate < to_date('" + paraMap.get("endDate") + "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
		}
		if (!StringUtil.isEmpty((String) paraMap.get("MakeOrganID"))) {
			sbBuffer.append(" \r\n and w.makeorganid = '" + paraMap.get("MakeOrganID") + "' "); // 机构条件
		}
		
		if (!StringUtil.isEmpty((String) paraMap.get("WarehouseID"))) {
			sbBuffer.append(" \r\n and w.id = '" + paraMap.get("WarehouseID") + "' "); // 仓库条件
		}
		if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
			sbBuffer.append(" \r\n and aid.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"' and specmode= '"+(String)paraMap.get("packSizeName")+"') ");
		} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
			sbBuffer.append(" \r\n and aid.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"') ");
		}
		sbBuffer.append(" \r\n  left join F_UNIT f on f.productid = aid.productid and f.funitid = aid.unitid ");
		sbBuffer.append(" \r\n group by aid.osid,aid.productid ");
		sbBuffer.append(" \r\n union ");
		sbBuffer.append(" \r\n select max(w.makeorganid) organid,max(w.id) warehouseid,bida.osid,bida.productid productid,max(bida.remark) remark,max(bi.auditdate) auditdate,sum(f.xquantity * untiprice),sum(f.xquantity * quantity),sum(f.xquantity * takequantity) ");
		sbBuffer.append(" \r\n from (warehouse w inner join barcode_inventory bi on w.id=bi.warehouseid) inner join barcode_inventory_d_all bida on bi.id=bida.osid ");
		if(DbUtil.isDealer(users)) {
			sbBuffer.append("\r\n and w.id in (select wv.warehouse_Id from rule_user_wh wv where wv.user_Id="+users.getUserid()+")");
		}
		sbBuffer.append(" \r\n  and bi.isaudit=1  ");
		if (!StringUtil.isEmpty((String) paraMap.get("beginDate"))) {
			sbBuffer.append(" \r\n and bi.auditdate >=to_date('" + paraMap.get("beginDate") + "','yyyy-MM-dd hh24:mi:ss') "); // 开始时间条件
		}
		if (!StringUtil.isEmpty((String) paraMap.get("endDate"))) {
			sbBuffer.append(" \r\n and bi.auditdate < to_date('" + paraMap.get("endDate") + "','yyyy-MM-dd hh24:mi:ss') "); // 结束时间条件
		}
		if (!StringUtil.isEmpty((String) paraMap.get("MakeOrganID"))) {
			sbBuffer.append(" \r\n and w.makeorganid = '" + paraMap.get("MakeOrganID") + "' "); // 机构条件
		}
		if (!StringUtil.isEmpty((String) paraMap.get("WarehouseID"))) {
			sbBuffer.append(" \r\n and w.id = '" + paraMap.get("WarehouseID") + "' "); // 仓库条件
		}
		if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && !StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
			sbBuffer.append(" \r\n and bida.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"' and specmode= '"+(String)paraMap.get("packSizeName")+"') ");
		} else if(!StringUtil.isEmpty((String)paraMap.get("ProductName")) && StringUtil.isEmpty((String)paraMap.get("packSizeName"))) { //产品条件
			sbBuffer.append(" \r\n and bida.productid in (select id from product where productname = '"+(String)paraMap.get("ProductName")+"') ");
		}
		sbBuffer.append(" \r\n left join F_UNIT f on f.productid = bida.productid and f.funitid = bida.unitid ");
		sbBuffer.append(" \r\n group by bida.osid,bida.productid "); 
		sbBuffer.append(" \r\n ) irs left join product p on irs.productid = p.id  ");
		sbBuffer.append(" \r\n left join product_struct pstr on p.psid = pstr.structcode ");
		sbBuffer.append(" \r\n inner join organ o on o.id = organid ");
		if(!DbUtil.isDealer(users)) {
			sbBuffer.append(" \r\n and "+DbUtil.getWhereCondition(users, "o"));
		}
//		if(sysPro != null &&"1".equals(sysPro.getProperty("genReportOnlyForPD"))){ //是否只统计PD
//			sbBuffer.append(" \r\n and o.organtype = 2 and o.organmodel = 1"); 
//		}
		sbBuffer.append(" \r\n left join country_area ca on ca.id = o.province ");
		sbBuffer.append(" \r\n left join region_area ra on ra.areaid = ca.id ");
		sbBuffer.append(" \r\n left join region r on r.regioncode = ra.regioncodeid ");
		sbBuffer.append(" \r\n where 1=1 ");
		if (!StringUtil.isEmpty((String) paraMap.get("region"))) {
			sbBuffer.append(" \r\n and r.regioncode =  '" + paraMap.get("region") + "'"); // 大区条件
		}
		sbBuffer.append(" \r\n and p.USEFLAG = 1 "); // 产品
		sbBuffer.append(" \r\n and irs.warehouseid not in (select id from WAREHOUSE where USEFLAG = 0) "); // 仓库
		String sql = sbBuffer.toString();
		List<Map> list = new ArrayList<Map>();
		
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql);
		}else {
			list = PageQuery.jdbcSqlserverQuery(request, "organid", sql, pageSize);
		}
		
		Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
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

			// 获取单据编号
			if (p.get("osid") != null) {
				rf.setBillno(p.get("osid").toString());
			}
			// 获取单据日期
			if (p.get("auditdate") != null) {
				rf.setBilldate(p.get("auditdate").toString());
			}

			rf.setReportdate(DateUtil.getCurrentTimestamp());
			
			Product product = appProduct.loadProductById(rf.getProductid());
			//设置数量与单位
			if(StringUtil.isEmpty((String)paraMap.get("countByUnit"))) {
				if(checkRate(product.getId(),product.getSunit(),Constants.DEFAULT_UNIT_ID, funitMap)){
					// 获取库存数量
					Double stockpile = Double.valueOf(p.get("unitprice").toString());
					stockpile = changeUnit(product.getId(), product.getSunit(), stockpile, Constants.DEFAULT_UNIT_ID,funitMap);
					rf.setStockpile(decimalFormat.format(stockpile));
					// 获取盘点数量
					Double inventorypile = Double.valueOf(p.get("quantity").toString());
					inventorypile = changeUnit(product.getId(), product.getSunit(), inventorypile, Constants.DEFAULT_UNIT_ID,funitMap);
					rf.setInventorypile(decimalFormat.format(inventorypile));
					// 获取差异数量
					Double differpile = Double.valueOf(p.get("takequantity").toString());
					differpile = changeUnit(product.getId(), product.getSunit(), differpile, Constants.DEFAULT_UNIT_ID,funitMap);
					rf.setDifferpile(decimalFormat.format(differpile));
				}
				rf.setCountunit(Constants.DEFAULT_UNIT_ID);
			} else {
				if(product != null && product.getBoxquantity() != null && product.getBoxquantity() != 0d) {
					// 获取库存数量
					Double stockpile = ArithDouble.mul(Double.valueOf(p.get("unitprice").toString()), product.getBoxquantity());
					rf.setStockpile(decimalFormat.format(stockpile));
					// 获取盘点数量
					Double inventorypile = ArithDouble.mul(Double.valueOf(p.get("quantity").toString()), product.getBoxquantity());
					rf.setInventorypile(decimalFormat.format(inventorypile));
					// 获取差异数量
					Double differpile = ArithDouble.mul(Double.valueOf(p.get("takequantity").toString()), product.getBoxquantity());
					rf.setDifferpile(decimalFormat.format(differpile));
					rf.setCountunit(product.getCountunit());
				}
			}
			
			
			// 获取备注数量
			if (p.get("remark") != null) {
				rf.setRemark(p.get("remark").toString());
			}
			// 获取机构内部编码
			if (p.get("oecode") != null) {
				rf.setOecode(p.get("oecode").toString());
			}
			resultList.add(rf);
		}
		return resultList;
	}
	
	
}
