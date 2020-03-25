package com.winsafe.drp.dao;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.pager.SimplePageInfo;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppLinkMan {

	public List searchLinkman(int pagesize, String pWhereClause,
			SimplePageInfo pPgInfo) throws Exception {
		List list = null;
		String sql = " from Linkman as l " + pWhereClause
				+ " order by l.makedate desc";
		list = EntityManager.getAllByHql(sql, pPgInfo.getCurrentPageNo(),
				pagesize);
		return list;
	}
	
	public List searchLinkMan(HttpServletRequest request, int pagesize,
			String pWhereClause)throws Exception{
		String hql = " from Linkman as l " + pWhereClause
		+ " order by l.makedate desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	public List searchLinkmanNew(HttpServletRequest request, int pagesize, String pWhereClause) throws Exception {
		String sql = " from Linkman as l  " + pWhereClause
				+ " order by l.makedate desc";
		return PageQuery.hbmQuery(request, sql, pagesize);
	}

	public List initLinkman(int pagesize, String pWhereClause) throws Exception {
		List list = null;
		String sql = "select l.id,l.cid,l.name,l.sex,l.idcard,l.birthday,l.department,l.duty,l.officetel,"
				+ " l.mobile,l.hometel,l.email,l.msn,l.addr,l.ismain,l.userid from Linkman as l  "
				+ pWhereClause + " order by l.makedate desc";
		list = EntityManager.getAllByHql(sql, 1, pagesize);
		return list;
	}

	public void addLinkman(ValidateLinkman linkman, Integer userid)
			throws Exception {
		String flag = "f";
		Linkman addLinkman = new Linkman();
		addLinkman.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
				"linkman", 0, "")));
		addLinkman.setCid(linkman.getCid());
		addLinkman.setName(linkman.getName());
		addLinkman.setSex(Integer.valueOf(linkman.getSex()));
		addLinkman.setIdcard(linkman.getIdcard());
		String birthdate = linkman.getBirthday().replace('-', '/');
		if (birthdate != null && birthdate.trim().length() > 0) {
			addLinkman.setBirthday(new Date(birthdate));
		}
		addLinkman.setDepartment(linkman.getDepartment());
		addLinkman.setDuty(linkman.getDuty());
		addLinkman.setMobile(linkman.getMobile());
		addLinkman.setOfficetel(linkman.getOfficetel());
		addLinkman.setHometel(linkman.getHometel());
		addLinkman.setEmail(linkman.getEmail());
		addLinkman.setQq(linkman.getQq());
		addLinkman.setAddr(linkman.getAddr());
		addLinkman.setMsn(linkman.getMsn());
		addLinkman.setIsmain(Integer.valueOf(linkman.getIsmain()));
		addLinkman.setMakedate(DateUtil.StringToDatetime(DateUtil
				.getCurrentDateTime()));
		EntityManager.save(addLinkman);

	}

	public void addLinkman(Linkman linkman) throws Exception {

		EntityManager.save(linkman);

	}

	public void delLinkman(Integer id) throws Exception {

		String sql = "delete from Linkman where id=" + id;
		EntityManager.updateOrdelete(sql);

	}

	public void delLinkmanByCid(String id) throws Exception {

		String sql = "delete from Linkman where cid='" + id + "'";
		EntityManager.updateOrdelete(sql);

	}

	public Linkman getLinkman(Integer id, String cid, Integer userid)
			throws Exception {
		Linkman linkman = null;

		String hql = "from Linkman as l where l.id=" + id + " and l.cid='"
				+ cid + "' and l.userid=" + userid + "";
		linkman = (Linkman) EntityManager.find(hql);
		return linkman;
	}

	public Linkman getLinkman(Integer id, Integer userid) throws Exception {
		Linkman linkman = null;

		// String
		// hql=" from Linkman as l where l.id="+id+" and l.userid="+userid+"";
		String hql = " from Linkman as l where l.id=" + id;
		linkman = (Linkman) EntityManager.find(hql);
		return linkman;
	}

	public Linkman getLinkmanByID(Integer id) throws Exception {
		Linkman linkman = null;

		String hql = "from Linkman as l where l.id=" + id;
		linkman = (Linkman) EntityManager.find(hql);
		return linkman;

	}

	public Linkman getMainLinkmanByCid(String cid) throws Exception {
		Linkman linkman = null;

		String hql = "from Linkman as l where l.cid='" + cid
				+ "' order by l.ismain desc ";
		List list = EntityManager.getAllByHql(hql);
		if (list.size() > 0) {
			linkman = (Linkman) list.get(0);
		}
		return linkman;

	}

	public List getLinkmanId(String cid, Integer userid) throws Exception {
		List list = null;
		String hql = "select l.id,l.name from Linkman as l where l.cid='" + cid
				+ "' and userid=" + userid + "";
		list = EntityManager.getAllByHql(hql);
		return list;
	}

	public List getLinkmanId(Integer userid) throws Exception {
		List list = null;
		String hql = "select l.id,l.name from Linkman as l where  userid="
				+ userid + "";
		list = EntityManager.getAllByHql(hql);
		return list;
	}

	public List getLinkmanByCid(String cid) throws Exception {
		List list = null;
		String hql = "from Linkman as l where l.cid='" + cid + "'";
		list = EntityManager.getAllByHql(hql);
		return list;
	}

	public void updLinkman(Linkman linkman) throws Exception {

		EntityManager.update(linkman);

	}

	public void updateLinkman(ValidateLinkman linkman, Integer id,
			String birthday, Integer userid) throws Exception {
		// boolean falg=false;

		String hql = "update Linkman set Name='" + linkman.getName() + "',Sex="
				+ linkman.getSex() + ",IdCard='" + linkman.getIdcard()
				+ "',Birthday='" + birthday + "',Department='"
				+ linkman.getDepartment() + "',Duty='" + linkman.getDuty()
				+ "',OfficeTel='" + linkman.getOfficetel() + "',Mobile='"
				+ linkman.getMobile() + "',HomeTel='" + linkman.getHometel()
				+ "',Email='" + linkman.getEmail() + "',Qq='" + linkman.getQq()
				+ "',Msn='" + linkman.getMsn() + "',Addr='" + linkman.getAddr()
				+ "',IsMain=" + linkman.getIsmain() + " where ID=" + id
				+ " and UserID=" + userid + "";
		// System.out.println("hql================="+hql);
		EntityManager.updateOrdelete(hql);

	}

	public void updateLinkman(Linkman linkman, String birthday)
			throws Exception {

		String bd = birthday.equals("") ? null : "'" + birthday + "'";
		String hql = "update Linkman set Name='" + linkman.getName() + "',Sex="
				+ linkman.getSex() + ",IdCard='" + linkman.getIdcard()
				+ "',Birthday=" + bd + ",Department='"
				+ linkman.getDepartment() + "',Duty='" + linkman.getDuty()
				+ "',OfficeTel='" + linkman.getOfficetel() + "',Mobile='"
				+ linkman.getMobile() + "',HomeTel='" + linkman.getHometel()
				+ "',Email='" + linkman.getEmail() + "',Qq='" + linkman.getQq()
				+ "',Msn='" + linkman.getMsn() + "',Addr='" + linkman.getAddr()
				+ "',IsMain=" + linkman.getIsmain() + ",Transit="
				+ linkman.getTransit() + " where ID=" + linkman.getId();
		// System.out.println("hql================="+hql);
		EntityManager.updateOrdelete(hql);

	}
}
