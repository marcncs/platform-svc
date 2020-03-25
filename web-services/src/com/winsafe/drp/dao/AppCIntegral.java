package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class AppCIntegral {

	public List getCIntegral(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List wr = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = " from CIntegral as c "
				+ pWhereClause + " order by c.id desc ";
		wr = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return wr;
	}

	public void addCIntegral(Object pp) throws Exception {
		EntityManager.save(pp);
	}
	
	
	public double getCIntegralByCID(String cid)throws Exception{
		double ci = 0d;
		//String sql=" select sum(ci.cintegral) from CIntegral as ci where ci.cid='"+cid+"'";
		String sql="select sum(completeintegral) from CIntegralDeal where cid='"+cid+"'";
		ci = EntityManager.getdoubleSum(sql);
		//System.out.println("----AA"+sql);
		return ci;
	}
//	

//	public double getCIntegralByMobileNo(String mobileno)throws Exception{
//		double ci = 0d;
//		String sql=" select sum(ci.cintegral) from CIntegral as ci where ci.mobileno='"+mobileno+"'";
//		ci = EntityManager.getdoubleSum(sql);
//		return ci;
//	}
	
	
	public double getUpgradeIntegralByCID(String cid)throws Exception{
		double ci = 0d;
		String sql=" select sum(ci.cintegral) from CIntegral as ci where ci.cid='"+cid+"' and ci.iyear='"+DateUtil.getCurrentYear()+"' and ci.isort!=9";
		ci = EntityManager.getdoubleSum(sql);
		return ci;
	}
	
	
	
	
	public void addCIntegralIsNoExist(CIntegral ci)throws Exception{
		
		String sql="insert into c_integral(organid,cid,isort,cintegral,iyear) select '"+ci.getOrganid()+"','"+ci.getCid()+"',"+ci.getIsort()+",0,"+ci.getIyear()+" where not exists (select cid from c_integral where organid='"+ci.getOrganid()+"' and cid='"+ci.getCid()+"' and isort="+ci.getIsort()+" and iyear='"+ci.getIyear()+"')";
		//System.out.println("===---=="+sql);
		EntityManager.updateOrdelete(sql);
		
	}
	
	public void updCIntegralByIntegral(CIntegral ci)throws Exception{
		String sql="update c_integral set cintegral = cintegral + "+ci.getCintegral()+" where organid='"+ci.getOrganid()+"' and cid='"+ci.getCid()+"' and isort="+ci.getIsort()+" and iyear='"+ci.getIyear()+"'";
		EntityManager.updateOrdelete(sql);
	}

	public List getAllCIntegral() throws Exception {
		List aw = null;
		String sql = " from CIntegral as c ";
		aw = EntityManager.getAllByHql(sql);
		return aw;
	}

	public CIntegral getCIntegralByID(Long id) throws Exception {
		String sql = " from CIntegral where id=" + id;
		CIntegral w = (CIntegral) EntityManager.find(sql);
		return w;
	}
	
	public CIntegral getCIntegralByBillNo(String billno,int isort) throws Exception {
		String sql = " from CIntegral where billno='" + billno+"' and isort= "+isort+" ";
		CIntegral w = (CIntegral) EntityManager.find(sql);
		return w;
	}
	

	public void updCIntegral(CIntegral w) throws Exception {
		
		EntityManager.update(w);
		
	}

}
