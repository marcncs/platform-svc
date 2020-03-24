package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppOIntegral {

	public List getOIntegral(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List wr = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from OIntegral as c "
				+ pWhereClause + " order by c.id ";
		wr = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return wr;
	}

	public void addOIntegral(Object pp) throws Exception {
		
		EntityManager.save(pp);
		
	}
	

	public double getOIntegralByOID(String oid)throws Exception{
		double ci = 0d;
		//String sql=" select sum(oi.ointegral) from OIntegral as oi where oi.equiporganid='"+oid+"'";
		String sql=" select sum(oi.completeintegral) from OIntegralDeal as oi where oi.oid='"+oid+"'";
		ci = EntityManager.getdoubleSum(sql);
		return ci;
	}
	
	
	
	public void addOIntegralIsNoExist(OIntegral ci)throws Exception{
		
		String sql="insert into o_integral(powerorganid,equiporganid,isort,ointegral,iyear) select '"+ci.getPowerorganid()+"','"+ci.getEquiporganid()+"',"+ci.getIsort()+",0,"+ci.getIyear()+" where not exists (select equiporganid from o_integral where powerorganid='"+ci.getPowerorganid()+"' and equiporganid='"+ci.getEquiporganid()+"' and isort="+ci.getIsort()+" and iyear='"+ci.getIyear()+"')";

		EntityManager.updateOrdelete(sql);
		
	}
	
	public void updOIntegralByIntegral(OIntegral ci)throws Exception{
		String sql="update o_integral set ointegral = ointegral + "+ci.getOintegral()+" where powerorganid='"+ci.getPowerorganid()+"' and equiporganid='"+ci.getEquiporganid()+"' and isort="+ci.getIsort()+" and iyear='"+ci.getIyear()+"'";
		EntityManager.updateOrdelete(sql);
	}

	public List getAllOIntegral() throws Exception {
		List aw = null;
		String sql = " from OIntegral as c ";
		aw = EntityManager.getAllByHql(sql);
		return aw;
	}

	public OIntegral getOIntegralByID(Long id) throws Exception {
		String sql = " from OIntegral where id=" + id;
		OIntegral w = (OIntegral) EntityManager.find(sql);
		return w;
	}
	
	public OIntegral getOIntegralByBillNo(String billno,int isort) throws Exception {
		String sql = " from OIntegral where billno='" + billno+"' and isort= "+isort+" ";
		OIntegral w = (OIntegral) EntityManager.find(sql);
		return w;
	}
	

	public void updOIntegral(OIntegral w) throws Exception {
		
		EntityManager.update(w);
		
	}

}
