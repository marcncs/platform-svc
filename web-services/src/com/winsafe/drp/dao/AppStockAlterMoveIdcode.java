package com.winsafe.drp.dao;

import java.sql.SQLException;
import java.util.HashMap; 
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppStockAlterMoveIdcode {

	public List searchStockAlterMoveIdcode(HttpServletRequest request, int pagesize, String pWhereClause)
	throws Exception {
		String hql = " from StockAlterMoveIdcode  " + pWhereClause + " order by id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	public void addStockAlterMoveIdcode(StockAlterMoveIdcode pii) throws Exception{		
		EntityManager.save(pii);		
	}
	
	public void updStockAlterMoveIdcode(StockAlterMoveIdcode pii) throws Exception{		
		EntityManager.update(pii);		
	}
	
	public void updStockAlterMoveIdcodeBillNo(String billno,String truebillno) throws Exception{		
		String sql="update stock_alter_move_idcode set samid='"+truebillno+"' where samid='"+billno+"'";
		EntityManager.updateOrdelete(sql);			
	}
	
	public StockAlterMoveIdcode getStockAlterMoveIdcodeById(Long id) throws Exception{
		String sql = "from StockAlterMoveIdcode where id="+id;
		return (StockAlterMoveIdcode)EntityManager.find(sql);
		}
	
	public void delStockAlterMoveIdcodeById(long id) throws Exception{		
		String sql="delete from stock_alter_move_idcode where id="+id;
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delStockAlterMoveIdcodeByPiid(String samid) throws Exception{		
		String sql="delete from stock_alter_move_idcode where samid='"+samid+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delStockAlterMoveIdcodeByPid(String productid, String samid) throws Exception{		
		String sql="delete from stock_alter_move_idcode where productid='"+productid+"' and samid='"+samid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getStockAlterMoveIdcodeBysamid(String samid) throws Exception{
		String sql = "from StockAlterMoveIdcode where  samid='"+samid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getUsefulStockAlterMoveIdcodeBysamid(String samid) throws Exception{ 
		String sql = "from StockAlterMoveIdcode where  samid='"+samid+"' and productid not in (select id from Product where useflag = 0) ";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getStockAlterMoveIdcodeBysamid(String samid, int isidcode) throws Exception{
		String sql = "from StockAlterMoveIdcode where  samid='"+samid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public List getStockAlterMoveIdcodeByPidBatch(String productid, String samid) throws Exception{
		String sql = "from StockAlterMoveIdcode where productid='"+productid+"' and samid='"+samid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getStockAlterMoveIdcodeByPidBatch(String productid, String samid, int isidcode) throws Exception{
		String sql = "from StockAlterMoveIdcode where productid='"+productid+"' and samid='"+samid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public StockAlterMoveIdcode getStockAlterMoveIdcodeByidcode(String productid, String samid, String idcode) throws Exception{
		//String sql = " from StockAlterMoveIdcode where productid='"+productid+"' and samid='"+samid+"' and idcode='"+idcode+"'";
		String sql = " from StockAlterMoveIdcode where productid='"+productid+"' and idcode='"+idcode+"'";

		return (StockAlterMoveIdcode)EntityManager.find(sql);
	}
	
	
	public double getQuantitySumBysamidProductid(String productid, String samid) throws Exception{
		String sql = "select sum(pii.quantity*f.xquantity) from StockAlterMoveIdcode as pii,FUnit as f "+
		"where pii.productid=f.productid and pii.unitid=f.funitid and pii.productid='"+productid+"' and pii.samid='"+samid+"'";
		return EntityManager.getdoubleSum(sql);
	}
	
	public double getQuantitySumBysamidProductid2(String productid, String samid) throws Exception{
		String sql = "select sum(pii.packquantity) from StockAlterMoveIdcode as pii  "+
		"where  pii.productid='"+productid+"' and pii.samid='"+samid+"'";
		return EntityManager.getdoubleSum(sql);
	}
	
	public void delStockAlterMoveIDcodeByNCcode(String nccode) throws Exception {

		String sql = "delete from stock_alter_move_idcode where Nccode='" + nccode
				+ "'";
		EntityManager.updateOrdelete(sql);

	}

	public StockAlterMoveIdcode getStockAlterMoveIdcodeByidcode(String idcode) throws Exception{
		String sql = " from StockAlterMoveIdcode where idcode='"+idcode+"'";
		return (StockAlterMoveIdcode)EntityManager.find(sql);
	}
	
	public List<StockAlterMoveIdcode> getStockAlterMoveIdcodesByidcode(String idcode) throws Exception{
		String sql = " from StockAlterMoveIdcode where idcode='"+idcode+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public void addStockAlterMoveIdcodes(Object pii) throws Exception{		
		EntityManager.batchSave(pii);		
	}
	public void delStockAlterMoveIdcodeByIdcodes(String billNo, String idcodes) throws HibernateException, SQLException, Exception {
		String sql = "delete from stock_alter_move_idcode where samid = '"+billNo+"' and idcode in ("+idcodes+")";
		EntityManager.updateOrdelete(sql); 
	}
	public List<Map<String,String>> getRemovedIdcodeById(String billNo) throws HibernateException, SQLException { 
		String sql = "select idcode from take_ticket_idcode where ttid = (SELECT id from take_ticket where billno = '"+billNo+"') and  idcode not in ( select idcode from stock_alter_move_idcode where SAMID = '"+billNo+"' )";
		return EntityManager.jdbcquery(sql);
	}
	
	public void updStockAlterMoveIdcodeBonus(String billNo, String productId,
			double codeBonus) throws HibernateException, SQLException, Exception {
		String sql = "update stock_alter_move_idcode set BONUSPOINT = "+codeBonus + 
			" where samid = '"+billNo+"'" +
			" and PRODUCTID = '"+productId+"'";
		EntityManager.updateOrdelete(sql);
	}
	public Map<String, String> getIdcodeBonusMapForReturn(String billNo) throws HibernateException, SQLException { 
		Map<String, String> bonusMap = new HashMap<String, String>();
		String sql = "select sami.idcode, sami.bonusPoint from STOCK_ALTER_MOVE_IDCODE sami join ( " +
				"select max(tti.id) id,tti.idcode from ORGAN_WITHDRAW_IDCODE owi " +
				"join STOCK_ALTER_MOVE_IDCODE tti on owi.idcode = tti.idcode and owi.owid = '"+billNo+"' " +
				"join STOCK_ALTER_MOVE tt on tt.id = tti.samid " +
				"join ORGAN o on o.id = tt.outOrganId and o.organType = 2 and ((organmodel = 2 and o.iskeyretailer = 1) OR organmodel = 1) " +
				"group by tti.idcode " +
				") temp on sami.id = temp.id ";
		List<Map<String,String>> codes = EntityManager.jdbcquery(sql);
		for(Map<String,String> code : codes) {
			bonusMap.put(code.get("idcode"), code.get("bonuspoint"));
		}
		return bonusMap;
	}
	public List<Map<String, String>> getDeliveryDetailInfo(String id) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select max(p.MCODE) mcode,max(p.productname) productname, SAMI.PRODUCTID,sum(sami.QUANTITY) count,SAMI.BATCH,sum(SAMI.PACKQUANTITY*p.BOXQUANTITY) quantity,max(p.specmode) specmode from STOCK_ALTER_MOVE_IDCODE sami ");
		sql.append("join PRODUCT p on p.id=SAMI.PRODUCTID ");
		sql.append("WHERE SAMI.SAMID='").append(id).append("' ");
		sql.append("GROUP BY SAMI.PRODUCTID,SAMI.BATCH ");
		return EntityManager.jdbcquery(sql.toString());
	}
	
}
