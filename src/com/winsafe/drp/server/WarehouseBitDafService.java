package com.winsafe.drp.server;

import java.util.Date;
import java.util.List;

import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.StringUtil;

/** 
 * @author: jelli 
 * @version:2009-10-19 下午12:27:09 
 * @copyright:www.winsafe.cn
 */
public class WarehouseBitDafService {
	private String tablename;
	private String billnocolumn;
	private String warehouseid;
	private AppProduct ap = new AppProduct();
	private boolean isnootherbit = true;
	
	public WarehouseBitDafService(String tablename, String billnocolumn, String warehouseid){
		this.tablename = tablename;
		this.billnocolumn=billnocolumn;
		this.warehouseid = warehouseid;
		this.isnootherbit = isNoOtherBit();
	}
	
	public void add(String billno, String productid, int unitid, double quantity)throws Exception{
		if ( !isnootherbit ){
			return;
		}
		Product p = ap.getProductByID(productid);
		if ( p.getIsidcode() == 1 ){
			return;
		}
		this.insertData(billno, productid, unitid, quantity, "");
	}
	
	public void add(String billno, String productid, int unitid, double quantity, String batch)throws Exception{
		if ( !isnootherbit ){
			return;
		}
		Product p = ap.getProductByID(productid);
		if (p.getIsidcode() != null) {
			if ( p.getIsidcode() == 1 ){
				return;
			}
		}
		this.insertData(billno, productid, unitid, quantity, batch);
	}
	
	public void del(String billno) throws Exception{
		String sql= "delete from "+tablename+" where "+billnocolumn+"='"+billno+"'";
		EntityManager.updateOrdelete(sql);
	}
	
	public void del(String billno, String[] productid) throws Exception{
		if ( productid == null){
			return;
		}		
		String sql= "delete from "+tablename+" where "+billnocolumn+"='"+billno+
		"' and productid not in("+StringUtil.appendStr(productid, ",")+")";
		//System.out.println("==========>"+sql);
		EntityManager.updateOrdelete(sql);				
	}
	
	
	private boolean isNoOtherBit(){
		WarehouseService ws = new WarehouseService();
		try{
			List list = ws.getWarehouseBitByWid(warehouseid);
			if ( !list.isEmpty() && list.size()>1 ){
				return false;
			}
		}catch(Exception e ){
			e.printStackTrace();
		}
		return true;
	}
	
	private void insertData(String billno, String productid, int unitid, double quantity, String batch) throws Exception{
		String sql = "insert into "+tablename+" (\"ID\","+billnocolumn+",productid,isidcode,warehousebit,\"BATCH\","+
		"ProduceDate,\"VALIDATE\",UnitID,Quantity,PackQuantity,LCode,StartNo,EndNo,IDCode,MakeDate) select "+
		Long.valueOf(MakeCode.getExcIDByRandomTableName(tablename,0,""))+",'"+billno+"','"+productid+"',0,'000','"+batch+"',"+
		"'','',"+unitid+","+quantity+",0.00,'','','','','"+new Date()+"' "+
		" from "+tablename+" "+
		" where not exists(select id from "+tablename+" where "+billnocolumn+"='"+billno+"' and productid='"+productid+"' "+
		" and batch='"+batch+"') ";
		EntityManager.updateOrdelete(sql);
	}
}
