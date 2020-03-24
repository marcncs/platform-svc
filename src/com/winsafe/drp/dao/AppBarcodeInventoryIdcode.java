package com.winsafe.drp.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppBarcodeInventoryIdcode {

	/**
	 * 新增barcodeinventoryidcode
	 * 
	 * @author jason.huang
	 * @param spb
	 * @throws Exception
	 */

	public void addBarcodeInventoryIdcode(Object spb) throws Exception {
		EntityManager.save(spb);
	}

	/**
	 * 判断barcodeinventoryidcode对应单号中是否存在该箱码
	 * 
	 * @param billno
	 * @param idcode
	 * @return
	 * @throws Exception
	 */
	public boolean isExist(String billno, String idcode) throws Exception {
		String sql = "  from BarcodeInventoryIdcode bu where bu.osid='" + billno
				+ "' and bu.idcode='" + idcode + "' ";
		BarcodeInventoryIdcode bii = (BarcodeInventoryIdcode) EntityManager.find(sql);
		if (bii != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param billNo
	 * @param productId
	 * @return
	 */
	public Integer getBarcodeQuantity(String billNo, String productId, String batch) {
		String sql = "select count(*) from BARCODE_INVENTORY_IDCODE where osid = '" + billNo
				+ "' and productid = '" + productId + "' and isidcode=1  and batch_='" + batch
				+ "' ";
		return EntityManager.getRecordCountBySql(sql);

	}

	public List searchBarcodeInventoryIdcode(HttpServletRequest request, int pagesize, String pWhereClause)
			throws Exception {
		String hql = " from BarcodeInventoryIdcode  " + pWhereClause + " order by id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}

	
	public List getBarcodeInventoryIdcodeByOsid(String id) throws Exception {
		String sql = " from BarcodeInventoryIdcode   bii where bii.osid='" + id + "'";
		return EntityManager.getAllByHql(sql);
	}
	
}
