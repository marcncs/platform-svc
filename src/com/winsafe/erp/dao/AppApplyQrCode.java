package com.winsafe.erp.dao;

import java.sql.SQLException;
import java.util.List; 
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.erp.metadata.QrStatus;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sap.metadata.SyncStatus;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.pojo.ApplyQrCode;
import com.winsafe.sap.pojo.ApplyQrCodeHz;
import com.winsafe.sap.pojo.ApplyScratchCode; 

public class AppApplyQrCode {
	
	public void addApplyQrCode(ApplyQrCode bic) throws Exception {		
		EntityManager.save(bic);		
	}
	
	public void updApplyQrCode(ApplyQrCode bic)throws Exception {		
		EntityManager.update(bic);		
	}
	
	public void deleteApplyQrCode(Integer id) throws HibernateException, SQLException, Exception  {
		String sql = "delete from APPLY_QR_CODE  where  ID=" + id; 
		EntityManager.executeUpdate(sql);
		
	}
	
	public List<Map<String,String>> getApplyQrCodeBySql(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String sql="select aqc.id, aqc.organid,o.organname,aqc.productid,p.productname,p.mcode,p.specmode,p.regcertcode,p.speccode,aqc.quantity,aqc.status,aqc.isaudit,aqc.makedate,aqc.redundance,aqc.filepath,aqc.pono,p.productnameen,p.packSizeNameEn,p.matericalEnDes,aqc.needcovertcode from APPLY_QR_CODE aqc " +
				"join organ o on o.id = aqc.organid " +
				"join product p on p.id = aqc.productid " +
				whereSql;
		return PageQuery.jdbcSqlserverQuery(request, "id desc", sql, pageSize);
	}
	
	public ApplyQrCode getApplyQrCodeByID(Integer id)throws Exception{
		return (ApplyQrCode)EntityManager.find("from ApplyQrCode where id=" + id + "");
	}

	public List<Map<String, String>> getApplyQrCodeById(Integer id) throws Exception {
		String sql="select aqc.id, aqc.organid,o.organname,aqc.productid,p.productname,p.mcode,p.specmode,p.regcertcode,p.speccode,aqc.quantity,aqc.redundance,fu.xquantity packingratio,aqc.pono from APPLY_QR_CODE aqc " +
			"join organ o on o.id = aqc.organid " +
			"join product p on p.id = aqc.productid " +
			"left join f_unit fu on fu.productid = p.id and funitId=" +Constants.DEFAULT_UNIT_ID +
			" where aqc.id ="+id;
		return EntityManager.jdbcquery(sql);
	}

	public List<ApplyQrCode> getAllUnProcessedApply() {
		String hql = "from ApplyQrCode where status = "+QrStatus.NOT_GENERATED.getValue()+" and isAudit ="+YesOrNo.YES.getValue()+" order by id desc";
		return EntityManager.getAllByHql(hql);
	}
	
	public void updApplyScratchCode(ApplyScratchCode bic)throws Exception {		
		EntityManager.update(bic);		
	}
	
	public void addApplyQrCodeHz(ApplyQrCodeHz bic) throws Exception {		
		EntityManager.save(bic);		
	}
	
	public ApplyQrCodeHz getApplyQrCodeHzByID(Integer id)throws Exception{
		return (ApplyQrCodeHz)EntityManager.find("from ApplyQrCodeHz where id=" + id + "");
	}
	
	public void deleteApplyQrCodeHz(Integer id) throws HibernateException, SQLException, Exception  {
		String sql = "delete from APPLY_QR_CODE_HZ  where  ID=" + id; 
		EntityManager.executeUpdate(sql);
		
	}
	
	public List<Map<String,String>> getApplyQrCodeHzBySql(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String sql="select aqc.id, aqc.organid,o.organname,aqc.productid,p.productname,p.mcode,p.specmode,p.regcertcode,p.speccode,aqc.quantity,aqc.status,aqc.isaudit,aqc.makedate,aqc.redundance,aqc.filepath,aqc.pono,p.productnameen,p.packSizeNameEn,p.matericalEnDes,aqc.needcovertcode,aqc.syncStatus from APPLY_QR_CODE_HZ aqc " +
				"join organ o on o.id = aqc.organid " +
				"join product p on p.id = aqc.productid " +
				whereSql;
		return PageQuery.jdbcSqlserverQuery(request, "id desc", sql, pageSize);
	}
	
	public List<Map<String, String>> getApplyQrCodeHzById(Integer id) throws Exception {
		String sql="select aqc.id, aqc.organid,o.organname,aqc.productid,p.productname,p.mcode,p.specmode,p.regcertcode,p.speccode,aqc.quantity,aqc.redundance,fu.xquantity packingratio,aqc.pono from APPLY_QR_CODE_HZ aqc " +
			"join organ o on o.id = aqc.organid " +
			"join product p on p.id = aqc.productid " +
			"left join f_unit fu on fu.productid = p.id and funitId=" +Constants.DEFAULT_UNIT_ID +
			" where aqc.id ="+id;
		return EntityManager.jdbcquery(sql);
	}
	
	public List<ApplyQrCodeHz> getAllUnProcessedApplyHz() {
		String hql = "from ApplyQrCodeHz where status = "+QrStatus.NOT_GENERATED.getValue()+" and isAudit ="+YesOrNo.YES.getValue()+" order by id desc";
		return EntityManager.getAllByHql(hql);
	}

	public List<ApplyQrCodeHz> getNotUploadedLog() {
		String hql = "from ApplyQrCodeHz where status= "+QrStatus.GENERATED.getValue()+" and (syncStatus = "+SyncStatus.NOT_UPLOADED.getValue()+" or syncStatus ="+SyncStatus.UPLOAD_ERROR.getValue()+")";
		return EntityManager.getAllByHql(hql);
	}
}

