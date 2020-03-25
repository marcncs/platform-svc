package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap; 
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppTakeTicketIdcode {

	public List searchTakeTicketIdcode(HttpServletRequest request, int pagesize, String pWhereClause)
			throws Exception {
		String hql = " from TakeTicketIdcode  " + pWhereClause + " order by id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public void addTakeTicketIdcode(TakeTicketIdcode pii) throws Exception {
		EntityManager.save(pii);
	}

	public void updTakeTicketIdcode(TakeTicketIdcode pii) throws Exception {
		EntityManager.update(pii);
	}

	public void updTakeTicketIdcodeBillNo(String billno, String truebillno) throws Exception {
		String sql = "update take_ticket_idcode set TTID='" + truebillno + "' where TTID='"
				+ billno + "'";
		EntityManager.updateOrdelete(sql);
	}

	public TakeTicketIdcode getTakeTicketIdcodeById(Long id) throws Exception {
		String sql = "from TakeTicketIdcode where id=" + id;
		return (TakeTicketIdcode) EntityManager.find(sql);
	}

	public void delTakeTicketIdcodeById(long id) throws Exception {
		String sql = "delete from take_ticket_idcode where id=" + id;
		EntityManager.updateOrdelete(sql);
	}

	/**
	 * 将ttid的条码复制到单据条码表中
	 * 
	 * @param bsort
	 * @param ttid
	 * @param billid
	 * @throws Exception
	 */
	public void addIdcodeToOrder(int bsort, String ttid, String billid) throws Exception {
		String tablename = Constants.TT_IDCODE_TABLE[bsort];
		String column = Constants.TT_MAIN_COLUMN[bsort];
		String sql = "insert into  "
				+ tablename
				+ " (id,"
				+ column
				+ ",productid,isidcode,warehousebit,batch,producedate,vad,unitid,quantity,packquantity,lcode,startno,endno,idcode ) "
				+ "  select (select nvl(max(id),1) from "
				+ tablename
				+ ")+ROWNUM,'"
				+ billid
				+ "',productid,isidcode,warehousebit,batch,producedate,vad,unitid,quantity,packquantity,lcode,startno,endno,idcode "
				+ " from take_ticket_idcode where ttid = '" + ttid + "'";
		EntityManager.updateOrdelete(sql);
		// 更新make_conf表
		AppMakeConf appMakeConf = new AppMakeConf();
		appMakeConf.updMakeConf(tablename, tablename);
	}
	
	/**
	 * 将ttid的条码复制到单据条码表中
	 * 
	 * @param bsort
	 * @param ttid
	 * @param billid
	 * @throws Exception
	 */
	public void addIdcodeToStockAlterMoveIdcode(int bsort, String ttid, String billid) throws Exception {
		String tablename = Constants.TT_IDCODE_TABLE[bsort];
		String column = Constants.TT_MAIN_COLUMN[bsort];
		String sql = "insert into  "
				+ tablename
				+ " (id,"
				+ column
				+ ",productid,isidcode,warehousebit,batch,producedate,vad,unitid,quantity,packquantity,lcode,startno,endno,idcode ) "
				+ "  select seq_stock_alter_move_idcode.nextval,'"
				+ billid
				+ "',productid,isidcode,warehousebit,batch,producedate,vad,unitid,quantity,packquantity,lcode,startno,endno,idcode "
				+ " from take_ticket_idcode where ttid = '" + ttid + "'";
		EntityManager.updateOrdelete(sql);
		// 更新make_conf表
		AppMakeConf appMakeConf = new AppMakeConf();
		appMakeConf.updMakeConf(tablename, tablename);
	}
	

	public void delTakeTicketIdcodeByTiid(String ttid) throws Exception {
		updIsUseOut(ttid, 1, 0);
		String sql = "delete from take_ticket_idcode where ttid='" + ttid + "'";
		EntityManager.updateOrdelete(sql);
	}

	public void updIsUseOut(String ttid, int isuse, int isout) throws Exception {
		String sql = "update idcode ic  set isuse="
				+ isuse
				+ ",isout="
				+ isout
				+ " where exists ( select id from take_ticket_idcode tti  where ic.idcode = tti.idcode and ttid = '"
				+ ttid + "')";
		EntityManager.updateOrdelete(sql);
	}

	public void delTakeTicketIdcodeByPid(String productid, String ttid, String batch)
			throws Exception {
		String sql = "delete from take_ticket_idcode where productid='" + productid
				+ "' and ttid='" + ttid + "' and batch='" + batch + "'";
		EntityManager.updateOrdelete(sql);

	}

	public List getTakeTicketIdcodeByttid(String ttid) throws Exception {
		String sql = "from TakeTicketIdcode where  ttid='" + ttid + "'";
		return EntityManager.getAllByHql(sql);
	}

	// 通过ttid和Idcode得到TakeTicketIdcode
	public TakeTicketIdcode getTakeTicketIdcodeByttidAndIdcode(String ttid, String idcode)
			throws Exception {
		String sql = "from TakeTicketIdcode where  ttid='" + ttid + "' and idcode='" + idcode + "'";
		return (TakeTicketIdcode) EntityManager.find(sql);
	}

	public List getTakeTicketIdcodeByttid(String ttid, int isidcode) throws Exception {
		String sql = "from TakeTicketIdcode where  ttid='" + ttid + "' and isidcode=" + isidcode;
		return EntityManager.getAllByHql(sql);
	}

	public List getTakeTicketIdcodeByPidBatch(String productid, String ttid) throws Exception {
		String sql = "from TakeTicketIdcode where productid='" + productid + "' and ttid='" + ttid
				+ "'";
		return EntityManager.getAllByHql(sql);
	}

	public List getTakeTicketIdcodeByPidBatch(String productid, String ttid, int isidcode)
			throws Exception {
		String sql = "from TakeTicketIdcode where productid='" + productid + "' and ttid='" + ttid
				+ "' and isidcode=" + isidcode;
		return EntityManager.getAllByHql(sql);
	}

	public List getTakeTicketIdcodeByPidBatch(String productid, String ttid, int isidcode,
			String batch) throws Exception {
		String sql = "from TakeTicketIdcode where productid='" + productid + "' and ttid='" + ttid
				+ "' and isidcode=" + isidcode + " and batch='" + batch + "'";
		return EntityManager.getAllByHql(sql);
	}

	public TakeTicketIdcode getTakeTicketIdcodeByidcode(String productid, String ttid, String idcode)
			throws Exception {
		String sql = " from TakeTicketIdcode where productid='" + productid + "' and ttid='" + ttid
				+ "' and idcode='" + idcode + "'";
		return (TakeTicketIdcode) EntityManager.find(sql);
	}

	public TakeTicketIdcode getTakeTicketIdcodeByttidAndidcode(String ttid, String idcode)
			throws Exception {
		String sql = " from TakeTicketIdcode where  ttid='" + ttid + "' and idcode='" + idcode
				+ "'";
		return (TakeTicketIdcode) EntityManager.find(sql);
	}

	/**
	 * 根据idcode找到所有的出库记录
	 * 
	 * @param idcode
	 * @return
	 * @throws Exception
	 */
	public List<TakeTicketIdcode> getTakeTicketIdcodeListByIdcode(String idcode) throws Exception {
		String sql = " from TakeTicketIdcode where idcode='" + idcode + "' order by makedate desc";
		return (List<TakeTicketIdcode>) EntityManager.getAllByHql(sql);
	}

	public double getQuantitySumByttidProductid(String productid, String ttid, String batch)
			throws Exception {
		String sql = "select sum(pii.quantity*f.xquantity) from TakeTicketIdcode as pii,FUnit as f "
				+ "where pii.productid=f.productid and pii.unitid=f.funitid and pii.productid='"
				+ productid + "' and pii.ttid='" + ttid + "'";// and
		// pii.batch='"+batch+"'";
		return EntityManager.getdoubleSum(sql);
	}

	public double getPackQuantitySum(String productid, String ttid) throws Exception {
		String sql = "select sum(pii.packquantity) from TakeTicketIdcode as pii "
				+ "where  pii.productid='" + productid + "' and pii.ttid='" + ttid + "'";
		return EntityManager.getdoubleSum(sql);
	}

	public double getPackQuantitySum2(String productid, String ttid) throws Exception {
		String sql = "select sum(pii.packquantity*f.xquantity) from TakeTicketIdcode as pii,FUnit as f "
				+ "where pii.productid=f.productid and pii.unitid=f.funitid and pii.productid='"
				+ productid + "' and pii.ttid='" + ttid + "'";
		return EntityManager.getdoubleSum(sql);
	}

	public TakeTicketIdcode getTakeTicketIdcodeByIdcodeAndWid(String idcode, String warehouseid)
			throws Exception {
		String sql = "from TakeTicketIdcode where  idcode='" + idcode
				+ "' and isidcode=1 and warehousefromid=" + warehouseid;
		return (TakeTicketIdcode) EntityManager.find(sql);
	}

	public double getQuantitySumByttidProductidBatchBit(String productid, String ttid,
			String batch, String warehouseBit) throws Exception {
		String sql = "select sum(pii.quantity*f.xquantity) from TakeTicketIdcode as pii,FUnit as f "
				+ "where pii.productid=f.productid and pii.unitid=f.funitid and pii.productid='"
				+ productid
				+ "' and pii.ttid='"
				+ ttid
				+ "' and pii.batch='"
				+ batch
				+ "' and pii.warehousebit ='" + warehouseBit + "'";
		return EntityManager.getdoubleSum(sql);
	}

	public String getTotalkgquantityByttidAndpid(String ttid, String pid)
			throws HibernateException, SQLException {
		String hql = "select sum(packquantity) as packquantity   from take_ticket_idcode tti join product p on tti.productid=p.id where  tti.ttid='"
				+ ttid + "' and tti.productid='" + pid + "' and p.cartonscanning = 1";
		String quantity = "";
		Map map = (Map) EntityManager.jdbcquery(hql).get(0);
		if (map.get("packquantity") != null) {
			quantity = (String) map.get("packquantity");
		}
		return quantity;
	}

	public List queryTotalkgquantityByttidAndpid(String pWhereClause) throws HibernateException,
			SQLException {
		String hql = "select ttid,productid,sum(packquantity) as packquantity from take_ticket_idcode "
				+ "where ttid in (select tt.id from take_ticket as tt,take_ticket_detail as  ttd "
				+ pWhereClause + "  group by tt.id) group by ttid,productid ";

		return EntityManager.jdbcquery(hql);
	}

	public List getTotalkgquantityByWarehouseid(String warehouseid, String pWhereClause)
			throws HibernateException, SQLException {
		String sql = "select ttd.productid, sum(ttd.packquantity) as packquantity "
				+ "from take_ticket tt, take_ticket_idcode ttd " + pWhereClause + " "
				+ "and tt.warehouseid = '" + warehouseid + "' " + "group by ttd.productid";
		return EntityManager.jdbcquery(sql);
	}

	public String getTotalkgquantityBypidAndWarehouseid(String warehouseid, String pid,
			String whereSql) throws HibernateException, SQLException {
		String where = " where ttd.productid='" + pid + "' and tt.warehouseid='" + warehouseid
				+ "' and   ttd.ttid=tt.id and tt.isaudit=1 and " + whereSql + "";
		where = DbUtil.getWhereSql(where);
		String hql = "select sum(packquantity) as packquantity   from take_ticket_idcode ttd, take_ticket tt "
				+ where + " ";
		String quantity = "";
		Map map = (Map) EntityManager.jdbcquery(hql).get(0);
		if (map.get("packquantity") != null) {
			quantity = (String) map.get("packquantity");
		}
		return quantity;
	}

	public List<TakeTicketIdcode> getTakeTicketIdcodesByttids(String ttids) throws Exception {
		String sql = "from TakeTicketIdcode where  ttid in (" + ttids + ")";
		return EntityManager.getAllByHql(sql);
	}

	public List<Map> getTtdGroupTTI(String ttid) throws Exception {
		String sql = "select ttid, unitid,productid,batch,count(*) total from take_ticket_idcode where ttid ='"
				+ ttid + "' group by ttid, unitid,productid,batch";
		return EntityManager.jdbcquery(sql);
	}

	public List<Map> getTtdGroupTTIFromPrintJob(String ttid) throws Exception {
		String sql = "select tti.ttid, tti.unitid, tti.productid, pj.PACKAGING_DATE || pj.MATERIAL_BATCH_NO as batch, count(*) total from take_ticket_idcode tti "
				+ "left join carton_code cc on cc.carton_code = tti.idcode left join print_job pj on pj.PRINT_JOB_ID = cc.PRINT_JOB_ID where tti.ttid = '"
				+ ttid + "' group by tti.ttid, tti.unitid, tti.productid, pj.PACKAGING_DATE || pj.MATERIAL_BATCH_NO";
		return EntityManager.jdbcquery(sql);
	}
	
	/**
	 * @author jason.huang
	 * @param productId
	 * @return
	 * @throws Exception
	 */

	public List getTakeTicketIdcodeByProductId(String productId) throws Exception {
		String sql = "from TakeTicketIdcode where  productid='" + productId + "'";
		return EntityManager.getAllByHql(sql);
	}

	/**
	 * @author jason.huang
	 * @param cartoncode
	 * @return
	 * @throws Exception
	 */

	public List getTakeTicketIdcodeByIdCode(String cartoncode) throws Exception {
		Map<String,Object> param = new HashMap<>();
		param.put("cartoncode", cartoncode);
		String sql = "from TakeTicketIdcode where  idcode=:cartoncode";
		return EntityManager.getAllByHql(sql, param);
	}
	
	/**
	 * @author jason.huang
	 * @param cartoncode
	 * @return
	 * @throws Exception
	 */

	public List getTakeTicketIdcodeByIdCode(String cartoncode,String primarycode) throws Exception {
		Map<String,Object> param = new HashMap<>();
		param.put("cartoncode", cartoncode);
		param.put("primarycode", primarycode);
		String sql = " from TakeTicketIdcode where  idcode=:cartoncode or idcode=:primarycode";
		return EntityManager.getAllByHql(sql, param);
	}

	/**
	 * @author jason.huang
	 * @param cartoncode
	 * @return
	 * @throws Exception
	 */

	public String getBatchByIdCode(String cartoncode) throws Exception {
		String sql = "from TakeTicketIdcode where  idcode='" + cartoncode + "'";
		List<TakeTicketIdcode> lttid = new ArrayList<TakeTicketIdcode>();
		lttid = EntityManager.getAllByHql(sql);
		String batchString = "";
		if (lttid.size() > 0) {
			batchString = lttid.get(0).getBatch();
		}
		return batchString;
	}

	public Integer getCodeQuantity(String billNo, String productId) {
		String sql = "select count(*) from TAKE_TICKET_IDCODE where ttid = '"+billNo+"' and productid = '" + productId+"' and isidcode=1";
		return EntityManager.getRecordCountBySql(sql);
		
	}
	
	public String getTotalQuantityByOidAndPid(String oid, String pid)
			throws HibernateException, SQLException {
		String hql = "select sum(packquantity) as quantity from take_ticket tt join take_ticket_idcode tti on tti.ttid = tt.id  where  tti.productid='"
				+ pid + "' and tt.oid ='" + oid + "'";
		String quantity = "";
		Map map = (Map) EntityManager.jdbcquery(hql).get(0);
		if (map.get("quantity") != null) {
			quantity = (String) map.get("quantity");
		}
		return quantity;
	}

	public void addTakeTicketIdcodes(List<TakeTicketIdcode> ttiList) {
		EntityManager.batchSave(ttiList);
	}

	public Integer getRecordCountByTtid(String ttids) {
		String hql = "select count(*) from TakeTicketIdcode where ttid in ('"+ttids+"')";
		return EntityManager.getCount(hql);
	}
	
	public List<TakeTicketIdcode> getTakeTicketIdcodeByPidTtid(String productid, String ttid, String orderBy) throws Exception {
		String sql = "from TakeTicketIdcode where productid='" + productid + "' and ttid='" + ttid
				+ "' " + orderBy;
		return EntityManager.getAllByHql(sql);
	}
	
	/**
	 * 根据单据获取条码包装数量(最小单位)
	 */
	public Map<String, Double>  getTtidcodePackQuantity(String ttid)throws Exception{
		String sql="select MAX(PRODUCTID) pid,SUM(NVL(PACKQUANTITY,0)) quantity from TAKE_TICKET_IDCODE " +
				"  where ttid = '" + ttid + "' GROUP BY PRODUCTID";
		List<Map<String, String>> list = EntityManager.jdbcquery(sql);
		Map<String, Double> resultMap = new HashMap<String, Double>();
		for(Map<String, String> map : list){
			String pid = map.get("pid");
			Double quantity = Double.valueOf(map.get("quantity"));
			resultMap.put(pid, quantity);
		}
		return resultMap;
	}

	public String getTotalQuantityByOidWidAndDate(String organId, String warehouseid, String beginDate,
			String endDate, Integer bsort) throws HibernateException, SQLException {
//		String hql = "select sum(quantity) as quantity from take_ticket tt join take_ticket_idcode tti on tti.ttid = tt.id  where tt.oid ='"
//			+ organId
//			+ "' and tt.auditdate >=to_date('"
//			+ beginDate
//			+ "','yyyy-MM-dd hh24:mi:ss') and tt.auditdate < to_date('"
//			+ endDate + "','yyyy-MM-dd hh24:mi:ss') ";
		//modified by ryan 20150512
		StringBuffer hql = new StringBuffer();
		hql.append("select sum(quantity) as quantity from take_ticket tt join take_ticket_idcode tti on tti.ttid = tt.id  and ");
		if(bsort != null) {
			//退货单(包括渠道退货和工厂退回)
			if(bsort == 7) {
				if(!StringUtil.isEmpty(warehouseid)) {
					hql.append("\r\n tt.bsort = 7 and ((tt.oid='"+organId+"' and tt.warehouseid = '"+warehouseid+"') or (tt.inoid='"+organId+"' and tt.inwarehouseid = '"+warehouseid+"'))");
				} else {
					hql.append("\r\n tt.bsort = 7 and (tt.oid='"+organId+"' or tt.inoid='"+organId+"')");
				}
				
			} else {
				hql.append("\r\n tt.bsort = "+bsort+" and tt.oid='"+organId+"'");
				if(!StringUtil.isEmpty(warehouseid)) {
					hql.append("\r\n and tt.warehouseid = '"+warehouseid+"'");
				}
			}
			
		} else {
			if(!StringUtil.isEmpty(warehouseid)) {
				hql.append("\r\n ((tt.oid='"+organId+"' and tt.warehouseid = '"+warehouseid+"') or (tt.bsort = 7 and tt.inoid='"+organId+"' and tt.inwarehouseid = '"+warehouseid+"'))");
			} else {
				hql.append("\r\n (tt.oid='"+organId+"' or (tt.bsort = 7 and tt.inoid='"+organId+"'))");
			}
		}
		hql.append("\r\n  join product p on p.id = tti.productid and p.cartonscanning = 1 and p.useflag = 1 ");
		hql.append("\r\n  and tt.auditdate >=to_date('" + beginDate + "','yyyy-MM-dd hh24:mi:ss') and tt.auditdate < to_date('" + endDate + "','yyyy-MM-dd hh24:mi:ss') ");
		
		String quantity = "";
		Map map = (Map) EntityManager.jdbcquery(hql.toString()).get(0);
		if (map.get("quantity") != null) {
			quantity = (String) map.get("quantity");
		}
		return quantity;
	}
	
	
	public void addIdcode(String ttid,String cartoncode) throws Exception {
		String sql = " insert into TAKE_TICKET_IDCODE (id,ttid,PRODUCTID,isidcode,warehousebit,batch,unitid,quantity,idcode,makedate)  "
				+ " select TTidcode_ID_OLD_SEQ.nextval,'"+ttid+"' ,cc.product_id,1,'000' ,pj.batch_number,2,1,'"+cartoncode+"',sysdate "
				+ " from CARTON_CODE cc,PRINT_JOB pj "
				+ " where cc.CARTON_CODE='"+cartoncode+"' and cc.PRINT_JOB_ID=pj.print_job_id ";
		EntityManager.updateOrdelete(sql);
	}

	public Integer getCodeQuantityByBillNo(String productId, String billNo) {
		String sql = "select count(*) from TAKE_TICKET_IDCODE where ttid = (select id from TAKE_TICKET where billNo = '"+billNo+"') and productid = '" + productId+"' and isidcode=1";
		return EntityManager.getRecordCountBySql(sql);
	}
	
	public void updbonusTakeIdcode(String ttid, String idcode, double bonuspoint) throws Exception {
		String sql = "update take_ticket_idcode   set BONUSPOINT="+bonuspoint + 
							"   where TTID='" + ttid +"' and IDCODE='" +idcode+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	/**
	 * 根据idcode找到发货
	 * 
	 * @param idcode
	 * @return
	 * @throws Exception
	 */
	public List<TakeTicket> getTakeTicketListByIdcode(String idcode,String oid) throws Exception {
		String sql = "select tt.* from TakeTicket tt ,TakeTicketIdcode tti where tti.ttid=tt.id and tti.idcode='"+idcode+
				"' and tt.oid='"+oid+"' and tt.bsort='1'  order by makedate desc";
		return (List<TakeTicket>) EntityManager.getAllByHql(sql);
	}

	public void updTakeTicketIdcodeBonus(String billNo, String productId,
			double codeBonus) throws HibernateException, SQLException, Exception {
		String sql = "update TAKE_TICKET_IDCODE set BONUSPOINT = "+codeBonus + 
				" where TTID = (select id from TAKE_TICKET where BILLNO = '"+billNo+"')" +
				" and PRODUCTID = '"+productId+"'";
		EntityManager.updateOrdelete(sql);
	}

	public Map<String, String> getIdcodeBonusMapForReturn(String billNo) throws HibernateException, SQLException {  
		Map<String, String> bonusMap = new HashMap<String, String>();
		String sql = "select tti.idcode, tti.bonusPoint from TAKE_TICKET_IDCODE tti join ( " +
				"select max(tti.id) id,tti.idcode from ORGAN_WITHDRAW_IDCODE owi " +
				"join TAKE_TICKET_IDCODE tti on owi.idcode = tti.idcode and owi.owid = '"+billNo+"' and BONUSPOINT > 0 " +
				"join TAKE_TICKET tt on tt.id = tti.ttid " +
				"join ORGAN o on o.id = tt.oid and o.organType = 2 and organmodel = 2 and o.iskeyretailer = 1 " +
				"group by tti.idcode" +
				") temp on tti.id = temp.id";
		List<Map<String,String>> codes = EntityManager.jdbcquery(sql);
		for(Map<String,String> code : codes) {
			bonusMap.put(code.get("idcode"), code.get("bonuspoint"));
		} 
		return bonusMap;
	}

	public List<TakeTicketIdcode> getRemovedIdcode(String billNo) { 
		String hql = "from TakeTicketIdcode where ttid = (select id from TakeTicket where billno = '"+billNo+"') and idcode not in (select idcode from StockAlterMoveIdcode where samid = '"+billNo+"')";
		return EntityManager.getAllByHql(hql);
	}

	public void deRemovedIdcodeFromTakeTicketIdcode(String billNo) throws HibernateException, SQLException, Exception {
		String sql = "delete from TAKE_TICKET_IDCODE " +
			" where ttid = (select id from TAKE_TICKET where BILLNO = '"+billNo+"') " +
			" and idcode not in (select idcode from STOCK_ALTER_MOVE_IDCODE where SAMID = '"+billNo+"')";
		EntityManager.updateOrdelete(sql);
	}

	public void updBySql(String sql) throws Exception {
		EntityManager.executeUpdate(sql);
	}
	
	/**
	 * 查询条码数量
	 * @param ttid
	 * @return
	 * @throws Exception
	 */
	public double getQuantityByTtid(String ttid) throws Exception{
		String sql = "select sum(TTI.QUANTITY) AS sum_quantity from TAKE_TICKET tt,TAKE_TICKET_DETAIL ttd, TAKE_TICKET_IDCODE tti where tt.id=ttd.TTID and ttd.TTID = TTI.TTID and ttd.PRODUCTID = TTI.PRODUCTID"+
				"and tt.id='" + ttid +"'";
		return EntityManager.getdoubleSum(sql);
	}
}
