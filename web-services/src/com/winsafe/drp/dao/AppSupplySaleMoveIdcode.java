package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppSupplySaleMoveIdcode {

	public List searchSupplySaleMoveIdcode(HttpServletRequest request, int pagesize, String pWhereClause)
	throws Exception {
		String hql = " from SupplySaleMoveIdcode  " + pWhereClause + " order by id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	public void addSupplySaleMoveIdcode(SupplySaleMoveIdcode pii) throws Exception{		
		EntityManager.save(pii);		
	}
	
	public void updSupplySaleMoveIdcode(SupplySaleMoveIdcode pii) throws Exception{		
		EntityManager.update(pii);		
	}
	public void updSupplySaleMoveIdcodeBillNo(String billno,String truebillno) throws Exception{		
		String sql="update supply_sale_move_idcode set smid='"+truebillno+"' where smid='"+billno+"'";
		EntityManager.updateOrdelete(sql);			
	}
	
	public SupplySaleMoveIdcode getSupplySaleMoveIdcodeById(Long id) throws Exception{
		String sql = "from SupplySaleMoveIdcode where id="+id;
		return (SupplySaleMoveIdcode)EntityManager.find(sql);
		}
	
	public void delSupplySaleMoveIdcodeById(long id) throws Exception{		
		String sql="delete from supply_sale_move_idcode where id="+id;
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delSupplySaleMoveIdcodeByPiid(String ssmid) throws Exception{		
		String sql="delete from supply_sale_move_idcode where ssmid='"+ssmid+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delSupplySaleMoveIdcodeByPid(String productid, String ssmid) throws Exception{		
		String sql="delete from supply_sale_move_idcode where productid='"+productid+"' and ssmid='"+ssmid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getSupplySaleMoveIdcodeByssmid(String ssmid) throws Exception{
		String sql = "from SupplySaleMoveIdcode where  ssmid='"+ssmid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getSupplySaleMoveIdcodeByssmid(String ssmid, int isidcode) throws Exception{
		String sql = "from SupplySaleMoveIdcode where  ssmid='"+ssmid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public List getSupplySaleMoveIdcodeByPidBatch(String productid, String ssmid) throws Exception{
		String sql = "from SupplySaleMoveIdcode where productid='"+productid+"' and ssmid='"+ssmid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getSupplySaleMoveIdcodeByPidBatch(String productid, String ssmid, int isidcode) throws Exception{
		String sql = "from SupplySaleMoveIdcode where productid='"+productid+"' and ssmid='"+ssmid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public SupplySaleMoveIdcode getSupplySaleMoveIdcodeByidcode(String productid, String ssmid, String idcode) throws Exception{
		String sql = " from SupplySaleMoveIdcode where productid='"+productid+"' and ssmid='"+ssmid+"' and idcode='"+idcode+"'";
		return (SupplySaleMoveIdcode)EntityManager.find(sql);
	}
	
	
	public double getQuantitySumByssmidProductid(String productid, String ssmid) throws Exception{
		String sql = "select sum(pii.quantity*f.xquantity) from SupplySaleMoveIdcode as pii,FUnit as f "+
		"where pii.productid=f.productid and pii.unitid=f.funitid and pii.productid='"+productid+"' and pii.ssmid='"+ssmid+"'";
		return EntityManager.getdoubleSum(sql);
	}
}
