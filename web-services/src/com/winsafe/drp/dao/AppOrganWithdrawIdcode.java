package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppOrganWithdrawIdcode {

	public List searchOrganWithdrawIdcode(HttpServletRequest request, int pagesize, String pWhereClause)
	throws Exception {
		String hql = " from OrganWithdrawIdcode  " + pWhereClause + " order by id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	public void addOrganWithdrawIdcode(OrganWithdrawIdcode pii) throws Exception{		
		EntityManager.save(pii);		
	}
	
	public void updOrganWithdrawIdcode(OrganWithdrawIdcode pii) throws Exception{		
		EntityManager.update(pii);		
	}
	
	public void updOrganWithdrawIdcodeBillNo(String billno,String truebillno) throws Exception{		
		String sql="update organ_withdraw_idcode set owid='"+truebillno+"' where owid='"+billno+"'";
		EntityManager.updateOrdelete(sql);			
	}
	
	public OrganWithdrawIdcode getOrganWithdrawIdcodeById(Long id) throws Exception{
		String sql = "from OrganWithdrawIdcode where id="+id;
		return (OrganWithdrawIdcode)EntityManager.find(sql);
		}
	
	public void delOrganWithdrawIdcodeById(long id) throws Exception{		
		String sql="delete from organ_withdraw_idcode where id="+id;
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delOrganWithdrawIdcodeByPiid(String owid) throws Exception{		
		String sql="delete from organ_withdraw_idcode where owid='"+owid+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delOrganWithdrawIdcodeByPid(String productid, String owid, String batch) throws Exception{		
		String sql="delete from organ_withdraw_idcode where productid='"+productid+"' and owid='"+owid+"' and batch='"+batch+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getOrganWithdrawIdcodeByowid(String owid) throws Exception{
		String sql = "from OrganWithdrawIdcode where  owid='"+owid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getOrganWithdrawIdcodeByowid(String owid, int isidcode) throws Exception{
		String sql = "from OrganWithdrawIdcode where  owid='"+owid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public List getOrganWithdrawIdcodeByPidBatch(String productid, String owid) throws Exception{
		String sql = "from OrganWithdrawIdcode where productid='"+productid+"' and owid='"+owid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getOrganWithdrawIdcodeByPidBatch(String productid, String owid, int isidcode, String batch) throws Exception{
		String sql = "from OrganWithdrawIdcode where productid='"+productid+"' and owid='"+owid+"' and isidcode="+isidcode+" and batch='"+batch+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public OrganWithdrawIdcode getOrganWithdrawIdcodeByidcode(String productid, String owid, String idcode) throws Exception{
		//String sql = " from OrganWithdrawIdcode where productid='"+productid+"' and owid='"+owid+"' and idcode='"+idcode+"'";
		String sql = " from OrganWithdrawIdcode where productid='"+productid+"' and idcode='"+idcode+"'";
		return (OrganWithdrawIdcode)EntityManager.find(sql);
	}
	
	/**
	 * 根据条码查找所有的退货条码
	 * @param idcode
	 * @return 所有条码集合
	 * @throws Exception
	 */
	public List<OrganWithdrawIdcode> getOrganWithdrawIdcodesByIdcode(String idcode) throws Exception{
		//String sql = " from OrganWithdrawIdcode where productid='"+productid+"' and owid='"+owid+"' and idcode='"+idcode+"'";
		String sql = " from OrganWithdrawIdcode where idcode='"+idcode+"'";
		return (List<OrganWithdrawIdcode>)EntityManager.getAllByHql(sql);
	}

	
	public double getQuantitySumByowidProductid(String productid, String owid, String batch) throws Exception{
		String sql = "select sum(pii.quantity*f.xquantity) from OrganWithdrawIdcode as pii,FUnit as f "+
		"where pii.productid=f.productid and pii.unitid=f.funitid and pii.productid='"+productid+"' and pii.owid='"+owid+"'";// and pii.batch='"+batch+"'";
		return EntityManager.getdoubleSum(sql);
	}
	public double getQuantitySumByowidProductid2(String productid, String owid) throws Exception{
		String sql = "select sum(pii.packquantity) from OrganWithdrawIdcode as pii  "+
		" where   pii.productid='"+productid+"' and pii.owid='"+owid+"'";
		return EntityManager.getdoubleSum(sql);
	}
	public List searchPlantWithdrawIdcode(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("\r\n select owi.idcode, owi.unitid, owi.quantity, owi.warehousebit, owi.batch, owi.producedate, owi.packquantity,temp_1.nccode, temp_1.makedate as movedate, temp_2.id, temp_2.receiveorganidname from TAKE_TICKET_IDCODE owi ");
		sql.append("\r\n left join (");
		sql.append("\r\n select owi.idcode, sam.nccode, sam.makedate from TAKE_TICKET_IDCODE owi");
		sql.append("\r\n join take_ticket_IDCODE sami on owi.idcode = sami.idcode  ");
		sql.append("\r\n join take_ticket sam on sam.id = sami.ttid and sam.bsort = 1 and sam.nccode is not NULL  ");
		sql.append("\r\n join organ o on o.id = sam.oid ");
		sql.append("\r\n and o.organtype = 1 ").append(whereSql);
		sql.append("\r\n ) temp_1 on owi.idcode = temp_1.idcode");
		sql.append("\r\n left join ( ");
		sql.append("\r\n select owi.idcode, sam.id, sam.receiveorganidname  from TAKE_TICKET_IDCODE owi");
		sql.append("\r\n join STOCK_ALTER_MOVE_IDCODE sami on owi.idcode = sami.idcode ");
		sql.append("\r\n join STOCK_ALTER_MOVE sam on sam.id = sami.samid ");
		sql.append("\r\n join organ o on o.id = sam.outorganid ");
		sql.append("\r\n and o.organtype = 2 and o.organmodel = 1 ").append(whereSql);
		sql.append("\r\n  ) temp_2 on owi.idcode = temp_2.idcode ");
		sql.append(whereSql);
		return PageQuery.jdbcSqlserverQuery(request, "idcode", sql.toString(), pagesize);
	}
	
	public List searchPlantWithdrawIdcode(String whereSql) throws HibernateException, SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("\r\n select owi.idcode, owi.unitid, owi.quantity, owi.warehousebit, owi.batch, owi.producedate, owi.packquantity,temp_1.nccode, temp_1.makedate as movedate, temp_2.id, temp_2.receiveorganidname,temp_3.billno smid, temp_3.transferorganname from TAKE_TICKET_IDCODE owi ");
		sql.append("\r\n left join (");
		sql.append("\r\n select owi.idcode, sam.nccode, sam.makedate from TAKE_TICKET_IDCODE owi");
		sql.append("\r\n join take_ticket_IDCODE sami on owi.idcode = sami.idcode  ");
		sql.append("\r\n join take_ticket sam on sam.id = sami.ttid and sam.bsort = 1 and sam.nccode is not NULL ");
		sql.append("\r\n join organ o on o.id = sam.oid ");
		sql.append("\r\n and o.organtype = 1 ").append(whereSql);
		sql.append("\r\n ) temp_1 on owi.idcode = temp_1.idcode");
		sql.append("\r\n left join ( ");
		sql.append("\r\n select owi.idcode, sam.id, sam.receiveorganidname  from TAKE_TICKET_IDCODE owi");
		sql.append("\r\n join STOCK_ALTER_MOVE_IDCODE sami on owi.idcode = sami.idcode ");
		sql.append("\r\n join STOCK_ALTER_MOVE sam on sam.id = sami.samid ");
		sql.append("\r\n join organ o on o.id = sam.outorganid ");
		sql.append("\r\n and o.organtype = 2 and o.organmodel = 1 ").append(whereSql);
		sql.append("\r\n  ) temp_2 on owi.idcode = temp_2.idcode ");
		sql.append("\r\n left join ( ");
		sql.append("\r\n select owi.idcode,tt.billno, o.organname transferorganname from TAKE_TICKET_IDCODE owi ");
		sql.append("\r\n join TAKE_TICKET_IDCODE smtti on owi.idcode = smtti.idcode  ");
		sql.append("\r\n join TAKE_TICKET tt on tt.id = smtti.ttid and tt.bsort=2 ");
		sql.append("\r\n join ORGAN o on o.id = tt.oid  ");
		sql.append("\r\n and o.organtype = 2 and o.organmodel = 1 ").append(whereSql);
		sql.append("\r\n  ) temp_3 on owi.idcode = temp_3.idcode   ");
		sql.append(whereSql);
		return EntityManager.jdbcquery(sql.toString());
	}
	
	public static void main(String[] args) {
		StringBuffer sql = new StringBuffer();
		sql.append("\r\n select owi.idcode, owi.unitid, owi.quantity, owi.warehousebit, owi.batch, owi.producedate, owi.packquantity,temp_1.nccode, temp_1.makedate as movedate, temp_2.id, temp_2.receiveorganidname from ORGAN_WITHDRAW_IDCODE owi ");
		sql.append("\r\n left join (");
		sql.append("\r\n select owi.idcode, sam.nccode, sam.makedate from ORGAN_WITHDRAW_IDCODE owi");
		sql.append("\r\n join take_ticket_IDCODE sami on owi.idcode = sami.idcode  ");
		sql.append("\r\n join take_ticket sam on sam.id = sami.ttid and sam.bsort = 1 and sam.nccode is not NULL  ");
		sql.append("\r\n join organ o on o.id = sam.oid ");
		sql.append("\r\n and o.organtype = 1 ");
		sql.append("\r\n ) temp_1 on owi.idcode = temp_1.idcode");
		sql.append("\r\n left join ( ");
		sql.append("\r\n select owi.idcode, sam.id, sam.receiveorganidname  from ORGAN_WITHDRAW_IDCODE owi");
		sql.append("\r\n join STOCK_ALTER_MOVE_IDCODE sami on owi.idcode = sami.idcode ");
		sql.append("\r\n join STOCK_ALTER_MOVE sam on sam.id = sami.samid ");
		sql.append("\r\n join organ o on o.id = sam.outorganid ");
		sql.append("\r\n and o.organtype = 2 and o.organmodel = 1 ");
		sql.append("\r\n  ) temp_2 on owi.idcode = temp_2.idcode ");
		System.out.println(sql.toString());
	}
}
