package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.exception.IdcodeException;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppCodeRule {
	
	public List getCodeUnitList(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = " from CodeUnit as cu "+ pWhereClause + " order by cu.ucode desc ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getCodeUnitList() throws Exception {
		String hql = " from CodeUnit as cu where not exists (select cr.id from CodeRule as cr where cr.ucode=cu.ucode) "+
		" order by cu.ucode  ";
		return EntityManager.getAllByHql(hql);
	}
	
	public List getCodeRuleByUcode(String ucode) throws Exception {
		String hql = " from CodeRule where ucode='"+ucode+"' order by crproperty  ";
		return EntityManager.getAllByHql(hql);
	}
	
	public List getAllCodeRule() throws Exception {
		String hql = " from CodeRule ";
		return EntityManager.getAllByHql(hql);
	}
	
	
	public CodeRule getCodeRuleByUcodeCrp(String ucode, int crproperty) throws IdcodeException {
		String sql = "  from CodeRule where ucode='"+ucode+"' and crproperty="+crproperty;
		return (CodeRule)EntityManager.find(sql);
	}
	
	public void addCodeRule(CodeRule cu) throws Exception {		
		EntityManager.save(cu);		
	}
	
	public void updCodeRule(CodeRule cu) throws Exception {		
		EntityManager.update(cu);		
	}
	
	public void delCodeRuleByID(String ucode) throws Exception {		
		String sql = "delete from code_unit where ucode='" + ucode+"' ";
		EntityManager.updateOrdelete(sql);		
	}
	
	
	//-------------------CodeRuleUpload---------------------
	public List getAllCodeRuleUploadList() throws Exception {
		String hql = " from CodeRuleUpload order by cruproperty  ";
		return EntityManager.getAllByHql(hql);
	}
	
	public CodeRuleUpload getCodeRuleUploadByCrp(int crproperty) throws Exception {
		String sql = "  from CodeRuleUpload where  cruproperty="+crproperty;
		return (CodeRuleUpload)EntityManager.find(sql);
	}
	
	public void updCodeRuleUpload(CodeRuleUpload cu) throws Exception {		
		EntityManager.update(cu);		
	}
	//-------------------CodeRuleUpload---------------------
}
