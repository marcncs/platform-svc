package com.winsafe.sap.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;



import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.erp.pojo.DupontCodeUploadLog;
import com.winsafe.erp.pojo.DupontPrimaryCode;
import com.winsafe.hbm.util.pager2.PageQuery;
@SuppressWarnings("unchecked")
public class AppDupontCodeUploadLog {
	
	public void addDupontCodeUploadLog(DupontCodeUploadLog uploadLog) throws Exception {	
		EntityManager.save(uploadLog);		
	}
	
	public void updDupontCodeUploadLog(DupontCodeUploadLog uploadLog)throws Exception {		
		EntityManager.update(uploadLog);		
	}
	
	public List<DupontCodeUploadLog> getDupontCodeUploadLog(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql=" from DupontCodeUploadLog as o "+whereSql+" order by o.id desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}

	
	/**
	 * 查询是否上传过相同的且已经成功处理完的文件
	 * Create Time: Oct 8, 2014 4:42:02 PM 
	 * @param uploadId
	 * @return
	 * @author ryan.xi
	 */
	public boolean isFileAlreadyExists(String hashCode) {
		String sql = "select count(id) from DUPONT_CODE_UPLOAD_LOG where filehashcode = '" + hashCode +"'";
		Integer count = EntityManager.getRecordCountBySql(sql);
		if(count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public void updNum(int id, int isdeal, int errorCount, int totalCount, String logFilePath,  int errorType) throws Exception {
		String sql = "update DUPONT_CODE_UPLOAD_LOG set errorCount="+errorCount+"," +
				"totalCount="+totalCount+",logFilePath='"+logFilePath+" where id=" + id + "";
		EntityManager.updateOrdelete(sql);
	}

	public List<Map<String, String>> executeSql(String sql) throws Exception {
		return EntityManager.jdbcquery(sql);
	}

	public void addDupontPrimaryCodes(List<DupontPrimaryCode> list) {
		EntityManager.batchSave(list);
	}

	public void executeBatchSqls(List<String> sqls) throws Exception {
		EntityManager.executeBatch(sqls);
	}
	
	public void addIdcodeByPrintJob(Integer printJobId, Integer unitId, String warehousId, Double packQuantity) throws Exception {
		String sql = "insert into idcode(IDCODE, PRODUCTID, PRODUCTNAME,BATCH,PRODUCEDATE, UNITID, QUANTITY,ISUSE,ISOUT,MAKEORGANID,CARTONCODE,WAREHOUSEID,PACKQUANTITY,WAREHOUSEBIT,PALLETCODE)" +
						   " select cc.CARTON_CODE , cc.PRODUCT_ID, pj.MATERIAL_NAME, pj.BATCH_NUMBER, pj.PRODUCTION_DATE,"+unitId+",1,1,0,pj.CREATE_USER,cc.CARTON_CODE," +warehousId+","+packQuantity+"," +
						   		"'"+Constants.WAREHOUSE_BIT_DEFAULT +"',cc.PALLET_CODE"+
						   " from CARTON_CODE cc join PRINT_JOB pj on CC.PRINT_JOB_ID = PJ.PRINT_JOB_ID where PJ.PRINT_JOB_ID = " + printJobId+ " " +
						   		"and not exists(select idcode from idcode a where a.idcode=cc.CARTON_CODE)" +
						   		" and not exists(select code from prepare_code pc where pc.code=cc.CARTON_CODE and pc.isrelease=1)";
		EntityManager.executeUpdate(sql);
	}

	public List<DupontPrimaryCode> getDupontPrimaryCode(
			HttpServletRequest request, int pagesize, String whereSql) throws Exception {
		String hql=" from DupontPrimaryCode "+whereSql+" order by primaryCode desc";
		return PageQuery.hbmQuery(request, hql, pagesize); 
	}
}

