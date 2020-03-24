package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppTakeBill {

	public List getTakeBill(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = " from TakeBill "+ pWhereClause + " order by makedate desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	public List getTakeBill(HttpServletRequest request, int pagesize, Integer userId ,String whereClause,String isAudit,String beginDate) throws Exception {
//		StringBuffer hql = new StringBuffer();
//		hql.append(" select tb  from  TakeBill tb, TakeTicket tt where ");
//		hql.append("\n    tb.id = tt.billno  and tb.isaudit=0 and tt.isaudit = 0");
//		hql.append("\n and ( tb.id not like 'OW%' ");
//		hql.append("\n  and (");
//		hql.append("\n  tb.inwarehouseid in (select wv.wid from  WarehouseVisit as wv where wv.userid="+userId+") ");
//		hql.append("\n   or tb.inwarehouseid in (select ruw.warehouseId from RuleUserWh ruw where ruw.userId =" + userId + " and activeFlag = 1) ");
//		hql.append("\n   )  ");
//		hql.append("\n  and tt.warehouseid in (select wv.wid from  WarehouseVisit as wv where wv.userid="+userId+")");
//		hql.append("\n  ) or ( ");
//		hql.append("\n  ( tb.id like 'OW%' and  ");
//		hql.append("\n  tb.inwarehouseid in (select wv.wid from  WarehouseVisit as wv where wv.userid="+userId+") ");
//		hql.append("\n   or tb.inwarehouseid in (select ruw.warehouseId from RuleUserWh ruw where ruw.userId =" + userId + " and activeFlag = 1) ");
//		hql.append("\n   )  ");
//		hql.append("\n  and tt.warehouseid in (select ruw.warehouseId from RuleUserWh ruw where ruw.userId ="+userId+")");
//		hql.append("\n   )");
//		hql.append("\n   ");
//		hql.append("\n   ");
//		hql.append("\n   ");
//		hql.append("\n   ");
//		hql.append("\n   ");
		
		StringBuffer sql = new StringBuffer();
		sql.append("\n     select  tb.id from take_bill tb inner join take_ticket tt on  tb.id = tt.billNo  ");
		sql.append("\n  and tb.IsAudit = " + isAudit + " and tt.IsAudit = " + isAudit + " and tb.id not like 'OW%' and tt.makedate >=  '" + beginDate + "'");
		sql.append("\n   where  ");
		sql.append("\n   ( tb.inwarehouseid in ( select ruw.warehouse_id from rule_user_wh ruw where  ");
		sql.append("\n  ruw.user_id=" + userId + " and ruw.activeFlag = 1 )  ");
		sql.append("\n   or tb.inwarehouseid in (select wid from warehouse_visit wv where  ");
		sql.append("\n  wv.userid=" + userId + " ))  ");
		sql.append("\n    and tt.warehouseid in ( select ruw.warehouse_id from rule_user_wh ruw where   ");
		sql.append("\n  ruw.user_id=" + userId + "  and ruw.activeFlag = 1 )  ");
		sql.append("\n  union   ");
		sql.append("\n   select  tb.id from take_bill tb inner join take_ticket tt on  tb.id = tt.billNo  ");
		sql.append("\n  and tb.IsAudit = " + isAudit + " and tt.IsAudit = " + isAudit + " and tb.id like 'OW%'  and tt.makedate >=  ' " + beginDate + "'");
		sql.append("\n   where   ");
		sql.append("\n  (tb.inwarehouseid in ( select ruw.warehouse_id from rule_user_wh ruw where  ");
		sql.append("\n  ruw.user_id=" + userId + " and ruw.activeFlag = 1 )  ");
		sql.append("\n   or tb.inwarehouseid in (select wid from warehouse_visit wv where  ");
		sql.append("\n  wv.userid=" + userId + ") )  ");
		sql.append("\n   and tt.warehouseid in ( select wid from warehouse_visit wv where  ");
		sql.append("\n wv.userid=" + userId + " ) ");
		
	    List<String> idList = PageQuery.getSqlList(sql.toString());
	    String idsWhere = "";
	    if(idList != null && idList.size()>0){
	    	for(String id : idList){
	    		idsWhere += ",'" + id + "'";
	    	}
	    }
	    if(idsWhere.startsWith(",")){
	    	idsWhere = idsWhere.substring(1);
	    }
	    if(StringUtil.isEmpty(idsWhere)){
	    	idsWhere = "''";
	    }
		String hql = " from TakeBill "+ whereClause + " and id in (" + idsWhere + ") order by makedate desc ";
		return PageQuery.hbmQuery(request, hql.toString(), pagesize);
	}
	public List getTakeBillSql(HttpServletRequest request, int pagesize, Integer userId ,String whereClause,String isAudit,String beginDate) throws Exception {

		List<TakeBill> resultList = new ArrayList<TakeBill>();
		whereClause = whereClause.replace(" id", "tb.id");
		whereClause = whereClause.replace("oname", "tb.oname");
		whereClause = whereClause.replace("bsort", "tb.bsort");
		whereClause = whereClause.replace("makeid", "tb.makeid");
		whereClause = whereClause.replace("isblankout", "tb.isblankout");
		whereClause = whereClause.replace("isaudit", "tb.isaudit");
		whereClause = whereClause.replace("makeorganid", "tb.makeorganid");
		whereClause = whereClause.replace("tel", "tb.tel");
		whereClause = whereClause.replace("makedate", "tb.makedate");
		whereClause = whereClause.replace("TbBsort", "tb.TbBsort");
		whereClause = whereClause.replace("where", " and ");
		String cols = "tb.id,tb.bsort,tb.oname,tb.rlinkman,tb.tel,tb.makeorganid,tb.equiporganid,tb.makeid,tb.inwarehouseid,tb.senddate,tb.isaudit,tb.isblankout,tb.isread,tb.makeDate makeDate,tb.TbBsort";
		
		StringBuffer sql = new StringBuffer();
//		sql.append("\n select t.* from ( ");
		sql.append("\n     select  " +
				cols +
				" from take_bill tb inner join take_ticket tt on  tb.id = tt.billNo  ");
		sql.append("\n   and tb.id not like 'OW%' ");
		sql.append("\n   where  ");
		sql.append("\n   ( tb.inwarehouseid in ( select ruw.warehouse_id from rule_user_wh ruw where  ");
		sql.append("\n  ruw.user_id=" + userId + " and ruw.activeFlag = 1 )  ");
		sql.append("\n   or tb.inwarehouseid in (select wid from warehouse_visit wv where  ");
		sql.append("\n  wv.userid=" + userId + " ))  ");
		sql.append("\n    and tt.warehouseid in ( select ruw.warehouse_id from rule_user_wh ruw where   ");
		sql.append("\n  ruw.user_id=" + userId + "  and ruw.activeFlag = 1 )  ");
		sql.append(whereClause);
		sql.append("\n  union   ");
		sql.append("\n   select  " +
				cols +
				" from take_bill tb inner join take_ticket tt on  tb.id = tt.billNo  ");
		sql.append("\n   and tb.id like 'OW%'  ");
		sql.append("\n   where   ");
		sql.append("\n  (tb.inwarehouseid in ( select ruw.warehouse_id from rule_user_wh ruw where  ");
		sql.append("\n  ruw.user_id=" + userId + " and ruw.activeFlag = 1 )  ");
		sql.append("\n   or tb.inwarehouseid in (select wid from warehouse_visit wv where  ");
		sql.append("\n  wv.userid=" + userId + ") )  ");
		sql.append("\n   and tt.warehouseid in ( select wid from warehouse_visit wv where  ");
		sql.append("\n wv.userid=" + userId + " ) ");
		sql.append(whereClause);
//		sql.append(" ) t ");
		
		List<Map> list =  PageQuery.jdbcSqlserverQuery(request, "makeDate desc", sql.toString(), 10);
		
		if(list != null && list.size()>0){
			for(Map tbMap : list){
				TakeBill tb = new TakeBill();
				tb.setBsort(tbMap.get("bsort") != null ? Integer.valueOf((String)tbMap.get("bsort")) : 0);
				tb.setTbBsort(tbMap.get("tbbsort") != null ? Integer.valueOf((String)tbMap.get("tbbsort")) : 0);
				if(tbMap.get("senddate") == null){
					tb.setSenddate(null);
				}else {
					tb.setSenddate(new java.sql.Date(Dateutil.formatDatetime((String)tbMap.get("senddate")).getTime()));
				}
				tb.setRlinkman((String)tbMap.get("rlinkman"));
				tb.setMakeorganid((String)tbMap.get("makeorganid"));
				tb.setOname((String)tbMap.get("oname"));
				tb.setIsread(tbMap.get("isread") != null ? Integer.valueOf((String)tbMap.get("isread")) : 0);
				tb.setTel((String)tbMap.get("tel"));
				tb.setId((String)tbMap.get("id"));
				tb.setEquiporganid((String)tbMap.get("equiporganid"));
				tb.setMakeid(tbMap.get("makeid") != null ? Integer.valueOf((String)tbMap.get("makeid")) : 0);
				tb.setIsaudit(tbMap.get("isaudit") != null ? Integer.valueOf((String)tbMap.get("isaudit")) : 0);
				tb.setIsblankout(tbMap.get("isblankout") != null ? Integer.valueOf((String)tbMap.get("isblankout")) : 0);
				tb.setInwarehouseid((String)tbMap.get("inwarehouseid"));
				resultList.add(tb);
			}
		}
		return resultList;
	}
	public List<TakeBill> getTakeBill(String pWhereClause) throws Exception {
		String hql = " from TakeBill "+ pWhereClause + " order by makedate desc ";
		return EntityManager.getAllByHql(hql);
	}
	public void updTakeBill(TakeBill a) throws Exception {
		EntityManager.update(a);		
	}

	public void addTakeBill(TakeBill a) throws Exception {		
		EntityManager.save(a);		
	}

	
	
	
	public TakeBill getTakeBillByID(String id) throws Exception {
		String sql = " from TakeBill where id='" + id + "'";
		return(TakeBill) EntityManager.find(sql);
	}
	
	public void delTakeBill(String id)throws Exception{		
		String sql="delete from Take_Bill where id='"+id+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	
	public void updIsAudit(String id, int userid,Integer audit) throws Exception {		
		String sql = "update Take_Bill set isaudit="+audit+",auditid=" + userid
				+ ",auditdate=sysdate where id='" + id + "'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void updIsAuditByTime(String id, Integer userid, int i, String datetime) throws  Exception {
		String sql = "update Take_Bill set isaudit="+i+",auditid=" + userid
		+ ",auditdate='" + datetime
		+ "' where id='" + id + "'";
         EntityManager.updateOrdelete(sql);	
		
	} 
	
	public void updIsRead(String id, int userid) throws Exception {		
		String sql = "update Take_Bill set isread=1 where inwarehouseid in (select wid " +
				"from warehouse_visit where userid=" + userid
				+ ") and id='" + id + "'";
		EntityManager.updateOrdelete(sql);		
	}
	
	
	public void blankout(String id, int userid, Integer blankout, String blankoutreason) throws Exception {		
		String sql = "update Take_Bill set isblankout="+blankout+",blankoutid=" + userid
				+ ",blankoutdate='" + DateUtil.getCurrentDateTime()
				+ "',blankoutreason='"+blankoutreason+"' where id='" + id + "'";
		EntityManager.updateOrdelete(sql);		
	}
	
	
	public void updTakeStatus(int bsort, String billid, int takestatus) throws Exception {
		String tablename = Constants.TT_MAIN_TABLE[bsort];
		String sql = "update "+tablename+" set takestatus="+takestatus+" where id='" + billid + "'";
		EntityManager.updateOrdelete(sql);
	}
	
}


