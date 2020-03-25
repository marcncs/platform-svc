package com.winsafe.drp.server;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.Dept;
import com.winsafe.hbm.util.cache.DeptCache;

public class DeptService {
	private DeptCache cache = new DeptCache();
	private AppDept appo = new AppDept();
	
	public void InsertDept(Dept d) throws Exception {		
		appo.InsertDept(d);		
		modifyCache();
	}
	
	public List getDept(HttpServletRequest request, int pagesize, String whereSql) throws Exception{
		return appo.getDept(request, pagesize, whereSql);
	}
	

	public void modifyDept(Dept o)throws Exception {		
		appo.modifyDept(o);
		modifyCache();
	}
	
	public void delDeptByID(int id)throws Exception{
		appo.delDeptByID(id);
		modifyCache();
	}
	
	public Dept getDeptByID(int id)throws Exception{
		if ( id==0 ){
			return null;
		}
		Dept o = cache.getDept(id);
		if ( o == null ){
			o = appo.getDeptByID(id);
			modifyCache();
		}
		return o;
	}
	
	public List getAllDept()throws Exception{
		List list = cache.getAllDept();
		if ( list == null ){
			list = appo.getAllDept();
			modifyCache();
		}
		return list;
	}

	
	public List getDeptByOID(String oid)throws Exception{
		List list = cache.getDeptByOID(oid);
		if ( list == null ){
			list = appo.getDeptByOID(oid);
			modifyCache();
		}
		return list;
	}
	
	public String getDeptName(int id) throws Exception{
		if ( id==0 ){
			return "";
		}
		Dept o = getDeptByID(id);
		if ( o != null ){
			return o.getDeptname();
		}
		return "";
	}

	
	 private void modifyCache() throws Exception {
		//cache.modifyDept(appo.getAllDept());
	}
	
	
}

