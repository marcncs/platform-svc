package com.winsafe.drp.dao;

import java.util.List;
import java.util.Map; 

import javax.servlet.http.HttpServletRequest;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppIdcodeUpload {
	public List<IdcodeUpload> getIdcodeUpload(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = "from IdcodeUpload  " + pWhereClause+ " order by makedate desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List<IdcodeUpload> getIdcodeUpload(HttpServletRequest request, int pagesize,
			String pWhereClause, Map<String,Object> param) throws Exception {
		String hql = "from IdcodeUpload  " + pWhereClause+ " order by makedate desc";
		return PageQuery.hbmQuery(request, hql, pagesize, param);
	}

	/**
	 * 查询指定数量的IdcodeUpload，并以日期倒序排列
	 * Create Time: Oct 12, 2011 3:00:44 PM 
	 * @param billsort
	 * @param count
	 * @return
	 * @throws Exception
	 * @author dufazuo
	 */
	public List queryIdcodeUpload(int billsort, int count, String fileName) throws Exception
	{
		String hql = "from IdcodeUpload where billsort = " + billsort + " and filename like '" + fileName + "%' order by makedate desc";
		return EntityManager.queryListWithCount(hql,count);
	}
	
	public List queryIdcodeUpload(int billsort, int count, int userid) throws Exception
	{
		String hql = "from IdcodeUpload where billsort = " + billsort + " and makeid = '" + userid + "' order by makedate desc";
		return EntityManager.queryListWithCount(hql,count);
	}
	
	public List queryIdcodeUpload(String billsort, int count, int userid, String startDate, String endDate) throws Exception
	{
		startDate = startDate + " 00:00:00";
		endDate = endDate + " 23:59:59";
		StringBuffer hqlBuffer = new StringBuffer();
		hqlBuffer.append(" from IdcodeUpload where 1=1  ");
		if(!StringUtil.isEmpty(billsort)){ 
			hqlBuffer.append(" and billsort = " + billsort + "");
		}
		hqlBuffer.append(" and makeid = '" + userid + "' ");
		hqlBuffer.append(" and makedate >= to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss')");
		hqlBuffer.append(" and makedate <= to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss') order by makedate desc");
//		return EntityManager.queryListWithCount(hqlBuffer.toString(),count);
		return EntityManager.getAllByHql(hqlBuffer.toString());
	}

	public void addIdcodeUpload(IdcodeUpload c) throws Exception {
		EntityManager.save(c);
	}
	
	public void updIdcodeUpload(IdcodeUpload c) throws Exception {
		EntityManager.update(c);
	}

	public void delIdcodeUpload(int id) throws Exception {
		String sql = "delete from Idcode_Upload where id=" + id + "";
		EntityManager.updateOrdelete(sql);
	}

	public IdcodeUpload getIdcodeUploadByID(int id) throws Exception {
		String sql = " from IdcodeUpload where id=" + id + "";
		return (IdcodeUpload) EntityManager.find(sql);
	}
	
	public void updIsDeal(int id, int isdeal) throws Exception {
		String sql = "update Idcode_Upload set isdeal="+isdeal+" where id=" + id + "";
		EntityManager.updateOrdelete(sql);
	}
	
	public void updNum(int id, int isdeal, int valinum, int failnum, String failfile) throws Exception {
		String sql = "update Idcode_Upload set isdeal="+isdeal+",valinum="+valinum+",failnum="+failnum+",failfilepath='"+failfile+"' where id=" + id + "";
		EntityManager.updateOrdelete(sql);
	}

	public void updIsUpload(int id, int isdeal) throws Exception {
		String sql = "update Idcode_Upload set isupload="+isdeal+" where id=" + id + "";
		EntityManager.updateOrdelete(sql);
	}

	public boolean isFileAlreadyExists(String hashCode) {
		String sql = "select count(*) from IdcodeUpload where filehashcode = '" + hashCode +"' and isdeal = 2";
		return EntityManager.getCount(sql) > 0;
	}

	/**
	 * 更新状态使其重新处理
	 * Create Time: Oct 8, 2014 4:42:02 PM 
	 * @param uploadId
	 * @author ryan.xi
	 * @throws Exception 
	 */
	public void reProcessFile(Integer uploadId) throws Exception {
		String sql = "update Idcode_Upload set isdeal = 0, isupload=0 where id = " + uploadId +" and isdeal=-1 and isupload=1 ";
//		String sql = "update Idcode_Upload set isdeal = 0 where id = " + uploadId +" and isdeal=-1";
		EntityManager.updateOrdelete(sql);
	}
	
	/**
	 * 单据类型23,24添加对上传的【条码不在当前仓库统计】为警告数量
	 * @param id
	 * @param isdeal
	 * @param valinum
	 * @param failnum
	 * @param warnnum
	 * @param failfile
	 * @throws Exception
	 */
	public void updNum(int id, int isdeal, int valinum, int failnum,int warnnum, String failfile,String fromorganid,String toorganid) throws Exception {
		String sql = "update Idcode_Upload set isdeal="+isdeal+",valinum="+valinum+",failnum="+failnum+", warnnum="+warnnum+",failfilepath='"+failfile+"', fromorganid='" +
				fromorganid+"',toorganid='" + toorganid+"'"+
				"  where id=" + id + "";
		EntityManager.updateOrdelete(sql);
	}
	
}
