package com.winsafe.sap.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CovertUploadReportBean {
	
	private CovertUploadReport covertUploadReport;
	
	// 错误记录数
	private Integer errorCount = 0;
	// 总记录数
	private Integer totalCount = 0;
	
	private Integer duplicateSqlInClauseCount = 0;
	
	private Integer duplicateInLogSqlInClauseCount = 0;
	
	private Integer alreadyExistsSqlInClauseCount = 0;
	//查看系统中重复暗码语句
	private StringBuffer checkDuplicateSql = new StringBuffer("select COVERT_CODE,PRIMARY_CODE FROM PRIMARY_CODE where COVERT_CODE in (");
	
	private List<String> checkDuplicateSqls = new ArrayList<String>();
	//查看日志中重复暗码语句
	private StringBuffer checkDuplicateInLogSql = new StringBuffer("select DISTINCT COVERT_CODE from COVERT_ERROR_LOG where COVERT_CODE IN (");
	
	private List<String> checkDuplicateInLogSqls = new ArrayList<String>();
	//查看系统中小包装码是否已存在暗码语句
	private StringBuffer checkAlreadyExistsSql = new StringBuffer("select COVERT_CODE,PRIMARY_CODE FROM PRIMARY_CODE where (COVERT_CODE is not null or UPLOAD_PR_ID = -1) and PRIMARY_CODE in (");
	
	private List<String> checkAlreadyExistsSqls = new ArrayList<String>();
	//添加错误日志语句
	private List<String> errorLogSqls = new ArrayList<String>();
	//更新暗码到小包装码表语句
	private List<String> updateCovertSqls = new ArrayList<String>();
	
	private List<String> covertCodeList = new ArrayList<String>();
	
	private Map<String, String[]> covertCodeMap = new HashMap<String, String[]>();
	
	private Map<String, String> primaryCodeMap = new HashMap<String, String>();
	//更新暗码为空的语句
	private List<String> updateCovertToEmptySqls = new ArrayList<String>();
	//更新暗码上传错误日志错误状态语句
	private List<String> updateErrorLogSqls = new ArrayList<String>();
	
	public CovertUploadReport getCovertUploadReport() {
		return covertUploadReport;
	}
	public void setCovertUploadReport(CovertUploadReport covertUploadReport) {
		this.covertUploadReport = covertUploadReport;
	}
	public List<String> getCheckDuplicateSqls() {
		return checkDuplicateSqls;
	}
	public void setCheckDuplicateSqls(List<String> checkDuplicateSqls) {
		this.checkDuplicateSqls = checkDuplicateSqls;
	}
	public List<String> getErrorLogSqls() {
		return errorLogSqls;
	}
	public void setErrorLogSqls(List<String> errorLogSqls) {
		this.errorLogSqls = errorLogSqls;
	}
	public List<String> getUpdateCovertSqls() {
		return updateCovertSqls;
	}
	public void setUpdateCovertSqls(List<String> updateCovertSqls) {
		this.updateCovertSqls = updateCovertSqls;
	}
	public Integer getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public StringBuffer getCheckDuplicateSql() {
		return checkDuplicateSql;
	}
	public void setCheckDuplicateSql(StringBuffer checkDuplicateSql) {
		this.checkDuplicateSql = checkDuplicateSql;
	}
	public List<String> getCovertCodeList() {
		return covertCodeList;
	}
	public void setCovertCodeList(List<String> covertCodeList) {
		this.covertCodeList = covertCodeList;
	}
	public Map<String, String[]> getCovertCodeMap() {
		return covertCodeMap;
	}
	public void setCovertCodeMap(Map<String, String[]> covertCodeMap) {
		this.covertCodeMap = covertCodeMap;
	}
	public StringBuffer getCheckAlreadyExistsSql() {
		return checkAlreadyExistsSql;
	}
	public void setCheckAlreadyExistsSql(StringBuffer checkAlreadyExistsSql) {
		this.checkAlreadyExistsSql = checkAlreadyExistsSql;
	}
	public List<String> getCheckAlreadyExistsSqls() {
		return checkAlreadyExistsSqls;
	}
	public void setCheckAlreadyExistsSqls(List<String> checkAlreadyExistsSqls) {
		this.checkAlreadyExistsSqls = checkAlreadyExistsSqls;
	}
	public Integer getDuplicateSqlInClauseCount() {
		return duplicateSqlInClauseCount;
	}
	public void setDuplicateSqlInClauseCount(Integer duplicateSqlInClauseCount) {
		this.duplicateSqlInClauseCount = duplicateSqlInClauseCount;
	}
	public Integer getAlreadyExistsSqlInClauseCount() {
		return alreadyExistsSqlInClauseCount;
	}
	public void setAlreadyExistsSqlInClauseCount(
			Integer alreadyExistsSqlInClauseCount) {
		this.alreadyExistsSqlInClauseCount = alreadyExistsSqlInClauseCount;
	}
	public Map<String, String> getPrimaryCodeMap() {
		return primaryCodeMap;
	}
	public void setPrimaryCodeMap(Map<String, String> primaryCodeMap) {
		this.primaryCodeMap = primaryCodeMap;
	}
	public List<String> getUpdateCovertToEmptySqls() {
		return updateCovertToEmptySqls;
	}
	public void setUpdateCovertToEmptySqls(List<String> updateCovertToEmptySqls) {
		this.updateCovertToEmptySqls = updateCovertToEmptySqls;
	}
	public List<String> getUpdateErrorLogSqls() {
		return updateErrorLogSqls;
	}
	public void setUpdateErrorLogSqls(List<String> updateErrorLogSqls) {
		this.updateErrorLogSqls = updateErrorLogSqls;
	}
	public StringBuffer getCheckDuplicateInLogSql() {
		return checkDuplicateInLogSql;
	}
	public void setCheckDuplicateInLogSql(StringBuffer checkDuplicateInLogSql) {
		this.checkDuplicateInLogSql = checkDuplicateInLogSql;
	}
	public List<String> getCheckDuplicateInLogSqls() {
		return checkDuplicateInLogSqls;
	}
	public void setCheckDuplicateInLogSqls(List<String> checkDuplicateInLogSqls) {
		this.checkDuplicateInLogSqls = checkDuplicateInLogSqls;
	}
	public Integer getDuplicateInLogSqlInClauseCount() {
		return duplicateInLogSqlInClauseCount;
	}
	public void setDuplicateInLogSqlInClauseCount(
			Integer duplicateInLogSqlInClauseCount) {
		this.duplicateInLogSqlInClauseCount = duplicateInLogSqlInClauseCount;
	}
	
}
