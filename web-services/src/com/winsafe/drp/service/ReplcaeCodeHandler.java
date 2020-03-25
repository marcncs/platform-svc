package com.winsafe.drp.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap; 
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import com.winsafe.erp.dao.AppPrepareCode;
import com.winsafe.erp.dao.AppProductPlan;
import com.winsafe.erp.metadata.CartonSeqStatus;
import com.winsafe.erp.pojo.ProductPlan;
import com.winsafe.erp.services.CartonSeqServices;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppPrimaryCode;
import com.winsafe.sap.pojo.UploadProduceLog;
/*******************************************************************************************
 * 类描述：  
 * 分装厂码替换处理类
 * @author: ryan.xi	  
 * @date：2017-12-01  
 * @version 1.0  
 *  
 * Version    Date       ModifiedBy                 Content  
 * -------- ---------    ----------         ------------------------  
 *                             
 *******************************************************************************************  
 */
public class ReplcaeCodeHandler extends ProduceFileHandler{
	
	private AppProductPlan appPlan = new AppProductPlan();
	private Map<String,ProductPlan> planMap = new HashMap<String, ProductPlan>();
	private Set<String> notExistsPlanId = new HashSet<String>();
	private int inClauseSize = 1000;
	
	public void execute(File file, UploadProduceLog uploadProduceLog, StringBuffer resultMsg) throws Exception {
		int totalCount= 0;
		int successCount = 0;
		//有问题的箱码
		Set<String> errCartonCodes = new HashSet<String>();
		//有问题的小码
		Set<String> errPrimaryCodes = new HashSet<String>();
		Set<String> cartonSeqId = new HashSet<String>();
		List<ReplaceCodes> codeList = new ArrayList<ReplaceCodes>();
		//获取码数据
		totalCount = getCodeFromFile(file, codeList, resultMsg);
		
		if(codeList.size() > 0) {
			doValidate(codeList, resultMsg, errCartonCodes, errPrimaryCodes, cartonSeqId);
			if(cartonSeqId.size() > 0) {
				successCount = doReplace(codeList, resultMsg, errCartonCodes, errPrimaryCodes, cartonSeqId);
			}
		}
		//设置数量
		uploadProduceLog.setTotalCount(totalCount);
		uploadProduceLog.setErrorCount(totalCount-successCount);
	}


	private int doReplace(List<ReplaceCodes> codeList, StringBuffer resultMsg,
			Set<String> errCartonCodes, Set<String> errPrimaryCodes, Set<String> cartonSeqId) throws Exception {
		CartonSeqServices css = new CartonSeqServices();
		//添加替换码到临时表的语句
		List<String> addReplaceSqlList = new ArrayList<String>();
		
		for(ReplaceCodes rc : codeList) {
			//去掉问题的码
			if(errCartonCodes.contains(rc.getCartonCode())
					|| errPrimaryCodes.contains(rc.getPrimaryCode())) {
				continue;
			}
			addReplaceSqlList.add(getAddReplaceSql(rc));
		}
		
		StringBuffer ids = new StringBuffer();
		for(String seqId : cartonSeqId) {
			ids.append(","+seqId);
		}
		
		//更新替换码的箱序号拆包状态的语句
		String updCartonSeqStatusSql  = getUpdCartonSeqStatusSql(ids.substring(1));
		
		int successCount = css.addReplaceCodes(addReplaceSqlList);
		
		int updatedSeqCount = css.updCartonSeq(updCartonSeqStatusSql);
		
		if(updatedSeqCount != cartonSeqId.size()) {
			throw new Exception("替换失败,小码对应的箱码序号状态发生了变化,请重试");
		}
		
		return successCount;
	}

	private String getUpdCartonSeqStatusSql(String ids) {
		return "UPDATE CARTON_SEQ SET STATUS = "+CartonSeqStatus.UNPACKED.getValue()+" where id in ("+ids+") and STATUS in ("+CartonSeqStatus.UNPACKED.getValue()+","+CartonSeqStatus.NOT_USED.getValue()+")";
	}

	private String getAddReplaceSql(ReplaceCodes rc) {
		return "INSERT INTO CODE_REPLACE(PRIMARYCODE, CARTONCODE, PRODUCTPLANID) VALUES('"+rc.getPrimaryCode()+"','"+rc.getCartonCode()+"',"+rc.getPlanId()+")";
	}

	private void doValidate(List<ReplaceCodes> codeList, StringBuffer resultMsg, Set<String> errCartonCodes, Set<String> errPrimaryCodes, Set<String> cartonSeqId) throws Exception {
		Map<String, Set<String>> cartonCodeMap = new HashMap<String, Set<String>>();
		for(ReplaceCodes rcode : codeList) {
			if(cartonCodeMap.containsKey(rcode.getPlanId())) {
				cartonCodeMap.get(rcode.getPlanId()).add(rcode.getCartonCode());
			} else {
				Set<String> ccSet = new HashSet<String>();
				ccSet.add(rcode.getCartonCode());
				cartonCodeMap.put(rcode.getPlanId(), ccSet);
			}
		}
		//检查箱码是否在生产计划中
		checkCartonCode(cartonCodeMap, resultMsg, errCartonCodes);
		
		//检查小码是否可用
		checkPrimaryCode(codeList, resultMsg, errPrimaryCodes, errCartonCodes, cartonSeqId);
		
	}

	private void checkPrimaryCode(List<ReplaceCodes> codeList,
			StringBuffer resultMsg, Set<String> errPrimaryCodes, Set<String> errCartonCodes, Set<String> cartonSeqId) throws Exception {
		//<小包装码,生产计划编号>
		Map<String,String> primaryCodeAndPlanId = new HashMap<String, String>();
		AppPrimaryCode appPrimaryCode = new AppPrimaryCode();
		StringBuffer sql = new StringBuffer();
		int count = 0;
		StringBuffer codes = new StringBuffer();
		for(ReplaceCodes rc : codeList) {
			//去掉箱码有问题的小码
			if(errCartonCodes.contains(rc.getCartonCode())) {
				continue;
			}
			primaryCodeAndPlanId.put(rc.getPrimaryCode(), rc.getPlanId());
			codes.append(",'"+rc.getPrimaryCode()+"'");
			count++;
			if(count >= inClauseSize) {
				createCheckPrimaryCodeSql(sql, codes);
				count = 0;
				codes = new StringBuffer();
			}
		}
		if(count > 0) {
			createCheckPrimaryCodeSql(sql, codes);
			codes = null;
		}
		Set<String> existsPrimaryCode = new HashSet<String>();
		List<Map<String, String>> pcodeList = new ArrayList<Map<String,String>>();
		if(sql.length() > 0) {
			pcodeList = appPrimaryCode.getListBySql(sql.substring(9));
		}
		for(Map<String, String> map : pcodeList) {
			String primaryCode = map.get("primary_code");
			existsPrimaryCode.add(primaryCode);
			//检查小码是否已使用
			if("1".equals(map.get("is_used"))) {
				resultMsg.append("小包装码"+primaryCode+"已使用\r\n");
				errPrimaryCodes.add(primaryCode);
				continue;
			}
			//检查是否已分配给生产计划
			if(StringUtil.isEmpty(map.get("status"))) {
				resultMsg.append("小包装码"+primaryCode+"未关联到任何箱序号\r\n");
				errPrimaryCodes.add(primaryCode);
				continue;
			}
			//检查是否已分配给生产计划
			if(CartonSeqStatus.LOCKED.getValue().toString().equals(map.get("status"))) {
				resultMsg.append("小包装码"+primaryCode+"已分配给其他生产计划\r\n");
				errPrimaryCodes.add(primaryCode);
				continue;
			}
			//检查是否已替换给其他生产计划的箱码 
			if(!StringUtil.isEmpty(map.get("cartoncode"))) {
				resultMsg.append("小包装码"+primaryCode+"已替换给其他箱码"+map.get("cartoncode")+"\r\n");
				errPrimaryCodes.add(primaryCode);
				continue;
			}
			//检查小包装对应的产品是否与箱码一致
			if(!planMap.get(primaryCodeAndPlanId.get(primaryCode)).getProductId().equals(map.get("productid"))) {
				resultMsg.append("小包装码"+primaryCode+"对应的产品与箱码不一致\r\n");
				errPrimaryCodes.add(primaryCode);
				continue;
			}
			//添加新小包装码关联饿箱序号编号
			cartonSeqId.add(map.get("seqid"));
			
		}
		//判断小包装码是否都存在
		Set<String> allPrimaryCode = new HashSet<String>();
		allPrimaryCode.addAll(primaryCodeAndPlanId.keySet());
		allPrimaryCode.removeAll(existsPrimaryCode);
		for(String primaryCode : allPrimaryCode) {
			resultMsg.append("小包装码"+primaryCode+"不存在\r\n");
			errPrimaryCodes.add(primaryCode);
			continue;
		}
	} 


	private void createCheckPrimaryCodeSql(StringBuffer sql, StringBuffer codes) {
		sql.append("union all");
		sql.append(" select PC.PRIMARY_CODE,PC.IS_USED,cs2.STATUS, cp.PRODUCTPLANID,cp.cartoncode,CS2.PRODUCTID,CS2.ID seqId from PRIMARY_CODE pc ");
		sql.append(" LEFT JOIN CARTON_SEQ cs2 on pc.carton_code = (cs2.productid||cs2.seq) ");
		sql.append(" LEFT JOIN CODE_REPLACE cp on cp.PRIMARYCODE = PC.PRIMARY_CODE ");
		sql.append(" where PC.PRIMARY_CODE in ("+codes.substring(1)+") ");
		
	}

	private boolean checkCartonCode(Map<String, Set<String>> cartonCodeMap, StringBuffer resultMsg, Set<String> errCartonCodes) throws Exception {
		AppPrepareCode appPrepareCode = new AppPrepareCode();
		StringBuffer sql = new StringBuffer();
		for(String planId : cartonCodeMap.keySet()) {
			int count = 0;
			StringBuffer codes = new StringBuffer();
			for(String cartonCode : cartonCodeMap.get(planId)) {
				codes.append(",'"+cartonCode+"'");
				count++;
				if(count >= inClauseSize) {
					createCheckCartonCodeSql(sql, codes, planId);
					count = 0;
					codes = new StringBuffer();
				}
			}
			if(count > 0) {
				createCheckCartonCodeSql(sql, codes, planId);
				codes = null;
			}
		}
		
		Set<String> cartonCodes = appPrepareCode.getCodeSetBySql(sql.substring(9));
		for(String planId : cartonCodeMap.keySet()) {
			Set<String> ccSet = cartonCodeMap.get(planId);
			ccSet.removeAll(cartonCodes);
			if(ccSet.size() > 0) {
				for(String cartonCode : ccSet) {
					errCartonCodes.add(cartonCode);
					resultMsg.append("编号为"+planId+"的生产任务未包含箱码"+cartonCode+"\r\n");
				}
			}
		}
		
		return false;
	}

	private void createCheckCartonCodeSql(StringBuffer sql, StringBuffer codes, String planId) {
		sql.append("union all");
		sql.append(" select code from PREPARE_CODE where PRODUCTPLAN_ID = "+planId);
		sql.append(" and code in ("+codes.substring(1)+") ");
		sql.append(" and (isrelease is null or isrelease = 0) ");
	}

	private int getCodeFromFile(File file, List<ReplaceCodes> codeList,
			StringBuffer resultMsg) throws Exception {
		int lineNo= 0;
		Set<String> primarCodeSet = new HashSet<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			for (String line = br.readLine(); line != null; line = br
					.readLine()) {
				// 判断是否为空行
				if ("".equals(line.trim())) {
					continue;
				}
				lineNo++;
				//小包装二维码信息， 新箱码（SSCC），被替换的包装二维码，产线，托盘计划编号
				String[] data = line.split(",");
				if(data.length != 5) {
					resultMsg.append("第"+lineNo+"行数据格式不正确\r\n");
					continue;
				}
				//检查托盘计划编号是否为空
				if(StringUtil.isEmpty(data[4])) {
					resultMsg.append("第"+lineNo+"行托盘计划编号为空\r\n");
					continue;
				}
				//检查小码是否重复
				if(!primarCodeSet.add(data[0])) {
					resultMsg.append("第"+lineNo+"行文件中存在重复的小包装码"+data[0]+"\r\n");
					continue;
				}
				//检查托盘计划是否存在
				if(!isProductPlanExists(data[4])) {
					resultMsg.append("第"+lineNo+"行编号为"+data[4]+"的生产计划不存在\r\n");
					continue;
				}
				//检查托盘计划是否已关闭
				if(isProductPlanClosed(data[4])) {
					resultMsg.append("第"+lineNo+"行编号为"+data[4]+"的生产计划已结束\r\n");
					continue;
				}
				codeList.add(new ReplaceCodes(lineNo, data[4], data[1], data[0]));
			}
		} finally {
			br.close();
		}
		return lineNo;
	}
	
	private boolean isProductPlanClosed(String planId) {
		ProductPlan plan = planMap.get(planId);
		if(plan.getCloseFlag() != null 
				&& plan.getCloseFlag() == 1) {
			return true;
		}
		return false;
	}

	private boolean isProductPlanExists(String planId) throws Exception {
		if(notExistsPlanId.contains(planId)) {
			return false;
		}
		if(planMap.containsKey(planId)) {
			return true;
		} else {
			ProductPlan plan = appPlan.getProductPlanByIDString(planId);
			if(plan != null) {
				planMap.put(planId, plan);
				return true;
			} else {
				notExistsPlanId.add(planId);
				return false;
			}
		}
	}

	public class ReplaceCodes {
		private int lineNo;
		private String planId;
		private String cartonCode;
		private String primaryCode;
		
		public ReplaceCodes(int lineNo, String planId, String cartonCode,
				String primaryCode) {
			super();
			this.lineNo = lineNo;
			this.planId = planId;
			this.cartonCode = cartonCode;
			this.primaryCode = primaryCode;
		}
		public int getLineNo() {
			return lineNo;
		}
		public void setLineNo(int lineNo) {
			this.lineNo = lineNo;
		}
		public String getPlanId() {
			return planId;
		}
		public void setPlanId(String planId) {
			this.planId = planId;
		}
		public String getCartonCode() {
			return cartonCode;
		}
		public void setCartonCode(String cartonCode) {
			this.cartonCode = cartonCode;
		}
		public String getPrimaryCode() {
			return primaryCode;
		}
		public void setPrimaryCode(String primaryCode) {
			this.primaryCode = primaryCode;
		}
	} 

}
