package com.winsafe.sap.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set; 

import org.apache.commons.lang.StringUtils;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.FileUtil;
import com.winsafe.drp.util.PlantConfig;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppPrimaryCode;
import com.winsafe.sap.dao.AppSeed;
import com.winsafe.sap.metadata.SeedType;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.pojo.ApplyQrCodeHz;
import com.winsafe.sap.pojo.PrimaryCode;
import com.winsafe.sap.pojo.Seed;
import com.winsafe.sap.util.FileUploadUtil;

import net.sf.json.JSONObject;

/*******************************************************************************************  
 * 类描述：  
 *  
 * @author: ryan.xi	  
 * @date：2019-08-04  
 * @version 1.0  
 *  
 *  
 * Version    Date       ModifiedBy                 Content  
 * -------- ---------    ----------         ------------------------  
 * 1.0      2019-08-04   ryan.xi                                
 *******************************************************************************************  
 */  
public class HzLabelGenerateService {
	private AppPrimaryCode appPrimaryCode = new AppPrimaryCode();
	private AppSeed appSeed = new AppSeed();
	private AppFUnit appFunit = new AppFUnit();
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
	private String traceUrl = "";
	//日期
	private Date date = null;
	//产品信息
	private AppProduct appProduct = new AppProduct();
	
	private String[] primaryCodeExcludes = new String[]{"seq"};

	private void init(Product product) throws Exception {
		//追溯网址
		traceUrl = PlantConfig.getConfig().getProperty("traceUrl")+"/";
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
	 * @param syncBw 
	 * @return
	 * @throws Exception
	 */
	private int addToDB(List<PrimaryCode> primaryCodeList, BufferedWriter bw, Seed seed, int quantity, BufferedWriter syncBw) throws Exception {
		appPrimaryCode.addPrimaryCodes(primaryCodeList);
		for(PrimaryCode pcode: primaryCodeList) {
			
			StringBuffer sb = new StringBuffer();
			//二维码地址，最小码的前10位，最小码的后11位，21位最小码
			sb.append(traceUrl).append(pcode.getPrimaryCode()).append(",").
				append(pcode.getPrimaryCode().substring(11,21)).append(",").
				append(pcode.getPrimaryCode().substring(21,32)).append(",").
				append(pcode.getPrimaryCode().substring(11,32)).append("\r\n");
			
			bw.write(sb.toString());
			
			//写入同步文件
			JSONObject object = JSONObject.fromBean(pcode, primaryCodeExcludes);
			object.put("createDate", date);
			syncBw.write(object.toString());
			syncBw.write("\r\n");
		}
		appSeed.updSeed(seed);
		quantity -= primaryCodeList.size(); 
		primaryCodeList.clear();
		HibernateUtil.commitTransaction();
		seed.setIsChanged(0);
		return quantity;
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
	public void generateQrCode(ApplyQrCodeHz aqc,
			SeedType seedType) throws Exception {
		//产品信息
		Product product = appProduct.getProductByID(aqc.getProductId());
		//初始化相关信息
		init(product);
		//创建码文件
		File codeFile = createCodeFile(product, aqc);
		//创建需要同步给杭州工厂的同步文件
		File syncFile = createSyncFile(aqc);
		//包装比例
//		double packingRatio = appFunit.getXQuantity(product.getId(), Constants.DEFAULT_UNIT_ID);
		//小包装总数量
		int totalQuantity = Double.valueOf((aqc.getQuantity()+aqc.getQuantity()*aqc.getRedundance()/100)).intValue();
		//生成码
		generatePrimaryCode(aqc, totalQuantity, seedType, codeFile, syncFile);
		//压缩需要同步给杭州工厂的同步文件
		creatSyncZipFile(aqc, syncFile);
	} 
	
	private void creatSyncZipFile(ApplyQrCodeHz aqc, File syncFile) throws Exception {
		String filePath = PlantConfig.getConfig().getProperty("printJobFilePath");
		String zipFileName = "applyQrCodeHz_"+aqc.getId().toString()+".zip";
		File zipfile = new File(filePath+zipFileName);  
		FileUtil.zipFiles(new File[]{syncFile}, zipfile);
		//更新文件路径
		aqc.setSyncFilePath(filePath+zipFileName);
	}

	private File createSyncFile(ApplyQrCodeHz aqc) throws Exception {
		String filePath = PlantConfig.getConfig().getProperty("printJobFilePath");
		String fileName = "ApplyQrCodeHz_"+aqc.getId()+".txt";
		File file = FileUploadUtil.createEmptyFile(filePath, fileName);
		return file;
	}

	private File createCodeFile(Product product, ApplyQrCodeHz aqc) throws Exception {
		String processOrder = "";
		if(aqc!=null && !StringUtil.isEmpty(aqc.getPono())) {
			processOrder = aqc.getPono();
		}
		String filePath = FileUploadUtil.getPrimayCodeFilePath()
			+ DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM")
			+ "/";
		String fileName = aqc.getId().toString()
			+ "_" + product.getProductname()+" "
			+ product.getSpecmode()+"_"
			+ "_" + product.getProductnameen()+" "
			+ product.getPackSizeNameEn()+"-"+ processOrder +".txt";
		aqc.setFilePath(filePath + fileName);
		return FileUploadUtil.createEmptyFile(filePath, fileName);
	}


	/**
	 * 生成后关联的QR码
	 * @param printJob
	 * @param quantity
	 * @param seedType
	 * @param codeFile
	 * @param syncFile 
	 * @throws Exception
	 */
	private void generatePrimaryCode(ApplyQrCodeHz aqc,
			int quantity, SeedType seedType, File codeFile, File syncFile) throws Exception {
		Date date = Dateutil.getCurrentDate();
		String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(2, 4);
		Random random = new Random();
		List<PrimaryCode> primaryCodeList = new ArrayList<PrimaryCode>();
		try (OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(codeFile), "UTF-8");
				BufferedWriter bw = new BufferedWriter(fw);	
				OutputStreamWriter syncfw = new OutputStreamWriter(new FileOutputStream(syncFile), "UTF-8");
				BufferedWriter syncBw = new BufferedWriter(syncfw);	){
//			addFileHeader(bw);
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
					quantity = addToDB(primaryCodeList, bw, seed, quantity, syncBw);
					seed = getSeed(year,seedType,seedLength,seedRange);
				}
			
				//添加到数据库中
				PrimaryCode primaryCode = new PrimaryCode();
				primaryCode.setPrimaryCode(primaryCodeStr);
				primaryCode.setCodeLength(32);
				primaryCode.setUploadPrId(aqc.getId().toString());
				primaryCode.setIsUsed(YesOrNo.NO.getValue());
				primaryCode.setCreateDate(date);
				primaryCodeList.add(primaryCode);
				if(primaryCodeList.size() >= quantity 
						|| primaryCodeList.size() == Constants.DB_BULK_SIZE) {
					quantity = addToDB(primaryCodeList, bw, seed, quantity, syncBw);
				}
			}
		} 
		
	}
}
