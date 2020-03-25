package com.winsafe.sap.dao;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.digester.Digester;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.metadata.LinkMode;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.UploadErrorMsg;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.sap.metadata.CodeType;
import com.winsafe.sap.metadata.PrimaryCodeStatus;
import com.winsafe.sap.metadata.PrintStatus;
import com.winsafe.sap.metadata.SapFileType;
import com.winsafe.sap.metadata.SyncStatus;
import com.winsafe.sap.metadata.YesOrNo; 
import com.winsafe.sap.pojo.CartonCode;
import com.winsafe.sap.pojo.PrintJob;

/*******************************************************************************************  
 * SAP文件处理类
 * @author: ryan.xi	  
 * @date：2014-12-17  
 * @version 1.0  
 *  
 *  
 * Version    Date       ModifiedBy                 Content  
 * -------- ---------    ----------         ------------------------  
 * 1.0      2014-12-17   ryan.xi  
 * 1.1      2017-10-23   ryan.xi            新增后关联模式的打印任务      
 *******************************************************************************************  
 */ 
public abstract class SapOrderUploadHandler extends SapUploadHandler{
	
	private AppPrintJob appPrintJob = new AppPrintJob();
	private AppCartonCode appCartonCode = new AppCartonCode();
	
	protected HashMap<PrintJob,PrintJob> printJobs = new HashMap<PrintJob, PrintJob>();
	//标签打印份数
	private Integer cartonLabelCount = 0;
	//箱码数据
	private TreeSet<CartonCode> cartonCodes = new TreeSet<CartonCode>();

	public SapOrderUploadHandler(SapFileType fileType) throws Exception {
		super(fileType);
		cartonLabelCount = Integer.parseInt(appBaseResource.getBaseResourceValue("CartonLabelCount", 1).getTagsubvalue());
	}
	
	/**
	 * 验证从文件中解析出来的数据是否有效
	 * Create Time 2014-10-20 上午11:23:26
	 * @param printJob
	 * @author Ryan.xi
	 */
	protected boolean doValidate(PrintJob printJob) {
		String lineNo = "第" + (totalCount + 1) + "行 : ";
		//检查GTIN码长度是否超过14位,不足用0补足
		if(!StringUtil.isEmpty(printJob.getGTINNumber())) {
			if(printJob.getGTINNumber().length() > 14) {
				errMsg.append(lineNo).append(UploadErrorMsg.getError(UploadErrorMsg.E00222)).append("\r\n");
				clearAndSetError();
			}
			if(printJob.getGTINNumber().length() < 14) {
				printJob.setGTINNumber(Constants.ZERO_PREFIX[14 - printJob.getGTINNumber().length()] + printJob.getGTINNumber());
			}
		}
		if(StringUtil.isEmpty(printJob.getCartonSSCC())) {
			errMsg.append(lineNo).append(UploadErrorMsg.getError(UploadErrorMsg.E00207)).append("\r\n");
			clearAndSetError();
		}
		//SAP过来的订单要检查机构编号是否存在
		if(fileType == SapFileType.ORDER_SAP) {
			if(!isOrganIdExists(printJob.getPlantCode())) {
				errMsg.append(lineNo).append(UploadErrorMsg.getError(UploadErrorMsg.E00201, printJob.getPlantCode())).append("\r\n");
				clearAndSetError();
			} else {
				printJob.setOrganId(organs.get(printJob.getPlantCode()).getId());
			}
		} else {
			printJob.setOrganId(organs.get(printJob.getPlantCode()).getId());
		}
		//检查机构编号是否存在仓库
		if(!StringUtil.isEmpty(printJob.getOrganId()) && !isOrganWarehousExists(printJob.getOrganId())) {
			errMsg.append(lineNo).append(UploadErrorMsg.getError(UploadErrorMsg.E00202, printJob.getOrganId())).append("\r\n");
			clearAndSetError();
		}
		//检查物料号是否存在且配置了包装比例关系
		if(!isMaterialCodeExists(printJob.getMaterialCode())) {
			errMsg.append(lineNo).append(UploadErrorMsg.getError(UploadErrorMsg.E00204, printJob.getMaterialCode())).append("\r\n");
			clearAndSetError();
		} else if(!isMaterialFUnitExists(existMaterialCodes.get(printJob.getMaterialCode()).getId())) {
			errMsg.append(lineNo).append(UploadErrorMsg.getError(UploadErrorMsg.E00205, printJob.getMaterialCode(), defaultUnitName)).append("\r\n");
			clearAndSetError();
		}
		//检查罐装日期格式是否正确
		if(!StringUtil.isEmpty(printJob.getPackagingDate())) {
			String date = validDate(printJob.getPackagingDate());
			if(date == null) {
				errMsg.append(lineNo).append(UploadErrorMsg.getError(UploadErrorMsg.E00208, "填写日期(Filling Date)" + printJob.getPackagingDate())).append("\r\n");
				clearAndSetError();
			} else {
				printJob.setPackagingDate(date);
			}
			
	    }
		//检查生产日期是否存在且正确
		if(!StringUtil.isEmpty(printJob.getProductionDate())) {
			String date = validDate(printJob.getProductionDate());
			if(date == null) {
				errMsg.append(lineNo).append(UploadErrorMsg.getError(UploadErrorMsg.E00208, "生产日期" + printJob.getProductionDate())).append("\r\n");
				clearAndSetError();
			} else {
				printJob.setProductionDate(date);
			}
		}
		return hasError;
	}
	
	/**
	 * 设置错误状态
	 */
	private void clearAndSetError() {
		if(!hasError) {
			printJobs.clear();
			hasError = true;
		}
	}
	
	/**
	 * 验证字符串是否为有效的日期格式
	 * Create Time 2014-10-20 上午11:23:26
	 * @param date
	 * @author Ryan.xi
	 */
	public String validDate(String dateStr) { 
		if(!StringUtil.isEmpty(dateStr)) {
			String format = getDateFormat(dateStr);
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
//			if(format.indexOf("-") != -1 || format.indexOf("/") != -1) {
//				dateStr = dateStr.substring(0, 10);
//			} else {
//				dateStr = dateStr.substring(0, 8);
//			}
			if(dateStr.indexOf("T") != -1) {
				dateStr = dateStr.substring(0, dateStr.indexOf("T"));
		    }
			 try { 
				  dateFormat.setLenient(false); 
		          Date date = dateFormat.parse(dateStr); 
		          return DateUtil.formatDate(date, "yyyyMMdd");
		       } catch (Exception e) { 
		    	  return null;
		       } 
		}
		
		return null;
	
	}
	
	/**
	 * 获取可用日期格式
	 * @param date
	 * @return
	 */
	private String getDateFormat(String date) {
		if(fileType == SapFileType.ORDER_SAP) {
			return "yyyyMMdd";
		}
		if(date.indexOf("T") != -1) {
	        date = date.substring(0, date.indexOf("T"));
	    }
		if(date.indexOf("-") != -1) {
	    	String[] dates = date.split("-");
	    	if(dates[0].length() < 4) {
	    		return "MM-dd-yyyy";
	    	} else {
	    		return "yyyy-MM-dd";
	    	}
	    }
	    if(date.indexOf("/") != -1) {
	    	String[] dates = date.split("/");
	    	if(dates[0].length() < 4) {
	    		return "MM/dd/yyyy";
	    	} else {
	    		return "yyyy/MM/dd";
	    	}
	    	
	    }
	    return "yyyyMMdd";
	}
	
	/* (non-Javadoc)
	 * @see com.winsafe.sap.dao.SapUploadHandler#checkResult()
	 */
	@Override
	public void checkResult() {
		if(printJobs.size() == 0 && cartonCodes.size() == 0 && !hasError) {
			hasError = true;
			errMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00211));
		}
	}
	
	/**
	 * 由Digester框架在解析文件时调用
	 * Create Time 2014-10-20 上午11:23:26
	 * @param printJob 
	 * @throws Exception
	 * @author Ryan.xi
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public void addPrintJob(PrintJob printJob) throws Exception {
		totalCount++;
		//预处理
		preProcess(printJob);
		if(!doValidate(printJob)) {
			if(StringUtil.isEmpty(printJob.getExpiryDate())) {
				setExpiryDate(printJob);
			}
			Product product = existMaterialCodes.get(printJob.getMaterialCode());
			//产品表标签份数不为空 并且>0时
			if (product.getCopys() != null && product.getCopys() > 0) {
				//标签份数为从产品表中取得
				cartonLabelCount = product.getCopys();
			}
			if(printJobs.containsKey(printJob)) {
				PrintJob temp = printJobs.get(printJob);
				temp.setNumberOfCases(temp.getNumberOfCases() + 1);
				temp.setTotalNumber(temp.getNumberOfCases() * cartonLabelCount);
				printJobs.put(temp, temp);
			} else {
				//生成打印任务
				printJob.setPrintJobId(Integer.parseInt(MakeCode.getExcIDByRandomTableName("print_job", 0, "")));
				printJob.setNumberOfCases(1);
				printJob.setTotalNumber(1 * cartonLabelCount);
				printJob.setUploadId(sapUploadLog.getId());
				printJob.setPrintingStatus(YesOrNo.NO.getValue());
				printJob.setMaterialName(existMaterialCodes.get(printJob.getMaterialCode()).getProductname());
				printJob.setProductId(existMaterialCodes.get(printJob.getMaterialCode()).getId());
				printJob.setPackSize(existMaterialCodes.get(printJob.getMaterialCode()).getSpecmode());
				if(fileType == SapFileType.ORDER_SAP) {
					printJob.setPlantName(organs.get(printJob.getPlantCode()).getOrganname());
					printJob.setOrganId(organs.get(printJob.getPlantCode()).getId());
				}
				if(fileType == SapFileType.ORDER_TOLLER) {
					printJob.setPrimaryCodeStatus(PrimaryCodeStatus.NOT_REQUIRED.getDatabaseValue());
				} else {
					if(existMaterialCodes.get(printJob.getMaterialCode()).getPrimaryPrintFlag() == YesOrNo.YES.getValue()) {
						printJob.setPrimaryCodeStatus(PrimaryCodeStatus.NOT_GENERATED.getDatabaseValue());
					} else {
						printJob.setPrimaryCodeStatus(PrimaryCodeStatus.NOT_REQUIRED.getDatabaseValue());
					}
				}
				//--20190806--印刷产品无需生成小包装码 
				if(product.getIsidcode()!=null && product.getIsidcode() == YesOrNo.YES.getValue()) {
					printJob.setPrimaryCodeStatus(PrimaryCodeStatus.NOT_REQUIRED.getDatabaseValue());
				}
				printJob.setPrintingStatus(PrintStatus.NOT_PRINTED.getDatabaseValue());
				printJob.setIsDelete(YesOrNo.NO.getValue());
				
				//设置关系模式
				if(product.getCodeType() != null && product.getLinkMode() !=null) {
					printJob.setCodeType(product.getCodeType());
					printJob.setLinkMode(product.getLinkMode());
				} else {//若产品未设置条码类型和关联模式,默认DM,前关联
					printJob.setCodeType(CodeType.DM.getValue());
					printJob.setLinkMode(LinkMode.Before.getValue());
				}
				if(product.getPrimaryPrintFlag() == YesOrNo.YES.getValue()) {
					printJob.setSyncStatus(SyncStatus.NOT_UPLOADED.getValue());
				} else {
					printJob.setSyncStatus(SyncStatus.NODEED.getValue());
				}
				printJobs.put(printJob, printJob);
			}
			//生成箱码信息
			createCartonCodeFromPrintJob(printJob);
			
			printJob.setCreateUser(sapUploadLog.getMakeId());
			printJob.setCreateDate(Dateutil.getCurrentDate());
			
		} else {
			errCount++;
		}
	}
	
	/**
	 * 生成箱码数据
	 * @param printJob
	 */
	private void createCartonCodeFromPrintJob(PrintJob printJob) {
		//生成outPinCode, 包括GTIN码或者物料码，批次号，生产日期和箱码
		StringBuffer outPinCode = new StringBuffer();
		if(StringUtil.isEmpty(printJob.getGTINNumber())) {
			outPinCode.append("01").append(printJob.getMaterialCode());
		} else {
			outPinCode.append("01").append(printJob.getGTINNumber());
		}
		outPinCode.append("10").append(printJob.getBatchNumber());
		outPinCode.append("11").append(printJob.getProductionDate());
		outPinCode.append(printJob.getCartonSSCC());
		
		CartonCode cartonCode = new CartonCode();
		cartonCode.setOutPinCode(outPinCode.toString());
		cartonCode.setCartonCode(printJob.getCartonSSCC());
		cartonCode.setPalletCode(printJob.getPalletSSCC());
		cartonCode.setCreateDate(Dateutil.getCurrentDate());
		cartonCode.setMaterialCode(printJob.getMaterialCode());
		cartonCode.setPrintJobId(printJobs.get(printJob).getPrintJobId());
		cartonCode.setPrimaryCodeStatus(0);
		cartonCode.setCreateDate(Dateutil.getCurrentDate());
		cartonCode.setProductID(existMaterialCodes.get(printJob.getMaterialCode()).getId());

		cartonCodes.add(cartonCode);
		
	}

	/**
	 * 预处理解析后的数据
	 * Create Time 2014-10-20 上午11:23:26 
	 * @throws Exception
	 */
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
	 * Create Time 2014-10-20 上午11:23:26 
	 * @throws Exception
	 */
	private void setExpiryDate(PrintJob printJob) {
		if(!StringUtil.isEmpty(printJob.getProductionDate())) {
			Date productionDate =  DateUtil.formatStrDate(printJob.getProductionDate());
			Integer expiryDays = existMaterialCodes.get(printJob.getMaterialCode()).getExpiryDays();
			if(expiryDays != null) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(productionDate);
				calendar.add(Calendar.DATE, expiryDays);
				Date expiryDate = calendar.getTime();
				printJob.setExpiryDate(DateUtil.formatDate(expiryDate, "yyyyMMdd"));
			}
		}
	}
	/**
	 * 将解析后的数据添加到系统中
	 * Create Time 2014-10-20 上午11:23:26 
	 * @throws Exception
	 */
	@Override
	public void handle() throws Exception {
		Map<String, Integer> countInPalletMap = new HashMap<String, Integer>();
		Map<Integer, Integer> printSeqMap = new HashMap<Integer, Integer>();
		Integer countInPallet = 0;
		Integer cartonSeq = 0;
		String currentPalletCode = "";
		for(CartonCode cc : cartonCodes) {
			//设置托中箱数量，箱顺序
			if(cc.getPalletCode() != null) {
				if(currentPalletCode.equals(cc.getPalletCode())) {
					cartonSeq++;
					countInPallet++;
				} else {
					currentPalletCode = cc.getPalletCode();
					cartonSeq = 1;
					countInPallet = 1;
				}
				cc.setCartonSeq(cartonSeq);
				countInPalletMap.put(currentPalletCode, countInPallet);
			} 
			//设置箱码打印顺序
			if(printSeqMap.containsKey(cc.getPrintJobId())) {
				Integer printSeq = printSeqMap.get(cc.getPrintJobId()) + 1;
				cc.setPrintSeq(printSeq);
				printSeqMap.put(cc.getPrintJobId(), printSeq);
			} else {
				cc.setPrintSeq(1);
				printSeqMap.put(cc.getPrintJobId(), 1);
			}
		}
		try {
			appCartonCode.addCartonCodes(cartonCodes, countInPalletMap);
		} catch (Exception e) {
			if (e.getCause() != null
					&& e.getCause().getMessage().contains("ORA-00001")) {
				throw new Exception("数据错误：导入数据中的箱码信息在系统中已存在");
			} else {
				throw e;
			}
		}
		appPrintJob.addPrintJobs(printJobs);
		addIdcode();
	}
	/**
	 * 将码添加到码库中
	 * Create Time 2014-10-20 上午11:23:26 
	 * @throws Exception
	 */
	private void addIdcode() throws Exception {
		for(PrintJob printJob : printJobs.values()) {
			Warehouse warehouse = organWithWarehouse.get(printJob.getOrganId());
			FUnit fUnit = materialWithFunit.get(printJob.getProductId());
			appIdcode.addIdcodeByPrintJob(printJob.getPrintJobId(), fUnit.getFunitid(), warehouse.getId(), fUnit.getXquantity());
		}
	}
	public abstract void addRule(Digester digester);
}
