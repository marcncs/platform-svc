package com.winsafe.drp.dao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.exception.IdcodeException;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.drp.server.CodeRuleService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.JProperties;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Encrypt;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppTakeTicket {
	public String getBillNoByIdcode( String idcode) throws Exception{
		String sql = "select tt from TakeTicket tt,TakeTicketIdcode tti where tt.id=tti.ttid and '"+idcode+"' between tti.startno and tti.endno order by tti.makedate desc";
		TakeTicket tt= (TakeTicket)EntityManager.find(sql);
		if(tt==null){
			return "";
		}
		return tt.getBillno();
	}

	public TakeTicket getTTByIdcode( String idcode) throws Exception{
		String sql = "select tt from TakeTicket tt,TakeTicketIdcode tti where tt.id=tti.ttid and tti.idcode = '"
			+idcode+"' order by tti.makedate desc";
		return (TakeTicket)EntityManager.find(sql);
		
	}
	
	public List getTakeTicket(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = "from TakeTicket  " + pWhereClause+ " order by makedate desc";
		return PageQuery.hbmQuery(request, hql,  pagesize);
	}
	
	public List<TakeTicket> getTakeTicketByRule(HttpServletRequest request, int pagesize, String pWhereClause ,UsersBean users)  throws Exception {
		return getTakeTicketByRule(request, pagesize, pWhereClause , users, null); 
	}
	
	public List<TakeTicket> getTakeTicketByRule(HttpServletRequest request, int pagesize, String pWhereClause ,UsersBean users, Map<String, Object> param)  throws Exception {
		Map<String, Object> newParam = new LinkedHashMap<>();
		String billCondition = " and (TT.ISAUDIT=1 or (TT.ISAUDIT=0 and TT.MAKEDATE >= to_date('2017-01-01','yyyy-MM-dd hh24:mi:ss')) ) ";
		Properties pro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
		if("0".equals(pro.get("hideBill"))) {
			billCondition = "";
		}
		//权限条件
		String Condition = " join organ outo on OUTO.ID=TT.OID " +
				" join organ ino on ino.id = tt.inoid " +
				" and ("+DbUtil.getWhereCondition(users, "outo") + " or "+DbUtil.getWhereCondition(users, "ino")+") ";
		//查询sql语句
		StringBuffer sql = new StringBuffer();
		//tommy add 
		//sql.append(" select * from ( ");
		
		
		sql.append(" select tt.* from take_ticket tt ");
		if(!DbUtil.isDealer(users)) {
			sql.append(Condition);
		}
		sql.append(" \n where tt.bsort = 1  "); //1代表渠道订购单
		sql.append(" \n " + pWhereClause );
		setParam(newParam, param);
		if(DbUtil.isDealer(users)) {
			sql.append(" \n and tt.warehouseid in ( select ruw.warehouse_id from rule_user_wh ruw where ruw.user_id=" + users.getUserid() + ") ");
			sql.append(" \n and tt.inwarehouseid in ( select wid from warehouse_visit wv where  wv.userid=" + users.getUserid() + ") ");
			sql.append(" \n "+billCondition);
			
			sql.append(" \n union ");
			
			sql.append(" select * from take_ticket tt where 1=1 ");
			sql.append(" \n and tt.bsort = 1  "); //1代表渠道订购单
			sql.append(" \n " + pWhereClause );
			setParam(newParam, param);
			sql.append(" \n and tt.warehouseid in ( select ruw.warehouse_id from rule_user_wh ruw where ruw.user_id=" + users.getUserid() + ") ");
			sql.append(" \n and tt.inwarehouseid is null ");
		}
		sql.append(" \n "+billCondition);
		sql.append(" \n union ");
		
		sql.append(" select tt.* from take_ticket tt ");
		if(!DbUtil.isDealer(users)) {
			sql.append(Condition);
		}
		sql.append(" \n where tt.bsort = 2  "); //2代表转仓
		sql.append(" \n " + pWhereClause );
		setParam(newParam, param);
		if(DbUtil.isDealer(users)) {
			sql.append(" \n and tt.warehouseid in ( select ruw.warehouse_id from rule_user_wh ruw where ruw.user_id=" + users.getUserid() + ") ");
		}
		sql.append(" \n "+billCondition);
		sql.append(" \n union ");
		
		sql.append(" select tt.* from take_ticket tt ");
		if(!DbUtil.isDealer(users)) {
			sql.append(Condition);
		}
		sql.append(" \n where tt.bsort = 7 and tt.billno like 'OW%' "); //7代表渠道退货单
		sql.append(" \n " + pWhereClause );
		setParam(newParam, param);
		if(DbUtil.isDealer(users)) {
			sql.append(" \n and tt.inwarehouseid in ( select ruw.warehouse_id from rule_user_wh ruw where ruw.user_id=" + users.getUserid() + ") ");
			//sql.append(" \n and tt.warehouseid in ( select wid from warehouse_visit wv where  wv.userid=" + userid + ") ");
		}
		sql.append(" \n "+billCondition);
		sql.append(" \n union ");
		
		sql.append(" select tt.* from take_ticket tt ");
		if(!DbUtil.isDealer(users)) {
			sql.append(Condition);
		}
		sql.append(" \n where tt.bsort = 7 and tt.billno like 'PW%'  "); //渠道工厂退回
		sql.append(" \n " + pWhereClause );
		setParam(newParam, param);
		if(DbUtil.isDealer(users)) {
			sql.append(" \n and tt.warehouseid in ( select ruw.warehouse_id from rule_user_wh ruw where ruw.user_id=" + users.getUserid() + ") ");
			//sql.append(" \n and tt.warehouseid in ( select wid from warehouse_visit wv where  wv.userid=" + userid + ") ");
			sql.append(" \n and  tt.inwarehouseid in (select id from Warehouse  w where w.makeorganid in (select id from Organ o where  o.isrepeal=0 and  o.organType = 1 )) ");
		}
		sql.append(" \n "+billCondition);
		//tommy add 
		//sql.append(" ) order by makeDate desc ");
		
		List<Map> list =  new ArrayList<Map>();
		if(pagesize == 0){
			if(param != null) {
				list =  EntityManager.jdbcquery(sql.toString(), newParam);
			} else {
				list =  EntityManager.jdbcquery(sql.toString());
			}
			
		}else {
			if(param != null) {
				list =  PageQuery.jdbcSqlserverQuery(request, "makeDate desc", sql.toString(), pagesize, newParam);
			} else {
				list =  PageQuery.jdbcSqlserverQuery(request, "makeDate desc", sql.toString(), pagesize);
			}
			
		}
		
		List<TakeTicket> resultList = new ArrayList<TakeTicket>();
		if(list != null && list.size()>0){
			for(Map ttMap : list){
				TakeTicket tt = new TakeTicket();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(ttMap, tt);
				if(!StringUtil.isEmpty(tt.getTel())) {
					tt.setTel(Encrypt.getSecret(tt.getTel(), 2));
				}
				resultList.add(tt);
			}
		}
		return resultList;
	}
	
	
	private void setParam(Map<String, Object> newParam, Map<String, Object> param) {
		if(param == null) {
			return;
		}
		for(Map.Entry<String, Object> entry : param.entrySet()) {
			newParam.put(UUID.randomUUID().toString(), entry.getValue());
		}
	}

	public List<TakeTicket> getTakeTicketByRuleForRI(HttpServletRequest request, int pagesize, String pWhereClause ,Integer userid) throws Exception {
		//查询sql语句
		StringBuffer sql = new StringBuffer();
		//tommy add 
		//sql.append(" select * from ( ");
		
		
		sql.append(" select * from take_ticket tt where 1=1 ");
		sql.append(" \n and tt.bsort = 1  "); //1代表渠道订购单
		sql.append(" \n " + pWhereClause );
		sql.append(" \n and tt.oid in ( select visitorgan from User_Visit where  userid=" + userid + ") ");
		
		sql.append(" \n union ");
		
		sql.append(" select * from take_ticket tt where 1=1 ");
		sql.append(" \n and tt.bsort = 1  "); //1代表渠道订购单
		sql.append(" \n " + pWhereClause );
		sql.append(" \n and tt.oid in ( select visitorgan from Organ_Visit  where userid=" + userid + ") ");
		
		
		sql.append(" \n union ");
		
		sql.append(" \n select * from take_ticket tt  where 1=1 ");
		sql.append(" \n and tt.bsort = 2  "); //2代表转仓
		sql.append(" \n " + pWhereClause );
		sql.append(" \n and tt.warehouseid in ( select ruw.warehouse_id from rule_user_wh ruw where ruw.user_id=" + userid + ") ");
		
		sql.append(" \n union ");
		
		sql.append(" \n select * from take_ticket tt  where 1=1 ");
		sql.append(" \n and tt.bsort = 7 and tt.billno like 'OW%' "); //7代表渠道退货单
		sql.append(" \n " + pWhereClause );
		sql.append(" \n and tt.inwarehouseid in ( select ruw.warehouse_id from rule_user_wh ruw where ruw.user_id=" + userid + ") ");
		sql.append(" \n and tt.warehouseid in ( select wid from warehouse_visit wv where  wv.userid=" + userid + ") ");
		
		sql.append(" \n union ");
		
		sql.append(" \n select * from take_ticket tt  where 1=1 ");
		sql.append(" \n and tt.bsort = 7 and tt.billno like 'PW%'  "); //渠道工厂退回
		sql.append(" \n " + pWhereClause );
		sql.append(" \n and tt.warehouseid in ( select ruw.warehouse_id from rule_user_wh ruw where ruw.user_id=" + userid + ") ");
		//sql.append(" \n and tt.warehouseid in ( select wid from warehouse_visit wv where  wv.userid=" + userid + ") ");
		sql.append(" \n and  tt.inwarehouseid in (select id from Warehouse  w where w.makeorganid in (select id from Organ o where  o.isrepeal=0 and  o.organType = 1 )) ");
		
		//tommy add 
		//sql.append(" ) order by makeDate desc ");
		
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pagesize == 0){
			list =  EntityManager.jdbcquery(sql.toString());
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request, "makeDate desc", sql.toString(), pagesize);
		}
		
		List<TakeTicket> resultList = new ArrayList<TakeTicket>();
		if(list != null && list.size()>0){
			for(Map ttMap : list){
				TakeTicket tt = new TakeTicket();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(ttMap, tt);
				resultList.add(tt);
			}
		}
		return resultList;
	}
	
	public void addTakeTicket(TakeTicket pii) throws Exception {		
		EntityManager.save(pii);		
	}

	public void updTakeTicket(TakeTicket pii) throws Exception {		
		EntityManager.update(pii);		
	}	

	public void updTakeReadStatusById(String id) throws Exception{
		String sql="update take_bill set isRead=1 where ID='"+id+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	public List getTakeTicketByBillno(String billno) throws Exception {
		String sql = "from TakeTicket where billno='" + billno+"'";
		return  EntityManager.getAllByHql(sql);
	}
	
	public TakeTicket getTakeTicket(String billno) throws Exception {
		String sql = "from TakeTicket where billno='" + billno+"'";
		return  (TakeTicket) EntityManager.find(sql);
	}
	
	public int getNoAuditCountByBillno(String billno) throws Exception {
		String sql = "select count(*) from TakeTicket where billno='" + billno+"' and isaudit=0";
		return  EntityManager.getRecordCount(sql);
	}
	

	public TakeTicket getTakeTicketById(String id) throws Exception {
		String sql = "from TakeTicket where id='" + id+"'";
		return (TakeTicket) EntityManager.find(sql);
	}

	public TakeTicket getTakeTicketByIdIsCheck(String id) throws Exception {
		String sql = "from TakeTicket where id='" + id+"' and isChecked=0";
		return (TakeTicket) EntityManager.find(sql);
	}
	
	public void delTakeTicketByid(String id) throws Exception {		
		String sql = "delete from take_ticket where id='" + id + "'";
		EntityManager.updateOrdelete(sql);		
	}
	
	
	public void delTakeTicketByBillNo(String billno) throws Exception {		
		String sql = "delete from take_ticket where billno='" + billno + "'";
		EntityManager.updateOrdelete(sql);		
	}
	/**
	 * 更新出库状态
	 * Create Time 2014-10-17 上午09:28:58 
	 * @param bsort
	 * @param billid
	 * @param isShipment
	 * @throws Exception
	 * @author lipeng
	 * @param date 
	 */
	public void updShipment(int bsort, String billid, int isShipment,int userid, String date) throws Exception {
		String tablename = Constants.TT_MAIN_TABLE[bsort];
		// 退货无isshipment,shipmentdate
		if(bsort == 7){
			return;
		}
		String sql = "update "+tablename+" set isshipment="+isShipment+",shipmentdate=to_date('" + date + "','yyyy-MM-dd hh24:mi:ss'),shipmentid="+userid+" where id='" + billid + "'";
		EntityManager.updateOrdelete(sql);
	}
	/**
	 * 更新单据检货状态
	 * @param bsort
	 * @param billid
	 * @param takestatus
	 * @throws Exception
	 */
	public void updTakeStatus(int bsort, String billid, int takestatus) throws Exception {
		String tablename = Constants.TT_MAIN_TABLE[bsort];
		String sql = "update "+tablename+" set takestatus="+takestatus+" where id='" + billid + "'";
		EntityManager.updateOrdelete(sql);
	}
	/**
	 * 更新单据是否已关闭
	 * @param bsort
	 * @param billid
	 * @param iscomplete
	 * @param userid
	 * @throws Exception
	 */
	public void updIsComplete(int bsort, String billid, int iscomplete,int userid) throws Exception {
		String tablename = Constants.TT_MAIN_TABLE[bsort];
		String sql = "update "+tablename+" set iscomplete="+iscomplete+",receivedate=sysdate,receiveid="+ userid +" where id='" + billid + "'";
		EntityManager.updateOrdelete(sql);
	}
	
	/**
	 * 主要功能 复核taketicket
	 * @param id tt票id
	 * @param userid 操作人员id
	 * @param audit 是否复核
	 * @throws Exception TO_DATE(DateUtil.getCurrentDateTime(),'yyyy-mm-dd hh24:mi:ss'),
	 */
	public void updIsAudit(String id, int userid, int audit)
			throws Exception {		
		String sql = "update take_ticket set isaudit=" + audit + ",auditid="
				+ userid + ",auditdate=TO_DATE('" + DateUtil.getCurrentDateTime() +  "','yyyy-mm-dd hh24:mi:ss') where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	

	/**
	 * 主要功能 复核taketicket(超过实际数量的复核)
	 * @param id tt票id
	 * @param userid 操作人员id
	 * @param audit 是否复核
	 * @throws Exception
	 */
	public void updIsAuditOverQuantity(String id, int userid, int audit)
			throws Exception {		
		String sql = "update take_ticket set isaudit=" + audit + ",auditid="
				+ userid + ",auditdate='" + DateUtil.getCurrentDateTime()
				+ "' , isOverQuantity=1 where id='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	public void updIsAuditByTime(String ttid, Integer userid, int i, String datetime) throws  Exception {
		String sql = "update take_ticket set isaudit=" + i + ",auditid="
		+ userid + ",auditdate='" + datetime
		+ "' , isOverQuantity=1 where id='" + ttid + "'";
        EntityManager.updateOrdelete(sql);
	}
	public void blankout(String id, Long userid, Integer blankout, String blankoutreason) throws Exception {		
		String sql = "update take_ticket set isblankout="+blankout+",blankoutid=" + userid
				+ ",blankoutdate='" + DateUtil.getCurrentDateTime()
				+ "',blankoutreason='"+blankoutreason+"' where billno='" + id + "'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getCostTotal(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String sql = "select makeorganid as makeorganid,productid as productid,productname as productname,specmode as specmode,unitid as unitid,sum(quantity) as quantity ,sum(unitprice*quantity) as subsum,sum(cost*quantity) as cost "
				+ " from take_ticket_detail as sod ,take_ticket as so "
				+ pWhereClause
				+ " group by makeorganid,productid,productname,specmode,unitid ";
		System.out.println("---c--"+sql);
		return PageQuery.jdbcSqlserverQuery(request, "makeorganid", sql, pagesize);
	}
	
	public List getTotalSum(String pWhereClause) throws Exception {
		String sql = "select sum(sod.quantity) as allqt, sum(sod.unitprice*sod.quantity) as allsubsum, sum(sod.cost*sod.quantity) as allcost"
				+ " from take_ticket_detail as sod ,take_ticket as so "
				+ pWhereClause;
		return EntityManager.jdbcquery(sql);
	}

	
	public List getCostProductDetail(HttpServletRequest request, int pageSize,
			String pWhereClause) throws Exception {

		String sql = "select so.id,so.oid,so.oname,so.billno,so.makedate,so.makeorganid,so.equiporganid,sod.productid,sod.productname,"
				+ "sod.specmode,sod.batch,sod.unitid,sod.unitprice,sod.quantity,sod.cost "
				+ "from take_ticket so,take_ticket_detail sod " + pWhereClause;
		return PageQuery.jdbcSqlserverQuery(request, sql, pageSize);
	}
	
	public List getCostProductDetail(
			String pWhereClause) throws Exception {

		String sql = "select so.id,so.oid,so.oname,so.billno,so.makedate,so.makeorganid,so.equiporganid,sod.productid,sod.productname,"
				+ "sod.specmode,sod.batch,sod.unitid,sod.unitprice,sod.quantity,sod.cost "
				+ "from take_ticket so,take_ticket_detail sod " + pWhereClause;
		return EntityManager.jdbcquery( sql);
	}
	
	public List getTotalSubum(String whereSql) throws Exception {
		String sql = "select sum(sod.quantity) as allquantity,sum(sod.unitprice*sod.quantity) as allsubsum,sum(sod.cost*sod.quantity) as allcost from take_ticket as so ,take_ticket_detail as sod "
				+ whereSql;
		return EntityManager.jdbcquery(sql);
	}
	
	
	public double getTakeTicketTotalSum(String yearmon, String conditon) {
		String[] date = yearmon.split("-");
		String sql = "select sum(sod.unitprice*sod.quantity) as subsum from TakeTicket so , " +
				"TakeTicketDetail sod where so.id=sod.ttid and year(makedate) ='"+date[0]+"'  and month(makedate)='"+date[1]+"' " +conditon;
		return EntityManager.getdoubleSum(sql);
	}
	
	public double getTakeTicketCostSum(String yearmon, String conditon) {
		String[] date = yearmon.split("-");
		String sql = "select sum(sod.cost*sod.quantity) as cost from TakeTicket so , " +
				"TakeTicketDetail sod where so.id=sod.ttid and year(makedate) ='"+date[0]+"'  and month(makedate)='"+date[1]+"' " +conditon;
		return EntityManager.getdoubleSum(sql);
	}
	public TakeTicket getTakeTicketByIdcodes(List<TakeTicketIdcode> idcodes) {
		
		Set<String> ttids = new TreeSet<String>((new Comparator<Object>() {
				public int compare(Object a, Object b) {
					int orderA = Integer.parseInt(a.toString());
					int orderB = Integer.parseInt(a.toString());
					return orderA - orderB;
				}
			}
		));
		for (TakeTicketIdcode takeTicketIdcode : idcodes) {
			ttids.add(takeTicketIdcode.getTtid());
		}
		String ttid = "";
		for (String string : ttids) {
			ttid = string;
			break;
		}
		String sql = "from TakeTicket where id = " + ttid;
		return (TakeTicket)EntityManager.find(sql);
	}
	
	public double getTakeTicketProfitSum(String yearmon, String conditon) {
		String[] date = yearmon.split("-");
		String sql = "select sum((sod.unitprice*sod.quantity)-(sod.cost*sod.quantity)) as profitsum from TakeTicket so , " +
				"TakeTicketDetail sod where so.id=sod.ttid and year(makedate) ='"+date[0]+"'  and month(makedate)='"+date[1]+"' " +conditon;
		return EntityManager.getdoubleSum(sql);
	}
	
	public List getCostTotal(String pWhereClause) throws Exception {
		String sql = "select makeorganid,productid as productid,productname as productname,specmode as specmode,unitid as unitid,sum(quantity) as quantity ,sum(unitprice*quantity) as subsum,sum(cost*quantity) as cost "
			+ " from take_ticket_Detail as sod ,take_ticket as so "
			+ pWhereClause
			+ " group by makeorganid,productid,productname,specmode,unitid ";
		return EntityManager.jdbcquery(sql);
	}
	
	/**
	 * 主要功能：得到未上传条码的tt单据
	 * @return TT单据列表
	 */
	public List<TakeTicket> getNoCodeTT(){
	    StringBuffer hql = new StringBuffer();
	    hql.append(" from TakeTicket tt where ");
	    hql.append(" tt.id not in(select ttidcode.ttid from TakeTicketIdcode ttidcode)" );
	    List<TakeTicket> returnList = EntityManager.getAllByHql(hql.toString());
	    return returnList;
	}
	
	/**
	 * 主要功能：得到所有未复核的TT单据
	 * @return TT单据列表
	 */
	public List<TakeTicket> getNotAuditTT(Integer userid){
	    StringBuffer hql = new StringBuffer();
	    hql.append(" from TakeTicket tt where ");
	    hql.append(" tt.isaudit = 0 " );
	    hql.append(" and tt.inwarehouseid in (select wv.wid from  WarehouseVisit as wv where wv.userid="+userid+")");
	    List<TakeTicket> returnList = EntityManager.getAllByHql(hql.toString());
	    return returnList;
	}
	
	/**
	 * 主要功能：得到错误的条码集合
	 * @return map条码集合
	 * @throws IdcodeException
	 * @throws Exception
	 */
	public Map<String, List<TakeTicketIdcode>> getNotExistTT() throws IdcodeException, Exception{
	    Map<String, List<TakeTicketIdcode>> returnMap = new HashMap<String, List<TakeTicketIdcode>>();
	    //检索出不能匹配的所有条码
	    //TakeTicketIdcode
	    //TakeTicket
	    StringBuffer hql = new StringBuffer();
	    hql.append(" from TakeTicketIdcode idcode where ");
	    hql.append(" idcode.ttid not in(select tt.id from TakeTicket tt)" );
	    List<TakeTicketIdcode> idcodes = EntityManager.getAllByHql(hql.toString());
	    //初始化条码集合
	    initNotExistTTMap(idcodes,returnMap);
	   
	    return returnMap;
	}
	
	/**
	 * 主要功能：初始化错误条码集合
	 * @param idcodes 错误条码列表
	 * @param idcodesMap 为整理的条码集合
	 * @throws Exception 
	 * @throws IdcodeException 
	 */
	private void initNotExistTTMap(List<TakeTicketIdcode> idcodes,Map<String, List<TakeTicketIdcode>> idcodesMap) throws IdcodeException, Exception{
	    AppICode appicode = new AppICode();
	    CodeRuleService codeRuleService = new CodeRuleService();
	    for (TakeTicketIdcode idcodeObj : idcodes) {
		//判断集合中是否存在此TT号
		String ttid = idcodeObj.getTtid();
		//如果存在,添加条码
		if(idcodesMap.containsKey(ttid)){
		    idcodesMap.get(ttid).add(idcodeObj);
		//如果不存在,添加TT号和条码
		}else{
		    List<TakeTicketIdcode> list = new ArrayList<TakeTicketIdcode>();
		    list.add(idcodeObj);
		    idcodesMap.put(ttid, list);
		}
	    } 
	}
	
	public String getProductName(String pid) {
		String productName = null;
		String sql = "select productname from product where id=:id";
		Session session = HibernateUtil.currentSession();
		Query query = session.createSQLQuery(sql);
		query.setParameter("id", pid);
		productName = (String) query.uniqueResult();
		return productName;
	}
	
	/**
	 * 获取按仓库和产品获取批次和数量的信息
	 * 
	 * @param warehouseid
	 *            仓库编号
	 * @param productid
	 *            产品编号
	 * @return 批次与数量信息的集合
	 */
	public List<BatchQuantity> getBatchQuantity(String warehouseid,
			String productid) {
		List<BatchQuantity> bqs = new ArrayList<BatchQuantity>();
		String sql = new StringBuffer()
				.append(
						" select batch,sum(stockpile+prepareout) totalQuantity from product_stockpile_all ")
				.append(" where warehouseid=:warehouseid ").append(
						" and productid=:productid group by batch ").append(
						" having sum(stockpile+prepareout)>0").toString();
		Session session = HibernateUtil.currentSession();
		Query query = session.createSQLQuery(sql);
		query.setParameter("warehouseid", warehouseid);
		query.setParameter("productid", productid);
		List<Object[]> results = query.list();

		for (Object[] obj : results) {
			BatchQuantity bq = new BatchQuantity();
			bq.setBatch((String) obj[0]);
			bq.setMaxQuantity(((java.math.BigDecimal) obj[1]).doubleValue());
			bqs.add(bq);
		}

		return bqs;
	}
	
	@SuppressWarnings("unchecked")
	public List getIdcodeScanRateList(HttpServletRequest request, int pageSize,
			String pWhereClause) throws Exception {

//		String sql = "select tt,ttd from TakeTicket tt,TakeTicketDetail ttd " 
//			+ pWhereClause+" order by tt.id desc";
		
		String sql = "select ttd.id ttdid,billno,tt.id,tt.nccode,warehouseid,ttd.productid,ttd.boxnum,ttd.scatternum,ttd.remark from take_ticket tt,take_ticket_detail ttd " 
			+ pWhereClause+" order by id desc,productid";
		return PageQuery.jdbcSqlserverQuery2(request, sql, pageSize);
//		BasePage bp = new BasePage(request, pageSize);		
//		List quList = EntityManager.getAllByHql(sql,bp.getPageNo(), pageSize);
//		Map map;
//		List reList  = new ArrayList();
//		for(int i=0;i<quList.size();i++){
//			Object[] obj = (Object[])quList.get(i);
//			TakeTicket t=(TakeTicket)obj[0];
//			TakeTicketDetail td = (TakeTicketDetail)obj[1];
//			 map = new HashMap();
//			 map.put("billno", t.getBillno());
//			 map.put("id", t.getId());
//			 map.put("nccode", t.getNccode());
//			 map.put("warehouseid", t.getWarehouseid());
//			 map.put("productid", td.getProductid());
//			 map.put("boxnum", td.getBoxnum());
//			 map.put("scatternum", td.getScatternum());
//			 
//			 reList.add(map);
//		}
//		
//		return reList;
	}
	
	
	public List getIdcodeScanRateList(HttpServletRequest request, int pageSize,
			String pWhereClause, String orderSql) throws Exception {

//		String sql = "select tt,ttd from TakeTicket tt,TakeTicketDetail ttd " 
//			+ pWhereClause+" order by tt.id desc";
		
		String sql = "select ttd.id ttdid,billno,tt.id,tt.nccode,tt.warehouseid,ttd.productid,ttd.boxnum,ttd.scatternum,ttd.remark,tt.inwarehouseid from take_ticket tt,take_ticket_detail ttd " + pWhereClause + "";
		
		if(!StringUtil.isEmpty(orderSql)){
			sql+= " order by "+ orderSql;
		}else{
			sql+= " order by id desc, productid ";
		}
		
		return PageQuery.jdbcSqlserverQuery2(request, sql, pageSize);
//		BasePage bp = new BasePage(request, pageSize);		
//		List quList = EntityManager.getAllByHql(sql,bp.getPageNo(), pageSize);
//		Map map;
//		List reList  = new ArrayList();
//		for(int i=0;i<quList.size();i++){
//			Object[] obj = (Object[])quList.get(i);
//			TakeTicket t=(TakeTicket)obj[0];
//			TakeTicketDetail td = (TakeTicketDetail)obj[1];
//			 map = new HashMap();
//			 map.put("billno", t.getBillno());
//			 map.put("id", t.getId());
//			 map.put("nccode", t.getNccode());
//			 map.put("warehouseid", t.getWarehouseid());
//			 map.put("productid", td.getProductid());
//			 map.put("boxnum", td.getBoxnum());
//			 map.put("scatternum", td.getScatternum());
//			 
//			 reList.add(map);
//		}
//		
//		return reList;
	}
	
	public List getIdcodeScanRateList(HttpServletRequest request,String pWhereClause) throws Exception {
		String sql = "select ttd.id ttdid,billno,tt.id,tt.nccode,tt.warehouseid,ttd.productid,ttd.boxnum,ttd.scatternum,ttd.remark,tt.inwarehouseid from take_ticket tt,take_ticket_detail ttd " 
			+ pWhereClause+" order by id desc,productid";
		return EntityManager.jdbcquery(sql); 
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * 扫描率按产品汇总
	 */
	public List getIdcodeScanRateListByProduct(HttpServletRequest request, int pageSize,
			String pWhereClause, String orderSql) throws Exception {
		String sql = "select tt.warehouseid, ttd.productid, sum(ttd.boxnum) as boxnum, sum(ttd.scatternum) as scatternum from take_ticket tt, take_ticket_detail ttd "
			+ pWhereClause + " group by tt.warehouseid, ttd.productid";
		
		if(!StringUtil.isEmpty(orderSql)){
			sql+= " order by "+ orderSql;
		}else{
			sql+= " order by productid ";
		}
		
		return PageQuery.jdbcSqlserverQuery2(request, sql, pageSize);
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * 扫描率按产品汇总
	 */
	public List getIdcodeScanRateListByProduct(HttpServletRequest request,
			String pWhereClause) throws Exception {
		String sql = "select MAX(tt.id) as id, tt.warehouseid, ttd.productid, sum(ttd.boxnum) as boxnum, sum(ttd.scatternum) as scatternum from take_ticket tt, take_ticket_detail ttd "
			+ pWhereClause + " group by tt.warehouseid, ttd.productid";
		return EntityManager.jdbcquery(sql);
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * 扫描率按仓库汇总
	 */
	public List getIdcodeScanRateListByWarehouse(HttpServletRequest request, int pageSize,
			String pWhereClause, String orderSql) throws Exception {
		String sql = "select tt.warehouseid, sum(ttd.boxnum) as boxnum, sum(ttd.scatternum) as scatternum from take_ticket tt, take_ticket_detail ttd "
			+ pWhereClause + " group by tt.warehouseid";
		
		if(!StringUtil.isEmpty(orderSql)){
			sql+= " order by "+ orderSql;
		}else{
			sql+= " order by warehouseid ";
		}
		
		return PageQuery.jdbcSqlserverQuery2(request, sql, pageSize);
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * 扫描率按仓库汇总
	 */
	public List getIdcodeScanRateListByWarehouse(HttpServletRequest request, 
			String pWhereClause) throws Exception {
		String sql = "select tt.warehouseid, sum(ttd.boxnum) as boxnum, sum(ttd.scatternum) as scatternum from take_ticket tt, take_ticket_detail ttd "
			+ pWhereClause + " group by tt.warehouseid";
		return EntityManager.jdbcquery(sql);
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * 补货
	 */
	public List getRepairListBySAM(HttpServletRequest request, int pageSize,
			String pWhereClause, String orderSql) throws Exception {
		String sql = " select tt.warehouseid, ttb.productid, " +
				" sum(ttb.Quantity) as quantity ,sum(RealBoxNum) as boxquantity,sum(RealScatterNum) as scatterquantity " +
				" from  take_ticket_detail_batch_bit as ttb,take_ticket as tt,Warehouse_Visit as wv, " +
				" Product as p,warehouse as w,product_struct as pstr, stock_alter_move as sam "
				+ pWhereClause + " group by ttb.productid,tt.warehouseid " ;
		if(!StringUtil.isEmpty(orderSql)){
			sql+= " order by "+ orderSql;
		}else{
			sql+= " order by productid ";
		}
		return PageQuery.jdbcSqlserverQuery2(request, sql, pageSize);
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * 补货
	 */
	public List getRepairListBySAMNoPage(String pWhereClause) throws Exception {
		String sql = " select tt.warehouseid, ttb.productid, " +
				" sum(ttb.Quantity) as quantity ,sum(RealBoxNum) as boxquantity,sum(RealScatterNum) as scatterquantity " +
				" from  take_ticket_detail_batch_bit as ttb,take_ticket as tt,Warehouse_Visit as wv, " +
				" Product as p,warehouse as w,product_struct as pstr, stock_alter_move as sam "
				+ pWhereClause + " group by ttb.productid,tt.warehouseid " ;
		return EntityManager.jdbcquery(sql);
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * 补货
	 */
	public List getRepairListBySM(HttpServletRequest request, int pageSize,
			String pWhereClause) throws Exception {
		String sql = " select ttb.ttid,ttb.productid ,tt.makedate,tt.warehouseid,p.nccode,p.productname,p.specmode,p.countunit, w.WarehouseName,pstr.sortname," +
				" sum(ttb.Quantity) as quantity ,sum(BoxNum) as boxquantity,sum(ScatterNum) as scatterquantity  " +
				" from  take_ticket_detail_batch_bit as ttb,take_ticket as tt,Warehouse_Visit as wv, " +
				" Product as p,warehouse as w,product_struct as pstr, stock_move as sm "
				+ pWhereClause + " group by ttb.ttid , ttb.productid,tt.makedate,tt.warehouseid," +
				" p.productname,p.specmode,p.countunit,p.nccode, " +
				" w.WarehouseName,pstr.sortname ";
		return PageQuery.jdbcSqlserverQuery(request, "productid", sql, pageSize);
	}
	
	//按仓库，产品汇总
	public List getWaitProductStock(String wid,String pid) throws Exception {
		String sql = "select ttb.ttid,tt.makedate,sum(ttb.Quantity) as quantity ,sum(BoxNum) as boxquantity,sum(ScatterNum) as scatterquantity  " +
				" from take_ticket_detail_batch_bit as ttb,take_ticket as tt  where " 
				+ "  tt.id = ttb.ttid and tt.IsAudit=0   and tt.WarehouseID='" + wid+ "' and  ttb.ProductID='" +pid+"'" + 
				" group by ttb.ttid ,tt.makedate ";
		return PageQuery.getJdbcSQLServerAll(sql," ttid desc");
	}
	
	
	//按仓库，产品汇总
	public List getWaitProductStock(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String sql = "select ttb.ttid,ttb.productid ,tt.makedate,tt.warehouseid,p.nccode,p.productname,p.specmode,p.countunit, w.WarehouseName,pstr.sortname ,sum(ttb.Quantity) as quantity ,sum(BoxNum) as boxquantity,sum(ScatterNum) as scatterquantity " +
				" from  take_ticket_detail_batch_bit as ttb,take_ticket as tt,Warehouse_Visit as wv, Product as p,warehouse as w,product_struct as pstr " + pWhereClause
				+ "  and tt.id = ttb.ttid and tt.IsAudit=0  and w.id=tt.warehouseid and pstr.StructCode=p.PSID   and p.id=ttb.productid " +
				"group by ttb.ttid , ttb.productid,tt.makedate,tt.warehouseid,p.productname,p.specmode,p.countunit,p.nccode, w.WarehouseName,pstr.sortname  ";
		return PageQuery.jdbcSqlserverQuery(request, "warehouseid ,productid,ttid desc ", sql, pagesize);
	}
	
	//按仓库，产品汇总
	public List getWaitProductStock(HttpServletRequest request, int pagesize, String pWhereClause,String orderby) throws Exception {
		String sql = "select ttb.ttid,ttb.productid ,tt.makedate,tt.warehouseid,p.nccode,p.productname,p.specmode,p.countunit, w.WarehouseName,pstr.sortname ,sum(ttb.Quantity) as quantity ,sum(BoxNum) as boxquantity,sum(ScatterNum) as scatterquantity " +
				" from  take_ticket_detail_batch_bit as ttb,take_ticket as tt,Warehouse_Visit as wv, Product as p,warehouse as w,product_struct as pstr " + pWhereClause
				+ "  and tt.id = ttb.ttid and tt.IsAudit=0  and w.id=tt.warehouseid and pstr.StructCode=p.PSID   and p.id=ttb.productid " +
				"group by ttb.ttid , ttb.productid,tt.makedate,tt.warehouseid,p.productname,p.specmode,p.countunit,p.nccode, w.WarehouseName,pstr.sortname  ";
		if(!StringUtil.isEmpty(orderby)){
			return PageQuery.jdbcSqlserverQuery(request, orderby, sql, pagesize);
		}else{
			return PageQuery.jdbcSqlserverQuery(request, "warehouseid ,productid,ttid desc ", sql, pagesize);
		}
	}
	
	//按仓库，产品汇总
	public List getWaitProductStock(String pWhereClause) throws Exception {
		String sql = "select ttb.ttid,ttb.productid ,tt.makedate,tt.warehouseid,p.nccode,p.productname,p.specmode,p.countunit, w.WarehouseName,pstr.sortname ,sum(ttb.Quantity) as quantity ,sum(BoxNum) as boxquantity,sum(ScatterNum) as scatterquantity " +
				" from  take_ticket_detail_batch_bit as ttb,take_ticket as tt,Warehouse_Visit as wv, Product as p,warehouse as w,product_struct as pstr " + pWhereClause
				+ "  and tt.id = ttb.ttid and tt.IsAudit=0  and w.id=tt.warehouseid and pstr.StructCode=p.PSID   and p.id=ttb.productid " +
				"group by ttb.ttid , ttb.productid,tt.makedate,tt.warehouseid,p.productname,p.specmode,p.countunit,p.nccode, w.WarehouseName,pstr.sortname  "
				+ " order by warehouseid ,productid,ttid desc ";
		return EntityManager.jdbcquery(sql);
	}
	
	/**
	 * @author jason.huang
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	
	public List<TakeTicket> getTakeTicketsByIds(String ids) throws Exception {
		String sql = "from TakeTicket where id = '" + ids+"'";
		return EntityManager.getAllByHql(sql);
	}

	/**
	 * @author jason.huang
	 * @param 
	 * @return
	 * @throws Exception
	 * 判断此tt中的两个仓库号是否在用户的管辖区域内
	 */
	
	public List<TakeTicket> getTakeTicketsById(String cartonCode,int userid) throws Exception {
		List<TakeTicket> resutlList = new ArrayList<TakeTicket>();
		String sql = " select * from take_ticket where ID in (select ttid from TAKE_TICKET_IDCODE where idcode='"+cartonCode+"')  and  (WAREHOUSEID IN (select WAREHOUSE_ID from Rule_User_Wh  where USER_ID='"+userid+"') or INWAREHOUSEID IN (select WAREHOUSE_ID from Rule_User_Wh  where USER_ID='"+userid+"')) and isblankout=0 order by makedate asc ";
		List<Map> list = EntityManager.jdbcquery(sql);
		for(Map map : list){
			TakeTicket tt = new TakeTicket();
			MapUtil.mapToObject(map, tt);
			resutlList.add(tt);
		}
		
		return resutlList;
	}
	
	/**
	 * @author jason.huang
	 * @param 
	 * @return
	 * @throws Exception
	 * 判断此tt中的两个仓库号是否在用户的管辖区域内
	 */
	
	public List<TakeTicket> getTakeTicketsById(String cartonCode,String primarycode,int userid) throws Exception {
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		param.put("cartonCode", cartonCode);
		param.put("primarycode", primarycode);
		param.put("userid", userid);
		param.put("userid2", userid);
		List<TakeTicket> resutlList = new ArrayList<TakeTicket>();
		String sql = " select * from take_ticket where ID in (select ttid from TAKE_TICKET_IDCODE where idcode=?  or idcode=?)  and  (WAREHOUSEID IN (select WAREHOUSE_ID from Rule_User_Wh  where USER_ID=?) or INWAREHOUSEID IN (select WAREHOUSE_ID from Rule_User_Wh  where USER_ID=?)) and  isaudit=1 order by makedate desc ";
		List<Map> list = EntityManager.jdbcquery(sql, param);
		for(Map map : list){
			TakeTicket tt = new TakeTicket();
			MapUtil.mapToObject(map, tt);
			resutlList.add(tt);
		}
		
		return resutlList;
	}
	
	/**
	 * @author jason.huang
	 * @param 
	 * @return
	 * @throws Exception
	 * 判断此tt中的两个仓库号是否在用户的管辖区域内
	 */
	
	public List<TakeTicket> getTakeTicketsById(String cartonCode,String primarycode) throws Exception {
		List<TakeTicket> resutlList = new ArrayList<TakeTicket>();
		String sql = " select * from take_ticket where ID in (select ttid from TAKE_TICKET_IDCODE where idcode='"+cartonCode+"'  or idcode='" + primarycode + "') and  isaudit=1 order by makedate desc ";
		List<Map> list = EntityManager.jdbcquery(sql);
		for(Map map : list){
			TakeTicket tt = new TakeTicket();
			MapUtil.mapToObject(map, tt);
			resutlList.add(tt);
		}
		
		return resutlList;
	}

	
	
	/**
	 * 根据箱码或者小码找TT单并且让单据按时间降序排列
	 * @author jason.huang
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	
	public List<TakeTicket> getTakeTicketsInf(String cartoncode) throws Exception {
		String sql = "select *	from Take_Ticket where id in (select ttid from TAKE_TICKET_IDCODE where idcode='"+cartoncode+"' )  order by makedate desc  ";
		return EntityManager.jdbcquery(sql);
	}
	
	/**
	 * 根据箱码或者小码找TT单并且让单据按时间降序排列
	 * @author jason.huang
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	
	public List<TakeTicket> getTakeTicketsInf(String cartoncode,String primarycode) throws Exception {
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		param.put("cartoncode", cartoncode);
		param.put("primarycode", primarycode);
		String sql = "select *	from Take_Ticket where id in (select ttid from TAKE_TICKET_IDCODE where idcode=? or idcode=?) and isblankout=0 and isaudit=1  order by makedate desc  ";
		return EntityManager.jdbcquery(sql, param);
	}
	
	/**
	 * 根据箱码或者小码找TT单并且让单据按时间降序排列
	 * @author jason.huang
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	
	public List<TakeTicket> getTakeTicketsByCode(String cartoncode,String primarycode) throws Exception {
		List<TakeTicket> resutlList = new ArrayList<TakeTicket>();
		String sql = "select *	from Take_Ticket where id in (select ttid from TAKE_TICKET_IDCODE where idcode='"+cartoncode+"' or idcode='"+primarycode+"') and isaudit=1  order by makedate desc  ";
		List<Map> list = EntityManager.jdbcquery(sql);
		for(Map map : list){
			TakeTicket tt = new TakeTicket();
			MapUtil.mapToObject(map, tt);
			resutlList.add(tt);
		}
		return resutlList;
	}
	
	public boolean isIdcodeQtyGreatOrEqualThanProductQty(String ttid,String productId) throws Exception {
		String sql = "select count(*) from dual where (select count(id) from TAKE_TICKET_IDCODE where TTID = '"+ttid+"' and PRODUCTID = '"+productId+"') >= (select QUANTITY from TAKE_TICKET_DETAIL where TTID = '"+ttid+"' and PRODUCTID = '"+productId+"')";
		return EntityManager.getRecordCountBySql(sql) > 0;
	}

	public List<Map<String, String>> getReceiveSummary(
			HttpServletRequest request, String makeorganid) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select organId,makedate yearMonth,max(organname) organName,PRODUCTNAME, sum(QUANTITY) quantity,max(countunit) countunit from ( ");
		sql.append("select TO_CHAR(SAM.receivedate,'yyyy-MM') makedate,sam.OUTORGANID organId,SAM.OUTORGANNAME organname,SAMD.PRODUCTNAME,SAMD.RECEIVEQUANTITY*fu.xquantity*p.boxquantity QUANTITY,p.countunit from STOCK_ALTER_MOVE sam  ");
		sql.append("join STOCK_ALTER_MOVE_DETAIL samd on SAM.id =SAMD.SAMID ");
		sql.append("and SAM.RECEIVEORGANID = '"+makeorganid+"' ");
		sql.append("and SAM.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("fromDate"))) {
			sql.append("and TO_CHAR(SAM.receivedate,'yyyy-MM') >= '"+request.getParameter("fromDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("toDate"))) {
			sql.append("and TO_CHAR(SAM.receivedate,'yyyy-MM') <= '"+request.getParameter("toDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and SAM.OUTORGANID = '"+request.getParameter("organId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and SAMD.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		sql.append("JOIN PRODUCT p on samd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(" UNION ALL ");
		sql.append("select TO_CHAR(ow.receivedate,'yyyy-MM'),ow.RECEIVEORGANID oid,o.ORGANNAME,owd.PRODUCTNAME,-(owd.TAKEQUANTITY*fu.xquantity*p.boxquantity) QUANTITY,p.countunit from ORGAN_WITHDRAW ow ");
		sql.append("join ORGAN_WITHDRAW_DETAIL owd on ow.id =owd.owid ");
		sql.append("and ow.PORGANID = '"+makeorganid+"' ");
		sql.append("and ow.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("fromDate"))) {
			sql.append("and TO_CHAR(ow.receivedate,'yyyy-MM') >= '"+request.getParameter("fromDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("toDate"))) {
			sql.append("and TO_CHAR(ow.receivedate,'yyyy-MM') <= '"+request.getParameter("toDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and ow.RECEIVEORGANID = '"+request.getParameter("organId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and owd.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		sql.append("JOIN ORGAN o on ow.RECEIVEORGANID = o.Id ");
		sql.append("JOIN PRODUCT p on owd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(") GROUP BY organId,makedate,PRODUCTNAME ");
		sql.append(" order BY organname,makedate desc ");
		return EntityManager.jdbcquery(sql.toString());
	}
	
	public List<Map<String, String>> getReceiveSummaryDetail(
			HttpServletRequest request, String makeorganid) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select max(oid) organId,makedate yearMonth,max(organname) organName,PRODUCTNAME,SPECMODE SPEC, sum(QUANTITY) quantity,max(countunit) countunit from ( ");
		sql.append("select TO_CHAR(SAM.receivedate,'yyyy-MM') makedate,sam.OUTORGANID oid,SAM.OUTORGANNAME organname,samd.productid,SAMD.PRODUCTNAME,SAMD.SPECMODE,SAMD.RECEIVEQUANTITY*fu.xquantity*p.boxquantity QUANTITY,1 category,p.countunit from STOCK_ALTER_MOVE sam  ");
		sql.append("join STOCK_ALTER_MOVE_DETAIL samd on SAM.id =SAMD.SAMID ");
		sql.append("and SAM.RECEIVEORGANID = '"+makeorganid+"' ");
		sql.append("and SAM.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("yearMonth"))) {
			sql.append("and TO_CHAR(SAM.receivedate,'yyyy-MM') = '"+request.getParameter("yearMonth")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and SAM.OUTORGANID = '"+request.getParameter("organId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
			sql.append("and SAMD.PRODUCTID = '"+request.getParameter("productId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and SAMD.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		sql.append("JOIN PRODUCT p on samd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(" UNION ALL ");
		sql.append("select TO_CHAR(ow.receivedate,'yyyy-MM'),ow.RECEIVEORGANID oid,o.ORGANNAME,owd.productid,owd.PRODUCTNAME,owd.SPECMODE,-(owd.TAKEQUANTITY*fu.xquantity*p.boxquantity) QUANTITY,2 category,p.countunit from ORGAN_WITHDRAW ow ");
		sql.append("join ORGAN_WITHDRAW_DETAIL owd on ow.id =owd.owid ");
		sql.append("and ow.PORGANID = '"+makeorganid+"' ");
		sql.append("and ow.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("yearMonth"))) {
			sql.append("and TO_CHAR(ow.receivedate,'yyyy-MM') = '"+request.getParameter("yearMonth")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and ow.RECEIVEORGANID = '"+request.getParameter("organId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
			sql.append("and owd.PRODUCTID = '"+request.getParameter("productId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and owd.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		sql.append("JOIN ORGAN o on ow.RECEIVEORGANID = o.Id ");
		sql.append("JOIN PRODUCT p on owd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(") GROUP BY makedate,PRODUCTNAME,SPECMODE ");
		sql.append(" ORDER BY makedate,PRODUCTNAME,SPECMODE ");
		return EntityManager.jdbcquery(sql.toString());
	}
	
	public List<Map<String, String>> getReceiveSummaryProductDetail(
			HttpServletRequest request, String makeorganid) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select makedate yearMonth,max(organname) organName,PRODUCTNAME,SPECMODE SPEC, sum(QUANTITY) quantity,max(countunit) countunit,category from ( ");
		sql.append("select TO_CHAR(SAM.receivedate,'yyyy-MM') makedate,sam.OUTORGANID oid,SAM.OUTORGANNAME organname,samd.productid,SAMD.PRODUCTNAME,SAMD.SPECMODE,SAMD.RECEIVEQUANTITY*fu.xquantity*p.boxquantity QUANTITY,1 category,p.countunit from STOCK_ALTER_MOVE sam  ");
		sql.append("join STOCK_ALTER_MOVE_DETAIL samd on SAM.id =SAMD.SAMID ");
		sql.append("and SAM.RECEIVEORGANID = '"+makeorganid+"' ");
		sql.append("and SAM.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("yearMonth"))) {
			sql.append("and TO_CHAR(SAM.receivedate,'yyyy-MM') = '"+request.getParameter("yearMonth")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and SAM.OUTORGANID = '"+request.getParameter("organId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
			sql.append("and SAMD.PRODUCTID = '"+request.getParameter("productId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and SAMD.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("spec"))) {
			sql.append("and SAMD.SPECMODE = '"+request.getParameter("spec")+"' ");
		}
		sql.append("JOIN PRODUCT p on samd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(" UNION ALL ");
		sql.append("select TO_CHAR(ow.receivedate,'yyyy-MM'),ow.RECEIVEORGANID oid,o.ORGANNAME,owd.productid,owd.PRODUCTNAME,owd.SPECMODE,-(owd.TAKEQUANTITY*fu.xquantity*p.boxquantity) QUANTITY,2 category,p.countunit from ORGAN_WITHDRAW ow ");
		sql.append("join ORGAN_WITHDRAW_DETAIL owd on ow.id =owd.owid ");
		sql.append("and ow.PORGANID = '"+makeorganid+"' ");
		sql.append("and ow.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("yearMonth"))) {
			sql.append("and TO_CHAR(ow.receivedate,'yyyy-MM') = '"+request.getParameter("yearMonth")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and ow.RECEIVEORGANID = '"+request.getParameter("organId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
			sql.append("and owd.PRODUCTID = '"+request.getParameter("productId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and owd.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("spec"))) {
			sql.append("and owd.SPECMODE = '"+request.getParameter("spec")+"' ");
		}
		sql.append("JOIN ORGAN o on ow.RECEIVEORGANID = o.Id ");
		sql.append("JOIN PRODUCT p on owd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(") GROUP BY makedate,PRODUCTNAME,SPECMODE,category ");
		sql.append(" ORDER BY PRODUCTNAME,SPECMODE,category ");
		return EntityManager.jdbcquery(sql.toString());
	}
	
	public List<Map<String, String>> getReceiveSummaryForBKR(
			HttpServletRequest request, String makeorganid) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select oid,makedate yearMonth,PRODUCTID,category,max(organname) organName,max(PRODUCTNAME) PRODUCTNAME,max(SPECMODE) SPEC, sum(QUANTITY) quantity,max(countunit) countunit from ( ");
		sql.append("select TO_CHAR(SAM.receivedate,'yyyy-MM') makedate,sam.OUTORGANID oid,SAM.OUTORGANNAME organname,samd.productid,SAMD.PRODUCTNAME,SAMD.SPECMODE,SAMD.RECEIVEQUANTITY*fu.xquantity*p.boxquantity QUANTITY,1 category,p.countunit from STOCK_ALTER_MOVE sam  ");
		sql.append("join STOCK_ALTER_MOVE_DETAIL samd on SAM.id =SAMD.SAMID ");
		sql.append("and SAM.RECEIVEORGANID = '"+makeorganid+"' ");
		sql.append("and SAM.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("fromDate"))) {
			sql.append("and TO_CHAR(SAM.receivedate,'yyyy-MM') >= '"+request.getParameter("fromDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("toDate"))) {
			sql.append("and TO_CHAR(SAM.receivedate,'yyyy-MM') <= '"+request.getParameter("toDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and SAM.OUTORGANID = '"+request.getParameter("organId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
			sql.append("and SAMD.PRODUCTID = '"+request.getParameter("productId")+"' ");
		}
		sql.append("JOIN PRODUCT p on samd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(" UNION ALL ");
		sql.append("select TO_CHAR(ow.receivedate,'yyyy-MM'),o.id oid,o.organName,owd.productid,owd.PRODUCTNAME,owd.SPECMODE,-(owd.TAKEQUANTITY*fu.xquantity*p.boxquantity) QUANTITY,2 category,p.countunit from ORGAN_WITHDRAW ow ");
		sql.append("join ORGAN_WITHDRAW_DETAIL owd on ow.id =owd.owid ");
		sql.append("and ow.PORGANID = '"+makeorganid+"' ");
		sql.append("and ow.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("fromDate"))) {
			sql.append("and TO_CHAR(ow.receivedate,'yyyy-MM') >= '"+request.getParameter("fromDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("toDate"))) {
			sql.append("and TO_CHAR(ow.receivedate,'yyyy-MM') <= '"+request.getParameter("toDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and ow.RECEIVEORGANID = '"+request.getParameter("organId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
			sql.append("and owd.PRODUCTID = '"+request.getParameter("productId")+"' ");
		}
		sql.append("JOIN PRODUCT p on owd.productId = p.Id ");
		sql.append("JOIN Organ o on ow.RECEIVEORGANID = o.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(") GROUP BY oid,makedate,productid,category ");
		sql.append(" order BY makedate desc,organname,PRODUCTNAME ");
		return EntityManager.jdbcquery(sql.toString());
	}

	public List<Map<String, String>> getDispatchSummary(
			HttpServletRequest request, String makeorganid) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select oid organId, makedate yearMonth,max(organname) organName,PRODUCTNAME, sum(QUANTITY) quantity,max(countunit) countunit from ( ");
		sql.append("select TO_CHAR(SAM.shipmentdate,'yyyy-MM') makedate,sam.RECEIVEORGANID oid,SAM.RECEIVEORGANIDNAME organname,samd.productid,SAMD.PRODUCTNAME,SAMD.SPECMODE,SAMD.RECEIVEQUANTITY*fu.xquantity*p.boxquantity QUANTITY,1 category,p.countunit from STOCK_ALTER_MOVE sam  ");
		sql.append("join STOCK_ALTER_MOVE_DETAIL samd on SAM.id =SAMD.SAMID ");
		sql.append("and SAM.outorganid = '"+makeorganid+"' ");
		sql.append("and SAM.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("fromDate"))) {
			sql.append("and TO_CHAR(SAM.shipmentdate,'yyyy-MM') >= '"+request.getParameter("fromDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("toDate"))) {
			sql.append("and TO_CHAR(SAM.shipmentdate,'yyyy-MM') <= '"+request.getParameter("toDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and SAM.RECEIVEORGANID = '"+request.getParameter("organId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and SAMD.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		sql.append("JOIN PRODUCT p on samd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(" UNION ALL ");
		sql.append("select TO_CHAR(ow.MAKEDATE,'yyyy-MM'),ow.PORGANID oid,ow.PORGANNAME,owd.productid,owd.PRODUCTNAME,owd.SPECMODE,-(owd.TAKEQUANTITY*fu.xquantity*p.boxquantity) QUANTITY,2 category,p.countunit from ORGAN_WITHDRAW ow ");
		sql.append("join ORGAN_WITHDRAW_DETAIL owd on ow.id =owd.owid ");
		sql.append("and ow.RECEIVEORGANID = '"+makeorganid+"' ");
		sql.append("and ow.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("fromDate"))) {
			sql.append("and TO_CHAR(ow.MAKEDATE,'yyyy-MM') >= '"+request.getParameter("fromDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("toDate"))) {
			sql.append("and TO_CHAR(ow.MAKEDATE,'yyyy-MM') <= '"+request.getParameter("toDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and ow.PORGANID = '"+request.getParameter("organId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and owd.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		sql.append("JOIN PRODUCT p on owd.productId = p.Id ");
		sql.append("join organ o on o.id = ow.RECEIVEORGANID ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(") GROUP BY oid,makedate,productname ");
		sql.append(" order BY organname,makedate desc ");
		return EntityManager.jdbcquery(sql.toString());
	}
	
	public List<Map<String, String>> getDispatchSummaryDetail(
			HttpServletRequest request, String makeorganid) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select max(oid) organId,makedate yearMonth,max(organname) organName,PRODUCTNAME,SPECMODE SPEC, sum(QUANTITY) quantity,max(countunit) countunit from ( ");
		sql.append("select TO_CHAR(SAM.shipmentdate,'yyyy-MM') makedate,sam.RECEIVEORGANID oid,SAM.RECEIVEORGANIDNAME organname,samd.productid,SAMD.PRODUCTNAME,SAMD.SPECMODE,SAMD.RECEIVEQUANTITY*fu.xquantity*p.boxquantity QUANTITY,1 category,p.countunit from STOCK_ALTER_MOVE sam  ");
		sql.append("join STOCK_ALTER_MOVE_DETAIL samd on SAM.id =SAMD.SAMID ");
		sql.append("and SAM.outorganid = '"+makeorganid+"' ");
		sql.append("and SAM.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("yearMonth"))) {
			sql.append("and TO_CHAR(SAM.shipmentdate,'yyyy-MM') = '"+request.getParameter("yearMonth")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and SAM.RECEIVEORGANID = '"+request.getParameter("organId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
			sql.append("and SAMD.PRODUCTID = '"+request.getParameter("productId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and SAMD.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		sql.append("JOIN PRODUCT p on samd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(" UNION ALL ");
		sql.append("select TO_CHAR(ow.MAKEDATE,'yyyy-MM'),ow.PORGANID oid,ow.PORGANNAME,owd.productid,owd.PRODUCTNAME,owd.SPECMODE,-(owd.TAKEQUANTITY*fu.xquantity*p.boxquantity) QUANTITY,2 category,p.countunit from ORGAN_WITHDRAW ow ");
		sql.append("join ORGAN_WITHDRAW_DETAIL owd on ow.id =owd.owid ");
		sql.append("and ow.RECEIVEORGANID = '"+makeorganid+"' ");
		sql.append("and ow.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("yearMonth"))) {
			sql.append("and TO_CHAR(ow.MAKEDATE,'yyyy-MM') = '"+request.getParameter("yearMonth")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and ow.PORGANID = '"+request.getParameter("organId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
			sql.append("and owd.PRODUCTID = '"+request.getParameter("productId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and owd.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		sql.append("JOIN PRODUCT p on owd.productId = p.Id ");
//		sql.append("join organ o on o.id = ow.RECEIVEORGANID ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(") GROUP BY makedate,PRODUCTNAME,SPECMODE ");
		sql.append(" ORDER BY makedate,PRODUCTNAME,SPECMODE ");
		return EntityManager.jdbcquery(sql.toString());
	}
	
	public List<Map<String, String>> getDispatchSummaryProductDetail(
			HttpServletRequest request, String makeorganid) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select makedate yearMonth,max(organname) organName,PRODUCTNAME,SPECMODE SPEC, sum(QUANTITY) quantity,max(countunit) countunit,category from ( ");
		sql.append("select TO_CHAR(SAM.shipmentdate,'yyyy-MM') makedate,sam.RECEIVEORGANID oid,SAM.RECEIVEORGANIDNAME organname,samd.productid,SAMD.PRODUCTNAME,SAMD.SPECMODE,SAMD.RECEIVEQUANTITY*fu.xquantity*p.boxquantity QUANTITY,1 category,p.countunit from STOCK_ALTER_MOVE sam  ");
		sql.append("join STOCK_ALTER_MOVE_DETAIL samd on SAM.id =SAMD.SAMID ");
		sql.append("and SAM.outorganid = '"+makeorganid+"' ");
		sql.append("and SAM.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("yearMonth"))) {
			sql.append("and TO_CHAR(SAM.shipmentdate,'yyyy-MM') = '"+request.getParameter("yearMonth")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and SAM.RECEIVEORGANID = '"+request.getParameter("organId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
			sql.append("and SAMD.PRODUCTID = '"+request.getParameter("productId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and SAMD.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("spec"))) {
			sql.append("and SAMD.SPECMODE = '"+request.getParameter("spec")+"' ");
		}
		sql.append("JOIN PRODUCT p on samd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(" UNION ALL ");
		sql.append("select TO_CHAR(ow.MAKEDATE,'yyyy-MM'),ow.PORGANID oid,ow.PORGANNAME,owd.productid,owd.PRODUCTNAME,owd.SPECMODE,-(owd.TAKEQUANTITY*fu.xquantity*p.boxquantity) QUANTITY,2 category,p.countunit from ORGAN_WITHDRAW ow ");
		sql.append("join ORGAN_WITHDRAW_DETAIL owd on ow.id =owd.owid ");
		sql.append("and ow.RECEIVEORGANID = '"+makeorganid+"' ");
		sql.append("and ow.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("yearMonth"))) {
			sql.append("and TO_CHAR(ow.MAKEDATE,'yyyy-MM') = '"+request.getParameter("yearMonth")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and ow.PORGANID = '"+request.getParameter("organId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
			sql.append("and owd.PRODUCTID = '"+request.getParameter("productId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and owd.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("spec"))) {
			sql.append("and owd.SPECMODE = '"+request.getParameter("spec")+"' ");
		}
		sql.append("JOIN PRODUCT p on owd.productId = p.Id ");
//		sql.append("join organ o on o.id = ow.RECEIVEORGANID ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(") GROUP BY makedate,PRODUCTNAME,SPECMODE,category ");
		sql.append(" ORDER BY PRODUCTNAME,SPECMODE,category ");
		return EntityManager.jdbcquery(sql.toString());
	}

	public List<Map<String, String>> getReceiveSummaryDetail(
			HttpServletRequest request, String makeorganid, String condition) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select max(organId) organId,max(makedate) yearMonth,max(organname) organName,PRODUCTNAME,SPECMODE SPEC, sum(QUANTITY) quantity,max(countunit) countunit from ( ");
		sql.append("select TO_CHAR(SAM.receivedate,'yyyy-MM') makedate,SAM.RECEIVEORGANID organId,sam.receiveorganidname organname,samd.productid,SAMD.PRODUCTNAME,SAMD.SPECMODE,SAMD.RECEIVEQUANTITY*fu.xquantity*p.boxquantity QUANTITY,1 category,p.countunit from STOCK_ALTER_MOVE sam  ");
		sql.append("join STOCK_ALTER_MOVE_DETAIL samd on SAM.id =SAMD.SAMID ");
		sql.append("and SAM.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("yearMonth"))) {
			sql.append("and TO_CHAR(SAM.receivedate,'yyyy-MM') = '"+request.getParameter("yearMonth")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and SAM.RECEIVEORGANID = '"+request.getParameter("organId")+"' ");
		} 
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and SAMD.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
			sql.append("and SAMD.PRODUCTID = '"+request.getParameter("productId")+"' ");
		}
		sql.append("JOIN PRODUCT p on samd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(" UNION ALL ");
		sql.append("select TO_CHAR(ow.receivedate,'yyyy-MM'),ow.PORGANID,ow.PORGANNAME,owd.productid,owd.PRODUCTNAME,owd.SPECMODE,-(owd.TAKEQUANTITY*fu.xquantity*p.boxquantity) QUANTITY,2 category,p.countunit from ORGAN_WITHDRAW ow ");
		sql.append("join ORGAN_WITHDRAW_DETAIL owd on ow.id =owd.owid ");
		sql.append("and ow.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and ow.PORGANID = '"+request.getParameter("organId")+"' ");
		} 
		if(!StringUtil.isEmpty(request.getParameter("yearMonth"))) {
			sql.append("and TO_CHAR(ow.receivedate,'yyyy-MM') = '"+request.getParameter("yearMonth")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
			sql.append("and owd.PRODUCTID = '"+request.getParameter("productId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and owd.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		sql.append("JOIN PRODUCT p on owd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(") GROUP BY PRODUCTNAME,SPECMODE ");
		sql.append(" ORDER BY PRODUCTNAME,SPECMODE ");
		return EntityManager.jdbcquery(sql.toString());
	}
	
	public List<Map<String, String>> getReceiveSummaryProductDetail(
			HttpServletRequest request, String makeorganid, String condition) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select max(makedate) yearMonth,max(organname) organName,PRODUCTNAME,SPECMODE SPEC, sum(QUANTITY) quantity,max(countunit) countunit,category from ( ");
		sql.append("select TO_CHAR(SAM.receivedate,'yyyy-MM') makedate,sam.receiveorganidname organname,samd.productid,SAMD.PRODUCTNAME,SAMD.SPECMODE,SAMD.RECEIVEQUANTITY*fu.xquantity*p.boxquantity QUANTITY,1 category,p.countunit from STOCK_ALTER_MOVE sam  ");
		sql.append("join STOCK_ALTER_MOVE_DETAIL samd on SAM.id =SAMD.SAMID ");
		sql.append("and SAM.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("yearMonth"))) {
			sql.append("and TO_CHAR(SAM.receivedate,'yyyy-MM') = '"+request.getParameter("yearMonth")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and SAM.RECEIVEORGANID = '"+request.getParameter("organId")+"' ");
		} 
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and SAMD.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
			sql.append("and SAMD.PRODUCTID = '"+request.getParameter("productId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("spec"))) {
			sql.append("and SAMD.SPECMODE = '"+request.getParameter("spec")+"' ");
		}
		sql.append("JOIN PRODUCT p on samd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(" UNION ALL ");
		sql.append("select TO_CHAR(ow.receivedate,'yyyy-MM'),ow.PORGANNAME,owd.productid,owd.PRODUCTNAME,owd.SPECMODE,-(owd.TAKEQUANTITY*fu.xquantity*p.boxquantity) QUANTITY,2 category,p.countunit from ORGAN_WITHDRAW ow ");
		sql.append("join ORGAN_WITHDRAW_DETAIL owd on ow.id =owd.owid ");
		sql.append("and ow.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and ow.PORGANID = '"+request.getParameter("organId")+"' ");
		} 
		if(!StringUtil.isEmpty(request.getParameter("yearMonth"))) {
			sql.append("and TO_CHAR(ow.receivedate,'yyyy-MM') = '"+request.getParameter("yearMonth")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
			sql.append("and owd.PRODUCTID = '"+request.getParameter("productId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and owd.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("spec"))) {
			sql.append("and owd.SPECMODE = '"+request.getParameter("spec")+"' ");
		}
		sql.append("JOIN PRODUCT p on owd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(") GROUP BY PRODUCTNAME,SPECMODE,category ");
		sql.append(" ORDER BY PRODUCTNAME,SPECMODE,category ");
		return EntityManager.jdbcquery(sql.toString());
	}
	
	public List<Map<String, String>> getReceiveSummaryDetailForBKR(
			HttpServletRequest request, String makeorganid, String condition) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select max(organId) organId,max(makedate) yearMonth,max(organname) organName,PRODUCTNAME,SPECMODE SPEC, sum(QUANTITY) quantity,max(countunit) countunit from ( ");
		sql.append("select TO_CHAR(SAM.receivedate,'yyyy-MM') makedate,SAM.RECEIVEORGANID organId,SAM.RECEIVEORGANIDNAME organname,samd.productid,SAMD.PRODUCTNAME,SAMD.SPECMODE,SAMD.RECEIVEQUANTITY*fu.xquantity*p.boxquantity QUANTITY,1 category,p.countunit from STOCK_ALTER_MOVE sam  ");
		sql.append("join STOCK_ALTER_MOVE_DETAIL samd on SAM.id =SAMD.SAMID ");
//		sql.append("and SAM.RECEIVEORGANID = '"+makeorganid+"' ");
		sql.append("and SAM.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and SAM.RECEIVEORGANID = '"+request.getParameter("organId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("yearMonth"))) {
			sql.append("and TO_CHAR(SAM.receivedate,'yyyy-MM') = '"+request.getParameter("yearMonth")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
			sql.append("and SAMD.PRODUCTID = '"+request.getParameter("productId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and SAMD.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		sql.append("JOIN PRODUCT p on samd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(" UNION ALL ");
		sql.append("select TO_CHAR(ow.receivedate,'yyyy-MM'),ow.PORGANID, ow.porganName,owd.productid,owd.PRODUCTNAME,owd.SPECMODE,-(owd.TAKEQUANTITY*fu.xquantity*p.boxquantity) QUANTITY,2 category,p.countunit from ORGAN_WITHDRAW ow ");
		sql.append("join ORGAN_WITHDRAW_DETAIL owd on ow.id =owd.owid ");
//		sql.append("and ow.RECEIVEORGANID = '"+makeorganid+"' ");
		sql.append("and ow.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and ow.PORGANID = '"+request.getParameter("organId")+"' ");
		} 
		if(!StringUtil.isEmpty(request.getParameter("yearMonth"))) {
			sql.append("and TO_CHAR(ow.receivedate,'yyyy-MM') = '"+request.getParameter("yearMonth")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
			sql.append("and owd.PRODUCTID = '"+request.getParameter("productId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and owd.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		sql.append("JOIN PRODUCT p on owd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(") GROUP BY PRODUCTNAME,SPECMODE ");
		sql.append(" ORDER BY PRODUCTNAME,SPECMODE ");
		return EntityManager.jdbcquery(sql.toString());
	}
	
	public List<Map<String, String>> getReceiveSummaryProductDetailForBKR(
			HttpServletRequest request, String makeorganid, String condition) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select max(makedate) yearMonth,max(organname) organName,PRODUCTNAME,SPECMODE SPEC, sum(QUANTITY) quantity,max(countunit) countunit,category from ( ");
		sql.append("select TO_CHAR(SAM.receivedate,'yyyy-MM') makedate,SAM.RECEIVEORGANIDNAME organname,samd.productid,SAMD.PRODUCTNAME,SAMD.SPECMODE,SAMD.RECEIVEQUANTITY*fu.xquantity*p.boxquantity QUANTITY,1 category,p.countunit from STOCK_ALTER_MOVE sam  ");
		sql.append("join STOCK_ALTER_MOVE_DETAIL samd on SAM.id =SAMD.SAMID ");
//		sql.append("and SAM.RECEIVEORGANID = '"+makeorganid+"' ");
		sql.append("and SAM.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and SAM.RECEIVEORGANID = '"+request.getParameter("organId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("yearMonth"))) {
			sql.append("and TO_CHAR(SAM.receivedate,'yyyy-MM') = '"+request.getParameter("yearMonth")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
			sql.append("and SAMD.PRODUCTID = '"+request.getParameter("productId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and SAMD.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("spec"))) {
			sql.append("and SAMD.SPECMODE = '"+request.getParameter("spec")+"' ");
		}
		sql.append("JOIN PRODUCT p on samd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(" UNION ALL ");
		sql.append("select TO_CHAR(ow.receivedate,'yyyy-MM'),ow.porganName,owd.productid,owd.PRODUCTNAME,owd.SPECMODE,-(owd.TAKEQUANTITY*fu.xquantity*p.boxquantity) QUANTITY,2 category,p.countunit from ORGAN_WITHDRAW ow ");
		sql.append("join ORGAN_WITHDRAW_DETAIL owd on ow.id =owd.owid ");
//		sql.append("and ow.RECEIVEORGANID = '"+makeorganid+"' ");
		sql.append("and ow.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and ow.PORGANID = '"+request.getParameter("organId")+"' ");
		} 
		if(!StringUtil.isEmpty(request.getParameter("yearMonth"))) {
			sql.append("and TO_CHAR(ow.receivedate,'yyyy-MM') = '"+request.getParameter("yearMonth")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
			sql.append("and owd.PRODUCTID = '"+request.getParameter("productId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and owd.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("spec"))) {
			sql.append("and owd.SPECMODE = '"+request.getParameter("spec")+"' ");
		}
		sql.append("JOIN PRODUCT p on owd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(") GROUP BY PRODUCTNAME,SPECMODE,category ");
		sql.append(" ORDER BY PRODUCTNAME,SPECMODE,category ");
		return EntityManager.jdbcquery(sql.toString());
	}

	public List<Map<String, String>> getDispatchSummaryDetail(
			HttpServletRequest request, String makeorganid, String condition) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select max(organId) organId,max(makedate) yearMonth,max(organname) organName,PRODUCTNAME,SPECMODE SPEC, sum(QUANTITY) quantity,max(countunit) countunit from ( ");
		sql.append("select TO_CHAR(SAM.shipmentdate,'yyyy-MM') makedate,SAM.outorganid organId,sam.receiveorganidname organname,samd.productid,SAMD.PRODUCTNAME,SAMD.SPECMODE,SAMD.RECEIVEQUANTITY*fu.xquantity*p.boxquantity QUANTITY,1 category,p.countunit from STOCK_ALTER_MOVE sam  ");
		sql.append("join STOCK_ALTER_MOVE_DETAIL samd on SAM.id =SAMD.SAMID ");
		sql.append("and SAM.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and SAM.outorganid = '"+request.getParameter("organId")+"' ");
		} 
		if(!StringUtil.isEmpty(request.getParameter("yearMonth"))) {
			sql.append("and TO_CHAR(SAM.shipmentdate,'yyyy-MM') = '"+request.getParameter("yearMonth")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
			sql.append("and SAMD.PRODUCTID = '"+request.getParameter("productId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and SAMD.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		sql.append("JOIN PRODUCT p on samd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(" UNION ALL ");
		sql.append("select TO_CHAR(ow.MAKEDATE,'yyyy-MM'),ow.RECEIVEORGANID,ino.ORGANNAME,owd.productid,owd.PRODUCTNAME,owd.SPECMODE,-(owd.TAKEQUANTITY*fu.xquantity*p.boxquantity) QUANTITY,2 category,p.countunit from ORGAN_WITHDRAW ow ");
		sql.append("join ORGAN_WITHDRAW_DETAIL owd on ow.id =owd.owid ");
		sql.append("and ow.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and ow.RECEIVEORGANID = '"+request.getParameter("organId")+"' ");
		} 
		if(!StringUtil.isEmpty(request.getParameter("yearMonth"))) {
			sql.append("and TO_CHAR(ow.MAKEDATE,'yyyy-MM') = '"+request.getParameter("yearMonth")+"' ");
		}
		
		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
			sql.append("and owd.PRODUCTID = '"+request.getParameter("productId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and owd.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		
		sql.append("JOIN PRODUCT p on owd.productId = p.Id ");
		sql.append("join organ ino on ino.id = ow.RECEIVEORGANID ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(") GROUP BY PRODUCTNAME,SPECMODE ");
		sql.append(" ORDER BY PRODUCTNAME,SPECMODE ");
		return EntityManager.jdbcquery(sql.toString());
	}
	
	public List<Map<String, String>> getDispatchSummaryProductDetail(
			HttpServletRequest request, String makeorganid, String condition) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select max(makedate) yearMonth,max(organname) organName,PRODUCTNAME,SPECMODE SPEC, sum(QUANTITY) quantity,max(countunit) countunit,category from ( ");
		sql.append("select TO_CHAR(SAM.shipmentdate,'yyyy-MM') makedate,sam.receiveorganidname organname,samd.productid,SAMD.PRODUCTNAME,SAMD.SPECMODE,SAMD.RECEIVEQUANTITY*fu.xquantity*p.boxquantity QUANTITY,1 category,p.countunit from STOCK_ALTER_MOVE sam  ");
		sql.append("join STOCK_ALTER_MOVE_DETAIL samd on SAM.id =SAMD.SAMID ");
		sql.append("and SAM.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and SAM.outorganid = '"+request.getParameter("organId")+"' ");
		} 
		if(!StringUtil.isEmpty(request.getParameter("yearMonth"))) {
			sql.append("and TO_CHAR(SAM.shipmentdate,'yyyy-MM') = '"+request.getParameter("yearMonth")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
			sql.append("and SAMD.PRODUCTID = '"+request.getParameter("productId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and SAMD.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("spec"))) {
			sql.append("and SAMD.SPECMODE = '"+request.getParameter("spec")+"' ");
		}
		sql.append("JOIN PRODUCT p on samd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(" UNION ALL ");
		sql.append("select TO_CHAR(ow.MAKEDATE,'yyyy-MM'),ino.ORGANNAME,owd.productid,owd.PRODUCTNAME,owd.SPECMODE,-(owd.TAKEQUANTITY*fu.xquantity*p.boxquantity) QUANTITY,2 category,p.countunit from ORGAN_WITHDRAW ow ");
		sql.append("join ORGAN_WITHDRAW_DETAIL owd on ow.id =owd.owid ");
		sql.append("and ow.ISCOMPLETE = 1 ");
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and ow.RECEIVEORGANID = '"+request.getParameter("organId")+"' ");
		} 
		if(!StringUtil.isEmpty(request.getParameter("yearMonth"))) {
			sql.append("and TO_CHAR(ow.MAKEDATE,'yyyy-MM') = '"+request.getParameter("yearMonth")+"' ");
		}
		
		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
			sql.append("and owd.PRODUCTID = '"+request.getParameter("productId")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and owd.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("spec"))) {
			sql.append("and owd.SPECMODE = '"+request.getParameter("spec")+"' ");
		}
		sql.append("JOIN PRODUCT p on owd.productId = p.Id ");
		sql.append("join organ ino on ino.id = ow.RECEIVEORGANID ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(") GROUP BY PRODUCTNAME,SPECMODE,category ");
		sql.append(" ORDER BY PRODUCTNAME,SPECMODE,category ");
		return EntityManager.jdbcquery(sql.toString());
	}
	
	public List<Map<String, String>> getReceiveSummary(
			HttpServletRequest request, String makeorganid, String condition) throws Exception {
		StringBuffer sql = new StringBuffer();
		/*sql.append("select oid,makedate yearMonth,PRODUCTID,category,max(organname) organName,max(PRODUCTNAME) PRODUCTNAME,max(SPECMODE) SPEC, sum(QUANTITY) quantity,max(countunit) countunit from ( ");
		sql.append("select TO_CHAR(SAM.receivedate,'yyyy-MM') makedate,o.id oid,o.organname,samd.productid,SAMD.PRODUCTNAME,SAMD.SPECMODE,SAMD.RECEIVEQUANTITY*fu.xquantity*p.boxquantity QUANTITY,1 category,p.countunit from STOCK_ALTER_MOVE sam  ");
		sql.append("join STOCK_ALTER_MOVE_DETAIL samd on SAM.id =SAMD.SAMID ");
		sql.append("and SAM.ISCOMPLETE = 1 ");
		sql.append("join ORGAN o on o.id = SAM.RECEIVEORGANID and o.organType="+OrganType.Dealer.getValue()+" and o.organModel ="+DealerType.BKD.getValue());
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and SAM.RECEIVEORGANID = '"+request.getParameter("organId")+"' ");
		} else {
			sql.append(" and "+condition);
		} 
		
		if(!StringUtil.isEmpty(request.getParameter("fromDate"))) {
			sql.append("and TO_CHAR(SAM.receivedate,'yyyy-MM') >= '"+request.getParameter("fromDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("toDate"))) {
			sql.append("and TO_CHAR(SAM.receivedate,'yyyy-MM') <= '"+request.getParameter("toDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
			sql.append("and SAMD.PRODUCTID = '"+request.getParameter("productId")+"' ");
		}
		sql.append("JOIN PRODUCT p on samd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(" UNION ALL ");
		sql.append("select TO_CHAR(ow.receivedate,'yyyy-MM'),ow.PORGANID oid,ow.PORGANNAME,owd.productid,owd.PRODUCTNAME,owd.SPECMODE,-(owd.TAKEQUANTITY*fu.xquantity*p.boxquantity) QUANTITY,2 category,p.countunit from ORGAN_WITHDRAW ow ");
		sql.append("join ORGAN_WITHDRAW_DETAIL owd on ow.id =owd.owid ");
		sql.append("and ow.ISCOMPLETE = 1 ");
		sql.append("join ORGAN o on o.id = ow.PORGANID and o.organType="+OrganType.Dealer.getValue()+" and o.organModel ="+DealerType.BKD.getValue());
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and ow.PORGANID = '"+request.getParameter("organId")+"' ");
		} else {
			sql.append(" and "+condition);
		}
		if(!StringUtil.isEmpty(request.getParameter("fromDate"))) {
			sql.append("and TO_CHAR(ow.receivedate,'yyyy-MM') >= '"+request.getParameter("fromDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("toDate"))) {
			sql.append("and TO_CHAR(ow.receivedate,'yyyy-MM') <= '"+request.getParameter("toDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
			sql.append("and owd.PRODUCTID = '"+request.getParameter("productId")+"' ");
		}
		sql.append("JOIN PRODUCT p on owd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(") GROUP BY oid,makedate,productid,category ");*/
		
		sql.append("select organId,makedate yearMonth,max(organname) organName,PRODUCTNAME PRODUCTNAME, sum(QUANTITY) quantity,max(countunit) countunit from ( ");
		sql.append("select TO_CHAR(SAM.receivedate,'yyyy-MM') makedate,o.id organId,o.organname,SAMD.PRODUCTNAME,SAMD.RECEIVEQUANTITY*fu.xquantity*p.boxquantity QUANTITY,1 category,p.countunit from STOCK_ALTER_MOVE sam  ");
		sql.append("join STOCK_ALTER_MOVE_DETAIL samd on SAM.id =SAMD.SAMID ");
		sql.append("and SAM.ISCOMPLETE = 1 ");
		sql.append("join ORGAN o on o.id = SAM.RECEIVEORGANID and o.organType="+OrganType.Dealer.getValue()+" and o.organModel ="+DealerType.BKD.getValue()+" ");
		if(!StringUtil.isEmpty(request.getParameter("areaId"))) {
			sql.append("and o.AREAS = "+request.getParameter("areaId")+" ");
		} 
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and SAM.RECEIVEORGANID = '"+request.getParameter("organId")+"' ");
		} else {
			sql.append(" and "+condition);
		} 
		
		if(!StringUtil.isEmpty(request.getParameter("fromDate"))) {
			sql.append("and TO_CHAR(SAM.receivedate,'yyyy-MM') >= '"+request.getParameter("fromDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("toDate"))) {
			sql.append("and TO_CHAR(SAM.receivedate,'yyyy-MM') <= '"+request.getParameter("toDate")+"' ");
		}
//		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
//			sql.append("and SAMD.PRODUCTID = '"+request.getParameter("productId")+"' ");
//		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and SAMD.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		sql.append("JOIN PRODUCT p on samd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(" UNION ALL ");
		sql.append("select TO_CHAR(ow.receivedate,'yyyy-MM'),ow.PORGANID oid,ow.PORGANNAME,owd.PRODUCTNAME,-(owd.TAKEQUANTITY*fu.xquantity*p.boxquantity) QUANTITY,2 category,p.countunit from ORGAN_WITHDRAW ow ");
		sql.append("join ORGAN_WITHDRAW_DETAIL owd on ow.id =owd.owid ");
		sql.append("and ow.ISCOMPLETE = 1 ");
		sql.append("join ORGAN o on o.id = ow.PORGANID and o.organType="+OrganType.Dealer.getValue()+" and o.organModel ="+DealerType.BKD.getValue()+" ");
		if(!StringUtil.isEmpty(request.getParameter("areaId"))) {
			sql.append("and o.AREAS = "+request.getParameter("areaId")+" ");
		} 
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and ow.PORGANID = '"+request.getParameter("organId")+"' ");
		} else {
			sql.append(" and "+condition);
		}
		if(!StringUtil.isEmpty(request.getParameter("fromDate"))) {
			sql.append("and TO_CHAR(ow.receivedate,'yyyy-MM') >= '"+request.getParameter("fromDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("toDate"))) {
			sql.append("and TO_CHAR(ow.receivedate,'yyyy-MM') <= '"+request.getParameter("toDate")+"' ");
		}
//		if(!StringUtil.isEmpty(request.getParameter("productId"))) {
//			sql.append("and owd.PRODUCTID = '"+request.getParameter("productId")+"' ");
//		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and owd.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		sql.append("JOIN PRODUCT p on owd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(") GROUP BY organId,makedate,PRODUCTNAME ");
		sql.append(" ORDER BY organName,makedate desc ");
		
		return EntityManager.jdbcquery(sql.toString());
	}
	
	public List<Map<String, String>> getReceiveSummaryForBKR(
			HttpServletRequest request, String makeorganid, String condition) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select organId,makedate yearMonth,max(organname) organName,PRODUCTNAME PRODUCTNAME, sum(QUANTITY) quantity,max(countunit) countunit from ( ");
		sql.append("select TO_CHAR(SAM.receivedate,'yyyy-MM') makedate,o.ID organId,o.ORGANNAME organname,SAMD.PRODUCTNAME,SAMD.RECEIVEQUANTITY*fu.xquantity*p.boxquantity QUANTITY,1 category,p.countunit from STOCK_ALTER_MOVE sam  ");
		sql.append("join STOCK_ALTER_MOVE_DETAIL samd on SAM.id =SAMD.SAMID ");
//		sql.append("and SAM.RECEIVEORGANID = '"+makeorganid+"' ");
		sql.append("and SAM.ISCOMPLETE = 1 ");
		sql.append("join ORGAN o on o.id = SAM.RECEIVEORGANID  and organType="+OrganType.Dealer.getValue()+" and organModel not in ("+DealerType.PD.getValue()+","+DealerType.BKD.getValue()+") ");
		if(!StringUtil.isEmpty(request.getParameter("areaId"))) {
			sql.append("and o.AREAS = "+request.getParameter("areaId")+" ");
		}
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and SAM.RECEIVEORGANID = '"+request.getParameter("organId")+"' ");
		} else {
			sql.append(" and "+condition);
		}
		if(!StringUtil.isEmpty(request.getParameter("fromDate"))) {
			sql.append("and TO_CHAR(SAM.receivedate,'yyyy-MM') >= '"+request.getParameter("fromDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("toDate"))) {
			sql.append("and TO_CHAR(SAM.receivedate,'yyyy-MM') <= '"+request.getParameter("toDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and SAMD.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		sql.append("JOIN PRODUCT p on samd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(" UNION ALL ");
		sql.append("select TO_CHAR(ow.receivedate,'yyyy-MM'),ow.PORGANID oid,ow.porganName organName,owd.PRODUCTNAME,-(owd.TAKEQUANTITY*fu.xquantity*p.boxquantity) QUANTITY,2 category,p.countunit from ORGAN_WITHDRAW ow ");
		sql.append("join ORGAN_WITHDRAW_DETAIL owd on ow.id =owd.owid ");
//		sql.append("and ow.RECEIVEORGANID = '"+makeorganid+"' ");
		sql.append("and ow.ISCOMPLETE = 1 ");
		sql.append("join ORGAN o on o.id = ow.PORGANID and organType="+OrganType.Dealer.getValue()+" and organModel not in ("+DealerType.PD.getValue()+","+DealerType.BKD.getValue()+") ");
		if(!StringUtil.isEmpty(request.getParameter("areaId"))) {
			sql.append("and o.AREAS = "+request.getParameter("areaId")+" ");
		}
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and ow.PORGANID = '"+request.getParameter("organId")+"' ");
		} else {
			sql.append(" and "+condition);
		}
//		sql.append(" join ORGAN ino on ino.id = ow.RECEIVEORGANID   ");
		if(!StringUtil.isEmpty(request.getParameter("fromDate"))) {
			sql.append("and TO_CHAR(ow.receivedate,'yyyy-MM') >= '"+request.getParameter("fromDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("toDate"))) {
			sql.append("and TO_CHAR(ow.receivedate,'yyyy-MM') <= '"+request.getParameter("toDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and owd.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		sql.append("JOIN PRODUCT p on owd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(") GROUP BY organId,makedate,PRODUCTNAME ");
		sql.append(" order BY organname,makedate desc ");
		return EntityManager.jdbcquery(sql.toString());
	}

	public List<Map<String, String>> getDispatchSummary(
			HttpServletRequest request, String makeorganid, String condition) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select organId, makedate yearMonth,max(organname) organName,PRODUCTNAME PRODUCTNAME, sum(QUANTITY) quantity,max(countunit) countunit from ( ");
		sql.append("select TO_CHAR(SAM.shipmentdate,'yyyy-MM') makedate,o.id organId,o.organname,SAMD.PRODUCTNAME,SAMD.RECEIVEQUANTITY*fu.xquantity*p.boxquantity QUANTITY,1 category,p.countunit from STOCK_ALTER_MOVE sam  ");
		sql.append("join STOCK_ALTER_MOVE_DETAIL samd on SAM.id =SAMD.SAMID ");
		sql.append("and SAM.ISCOMPLETE = 1 ");
		sql.append("join ORGAN o on o.id = SAM.outorganid and o.organType="+OrganType.Dealer.getValue()+" and o.organModel ="+DealerType.BKD.getValue()+" ");
		if(!StringUtil.isEmpty(request.getParameter("areaId"))) {
			sql.append("and o.AREAS = "+request.getParameter("areaId")+" ");
		}
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and SAM.outorganid = '"+request.getParameter("organId")+"' ");
		} else {
			sql.append(" and "+condition);
		}
		if(!StringUtil.isEmpty(request.getParameter("fromDate"))) {
			sql.append("and TO_CHAR(SAM.shipmentdate,'yyyy-MM') >= '"+request.getParameter("fromDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("toDate"))) {
			sql.append("and TO_CHAR(SAM.shipmentdate,'yyyy-MM') <= '"+request.getParameter("toDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and SAMD.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		sql.append("JOIN PRODUCT p on samd.productId = p.Id ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(" UNION ALL ");
		sql.append("select TO_CHAR(ow.MAKEDATE,'yyyy-MM'),ow.RECEIVEORGANID oid,ino.ORGANNAME,owd.PRODUCTNAME,-(owd.TAKEQUANTITY*fu.xquantity*p.boxquantity) QUANTITY,2 category,p.countunit from ORGAN_WITHDRAW ow ");
		sql.append("join ORGAN_WITHDRAW_DETAIL owd on ow.id =owd.owid ");
		sql.append("and ow.ISCOMPLETE = 1 ");
		sql.append("join ORGAN o on o.id = ow.RECEIVEORGANID and o.organType="+OrganType.Dealer.getValue()+" and o.organModel ="+DealerType.BKD.getValue()+" ");
		if(!StringUtil.isEmpty(request.getParameter("areaId"))) {
			sql.append("and o.AREAS = "+request.getParameter("areaId")+" ");
		}
		if(!StringUtil.isEmpty(request.getParameter("organId"))) {
			sql.append("and ow.RECEIVEORGANID = '"+request.getParameter("organId")+"' ");
		} else {
			sql.append(" and "+condition);
		}
		if(!StringUtil.isEmpty(request.getParameter("fromDate"))) {
			sql.append("and TO_CHAR(ow.MAKEDATE,'yyyy-MM') >= '"+request.getParameter("fromDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("toDate"))) {
			sql.append("and TO_CHAR(ow.MAKEDATE,'yyyy-MM') <= '"+request.getParameter("toDate")+"' ");
		}
		if(!StringUtil.isEmpty(request.getParameter("productName"))) {
			sql.append("and owd.PRODUCTNAME = '"+request.getParameter("productName")+"' ");
		}
		sql.append("JOIN PRODUCT p on owd.productId = p.Id ");
		sql.append("join organ ino on ino.id = ow.RECEIVEORGANID ");
		sql.append("JOIN F_UNIT fu on p.id = fu.productId and fu.funitid = "+Constants.DEFAULT_UNIT_ID);
		sql.append(") GROUP BY organId,makedate,PRODUCTNAME ");
		sql.append(" order BY organname,makedate desc ");
		return EntityManager.jdbcquery(sql.toString());
	}
	
	public static void main(String[] args) throws Exception {
		AppTakeTicket apc = new AppTakeTicket();
		Object result = apc.getTakeTicketsInf("00040522970027130926","00040522970027130926");
//		apc.updFirstSearch("2018-04-24 19:00:01","0194127703767");
//		HibernateUtil.commitTransaction();
		System.out.println("--------result:"+result);
	}
}
