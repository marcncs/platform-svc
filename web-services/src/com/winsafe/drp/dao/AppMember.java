package com.winsafe.drp.dao;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.Encrypt; 
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppMember {

	public List getMembers(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = " from Member as u  " + pWhereClause
				+ " order by u.id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public Member getMember(String loginname) throws Exception {
		String sql = " from Member as u where u.loginname= '" + loginname + "'";
		return (Member) EntityManager.find(sql);
	}

	public int getCountMember() throws Exception {
		String sql = " select count(*) from Member ";
		return EntityManager.getRecordCount(sql);
	}

	public void InsertMember(Member Member) throws Exception {
		EntityManager.save(Member);
	}

	public void updMember(Member Member) throws Exception {
		EntityManager.update(Member);
	}

	public void setOnline(int Memberid) throws Exception {
		String sql = "update Member set isonline=1 where Memberid=" + Memberid;
		EntityManager.updateOrdelete(sql);
	}

	public void setOffline(int Memberid) throws Exception {
		String sql = "update Member set isonline=0 where Memberid=" + Memberid;
		EntityManager.updateOrdelete(sql);
	}

	public void delMemberById(int Memberid) throws Exception {
		EntityManager
				.updateOrdelete("delete from Member where Memberid=" + Memberid);
	}

	public void resetMemberPWD(int uid, String pwd) throws Exception {
		String sql = "update Member set password='" + pwd + "' where Memberid="
				+ uid;
		EntityManager.updateOrdelete(sql);

	}

	public boolean CheckMemberPasswordByMemberID(int Memberid, String password)
			throws Exception {
		String sql = " from Member as u where u.Memberid='" + Memberid
				+ "' and u.password='" + password + "'";
		Member Member = (Member) EntityManager.find(sql);
		if (Member != null) {
			return true;
		}
		return false;
	}

	public List getAllAreas() throws Exception {
		String sql = "from CountryArea";
		return EntityManager.getAllByHql(sql);
	}

	public List getAllMemberAreas(int Memberid) throws Exception {
		return EntityManager.getAllByHql("from MemberArea as ua where ua.Memberid="
				+ Memberid);
	}

	public void deleteMemberAreas(int Memberid) throws Exception {
		EntityManager.updateOrdelete("delete from Member_area where Memberid="
				+ Memberid);
	}

	/**
	 * 通过登录名找到相对应的有效期字段
	 * 
	 * @param Membername
	 * @return
	 * @throws Exception
	 */
	public Date getValByUname(String Membername) throws Exception {
		Member Member = null;
		Date MemberVal = null;
		String sql = " from Member as u where u.loginname='" + Membername
				+ "'  and islogin=1";
		Member = (Member) EntityManager.find(sql);
		if (Member != null) {
			MemberVal = Member.getValidate();
		}
		return MemberVal;

	}

	/**
	 * 通过登录名找到相对应的有效期字段(key列)
	 * 
	 * @param Membername
	 * @return
	 * @throws Exception
	 */
	public String getKeyByUname(String Membername) throws Exception {
		Member Member = null;
		String key = "";
		String sql = " from Member as u where u.loginname='" + Membername
				+ "'  and islogin=1";
		Member = (Member) EntityManager.find(sql);
		if (Member != null) {
			key = Member.getValkey();
		}
		return key;

	}

	/**
	 * 每次登陆成功后通过配置文件的值改变所有人员的有效期
	 * 
	 * @param MemberVal
	 * @throws Exception
	 */
	public void updateMemberValKey(String valkey) throws Exception {
		String sql = "update Member set valkey='" + valkey + "'";
		EntityManager.updateOrdelete(sql);

	}

	/**
	 * 通过登录名找到相对应的最后登录时间
	 * 
	 * @param Membername
	 * @return
	 * @throws Exception
	 */
	public Date getLastTimeByUname(String Membername) throws Exception {
		Date date = null;
		Member Member = new Member();
		String sql = " from Member as u where u.loginname='" + Membername
				+ "'  and islogin=1";
		Member = (Member) EntityManager.find(sql);
		if (Member != null) {
			date = Member.getLastlogin();
		}
		return date;

	}

	public void updateLastLogin(String Membername) throws Exception {
		String sql = "update Member set LastLogin=sysdate,logintimes=nvl(logintimes,0)+1 where loginname='"
				+ Membername + "' ";
		EntityManager.updateOrdelete(sql);
	}
	
	public Member getMemberByMobile(String mobile) throws Exception {
		mobile = Encrypt.getSecret(mobile, 3);
		String sql = " from Member as u where u.mobile='" + mobile+"'";
		return (Member) EntityManager.find(sql);
	}
	
	public Member CheckMemberNamePassword(String username, String password)
		throws Exception {
		Users users = null;
		String sql = " from Member as u where upper(u.loginname)=upper('" + username
				+ "') and u.password='" + password + "'";
		return (Member) EntityManager.find(sql);
	}

	public List getAllMember(String whereSql) {
		String hql = " from Member as u  " + whereSql
			+ " order by u.id desc";
		return EntityManager.getAllByHql(hql);
	}

}
