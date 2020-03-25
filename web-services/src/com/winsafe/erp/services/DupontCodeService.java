package com.winsafe.erp.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.erp.dao.AppProductPlan;
import com.winsafe.erp.pojo.DupontCodeUploadLog;
import com.winsafe.erp.pojo.DupontPrimaryCode;
import com.winsafe.erp.pojo.ProductPlan;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppDupontCodeUploadLog;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.metadata.PrimaryCodeStatus;
import com.winsafe.sap.metadata.PrintStatus;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.pojo.PrintJob;
import com.winsafe.sap.util.FileUploadUtil;

public class DupontCodeService {
	
	private Logger logger = Logger.getLogger(DupontCodeService.class);
	public final Integer DB_IN_SIZE = 999;
	private AppDupontCodeUploadLog rDao = new AppDupontCodeUploadLog();
	AppProductPlan appProductPlan = new AppProductPlan();
	AppPrintJob appPrintJob = new AppPrintJob();
	AppProduct appProduct = new AppProduct();
	AppFUnit appFUnit = new AppFUnit();
	AppDupontCodeUploadLog adcul = new AppDupontCodeUploadLog();
	Map<String, ProductPlanWithCodes> planMap = new HashMap<String, ProductPlanWithCodes>();
	Map<String, PrintJobWithCodes> printJobMap = new HashMap<String, PrintJobWithCodes>();
	Set<String> notExistsMaterials = new HashSet<String>();
	Map<String, Product> existsMaterials = new HashMap<String, Product>();
	Set<String> notExistsFUnit = new HashSet<String>();
	Map<String, FUnit> existsFUnit = new HashMap<String, FUnit>();
	//箱码对应的条码记录
	Map<String, List<DupontPrimaryCode>> dpcMap = new HashMap<String, List<DupontPrimaryCode>>();
	//有问题的箱码
	Set<String> errorCartoncode = new HashSet<String>();
	Map<String,String> primaryCodeCartonCodeMap = new HashMap<String, String>();
	
	private Organ organ;
	private Warehouse warehouse;
	private UsersBean users;
	DupontCodeUploadLog uploadlog;
	Date currentDate = DateUtil.getCurrentDate();
	
	StringBuffer resultMsg = new StringBuffer();
	int lineNumber = 0;
	int errCount = 0;

	public DupontCodeService(Organ organ, Warehouse warehouse, UsersBean users) {
		this.organ = organ;
		this.warehouse = warehouse;
		this.users = users;
	}


	public void deal(FormFile codeFile) throws Exception { 
		logger.debug("saveFile(codeFile)...");
		File file = saveFile(codeFile);
		logger.debug("readFile(file)...");
		readFile(file); 
		logger.debug("checkIsCartonCodeAlreadyExist()...");
		checkIsCartonCodeAlreadyExist();  
		logger.debug("checkIsPrimaryCodeAlreadyExist()...");
		checkIsPrimaryCodeAlreadyExist();  
		logger.debug("addDupontPrimaryCode()...");
		addDupontPrimaryCode();
		logger.debug("addPrintJob()...");
		addPrintJob();
		logger.debug("addProductPlan()...");
		addProductPlan(); 
		logger.debug("createLogFile()...");
		createLogFile(codeFile);
		logger.debug("createDeliveryFile()...");
		createDeliveryFile();
	}
	
	
	private void createDeliveryFile() throws Exception { 
		String saveFileName = UUID.randomUUID().toString().replaceAll("-", "") + ".xls";
		String savePath = FileUploadUtil.getDupontCodeFilePath() + DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM") + "/";
		FileUploadUtil.createEmptyFile(savePath, saveFileName);
		WritableWorkbook workbook = Workbook.createWorkbook(new File(savePath, saveFileName));
		
		int snum = 1; 
		snum = dpcMap.size() / 50000;
		if (dpcMap.size() % 50000 >= 0) {
			snum++;
		}
		WritableSheet[] sheets = new WritableSheet[snum];
		for (int j = 0; j < snum; j++) {
			sheets[j] = workbook.createSheet("sheet" + j, j);
			int currentnum = (j + 1) * 40000;
			if (currentnum >= dpcMap.size()) {
				currentnum = dpcMap.size();
			}
			int start = j * 40000;
			
			WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat wcfFC = new WritableCellFormat(wfc);
			
			
			sheets[j].addCell(new Label(0, start, "BUN_EXIDV", wcfFC));
			sheets[j].addCell(new Label(1, start, "MATNR", wcfFC));
			sheets[j].addCell(new Label(2, start, "CHARG", wcfFC));
			int row = 0;
			int i = start;
			for(String cartonCode : dpcMap.keySet()) {
				row = i - start + 1;
				DupontPrimaryCode dpc = dpcMap.get(cartonCode).get(0);
				sheets[j].addCell(new Label(0, row, cartonCode));
				sheets[j].addCell(new Label(1, row, dpc.getMaterialCode()));
				sheets[j].addCell(new Label(2, row, dpc.getBcsBatch()));
				i++;
			}
		}
		workbook.write();
		workbook.close();
		uploadlog.setCodeFilePath(savePath + saveFileName);
		rDao.updDupontCodeUploadLog(uploadlog);
	}


	private void createLogFile(FormFile codeFile) throws Exception {
		if(resultMsg.length() > 0) {
			String fileName = codeFile.getFileName();
			String saveFileName = UUID.randomUUID().toString().replaceAll("-", "") + "_" + fileName+"_log.txt";
			String savePath = FileUploadUtil.getDupontCodeLogFilePath() + DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM") + "/";
			FileUploadUtil.CreateFileWithMessage(resultMsg.toString(), savePath, saveFileName);
			uploadlog.setLogFilePath(savePath + saveFileName);
		}
		uploadlog.setErrorCount(errCount);
		uploadlog.setTotalCount(lineNumber);
		rDao.updDupontCodeUploadLog(uploadlog);
	}


	private void addPrintJob() throws Exception {
		for(String cartonCode : dpcMap.keySet()) {
			DupontPrimaryCode dpc = dpcMap.get(cartonCode).get(0);
			String key = dpc.getMaterialCode()+"_"+dpc.getBcsBatch();
			if(printJobMap.containsKey(key)) {
				printJobMap.get(key).getCartonCodes().add(cartonCode);
			} else {
				PrintJobWithCodes ppwc = createPrintJobWithCodes(dpc);
				printJobMap.put(key, ppwc);
			}
		}
		
		for(PrintJobWithCodes ppwc : printJobMap.values()) { 
			PrintJob pp = ppwc.getPrintJob();
			List<String> sqls = new ArrayList<String>();
			for(String cartonCode : ppwc.getCartonCodes()) {
				sqls.add(getAddCartonCodeSql(cartonCode, pp)); 
				if(sqls.size() >= Constants.DB_BULK_COUNT) {
					adcul.executeBatchSqls(sqls);
					sqls.clear();
				}
 			}
			if(sqls.size() > 0) {
				adcul.executeBatchSqls(sqls);
			}
			FUnit funit = existsFUnit.get(pp.getProductId());
			adcul.addIdcodeByPrintJob(pp.getPrintJobId(), funit.getFunitid(), warehouse.getId(), funit.getXquantity());
		}
		
		
		
	}

	private String getAddCartonCodeSql(String cartonCode, PrintJob pp) {
		String sql = "INSERT INTO CARTON_CODE(CARTON_CODE,OUT_PIN_CODE,CREATE_DATE,MATERIAL_CODE,PRODUCT_ID,PRINT_JOB_ID,PRIMARY_CODE_STATUS) " +
			"VALUES('"+cartonCode+"','"+pp.getTdCode()+"',SYSDATE,'"+pp.getMaterialCode()+"','"+pp.getProductId()+"',"+pp.getPrintJobId()+",0)";
		return sql;
	}

	private PrintJobWithCodes createPrintJobWithCodes(DupontPrimaryCode dpc) throws Exception {
		Product product = existsMaterials.get(dpc.getMaterialCode());
		PrintJob pj = appPrintJob.getByPidAndBatch(product.getId(), dpc.getBcsBatch());
		if(pj == null) {
			pj = createPrintJob(dpc);
		}
		pj.setTdCode(dpc.getBcsTDCode());
		PrintJobWithCodes ppwc = new PrintJobWithCodes();
		ppwc.setPrintJob(pj);
		List<String> cartonCodes = new ArrayList<String>();
		cartonCodes.add(dpc.getBcsCartoncode());
		ppwc.setCartonCodes(cartonCodes);
		return ppwc;
	}
	
	private PrintJob createPrintJob(DupontPrimaryCode dpc) throws Exception {
		Product product = existsMaterials.get(dpc.getMaterialCode());
		PrintJob pj = new PrintJob();
		Integer pjid = Integer.parseInt(MakeCode.getExcIDByRandomTableName("print_job", 0, ""));
		pj.setTransOrder("");
		pj.setGTINNumber("");
//		pj.setProcessOrderNumber(plan.getPONO());
		pj.setProductionDate(Dateutil.formatDate(dpc.getProductionDate(),"yyyyMMdd"));
		pj.setPackagingDate(Dateutil.formatDate(dpc.getProductionDate(),"yyyyMMdd"));
		pj.setBatchNumber(dpc.getBcsBatch());
		pj.setMaterialName(product.getProductname());
		pj.setProductId(product.getId());
		pj.setMaterialCode(product.getmCode());
		pj.setExpiryDate(GetExpiryDate(pj.getProductionDate(),product.getExpiryDays()));
		pj.setPackSize(product.getSpecmode());
		pj.setIsDelete(YesOrNo.NO.getValue());
		pj.setPrintingStatus(PrintStatus.PRINTED.getDatabaseValue());
		pj.setPrintJobId(pjid);
		pj.setCreateUser(users.getUserid());
		pj.setPrimaryCodeStatus(PrimaryCodeStatus.NOT_REQUIRED.getDatabaseValue());
		pj.setPlantCode(organ.getOecode());
		pj.setPlantName(organ.getOrganname());
//		pj.setNumberOfCases(plan.getBoxnum());
//		pj.setTotalNumber(plan.getBoxnum()*plan.getCopys());
		pj.setCreateDate(Dateutil.getCurrentDate()); 
		
//		pj.setMaterialBatchNo(dpc.getBcsBatch());
		preProcess(pj);
		pj.setUploadId(uploadlog.getId());
		appPrintJob.addPrintJob(pj);
//		plan.setPrintJobId(pj.getPrintJobId());
//		abu.updProductPlan(plan);
		return pj;
	}


	private void preProcess(PrintJob printJob) {
		//如果物料码超过8位，取最后八位
		String materialCode = printJob.getMaterialCode();
		if(!StringUtil.isEmpty(materialCode) && materialCode.length() > 8) {
			int length = materialCode.length();
			printJob.setMaterialCode(materialCode.substring(length - 8, length));
		}
		//如果GTIN码为空，则用物料码生成14位GTIN码（不包括前缀），位数不足用0补足
		if(StringUtil.isEmpty(printJob.getGTINNumber())) {
			printJob.setGTINNumber(Constants.ZERO_PREFIX[6] + printJob.getMaterialCode());
		}
		//如果批次不足10位，前面加0补足
		if(!StringUtil.isEmpty(printJob.getBatchNumber()) && printJob.getBatchNumber().length() < 10) {
			printJob.setBatchNumber(Constants.ZERO_PREFIX[10 - printJob.getBatchNumber().length()] + printJob.getBatchNumber());
		}
		
	}
	
	/**
	 * 根据生产日期和保质期得到过期日期
	 * @throws Exception
	 */
	private String GetExpiryDate(String proDate,Integer dats) {
		Date productionDate =  DateUtil.formatStrDate(proDate);
		if(dats != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(productionDate);
			calendar.add(Calendar.DATE, dats);
			Date expiryDate = calendar.getTime();
			return DateUtil.formatDate(expiryDate, "yyyyMMdd");
		}else{
			return "";
		}
	}


	private void addDupontPrimaryCode() {
		for(List<DupontPrimaryCode> list : dpcMap.values()) {
			adcul.addDupontPrimaryCodes(list);
		}
	}

	private File saveFile(FormFile codeFile) throws Exception {
		// 保存文件
		String fileName = codeFile.getFileName();
		String saveFileName = UUID.randomUUID().toString().replaceAll("-", "") + "_" + fileName;
		String savePath = FileUploadUtil.getDupontCodeFilePath() + DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM") + "/";
		File file = FileUploadUtil.saveUplodedFile(codeFile.getInputStream(), savePath, saveFileName);
		addUploadlog(fileName, savePath, saveFileName);	
		if(codeFile.getFileName().toLowerCase().indexOf("zip") >= 0) {
			List<File> files = FileUploadUtil.unZipFiles(file, savePath); 
			return files.get(0);
		}
		return file;
	}

	private void addUploadlog(String fileName, String savePath, String saveFileName) throws Exception { 
		//添加文件上传日志
		uploadlog = new DupontCodeUploadLog();
		uploadlog.setFileName(fileName);
		uploadlog.setFilePath(savePath + saveFileName);
		uploadlog.setMakeId(users.getUserid());
		uploadlog.setMakeOrganId(users.getMakeorganid());
		uploadlog.setMakeDate(DateUtil.getCurrentDate());
		rDao.addDupontCodeUploadLog(uploadlog);
	}

	private void addProductPlan() throws Exception {
		for(String cartonCode : dpcMap.keySet()) {
			DupontPrimaryCode dpc = dpcMap.get(cartonCode).get(0);
			String key = dpc.getMaterialCode()+"_"+dpc.getBcsBatch()+"_"+dpc.getProductionDate();
			if(planMap.containsKey(key)) {
				ProductPlan productPlan = planMap.get(key).getProductPlan();
				productPlan.setBoxnum(productPlan.getBoxnum()+1);
				planMap.get(key).getCartonCodes().add(cartonCode);
			} else {
				ProductPlanWithCodes ppwc = createProductPlanWithCodes(dpc);
				planMap.put(key, ppwc);
			}
		}
		for(ProductPlanWithCodes ppwc : planMap.values()) { 
			ProductPlan pp = ppwc.getProductPlan();
			appProductPlan.AddProductPlan(pp);
			List<String> sqls = new ArrayList<String>();
			for(String cartonCode : ppwc.getCartonCodes()) {
				sqls.add(getAddPrepareCodeSql(cartonCode, ppwc.getProductPlan().getId(), pp.getTdCode())); 
				if(sqls.size() >= Constants.DB_BULK_COUNT) {
					adcul.executeBatchSqls(sqls);
					sqls.clear();
				}
 			}
			if(sqls.size() > 0) {
				adcul.executeBatchSqls(sqls);
			}
		}
	}

	private String getAddPrepareCodeSql(String cartonCode, long id, String tdCode) {  
//		String sql = "INSERT INTO PREPARE_CODE(CODE,SAP_LOG_ID,ISUSE,ORGANID,MODIFIED_DATE,MODIFIED_USER,PRODUCTPLAN_ID,TCODE) " +
//				"VALUES('"+cartonCode+"',"+uploadlog.getId()+",1,'"+organ.getId()+"',SYSDATE,"+users.getUserid()+","+id+",'"+tdCode+"')";
		String sql = "INSERT INTO PREPARE_CODE(CODE,SAP_LOG_ID,ISUSE,ORGANID,MODIFIED_DATE,MODIFIED_USER,PRODUCTPLAN_ID) " +
				"VALUES('"+cartonCode+"',"+uploadlog.getId()+",1,'"+organ.getId()+"',SYSDATE,"+users.getUserid()+","+id+")";
		return sql;
	}

	private ProductPlanWithCodes createProductPlanWithCodes(DupontPrimaryCode dpc) throws Exception {
		ProductPlan pp = new ProductPlan();
		pp.setTemp("已生成");
		pp.setApprovalFlag(1);
		pp.setCloseFlag(1);
		pp.setIsUpload(1);
		pp.setMakeId(users.getUserid());
		pp.setCloseMan(users.getUserid());
		pp.setMbatch(dpc.getBcsBatch());
		pp.setOrganId(organ.getId());
		pp.setPackDate(dpc.getProductionDate());
		pp.setPbatch(dpc.getBcsBatch());
		pp.setProDate(dpc.getProductionDate());
		pp.setProductId(existsMaterials.get(dpc.getMaterialCode()).getId());
		pp.setTdCode(dpc.getBcsTDCode());
		pp.setBoxnum(1);
		ProductPlanWithCodes ppwc = new ProductPlanWithCodes();
		ppwc.setProductPlan(pp);
		List<String> cartonCodes = new ArrayList<String>();
		cartonCodes.add(dpc.getBcsCartoncode());
		ppwc.setCartonCodes(cartonCodes);
		return ppwc;
	}

	private void checkIsCartonCodeAlreadyExist() throws Exception {
		List<String> sqls = new ArrayList<String>();
		StringBuffer cartonCodes = new StringBuffer();
		int count = 0;
		for(String cartonCode : dpcMap.keySet()) {
			cartonCodes.append(",'"+cartonCode+"'");
			count++;
			if(count >= DB_IN_SIZE) { 
				sqls.add(getCheckCartonCodeSql(cartonCodes.substring(1)));
				cartonCodes = new StringBuffer();
				count = 0;
			}
		}
		if(cartonCodes.length() > 0) {
			sqls.add(getCheckCartonCodeSql(cartonCodes.substring(1)));
		}
		for(String sql : sqls) {
			List<Map<String,String>> result = adcul.executeSql(sql);
			for(Map<String,String> map : result) {
				String cartoncode = map.get("cartoncode");
				List<DupontPrimaryCode> list = dpcMap.get(cartoncode); 
				errCount = errCount + list.size();
				resultMsg.append("第[");
				for(DupontPrimaryCode dpc : list) {
					resultMsg.append(dpc.getLineNo()+",");
				}
				resultMsg.append("]行对应的箱码在系统中已存在\r\n");
				dpcMap.remove(cartoncode);
			}
		}
	}

	private String getCheckCartonCodeSql(String codes) {
		String sql = "select CARTON_CODE cartoncode from CARTON_CODE where CARTON_CODE in ("+codes+")";
		return sql;
	}

	private void checkIsPrimaryCodeAlreadyExist() throws Exception {
		List<String> sqls = new ArrayList<String>();
		StringBuffer primaryCodes = new StringBuffer();
		int count = 0;
		for(List<DupontPrimaryCode> list : dpcMap.values()) {
			for(DupontPrimaryCode dpc : list) {
				primaryCodes.append(",'"+dpc.getPrimaryCode()+"'");
				count++;
				if(count >= DB_IN_SIZE) {
					sqls.add(getCheckPrimaryCodeSql(primaryCodes.substring(1)));
					primaryCodes = new StringBuffer();
					count = 0;
				}
			}
		}
		if(primaryCodes.length() > 0) {
			sqls.add(getCheckPrimaryCodeSql(primaryCodes.substring(1)));
		}
		for(String sql : sqls) {
			List<Map<String,String>> result = adcul.executeSql(sql);
			for(Map<String,String> map : result) {
				String cartoncode = map.get("cartoncode");
				String primarycode = map.get("primarycode");
				List<DupontPrimaryCode> list = dpcMap.get(primaryCodeCartonCodeMap.get(primarycode)); 
				if(list != null) {
					errCount = errCount + list.size();
					for(DupontPrimaryCode dpc : list) {
						if(dpc.getPrimaryCode().equals(primarycode)) {
							resultMsg.append("第["+dpc.getLineNo()+"]行小包装码（激光码）["+primarycode+"]在系统中已存在,文件中对应箱码["+dpc.getBcsCartoncode()+"]与系统中箱码["+cartoncode+"]不一致,将不做处理\r\n");
						}
					}
					dpcMap.remove(primaryCodeCartonCodeMap.get(primarycode));
				}
			}
		}
	}

	private String getCheckPrimaryCodeSql(String pcodes) {
		String sql = "select PRIMARYCODE primarycode,BCSCARTONCODE cartoncode from DUPONT_PRIMARY_CODE where PRIMARYCODE in ("+pcodes+")";
		return sql;
	}

	private void readFile(File file) throws Exception { 
		boolean hasError = false;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				//空行不处理
				if(StringUtil.isEmpty(line)) {
					continue;
				}
				lineNumber++;
				String[] codeData = line.split(",");
				hasError = doValidate(codeData, lineNumber, resultMsg); 
				if(hasError) {
					errorCartoncode.add(codeData[6]);
					errCount++;
					dpcMap.remove(codeData[6]);
					continue;
				}
				primaryCodeCartonCodeMap.put(codeData[4], codeData[6]);
				DupontPrimaryCode dpc = getDupontPrimaryCode(codeData);
				if(dpcMap.containsKey(dpc.getBcsCartoncode())) {
					dpcMap.get(dpc.getBcsCartoncode()).add(dpc);
				} else {
					List<DupontPrimaryCode> list = new ArrayList<DupontPrimaryCode>();
					list.add(dpc);
					dpcMap.put(dpc.getBcsCartoncode(), list);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if(br != null) {
				br.close();
			}
		}
		
	}
	
	private DupontPrimaryCode getDupontPrimaryCode(String[] codeData) {
		DupontPrimaryCode dpc = new DupontPrimaryCode();
		dpc.setMaterialCode(codeData[0]);
		dpc.setProductionDate(DateUtil.StringToDate(codeData[1]));
		dpc.setDpBatch(codeData[2]);
		dpc.setBcsBatch(codeData[3]);
		dpc.setPrimaryCode(codeData[4]);
		dpc.setDpCartoncode(codeData[5]);
		dpc.setBcsCartoncode(codeData[6]);
		dpc.setBcsTDCode(codeData[7]);
		dpc.setDpPalletCode(codeData[8]);
		dpc.setMakeDate(currentDate);
		dpc.setUploadId(uploadlog.getId());
		dpc.setLineNo(lineNumber);
		return dpc;
	}

	private boolean doValidate(String[] codeData, int lineNumber,
			StringBuffer resultMsg) {
		if(errorCartoncode.contains(codeData[6])) {
			resultMsg.append("第["+lineNumber+"]行对应的箱码出现过错误,将不处理,箱码["+codeData[6]+"]\r\n");
			return true;
		}
		if(!isMaterialExists(codeData[0])) {
			resultMsg.append("第["+lineNumber+"]行物料号不存在,箱码["+codeData[6]+"]\r\n");
			return true;
		}
		Product product = existsMaterials.get(codeData[0]);
		if(!isFUnitInfoExists(product.getId(), Constants.DEFAULT_UNIT_ID)) {
			resultMsg.append("第["+lineNumber+"]行物料号对应的包装比例关系不存在,箱码["+codeData[6]+"]\r\n");
			return true;
		}
		if(primaryCodeCartonCodeMap.containsKey(codeData[5])) {
			resultMsg.append("第["+lineNumber+"]行小包装码(激光码)文件中有重复,箱码["+codeData[6]+"]\r\n");
			return true;
		}
		return false;
	}

	private boolean isFUnitInfoExists(String proId, int defaultUnitId) {
		if(notExistsFUnit.contains(proId)) {
			return false;
		}
		if(existsFUnit.containsKey(proId)) {
			return true;
		}
		try {
			FUnit fUnit =appFUnit.getFUnit(proId, defaultUnitId);
			existsFUnit.put(proId, fUnit);
			return true;
		} catch (Exception e) { 
			notExistsFUnit.add(proId);
			return false;
		}
	}

	private boolean isMaterialExists(String materialCode) {
		if(notExistsMaterials.contains(materialCode)) {
			return false;
		}
		if(existsMaterials.containsKey(materialCode)) {
			return true;
		}
		try {
			Product product =appProduct.getByMCode(materialCode);
			existsMaterials.put(materialCode, product);
			return true;
		} catch (Exception e) { 
			notExistsMaterials.add(materialCode);
			return false;
		}
	}

	public class ProductPlanWithCodes {
		private ProductPlan productPlan;
		private List<String> cartonCodes;
		public ProductPlan getProductPlan() {
			return productPlan;
		}
		public void setProductPlan(ProductPlan productPlan) {
			this.productPlan = productPlan;
		}
		public List<String> getCartonCodes() {
			return cartonCodes;
		}
		public void setCartonCodes(List<String> cartonCodes) {
			this.cartonCodes = cartonCodes;
		}
	}
	
	public class PrintJobWithCodes {
		private PrintJob printJob;
		private List<String> cartonCodes;
		public List<String> getCartonCodes() {
			return cartonCodes;
		}
		public void setCartonCodes(List<String> cartonCodes) {
			this.cartonCodes = cartonCodes;
		}
		public PrintJob getPrintJob() {
			return printJob;
		}
		public void setPrintJob(PrintJob printJob) {
			this.printJob = printJob;
		}
	}

	public UsersBean getUsers() {
		return users;
	}

	public void setUsers(UsersBean users) {
		this.users = users;
	}
	
}
