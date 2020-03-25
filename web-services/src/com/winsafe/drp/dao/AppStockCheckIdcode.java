package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppStockCheckIdcode {

	public List searchStockCheckIdcode(HttpServletRequest request, int pagesize, String pWhereClause)
	throws Exception {
		String hql = " from StockCheckIdcode  " + pWhereClause + " order by id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	public void copyIdcodeToStockCheckIdcode(String scid, String warehouseid, String warehousebit, String batch, String productid) throws Exception{		
		String sql = " insert into stock_check_idcode(scid,productid,warehousebit,batch,producedate,validate,unitid, "+
		" quantity,cquantity,fquantity,packquantity,lcode,startno,endno,idcode,cidcode,makedate) "+
		" select '"+scid+"',productid,warehousebit,batch,producedate,validate,unitid, "+
		" quantity,0,fquantity,packquantity,lcode,startno,endno,idcode,'',makedate "+
		" from idcode "+
		" where (isuse=1 or quantity>0 and quantity!=fquantity) and batch='"+batch+
		"' and warehouseid='"+warehouseid+"' and warehousebit='"+warehousebit+"' and productid='"+productid+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	
	public void addStockCheckIdcode(StockCheckIdcode pii) throws Exception{		
		EntityManager.save(pii);		
	}
	
	public void updStockCheckIdcode(StockCheckIdcode pii) throws Exception{		
		EntityManager.update(pii);		
	}
	public void updStockCheckIdcodeBillNo(String billno,String truebillno) throws Exception{		
		String sql="update stock_check_idcode set scid='"+truebillno+"' where scid='"+billno+"'";
		EntityManager.updateOrdelete(sql);			
	}		
	
	
	public StockCheckIdcode getStockCheckIdcodeById(Long id) throws Exception{
		String sql = "from StockCheckIdcode where id="+id;
		return (StockCheckIdcode)EntityManager.find(sql);
		}
	
	public void delStockCheckIdcodeById(long id) throws Exception{		
		String sql="delete from stock_check_idcode where id="+id;
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delStockCheckIdcodeByScid(String scid) throws Exception{		
		String sql="delete from stock_check_idcode where scid='"+scid+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delStockCheckIdcodeByPid(String productid, String scid, String batch) throws Exception{		
		String sql="delete from stock_check_idcode where productid='"+productid+"' and scid='"+scid+"' and batch='"+batch+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delStockCheckIdcodeByPid(String scid, String productid, String wbit, String batch) throws Exception {
		String sql = "delete from stock_check_idcode where scid='" + scid + "' and productid='"+productid+
		"' and warehousebit='"+wbit+"' and batch='"+batch+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	public List getStockCheckIdcodeByscid(String scid) throws Exception{
		String sql = "from StockCheckIdcode where  scid='"+scid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public StockCheckIdcode getStockCheckIdcodeByscid(String scid, String idcode) throws Exception{
		String sql = "from StockCheckIdcode where  scid='"+scid+"' and idcode='"+idcode+"'";
		return (StockCheckIdcode)EntityManager.find(sql);
	}
	
	public List getStockCheckIdcodeByPidBatch(String productid, String scid) throws Exception{
		String sql = "from StockCheckIdcode where productid='"+productid+"' and scid='"+scid+"'";
		return EntityManager.getAllByHql(sql);
	}

	
	public List getStockCheckIdcodeByPidBatch(String productid, String scid, int isidcode, String batch) throws Exception{
		String sql = "from StockCheckIdcode where productid='"+productid+"' and scid='"+scid+"' and isidcode="+isidcode+" and batch='"+batch+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public StockCheckIdcode getStockCheckIdcodeByidcode(String productid, String scid, String idcode) throws Exception{
		String sql = " from StockCheckIdcode where productid='"+productid+"' and scid='"+scid+"' and idcode='"+idcode+"'";
		return (StockCheckIdcode)EntityManager.find(sql);
	}
	
	public StockCheckIdcode getStockCheckIdcodeByCidcode(String productid, String scid, String cidcode) throws Exception{
	//	String sql = " from StockCheckIdcode where productid='"+productid+"' and scid='"+scid+"' and cidcode='"+cidcode+"'";
		String sql = " from StockCheckIdcode where productid='"+productid+"' and cidcode='"+cidcode+"'";
		return (StockCheckIdcode)EntityManager.find(sql);
	}
	
	
	public double getQuantitySumByscidProductid(String productid, String scid, String batch) throws Exception{
		String sql = "select sum(pii.quantity*f.xquantity) from StockCheckIdcode as pii,FUnit as f "+
		"where pii.productid=f.productid and pii.unitid=f.funitid and pii.productid='"+productid+"' and pii.scid='"+scid+"' and pii.batch='"+batch+"'";
		return EntityManager.getdoubleSum(sql);
	}
	
	public StockCheckIdcode getIdcodeByWLM(String startno, String endno, String scid)throws Exception {
		String sql = "from StockCheckIdcode where '"+startno+"' between startno and endno "+
		" and '"+endno+"' between startno and endno and idcode !='' and scid='"+scid+"'";	
		return (StockCheckIdcode) EntityManager.find(sql);
	}
}
