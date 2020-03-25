package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppOutlayDetail {

	  public void addOutlayDetail(Object od)throws Exception{

	    EntityManager.save(od);

	  }

	  public List getOutlayDetailByOID(String oid)throws Exception{

	    String sql=" from OutlayDetail as d where d.oid='"+oid+"'";
	    List sl = EntityManager.getAllByHql(sql);
	    return sl;
	  }

	  public void delOutlayByOID(String oid)throws Exception{

	    String sql="delete from outlay_detail where oid='"+oid+"'";
	  EntityManager.updateOrdelete(sql);

	  }
}
