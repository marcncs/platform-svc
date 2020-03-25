package com.winsafe.sap.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.metadata.LinkMode;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sap.metadata.PrimaryCodeStatus;
import com.winsafe.sap.metadata.SyncStatus;
import com.winsafe.sap.pojo.PrintJob; 
public class AppPrintJob {
	
	public void addPrintJob(PrintJob d) throws Exception {		
		EntityManager.save(d);		
	}
	
	
	public void addPrintJobs(HashMap<PrintJob, PrintJob> printJobs) throws Exception {		
		EntityManager.batchSave(new ArrayList<PrintJob>(printJobs.values()));	
	}
	
	public List getPrintJob(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql="select p from PrintJob as p "+whereSql +" order by p.printJobId desc";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public PrintJob getPrintJobByID(Integer id)throws Exception{
		return (PrintJob)EntityManager.find("from PrintJob where printJobId="+id);
	}
	
	public PrintJob getPrintJobById(String id)throws Exception{
		return (PrintJob)EntityManager.find("from PrintJob where printJobId="+id);
	} 
	
	public PrintJob getPrintJobByBatAMc(String mcode,String batch)throws Exception{
		return (PrintJob)EntityManager.find("from PrintJob where materialCode = '"+mcode+"' and batchNumber = '"+batch+"' ");
	}

	public PrintJob getPrintJobByBatAPd(String productid,String batch)throws Exception{
		return (PrintJob)EntityManager.find("from PrintJob where productId = '"+productid+"' and batchNumber = '"+batch+"' ");
	}
	
	public void updPrintJob(PrintJob printJob) throws Exception {
		EntityManager.update(printJob);		
		
	}


	public void delPrintJob(Integer printJobId) throws Exception {
//		String sql = "delete print_job where print_job_id ="+printJobId;
		String sql = "update print_job set isdelete = 1 where print_job_id ="+printJobId;
		EntityManager.updateOrdelete(sql);		
		
	}
	
	public PrintJob getPrintJobByID(int id)throws Exception{
		return (PrintJob)EntityManager.find("from PrintJob where printJobId='"+id+"'");
	}

	public List<PrintJob> getPrintJobByWhere(String whereSql) {
		String hql = "from PrintJob " + whereSql;
		return EntityManager.getAllByHql(hql);
	}
	
	public int updPrintJobBySql(String sql) throws Exception {
		return EntityManager.executeUpdate(sql);
	}
	
	public Integer getPrintJobIdForInventoryUpload() throws Exception {
		String sql = "select PRINT_JOB_ID_OLD_SEQ.nextval from dual";
		return EntityManager.getRecordCountBySql(sql);
	}

	public PrintJob getByPidAndBatch(String pid, String batch) {
		return (PrintJob)EntityManager.find("from PrintJob where productId='"+pid+"' and batchNumber='"+batch+"'");
	}
	
	
	public void addPrintJob(String mcode,String batch,String productDate,String packagingDate,String expiryDate) throws Exception {
		String sql = "insert into PRINT_JOB (PRINT_JOB_ID,TRANS_ORDER,MATERIAL_CODE,BATCH_NUMBER,PRODUCTION_DATE,PACKAGING_DATE,EXPIRY_DATE,NUMBER_OF_CASES,TOTAL_NUMBER,MATERIAL_NAME,PRODUCT_ID,PACK_SIZE,UPLOAD_ID,PRINTING_STATUS,PRIMARY_CODE_STATUS,CREATE_DATE,ISDELETE,CONFIRM_FLAG) "
				+ " select print_job_id_OLD_SEQ.nextval,-1,'"+mcode+"','"+batch+"','"+productDate+"','"+packagingDate+"','"+expiryDate+"',1,1*4,pr.PRODUCTNAME,pr.ID,pr.specmode,-1,2,1,sysdate,0,1 from "
				+ " product pr where pr.mcode='"+mcode+"' ";
		EntityManager.updateOrdelete(sql);

	}


	public PrintJob getPrintJobByProcessOrderNumber(String pono) {
		String hql = "from PrintJob where processOrderNumber = '"+pono+"'" ;
		return (PrintJob)EntityManager.find(hql);
	}


	public Map<String,String> getPrintJobByCode(String cartonCode, String primaryCode, String covertCode) throws HibernateException, SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.productname productName,pj.production_date produceDate,pj.expiry_date expiryDate,pj.batch_number batch,PC.CARTON_CODE cartonCode,pj.packaging_date packDate,p.specmode spec from PRIMARY_CODE pc ");
		sql.append("join PRINT_JOB pj on pc.PRINT_JOB_ID = PJ.PRINT_JOB_ID ");
		sql.append("join product p on pj.product_id = p.id ");
		if(cartonCode != null) {
			sql.append("and PC.CARTON_CODE = '"+cartonCode+"' ");
		}
		if(primaryCode != null) {
			sql.append("and PC.PRIMARY_CODE = '"+primaryCode+"' ");
		}
		if(covertCode != null) {
			sql.append("and PC.COVERT_CODE = '"+covertCode+"' ");
		}
		sql.append("ORDER BY pj.CREATE_DATE DESC ");
		List<Map<String,String>> list = EntityManager.jdbcquery(sql.toString());
		if(list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	public Map<String,String> getPrintJobByCartonCode(String cartonCode) throws HibernateException, SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.productname productName,pj.production_date produceDate,pj.expiry_date expiryDate,pj.batch_number batch,PC.CARTON_CODE cartonCode,pj.packaging_date packDate,p.specmode spec from CARTON_CODE pc ");
		sql.append("join PRINT_JOB pj on pc.PRINT_JOB_ID = PJ.PRINT_JOB_ID ");
		sql.append("join product p on pj.product_id = p.id ");
		sql.append("and PC.CARTON_CODE = '"+cartonCode+"' ");
		sql.append("ORDER BY pj.CREATE_DATE DESC ");
		List<Map<String,String>> list = EntityManager.jdbcquery(sql.toString());
		if(list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}


	public List<PrintJob> getNotUploadedPrintJob() { 
		String hql = "from PrintJob where (primaryCodeStatus ="+PrimaryCodeStatus.GENERATED.getDatabaseValue()+" or (productId in (select id from Product where isidcode=1) and confirmFlag = 1)) and syncStatus in ("+SyncStatus.NOT_UPLOADED.getValue()+","+SyncStatus.UPLOAD_ERROR.getValue()+")";
		return EntityManager.getAllByHql(hql);
	}


	public String getBatchNumberByPrimaryCode(String primaryCode) {
		String hql = "select batchNumber from PrintJob where printJobId =(select printJobId from PrimaryCode where primaryCode = '"+primaryCode+"')";
		return (String)EntityManager.find(hql); 
	} 
	
}

