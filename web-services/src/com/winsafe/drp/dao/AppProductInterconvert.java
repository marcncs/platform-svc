package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppProductInterconvert {

	public void addProductInterconvert(Object stockAlterMove) throws Exception {
		EntityManager.save(stockAlterMove);
	}

	public List getProductInterconvert(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String sql = "from ProductInterconvert as sm "
				+ pWhereClause + " order by sm.makedate desc ";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}
	
	public double getTotalSum(String pWhereClause) throws Exception {
		String sql = "select sum(sm.totalsum) from ProductInterconvert as sm "
				+ pWhereClause;
		return EntityManager.getdoubleSum(sql);
	}
	
	
	
	public void delProductInterconvert(String id)throws Exception{		
		String sql="delete from product_interconvert where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
		
	}

	public ProductInterconvert getProductInterconvertByID(String id) throws Exception {
		String sql = " from ProductInterconvert as sm where sm.id='" + id+"'";
		return (ProductInterconvert) EntityManager.find(sql);
	}

	
	public void updProductInterconvertByID(ProductInterconvert sm,String movedate) throws Exception {		
		String sql = "update product_interconvert set movedate='"+movedate+"',outwarehouseid="+sm.getOutwarehouseid()+",inwarehouseid="+sm.getInwarehouseid()+",totalsum="+sm.getTotalsum()+",movecause='"+sm.getMovecause()+"',remark='"+sm.getRemark()+"' where id='" + sm.getId()+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void updstockAlterMove(ProductInterconvert sam)throws Exception{		
		EntityManager.update(sam);		
	}
	
	
	public void updProductInterconvertIsShipment(String id,int isshipment,int userid)throws Exception{
		String sql="update product_interconvert set isshipment="+isshipment+",shipmentid="+userid+",shipmentdate='"+DateUtil.getCurrentDateTime()+"' where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	
	public void updProductInterconvertIsComplete(String id,int iscomplete,int userid)throws Exception{
		String sql="update product_interconvert set iscomplete="+iscomplete+",receivedate='"+DateUtil.getCurrentDateTime()+"',receiveid="+userid+" where id='"+id+"'";
		EntityManager.updateOrdelete(sql);
	}
	

	public void updIsAudit(String ppid, Integer userid,Integer audit) throws Exception {		
		String sql = "update product_interconvert set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateTime()
				+ "' where id='" + ppid + "'";
		EntityManager.updateOrdelete(sql);
		
	}

	public List<ProductInterconvert> getProductInterconvert(String whereSql) {
		String sql = "from ProductInterconvert as sm "
			+ whereSql + " order by sm.makedate desc ";
		return EntityManager.getAllByHql(sql);
	}
	
}
