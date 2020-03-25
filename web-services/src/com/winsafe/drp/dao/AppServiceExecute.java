package com.winsafe.drp.dao;

import java.util.List;

import com.winsafe.drp.entity.EntityManager;

public class AppServiceExecute {

	
	public List getExecuteIDByServiceID(Integer said) throws Exception {
		List els = null;
		String sql = " select se.id,se.said,se.userid,se.isaffirm from ServiceExecute as se where se.said="
				+ said;
		els = EntityManager.getAllByHql(sql);
		return els;
	}

	public List getExecuteIDByServiceID(String whereSql) throws Exception {
		List els = null;
		String sql = " select se.id,se.said,se.userid,se.isaffirm from ServiceExecute as se "
				+ whereSql;
		els = EntityManager.getAllByHql(sql);
		return els;
	}

	
	public void addExecute(Object s) throws Exception {

		EntityManager.save(s);

	}

	
	public void updIsAffirmServiceExecute(Integer said, Integer userid)
			throws Exception {

		String sql = "update service_execute set isaffirm=1 where said=" + said
				+ " and userid=" + userid;
		EntityManager.updateOrdelete(sql);

	}

	public ServiceExecute getServiceExecute(Integer said, Integer userid)
			throws Exception {
		ServiceExecute te = new ServiceExecute();
		String sql = " from ServiceExecute where sasid=" + said
				+ " and userid=" + userid;
		te = (ServiceExecute) EntityManager.find(sql);
		return te;
	}

	
	public void delServiceExecuteBySAID(Integer said) throws Exception {

		String sql = "delete from service_execute where said =" + said;
		EntityManager.updateOrdelete(sql);

	}

	public List getServiceExecuteUsers(Integer id) {
		String sql = " select te.userid from ServiceExecute as te where te.said="
				+ id;
		return EntityManager.getAllByHql(sql);
	}

}
