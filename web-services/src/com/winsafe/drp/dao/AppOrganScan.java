package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppOrganScan {

	public List searchOrganScan(HttpServletRequest request, String pWhereClause)
			throws Exception {
		String hql = " select new map(os.id as id,sc.bname as bname,os.isscan as isscan) from OrganScan as os,ScanConf as sc  " + pWhereClause + " order by scb desc";
		return PageQuery.hbmQuery(request, hql);
	}

	public void addOrganScan(OrganScan os) throws Exception {
		EntityManager.save(os);
	}
	
	public void updOrganScan(OrganScan os) throws Exception {
		EntityManager.update(os);
	}

	public void delOrganScan(int id) throws Exception {
		String sql = "delete from OrganScan where id=" + id;
		EntityManager.updateOrdelete(sql);

	}

	public void delOrganScanByCid(String id) throws Exception {
		String sql = "delete from OrganScan where cid='" + id + "'";
		EntityManager.updateOrdelete(sql);

	}

	public OrganScan getOrganScanByID(int id) throws Exception {
		String hql = "from OrganScan as l where l.id=" + id;
		return (OrganScan) EntityManager.find(hql);
	}

	public OrganScan getOrganScanByOidScb(String oid, String scb) throws Exception {
		String hql = "from OrganScan  where organid='" + oid + "' and scb='"+scb+"'";
		return (OrganScan)EntityManager.find(hql);
	}
	
	public ScanConf getScanConfByID(String id) throws Exception {
		String hql = "from ScanConf  where id='" + id+"'";
		return (ScanConf) EntityManager.find(hql);
	}
	
	public List getAllScanConf() throws Exception {
		String hql = "from ScanConf ";
		return EntityManager.getAllByHql(hql);
	}
	
	public void insertOrganScan(String organid) throws Exception{
		String sql="insert into Organ_Scan(scb,organid,isscan) "+
			"select id,'"+organid+"',0  from Scan_Conf ";
		EntityManager.updateOrdelete(sql);
	}
	public void insertOrganScan(String organid,String... billTypes) throws Exception{
		String sql = null;
		if(billTypes != null && billTypes.length > 0){
			String caseWhenStr = "";
			for(String billType : billTypes){
				caseWhenStr += " when '" + billType + "' then 1 ";
			}
			caseWhenStr = " case sc.id " + caseWhenStr + " else 0 end ";
			sql="insert into Organ_Scan(scb,organid,isscan) "+
			"select sc.id,'"+organid+"'," + caseWhenStr + " from Scan_Conf as sc ";
		}else {
			sql="insert into Organ_Scan(scb,organid,isscan) "+
			"select sc.id,'"+organid+"',0  from Scan_Conf as sc ";
		}
		EntityManager.updateOrdelete(sql);
	}

	

}
