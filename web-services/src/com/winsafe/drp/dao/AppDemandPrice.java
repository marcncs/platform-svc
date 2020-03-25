package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;


public class AppDemandPrice {
	
	public List getDemandPrice(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List pls = null;
		int targetPage = pPgInfo.getCurrentPageNo();
		String sql = "select dp.id,dp.cid,dp.linkman,dp.demandname,dp.makeid,dp.makedate,dp.isaudit,dp.auditid,dp.auditdate,dp.totalsum,dp.isover,dp.remark from DemandPrice as dp "
				+ pWhereClause + " order by dp.makedate desc";
		pls = EntityManager.getAllByHql(sql, targetPage, pagesize);
		return pls;
	}
	
	public List getHistoryPrice(String cid,String productid)throws Exception {
		List pls = null;
		String sql = "select dpd.unitprice,dp.makedate from DemandPrice as dp,DemandPriceDetail as dpd where dp.cid='"+cid+"' and dpd.productid='"+productid+"' and dp.id=dpd.dpid order by dp.makedate desc";
		pls = EntityManager.getAllByHql(sql,1,5);
		return pls;
	}

  public void addDemandPrice(Object dpd)throws Exception{
    
    EntityManager.save(dpd);
    
  }
  
  public void updDemandPrice(DemandPrice dp)throws Exception{	    
	    EntityManager.update(dp);	    
	  }
  
  public void updDemandPriceByID(DemandPrice so)
	throws Exception {
	  
	  String sql = "update demand_price set cid='"+so.getCid()+"',linkman='"+so.getLinkman()+"',tel='"+so.getTel()+"',demandname='"+so.getDemandname()+"',totalsum="+so.getTotalsum()+",remark='"+so.getRemark()+"' where ID=" + so.getId()
		+ "";
	  EntityManager.updateOrdelete(sql);
	  
  }
  
  public void delDemandPrice(Long dpid)throws Exception{
		
		String sql="delete from demand_price where id="+dpid+"";
		EntityManager.updateOrdelete(sql);
		
	}
  

  public DemandPrice getDemandPriceByID(Long dpid)throws Exception{
    DemandPrice dp = null;
    String sql = " from DemandPrice as dp where dp.id="+dpid;
    dp = (DemandPrice)EntityManager.find(sql);
    return dp;
  }

  public void delDemandPriceByID(Long dpid)throws Exception{
    
    String sql="delete from demand_price_detail where dpid="+dpid;
    EntityManager.updateOrdelete(sql);
    
  }
  

	public void updIsAudit(Long dpid, Long userid,Integer audit) throws Exception {
		
		String sql = "update demand_price set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id=" + dpid + "";
		EntityManager.updateOrdelete(sql);
		
	}


}
