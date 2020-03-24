package com.winsafe.erp.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;




import com.winsafe.drp.entity.EntityManager;
import com.winsafe.erp.metadata.CartonSeqStatus;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.pojo.CartonSeq;
import com.winsafe.sap.pojo.CartonSeqLog;

public class AppCartonSeq {
	
	public void addCartonSeq(CartonSeq bic) throws Exception {		
		EntityManager.save(bic);		
	}
	
	public void addCartonSeqLog(CartonSeqLog bic) throws Exception {		
		EntityManager.save(bic);		
	}
	
	public void updCartonSeq(CartonSeq bic)throws Exception {		
		EntityManager.update(bic);		
	}
	
	public CartonSeq getCartonSeq(String pid, String seq)throws Exception {	
		String hql = "from CartonSeq where productId='"+pid +"' and seq='" + seq+"'";
		return (CartonSeq)EntityManager.find(hql);		
	}
	
	public String getMaxSeqByProductId(String productId)throws Exception {
		String hql = "select max(seq) from CartonSeq where productId = '"+productId+"'"; 
		return (String)EntityManager.find(hql);
	}

	public void addBatch(List<Object> codeList) {
		EntityManager.batchSave(codeList);
	}

	public List<String> getUsableSeqByProductId(String productId) {
		String hql = "select seq from CartonSeq where productId = '"+productId+"' and status ="+CartonSeqStatus.NOT_USED.getValue()+" order by id"; 
		return EntityManager.getAllByHql(hql);
		
	}
	
	public List<String> getUsableSeqForActivationByProductId(String productId) {
		String hql = "select seq from CartonSeq where productId = '"+productId+"' and status in ("+CartonSeqStatus.NOT_USED.getValue()+","+CartonSeqStatus.ACTIVATING.getValue()+") order by id"; 
		return EntityManager.getAllByHql(hql);
		
	}

	public int executeBatchWithResult(List<String> batchSqls) throws Exception {
		int result = 0;
		int results[] = EntityManager.executeBatchWithResult(batchSqls);
		for(int r : results) {
			result+=r;
		}
		return result;
	}
	
	public List<Map<String, String>> getCartonSeq(
			HttpServletRequest request, int pageSize, int printJobId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select cc.CARTON_CODE CARTONCODE,to_number(CS.SEQ) SEQ,CC.PALLET_CODE PALLETCODE,CC.PRINT_SEQ printSeq,pc.isrelease,pj.material_name,pj.pack_size,pj.packaging_date,p.MCODE,pj.production_date,p.MATERICALCHDES,CC.OUT_PIN_CODE from CARTON_CODE cc ");
		sql.append(" left join CARTON_SEQ cs on cs.CARTONCODE=CC.CARTON_CODE ");
		sql.append(" join PREPARE_CODE pc on pc.code = CC.CARTON_CODE ");
		sql.append(" join PRINT_JOB pj on CC.PRINT_JOB_ID =pj.PRINT_JOB_ID  ");
		sql.append(" join PRODUCT p on p.id=pj.product_id ");
		sql.append(" where CC.PRINT_JOB_ID = "+printJobId);
		if(pageSize == 0) {
			sql.append(" order by CC.PRINT_SEQ ");
			return EntityManager.jdbcquery(sql.toString());
		}
		return PageQuery.jdbcSqlserverQuery(request, "printSeq", sql.toString(), pageSize);
	}

	public List<String> getAvailableSeqList(String productId,
			String newSeqs, String oldCartonCodes) {
		String hql = "select seq from CartonSeq where productId ='"+productId+"' and ((seq in ("+newSeqs+") and status="+CartonSeqStatus.NOT_USED.getValue()+") or cartonCode in ("+oldCartonCodes+"))";
		return EntityManager.getAllByHql(hql);
	}

	public void releaseCartonSeq(String cartonCodesString) throws Exception { 
		String sql ="update CARTON_SEQ set STATUS = "+CartonSeqStatus.NOT_USED.getValue()+",CARTONCODE=null where CARTONCODE in ("+cartonCodesString+")";
		EntityManager.executeUpdate(sql);
	}

	public List<Map<String, String>> getCartonSeqLog(
			HttpServletRequest request, int pageSize, String whereSql) throws Exception {
		String sql = " select csl.id,o.ORGANNAME,p.productname,p.specmode,p.mcode,CSL.RANGE,CSL.MAKEDATE,p.productnameen,p.packSizeNameEn,p.matericalEnDes,csl.batch,csl.productionDate,csl.packingDate,csl.inspectionDate,csl.inspectionInstitution from CARTON_SEQ_LOG csl " +
				"join organ o on o.id = CSL.ORGANID " + 
				"join product p on p.id = csl.productid "+whereSql;
		return PageQuery.jdbcSqlserverQuery(request, "id desc", sql, pageSize);
	}

	public void releaseCartonSeqByPlanId(long planId) throws Exception {
		String sql = "update CARTON_SEQ set STATUS = "+CartonSeqStatus.NOT_USED.getValue()+",CARTONCODE=null where CARTONCODE in (select code from prepare_code pc where pc.productplan_id="+planId+" and pc.isrelease=1)";
		EntityManager.executeUpdate(sql);
	}

	public void closeCartonSeq(long planId) throws Exception {
		String sql = "update CARTON_SEQ set status = "+CartonSeqStatus.USED.getValue()+" where cartoncode in (select code from prepare_code p where p.productplan_id="+planId+" ) and status = "+CartonSeqStatus.LOCKED.getValue();
		EntityManager.executeUpdate(sql);
	}

	public void updPrimaryCodeCartonCode(long planId, Integer printJobId) throws Exception {
		String sql = "update PRIMARY_CODE pc set pc.is_used = "+YesOrNo.YES.getValue()+", " +
				"PRINT_JOB_ID = "+printJobId+"," +
				"CARTON_CODE = (select cartoncode from CARTON_SEQ where PRODUCTID||SEQ = PC.CARTON_CODE) " +
				"where pc.carton_code in (" +
				"select productid||seq from CARTON_SEQ where cartoncode in (" +
				"select code from prepare_code p where p.productplan_id=" +planId+
				")) and pc.is_used = "+YesOrNo.NO.getValue();
		EntityManager.executeUpdate(sql);
	}
	
	public void closeCartonSeq(Integer printJobId) throws Exception {
		String sql = "update CARTON_SEQ set status = "+CartonSeqStatus.USED.getValue()+" where cartoncode in (select CARTON_CODE from CARTON_CODE where PRINT_JOB_ID = "+printJobId+" ) and status = "+CartonSeqStatus.LOCKED.getValue();
		EntityManager.executeUpdate(sql);
	}
	
	public void updPrimaryCodeCartonCode(Integer printJobId) throws Exception {
		String sql = "update PRIMARY_CODE pc set pc.is_used = "+YesOrNo.YES.getValue()+", " +
				"PRINT_JOB_ID = "+printJobId+"," +
				"CARTON_CODE = (select cartoncode from CARTON_SEQ where PRODUCTID||SEQ = PC.CARTON_CODE) " +
				"where pc.carton_code in (" +
				"select productid||seq from CARTON_SEQ where cartoncode in (" +
				"select CARTON_CODE from CARTON_CODE where PRINT_JOB_ID = " +printJobId+
				")) and pc.is_used = "+YesOrNo.NO.getValue();
		EntityManager.executeUpdate(sql);
	}

	public int executeUpdateWithResult(String sql) throws Exception {
		return EntityManager.executeUpdate(sql);
	}

	public void releaseReplacedCodeByPlanId(long planId) throws Exception {
		String sql = "delete from CODE_REPLACE where CARTONCODE in (select code from prepare_code pc where pc.productplan_id="+planId+" and pc.isrelease=1)";
		EntityManager.executeUpdate(sql);
	}

	public void updReplaceCode(long planId, Integer printJobId) throws Exception {
		String sql = "update PRIMARY_CODE pc set pc.is_used = "+YesOrNo.YES.getValue()+", " +
			"PRINT_JOB_ID = "+printJobId+"," +
			"CARTON_CODE = (select cartoncode from CODE_REPLACE where PRIMARYCODE = PC.PRIMARY_CODE) " +
			"where pc.PRIMARY_CODE IN (" +
			"select PRIMARYCODE from CODE_REPLACE where PRODUCTPLANID = " +planId+
			") and pc.is_used = "+YesOrNo.NO.getValue();
		EntityManager.executeUpdate(sql);
		
	}

	public void delReplaceCode(long planId) throws Exception {
		String sql = "delete from CODE_REPLACE where PRODUCTPLANID ="+planId;
		EntityManager.executeUpdate(sql);
	}

	public CartonSeqLog getCartonSeqLogById(String uploadPrId) {
		String hql = "from CartonSeqLog where id = "+uploadPrId; 
		return (CartonSeqLog)EntityManager.find(hql);
	}

	public List<Map<String, String>> getNotFullyActivatedCartSeq(
			String productId) throws Exception {
		String sql = "select to_number(cs.seq) cartonSeq,to_number(min(substr(PRIMARY_CODE, 22, 3))) startPSeq,to_number(max(substr(PRIMARY_CODE, 22, 3))) endPSeq from CARTON_SEQ CS " +
				"join PRIMARY_CODE pc on cs.productid||cs.seq=PC.CARTON_CODE and pc.is_used =" +YesOrNo.NO.getValue() +
				" where status = "+CartonSeqStatus.ACTIVATING.getValue()+" and productid = '"+productId+"' GROUP BY cs.seq";
		return EntityManager.jdbcquery(sql);
	}
}

