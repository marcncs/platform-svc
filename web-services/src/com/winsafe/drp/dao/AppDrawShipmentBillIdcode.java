package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;



/**
 * @author : Qhonge
 * @version : 2010-1-15 下午09:04:39
 * www.winsafe.cn
 */
public class AppDrawShipmentBillIdcode  {
	public List searchDrawShipmentBillIdcode(HttpServletRequest request, int pagesize, String pWhereClause)
	throws Exception {
		String hql = " from DrawShipmentBillIdcode  " + pWhereClause + " order by id desc";
		System.out.println("==========>"+hql);
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	public void addDrawShipmentBillIdcode(DrawShipmentBillIdcode dsb) throws Exception{		
		EntityManager.save(dsb);		
	}
	
	public void updDrawShipmentBillIdcode(DrawShipmentBillIdcode dsb) throws Exception{		
		EntityManager.update(dsb);		
	}
	
	public void updDrawShipmentBillIdcodeBillNo(String billno,String truebillno) throws Exception{		
		String sql="update draw_shipment_bill_idcode set dsid='"+truebillno+"' where dsid='"+billno+"'";
		EntityManager.updateOrdelete(sql);			
	}
	
	public DrawShipmentBillIdcode getDrawShipmentBillIdcodeById(Long id) throws Exception{
		String sql = "from DrawShipmentBillIdcode where id="+id;
		return (DrawShipmentBillIdcode)EntityManager.find(sql);
		}
	
	public void delDrawShipmentBillIdcodeById(long id) throws Exception{		
		String sql="delete from draw_shipment_bill_idcode where id="+id;
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delDrawShipmentBillIdcodeByDsid(String dsid) throws Exception{		
		String sql="delete from draw_shipment_bill_idcode where dsid='"+dsid+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void delDrawShipmentBillIdcodeByPid(String productid, String dsid, String batch) throws Exception{		
		String sql="delete from draw_shipment_bill_idcode where productid='"+productid+"' and osid='"+dsid+"' and batch='"+batch+"'";
		EntityManager.updateOrdelete(sql);
		
	}
	
	public List getDrawShipmentBillIdcodeBydsid(String dsid) throws Exception{
		String sql = "from DrawShipmentBillIdcode where  osid='"+dsid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getDrawShipmentBillIdcodeBydsid(String dsid, int isidcode) throws Exception{
		String sql = "from DrawShipmentBillIdcode where  dsid='"+dsid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public List getDrawShipmentBillIdcodeByPidBatch(String productid, String dsid) throws Exception{
		String sql = "from DrawShipmentBillIdcode where productid='"+productid+"' and dsid='"+dsid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getDrawShipmentBillIdcodeByPidBatch(String productid, String dsid, int isidcode) throws Exception{
		String sql = "from DrawShipmentBillIdcode where productid='"+productid+"' and dsid='"+dsid+"' and isidcode="+isidcode;
		return EntityManager.getAllByHql(sql);
	}
	
	public List getDrawShipmentBillIdcodeByPidBatch(String productid, String dsid, int isidcode, String batch) throws Exception{
		String sql = "from DrawShipmentBillIdcode where productid='"+productid+"' and dsid='"+dsid+"' and isidcode="+isidcode+" and batch='"+batch+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public DrawShipmentBillIdcode getDrawShipmentBillIdcodeByidcode(String productid, String dsid, String idcode) throws Exception{
		//String sql = " from DrawShipmentBillIdcode where productid='"+productid+"' and osid='"+dsid+"' and idcode='"+idcode+"'";
		String sql = " from DrawShipmentBillIdcode where productid='"+productid+"' and idcode='"+idcode+"'";

		return (DrawShipmentBillIdcode)EntityManager.find(sql);
	}
	
	
	public double getQuantitySumByosidProductid(String productid, String osid, String batch) throws Exception{
		String sql = "select sum(dsb.quantity*f.xquantity) from DrawShipmentBillIdcode as dsb,FUnit as f "+
		"where dsb.productid=f.productid and dsb.unitid=f.funitid and dsb.productid='"+productid+"' and dsb.dsid='"+osid+"' and dsb.batch='"+batch+"'";
		return EntityManager.getdoubleSum(sql);
	}

}
