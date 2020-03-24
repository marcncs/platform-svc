package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

/**
 * @author : jerry
 * @version : 2009-8-26 下午03:49:02
 * www.winsafe.cn
 */
public class AppOrganWithdraw {

	
	public List<OrganWithdraw> getOrganWithdrawAll(HttpServletRequest request,Integer pageSize, String whereSql)throws Exception{
		String hql =" from OrganWithdraw as ow " +whereSql+" order by ow.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public List<Map<String,String>> getOrganWithdrawAllList(HttpServletRequest request,Integer pageSize, String whereSql)throws Exception{
		String sql ="select ow.*,outo.organname outoname,ino.organname inoname from ORGAN_WITHDRAW ow " +
				"join organ outo on ow.PORGANID = OUTO.ID " +
				"join organ ino on ino.id=ow.receiveorganid " +whereSql;
		if(pageSize > 0) {
			return PageQuery.jdbcSqlserverQuery(request, "id desc", sql, pageSize);
		} else {
			sql+= " order by ow.id desc ";
			return EntityManager.jdbcquery(sql);
		}
		
	}
	
	public List<Map<String,String>> getOrganWithdrawAllList(HttpServletRequest request,Integer pageSize, String whereSql, Map<String, Object> param)throws Exception{
		String sql ="select ow.*,outo.organname outoname,ino.organname inoname from ORGAN_WITHDRAW ow " +
				"join organ outo on ow.PORGANID = OUTO.ID " +
				"join organ ino on ino.id=ow.receiveorganid " +whereSql;
		if(pageSize > 0) {
			return PageQuery.jdbcSqlserverQuery(request, "id desc", sql, pageSize, param);
		} else {
			sql+= " order by ow.id desc ";
			return EntityManager.jdbcquery(sql, param); 
		}
		
	}
	
	public OrganWithdraw getOrganWithdrawByID(String id)throws Exception{
		String hql = " from OrganWithdraw ow where ow.id='" + id+"'";
		return (OrganWithdraw) EntityManager.find(hql);
	}
	public void save(OrganWithdraw organWithdraw)throws Exception{
		EntityManager.save(organWithdraw);
	}
	public void update(OrganWithdraw organWithdraw)throws Exception{
		EntityManager.update(organWithdraw);
	}
	public void delete(OrganWithdraw organWithdraw)throws Exception{
		EntityManager.delete(organWithdraw);
	}
	public void updOrganWithdrawIsReceive(String id,int isreceive,int userid)throws Exception{
		String sql="update organ_withdraw set iscomplete="+isreceive+",receivedate= to_date('"+DateUtil.getCurrentDateTime()+"','yyyy-mm-dd hh24:mi:ss'),receiveid="+userid+" where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
	}
	public void updOrganWithdrawTakestatus(String id,int takestatus)throws Exception{
		String sql="update organ_withdraw set takestatus="+takestatus+" where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
	}

	public List getOrganWithdrawOrganTotal(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String hql = "select porganid,porganname,plinkman,tel,makeorganid,sum(totalsum) as totalsum from organ_withdraw "
			+ whereSql
			+ " group by porganid,porganname,plinkman,tel,makeorganid";
	return PageQuery.jdbcSqlserverQuery(request, "porganid", hql,
			pagesize);
	}

	public double getOrganWithdrawTotalSum(String whereSql) {
		String hql = "select sum(totalsum) as totalsum from OrganWithdraw "
			+ whereSql;
		return EntityManager.getdoubleSum(hql);
	}

	public List getOrganWithdrawOrganTotal(String whereSql) throws HibernateException, SQLException {
		String hql = "select porganid,porganname,plinkman,tel,makeorganid,sum(totalsum) as totalsum from organ_withdraw "
			+ whereSql
			+ " group by porganid,porganname,plinkman,tel,makeorganid";
		return EntityManager.jdbcquery(hql);
	}

	public List getOrganWithdrawProductTotal(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String hql = "select  makeorganid,productid,productname,specmode,unitid,unitprice,sum(quantity) as quantity,sum(subsum )as subsum"
			+ " from organ_withdraw ow , organ_withdraw_detail owd "
			+ whereSql +
			" group by makeorganid,productid,productname,specmode,unitid,unitprice";
	return PageQuery.jdbcSqlserverQuery(request,"makeorganid", hql, pagesize);
	}

	public List getOrganWithdrawProductTotalSum(String whereSql) throws HibernateException, SQLException {
		String hql = "select sum(quantity) as quantity, sum(subsum )as subsum"
			+ " from organ_withdraw  ow , organ_withdraw_detail owd "
			+ whereSql ;
		return EntityManager.jdbcquery(hql);
	}

	public List getOrganWithdrawProductTotal(String whereSql) throws HibernateException, SQLException {
		String hql = "select  makeorganid,productid,productname,specmode,unitid,unitprice,sum(quantity) as quantity,sum(subsum )as subsum"
			+ " from organ_withdraw ow , organ_withdraw_detail owd "
			+ whereSql +
			" group by makeorganid,productid,productname,specmode,unitid,unitprice";
		return EntityManager.jdbcquery(hql);
	}
	
	
	public List getOrganWithdrawDetail(HttpServletRequest request,
			int pagesize, String whereSql)throws Exception{
		String hql = "select ow.id, porganid,porganname,makeorganid, makedate, " +
				"productid,productname,specmode,unitid,unitprice, quantity, subsum " +
				" from organ_withdraw ow , organ_withdraw_detail owd "+whereSql;
		return PageQuery.jdbcSqlserverQuery(request, hql, pagesize);
	}

	public List getOrganWithdrawBillTotal(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String sql = "select id ,makedate,porganid,porganname,makeorganid,totalsum from organ_withdraw "
			+ whereSql ;
		return PageQuery.jdbcSqlserverQuery(request, sql, pagesize);
	}

	public List getOrganWithdrawBillTotal(String whereSql) throws Exception {
		String sql = "select id ,makedate,porganid,porganname,makeorganid,totalsum from organ_withdraw "
			+ whereSql ;
		return EntityManager.jdbcquery(sql);
	}

	public List getOrganWithdrawDetail(String whereSql) throws HibernateException, SQLException {
		String hql = "select ow.id, porganid,porganname,makeorganid, makedate, " +
		"productid,productname,specmode,unitid,unitprice, quantity, subsum " +
		" from organ_withdraw ow , organ_withdraw_detail owd "+whereSql;
		return EntityManager.jdbcquery(hql);
	}

	public List<OrganWithdraw> getOrganWithdrawAll(String whereSql) {
		String hql =" from OrganWithdraw as ow " +whereSql+" order by ow.id desc";
		return EntityManager.getAllByHql(hql);
	}

	public OrganWithdraw getOrganWithdrawByOidAndNccode(String oid, String nccode)throws Exception{
		String hql = " from OrganWithdraw ow where ow.receiveorganid='"+oid+"' and ow.nccode='" + nccode+"'";
		return (OrganWithdraw) EntityManager.find(hql);
	}

	public List<OrganWithdraw> getOrganWithdrawToBonus(String startDate, String endDate) {
		String sql = "select ow from OrganWithdraw ow,Organ o,Organ o2 where ow.porganid = o.id and o.organType = 2 and o.organModel not in (1,2) and o.isKeyRetailer = 1 " +
				"and ow.receiveorganid = o2.id and o2.organType = 2 and ((o2.organModel = 2 and o2.isKeyRetailer = 1) or o2.organModel = 1) and ow.iscomplete = 1 and ow.bonusStatus = 0 and ow.makedate >= to_date('"+startDate+"','yyyy-MM-dd hh24:mi:ss') and ow.makedate <= to_date('"+endDate+" 23:59:59','yyyy-MM-dd hh24:mi:ss')";
		return EntityManager.getAllByHql(sql);
	}
	//非关键--〉关键
	public List<OrganWithdraw> getOrganWithdrawToTBonus(String startDate, String endDate) {
		String sql = "select ow from OrganWithdraw ow,Organ o,Organ o2 where ow.porganid = o.id and o.organType = 2 and o.organModel not in (1,2) and o.isKeyRetailer <> 1 " +
				"and ow.receiveorganid = o2.id and o2.organType = 2 and o2.organModel = 2 and o2.isKeyRetailer = 1 and ow.iscomplete = 1 and ow.bonusStatus = 0 and ow.makedate >= to_date('"+startDate+"','yyyy-MM-dd hh24:mi:ss') and ow.makedate <= to_date('"+endDate+" 23:59:59','yyyy-MM-dd hh24:mi:ss')";
		return EntityManager.getAllByHql(sql);
	}
	
}
