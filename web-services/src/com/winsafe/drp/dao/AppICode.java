package com.winsafe.drp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppICode {
	
	public List getICodeList(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = " from ICode "+ pWhereClause + " order by id desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	
	public List getICodeListByProductid(String productid) throws Exception {
		String hql = " from ICode where productid='"+productid+"' order by id  ";
		return EntityManager.getAllByHql(hql);
	}
	
	public String getLCodeByPid(String productid) throws Exception {
		String hql = " from ICode where productid='"+productid+"' order by lcode";
		List<ICode> list = EntityManager.getAllByHql(hql);
		StringBuffer sb = new StringBuffer();
		for ( ICode icode : list ){
			sb.append(icode.getLcode());
		}		
		return sb.toString();
	}
	
	public String getLCodeStringByPid(String productid) throws Exception {
		String result = "";
		String hql = " from ICode where productid='"+productid+"' order by lcode";
		ICode iCode = (ICode) EntityManager.find(hql);
		if(iCode != null && !StringUtil.isEmpty(iCode.getLcode())){
			result = iCode.getLcode();
		}
		return result;
	}
	
	
	public ICode getICodeByLcode(String lcode) throws Exception {
		String sql = "  from ICode where lcode='"+lcode+"' ";
		return (ICode)EntityManager.find(sql);
	}
	
	public String getProductIdByLcode(String lcode) throws Exception {
		ICode icode = getICodeByLcode(lcode);
		if ( icode == null ){
			return "";
		}
		return icode.getProductid();
	}
	
	public void addICode(ICode cu) throws Exception {		
		EntityManager.save(cu);		
	}
	
	public void updICode(ICode cu) throws Exception {		
		EntityManager.update(cu);		
	}
	
	public void delCodeRuleByID(String lcode) throws Exception {		
		String sql = "delete from i_code where lcode='" + lcode+"' ";
		EntityManager.updateOrdelete(sql);		
	}

	@SuppressWarnings("unchecked")
	public List<ICode> queryByInIcode(String ids) throws Exception{
//		Map<String,Product> pMap = new HashMap<String,Product>();
//		Map<String,Product> lMap = new HashMap<String,Product>();
//		AppProduct ap = new AppProduct();
		String sql = "  from ICode where productid in(" + ids+ ")";
		List<ICode> icodes = EntityManager.getAllByHql(sql);
//		String pid = null;
//		Product p =null;
//		for(ICode icode : icodes){
//			pid = icode.getProductid();
//			p = ap.getProductByID(pid);
//			lMap.put(icode.getLcode(), p);
//			pMap.put(pid, p);
//		}
//		Object[] objArr = new Object[2];
//		objArr[0] = pMap;
//		objArr[1] = lMap;
		return icodes;
	 }
	
	public List<ICode> queryByInIcodeWithlcode(String ids) throws Exception{
		String sql = "  from ICode where lcode in(" + ids+ ")";
		List<ICode> icodes = EntityManager.getAllByHql(sql);
		return icodes;
	}
	
	public List<ICode> getICodeAll() throws Exception{
		String hql="from ICode";
		return EntityManager.getAllByHql(hql);
	}
}
