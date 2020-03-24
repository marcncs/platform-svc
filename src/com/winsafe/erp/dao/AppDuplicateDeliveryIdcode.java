package com.winsafe.erp.dao;

import java.util.List;
import java.util.Map;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.StringUtil;

public class AppDuplicateDeliveryIdcode {

	public List<Map<String, String>> getDuplicateDeliveryIdcode() throws Exception {
		/*if(!StringUtil.isEmpty(startTime)) {
			startTime = "to_date('" + startTime + "','yyyy-MM-dd hh24:mi:ss')";
		}
		if(!StringUtil.isEmpty(endTime)) {
			endTime = "to_date('" + endTime + "','yyyy-MM-dd hh24:mi:ss')";
		}*/
		StringBuffer sql = new StringBuffer();
		sql.append("select tti.idcode,p.mcode,p.productname,p.specmode,tti.batch,tt.billno,tt.nccode,o.id outoid,o.organname,outw.id outwid,outw.warehousename,tt.auditdate,ino.id inoid,ino.organname inorganname,inw.id inwid,inw.warehousename inwarehousename,sam.receivedate,p.id productid from ");
		sql.append("transaction_temp_dup_delivery temp_t ");
		sql.append("join TAKE_TICKET_IDCODE tti on tti.idcode=temp_t.idcode ");
		sql.append("join TAKE_TICKET tt on tt.id=tti.ttid ");
		sql.append("join organ o on TT.OID=o.ID and TT.BSORT=1 and o.ORGANTYPE =temp_t.ORGANTYPE and TT.ISAUDIT=1 ");
		sql.append("join product p on p.id=tti.productid ");
		sql.append("join organ ino on ino.id=tt.inoid ");
		sql.append("join WAREHOUSE outw on outw.id=tt.warehouseid ");
		sql.append("join STOCK_ALTER_MOVE sam on sam.id=tt.billno ");
		sql.append("left join WAREHOUSE inw on inw.id=tt.inwarehouseid ");
		sql.append("order by tti.idcode,o.organtype ");
		return EntityManager.jdbcquery(sql.toString());
	}

	public void addDuplicateDeliveryIdcode() throws Exception {
		/*List<String> sqls = new ArrayList<>();
		for(Map<String, String> map : dataList) {
			StringBuffer sb = new StringBuffer();
			sb.append("INSERT INTO DUPLICATE_DELIVERY_IDCODE (ID, IDCODE, PRODUCTID, BATCH, BILLNO, NCCODE, OUTOID, OUTWID, OUTDATE, INOID, INWID, RECEIVEDATE) ");
			sb.append("select DUPLICATE_DELIVERY_IDCODE_SEQ.nextval,");
			sb.append("'").append(map.get("idcode")).append("',");
			sb.append("'").append(map.get("productid")).append("',");
			sb.append("'").append(map.get("batch")).append("',");
			sb.append("'").append(map.get("billno")).append("',");
			sb.append("'").append(map.get("nccode")).append("',");
			sb.append("'").append(map.get("outoid")).append("',");
			sb.append("'").append(map.get("outwid")).append("',");
			sb.append("to_date('").append(map.get("auditdate")).append("','yyyy-MM-dd hh24:mi:ss'),");
			sb.append("'").append(map.get("inoid")).append("',");
			sb.append("'").append(map.get("inwid")).append("',");
			if(!StringUtil.isEmpty(map.get("receivedate"))) {
				sb.append("to_date('").append(map.get("receivedate")).append("','yyyy-MM-dd hh24:mi:ss') ");
			} else {
				sb.append("NULL ");
			}
			sb.append("from dual");
			sqls.add(sb.toString());
			if(sqls.size() == Constants.DB_BULK_SIZE){
				EntityManager.executeBatch(sqls);
				sqls.clear();
			}
		}
		if(sqls.size() > 0) {
			EntityManager.executeBatch(sqls);
		}*/
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO DUPLICATE_DELIVERY_IDCODE (ID, IDCODE, PRODUCTID, BATCH, BILLNO, NCCODE, OUTOID, OUTWID, OUTDATE, INOID, INWID, ISRECEIVE, RECEIVEDATE) ");
		sql.append("select DUPLICATE_DELIVERY_IDCODE_SEQ.nextval, tti.idcode,tti.productid ,tti.batch,tt.billno,tt.nccode,tt.oid,tt.warehouseid ,tt.auditdate,tt.inoid,tt.inwarehouseid,sam.iscomplete,sam.receivedate from transaction_temp_dup_delivery temp_t ");
		sql.append("join TAKE_TICKET_IDCODE tti on tti.idcode=temp_t.idcode ");
		sql.append("join TAKE_TICKET tt on tt.id=tti.ttid ");
		sql.append("join organ o on TT.OID=o.ID and TT.BSORT=1 and o.ORGANTYPE =temp_t.ORGANTYPE and TT.ISAUDIT=1 ");
		sql.append("join STOCK_ALTER_MOVE sam on sam.id=tt.billno ");
		sql.append("where not EXISTS ( ");
		sql.append("select ID from DUPLICATE_DELIVERY_IDCODE where IDCODE=temp_t.IDCODE and BILLNO=tt.billno) ");
		EntityManager.executeUpdate(sql.toString());
	}

	public void getDeliveryIdcode(String startTime, String endTime) throws Exception {
		if(!StringUtil.isEmpty(startTime)) {
			startTime = "to_date('" + startTime + "','yyyy-MM-dd hh24:mi:ss')";
		}
		if(!StringUtil.isEmpty(endTime)) {
			endTime = "to_date('" + endTime + "','yyyy-MM-dd hh24:mi:ss')";
		}
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO transaction_temp_dup_delivery(IDCODE, ORGANTYPE) ");
		sql.append("select tti.IDCODE,o.ORGANTYPE from ( ");
		sql.append("select TTi.idcode from TAKE_TICKET tt ");
		sql.append("join organ o on TT.OID=o.ID and TT.BSORT=1 and (o.ORGANTYPE = 1 or (o.ORGANTYPE = 2 and o.ORGANMODEL=1)) and TT.ISAUDIT=1 ");
		sql.append("and TT.AUDITDATE > ").append(startTime).append(" and TT.AUDITDATE <= ").append(endTime).append("  ");
		sql.append("join TAKE_TICKET_IDCODE tti on tt.id=tti.ttid ");
		sql.append("GROUP BY TTi.idcode ");
		sql.append("UNION ");
		sql.append("select TTi.idcode from TAKE_TICKET_IDCODE tti ");
		sql.append("join TAKE_TICKET tt on tt.id=tti.ttid ");
		sql.append("join organ o on TT.OID=o.ID and TT.BSORT=1 and (o.ORGANTYPE = 1 or (o.ORGANTYPE = 2 and o.ORGANMODEL=1)) and TT.ISAUDIT=1 ");
		sql.append("and tti.makedate > ").append(startTime).append(" and tti.makedate < ").append(endTime).append("  ");
		sql.append("and tti.makedate > TT.AUDITDATE ");
		sql.append("GROUP BY TTi.idcode ");
		sql.append(") temp  ");
		sql.append("join TAKE_TICKET_IDCODE tti on tti.idcode=temp.idcode ");
		sql.append("join TAKE_TICKET tt on tt.id=tti.ttid ");
		sql.append("join organ o on TT.OID=o.ID and TT.BSORT=1 and (o.ORGANTYPE = 1 or (o.ORGANTYPE = 2 and o.ORGANMODEL=1)) and TT.ISAUDIT=1 ");
		sql.append("GROUP BY o.ORGANTYPE, tti.IDCODE ");
		sql.append("having count(tti.IDCODE) > 1 ");
		EntityManager.executeUpdate(sql.toString());
	}

	public void delReturnIdcode() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from transaction_temp_dup_delivery where idcode in (");
		sql.append("select temp_t.idcode from transaction_temp_dup_delivery temp_t ");
		sql.append("join TAKE_TICKET_IDCODE tti on tti.idcode=temp_t.idcode ");
		sql.append("join TAKE_TICKET tt on tt.id=tti.ttid and tt.bsort=7 ");
		sql.append("join organ o on o.id=tt.inoid and o.organtype=2 and o.organmodel=1) ");
		EntityManager.executeUpdate(sql.toString());
	}

	public void updReceiveDate() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update DUPLICATE_DELIVERY_IDCODE ddi set (ddi.isreceive,ddi.receivedate,ddi.inwid) ");
		sql.append("=(select iscomplete,receivedate,inwarehouseid from STOCK_ALTER_MOVE where id=ddi.billno) ");
		sql.append("where ddi.isreceive=0 ");
		EntityManager.executeUpdate(sql.toString());
	}
	
}

