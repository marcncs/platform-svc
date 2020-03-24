package com.winsafe.erp.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import com.winsafe.drp.entity.EntityManager;
import com.winsafe.erp.pojo.PrepareCode;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;
public class AppPrepareCode {
	
	public void addPrepareCode(PrepareCode d) throws Exception {		
		EntityManager.save(d);		
	}
	
	public int countNoUsedByOid(String oid) throws Exception {
		int count = 0;
		String sql = "select count(*) from PrepareCode pc  where pc.isuse=0  and organid = "+oid+" ";
		count = EntityManager.getRecordCount(sql);
		return count;
	}
	
	public int countIsRelease(String whereSql) throws Exception {
		int count = 0;
		String sql = "select count(*) from PrepareCode   "+whereSql +" and tcode !='t' and (isRelease is null or isRelease =0)";
		count = EntityManager.getRecordCount(sql);
		return count;
	}
	
	public int countReleaseCode(String whereSql) throws Exception {
		int count = 0;
		String sql = "select count(*) from PrepareCode   "+whereSql +" and tcode !='t' and (isRelease =1)";
		count = EntityManager.getRecordCount(sql);
		return count;
	}
	
	public void addPrepareCodes(HashMap<PrepareCode, PrepareCode> prepareCode) throws Exception {		
		EntityManager.batchSave(new ArrayList<PrepareCode>(prepareCode.values()));	
	}
	
	public List getPrepareCode(HttpServletRequest request, int pageSize, String whereSql) throws Exception{
		String hql="select p from PrepareCode as p "+whereSql +" order by code";
		return PageQuery.hbmQuery(request, hql, pageSize);
	}
	
	public PrepareCode getPrepareCodeByID(Integer id)throws Exception{
		return (PrepareCode)EntityManager.find("from PrepareCode where printJobId="+id);
	}
	
	public PrepareCode getPrepareCodeByBatAMc(String mcode,String batch)throws Exception{
		return (PrepareCode)EntityManager.find("from PrepareCode where materialCode = '"+mcode+"' and batchNumber = '"+batch+"' ");
	}

	public PrepareCode getPrepareCodeByBatAPd(String productid,String batch)throws Exception{
		return (PrepareCode)EntityManager.find("from PrepareCode where productId = '"+productid+"' and batchNumber = '"+batch+"' ");
	}
	
	public void updPrepareCode(PrepareCode printJob) throws Exception {
		EntityManager.update(printJob);		
	}


	public void delPrepareCode(Integer printJobId) throws Exception {
//		String sql = "delete print_job where print_job_id ="+printJobId;
		String sql = "update print_job set isdelete = 1 where print_job_id ="+printJobId;
		EntityManager.updateOrdelete(sql);		
		
	}
	
	public void releasePrepareCode(String code) throws Exception {
		String sql = "update prepare_code set isrelease = 1 where code ='"+code+"'";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void releasePrepareCodeTcode(String code) throws Exception {
		String sql = "update prepare_code set isrelease = 1 where code =(select tcode from prepare_code where code ='"+code+"') and 0 = " +
				"( select count(*) from prepare_code where tcode= (select tcode from prepare_code where code ='"+code+"') and (isrelease is " +
						"null or  isrelease=0))";
		EntityManager.updateOrdelete(sql);		
	}
	
	public void cancelReleasePrepareCode(String code) throws Exception {
		String sql = "update prepare_code set isrelease = NULL where code ='"+code+"'";
		EntityManager.updateOrdelete(sql);		
		
	}
	
	public void updateReleasePrepareCodeTcode(String code,Integer status) throws Exception {
		String sql = "";
		if(status==1){
			 sql = "update prepare_code set isrelease = "+status+" where code = (select tcode from prepare_code where code ='"+code+"')" ;
		}else{
			 sql = "update prepare_code set isrelease = NULL where code = (select tcode from prepare_code where code ='"+code+"')" ;

		}
		EntityManager.updateOrdelete(sql);		
		
	}
	
	public PrepareCode getPrepareCodeByID(int id)throws Exception{
		return (PrepareCode)EntityManager.find("from PrepareCode where printJobId='"+id+"'");
	}

	public List getPrepareCodeByWhereOrder(String whereSql,int num) throws Exception {
		String sql = "select code  from( select code from Prepare_Code " + whereSql + ") where rownum <="+num;
//		return EntityManager.getAllByHql(hql);
		return EntityManager.jdbcquery(sql);
	}
	
	public List<PrepareCode> getPrepareCodeByWhere(String whereSql) {
		String hql = "from PrepareCode " + whereSql;
		return EntityManager.getAllByHql(hql);
	}
	
	public List<PrepareCode> getBoxResultByCode(String code) {
		String hql = "from PrepareCode where  (tcode!='t' and code = '"+code+"') or tcode = '"+code+"'";
		return EntityManager.getAllByHql(hql);
	}
	
	public int updPrepareCodeBySql(String sql) throws Exception {
		return EntityManager.executeUpdate(sql);
	}
	
	public Integer getPrepareCodeIdForInventoryUpload() throws Exception {
		String sql = "select PRINT_JOB_ID_OLD_SEQ.nextval from dual";
		return EntityManager.getRecordCountBySql(sql);
	}

	public PrepareCode getByPidAndBatch(String pid, String batch) {
		return (PrepareCode)EntityManager.find("from PrepareCode where productId='"+pid+"' and batchNumber='"+batch+"'");
	}
	
	
	public void addPrepareCode(String mcode,String batch,String productDate,String packagingDate,String expiryDate) throws Exception {
		String sql = "insert into PRINT_JOB (PRINT_JOB_ID,TRANS_ORDER,MATERIAL_CODE,BATCH_NUMBER,PRODUCTION_DATE,PACKAGING_DATE,EXPIRY_DATE,NUMBER_OF_CASES,TOTAL_NUMBER,MATERIAL_NAME,PRODUCT_ID,PACK_SIZE,UPLOAD_ID,PRINTING_STATUS,PRIMARY_CODE_STATUS,CREATE_DATE,ISDELETE,CONFIRM_FLAG) "
				+ " select print_job_id_OLD_SEQ.nextval,-1,'"+mcode+"','"+batch+"','"+productDate+"','"+packagingDate+"','"+expiryDate+"',1,1*4,pr.PRODUCTNAME,pr.ID,pr.specmode,-1,2,1,sysdate,0,1 from "
				+ " product pr where pr.mcode='"+mcode+"' ";
		EntityManager.updateOrdelete(sql);

	}
	public List<Map> getPrepareCodes(HttpServletRequest request, int pagesize,
			Map paraMap, String condition) throws Exception {
		//对于结束日期增加一天
		if(!StringUtil.isEmpty((String)paraMap.get("EndDate"))){
			String endDateStr = (String)paraMap.get("EndDate");
			endDateStr = endDateStr.replaceAll("-", "") + "235959";
			paraMap.put("EndDate", endDateStr);
		}
		//对于结束日期增加一天
		if(!StringUtil.isEmpty((String)paraMap.get("BeginDate"))){
			String beginDateStr = (String)paraMap.get("BeginDate");
			beginDateStr = beginDateStr.replaceAll("-", "") + "000000";
			paraMap.put("BeginDate", beginDateStr);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("\r\n select aaa.code code");
		sql.append("\r\n ,aaa.sap_log_id sapLogID");
		sql.append("\r\n ,aaa.isuse isuse");
		sql.append("\r\n ,aaa.organid organid");
		sql.append("\r\n ,to_char(aaa.modified_Date,'YYYY-MM-DD HH24:MI:SS') modifiedDate");
//		sql.append("\r\n ,to_char(modified_Date,'YYYY-MM-DD') modifiedDate");
		sql.append("\r\n ,aaa.modified_user modifiedUser");
		sql.append("\r\n ,aaa.productplan_id productPlanId");
		sql.append("\r\n ,aaa.isRelease isRelease");
		sql.append("\r\n ,uuu.realname username");
		sql.append("\r\n from Prepare_Code aaa join users uuu on aaa.modified_user=uuu.userid where 1=1 ");
		if(!StringUtil.isEmpty((String)paraMap.get("code"))) {
			sql.append(" and aaa.code= '"+paraMap.get("code")+"'");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("organId"))) {
			sql.append(" and aaa.organid = '"+paraMap.get("organId")+"'");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("productPlanId"))) {
			sql.append(" and aaa.productplan_id = '"+paraMap.get("productPlanId")+"'");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("isuse"))) {
			sql.append(" and aaa.isuse = '"+paraMap.get("isuse")+"'");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("BeginDate"))) {
			sql.append(" and aaa.modified_Date >= to_date('"+paraMap.get("BeginDate").toString().substring(0,8)+"','yyyyMMdd')");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("EndDate"))) {
			sql.append(" and aaa.modified_Date <= to_date('"+paraMap.get("EndDate").toString().substring(0,8)+" 23:59:59','yyyyMMdd hh24:mi:ss')");
		}
		//是否为托
		if(!StringUtil.isEmpty((String)paraMap.get("istcode"))) {
			if(paraMap.get("istcode").equals("1")){
				sql.append(" and aaa.tcode = '"+"t"+"'");
			}else{
				sql.append(" and aaa.tcode != '"+"t"+"' and tcode is not null ");
			}
			
		}
		//是否释放
		if(!StringUtil.isEmpty((String)paraMap.get("isRelease"))) {
			if(paraMap.get("isRelease").equals("1")){
				sql.append(" and aaa.isRelease = 1 ");
			}else{
			}
		}
		sql.append(condition);
		return PageQuery.jdbcSqlserverQuery(request, "code", sql.toString(), pagesize);
	}
	
	
	public void addCartonCodes(List<PrepareCode> prepareCodes,int useflag,long planid) throws Exception {
		List<String> batchSqls = new ArrayList<String>();
		for(PrepareCode cc :prepareCodes) {
			StringBuffer sb = new StringBuffer();
			sb.append("update Prepare_Code set isuse ="+useflag+",productplan_id = "+planid +" where code ='"+cc.getCode()+"'");
			batchSqls.add(sb.toString());
		}
		EntityManager.executeBatch(batchSqls);
	}
	//生成打印任务后更新
	public void updatePrepareCode(Map<String, StringBuffer> tbmap,int useflag,long planid) throws Exception {
		List<String> batchSqls = new ArrayList<String>();
		for(Map.Entry<String, StringBuffer> entry : tbmap.entrySet()){
			String tcode = entry.getKey();
			String tsql = "update Prepare_Code set isuse ="+useflag+",productplan_id = "+planid +",tcode ='t' where code ='"+tcode+"'";
			batchSqls.add(tsql);
			
			StringBuffer codesbf = entry.getValue();
			String codesql = "update Prepare_Code set isuse ="+useflag+",productplan_id = "+planid +",tcode ='"+tcode+"' " +
					"where code in ("+codesbf.substring(1)+")";
			batchSqls.add(codesql);
		}
		if(batchSqls.size()>0){
			EntityManager.executeBatch(batchSqls);
		}
	}
	public Integer getPrepareCodeUsedCount(Map paraMap, String condition) throws Exception {
		//对于结束日期增加一天
		if(!StringUtil.isEmpty((String)paraMap.get("EndDate"))){
			String endDateStr = (String)paraMap.get("EndDate");
			endDateStr = endDateStr.replaceAll("-", "") + "235959";
			paraMap.put("EndDate", endDateStr);
		}
		//对于结束日期增加一天
		if(!StringUtil.isEmpty((String)paraMap.get("BeginDate"))){
			String beginDateStr = (String)paraMap.get("BeginDate");
			beginDateStr = beginDateStr.replaceAll("-", "") + "000000";
			paraMap.put("BeginDate", beginDateStr);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("\r\n select count(*)");
		sql.append("\r\n from Prepare_Code aaa join users uuu on aaa.modified_user=uuu.userid where 1=1 and isuse=1 ");
		if(!StringUtil.isEmpty((String)paraMap.get("code"))) {
			sql.append(" and aaa.code= '"+paraMap.get("code")+"'");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("organId"))) {
			sql.append(" and aaa.organid = '"+paraMap.get("organId")+"'");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("productPlanId"))) {
			sql.append(" and aaa.productplan_id = '"+paraMap.get("productPlanId")+"'");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("isuse"))) {
			sql.append(" and aaa.isuse = '"+paraMap.get("isuse")+"'");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("BeginDate"))) {
			sql.append(" and aaa.modified_Date >= to_date('"+paraMap.get("BeginDate").toString().substring(0,8)+"','yyyyMMdd')");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("EndDate"))) {
			sql.append(" and aaa.modified_Date <= to_date('"+paraMap.get("EndDate").toString().substring(0,8)+" 23:59:59','yyyyMMdd hh24:mi:ss')");
		}
		//是否为托
		if(!StringUtil.isEmpty((String)paraMap.get("istcode"))) {
			if(paraMap.get("istcode").equals("1")){
				sql.append(" and aaa.tcode = '"+"t"+"'");
			}else{
				sql.append(" and aaa.tcode != '"+"t"+"' and tcode is not null ");
			}
			
		}
		//是否释放
		if(!StringUtil.isEmpty((String)paraMap.get("isRelease"))) {
			if(paraMap.get("isRelease").equals("1")){
				sql.append(" and aaa.isRelease = 1 ");
			}else{
			}
		}
		sql.append(condition);
		return EntityManager.getRecordCountBySql(sql.toString());
	}
	
	public Integer getPrepareCodeRelease(Map paraMap, String condition) throws Exception {
		//对于结束日期增加一天
		if(!StringUtil.isEmpty((String)paraMap.get("EndDate"))){
			String endDateStr = (String)paraMap.get("EndDate");
			endDateStr = endDateStr.replaceAll("-", "") + "235959";
			paraMap.put("EndDate", endDateStr);
		}
		//对于结束日期增加一天
		if(!StringUtil.isEmpty((String)paraMap.get("BeginDate"))){
			String beginDateStr = (String)paraMap.get("BeginDate");
			beginDateStr = beginDateStr.replaceAll("-", "") + "000000";
			paraMap.put("BeginDate", beginDateStr);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("\r\n select count(*)");
		sql.append("\r\n from Prepare_Code aaa join users uuu on aaa.modified_user=uuu.userid where 1=1 and isuse=1 ");
		if(!StringUtil.isEmpty((String)paraMap.get("code"))) {
			sql.append(" and aaa.code= '"+paraMap.get("code")+"'");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("organId"))) {
			sql.append(" and aaa.organid = '"+paraMap.get("organId")+"'");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("productPlanId"))) {
			sql.append(" and aaa.productplan_id = '"+paraMap.get("productPlanId")+"'");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("isuse"))) {
			sql.append(" and aaa.isuse = '"+paraMap.get("isuse")+"'");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("BeginDate"))) {
			sql.append(" and aaa.modified_Date >= to_date('"+paraMap.get("BeginDate").toString().substring(0,8)+"','yyyyMMdd')");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("EndDate"))) {
			sql.append(" and aaa.modified_Date <= to_date('"+paraMap.get("EndDate").toString().substring(0,8)+" 23:59:59','yyyyMMdd hh24:mi:ss')");
		}
		//是否为托
		if(!StringUtil.isEmpty((String)paraMap.get("istcode"))) {
			if(paraMap.get("istcode").equals("1")){
				sql.append(" and aaa.tcode = '"+"t"+"'");
			}else{
				sql.append(" and aaa.tcode != '"+"t"+"' and tcode is not null ");
			}
			
		}
		//是否释放
		if(!StringUtil.isEmpty((String)paraMap.get("isRelease"))) {
			if(paraMap.get("isRelease").equals("1")){
				sql.append(" and aaa.isRelease = 1 ");
			}else{
			}
		}
		//
		sql.append(" and aaa.isrelease = 1 ");
		sql.append(condition);
		return EntityManager.getRecordCountBySql(sql.toString());
	}
	
	public Integer getPrepareCodeUsedAll(Map paraMap, String condition) throws Exception {
		//对于结束日期增加一天
		if(!StringUtil.isEmpty((String)paraMap.get("EndDate"))){
			String endDateStr = (String)paraMap.get("EndDate");
			endDateStr = endDateStr.replaceAll("-", "") + "235959";
			paraMap.put("EndDate", endDateStr);
		}
		//对于结束日期增加一天
		if(!StringUtil.isEmpty((String)paraMap.get("BeginDate"))){
			String beginDateStr = (String)paraMap.get("BeginDate");
			beginDateStr = beginDateStr.replaceAll("-", "") + "000000";
			paraMap.put("BeginDate", beginDateStr);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("\r\n select count(*)");
		sql.append("\r\n from Prepare_Code aaa join users uuu on aaa.modified_user=uuu.userid where 1=1  ");
		if(!StringUtil.isEmpty((String)paraMap.get("code"))) {
			sql.append(" and aaa.code= '"+paraMap.get("code")+"'");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("organId"))) {
			sql.append(" and aaa.organid = '"+paraMap.get("organId")+"'");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("productPlanId"))) {
			sql.append(" and aaa.productplan_id = '"+paraMap.get("productPlanId")+"'");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("isuse"))) {
			sql.append(" and aaa.isuse = '"+paraMap.get("isuse")+"'");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("BeginDate"))) {
			sql.append(" and aaa.modified_Date >= to_date('"+paraMap.get("BeginDate").toString().substring(0,8)+"','yyyyMMdd')");
		}
		if(!StringUtil.isEmpty((String)paraMap.get("EndDate"))) {
			sql.append(" and aaa.modified_Date <= to_date('"+paraMap.get("EndDate").toString().substring(0,8)+" 23:59:59','yyyyMMdd hh24:mi:ss')");
		}
		//是否为托
		if(!StringUtil.isEmpty((String)paraMap.get("istcode"))) {
			if(paraMap.get("istcode").equals("1")){
				sql.append(" and aaa.tcode = '"+"t"+"'");
			}else{
				sql.append(" and aaa.tcode != '"+"t"+"' and tcode is not null ");
			}
			
		}
		//是否释放
		if(!StringUtil.isEmpty((String)paraMap.get("isRelease"))) {
			if(paraMap.get("isRelease").equals("1")){
				sql.append(" and aaa.isRelease = 1 ");
			}else{
			}
		}
		
		sql.append(condition);
		return EntityManager.getRecordCountBySql(sql.toString());
	}
	
	/**
	 * 生产计划下载码查询 ,暂时不使用
	 * @param whereSql
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getPrepareCodeByProductPlanId(String id) throws Exception{
		 
		StringBuffer bf = new StringBuffer();
		bf.append(" select pp.printJobId,pp.PONO,pp.organid||'_'||pp.productid oidPid,pp.mbatch,pp.id,pp.packDate,pp.pono,pc.code,pp.pbatch,pt.mcode ");
		bf.append(" from ProductPlan pp,Prepare_Code pc,Product pt");
		bf.append(" where pp.productid = pt.id and pp.id = pc.productplan_id");
		bf.append(" and (pc.isrelease is null or pc.isrelease = 0) and pc.tcode !='t'");
		bf.append(" and pp.id in(");
		bf.append(id);
		bf.append(") order by pt.mcode,pc.code ");
		return EntityManager.jdbcquery(bf.toString().trim());
		
	}

	public List<Map<String, String>> getCodeListForUpdCovertCode(
			HttpServletRequest request, int pageSize) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select PC.code,PC.tcode,PC.ISRELEASE,pcode.covert_Code from Prepare_Code pc");
		sql.append(" join productplan pp on PC.PRODUCTPLAN_ID = pp.ID and PP.id ="+request.getParameter("ID")+" and pc.isuse=1 and pc.tcode != 't' ");
		if(!StringUtil.isEmpty(request.getParameter("isRelease"))){
    		if(request.getParameter("isRelease").equals("0")){
    			sql.append(" and (isRelease is null or isRelease=0) ");
    		}else{ 
    			sql.append(" and isRelease = 1 ");
    		}
    	}
		sql.append(" LEFT JOIN PRIMARY_CODE pcode on pcode.print_job_id = pp.printJobId  and PC.code = pcode.primary_code");
		if(!StringUtil.isEmpty(request.getParameter("KeyWord"))){
			sql.append(" where pc.code like '%"+request.getParameter("KeyWord")+"%' or pc.tcode like '%"+request.getParameter("KeyWord")+"%' or pcode.covert_Code like '%"+request.getParameter("KeyWord")+"%' ");
    	}
		if(pageSize == 0) {
			sql.append(" order by PC.code ");
			return EntityManager.jdbcquery(sql.toString());
		}
		return PageQuery.jdbcSqlserverQuery(request, "code", sql.toString(), pageSize);
	}

	public List<String> getCodeListByPlanId(long id) {
		String hql = "select code from PrepareCode where productPlanId=" + id;
		return EntityManager.getAllByHql(hql);
	}

	public Set<String> getCodeSetBySql(String sql) throws Exception {
		List<Map<String,String>> list = EntityManager.jdbcquery(sql);
		Set<String> codeSet = new HashSet<String>();
		for(Map<String,String> map : list) {
			codeSet.addAll(map.values());
		}
		return codeSet;
	} 
	
	public List<String> getCodeListByPlanIdAndCCode(long id, String cartonCode) {
		String hql = "select code from PrepareCode where productPlanId=" + id + " and code='"+cartonCode+"'";
		return EntityManager.getAllByHql(hql);
	}
	
	public List<Map<String,String>> getDuplicatedPrepareCode() throws Exception {
		String sql = "select ttpc.code from transaction_temp_preapare_code ttpc join PREPARE_CODE pc on ttpc.code=pc.code ";
		return EntityManager.jdbcquery(sql);
	}

	public void addPrepareCode(Integer sapLogID, Integer isuse, String organId, Date modifiedDate, Integer modifiedUser) throws Exception {
		String sql = "INSERT INTO PREPARE_CODE(CODE,SAP_LOG_ID,ISUSE,ORGANID,MODIFIED_DATE,MODIFIED_USER) "
				+ "select CODE,"+sapLogID+","+isuse+",'"+organId+"',SYSDATE,"+modifiedUser+" FROM transaction_temp_preapare_code ttpc "
				+ "WHERE NOT EXISTS (select code from PREPARE_CODE where code = ttpc.code) ";
		EntityManager.executeUpdate(sql);
	}
}

