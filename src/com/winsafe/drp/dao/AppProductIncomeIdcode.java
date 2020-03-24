package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppProductIncomeIdcode {

	public List searchProductIncomeIdcode(HttpServletRequest request, int pagesize, String pWhereClause)
	throws Exception {
		String hql = " from ProductIncomeIdcode  " + pWhereClause + " order by id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	public void addProductIncomeIdcode(ProductIncomeIdcode pii) throws Exception{		
		EntityManager.save3(pii);		
	}
	
	public boolean getProductIncomeIdcodeByNativeSql(String idcode) throws Exception{		
       String sql = "select idcode from product_income_idcode where idcode='"+idcode+"'";
		return EntityManager.getResultByNativeSql(sql);
	}
	
	public void updProductIncomeIdcode(ProductIncomeIdcode pii) throws Exception{		
		EntityManager.update(pii);		
	}
	
	public void updProductIncomeIdcodeBillNo(String billno,String truebillno) throws Exception{		
		String sql="update product_income_idcode set PIID='"+truebillno+"' where PIID='"+billno+"'";
		EntityManager.updateOrdelete(sql);			
	}
	
	public ProductIncomeIdcode getProductIncomeIdcodeById(Long id) throws Exception{
		String sql = "from ProductIncomeIdcode where id="+id;
		return (ProductIncomeIdcode)EntityManager.find(sql);
		}
	
	public void delProductIncomeIdcodeById(long id) throws Exception{		
		String sql="delete from product_income_idcode where id="+id;
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delProductIncomeIdcodeByPiid(String piid) throws Exception{		
		String sql="delete from product_income_idcode where piid='"+piid+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delProductIncomeIdcodeByPid(String productid, String piid) throws Exception{		
		String sql="delete from product_income_idcode where productid='"+productid+"' and piid='"+piid+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List<ProductIncomeIdcode> getProductIncomeIdcodeByPiid(String piid) throws Exception{
		String sql = "from ProductIncomeIdcode where  piid='"+piid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List<ProductIncomeIdcode> getProductIncomeIdcodeByPiid(String piid, int isidcode) throws Exception{
		String sql = "from ProductIncomeIdcode where  piid='"+piid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public List getProductIncomeIdcodeByPidBatch(String productid, String piid) throws Exception{
		String sql = "from ProductIncomeIdcode where productid='"+productid+"' and piid='"+piid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getProductIncomeIdcodeByPidBatch(String productid, String piid, int isidcode) throws Exception{
		String sql = "from ProductIncomeIdcode where productid='"+productid+"' and piid='"+piid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public ProductIncomeIdcode getProductIncomeIdcodeByidcode(String productid, String piid, String idcode) throws Exception{
		//String sql = " from ProductIncomeIdcode where productid='"+productid+"' and piid='"+piid+"' and idcode='"+idcode+"'";
		String sql = " from ProductIncomeIdcode where productid='"+productid+"' and idcode='"+idcode+"'";

		return (ProductIncomeIdcode)EntityManager.find(sql);
	}
	
	public ProductIncomeIdcode getProductIncomeIdcodeByIdcode(String idcode)
	{
		String sql = " from ProductIncomeIdcode where idcode='"+idcode+"'";
		return (ProductIncomeIdcode)EntityManager.find(sql);
	}
	
	public ProductIncomeIdcode getProductIncomeIdcodeByidcode(String productid,String idcode) throws Exception{
		//String sql = " from ProductIncomeIdcode where productid='"+productid+"' and piid='"+piid+"' and idcode='"+idcode+"'";
		String sql = " from ProductIncomeIdcode where productid='"+productid+"' and idcode='"+idcode+"'";

		return (ProductIncomeIdcode)EntityManager.find(sql);
	}
	
	public ProductIncomeIdcode getProductIncomeIdcodeByUnitNo(String productid,String idcode) throws Exception{
		//String sql = " from ProductIncomeIdcode where productid='"+productid+"' and piid='"+piid+"' and idcode='"+idcode+"'";
		String sql = " from ProductIncomeIdcode where productid='"+productid+"' and idcode like '%"+idcode+"%'";

		return (ProductIncomeIdcode)EntityManager.find(sql);
	}
	
	public boolean ProductIncomeIdcodeIsExistByUnitNo(String idcode) throws Exception{
		String sql = "select count(*)  from ProductIncomeIdcode where idcode='"+idcode+"'";
		return EntityManager.getRecordCount(sql) > 0;
	}
	
	public double getQuantitySumByPiidProductid(String productid, String piid) throws Exception{
		String sql = "select sum(pii.quantity*f.xquantity) from ProductIncomeIdcode as pii,FUnit as f "+
		"where pii.productid=f.productid and pii.unitid=f.funitid and pii.productid='"+productid+"' and pii.piid='"+piid+"'";
		return EntityManager.getdoubleSum(sql);
	}
	
	public void delProductIncomeIDcodeByNCcode(String nccode) throws Exception {
		String sql = "delete from product_income_idcode where Nccode='"
				+ nccode + "'";
		EntityManager.updateOrdelete(sql);
	}
	public double getTotalQuantityByPiid(String piid) throws Exception{
		String sql = "select sum(pii.packquantity) from ProductIncomeIdcode as pii "+
		"where  pii.piid='"+piid+"'";
		return EntityManager.getdoubleSum(sql);
	}
	
	public int insertProductIncomeIdcode(ProductIncomeIdcode pii) throws Exception {		
		String sql = "insert into product_income_idcode(id,piid,lcode,productid,isidcode,warehousebit,batch,producedate," +
				"vad,unitid,quantity,idcode,startno,endno,packquantity,makedate)  select '"
				+ pii.getId()+ "','"+ pii.getPiid()+ "','"+pii.getLcode()+"','"+pii.getProductid()+"',"
				+ pii.getIsidcode()+ ",'"+pii.getWarehousebit()+"','"+pii.getBatch()+"','"+pii.getProducedate()+"','"
				+ pii.getVad()+"',"+pii.getUnitid()+","+pii.getQuantity()+",'"+pii.getIdcode()+"','"+pii.getStartno()+"','"+pii.getEndno()+"',"
				+ pii.getPackquantity()+",TO_DATE('"+ DateUtil.formatDateTime(pii.getMakedate())+"','yyyy-mm-dd hh24:mi:ss')"
				+ " from dual where not exists(select id from product_income_idcode a where a.idcode='"+ pii.getIdcode() + "')";
	        	return  EntityManager.executeUpdate(sql);
	}
	
}
