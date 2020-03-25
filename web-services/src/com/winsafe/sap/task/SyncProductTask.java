package com.winsafe.sap.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject; 

import org.apache.log4j.Logger;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.FileUtil;
import com.winsafe.drp.util.HttpUtils; 
import com.winsafe.drp.util.PlantConfig;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.metadata.FileType;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.util.FileUploadUtil;

/*******************************************************************************************  
 * 定时将产品信息同步到杭州工厂
 * @author: ryan.xi	  
 * @date：2017-10-23  
 * @version 1.0  
 *  
 *  
 * Version    Date       ModifiedBy                 Content  
 * -------- ---------    ----------         ------------------------  
 * 1.0      2017-10-23   ryan.xi                 
 *******************************************************************************************  
 */  
public class SyncProductTask {

	private static Logger logger = Logger
			.getLogger(SyncProductTask.class);
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private AppProduct appProduct = new AppProduct();
	private AppFUnit appFUnit = new AppFUnit();
	//杭州工厂文件上传接口地址
	private String url = null;
	//上传文件保存路径
	private String filePath = null;
	
	private String[] productExcludes = new String[]{"modificationTime","productType","copys","inspectionInstitution"};

	/**
	 * 初始化要处理的任务 
	 * 
	 * @throws IOException
	 */
	public void init() throws IOException {
		url = PlantConfig.getConfig().getProperty("HZPlantUrl");
		filePath = PlantConfig.getConfig().getProperty("productFilePath");
	}
	/**
	 * 定时任务入口方法
	 */
	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					logger.debug("start genernate primary code.");
					logger.info(DateUtil.getCurrentDate() + " 产品数据同步任务---开始---");
					this.init();
					isRunning = true;
					startSync();
				} catch (Exception e) {
					logger.info(DateUtil.getCurrentDate() + " 产品数据同步任务发生异常:"
							+ e.getMessage());
					logger.error(e);
				} finally {
					HibernateUtil.closeSession();
					isRunning = false;
					logger.info(DateUtil.getCurrentDate() + " 产品数据同步任务---结束---");
				}
			}
		}

	}
	/**
	 * 开始上传文件
	 * @throws Exception
	 */
	private void startSync() throws Exception {
		String dateString = DateUtil.getCurrentDateTimeString();
		Map<String, FUnit> unitMap = appFUnit.getAllMap();
		String fileName = "product_"+dateString+".txt";
		//获取产品信息生成文件
		List<Product> productList = appProduct.getProductByUseFlagAndPrimaryPrintFlag(YesOrNo.YES.getValue(),YesOrNo.YES.getValue());
		OutputStreamWriter fw = null;
		BufferedWriter bw = null;
		File file = null;
		try {
			file = FileUploadUtil.createEmptyFile(filePath, fileName);
			fw = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
			bw = new BufferedWriter(fw);
			for(Product product : productList) {
				FUnit funit = unitMap.get(product.getId()+"_"+Constants.DEFAULT_UNIT_ID);
				if(funit == null) {
					continue;
				}
				product.setProductcodedef(product.getIsidcode()!=null?product.getIsidcode().toString():null);
				product.setPackingRatio(funit.getXquantity().intValue());
				JSONObject object = JSONObject.fromObject(product, productExcludes);
				bw.write(object.toString());
				bw.write("\r\n");
			}
			bw.flush();
		} catch (Exception e) {
			logger.error("failed to create common Code File",e);
			return;
		} finally {
			if (bw != null) {
				bw.close();
			}
			if (fw != null) {
				fw.close();
			}
		}
		
		//压缩文件
		String zipFileName = "product_"+dateString+".zip";
		File zipfile = new File(filePath+zipFileName);  
		FileUtil.zipFiles(new File[]{file}, zipfile);
		//同步到杭州工厂服务器
		HttpUtils.upload(url+"?fileType="+FileType.PRODUCT.getValue(), zipfile);
		//删除临时文件
		/*zipfile.delete();
		file.delete();*/
	}
	
	public static void main(String[] args) throws Exception {
		new SyncProductTask().run();
	}
}
