package com.winsafe.drp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.exception.NotExistException;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppWarehouse {

	public List<Warehouse> getRWNotInOrganIds(String organids,Integer userId) throws Exception {
		if(StringUtil.isEmpty(organids) ){
			return new ArrayList<Warehouse>();
		}
		String sql = "select w from Warehouse as w where w.makeorganid in ("+organids+") and w.id not in (select warehouseId from RuleUserWh as rw where userId=" + userId + ")";
		return EntityManager.getAllByHql(sql);
	}
	
	/**
	 * 
	 * Create Time: Oct 8, 2011 5:32:48 PM 
	 * @param scanid
	 * @return
	 * @author dufazuo
	 */
	public ScannerWarehouse getScannerWarehouseScanner(String scanid)
	{
		return (ScannerWarehouse)EntityManager.find("from ScannerWarehouse where scannerid='"+scanid+"'");
	}
	
	/**
	 * 
	 * Create Time: Oct 8, 2011 5:37:13 PM 
	 * @param id
	 * @return
	 * @author dufazuo
	 */
	public String getWarehouseNameById(String id)
	{
		return EntityManager.getString("select warehousename from Warehouse where id='" + id + "'");
	}
	
	public List getWarehouse(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String sql = " from Warehouse as w "
				+ pWhereClause + " order by w.id ";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public List getWarehouseList(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String sql = "select w.id,w.nccode,w.warehousename,w.username,w.warehousetel,w.warehouseproperty,w.useflag,o.id oid,o.organname " +
				"from warehouse w join organ o on w.makeorganid = o.id "
				+ pWhereClause + " order by w.id ";
		return PageQuery.jdbcSqlserverQuery(request, sql, pagesize);
	}
	
	public List getWarehouseByNoPage(String pWhereClause) throws Exception {
		String sql = " from Warehouse as w "
				+ pWhereClause + " order by w.id ";
		return EntityManager.getAllByHql(sql);
	}

	public void addWarehouse(Warehouse warehouse) throws Exception {		
		EntityManager.save(warehouse);		
	}
	
	public void updWarehouse(Warehouse w) throws Exception {		
		EntityManager.update(w);		
	}
	
	public void delWarehouse(String id) throws Exception {
		String sql = "delete from Warehouse where id ='"+id+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	public List getAllWarehousebean() throws Exception {
		String sql = "from Warehouse as w ";
		return EntityManager.getAllByHql(sql);
	}
	

	public List getEnableWarehouseByVisit(int userid) throws Exception {
		String sql = "select w from Warehouse as w,WarehouseVisit as wv where w.useflag=1 and w.id=wv.wid and wv.userid="+userid + "order by w.id,wv.id";
		return EntityManager.getAllByHql(sql);
	}
	
	/**
	 * 根据用户ID或仓库属性获取对应用户的仓库列表
	 * @Time 2011-8-15 上午11:54:32 create
	 * @param userid 用户ID
	 * @param warehouseProperty 仓库属性
	 * @return List 对应指定用户的可用的仓库列表
	 * @throws Exception
	 * @author dufazuo
	 */
	public List getEnableWarehouseByVisit(int userid, int warehouseProperty) throws Exception
	{
		String sql = "select w from Warehouse as w,WarehouseVisit as wv where w.useflag=1 and w.id=wv.wid and wv.userid="+userid + " and w.warehouseproperty=" + warehouseProperty;
		return EntityManager.getAllByHql(sql);
	}
	/**
	 * 根据机构属性,查找仓库
	 * Create Time 2014-10-21 上午10:36:26 
	 * @param organProperty
	 * @return
	 * @throws Exception
	 */
	public List getWarehouseByOrganProperty(int organProperty) throws Exception
	{
		String sql = "select w from Warehouse as w,Organ as o where w.useflag=1 and w.makeorganid = o.id  and o.organType=" + organProperty + " order by w.id,o.id";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getEnableWarehouseByVisit(int userid, String organid) throws Exception {
		String sql = "select w from Warehouse as w,WarehouseVisit as wv where w.useflag=1 "+
		"and w.id=wv.wid and wv.userid="+userid+" and w.makeorganid='"+organid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	/**
	 * 获取指定用户、仓库属性和机构的仓库列表
	 * @Time 2011-8-15 下午12:00:24 create
	 * @param userid 用户ID
	 * @param warehouseProperty 仓库属性
	 * @param organid 机构ID
	 * @return List 指定用户、仓库属性和机构的仓库列表
	 * @throws Exception
	 * @author dufazuo
	 */
	public List getEnableWarehouseByvisit(int userid, int warehouseProperty, String organid) throws Exception
	{
		String sql = "select w from Warehouse as w,WarehouseVisit as wv where w.useflag=1 "+
		"and w.id=wv.wid and wv.userid=" + userid + " and w.warehouseproperty=" + warehouseProperty + " and w.makeorganid='" + organid + "'" +" order by w.id";
		return EntityManager.getAllByHql(sql);
	}
	
	public List<Warehouse> getEnableWarehouseByvisit(int userid, String organid) throws Exception
	{
		String sql = "select w from Warehouse as w,WarehouseVisit as wv where w.useflag=1 "+
		"and w.id=wv.wid and wv.userid=" + userid + " and w.makeorganid='" + organid + "'" +" order by w.id asc";
		return EntityManager.getAllByHql(sql);
	}
	
	public HttpServletRequest getEnableWarehouseByvisit(HttpServletRequest request,int userid,String organid) throws Exception
	{
//		String sql = "select w from Warehouse as w where w.useflag=1 "+
//		" and w.makeorganid='" + organid + "'" +" order by w.id asc";
		String sql = "select w from Warehouse as w,RuleUserWh as wv where w.useflag=1 "
			+ "and w.id=wv.warehouseId and wv.userId=" + userid + "  and w.makeorganid='" + organid + "'"
			+ " order by w.id";
		List warehouseList = EntityManager.getAllByHql(sql);
		if (warehouseList != null && warehouseList.size()>0) {
			Warehouse warehouse = (Warehouse)warehouseList.get(0);
			request.setAttribute("WarehouseID", warehouse.getId());
			request.setAttribute("wname", warehouse.getWarehousename());
//			request.setAttribute("waddr", warehouse.getWarehouseaddr());
		}
		return request;
	}
	
	public List getCanUseWarehouse() throws Exception {
		String sql = " from Warehouse as w where w.useflag=1 ";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getCanUseWarehouseByOid(String oid) throws Exception {
		String sql = " from Warehouse as w where w.useflag=1 and w.makeorganid='"+oid+"' order by w.id asc";
		return EntityManager.getAllByHql(sql);
	}

	public Warehouse getWarehouseByID(String id) throws Exception {
		String sql = " from Warehouse where id='" + id+"'";
		return (Warehouse) EntityManager.find(sql);
	}
	
	public Warehouse getWarehouseByShortName(String shortname) throws Exception {
		String sql = " from Warehouse where shortname='" + shortname+"'";
		return (Warehouse) EntityManager.find(sql);
	}

	public List getWarehouseListByOID(String oid)throws Exception{
		Map<String,Object> param = new HashMap<>();
	    param.put("oid", oid);
		String sql=" from Warehouse as w where w.makeorganid=:oid ";
		return EntityManager.getAllByHql(sql, param);
	}
	
	public List getWarehouseListByOID(HttpServletRequest request, int pagesize, String oid)throws Exception{
		String sql=" from Warehouse as w where w.makeorganid='"+oid+"' ";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public List getWarehouseListByOIDWProperty(String oid, int wProperty)throws Exception{
		String sql=" from Warehouse as w where w.makeorganid='"+oid+"' and w.warehouseproperty=" + wProperty;
		return EntityManager.getAllByHql(sql);
	}
	
	
	public Warehouse getWarehouseByOID(String oid)throws Exception{
		String sql=" from Warehouse as w where w.makeorganid='"+oid+"' ";
		return (Warehouse)EntityManager.find(sql);
	}
	
	
	public Warehouse getWarehouseByWarehouseName(String name)throws Exception{
		String sql=" from Warehouse as w where w.warehousename='"+name+"' ";
		return (Warehouse)EntityManager.find(sql);
	}
	
	
	public void addWarehouseBit(WarehouseBit wb) throws Exception {		
		String sql = "insert into warehouse_bit(id,wid,wbid) select "+wb.getId()+",'"+
		wb.getWid()+"','"+wb.getWbid()+"'from dual where not exists (select id from warehouse_bit where wid='"+
		wb.getWid()+"' and wbid='"+wb.getWbid()+"')";
		EntityManager.updateOrdelete(sql);
	}
	
	public List getWarehouseBit(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String sql = " from WarehouseBit "
				+ pWhereClause + " order by id desc ";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public List getWarehouseBitByWid(String wid) throws Exception {
		String sql = "from WarehouseBit where wid ='"+wid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public WarehouseBit getWarehouseBitByid(int id) throws Exception {
		String sql = "from WarehouseBit where id ="+id+"";
		return (WarehouseBit)EntityManager.find(sql);
	}
	
	public WarehouseBit getWarehouseBitByWidWbid(String wid, String wbid) throws Exception {
		String sql = "from WarehouseBit where wid ='"+wid+"' and wbid='"+wbid+"'";
		return (WarehouseBit)EntityManager.find(sql);
	}
	
	/**获得所有对象。
	 * @author shoshana
	 * @param wid
	 * @param wbid
	 * @return
	 * @throws Exception
	 */
	public List getAllWarehouseBitByWidWbid(String wid, String wbid) throws Exception {
		String sql = "select wb.wid,wb.wbid,wb.bitName from WarehouseBit as wb where wid ='"+wid+"' and wbid='"+wbid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public void delWarehouseBitByid(int id) throws Exception {
		String sql = "delete from Warehouse_Bit where id ="+id+"";
		EntityManager.updateOrdelete(sql);
	}

	/**
	 * 根据用友的导入的NCCODE查询WINSAFE的标准编号
	 * @param nccode 用友提供的编号
	 * @return Warehouse
	 * @throws Exception
	 */
	public Warehouse findByNCcode(String nccode) throws NotExistException {
		
		Warehouse temp = new Warehouse();
		String sql = " from Warehouse as w where w.nccode='" + nccode + "'";
		temp = (Warehouse) EntityManager.find(sql);
		if(temp==null){
		throw new NotExistException("基础数据错误，找不到相对应的仓库");
		}
		return temp;
	}
	
public Warehouse findByNCcode(String nccode,int i) throws NotExistException {
		
		Warehouse temp = new Warehouse();
		String sql = " from Warehouse as w where w.nccode='" + nccode + "'";
		temp = (Warehouse) EntityManager.find(sql);
		if(temp==null){
			try {
				DBUserLog.addUserLog(i, 13,"基础数据错误，找不到相对应的仓库" + nccode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		throw new NotExistException("基础数据错误，找不到相对应的仓库");
		}
		return temp;
	}

	/**
	 * 通过类型找到相对应的仓库
	 * @param i 仓库类型对应的编号
	 * @return 仓库集合
	 */
	public List<Warehouse> getWarehouseByProperty(String i){
		String sql = "from Warehouse as w where w.warehouseproperty="+i;
		return EntityManager.getAllByHql(sql);
		
	}
	
	public WarehouseBit getWarehouseBitBywbid(String id) throws Exception {
		String sql = "from WarehouseBit where wbid ='" + id + "'";
		return (WarehouseBit) EntityManager.find(sql);
	}
	
	/**
	 * 根据用户，查他可转仓的仓库
	 * @param userid
	 * @return
	 */
	public List<Warehouse> queryStockMoveWarehouse(String userid){
		String sql = "select w from Warehouse as w,MoveCanuseOrgan mco where mco.oid=w.makeorganid and mco.uid="+userid +" order by w.id desc ";
		return EntityManager.getAllByHql(sql);
		
	}
	
	/**
	 * 根据用户，查他可转仓的仓库
	 * @param userid
	 * @return
	 */
	public List<Warehouse> queryStockMoveWarehouse2(String userid){
		String sql = "select w from Warehouse as w,MoveCanuseOrgan mco where mco.oid=w.makeorganid and mco.uid="+userid +" " +
				"and begindate<='"+DateUtil.formatDate(DateUtil.getCurrentDate(),"yyyy-MM-dd")+"' and enddate>='"+DateUtil.formatDate(DateUtil.getCurrentDate(),"yyyy-MM-dd")+"'"+
				"order by w.id desc ";
		return EntityManager.getAllByHql(sql);
		
	}
	
	public List<Warehouse> queryByInWarehouse(String ids){
		 String sql = "from Warehouse where id in(" + ids+ ")";
			return EntityManager.getAllByHql(sql);
	 }
	
	public Warehouse getWhBySiName(String siname) throws Exception{
		String hql="select w from Warehouse w, Organ o, CustomerMatchOrder c where w.makeorganid = o.id and o.organname = c.organname and c.siname = '" + siname +"'";
		return (Warehouse)EntityManager.find(hql);
	}
	
	public Warehouse getWhById(String id) throws Exception{
		String hql="from Warehouse w where w.id = '"+id+"' ";
		return (Warehouse)EntityManager.find(hql);
	}
	
	/**
	 * 检查收货仓库是否直属于收货机构
	 * @param userid
	 * @return
	 */
	public boolean isDirectWarehouse(String organId,
			String warehouseId) {
		String sql = "select count(w.id) from warehouse w inner join organ o on w.makeorganid = o.id where w.nccode = '" + warehouseId + "' and o.oecode = '" + organId + "'";
		return EntityManager.getRecordCountBySql(sql) > 0;
	}
	
	/**
	 * @author jason.huang
	 * @param s whereSql
	 * 按照一定条件获取采集器仓库号信息
	 */
	public List selectWareHouse(String whereSql) throws Exception {
		String hql = " from Warehouse ";
		return EntityManager.getAllByHql(hql);
		}
	/**
	 * @author jason.huang
	 * @param s whereSql
	 * 根据SAP仓库编号获取仓库信息
	 */
	public Warehouse getWarehouseByNCode(String nccode) throws NotExistException {
		String sql = " from Warehouse where useflag =1 and nccode='" + nccode+"' order by id asc";
		Warehouse temp = (Warehouse) EntityManager.find(sql);
		if (temp == null) {
			throw new NotExistException("基础数据错误，找不到相对应的仓库");
		}
		return temp;
	}
	/**
	 * @author ryan.xi
	 * @param oid
	 * 根据机构编号获取可用的仓库信息
	 */
	public Warehouse getAvaiableWarehouseByOID(String oid)throws NotExistException{
		String sql=" from Warehouse as w where w.useflag =1 and w.makeorganid='"+oid+"' order by w.id asc";
		Warehouse temp =  (Warehouse)EntityManager.find(sql);
		if (temp == null) {
			throw new NotExistException("基础数据错误，编号为"+ oid +"的收货机构不存在可用仓库");
		}
		return temp;
	}

	public boolean isWarehousExists(String id, String warehouseName,
			String warehouseNcCode) {
		String sql = "";
		if(!StringUtil.isEmpty(warehouseNcCode)){
			sql = "select count(id) from warehouse where makeorganid ='"+id+"' and (warehousename = '" + warehouseName + "' or nccode = '"+warehouseNcCode+"')";
		} else {
			sql = "select count(id) from warehouse where makeorganid ='"+id+"' and warehousename = '" + warehouseName + "'";
		}
		return EntityManager.getRecordCountBySql(sql) > 0;
		
	}
	
	public List<Warehouse> queryByInOrganId(String ids){
		 String sql = "from Warehouse where makeorganid in(" + ids+ ") order by id desc";
			return EntityManager.getAllByHql(sql);
	 }
	
	public Warehouse getRuleUserWarehouse(String oid, int userId)throws Exception{
		String sql=" from Warehouse as w where w.makeorganid='"+oid+"' and id in (select warehouseId from RuleUserWh where userId ="+userId+") and w.useflag = 1 order by id ";
		return (Warehouse)EntityManager.find(sql);
	}
	
	public List<Warehouse> getWarehouseByIds(String ids) {
		String sql = " from Warehouse where id in (" + ids+")";
		return EntityManager.getAllByHql(sql);
	}
	
	public static void main(String[] args) throws Exception {
		AppWarehouse apc = new AppWarehouse();
		Object result = apc.getWarehouseListByOID("00000001");
//		apc.updFirstSearch("2018-04-24 19:00:01","0194127703767");
//		HibernateUtil.commitTransaction();
		System.out.println("--------result:"+result);
	}
}
