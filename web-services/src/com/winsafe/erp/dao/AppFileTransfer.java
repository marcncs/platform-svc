package com.winsafe.erp.dao; 

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.erp.metadata.TransferFileStatus;
import com.winsafe.erp.pojo.TransferLog;
import com.winsafe.erp.pojo.UnitInfo;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;
public class AppFileTransfer {
	
	public void addTransferLog(TransferLog bic) throws Exception {		
		EntityManager.save(bic);		
	}
	
	public void updTransferLog(TransferLog bic)throws Exception {		
		EntityManager.update(bic);		
	}
	
	public void deleteTransferLog(Integer id) throws HibernateException, SQLException, Exception  {
		String sql = "delete from UnitInfo  where  ID=" + id; 
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List<TransferLog> getTransferLogs(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql=" from TransferLog " + whereSql + " order by id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public TransferLog getTransferLogByID(Integer id)throws Exception{
		return (TransferLog)EntityManager.find("from TransferLog where id=" + id + "");
	}

	public TransferLog getTransferLogByID(String organId, Integer templateNo, String fieldName)throws Exception{
		return (TransferLog)EntityManager.find("from UnitInfo where organId='" + organId + "' and templateNo='" + templateNo + "' and fieldName='" + fieldName + "'");
	}
	
	public List<TransferLog> getUnitInfo()throws Exception{
		return EntityManager.getAllByHql(" from TransferLog ");
	}
	
	public List<TransferLog> getUnitInfoByOrganId(String organId)throws Exception{
		return EntityManager.getAllByHql(" from TransferLog where organId='" + organId + "'");
	}
	
	
	public UnitInfo getUnitInfoByOidAndPid(String organId, String pid)throws Exception{
		return (UnitInfo)EntityManager.find("from UnitInfo where organId='" + organId + "' and productId='" +pid + "' and isactive = 1");
	}

	public Map<String, String> getNeedRepakageMap() throws HibernateException, SQLException {
		Map<String,String> needRepakageMap = new HashMap<String, String>();
		String sql = "select organid||'_'||productid oidPid,NEEDREPACKAGE from UNITINFO";
		List<Map<String,String>> list = EntityManager.jdbcquery(sql);
		for(Map<String,String> map : list) {
			needRepakageMap.put(map.get("oidpid"), map.get("needrepackage"));
		}
		return needRepakageMap;
	} 

	public boolean isProCodeAlreadyExists(String proCode) {
		String sql = "select count(*) from UnitInfo where codeSeq like '"+proCode+"%'";
		return EntityManager.getRecordCountBySql(sql) > 0;
	}

	public List<Map<String, String>> getDealersToTransfer(String startTime, String endTime) throws Exception { 
		if(!StringUtil.isEmpty(startTime)) {
			startTime = "to_date('" + startTime + "','yyyy-MM-dd hh24:mi:ss')";
		}
		if(!StringUtil.isEmpty(endTime)) {
			endTime = "to_date('" + endTime + "','yyyy-MM-dd hh24:mi:ss')";
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select o.organmodel,o.id,o.oecode,o.organname,o.parentid,po.organname poname,");
		sql.append("pca.areaname province,cca.areaname city,aca.areaname area,bsba.name_loc bigarea, ");
		sql.append("msba.name_loc middlearea, sba.name_loc smallarea,");
		sql.append("case when o.isrepeal = 1 then '是' else '否' END isrepeal,");
		sql.append("o.validate_status status,");
		sql.append("case when o.province = 1 then null else o.province end provinceid,");
		sql.append("case when o.city = 1 then null else o.city end cityid,");
		sql.append("case when o.areas = 1 then null else o.areas end areasid ");
		sql.append("from ORGAN o  ");
		sql.append("join ORGAN po on po.id=o.parentid ");
		sql.append("and o.organtype = "+OrganType.Dealer.getValue());
		if(!StringUtil.isEmpty(startTime)) {
			sql.append(" and ((o.creationTime >=" + startTime + " and o.creationTime <=" + endTime + ") or ");
			sql.append(" (o.modificationTime >=" + startTime + " and o.modificationTime <=" + endTime + ")) ");
		} else {
			sql.append(" and (o.creationTime <=" + endTime + " or o.modificationTime <= " + endTime + ") ");
		}
		sql.append("LEFT JOIN COUNTRY_AREA pca on pca.id=o.province ");
		sql.append("LEFT JOIN COUNTRY_AREA cca on cca.id=o.city ");
		sql.append("LEFT JOIN COUNTRY_AREA aca on aca.id=o.areas ");
		sql.append("LEFT JOIN SALES_AREA_COUNTRY sac on sac.countryareaid=o.areas ");
		sql.append("LEFT JOIN S_BONUS_AREA sba on sba.id=sac.salesareaid ");
		sql.append("LEFT JOIN S_BONUS_AREA msba on sba.parentid=msba.id ");
		sql.append("LEFT JOIN S_BONUS_AREA bsba on msba.parentid=bsba.id ");
		return EntityManager.jdbcquery(sql.toString());
	}

	public List<Map<String, String>> getWarehousesToTransfer(String startTime, String endTime) throws Exception {
		if(!StringUtil.isEmpty(startTime)) {
			startTime = "to_date('" + startTime + "','yyyy-MM-dd hh24:mi:ss')";
		}
		if(!StringUtil.isEmpty(endTime)) {
			endTime = "to_date('" + endTime + "','yyyy-MM-dd hh24:mi:ss')";
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select w.MAKEORGANID,w.id,w.NCCODE,w.WAREHOUSENAME,case when w.USEFLAG = 1 then '是' else '否' END USEFLAG ");
		sql.append("from WAREHOUSE w ");
		sql.append("join organ o on o.id = w.MAKEORGANID ");
		sql.append("where o.organtype ="+OrganType.Dealer.getValue());
		if(!StringUtil.isEmpty(startTime)) {
			sql.append(" and ((o.creationTime >=" + startTime + " and o.creationTime <=" + endTime + ") or ");
			sql.append(" (o.modificationTime >=" + startTime + " and o.modificationTime <= " + endTime + ")) ");
		} else {
			sql.append(" and (o.creationTime <=" + endTime + " or o.modificationTime <=" + endTime + ") ");
		}
		return EntityManager.jdbcquery(sql.toString());
	}

	public List<Map<String, String>> getBillsToTransfer(String startTime, String endTime) throws Exception {
		if(!StringUtil.isEmpty(startTime)) {
			startTime = "to_date('" + startTime + "','yyyy-MM-dd hh24:mi:ss')";
		}
		if(!StringUtil.isEmpty(endTime)) {
			endTime = "to_date('" + endTime + "','yyyy-MM-dd hh24:mi:ss')";
		}
		StringBuffer sqlFragment1 = new StringBuffer();
		sqlFragment1.append("select sam.id,tt.oid outoid,outo.organname outoname,tt.warehouseid outwid,outw.warehousename outwname,tt.inoid,ino.organname inoname,tt.inwarehouseid inwid,inw.warehousename inwname,sam.makedate,sam.auditdate,tt.auditdate shipmentdate,sam.receivedate  ");
		
		StringBuffer sqlFragment2 = new StringBuffer();
		sqlFragment2.append("from TAKE_TICKET tt ");
		sqlFragment2.append("join organ outo on tt.oid=outo.id ");
		sqlFragment2.append("join organ ino on tt.inoid=ino.id ");
		sqlFragment2.append("join WAREHOUSE outw on outw.id=tt.warehouseid ");
		sqlFragment2.append("and ((outo.organtype="+OrganType.Dealer.getValue()+" and outo.organmodel="+DealerType.PD.getValue()+") or (ino.organtype="+OrganType.Dealer.getValue()+" and ino.organmodel="+DealerType.PD.getValue()+" )) ");
		
		StringBuffer sqlTimeCondition = new StringBuffer();
		StringBuffer sqlTimeConditionForSM = new StringBuffer();
		if(!StringUtil.isEmpty(startTime)) { 
			sqlTimeCondition.append(" and ((sam.makedate>="+startTime+" and sam.makedate<="+endTime+") or (sam.auditdate>="+startTime+" and sam.auditdate<="+endTime+") or (tt.auditdate>="+startTime+" and tt.auditdate<="+endTime+") or (sam.receivedate>="+startTime+" and sam.receivedate<="+endTime+")) ");
			sqlTimeConditionForSM.append(" and ((tt.auditdate>="+startTime+" and tt.auditdate<="+endTime+") or (sam.receivedate>="+startTime+" and sam.receivedate<="+endTime+")) ");
		} else {
			sqlTimeCondition.append(" and (sam.makedate<="+endTime+" or sam.auditdate<="+endTime+" or tt.auditdate<="+endTime+" or sam.receivedate<="+endTime+") ");
			sqlTimeConditionForSM.append(" and (tt.auditdate<="+endTime+" or sam.receivedate<="+endTime+") ");
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append(sqlFragment1+",sam.bsort "+sqlFragment2);
		sql.append("and bsort = 1 ");
		sql.append("join STOCK_ALTER_MOVE sam on sam.id = tt.billno ");
		sql.append(sqlTimeCondition);
		sql.append("left join WAREHOUSE inw on inw.id=tt.inwarehouseid ");
		sql.append("UNION ALL ");
		
		sql.append(sqlFragment1+",NULL "+sqlFragment2);
		sql.append("and bsort = 2 ");
		sql.append("join STOCK_MOVE sam on sam.id = tt.billno ");
		sql.append(sqlTimeConditionForSM);
		sql.append("left join WAREHOUSE inw on inw.id=tt.inwarehouseid ");
		sql.append("UNION ALL ");
		
		sql.append(sqlFragment1+",NULL "+sqlFragment2);
		sql.append("and bsort = 7 ");
		sql.append("join ORGAN_WITHDRAW sam on sam.id = tt.billno ");
		sql.append(sqlTimeCondition);
		sql.append("left join WAREHOUSE inw on inw.id=tt.inwarehouseid ");

		return EntityManager.jdbcquery(sql.toString());
	}

	public List<Map<String, String>> getBillDetailsToTransfer(String startTime, String endTime) throws Exception {
		if(!StringUtil.isEmpty(startTime)) {
			startTime = "to_date('" + startTime + "','yyyy-MM-dd hh24:mi:ss')";
		}
		if(!StringUtil.isEmpty(endTime)) {
			endTime = "to_date('" + endTime + "','yyyy-MM-dd hh24:mi:ss')";
		}
		StringBuffer sqlTimeCondition = new StringBuffer();
		StringBuffer sqlTimeConditionForSM = new StringBuffer();
		if(!StringUtil.isEmpty(startTime)) {
			sqlTimeCondition.append(" and ((sam.makedate>="+startTime+" and sam.makedate<="+endTime+") or (tt.auditdate>="+startTime+" and tt.auditdate<="+endTime+") or (sam.receivedate>="+startTime+" and sam.receivedate<="+endTime+")) ");
			sqlTimeConditionForSM.append(" and ((tt.auditdate>="+startTime+" and tt.auditdate<="+endTime+") or (sam.receivedate>="+startTime+" and sam.receivedate<="+endTime+")) ");
		} else {
			sqlTimeCondition.append(" and (sam.makedate<="+endTime+" or tt.auditdate<="+endTime+" or sam.receivedate<="+endTime+") ");
			sqlTimeConditionForSM.append(" and (tt.auditdate<="+endTime+" or sam.receivedate<="+endTime+") ");
		}
		StringBuffer sqlFragment = new StringBuffer();
		sqlFragment.append("join organ outo on tt.oid=outo.id ");
		sqlFragment.append("join organ ino on tt.inoid=ino.id ");
		sqlFragment.append("and ((outo.organtype="+OrganType.Dealer.getValue()+" and outo.organmodel="+DealerType.PD.getValue()+") or (ino.organtype="+OrganType.Dealer.getValue()+" and ino.organmodel="+DealerType.PD.getValue()+" )) ");
		
		
		StringBuffer sql = new StringBuffer();
		sql.append("select sam.id,samd.productid,samd.quantity,samd.receivequantity from TAKE_TICKET tt ");
		sql.append(sqlFragment);
		sql.append("and bsort = 1 ");
		sql.append("join STOCK_ALTER_MOVE sam on sam.id = tt.billno ");
		sql.append("join STOCK_ALTER_MOVE_DETAIL samd on samd.samid = sam.id ");
		sql.append(sqlTimeCondition);
		sql.append("UNION ALL ");
		
		sql.append("select sam.id,samd.productid,samd.quantity,samd.takequantity from TAKE_TICKET tt ");
		sql.append(sqlFragment);
		sql.append("and bsort = 2 ");
		sql.append("join STOCK_MOVE sam on sam.id = tt.billno ");
		sql.append("join STOCK_MOVE_DETAIL samd on samd.smid = sam.id ");
		sql.append(sqlTimeConditionForSM);
		sql.append("UNION ALL ");
		
		sql.append("select sam.id,samd.productid,samd.quantity,samd.takequantity from TAKE_TICKET tt ");
		sql.append(sqlFragment);
		sql.append("and bsort = 7 ");
		sql.append("join ORGAN_WITHDRAW sam on sam.id = tt.billno ");
		sql.append("join ORGAN_WITHDRAW_DETAIL samd on samd.owid = sam.id ");
		sql.append(sqlTimeCondition);
		
		return EntityManager.jdbcquery(sql.toString());
	}

	public List<Map<String, String>> getBillCartonCodeToTransfer(String startTime, String endTime) throws Exception {
		if(!StringUtil.isEmpty(startTime)) {
			startTime = "to_date('" + startTime + "','yyyy-MM-dd hh24:mi:ss')";
		}
		if(!StringUtil.isEmpty(endTime)) {
			endTime = "to_date('" + endTime + "','yyyy-MM-dd hh24:mi:ss')";
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select tt.billno,tti.productid,tti.idcode,tti.batch,pj.production_date ,pj.packaging_date from TAKE_TICKET_IDCODE tti ");
		sql.append("join TAKE_TICKET tt on tti.ttid=tt.id ");
		sql.append("and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(startTime)) {
			sql.append(" and tti.makedate>="+startTime+" and tti.makedate<="+endTime);
		} else {
			sql.append(" and tti.makedate<="+endTime);
		}
		sql.append(" join organ outo on tt.oid=outo.id ");
		sql.append("join organ ino on tt.inoid=ino.id ");
		sql.append("and ((outo.organtype="+OrganType.Dealer.getValue()+" and outo.organmodel="+DealerType.PD.getValue()+") or (ino.organtype="+OrganType.Dealer.getValue()+" and ino.organmodel="+DealerType.PD.getValue()+" )) ");
		sql.append("join CARTON_CODE cc on cc.carton_code=tti.idcode ");
		sql.append("LEFT JOIN PRINT_JOB pj on cc.print_job_id = pj.print_job_id");
		sql.append(" union ");
		sql.append("select tt.billno,tti.productid,tti.idcode,tti.batch,pj.production_date ,pj.packaging_date from TAKE_TICKET_IDCODE tti ");
		sql.append("join TAKE_TICKET tt on tti.ttid=tt.id ");
		sql.append("and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(startTime)) {
			sql.append(" and tt.auditdate>="+startTime+" and tt.auditdate<="+endTime);
		} else {
			sql.append(" and tt.auditdate<="+endTime);
		}
		sql.append(" join organ outo on tt.oid=outo.id ");
		sql.append("join organ ino on tt.inoid=ino.id ");
		sql.append("and ((outo.organtype="+OrganType.Dealer.getValue()+" and outo.organmodel="+DealerType.PD.getValue()+") or (ino.organtype="+OrganType.Dealer.getValue()+" and ino.organmodel="+DealerType.PD.getValue()+" )) ");
		sql.append("join CARTON_CODE cc on cc.carton_code=tti.idcode ");
		sql.append("LEFT JOIN PRINT_JOB pj on cc.print_job_id = pj.print_job_id");
		
		return EntityManager.jdbcquery(sql.toString());
	}
	
	public List<Map<String, String>> getBillCartonCodeToTransferWithoutCartonCode(String startTime, String endTime) throws Exception {
		if(!StringUtil.isEmpty(startTime)) {
			startTime = "to_date('" + startTime + "','yyyy-MM-dd hh24:mi:ss')";
		}
		if(!StringUtil.isEmpty(endTime)) {
			endTime = "to_date('" + endTime + "','yyyy-MM-dd hh24:mi:ss')";
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select tt.billno,tti.productid,tti.idcode,tti.batch,pj.production_date ,pj.packaging_date from TAKE_TICKET_IDCODE tti ");
		sql.append("join TAKE_TICKET tt on tti.ttid=tt.id ");
		sql.append("and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(startTime)) {
			sql.append(" and tti.makedate>="+startTime+" and tti.makedate<="+endTime);
		} else {
			sql.append(" and tti.makedate<="+endTime);
		}
		sql.append(" join organ outo on tt.oid=outo.id ");
		sql.append("join organ ino on tt.inoid=ino.id ");
		sql.append("and ((outo.organtype="+OrganType.Dealer.getValue()+" and outo.organmodel="+DealerType.PD.getValue()+") or (ino.organtype="+OrganType.Dealer.getValue()+" and ino.organmodel="+DealerType.PD.getValue()+" )) ");
		sql.append("JOIN PRINT_JOB pj on pj.PRODUCT_ID = tti.productid and pj.Batch_Number=tti.batch ");
		sql.append("where not exists (select CARTON_CODE from CARTON_CODE where CARTON_CODE=tti.idcode) ");
		sql.append(" union ");
		sql.append("select tt.billno,tti.productid,tti.idcode,tti.batch,pj.production_date ,pj.packaging_date from TAKE_TICKET_IDCODE tti ");
		sql.append("join TAKE_TICKET tt on tti.ttid=tt.id ");
		sql.append("and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(startTime)) {
			sql.append(" and tt.auditdate>="+startTime+" and tt.auditdate<="+endTime);
		} else {
			sql.append(" and tt.auditdate<="+endTime);
		}
		sql.append(" join organ outo on tt.oid=outo.id ");
		sql.append("join organ ino on tt.inoid=ino.id ");
		sql.append("and ((outo.organtype="+OrganType.Dealer.getValue()+" and outo.organmodel="+DealerType.PD.getValue()+") or (ino.organtype="+OrganType.Dealer.getValue()+" and ino.organmodel="+DealerType.PD.getValue()+" )) ");
		sql.append("JOIN PRINT_JOB pj on pj.PRODUCT_ID = tti.productid and pj.Batch_Number=tti.batch ");
		sql.append("where not exists (select CARTON_CODE from CARTON_CODE where CARTON_CODE=tti.idcode) ");
		
		return EntityManager.jdbcquery(sql.toString());
	}

	public List<Map<String, String>> getBillPrimaryCodeToTransfer(String startTime, String endTime) throws Exception {
		if(!StringUtil.isEmpty(startTime)) {
			startTime = "to_date('" + startTime + "','yyyy-MM-dd hh24:mi:ss')";
		}
		if(!StringUtil.isEmpty(endTime)) {
			endTime = "to_date('" + endTime + "','yyyy-MM-dd hh24:mi:ss')";
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select tti.idcode,pc.primary_code from TAKE_TICKET_IDCODE tti ");
		sql.append("join TAKE_TICKET tt on tti.ttid=tt.id ");
		sql.append("and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(startTime)) {
			sql.append(" and tti.makedate>="+startTime+" and tti.makedate<="+endTime);
		} else {
			sql.append(" and tti.makedate<="+endTime);
		}
		sql.append(" join organ outo on tt.oid=outo.id ");
		sql.append("join organ ino on tt.inoid=ino.id ");
		sql.append("and (outo.organtype="+OrganType.Plant.getValue()+" and ino.organtype="+OrganType.Dealer.getValue()+") ");
		sql.append("join PRIMARY_CODE pc on pc.carton_code=tti.idcode");
		return EntityManager.jdbcquery(sql.toString());
	}

	public List<Map<String, String>> getInventoryToTransfer(String startTime, String endTime) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select max(o.id) organid,psa.warehouseid,psa.productid,sum(psa.stockpile) stockpile from PRODUCT_STOCKPILE_ALL psa ");
		sql.append("join WAREHOUSE w on psa.warehouseid=w.id ");
		sql.append("join organ o on o.id=w.makeorganid ");
		sql.append("and o.organtype="+OrganType.Dealer.getValue()+" and o.organmodel="+DealerType.PD.getValue());
		sql.append(" GROUP BY psa.warehouseid,psa.productid ");
		return EntityManager.jdbcquery(sql.toString());
	}

	public List<Map<String, String>> getInventoryCartonCodeToTransfer(String startTime, String endTime, String organId) throws HibernateException, SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("select TEMP.organid,TEMP.warehouseid,TEMP.productid,ic.idcode,ic.batch,pj.production_date ,pj.packaging_date from ( ");
		sql.append("select o.id organid,psa.warehouseid,psa.productid from PRODUCT_STOCKPILE_ALL psa ");
		sql.append("join WAREHOUSE w on psa.warehouseid=w.id ");
		sql.append("join organ o on o.id=w.makeorganid ");
		sql.append("and o.id='"+organId+"' ");
		sql.append(" GROUP BY o.id,psa.warehouseid,psa.productid ");
		sql.append(") TEMP ");
		sql.append("join idcode ic on ic.warehouseid=TEMP.warehouseid and ic.productid=TEMP.productid ");
		sql.append("LEFT JOIN CARTON_CODE cc on cc.carton_code=ic.idcode ");
		sql.append("LEFT JOIN PRINT_JOB pj on cc.print_job_id = pj.print_job_id ");
		return EntityManager.jdbcquery(sql.toString());
	}

	public List<Map<String, String>> getInventoryPrimaryCodeToTransfer(String organId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ic.idcode,pc.primary_code from ( ");
		sql.append("select o.id organid,psa.warehouseid,psa.productid from PRODUCT_STOCKPILE_ALL psa ");
		sql.append("join WAREHOUSE w on psa.warehouseid=w.id ");
		sql.append("join organ o on o.id=w.makeorganid ");
		sql.append("and o.id='"+organId+"'");
		sql.append(" GROUP BY o.id,psa.warehouseid,psa.productid ");
		sql.append(") TEMP ");
		sql.append("join idcode ic on ic.warehouseid=TEMP.warehouseid and ic.productid=TEMP.productid ");
		sql.append("JOIN PRIMARY_CODE pc on pc.carton_code=ic.idcode ");
		return EntityManager.jdbcquery(sql.toString());
	}

	public List<TransferLog> getAllUnTransferedLogs() {
		return EntityManager.getAllByHql("from TransferLog where status in (" + TransferFileStatus.NOT_TRANSFERED.getValue() + ","+TransferFileStatus.TRANSFER_ERROR.getValue()+")");
	}
	
	public static void main(String[] args) {
		String startTime = "2016-01-01 00:00:00";
		String endTime = "2017-12-31 23:59:59";
		if(!StringUtil.isEmpty(startTime)) {
			startTime = "to_date('" + startTime + "','yyyy-MM-dd hh24:mi:ss')";
		}
		if(!StringUtil.isEmpty(endTime)) {
			endTime = "to_date('" + endTime + "','yyyy-MM-dd hh24:mi:ss')";
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select tt.billno,tti.productid,tti.idcode,tti.batch,pj.production_date ,pj.packaging_date from TAKE_TICKET_IDCODE tti ");
		sql.append("join TAKE_TICKET tt on tti.ttid=tt.id ");
		sql.append("and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(startTime)) {
			sql.append(" and tti.makedate>="+startTime+" and tti.makedate<="+endTime);
		} else {
			sql.append(" and tti.makedate<="+endTime);
		}
		sql.append(" join organ outo on tt.oid=outo.id ");
		sql.append("join organ ino on tt.inoid=ino.id ");
		sql.append("and ((outo.organtype="+OrganType.Dealer.getValue()+" and outo.organmodel="+DealerType.PD.getValue()+") or (ino.organtype="+OrganType.Dealer.getValue()+" and ino.organmodel="+DealerType.PD.getValue()+" )) ");
		sql.append("JOIN PRINT_JOB pj on pj.PRODUCT_ID = tti.productid and pj.Batch_Number=tti.batch ");
		sql.append("where not exists (select CARTON_CODE from CARTON_CODE where CARTON_CODE=tti.idcode) ");
		sql.append(" union ");
		sql.append("select tt.billno,tti.productid,tti.idcode,tti.batch,pj.production_date ,pj.packaging_date from TAKE_TICKET_IDCODE tti ");
		sql.append("join TAKE_TICKET tt on tti.ttid=tt.id ");
		sql.append("and tt.isaudit = 1 ");
		if(!StringUtil.isEmpty(startTime)) {
			sql.append(" and tt.auditdate>="+startTime+" and tt.auditdate<="+endTime);
		} else {
			sql.append(" and tt.auditdate<="+endTime);
		}
		sql.append(" join organ outo on tt.oid=outo.id ");
		sql.append("join organ ino on tt.inoid=ino.id ");
		sql.append("and ((outo.organtype="+OrganType.Dealer.getValue()+" and outo.organmodel="+DealerType.PD.getValue()+") or (ino.organtype="+OrganType.Dealer.getValue()+" and ino.organmodel="+DealerType.PD.getValue()+" )) ");
		sql.append("JOIN PRINT_JOB pj on pj.PRODUCT_ID = tti.productid and pj.Batch_Number=tti.batch ");
		sql.append("where not exists (select CARTON_CODE from CARTON_CODE where CARTON_CODE=tti.idcode) ");
		
		
		System.out.println(sql.toString().toLowerCase());
	}

	public List<Map<String, String>> getInventoryDetailToTransfer() throws Exception {
		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT O.ID ORGANID,O.ORGANNAME, W.ID WAREHOUSEID ,W.WAREHOUSENAME,PS.PRODUCTID,P.PRODUCTNAME,P.SPECMODE,PS.BATCH ,PS.STOCKPILE,T.PRODUCTIONDATE,T.EXPIRYDATE,p.boxquantity,ps.countunit FROM PRODUCT_STOCKPILE PS ");
		sql.append("SELECT O.OECODE ORGANID,O.ORGANNAME, W.NCCODE WAREHOUSEID ,W.WAREHOUSENAME,P.MCODE,PS.PRODUCTID,P.PRODUCTNAME,P.SPECMODE,PS.BATCH ,PS.STOCKPILE,T.PRODUCTIONDATE,T.EXPIRYDATE,p.boxquantity,ps.countunit FROM PRODUCT_STOCKPILE PS ");
		sql.append("JOIN WAREHOUSE W ON W.ID=PS.WAREHOUSEID ");
		sql.append("JOIN ORGAN O ON O.ID = W.MAKEORGANID ");
		sql.append("and o.organtype="+OrganType.Dealer.getValue()+" and o.organmodel="+DealerType.PD.getValue()+" AND o.ISREPEAL=0 and PS.STOCKPILE <> 0");
		sql.append(" JOIN PRODUCT P ON P.ID=PS.PRODUCTID AND P.USEFLAG = 1 ");
		sql.append("LEFT JOIN ( ");
		sql.append("SELECT PRODUCT_ID,BATCH_NUMBER,MAX(PRODUCTION_DATE) PRODUCTIONDATE,MAX(EXPIRY_DATE) EXPIRYDATE FROM PRINT_JOB GROUP BY PRODUCT_ID,BATCH_NUMBER ");
		sql.append(") T ON T.PRODUCT_ID=P.ID AND T.BATCH_NUMBER=PS.BATCH ");
		return EntityManager.jdbcquery(sql.toString());
	}
	
	
	public List<Map<String, String>> getCSSIToTransfer(String startTime, String endTime) throws Exception {
		if(!StringUtil.isEmpty(startTime)) {
			startTime = "to_date('" + startTime + "','yyyy-MM-dd hh24:mi:ss')";
		}
		if(!StringUtil.isEmpty(endTime)) {
			endTime = "to_date('" + endTime + "','yyyy-MM-dd hh24:mi:ss')";
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select tt.BILLNO saleInfoId, to_char(tt.AUDITDATE,'yyyy') year,to_char(tt.AUDITDATE,'mm') month,tt.OID tdCode,TD.ORGANNAME tdName,TD.PROVINCE tdProvinceCode, TD.CITY tdCityCode, TD.AREAS tdAreaCode,");
		sql.append("to_char(TT.AUDITDATE,'yyyy/mm/dd') saleDate, to_char(TT.AUDITDATE,'yyyy/mm/dd hh24:mi:ss') tdInsertDate,sam.bsort saleTypeName, tti.idcode productBarCode,");
		sql.append("w.id tdStoreroomCode,w.warehousename tdStoreroomName, bkd.organmodel rgTypeName, bkd.id rgCode,bkd.organname rgName,bkd.PROVINCE rgProvinceCode, bkd.CITY rgCityCode, bkd.AREAS rgAreaCode,");
		sql.append("to_char(TT.AUDITDATE,'yyyy/mm/dd hh24:mi:ss') rgInsertDate,p.mcode productCode,p.productName productName,p.matericalchdes productNameAll, p.specmode productFormat,to_char(tti.packquantity*p.boxquantity, 'fm999999990.99999') saleNumKL,to_char(SYSDATE,'yyyy/mm/dd hh24:mi:ss') insertDate ");
		sql.append("from TAKE_TICKET tt ");
		sql.append("join ORGAN td on TT.OID=TD.id and TD.ORGANTYPE="+OrganType.Dealer.getValue()+" and TD.ORGANMODEL="+DealerType.PD.getValue()+" ");
		sql.append("and tt.isaudit=1 ");
		sql.append("join TAKE_TICKET_IDCODE tti on tt.id=tti.ttid ");
		if(!StringUtil.isEmpty(startTime)) {
			sql.append(" and ((tt.auditdate>="+startTime+" and tt.auditdate<="+endTime+") or (tti.makedate>="+startTime+" and tti.makedate<="+endTime+")) ");
		} else {
			sql.append(" and (tt.auditdate<="+endTime+" or tti.makedate<="+endTime+" )");
		}
		sql.append("join ORGAN bkd on TT.INOID=bkd.id ");
		sql.append("join WAREHOUSE w on tt.warehouseid = w.id ");
		sql.append("join STOCK_ALTER_MOVE sam on tt.billno=sam.id ");
		sql.append("join PRODUCT p on p.id=tti.productid ");
		System.out.println(sql.toString());
		return EntityManager.jdbcquery(sql.toString());
	}
}

