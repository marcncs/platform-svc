package com.winsafe.sap.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Set; 

import org.apache.commons.lang.StringUtils;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.metadata.LinkMode;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.dao.AppCommonCodeLog;
import com.winsafe.sap.dao.AppPrimaryCode;
import com.winsafe.sap.dao.AppSeed;
import com.winsafe.sap.metadata.SeedType;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.pojo.CartonCode;
import com.winsafe.sap.pojo.CommonCodeLog;
import com.winsafe.sap.pojo.PrimaryCode;
import com.winsafe.sap.pojo.PrintJob;
import com.winsafe.sap.pojo.Seed;
import com.winsafe.sap.util.FileUploadUtil;

/*******************************************************************************************  
 * 类描述：  
 *  
 * @author: ryan.xi	  
 * @date：2017-10-10  
 * @version 1.0  
 *  
 *  
 * Version    Date       ModifiedBy                 Content  
 * -------- ---------    ----------         ------------------------  
 * 1.0      2017-10-10   ryan.xi                               
 *******************************************************************************************  
 */  
public class LabelGenerateService {
	private AppPrimaryCode appPrimaryCode = new AppPrimaryCode();
	private AppSeed appSeed = new AppSeed();
	private AppCartonCode appCartonCode = new AppCartonCode();
	private AppCommonCodeLog appCommonCodeLog = new AppCommonCodeLog();
	//种子范围
	private int seedRange = 10000;
	//种子长度
	private int seedLength = 4;
	//随机数最大值
	private long maxValue = 10000000000000l;
	//随机数步长
	private int step = 100000;
	//QR码前缀
	private String codePrefix = "";
	//追溯网址
//	private String traceUrl = "";
	//打印任务
	private PrintJob printJob;
	//产品信息
	private Product product;
	
	public LabelGenerateService(PrintJob printJob, Product product) {
		this.printJob = printJob;
		this.product = product;
	}

	private void init() throws Exception {
		//条码前缀:登记类别+农药登记证后6位+生产类型+产品规格码+内部生产类型
		codePrefix=product.getRegCertType()+product.getRegCertCodeFixed()+product.getProduceType()+product.getSpecCode()+product.getInnerProduceType();
//		Properties sysPro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
//		traceUrl = sysPro.getProperty("traceUrl")+"/";
	}

	/**
	 * 将条码信息更新到数据库中,同时写入文件
	 * @param primaryCodeList
	 * @param bw
	 * @param seed
	 * @param quantity
	 * @return
	 * @throws Exception
	 */
	private int addToDB(List<PrimaryCode> primaryCodeList, BufferedWriter bw, Seed seed, int quantity) throws Exception {
		appPrimaryCode.addPrimaryCodes(primaryCodeList);
		for(PrimaryCode pcode: primaryCodeList) {
			
			StringBuffer sb = new StringBuffer();
			//产品名称 登记证持有人信息 追溯网址 单元识别码
			/*sb.append(product.getStandardName()).append(" ");
			sb.append(product.getRegCertUser()).append(" ");
			sb.append(traceUrl).append(" ");*/
			sb.append(pcode.getPrimaryCode()).append(",");
			// 生产日期
			sb.append(printJob.getProductionDate()).append(",");
			// 封装日期，如果生产日期=分装日期，则分装日期为空
			if (printJob.getProductionDate().equals(
					printJob.getPackagingDate())) {
				sb.append(",");
			} else {
				sb.append(
						printJob.getPackagingDate() != null ? printJob
								.getPackagingDate() : "").append(",");
			}
			// 批次号
			sb.append(printJob.getBatchNumber()).append(",");
			// 箱码
			sb.append(StringUtil.removeNull(pcode.getCartonCode())).append(",");
			// 小包装唯一码
			sb.append(pcode.getPrimaryCode()).append("\r\n");
			//写入到文件中
			bw.write(sb.toString());
		}
		appSeed.updSeed(seed);
		quantity -= primaryCodeList.size(); 
		primaryCodeList.clear();
		HibernateUtil.commitTransaction();
		seed.setIsChanged(0);
		return quantity;
	}
	
	private void addFileHeader(BufferedWriter bw) throws Exception { 
		//任务号, 任务数量, 产线号, 产品名称, 产品规格, 农药标准名称, 批号, 生产日期, 分装日期
		StringBuffer header = new StringBuffer();
		header.append(printJob.getPrintJobId()).append(",");
		header.append(printJob.getNumberOfCases()).append(",");
		header.append(printJob.getProductionLine()).append(",");
		header.append(product.getProductname()).append(",");
		header.append(product.getSpecmode()).append(",");
		header.append(product.getStandardName()).append(",");
		header.append(printJob.getBatchNumber()).append(",");
		// 生产日期
		header.append(printJob.getProductionDate()).append(",");
		// 封装日期
		header.append(StringUtil.removeNull(printJob.getPackagingDate())).append(",");
		//箱到小包装比例
		AppFUnit appFunit = new AppFUnit();
		FUnit funit = appFunit.getFUnit(product.getId(), Constants.DEFAULT_UNIT_ID);
		if(funit != null) {
			header.append(funit.getXquantity().toString()).append("\r\n");
		} else {
			header.append(0).append("\r\n");
		}
		//写入到文件中
		bw.write(header.toString());
	}

	/**
	 * 获取码种子
	 * @param year
	 * @param seedType
	 * @param seedLength
	 * @param seedRange
	 * @return
	 */
	private Seed getSeed(String year, SeedType seedType,int seedLength, int seedRange) {
		Seed seed = appSeed.getNotUsedSeed(seedType, year);
		if(seed != null) {
			return seed;
		}
		seed = createNewSeed(seedRange, seedLength, year, seedType, -1);
		return seed;
	}
	
	/**
	 * 生成新种子
	 * @param seedRange
	 * @param seedLength
	 * @param year
	 * @param seedType
	 * @param seqNum
	 * @return
	 */
	private Seed createNewSeed(int seedRange, int seedLength, String year, SeedType seedType, int seqNum) {
		Set<String> seedSet = appSeed.getAllUsedSeedSet(year, seedType);
		Random random = new Random();
		int seedInt = random.nextInt(seedRange);
		while(seedSet.contains(getSeedStr(seedInt, seedLength))) {
			seedInt = random.nextInt(seedRange);
		}
		Seed seed = new Seed();
		seed.setCurrentValue(0l);
		seed.setIsUsed(0);
		seed.setYear(Integer.valueOf(year));
		seed.setMakeDate(Dateutil.getCurrentDate());
		seed.setSeed(getSeedStr(seedInt, seedLength));
		seed.setType(seedType.getValue());
		appSeed.addSeed(seed);
		return seed;
	}

	/**
	 * 将种子转换为固定长度的字符串
	 * @param seedInt
	 * @param seedlength
	 * @return
	 */
	private String getSeedStr(int seedInt, int seedlength) {
		String seedStr = String.valueOf(seedInt);
		seedStr = Constants.ZERO_PREFIX[seedlength-seedStr.length()] + seedStr;
		return seedStr;
	}
	
	/**
	 * 将种子拆开放入指定位置
	 * @param input
	 * @param seed
	 * @return
	 */
	private static String insertSeed(long input, String seed) {
		//随机数拼成13位
		String four = StringUtils.leftPad(String.valueOf(input), 13, "0");
		String part1 = four.substring(0, 2);
		String part2 = four.substring(2, 4);
		String part3 = four.substring(4, 6);
		String part4 = four.substring(6, 8);
		String part5 = four.substring(8, 10);
		String part6 = four.substring(10);

		return part1 + seed.charAt(0) + part2 + seed.charAt(1)+ part3 + seed.charAt(2)+ part4+ seed.charAt(3)+ part5+ seed.charAt(4)+ part6+ seed.charAt(5);
	}

	/**
	 * 生成QR码
	 * @param printJob
	 * @param boxQuantity
	 * @param seedType
	 * @throws Exception
	 */
	public void generatePrimaryCode(PrintJob printJob, int boxQuantity,
			SeedType seedType) throws Exception {
		init();
		//创建码文件
		String filePath = FileUploadUtil.getPrimayCodeFilePath()
			+ DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM")
			+ "/";
		String fileName = StringUtil.removeNull(printJob.getProductionLine())
			+ "_" + product.getProductnameen()+" "
			+ product.getPackSizeNameEn() + "_" + printJob.getBatchNumber()
			+ "." + printJob.getPrintJobId() + ".txt";
		printJob.setCodeFilePath(filePath + fileName);
		File codeFile = FileUploadUtil.createEmptyFile(filePath, fileName);
		if(LinkMode.After.getValue().equals(printJob.getLinkMode())) {//后关联
			Integer noOfCase = (int) (printJob.getNumberOfCases() * boxQuantity);
			generatePrimaryCodeWithAfterLinkMode(printJob, noOfCase, seedType, codeFile);
		} else {//前关联
			generatePrimaryCodeWithBeforeLinkMode(printJob, boxQuantity, seedType, codeFile);
		}
	} 
	
	/**
	 * 生成通用QR码
	 * @param printJob
	 * @param commonCodeLog
	 * @param commonCode
	 * @param seedType
	 * @throws Exception
	 */
	public void generateCommonCode(PrintJob printJob,
			CommonCodeLog commonCodeLog, String commonCode, SeedType seedType) throws Exception {
		//创建码文件
		init();
		int seq = appCommonCodeLog.getSeqForCommonCodeFile(commonCodeLog.getPrintJobId(), commonCodeLog.getId());
		String filePath = FileUploadUtil.getCommonCodeFilePath()
			+ DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM")
			+ "/";
		String fileName = "T_" + StringUtil.removeNull(printJob.getProductionLine()) + "_"
			+ product.getProductnameen() +" "+ product.getPackSizeNameEn()
			+ "_" + printJob.getBatchNumber() + "."
			+ printJob.getPrintJobId() + "."+seq+".txt";
		commonCodeLog.setFilePath(filePath + fileName);
		File codeFile = FileUploadUtil.createEmptyFile(filePath, fileName);
		generateCommonCode(printJob, commonCodeLog, commonCode, seedType, codeFile);
	}
	
	/**
	 * 生成通用QR码
	 * @param printJob
	 * @param commonCodeLog
	 * @param CommonCode
	 * @param seedType
	 * @param codeFile
	 * @throws Exception
	 */
	private void generateCommonCode(PrintJob printJob,
			CommonCodeLog commonCodeLog, String CommonCode,  SeedType seedType, File codeFile) throws Exception {
		int quantity = commonCodeLog.getCount();
		OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(codeFile), "UTF-8");
		BufferedWriter bw = new BufferedWriter(fw);
		String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(2, 4);
		Random random = new Random();
		List<PrimaryCode> primaryCodeList = new ArrayList<PrimaryCode>();
		try {
			addFileHeader(bw);
			Seed seed = getSeed(year,seedType,seedLength,seedRange);
			while(quantity > 0) {
				long currentValue = seed.getCurrentValue();
				long randomNum = currentValue + random.nextInt(step);
				//年份后两位+种子类型+13位随机码
				
				String primaryCodeStr = codePrefix+seedType.getValue()+insertSeed(randomNum, year+seed.getSeed());

				currentValue += step;
				seed.setCurrentValue(currentValue);
				//若该种子已用完则更新状态并重新获取种子
				if(currentValue >= maxValue)  {
					seed.setIsUsed(1);
					quantity = addToDB(primaryCodeList, bw, seed, quantity);
					seed = getSeed(year,seedType,seedLength,seedRange);
				}
			
				//添加到数据库中
				PrimaryCode primaryCode = new PrimaryCode();
				primaryCode.setPrimaryCode(primaryCodeStr);
				primaryCode.setCartonCode(CommonCode);
				primaryCode.setCommonCodeLogId(commonCodeLog.getId());
				primaryCode.setCodeLength(32);
				primaryCode.setPrintJobId(printJob.getPrintJobId());
				primaryCode.setIsUsed(YesOrNo.YES.getValue());
				primaryCodeList.add(primaryCode);
				if(primaryCodeList.size() >= quantity 
						|| primaryCodeList.size() == Constants.DB_BULK_SIZE) {
					quantity = addToDB(primaryCodeList, bw, seed, quantity);
				}
			}
		} finally {
			bw.close();
		}
		
	}

	/**
	 * 生成前关联的QR码
	 * @param printJob
	 * @param boxQuantity
	 * @param seedType
	 * @param codeFile
	 * @throws Exception
	 */
	private void generatePrimaryCodeWithBeforeLinkMode(PrintJob printJob,
			int boxQuantity, SeedType seedType, File codeFile) throws Exception {
		OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(codeFile), "UTF-8");
		BufferedWriter bw = new BufferedWriter(fw);
		String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(2, 4);
		Random random = new Random();
		List<PrimaryCode> primaryCodeList = new ArrayList<PrimaryCode>();
		List<CartonCode> cartonCodes = appCartonCode.getCartonCodeByPrintjobId(printJob.getPrintJobId());
		try {
			addFileHeader(bw);
			Seed seed = getSeed(year,seedType,seedLength,seedRange);
			for(CartonCode cc : cartonCodes) {
				int quantity = boxQuantity;
				while(quantity > 0) {
					long currentValue = seed.getCurrentValue();
					long randomNum = currentValue + random.nextInt(step);
					//年份后两位+种子类型+7位随机码
					
					String primaryCodeStr = codePrefix+seedType.getValue()+insertSeed(randomNum, year+seed.getSeed());

					currentValue += step;
					seed.setCurrentValue(currentValue);
					//若该种子已用完则更新状态并重新获取种子
					if(currentValue >= maxValue)  {
						seed.setIsUsed(1);
						quantity = addToDB(primaryCodeList, bw, seed, quantity);
						seed = getSeed(year,seedType,seedLength,seedRange);
					}
					
					//添加到数据库中
					PrimaryCode primaryCode = new PrimaryCode();
					primaryCode.setPrimaryCode(primaryCodeStr);
					primaryCode.setCodeLength(32);
					primaryCode.setPrintJobId(printJob.getPrintJobId());
					primaryCode.setIsUsed(YesOrNo.YES.getValue());
					primaryCode.setCartonCode(cc.getCartonCode());
					primaryCode.setPalletCode(cc.getPalletCode());
					primaryCodeList.add(primaryCode);
					if(primaryCodeList.size() >= quantity 
							|| primaryCodeList.size() == boxQuantity) {
						quantity = addToDB(primaryCodeList, bw, seed, quantity);
					}
				}
			}
		} finally {
			bw.close();
		}
	}


	/**
	 * 生成后关联的QR码
	 * @param printJob
	 * @param quantity
	 * @param seedType
	 * @param codeFile
	 * @throws Exception
	 */
	private void generatePrimaryCodeWithAfterLinkMode(PrintJob printJob,
			int quantity, SeedType seedType, File codeFile) throws Exception {
		OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(codeFile), "UTF-8");
		BufferedWriter bw = new BufferedWriter(fw);
		String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(2, 4);
		Random random = new Random();
		List<PrimaryCode> primaryCodeList = new ArrayList<PrimaryCode>();
		try {
			addFileHeader(bw);
			Seed seed = getSeed(year,seedType,seedLength,seedRange);
			while(quantity > 0) {
				long currentValue = seed.getCurrentValue();
				long randomNum = currentValue + random.nextInt(step);
				//种子类型+年份后两位+13位随机码
				String primaryCodeStr = codePrefix+seedType.getValue()+insertSeed(randomNum, year+seed.getSeed());

				currentValue += step;
				seed.setCurrentValue(currentValue);
				//若该种子已用完则更新状态并重新获取种子
				if(currentValue >= maxValue)  {
					seed.setIsUsed(1);
					quantity = addToDB(primaryCodeList, bw, seed, quantity);
					seed = getSeed(year,seedType,seedLength,seedRange);
				}
			
				//添加到数据库中
				PrimaryCode primaryCode = new PrimaryCode();
				primaryCode.setPrimaryCode(primaryCodeStr);
				primaryCode.setCodeLength(32);
				primaryCode.setPrintJobId(printJob.getPrintJobId());
				primaryCode.setIsUsed(YesOrNo.YES.getValue());
				primaryCodeList.add(primaryCode);
				if(primaryCodeList.size() >= quantity 
						|| primaryCodeList.size() == Constants.DB_BULK_SIZE) {
					quantity = addToDB(primaryCodeList, bw, seed, quantity);
				}
			}
		} finally {
			bw.close();
		}
		
	}

}
