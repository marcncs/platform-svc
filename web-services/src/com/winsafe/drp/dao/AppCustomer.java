package com.winsafe.drp.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;
@SuppressWarnings("unchecked")
public class AppCustomer {


	public List searchCustomer(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List list = null;
		String sql = "select c.cid,c.cname,c.yauld,c.officetel,c.customertype,c.customerstatus,c.source,c.makedate,c.makeid,c.specializeid from Customer as c, UserArea as ua "
				+ pWhereClause + " order by c.makedate desc";
		list = EntityManager.getAllByHql(sql, pPgInfo.getCurrentPageNo(),
				pagesize);

		return list;
	}

	public List searchCustomerNew(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List list = null;
		String sql = "select c.cid,c.cname,c.yauld,c.officetel,c.customertype,c.customerstatus,c.source,c.makedate,c.makeid,c.specializeid from Customer as c  "
				+ pWhereClause + " order by c.makedate desc";
		list = EntityManager.getAllByHql(sql, pPgInfo.getCurrentPageNo(),
				pagesize);

		return list;
	}

	public List searchMember(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List list = null;
		String sql = "select c.cid,c.cname,c.membersex,c.membercompany,c.mobile,c.officetel,c.source,c.makedate,c.makeid from Customer as c "
				+ pWhereClause + " order by c.cid desc";
		list = EntityManager.getAllByHql(sql, pPgInfo.getCurrentPageNo(),
				pagesize);
		return list;
	}

	public List searchMember(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = "from Customer as c " + pWhereClause
				+ " order by c.makedate desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public List searchCustomerByDitch(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List list = null;
		String sql = "select c.cid,c.cname,c.yauld,c.officetel,c.customertype,c.customerstatus,c.source,c.makedate,c.makeid,c.specializeid from Customer as c "
				+ pWhereClause + " order by c.registdate desc";
		list = EntityManager.getAllByHql(sql, pPgInfo.getCurrentPageNo(),
				pagesize);
		return list;
	}

	public List searchCustomer(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = "from Customer as c " + pWhereClause
				+ " order by c.makedate desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public List searchCustomer2(String pWhereClause) throws Exception {
		String sql = "from Customer as c " + pWhereClause
				+ " order by c.makedate desc";
		return EntityManager.getAllByHql(sql);
	}

	public List searchRetailCustomer(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List list = null;
		String sql = "select c from Customer as c,RetailSet as rs "
				+ pWhereClause + " order by c.registdate desc";
		list = EntityManager.getAllByHql(sql, pPgInfo.getCurrentPageNo(),
				pagesize);
		return list;
	}

	public List getAllCustomer(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List list = null;
		String sql = "select c.cid,c.cname,c.officetel,c.customertype,c.customerstatus,c.makedate from Customer as c  "
				+ pWhereClause + " order by c.makedate desc";

		list = EntityManager.getAllByHql(sql, pPgInfo.getCurrentPageNo(),
				pagesize);
		return list;
	}

	public void addCustomer(Object customer) throws Exception {

		EntityManager.save(customer);

	}

	public void appointsCustomer(String sid, String cid) throws Exception {

		String sql = "update customer set specializeid='" + sid
				+ "' where cid='" + cid + "'";
		EntityManager.updateOrdelete(sql);

	}

	public void updContactDate(String cid, String currDate, String nextDate)
			throws Exception {

		String sql = "update customer set LastContact='" + currDate
				+ "',NextContact='" + nextDate + "' where cid='" + cid + "' ";
		EntityManager.updateOrdelete(sql);

	}

	public List getCustoemrUsersByCID(String cid) throws Exception {
		List list = null;
		String sql = "select cu.id, cu.uid from CustomerUsers as cu where cu.cid='"
				+ cid + "'";
		list = EntityManager.getAllByHql(sql);
		return list;
	}

	public void delCustomerUsersByCID(String cid) throws Exception {

		String sql = "delete from Customer_Users where cid='" + cid
				+ "' and iscreate!=1";
		EntityManager.updateOrdelete(sql);

	}

	public void updateCustomer(Customer c) throws Exception {

		EntityManager.update(c);

	}

	public boolean customerNameExist(String customerName) throws Exception {
		boolean flag = false;

		String hql = "from Customer as c where c.cname='" + customerName + "'";
		Customer customer = (Customer) EntityManager.find(hql);
		if (customer != null) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	public String getCName(String cid) throws Exception {
		if (cid == null || "".equals(cid) || "null".equals(cid)) {
			return "";
		}
		Customer c = getCustomer(cid);
		if (c != null) {
			return c.getCname();
		}
		return "";
	}

	public Customer getCustomer(String cid) throws Exception {
		String hql = " from Customer as c where  c.cid='" + cid + "'";
		return (Customer) EntityManager.find(hql);
	}

	public List getCustomerBySql(String wsql) throws Exception {
		List list = null;
		String sql = "from Customer  " + wsql + " order by makedate desc";
		list = EntityManager.getAllByHql(sql);
		return list;
	}

	public Customer getCustomerByMobile(String mobile) throws Exception {
		Customer customer = null;
		String hql = " from Customer as c where  c.mobile='" + mobile + "'";
		customer = (Customer) EntityManager.find(hql);
		return customer;
	}

	public Customer getCustomerByTel(String officetel) throws Exception {
		Customer customer = null;
		String hql = " from Customer as c where  c.officetel='" + officetel
				+ "'";
		customer = (Customer) EntityManager.find(hql);
		return customer;
	}

	public List getAllCustomer() throws Exception {
		List customer = null;
		String hql = "from Customer ";
		customer = EntityManager.getAllByHql(hql);
		return customer;
	}

	public int getCustomerCount(String pWhereClause) throws Exception {
		int c = 0;
		String hql = "select count(c.cid) from Customer as c " + pWhereClause;
		c = EntityManager.getRecordCount(hql);
		return c;
	}

	public List getCustomerReport(String pWhereClause, String p)
			throws Exception {
		List ls = new ArrayList();
		String hql = "select count(c.cid) as count," + p + " from Customer as c "
				+ pWhereClause + " group by c." + p;
		
		
		List rs = EntityManager.jdbcquery(hql);
		CustomerReportForm crf = null;
		for(int  i = 0; i<rs.size() ; i++){
			crf = new CustomerReportForm();
			Map map = (Map) rs.get(i);
			crf.setCidcount(Integer.valueOf(map.get("count").toString()));
			crf.setReportid(Integer.valueOf(map.get(p).toString()));
			ls.add(crf);
		}
		return ls;
	}

	public List getCustomerExpand(String registdate, int dept) throws Exception {
		List ls = new ArrayList();

		String sql = "select count(c.cid),cast(DATEPART(yy,c.registdate) as varchar(4)) +'-'+cast(DATEPART(MM,c.registdate)as varchar(4)) from Customer as c,users as u,customer_users as cu ";
		sql += " where cast(DATEPART(yy,c.registdate) as varchar(4)) +'-'+cast(DATEPART(MM,c.registdate)as varchar(4))='"
				+ registdate
				+ "' and c.cid=cu.cid and cu.uid=u.userid and u.dept= "
				+ dept
				+ " ";
		sql += "group by cast(DATEPART(yy,c.registdate) as varchar(4)) +'-'+cast(DATEPART(MM,c.registdate)as varchar(4))";
		ResultSet rs = EntityManager.query(sql);
		CustomerExpandForm cef = null;
		if (!rs.first()) {
			cef = new CustomerExpandForm();
			cef.setCidcount(Integer.valueOf(0));
			cef.setRegistdate(registdate);
			ls.add(cef);
		} else {
			cef = new CustomerExpandForm();
			cef.setCidcount(rs.getInt(1));
			cef.setRegistdate(rs.getString(2));
			ls.add(cef);
			rs.close();
		}
		return ls;
	}

	public List getCustomerExpandDept() throws Exception {
		List ls = new ArrayList();
		String sql = "select u.dept from customer as c,users as u,customer_users as cu where u.userid=cu.uid and c.cid=cu.cid and cu.iscreate=1 group by u.dept";
		ResultSet rs = EntityManager.query(sql);
		CustomerExpandDeptForm cedf = null;
		do {
			cedf = new CustomerExpandDeptForm();
			cedf.setDeptid(rs.getInt(1));
			ls.add(cedf);
		} while (rs.next());
		rs.close();
		return ls;
	}

	public int getMancountByRegieID(Long regieid) throws Exception {
		int m = 0;
		String sql = "select count(*) from Customer where regieid=" + regieid;
		m = EntityManager.getRecordCountQuery(sql);
		return m;

	}

	public void updCustomerToDel(String cid) throws Exception {

		String sql = "update customer set isdel=1 where cid='" + cid + "'";
		EntityManager.updateOrdelete(sql);

	}

	public int getCustomerByCcode(String cid) throws Exception {
		int c = 0;
		String sql = "select count(*) from Customer where cid='" + cid + "'";
		c = EntityManager.getRecordCountQuery(sql);
		return c;
	}

	public int getRepeatCustomerByMobile(String mobile) throws Exception {
		int c = 0;
		String sql = "select count(*) from Customer where mobile='" + mobile
				+ "' and isdel=0 ";
		c = EntityManager.getRecordCountQuery(sql);
		return c;
	}

	public String getMaxCustomerIdByAreas(Integer areas) throws Exception {
		String sql = "select MAX(SUBSTRING(cid, LEN(cid)-3, 4)) from Customer where areas="
				+ areas;
		return (String) EntityManager.find(sql);
	}

	public List getCustomerExpandUsers(String organid) throws Exception {
		List ls = new ArrayList();
		String sql = "select c.makeid from customer as c where makeorganid = '"+organid+"'";
		ResultSet rs = EntityManager.query(sql);
		CustomerExpandDeptForm cedf = null;
		do {
			cedf = new CustomerExpandDeptForm();
			cedf.setUserid(rs.getString(1));
			ls.add(cedf);
		} while (rs.next());
		rs.close();
		return ls;
	}

	public List getCustomerExpand(String registdate, String userid)
			throws Exception {
		List ls = new ArrayList();

		String sql = "select count(c.cid),subString(CONVERT(varchar, c.makedate,21),0,8) from customer as c ";
		sql += " where subString(CONVERT(varchar, c.makedate,21),0,8)='"
				+ registdate + "' and c.makeid='" + userid
				+ "' and c.isdel=0 ";
		sql += "group by subString(CONVERT(varchar, c.makedate,21),0,8)";
		ResultSet rs = EntityManager.query(sql);
		CustomerExpandForm cef = null;
		if (rs.getRow() <= 0) {
			cef = new CustomerExpandForm();
			cef.setCidcount(Integer.valueOf(0));
			cef.setRegistdate(registdate);
			ls.add(cef);
		} else {
			// do{
			cef = new CustomerExpandForm();
			cef.setCidcount(rs.getInt(1));
			cef.setRegistdate(rs.getString(2));
			ls.add(cef);
			// }while(rs.next());
			rs.close();
		}
		// System.out.println("----------"+ls.size());
		return ls;
	}

}
