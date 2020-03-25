package com.winsafe.drp.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;

public class AppIdcodeUploadLog {

	public void addIdcodeUploadLogs(Collection<ErrorBean> errorBeans, IdcodeUpload idcodeUpload, String outWarehouse, String billNo) throws Exception {
		List<String> batchSqls = new ArrayList<String>();
		for(ErrorBean errorBean : errorBeans){
			String sql = "INSERT INTO IDCODE_UPLOAD_LOG VALUES(seq_idcode_upload_log.nextval, '"
				+ StringUtil.removeNull(billNo)
				+ "', to_date('"
				+ DateUtil.formatDate(idcodeUpload.getMakedate(), "yyyy-MM-dd HH:mm:ss")
				+ "','yyyy-MM-dd:hh24:mi:ss'), "
				+ idcodeUpload.getMakeid()
				+ " , '"
				+ errorBean.getInfo()
				+ "', "
				+ idcodeUpload.getId()
				+ ", "
				+ idcodeUpload.getBillsort() 
				+ ", '"
				+ idcodeUpload.getMakeorganid() 
				+ "', '"
				+ StringUtil.removeNull(outWarehouse)
				+ "', '"
				+ errorBean.getErrCode()
			    + "', '"
				+ StringUtil.removeNull(errorBean.getIdcode())
				+ "')";
			batchSqls.add(sql);
			if(batchSqls.size() == Constants.DB_BULK_SIZE) {
				EntityManager.executeBatch(batchSqls);
				batchSqls.clear();
			}
		}
		EntityManager.executeBatch(batchSqls);
	}
	
	public List getIdcodeUploadLogs(HttpServletRequest request,
			int pagesize, String whereSql) throws Exception {
		String hql=" from IdcodeUploadLog as o "+whereSql +" order by o.id desc";
		return PageQuery.hbmQuery(request, hql, pagesize);
	}
	
	public List getIdcodeUploadLogs(HttpServletRequest request,
			int pagesize, String whereSql, Map<String,Object> param) throws Exception {
		String hql=" from IdcodeUploadLog as o "+whereSql +" order by o.id desc";
		return PageQuery.hbmQuery(request, hql, pagesize, param); 
	}
	
	public void addIdcodeUploadLogs(String failMsg, String billNo,String userid,Integer billSort, String organId, String warehouseId, String idcode) throws Exception {
		String sql = "INSERT INTO IDCODE_UPLOAD_LOG (ID,BILLNO,UPLOAD_DATE,UPLOAD_USER,ERROR_MSG,IDCODE_UPLOAD_ID,BSORT,UPLOAD_ORGAN_ID,UPLOAD_WAREHOUSE_ID,ERROR_CODE,IDCODE) VALUES(seq_idcode_upload_log.nextval, '"
			+ StringUtil.removeNull(billNo)
			+ "', to_date('"
			+ DateUtil.formatDate(Dateutil.getCurrentDate(), "yyyy-MM-dd HH:mm:ss")
			+ "','yyyy-MM-dd:hh24:mi:ss'), "
			+ userid
			+ " , '"
			+ failMsg
			+ "', "
			+ null
			+ ", "
			+ billSort 
			+ ", '"
			+ organId 
			+ "', '"
			+ warehouseId
			+ "', '"
			+ failMsg.split(":")[0]
		    + "', '"
			+ idcode
			+ "')";
		EntityManager.executeUpdate(sql);
	}
}
