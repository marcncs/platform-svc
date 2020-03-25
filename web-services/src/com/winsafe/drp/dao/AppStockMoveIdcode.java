package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppStockMoveIdcode {

	public List searchStockMoveIdcode(HttpServletRequest request, int pagesize, String pWhereClause)
	throws Exception {
		String hql = " from StockMoveIdcode  " + pWhereClause + " order by id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	public void addStockMoveIdcode(StockMoveIdcode pii) throws Exception{		
		EntityManager.save(pii);		
	}
	
	public void updStockMoveIdcode(StockMoveIdcode pii) throws Exception{		
		EntityManager.update(pii);		
	}
	
	public void updStockMoveIdcodeBillNo(String billno,String truebillno) throws Exception{		
		String sql="update stock_move_idcode set smid='"+truebillno+"' where smid='"+billno+"'";
		EntityManager.updateOrdelete(sql);			
	}
	
	public StockMoveIdcode getStockMoveIdcodeById(Long id) throws Exception{
		String sql = "from StockMoveIdcode where id="+id;
		return (StockMoveIdcode)EntityManager.find(sql);
		}
	
	public void delStockMoveIdcodeById(long id) throws Exception{		
		String sql="delete from stock_move_idcode where id="+id;
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delStockMoveIdcodeByPiid(String smid) throws Exception{		
		String sql="delete from stock_move_idcode where smid='"+smid+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delStockMoveIdcodeByPid(String productid, String smid) throws Exception{		
		String sql="delete from stock_move_idcode where productid='"+productid+"' and smid='"+smid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getStockMoveIdcodeBysmid(String smid) throws Exception{
		String sql = "from StockMoveIdcode where  smid='"+smid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getStockMoveIdcodeBysmid(String smid, int isidcode) throws Exception{
		String sql = "from StockMoveIdcode where  smid='"+smid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public List getStockMoveIdcodeByPidBatch(String productid, String smid) throws Exception{
		String sql = "from StockMoveIdcode where productid='"+productid+"' and smid='"+smid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getStockMoveIdcodeByPidBatch(String productid, String smid, int isidcode) throws Exception{
		String sql = "from StockMoveIdcode where productid='"+productid+"' and smid='"+smid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public StockMoveIdcode getStockMoveIdcodeByidcode(String productid, String smid, String idcode) throws Exception{
	//	String sql = " from StockMoveIdcode where productid='"+productid+"' and smid='"+smid+"' and idcode='"+idcode+"'";
		String sql = " from StockMoveIdcode where productid='"+productid+"' and idcode='"+idcode+"'";

		return (StockMoveIdcode)EntityManager.find(sql);
	}
	
	
	public double getQuantitySumBysmidProductid(String productid, String smid) throws Exception{
		String sql = "select sum(pii.quantity*f.xquantity) from StockMoveIdcode as pii,FUnit as f "+
		"where pii.productid=f.productid and pii.unitid=f.funitid and pii.productid='"+productid+"' and pii.smid='"+smid+"'";
		return EntityManager.getdoubleSum(sql);
	}
	
	public double getQuantitySumBysmidProductid2(String productid, String smid) throws Exception{
		String sql = "select sum(pii.packquantity) from StockMoveIdcode as pii"+
		" where  pii.productid='"+productid+"' and pii.smid='"+smid+"'";
		return EntityManager.getdoubleSum(sql);
	}

	public void delStockMoveIDcodeByNCcode(String nccode) throws Exception {
		String sql = "delete from stock_move_idcode where Nccode='"
				+ nccode + "'";
		EntityManager.updateOrdelete(sql);
	}

}
