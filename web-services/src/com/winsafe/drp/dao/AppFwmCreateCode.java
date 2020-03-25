package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppFwmCreateCode {
	public List<ClientProduct> getClientProductByGuangMing(String name)
	throws Exception {
String hql = "from ClientProduct where clientName like '%" + name
		+ "%'";
return EntityManager.getAllByHql(hql);
}

public List selectFwm(HttpServletRequest request, int pageSize,
	String whereSql) throws Exception {
String hql = " from FwmCreate f " + whereSql + " order by f.fwmCodeFile desc";
return PageQuery.hbmQuery(request, hql, pageSize);
}

public void AddFwmCreate(FwmCreate d) throws Exception {
EntityManager.save(d);
}

public void UpdateFwmCreate(FwmCreate d) throws Exception {
Session s = HibernateUtil.currentSession(true);
s.getTransaction().begin();
EntityManager.update(d);
s.getTransaction().commit();
}

/**
* 根据ID查询客户信息
* 
* @throws Exception
*/
public FwmCreate getFwmCreateByID(Integer id) throws Exception {
String sql = "from FwmCreate where id = " + id + "";
return (FwmCreate) EntityManager.find(sql);
}

/**
* 根据客户名称查询客户信息
* 
* @throws Exception
*/
public List<FwmCreate> getFwmCreate(String fwmClientName,
	String fwmProductName, String fwmCreateDate) throws Exception {
String sql = "from FwmCreate where fwmClientName = '" + fwmClientName
		+ "' " + " and fwmProductName = '" + fwmProductName
		+ "' and fwmCreateDate = " + fwmCreateDate + "";
return EntityManager.getAllByHql(sql);
}

/**
* 更改防伪码生成状态的信息
* 
* @throws Exception
*/
public void updateFwmInfo(int isDeal, int id) throws Exception {
String sql = "update fwmcreate set isDeal = " + isDeal + " where id = "
		+ id;
EntityManager.updateOrdelete(sql);
}

public void insertFwmCreate(String enterpriseName, String enterpriseId,
	String productname, String producterpcode, String daoformat,
	Integer fwmcreatlength, Integer fwmnum, Integer FwmBuMaLv,
	String Productbrand, String ordercode, Integer IsDeal, String Type,
	Integer IsApply, Integer isapprove, String productcode,
	String codeApplyID, String relation)throws Exception {
// String
// sql="insert into user_log(userid,modeltype,logtime,detail) values("+userid+","+modeltype+",sysdate,'"+content+"')";
String sql = "insert into fwmcreate(FWMCLIENTNAME,FWMCLIENTCODE,FWMPRODUCTNAME,FWMPRODUCTCODE,FWMDAOFORMAT,FWMLENGTH,FWMNUM,FWMBUMALV,ISDEAL,FWMCODEFILE,PRODUCTBRAND,ORDERCODE,ATYPE,ISAPPLY,ISAPPROVE,ICODE,RELATION)  "
		+ "values('"
		+ enterpriseName
		+ "','"
		+ enterpriseId
		+ "','"
		+ productname
		+ "','"
		+ producterpcode
		+ "','"
		+ daoformat
		+ "',"
		+ fwmcreatlength
		+ ","
		+ fwmnum
		+ ","
		+ FwmBuMaLv
		+ ","
		+ IsDeal
		+ ",'"
		+ codeApplyID
		+ "','"
		+ Productbrand
		+ "','"
		+ ordercode
		+ "','"
		+ Type
		+ "',"
		+ IsApply
		+ ","
		+ isapprove
		+ ",'"
		+ productcode
		+ "','"
		+ relation + "')";

EntityManager.updateOrdelete(sql);

}
}
