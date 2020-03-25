package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppMakeConf {

	public MakeConf getMakeConfByID(String tableName) throws Exception {
		String sql = " from MakeConf as mc where mc.tablename='" + tableName + "'";
		return (MakeConf) EntityManager.find(sql);

	}
	
	public List getMakeConf(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String hql = "from MakeConf as mc "
				+ pWhereClause + " order by mc.id ";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public void updMakeConf(MakeConf mc)throws Exception{
		
		EntityManager.update(mc);
		
	}
	
	/**
	 * 更新make_conf
	 * Create Time 2013-11-12 上午10:30:53 
	 * @throws Exception
	 */
	public void updMakeConf(String tableName,String mcTableName) throws  Exception{
		//先上架所有产品,再修改make_conf中organ_product的值
		String sql = "update make_conf set CurrentValue =(select nvl(max(id),0)+1 from " + tableName + ") where TableName='" + mcTableName + "'";
		EntityManager.updateOrdelete(sql); 
	}
	
}
