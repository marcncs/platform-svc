package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppOutlay {

    public List getOutLay(HttpServletRequest request, int pagesize,
			String pWhereClause)throws Exception{
        String sql="from Outlay as o "
                   +pWhereClause+" order by o.makedate desc";
        return PageQuery.hbmQuery(request, sql, pagesize);
    }

    public Outlay getOutlayByID(String id)throws Exception{
        String sql="from Outlay as o where o.id='"+id+"'";
        Outlay outlay =(Outlay)EntityManager.find(sql);
        return outlay;
    }
    
    public void delOutlay(String id)throws Exception{
		String sql="delete from Outlay where id='"+id+"'";
	EntityManager.updateOrdelete(sql);

	}
    
    public void updOutlay(Outlay ol)throws Exception{
    EntityManager.update(ol);

    }


    public void addOutlay(Object outlay)throws Exception{
      EntityManager.save(outlay);
    }

    
    public void updIsRefer(String id) throws Exception {

      String sql = "update outlay set isrefer=1 where id='" + id+"'";
      EntityManager.updateOrdelete(sql);

    }
    
    
    public void updIsCreate(String id)throws Exception{
    	String sql="update outlay set iscreate=1 where id='"+id+"'";
    	EntityManager.updateOrdelete(sql);

    }

    
    public List waitApproveOutlay(int pagesize, String pWhereClause,
                                      SimplePageInfo pPgInfo) throws Exception {
      int targetPage = pPgInfo.getCurrentPageNo();
      String sql = "select o.id,o.customerid,o.castdept,o.caster,o.totaloutlay,oa.approve,oa.actid from Outlay as o,OutlayApprove as oa " +
          pWhereClause + " order by oa.approve, o.makedate desc";
      List wpa = EntityManager.getAllByHql(sql, targetPage, pagesize);
      return wpa;
    }


    //
    public double getTotalOutlay(String pWhereClause) throws Exception {
      double totalincome = Double.parseDouble("0.00");
      String sql = "select sum(o.totaloutlay) from Outlay as o " +
          pWhereClause+" o.approvestatus =1";
      totalincome = EntityManager.getdoubleSum(sql);
      return totalincome;
    }


	public void updIsAudit(String oid, Integer userid,Integer audit) throws Exception {
		String sql = "update outlay set isaudit="+audit+",auditid=" + userid
				+ ",auditdate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + oid + "'";
		 EntityManager.updateOrdelete(sql);

	}
	
	

	public void updIsEndcase(String oid, Integer userid,Integer audit) throws Exception {
		String sql = "update outlay set isendcase="+audit+",endcaseid=" + userid
				+ ",endcasedate='" + DateUtil.getCurrentDateString()
				+ "' where id='" + oid + "'";
		 EntityManager.updateOrdelete(sql);

	}


}
