package com.winsafe.drp.keyretailer.salesman.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.metadata.StockMoveConfirmStatus;
import com.winsafe.drp.metadata.ValidateStatus;
import com.winsafe.hbm.util.StringUtil;

import net.sf.json.JSONObject;

public class AppSalesMan {

	public List<Map<String, String>> getCustomerInfo(String organId, String condition, String areaId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select o.id organid,o.organname,ca1.areaName provinceName,ca2.areaName cityName,ca3.areaName areasName,o.oaddr,o.OMOBILE mobile,OL.NAME,o.iskeyretailer,o.organmodel typeid,oppo.id opporganid,oppo.organname opporganname,oppo.oaddr oppoaddr from ORGAN o ");
		sql.append("LEFT JOIN OLINKMAN ol on o.id = ol.cid and o.OMOBILE = ol.mobile ");
		sql.append("LEFT JOIN COUNTRY_AREA ca1 on ca1.id = o.province ");
		sql.append("LEFT JOIN COUNTRY_AREA ca2 on ca2.id = o.city ");
		sql.append("LEFT JOIN COUNTRY_AREA ca3 on ca3.id = o.areas ");
		sql.append("LEFT JOIN S_TRANSFER_RELATION str on o.id= str.organizationid ");
		sql.append("LEFT JOIN organ oppo on oppo.id= str.opporganid ");
		sql.append("where o.VALIDATE_STATUS ="+ValidateStatus.PASSED.getValue()+" ");
		if(!StringUtil.isEmpty(areaId)) {
			sql.append(" and o.AREAS = "+areaId+" ");
		} 
		if(!StringUtil.isEmpty(organId)) {
			sql.append(" and o.id='"+organId+"'");
		} else {
			sql.append(" and "+condition);
		}
		sql.append(" order by o.id ");
		return EntityManager.jdbcquery(sql.toString());
	}

	public List<Map<String, String>> getCustomerToAudit(String organId, 
			String condition, String areaId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select o.id organid,o.organname,ca1.ID provinceId,ca1.areaName provinceName,ca2.ID cityId,ca2.areaName cityName,ca3.ID areasId,ca3.areaName areasName,o.oaddr,o.OMOBILE mobile,OL.NAME,o.organmodel typeid,oppo.id opporganid,oppo.organname opporganname,oppo.oaddr oppoaddr,u.userid,u.loginname username,u.realname from ORGAN o ");
		sql.append("LEFT JOIN OLINKMAN ol on o.id = ol.cid and o.OMOBILE = ol.mobile ");
		sql.append("LEFT JOIN COUNTRY_AREA ca1 on ca1.id = o.province ");
		sql.append("LEFT JOIN COUNTRY_AREA ca2 on ca2.id = o.city ");
		sql.append("LEFT JOIN COUNTRY_AREA ca3 on ca3.id = o.areas ");
		sql.append("LEFT JOIN ( ");
		sql.append("select min(id) id,ORGANIZATIONID organid from S_TRANSFER_RELATION ");
		sql.append("GROUP BY ORGANIZATIONID ");
		sql.append(") temp on o.id=temp.organid ");
		sql.append("LEFT JOIN ( ");
		sql.append("select organid,max(userid) userid from USER_CUSTOMER ");
		sql.append("GROUP BY organid ");
		sql.append(") temp2 on temp2.organid = o.id ");
		sql.append("LEFT JOIN USERS u on u.userid = temp2.userid ");
		sql.append("LEFT JOIN S_TRANSFER_RELATION str on TEMP.id= str.id ");
		sql.append("LEFT JOIN organ oppo on oppo.id = str.opporganid ");
		sql.append("where o.VALIDATE_STATUS not in ("+ValidateStatus.PASSED.getValue()+","+ValidateStatus.NOT_PASSED.getValue()+") and o.ISREPEAL<>1 ");
		if(!StringUtil.isEmpty(areaId)) {
			sql.append(" and o.AREAS="+areaId+" ");
		}
		if(!StringUtil.isEmpty(organId)) {
			sql.append("and o.id='"+organId+"'");
		} else {
			sql.append(" and "+condition);
		}
		sql.append(" order by o.id desc ");
		return EntityManager.jdbcquery(sql.toString());
	}
	
	public int getCustomerToAuditCount(String condition) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from ORGAN o ");
		sql.append("where o.VALIDATE_STATUS not in ("+ValidateStatus.PASSED.getValue()+","+ValidateStatus.NOT_PASSED.getValue()+") ");
		sql.append(" and "+condition);
		return EntityManager.getRecordCountBySql(sql.toString());
	}

	public List<Map<String, String>> getStockMoveToAudit(Integer userid, String billNo) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select MA.ID billno,outo.organname fromOrgan,ino.organname toOrgan,outw.warehousename fromWarehouse,inw.warehousename toWarehouse,mad.productid ,mad.productname ,mad.specmode spec,mad.quantity, 0 status,ma.movetype type,ma.movecause reason  from MOVE_APPLY ma ");
		sql.append("join USER_CUSTOMER uc on ma.OUTORGANID = UC.ORGANID and UC.USERID ="+userid);
		sql.append(" and MA.ISRATIFY = 0 ");
		if(!StringUtil.isEmpty(billNo)) {
			sql.append(" and MA.ID = '"+billNo+"' ");
		}
		sql.append("join MOVE_APPLY_DETAIL mad on mad.maid = ma.id ");
		sql.append("join ORGAN outo on outo.id=ma.OUTORGANID  ");
		sql.append("join ORGAN ino on ino.id=ma.INORGANID ");
		sql.append("join WAREHOUSE outw on outw.id=ma.OUTWAREHOUSEID ");
		sql.append("join WAREHOUSE inw on inw.id=ma.INWAREHOUSEID ");
		sql.append("UNION ALL ");
		sql.append("select MA.ID billno,outo.organname fromOrgan,ino.organname toOrgan,outw.warehousename fromWarehouse,inw.warehousename toWarehouse,mad.productid id,mad.productname name,mad.specmode spec,mad.quantity, 2 status,ma.movetype type,ma.movecause reason from MOVE_APPLY ma ");
		sql.append("join USER_CUSTOMER uc on ma.INORGANID = UC.ORGANID and UC.USERID ="+userid);
		sql.append(" and MA.ISRATIFY = 2 ");
		if(!StringUtil.isEmpty(billNo)) {
			sql.append(" and MA.ID = '"+billNo+"' ");
		}
		sql.append("join MOVE_APPLY_DETAIL mad on mad.maid = ma.id ");
		sql.append("join ORGAN outo on outo.id=ma.OUTORGANID  ");
		sql.append("join ORGAN ino on ino.id=ma.INORGANID ");
		sql.append("join WAREHOUSE outw on outw.id=ma.OUTWAREHOUSEID ");
		sql.append("join WAREHOUSE inw on inw.id=ma.INWAREHOUSEID ");
		return EntityManager.jdbcquery(sql.toString());
	}

	public int getStockMoveToAuditCount(Integer userid) {
		StringBuffer sql = new StringBuffer();
		sql.append("select (");
		sql.append("select count(MA.id) from MOVE_APPLY ma ");
		sql.append("join USER_CUSTOMER uc on ma.OUTORGANID = UC.ORGANID and UC.USERID ="+userid);
		sql.append(" and MA.ISRATIFY = 0 ");
		sql.append(")+(");
		sql.append("select count(MA.id) from MOVE_APPLY ma ");
		sql.append("join USER_CUSTOMER uc on ma.INORGANID = UC.ORGANID and UC.USERID ="+userid);
		sql.append(" and MA.ISRATIFY = 2 ");
		sql.append(") from dual");
		return EntityManager.getRecordCountBySql(sql.toString());
	}
	
	public List<Map<String, String>> getCustomerInfoFromLccal(String keyword, String wideType, String province, String city, String county) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select cli.id, cli.name, cli.leg as operName, TO_CHAR(cli.C_DATE, 'YYYY-MM-DD') as startDate, cli.status, cli.RE_CODE as creditNo from CUSTOMER_LOCAL_INFO cli where 1 = 1");
		if (province != null && province.trim().length() > 0) {
			sql.append(" and cli.province_code = '" + province + "'");
		}
		if (city != null && city.trim().length() > 0) {
			sql.append(" and cli.city_code = '" + city + "'");
		}
		if (county != null && county.trim().length() > 0) {
			sql.append(" and cli.county_code = '" + county + "'");
		}
		if ("0".equals(wideType)) {
			sql.append(" and cli.leg like '%" + keyword + "%' order by cli.leg");
		} else {
			sql.append(" and cli.name like '%" + keyword + "%' order by cli.name");
		}
		return EntityManager.jdbcquery(sql.toString());
	}
	
	public void addCustomerLocalInfo(String id, String companyName, String operName, String startDate, String status, String creditNo) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO CUSTOMER_LOCAL_INFO (id, name, leg, C_DATE, status, RE_CODE) values (");
		sb.append("'" + id + "',");
		sb.append(companyName == null ? null + "," : "'" + companyName + "',");
		sb.append(operName == null ? null + "," : "'" + operName + "',");
		sb.append(startDate == null ? null + "," : "TO_DATE('" + startDate + "', 'yyyy-mm-dd hh24:mi:ss'),");
		sb.append(status == null ? null + "," : "'" + status + "',");
		sb.append(creditNo == null ? null + ")" : "'" + creditNo + "')");
		EntityManager.executeUpdate(sb.toString());
	}

	public void addCustomerLocalInfo(JSONObject detail, JSONObject contactInfo, Map<String, String> areasMap, Organ organ) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO CUSTOMER_LOCAL_INFO (ID, NAME, RE_CODE, OFFICE, LEG, C_DATE, STATUS, PROVINCE, TYPE, AMOUNT, ADDRESS, SCOPE, DATE_FROM, DATE_TO, ISSUE_DATE, TEL, CITY, COUNTY, COUNTY_CODE, COUNTY_NAME, CITY_NAME, PROVINCE_NAME_CN, CITY_CODE, PROVINCE_CODE) ");
		sb.append("VALUES ('");
		sb.append(StringUtil.removeNull(detail.getString("KeyNo")));
		sb.append("','");
		sb.append(StringUtil.removeNull(detail.getString("Name")));
		sb.append("','");
		sb.append(StringUtil.removeNull(detail.getString("No")));
		sb.append("','");
		sb.append(StringUtil.removeNull(detail.getString("BelongOrg")));
		sb.append("','");
		sb.append(StringUtil.removeNull(detail.getString("OperName")));
		sb.append("',");
		sb.append(StringUtil.removeNull("TO_DATE('" + detail.getString("StartDate") + "', 'yyyy-mm-dd hh24:mi:ss')"));
		sb.append(",'");
		sb.append(StringUtil.removeNull(detail.getString("Status")));
		sb.append("','");
		sb.append(StringUtil.removeNull(detail.getString("Province")));
		sb.append("','");
		sb.append(StringUtil.removeNull(detail.getString("EconKind")));
		sb.append("','");
		sb.append(StringUtil.removeNull(detail.getString("RegistCapi")));
		sb.append("','");
		sb.append(StringUtil.removeNull(detail.getString("Address")));
		sb.append("','");
		sb.append(StringUtil.removeNull(detail.getString("Scope")));
		sb.append("','");
		sb.append(StringUtil.removeNull(detail.getString("TermStart")));
		sb.append("','");
		sb.append(StringUtil.removeNull(detail.getString("TeamEnd")));
		sb.append("',");
		sb.append(StringUtil.removeNull("TO_DATE('" + detail.getString("StartDate") + "', 'yyyy-mm-dd hh24:mi:ss')"));
		sb.append(",'");
		sb.append(StringUtil.removeNull(contactInfo.getString("PhoneNumber")));
		sb.append("','");
		sb.append(StringUtil.removeNull(areasMap.get(organ.getCity().toString())));
		sb.append("','");
		sb.append(StringUtil.removeNull(areasMap.get(organ.getAreas().toString())));
		sb.append("','");
		sb.append(StringUtil.removeNull(organ.getAreas().toString()));
		sb.append("','");
		sb.append(StringUtil.removeNull(areasMap.get(organ.getAreas().toString())));
		sb.append("','");
		sb.append(StringUtil.removeNull(areasMap.get(organ.getCity().toString())));
		sb.append("','");
		sb.append(StringUtil.removeNull(areasMap.get(organ.getProvince().toString())));
		sb.append("','");
		sb.append(StringUtil.removeNull(organ.getCity().toString()));
		sb.append("','");
		sb.append(StringUtil.removeNull(organ.getProvince().toString()));
		sb.append("')");
		EntityManager.executeUpdate(sb.toString());
	}
	
	public void addCustomerLocalInfo(JSONObject detail, JSONObject contactInfo) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO CUSTOMER_LOCAL_INFO (ID, NAME, RE_CODE, OFFICE, LEG, C_DATE, STATUS, PROVINCE, TYPE, AMOUNT, ADDRESS, SCOPE, DATE_FROM, DATE_TO, ISSUE_DATE, TEL, CITY, COUNTY, COUNTY_CODE, COUNTY_NAME, CITY_NAME, PROVINCE_NAME_CN, CITY_CODE, PROVINCE_CODE) ");
		sb.append("VALUES ('");
		sb.append(StringUtil.removeNull(detail.getString("KeyNo")));
		sb.append("','");
		sb.append(StringUtil.removeNull(detail.getString("Name")));
		sb.append("','");
		sb.append(StringUtil.removeNull(detail.getString("No")));
		sb.append("','");
		sb.append(StringUtil.removeNull(detail.getString("BelongOrg")));
		sb.append("','");
		sb.append(StringUtil.removeNull(detail.getString("OperName")));
		sb.append("',");
		sb.append(StringUtil.removeNull("TO_DATE('" + detail.getString("StartDate") + "', 'yyyy-mm-dd hh24:mi:ss')"));
		sb.append(",'");
		sb.append(StringUtil.removeNull(detail.getString("Status")));
		sb.append("','");
		sb.append(StringUtil.removeNull(detail.getString("Province")));
		sb.append("','");
		sb.append(StringUtil.removeNull(detail.getString("EconKind")));
		sb.append("','");
		sb.append(StringUtil.removeNull(detail.getString("RegistCapi")));
		sb.append("','");
		sb.append(StringUtil.removeNull(detail.getString("Address")));
		sb.append("','");
		sb.append(StringUtil.removeNull(detail.getString("Scope")));
		sb.append("','");
		sb.append(StringUtil.removeNull(detail.getString("TermStart")));
		sb.append("','");
		sb.append(StringUtil.removeNull(detail.getString("TeamEnd")));
		sb.append("',");
		sb.append(StringUtil.removeNull("TO_DATE('" + detail.getString("StartDate") + "', 'yyyy-mm-dd hh24:mi:ss')"));
		sb.append(",'");
		sb.append(StringUtil.removeNull(contactInfo.getString("PhoneNumber")));
		sb.append("')");
		EntityManager.executeUpdate(sb.toString());
	}

	public List<Map<String, String>> getStockMoveToAuditByCM(String billNo, String condition) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (");
		sql.append("select MA.ID billno,outo.organname fromOrgan,ino.organname toOrgan,outw.warehousename fromWarehouse,inw.warehousename toWarehouse,mad.productid ,mad.productname ,mad.specmode spec,mad.quantity, 0 status,ma.movetype type,ma.movecause reason  from MOVE_APPLY ma ");
		sql.append(" join MOVE_APPLY_DETAIL mad on mad.maid = ma.id and ma.isratify=").append(StockMoveConfirmStatus.OUT_ASM_APPROVED.getValue());
		if(!StringUtil.isEmpty(billNo)) {
			sql.append(" and MA.ID = '").append(billNo).append("' ");
		}
		sql.append(" join ORGAN outo on outo.id=ma.OUTORGANID ");
		sql.append(" and outo.").append(condition);
		sql.append(" join ORGAN ino on ino.id=ma.INORGANID ");
		sql.append(" join WAREHOUSE outw on outw.id=ma.OUTWAREHOUSEID ");
		sql.append(" join WAREHOUSE inw on inw.id=ma.INWAREHOUSEID ");
		sql.append(" UNION ALL ");
		sql.append("select MA.ID billno,outo.organname fromOrgan,ino.organname toOrgan,outw.warehousename fromWarehouse,inw.warehousename toWarehouse,mad.productid ,mad.productname ,mad.specmode spec,mad.quantity, 0 status,ma.movetype type,ma.movecause reason  from MOVE_APPLY ma ");
		sql.append(" join MOVE_APPLY_DETAIL mad on mad.maid = ma.id and ma.isratify=").append(StockMoveConfirmStatus.IN_ASM_APPROVED.getValue());
		if(!StringUtil.isEmpty(billNo)) {
			sql.append(" and MA.ID = '").append(billNo).append("' ");
		}
		sql.append(" join ORGAN outo on outo.id=ma.OUTORGANID ");
		sql.append(" join ORGAN ino on ino.id=ma.INORGANID ");
		sql.append(" and ino.").append(condition);
		sql.append(" join WAREHOUSE outw on outw.id=ma.OUTWAREHOUSEID ");
		sql.append(" join WAREHOUSE inw on inw.id=ma.INWAREHOUSEID ");
		sql.append(") temp order by billno desc");
		return EntityManager.jdbcquery(sql.toString());
	}
	
	public int getStockMoveToAuditByCMCount(String condition) {
		StringBuffer sql = new StringBuffer();
		sql.append("select (");
		sql.append("select count(MA.ID) from MOVE_APPLY ma ");
		sql.append(" join ORGAN outo on outo.id=ma.OUTORGANID and ma.isratify=").append(StockMoveConfirmStatus.OUT_ASM_APPROVED.getValue());
		sql.append(" and outo.").append(condition);
		sql.append(" ) + ( ");
		sql.append("select count(MA.ID) from MOVE_APPLY ma ");
		sql.append(" join ORGAN ino on ino.id=ma.INORGANID and ma.isratify=").append(StockMoveConfirmStatus.IN_ASM_APPROVED.getValue());
		sql.append(" and ino.").append(condition);
		sql.append(") from dual ");
		return EntityManager.getRecordCountBySql(sql.toString());
	}
	
	public int getStockMoveToAuditByCMCount(boolean firstAuditRole,
			boolean secondAuditRole) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(MA.ID) from MOVE_APPLY ma where 1=1 ");
		if(firstAuditRole && secondAuditRole) {
			sql.append(" and MA.ISRATIFY in (").append(StockMoveConfirmStatus.OUT_ASM_APPROVED.getValue()).append(",").append(StockMoveConfirmStatus.IN_ASM_APPROVED.getValue()).append(") ");
		} else if(firstAuditRole) {
			sql.append(" and MA.ISRATIFY =").append(StockMoveConfirmStatus.OUT_ASM_APPROVED.getValue());
		} else if(secondAuditRole) {
			sql.append(" and MA.ISRATIFY =").append(StockMoveConfirmStatus.IN_ASM_APPROVED.getValue());
		}
		return EntityManager.getRecordCountBySql(sql.toString());
	}

	public List<Map<String, String>> getStockMoveToAuditByCM(String billNo, boolean firstAuditRole,
			boolean secondAuditRole) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select MA.ID billno,outo.organname fromOrgan,ino.organname toOrgan,outw.warehousename fromWarehouse,inw.warehousename toWarehouse,mad.productid ,mad.productname ,mad.specmode spec,mad.quantity, MA.ISRATIFY status,ma.movetype type,ma.movecause reason  from MOVE_APPLY ma ");
		sql.append("join MOVE_APPLY_DETAIL mad on mad.maid = ma.id ");
		if(firstAuditRole && secondAuditRole) {
			sql.append(" and MA.ISRATIFY in (").append(StockMoveConfirmStatus.OUT_ASM_APPROVED.getValue()).append(",").append(StockMoveConfirmStatus.IN_ASM_APPROVED.getValue()).append(") ");
		} else if(firstAuditRole) {
			sql.append(" and MA.ISRATIFY =").append(StockMoveConfirmStatus.OUT_ASM_APPROVED.getValue());
		} else if(secondAuditRole) {
			sql.append(" and MA.ISRATIFY =").append(StockMoveConfirmStatus.IN_ASM_APPROVED.getValue());
		}
		if(!StringUtil.isEmpty(billNo)) {
			sql.append(" and MA.ID = '"+billNo+"' ");
		}
		sql.append(" join ORGAN outo on outo.id=ma.OUTORGANID  ");
		sql.append("join ORGAN ino on ino.id=ma.INORGANID ");
		sql.append("join WAREHOUSE outw on outw.id=ma.OUTWAREHOUSEID ");
		sql.append("join WAREHOUSE inw on inw.id=ma.INWAREHOUSEID ");
		return EntityManager.jdbcquery(sql.toString());
	}
	
	
}
