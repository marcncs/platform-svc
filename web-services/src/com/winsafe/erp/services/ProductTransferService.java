package com.winsafe.erp.services;

import java.util.Arrays;
import java.util.List; 
import java.util.Map;

import com.winsafe.drp.dao.AppProduct;
import com.winsafe.erp.metadata.FileType;
import com.winsafe.hbm.entity.HibernateUtil;

public class ProductTransferService extends FileTransferService {
	
	private AppProduct appProduct = new AppProduct();
	private String TAG_NAME = "productTime";
	
	/**
	 * 初始化数据
	 */
	public void init() {
		title="产品编号,产品类别中文,产品类别英文,物料号,物料中文描述,物料英文描述,产品中文名称,产品英文名称,"
				+ "包装规格中文,包装规格英文,包装规格明细,保质期,是否可用,计量单位,最小包装计量单位转换率,最小包装数量";
		columnNames = Arrays.asList("id","sortname","sortnameen","mcode","matericalchdes","matericalendes","productname","productnameen","packsizename","packsizenameen","speccode","expirydays","useflag","countunit","boxquantity","xquantity");
	}

	public void createTransferFile() throws Exception {
		init();
		String startTime = getStartTime(TAG_NAME);
		String endTime = getEndTime();
		List<Map<String,String>> dataList = appProduct.getProductsToTransfer(startTime, endTime);
		createFileAndAddLog(dataList, FileType.PRODUCT, endTime, TAG_NAME);
	}
	
	public static void main(String[] args) throws Exception {
		new ProductTransferService().createTransferFile();
		HibernateUtil.commitTransaction();
	}
	
}
