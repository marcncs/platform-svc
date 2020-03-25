package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppOtherShipmentBillIdcode {

	public List searchOtherShipmentBillIdcode(HttpServletRequest request, int pagesize, String pWhereClause)
	throws Exception {
		String hql = " from OtherShipmentBillIdcode  " + pWhereClause + " order by id desc";
		System.out.println("==========>"+hql);
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	public void addOtherShipmentBillIdcode(OtherShipmentBillIdcode pii) throws Exception{		
		EntityManager.save(pii);		
	}
	
	public void updOtherShipmentBillIdcode(OtherShipmentBillIdcode pii) throws Exception{		
		EntityManager.update(pii);		
	}
	
	public void updOtherShipmentBillIdcodeBillNo(String billno,String truebillno) throws Exception{		
		String sql="update other_shipment_bill_idcode set osid='"+truebillno+"' where osid='"+billno+"'";
		EntityManager.updateOrdelete(sql);			
	}
	
	public OtherShipmentBillIdcode getOtherShipmentBillIdcodeById(Long id) throws Exception{
		String sql = "from OtherShipmentBillIdcode where id="+id;
		return (OtherShipmentBillIdcode)EntityManager.find(sql);
		}
	
	public void delOtherShipmentBillIdcodeById(long id) throws Exception{		
		String sql="delete from other_shipment_bill_idcode where id="+id;
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delOtherShipmentBillIdcodeByTiid(String osid) throws Exception{		
		String sql="delete from other_shipment_bill_idcode where osid='"+osid+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delOtherShipmentBillIdcodeByPid(String productid, String osid, String batch) throws Exception{		
		String sql="delete from other_shipment_bill_idcode where productid='"+productid+"' and osid='"+osid+"' and batch='"+batch+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getOtherShipmentBillIdcodeByosid(String osid) throws Exception{
		String sql = "from OtherShipmentBillIdcode where  osid='"+osid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getOtherShipmentBillIdcodeByosid(String osid, int isidcode) throws Exception{
		String sql = "from OtherShipmentBillIdcode where  osid='"+osid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public List getOtherShipmentBillIdcodeByPidBatch(String productid, String osid) throws Exception{
		String sql = "from OtherShipmentBillIdcode where productid='"+productid+"' and osid='"+osid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getOtherShipmentBillIdcodeByPidBatch(String productid, String osid, int isidcode) throws Exception{
		String sql = "from OtherShipmentBillIdcode where productid='"+productid+"' and osid='"+osid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public List getOtherShipmentBillIdcodeByPidBatch(String productid, String osid, int isidcode, String batch) throws Exception{
		String sql = "from OtherShipmentBillIdcode where productid='"+productid+"' and osid='"+osid+"' and isidcode="+isidcode+" and batch='"+batch+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public OtherShipmentBillIdcode getOtherShipmentBillIdcodeByidcode(String productid, String osid, String idcode) throws Exception{
		//String sql = " from OtherShipmentBillIdcode where productid='"+productid+"' and osid='"+osid+"' and idcode='"+idcode+"'";
		String sql = " from OtherShipmentBillIdcode where productid='"+productid+"' and idcode='"+idcode+"'";

		return (OtherShipmentBillIdcode)EntityManager.find(sql);
	}
	
	
	public double getQuantitySumByosidProductid(String productid, String osid, String batch) throws Exception{
		String sql = "select sum(pii.quantity*f.xquantity) from OtherShipmentBillIdcode as pii,FUnit as f "+
		"where pii.productid=f.productid and pii.unitid=f.funitid and pii.productid='"+productid+"' and pii.osid='"+osid+"' and pii.batch='"+batch+"'";
		return EntityManager.getdoubleSum(sql);
	}
}
