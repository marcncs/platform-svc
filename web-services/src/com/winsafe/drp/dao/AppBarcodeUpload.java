package com.winsafe.drp.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppBarcodeUpload {
	
	/**
	 * 增加一条记录
	 * @param c
	 * @throws Exception
	 */
	public void addBarcodeUpload(BarcodeUpload c) throws Exception {
		EntityManager.save(c);
	}
	
	/**
	 * 得到所有barcodeupload记录
	 * @param request
	 * @param pagesize
	 * @param pWhereClause
	 * @return
	 * @throws Exception
	 */
	public List getBarcodeUpload(HttpServletRequest request, int pagesize,
			String pWhereClause) throws Exception {
		String hql = "from BarcodeUpload  " + pWhereClause+ " order by makedate desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getBarcodeUpload(HttpServletRequest request, int pagesize,
			String pWhereClause, Map<String,Object> param) throws Exception {
		String hql = "from BarcodeUpload  " + pWhereClause+ " order by makedate desc";
		return PageQuery.hbmQuery(request, hql, pagesize, param); 
	}
	/**
	 * 更新是否上传
	 * @param id
	 * @param isdeal
	 * @throws Exception
	 */
	public void updIsUpload(int id, int isdeal) throws Exception {
		String sql = "update Barcode_Upload set isupload="+isdeal+" where id=" + id + "";
		EntityManager.updateOrdelete(sql);
	}
	
	/**
	 * 更新处理状态
	 * @param id
	 * @param isdeal
	 * @throws Exception
	 */
	public void updIsDeal(int id, int isdeal) throws Exception {
		String sql = "update Barcode_Upload set isdeal="+isdeal+" where id=" + id + "";
		EntityManager.updateOrdelete(sql);
	}
	/**
	 * 更新状态
	 * @param id
	 * @param isdeal
	 * @param valinum
	 * @param failnum
	 * @param failfile
	 * @throws Exception
	 */
	public void updNum(int id, int isdeal, int valinum, int failnum, String failfile) throws Exception {
		String sql = "update Barcode_Upload set isdeal="+isdeal+",valinum="+valinum+",failnum="+failnum+",failfilepath='"+failfile+"' where id=" + id + "";
		EntityManager.updateOrdelete(sql);
	}
	
	
}
