package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppIdcodeDetail {

	public List getIdcodeDetail(int pagesize, String pWhereClause,
            SimplePageInfo pPgInfo)throws Exception{
		 String sql=" from IdcodeDetail "+pWhereClause+" order by id desc";
		 return EntityManager.getAllByHql(sql, pPgInfo.getCurrentPageNo(),pagesize);
	}
	
	public void addIdcodeDetail(IdcodeDetail pii) throws Exception{		
		EntityManager.save(pii);		
	}
	
	public void updIdcodeDetail(IdcodeDetail pii) throws Exception{		
		EntityManager.update(pii);		
	}
	
	public IdcodeDetail getIdcodeDetailById(Long id) throws Exception{
		String sql = "from IdcodeDetail where id="+id;
		return (IdcodeDetail)EntityManager.find(sql);
	}
	
	public void delIdcodeDetailByBillid(String billid) throws Exception{		
		String sql="delete from idcode_detail where  billid='"+billid+"'";
		EntityManager.updateOrdelete(sql);		
	}
	public void delIdcodeDetailByPidBillid(String productid, String billid) throws Exception{		
		String sql="delete from idcode_detail where productid='"+productid+"' and billid='"+billid+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public List getIdcodeDetailByBillid(String billid) throws Exception{
		String sql = "from IdcodeDetail where  billid='"+billid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getIdcodeDetailByPidBillid(String productid, String billid) throws Exception{
		String sql = "from IdcodeDetail where productid='"+productid+"' and billid='"+billid+"'";
		return EntityManager.getAllByHql(sql);
	}
	
	public List getIdcodeDetailByIdcode(String idcode) throws Exception{
		String sql = "from IdcodeDetail where idcode='"+idcode+"' order by id";
		return EntityManager.getAllByHql(sql);
	}
	
	
	public String getMaxIdcode(String productid, String header) throws Exception{
		String sql = "select MAX(idcode) from IdcodeDetail where  idcode like '"+header+"%' and productid='"+productid+"'";
		return (String)EntityManager.find(sql);
	}
	
	
	public String getIdcodeStartIndex(String productid, String header)throws Exception{
		String maxidcode = getMaxIdcode(productid, header);
		if ( maxidcode == null || maxidcode.equals("") ){
			return DataFormat.getFormatNums(1, 21-header.length());
		}
		maxidcode = maxidcode.substring(header.length());
		return DataFormat.getFormatNums(Long.valueOf(maxidcode)+1, 21-header.length());
	}
}
