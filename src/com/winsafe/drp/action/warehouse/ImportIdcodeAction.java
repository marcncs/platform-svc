package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.exception.NotExistException;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.NumberUtil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.metadata.CartonSeqStatus;
import com.winsafe.sap.metadata.PrimaryCodeStatus;
import com.winsafe.sap.metadata.SyncStatus;
import com.winsafe.sap.pojo.CartonCode;
import com.winsafe.sap.pojo.PrintJob;

public class ImportIdcodeAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ImportIdcodeAction.class);
	private AppProduct ap = new AppProduct();
	protected AppIdcode appidcode = new AppIdcode();
	private AppCartonCode appCartonCode = new AppCartonCode();
	private AppPrintJob appPrintJob = new AppPrintJob();
	private AppOrgan appOrgan = new AppOrgan();
	private AppFUnit appFUnit = new AppFUnit();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		Map<String, Product> existMaterialCodes = new HashMap<String, Product>();
		Map<String, Double> funitMap = new HashMap<String, Double>();
		Set<String> notExistsMaterialCodes = new HashSet<String>();
		Map<String, PrintJob> printJobMap = new HashMap<String, PrintJob>();
		Map<String, Integer> idcodeMap = new HashMap<String, Integer>();
		List<ImportIdcode> idcodeList = new ArrayList<>();
		int batchCount = 999;
		// 初始化
		initdata(request);
		//机构编号
		String organId = request.getParameter("organid");
		// 仓库编号
		String warehouseId = request.getParameter("outwarehouseid");
		// 保存报错信息
		StringBuffer errMsg = new StringBuffer();
		int CCount = 0, SCount = 0;
		Set<String> idcodeSet = new HashSet<>();
		try {
			IdcodeUploadForm mf = (IdcodeUploadForm) form;
			FormFile idcodefile = (FormFile) mf.getIdcodefile();
			boolean bool = false;
			if (idcodefile != null && !idcodefile.equals("")) {
				if (idcodefile.getContentType() != null) {
					if (idcodefile.getFileName().indexOf("xls") >= 0) {
						bool = true;
					}
				}
			}
			if (bool) {
				Organ organ = appOrgan.getOrganByID(organId);
				Workbook wb = Workbook.getWorkbook(idcodefile.getInputStream());
				Sheet sheet = wb.getSheet(0);
				int row = sheet.getRows();
				for (int i = 1; i < row; i++) {
					SCount++;
					try {
						
						// 获取条码
						String idcode = sheet.getCell(0, i).getContents() == null ? null : sheet
								.getCell(0, i).getContents().trim();
						// 条码判断
						if (StringUtil.isEmpty(idcode)) {
							errMsg.append("第[" + (i + 1) + "]行条码不能为空<br />");
							continue;
						}
						// 获取条码长度
						if (idcode.length() != 20) {
							errMsg.append("第[" + (i + 1) + "]行条码格式不正确<br />");
							continue;
						}
						// 判断条码是否全为数字
						if (!NumberUtil.isNumberic(idcode)) {
							errMsg.append("第[" + (i + 1) + "]行条码格式不正确<br />");
							continue;
						}
						
						if(!idcodeSet.add(idcode)) {
							errMsg.append("第[" + (i + 1) + "]行文件中存在重复的条码["+idcode+"]<br />");
							continue;
						}
						
						//判断条码是否存在
						CartonCode cartonCode = appCartonCode.getByCartonCode(idcode);
						if (cartonCode != null) {
							errMsg.append("第[" + (i + 1) + "]行该条码已存在系统中<br />");
							continue;
						}
						
						// 获取物料号
						String mCode = sheet.getCell(1, i).getContents() == null ? null : sheet
								.getCell(1, i).getContents().trim();
						
						// 判断物料号是否为空
						if (StringUtil.isEmpty(mCode)) {
							errMsg.append("第[" + (i + 1) + "]行物料号不能为空<br />");
							continue;
						}
						
						// 判断物料号是否存在
						if (!isMaterialCodeExists(mCode, existMaterialCodes, notExistsMaterialCodes)) {
							errMsg.append("第[" + (i + 1) + "]行物料号"+mCode+"不存在<br />");
							continue;
						}
						
						
						// 获取批次
						String batch = sheet.getCell(2, i).getContents() == null ? null : sheet
								.getCell(2, i).getContents().trim();
						
						// 判断批次是否为空
						if (StringUtil.isEmpty(batch)) {
							errMsg.append("第[" + (i + 1) + "]行批次不能为空<br />");
							continue;
						}
						
						// 获取生产日期
						String productionDate = sheet.getCell(3, i).getContents() == null ? null : sheet
								.getCell(3, i).getContents().trim();
						
						// 判断生产日期是否为空
						if (StringUtil.isEmpty(productionDate)) {
							errMsg.append("第[" + (i + 1) + "]行生产日期不能为空<br />");
							continue;
						}
						
						// 判断生产日期格式是否正确
						if (!DateUtil.isValidDate(productionDate, "yyyyMMdd")) {
							errMsg.append("第[" + (i + 1) + "]行生产日期格式不正确，支持的格式为yyyyMMdd<br />");
							continue;
						}
						
						// 获取过期日期
						String expiryDate = sheet.getCell(4, i).getContents() == null ? null : sheet
								.getCell(4, i).getContents().trim();
						if (!StringUtil.isEmpty(expiryDate)) {
							// 判断过期日期格式是否正确
							if (!DateUtil.isValidDate(expiryDate, "yyyyMMdd")) {
								errMsg.append("第[" + (i + 1) + "]行过期日期格式不正确，支持的格式为yyyyMMdd<br />");
								continue;
							}
						} else {
							expiryDate = getExpiryDate(productionDate, mCode, existMaterialCodes);
						}
						
						// 获取分装日期
						String packageDate = sheet.getCell(5, i).getContents() == null ? null : sheet
								.getCell(5, i).getContents().trim();
						if (!StringUtil.isEmpty(packageDate)) {
							// 判断过期日期格式是否正确
							if (!DateUtil.isValidDate(packageDate, "yyyyMMdd")) {
								errMsg.append("第[" + (i + 1) + "]行封装日期格式不正确，支持的格式为yyyyMMdd<br />");
								continue;
							}
						} 
						//sql语句
						StringBuffer sb = new StringBuffer();
						// 判断Print_Job
						checkAndCreatePrintJob(printJobMap, mCode, batch, productionDate, packageDate, expiryDate, organ, existMaterialCodes);
						
						idcodeList.add(new ImportIdcode(i + 1, idcode, mCode, batch));
						idcodeMap.put(idcode, i + 1);
						if(idcodeList.size()%batchCount==0) {
							dealIdcode(idcodeMap, errMsg, existMaterialCodes, idcodeList, printJobMap,organId, warehouseId, funitMap);
							CCount+=idcodeMap.size();
							idcodeMap.clear();
							idcodeList.clear();
						}
						if(idcodeList.size() > 0) {
							dealIdcode(idcodeMap, errMsg, existMaterialCodes, idcodeList, printJobMap,organId, warehouseId, funitMap);
							CCount+=idcodeMap.size();
							idcodeMap.clear();
							idcodeList.clear();
						}
						HibernateUtil.commitTransaction();
					} catch (Exception e) {
						errMsg.append("第[" + (i + 1) + "]行导入失败<br />");
						logger.error("第[" + (i + 1) + "]行导入失败", e);
						HibernateUtil.rollbackTransaction();
					}
				}
				wb.close();
			} else {
				request.setAttribute("result", "上传文件失败,请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			String resultMsg = "上传条码资料成功,本次总共添加 :" + (SCount) + "条! 成功:" + CCount + "条! 失败："
					+ (SCount - CCount) + "条!";
			if (SCount - CCount > 0) {
				resultMsg = resultMsg + "<br/>&nbsp;&nbsp;&nbsp;&nbsp;失败原因如下：<br/>" + "<div >"
						+ errMsg.toString() + "</div>";
			}
			request.setAttribute("result", resultMsg);
			return mapping.findForward("success");
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			logger.error("文件上传失败", e);
			request.setAttribute("result", "上传文件失败,文件格式不对,请重试");
			return new ActionForward("/sys/lockrecord2.jsp");
		} finally {
			existMaterialCodes.clear();
			notExistsMaterialCodes.clear();
			funitMap.clear();
		}
	}

	
	private void dealIdcode(Map<String, Integer> idcodeMap, StringBuffer errMsg, Map<String, Product> existMaterialCodes, List<ImportIdcode> idcodeList, Map<String, PrintJob> printJobMap, String organId, String warehouseId, Map<String, Double> funitMap) throws Exception {
		//检查条码是否存在
		StringBuffer checkExistsSql = new StringBuffer();
		StringBuffer codes = new StringBuffer();
		for(String idcode : idcodeMap.keySet()) {
			codes.append(",'").append(idcode).append("'");
		}
		checkExistsSql.append("select CARTON_CODE cc from CARTON_CODE where CARTON_CODE in (").append(codes.substring(1)).append(")");
		List<Map<String,String>> existCodes = EntityManager.jdbcquery(checkExistsSql.toString());
		if(existCodes!=null&&existCodes.size()>0) {
			for(Map<String,String> map : existCodes) {
				errMsg.append("第[").append(idcodeMap.get(map.get("cc"))).append("]行该条码已存在系统中<br />");
				idcodeMap.remove(map.get("cc"));
			}
		}
		//插入到系统中
		List<String> insertCartonCodeSqls = new ArrayList<>();
		List<String> insertIdcodeSqls = new ArrayList<>();
		for(ImportIdcode idcode : idcodeList) {
			if(idcodeMap.containsKey(idcode.getIdcode())) {
				insertCartonCodeSqls.add(getInsertCartonCodeSql(idcode, existMaterialCodes, printJobMap));
				insertIdcodeSqls.add(getInsertIdcodeSql(idcode, printJobMap,organId,warehouseId, existMaterialCodes, funitMap));
			}
		}
		EntityManager.executeBatch(insertCartonCodeSqls);
		EntityManager.executeBatch(insertIdcodeSqls);
		updatePrintJob(printJobMap);
	}


	private void updatePrintJob(Map<String, PrintJob> printJobMap) throws Exception {
		for(PrintJob pj : printJobMap.values()) {
			String sql = "update PRINT_JOB set NUMBER_OF_CASES = "+pj.getNumberOfCases()+" where PRINT_JOB_ID="+pj.getPrintJobId();
			EntityManager.executeUpdate(sql);
		}
	}


	private String getInsertIdcodeSql(ImportIdcode ii, Map<String, PrintJob> printJobMap, String organId, String warehouseId, Map<String, Product> existMaterialCodes, Map<String, Double> funitMap) throws Exception {
		PrintJob printJob = printJobMap.get(ii.getMcode()+ii.getBatch());
		Product product = existMaterialCodes.get(ii.getMcode());
		StringBuffer sb = new StringBuffer();
		sb.append(" \r\n insert into idcode (idcode,PRODUCTID,PRODUCTNAME,batch,PRODUCEDATE,unitid,quantity ");
		sb.append(" \r\n ,isuse,isout,CARTONCODE,PALLETCODE,NCLOTNO,packquantity,MAKEORGANID,WAREHOUSEID ,WAREHOUSEBIT) ");
		sb.append(" \r\n select '"+ii.getIdcode()+"','"+product.getId()+"','"+product.getProductname()+"','"+ii.getBatch()+"','"+printJob.getProductionDate()+"',2,1 " );
		sb.append(" \r\n ,1,0,'"+ii.getIdcode()+"','','-1' ,"+getFunit(product.getId(), Constants.DEFAULT_UNIT_ID, funitMap)+" ,'"+organId+"','"+warehouseId+"' ,'000' ");
		sb.append(" \r\n from dual ");
		return sb.toString();
	}


	private double getFunit(String productId, int defaultUnitId, Map<String, Double> funitMap) throws Exception {
		if(funitMap.containsKey(productId)) {
			return funitMap.get(productId);
		} else {
			double quantity = appFUnit.getXQuantity(productId, defaultUnitId);
			if(quantity == 0d) {
				throw new Exception("未找到产品"+productId+"对应的箱单位配置");
			}
			funitMap.put(productId, quantity);
			return quantity;
		}
	}


	private String getInsertCartonCodeSql(ImportIdcode ii, Map<String, Product> existMaterialCodes, Map<String, PrintJob> printJobMap) {
		//往Carton_Code中增加数据
		PrintJob printJob = printJobMap.get(ii.getMcode()+ii.getBatch());
		StringBuffer sql = new StringBuffer();
		sql.append(" \r\n insert into CARTON_CODE  ");
		sql.append(" \r\n (PRODUCT_ID,PALLET_CODE,CARTON_CODE,OUT_PIN_CODE,INNER_PIN_CODE,MATERIAL_CODE,CREATE_DATE,PRINT_JOB_ID,PRIMARY_CODE_STATUS) ");
		sql.append(" \r\n select '"+existMaterialCodes.get(ii.getMcode()).getId()+"','','"+ii.getIdcode()+"','','','"+ii.getMcode()+"',SYSDATE,"+printJob.getPrintJobId()+",1 " );
		sql.append(" \r\n from dual ");
		printJob.setNumberOfCases(printJob.getNumberOfCases()+1);
		return sql.toString();
	}


	private void checkAndCreatePrintJob(Map<String, PrintJob> printJobMap, String mCode, String batch,
			String productionDate, String packageDate, String expiryDate, Organ organ, Map<String, Product> existMaterialCodes) throws Exception {
		if(printJobMap.containsKey(mCode+batch)) {
			return;
		} else {
			PrintJob printJob = appPrintJob.getPrintJobByBatAMc(mCode, batch);
			if(printJob == null) {
				printJob = createPrintJob(mCode, batch, productionDate, packageDate, expiryDate, organ, existMaterialCodes);
			} 
			printJobMap.put(mCode+batch, printJob);
		}
	}


	private PrintJob createPrintJob(String mCode, String batch, String productionDate, String packageDate,
			String expiryDate, Organ organ, Map<String, Product> existMaterialCodes) throws Exception {
		int printJobId = Integer.parseInt(MakeCode.getExcIDByRandomTableName("print_job", 0, ""));
		Product product = existMaterialCodes.get(mCode);
		StringBuffer sb = new StringBuffer();
		//往Print_Job中增加数据
		sb.append(" \r\n insert into PRINT_JOB (PRINT_JOB_ID,TRANS_ORDER,MATERIAL_CODE,BATCH_NUMBER ");
		sb.append(" \r\n ,PRODUCTION_DATE,PACKAGING_DATE,EXPIRY_DATE,NUMBER_OF_CASES,TOTAL_NUMBER,MATERIAL_NAME,PRODUCT_ID");
		sb.append(" \r\n ,PACK_SIZE,UPLOAD_ID,PRINTING_STATUS,PRIMARY_CODE_STATUS,CREATE_DATE,ISDELETE,CONFIRM_FLAG,PLANT_CODE,PLANT_NAME,SYNCSTATUS,CARTONSEQSTATUS)");
		//sb.append(" \r\n select print_job_id_OLD_SEQ.nextval,-1,'"+mCode+"','"+batch+"' ");
		sb.append(" \r\n select "+printJobId+",-1,'"+mCode+"','"+batch+"' ");
		sb.append(" \r\n ,'"+productionDate+"','"+packageDate+"','"+expiryDate+"',1,0,'"+product.getProductname()+"','"+product.getId()+"' ");
		sb.append(" \r\n ,'"+product.getSpecmode()+"',-1,2,"+PrimaryCodeStatus.NOT_REQUIRED.getDatabaseValue()+",sysdate,0,1,'"+organ.getOecode()+"','"+organ.getOrganname()+"',"+SyncStatus.POST_BACK.getValue()+","+CartonSeqStatus.NO_DEED.getValue());
		sb.append(" \r\n from dual ");
		EntityManager.executeUpdate(sb.toString());
		PrintJob pj = new PrintJob();
		pj.setPrintJobId(printJobId);
		pj.setMaterialCode(mCode);
		pj.setBatchNumber(batch);
		pj.setProductionDate(productionDate);
		pj.setNumberOfCases(0);
		return pj;
	}


	/**
	 * 验证物料码是否在系统中存在
	 * Create Time 2014-10-20 上午11:23:26
	 * @param materialCode
	 * @author Ryan.xi
	 * @param notExistsMaterialCodes 
	 * @param existMaterialCodes 
	 */
	public boolean isMaterialCodeExists(String materialCode, Map<String, Product> existMaterialCodes, Set<String> notExistsMaterialCodes) {
		if(existMaterialCodes.containsKey(materialCode)) {
			return true;
		} else if(notExistsMaterialCodes.contains(materialCode)){
			return false;
		} else {
			try {
				Product procuct = ap.getByMCode(materialCode);
				existMaterialCodes.put(materialCode, procuct);
				return true;
			} catch (NotExistException e) {
				notExistsMaterialCodes.add(materialCode);
				return false;
			}
		}
	}
	
	/**
	 * 根据生产日期和保质期得到过期日期
	 * Create Time 2014-10-20 上午11:23:26 
	 * @param existMaterialCodes 
	 * @throws Exception
	 */
	private String getExpiryDate(String dateStr, String mCode, Map<String, Product> existMaterialCodes) {
		Date productionDate =  DateUtil.StringToDate(dateStr, "yyyyMMdd");
		Integer expiryDays = existMaterialCodes.get(mCode).getExpiryDays();
		if(expiryDays != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(productionDate);
			calendar.add(Calendar.DATE, expiryDays);
			Date expiryDate = calendar.getTime();
			return DateUtil.formatDate(expiryDate, "yyyyMMdd");
		}
		return "";
	}
	
	public class ImportIdcode {
		private int lineNo;
		private String idcode;
		private String mcode;
		private String batch;
		public int getLineNo() {
			return lineNo;
		}
		
		public ImportIdcode(int lineNo, String idcode, String mcode, String batch) {
			super();
			this.lineNo = lineNo;
			this.idcode = idcode;
			this.mcode = mcode;
			this.batch = batch;
		}

		public void setLineNo(int lineNo) {
			this.lineNo = lineNo;
		}
		public String getIdcode() {
			return idcode;
		}
		public void setIdcode(String idcode) {
			this.idcode = idcode;
		}
		public String getMcode() {
			return mcode;
		}
		public void setMcode(String mcode) {
			this.mcode = mcode;
		}
		public String getBatch() {
			return batch;
		}
		public void setBatch(String batch) {
			this.batch = batch;
		}
		
	}
}
