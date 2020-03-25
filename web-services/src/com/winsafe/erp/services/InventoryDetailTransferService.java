package com.winsafe.erp.services;

import java.util.Arrays;
import java.util.List; 
import java.util.Map;

import org.owasp.esapi.ESAPI;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.drp.util.sftp.SFTPServices;
import com.winsafe.erp.metadata.FileType;
import com.winsafe.erp.metadata.TransferFileStatus;
import com.winsafe.erp.pojo.TransferLog;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.StringUtil;

public class InventoryDetailTransferService extends FileTransferService {
	
	private AppFUnit appFUnit = new AppFUnit();
	/**
	 * 初始化数据
	 */
	public void init() {
		title="TD编码,TD名称,仓库编码,仓库名称,产品编码,产品名称,产品规格,产品批号,生产日期,失效日期,数量（kg/L）,数量（箱）";
		columnNames = Arrays.asList("organid","organname","warehouseid","warehousename","mcode","productname","specmode","batch","productiondate","expirydate","quantity","count");
	}

	public void createTransferFile() throws Exception {
		init();
		WfLogger.debug("获取库存信息");
		List<Map<String,String>> dataList = appTl.getInventoryDetailToTransfer();
		if(dataList != null && dataList.size() > 0) {
			Map<String,FUnit>  funitMap = appFUnit.getAllMap(); //获取所有的单位换算信息
			for(Map<String,String> map : dataList) {
				Double stockpile = Double.valueOf(map.get("stockpile"));
				Double boxQuantity = Double.valueOf(map.get("boxquantity"));
				Integer unitId = Integer.valueOf(map.get("countunit"));
				map.put("count", changeUnit(map.get("productid"), unitId, stockpile, Constants.DEFAULT_UNIT_ID, funitMap).toString());
				map.put("quantity", String.valueOf(ArithDouble.mul(stockpile, boxQuantity)));
				map.put("organname", ESAPI.encoder().decodeForHTML(map.get("organname")));
				map.put("warehousename", ESAPI.encoder().decodeForHTML(map.get("warehousename")));
				map.put("productname", ESAPI.encoder().decodeForHTML(map.get("productname")));
			}
		}
		WfLogger.debug("新增日志");
		TransferLog log = createFileAndAddLog(dataList, FileType.INVENTORY_DETAIL);
		if(log != null) {
			SFTPServices sftpServices = new SFTPServices();
			try {
				transferFile(sftpServices, log);
			} finally {
				sftpServices.close();
			}
		}
		
	}
	
	private void transferFile(SFTPServices sftpServices, TransferLog log) {
		WfLogger.debug("开始传输日志编号为"+log.getId()+"的文件");
		if(sftpServices.uploadFile(log.getFilePath(), log.getFileName())) {
			log.setStatus(TransferFileStatus.TRANSFERED.getValue());
			addTransferLog(log);
		} else {
			log.setStatus(TransferFileStatus.TRANSFER_ERROR.getValue());
			addTransferLog(log);
		}
		WfLogger.debug("结束传输日志编号为"+log.getId()+"的文件");
	}
	
	private void addTransferLog(TransferLog log) {
		try {
			appTl.addTransferLog(log);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			WfLogger.error("更新传输日志发生异常", e); 
		}
	}
	
	/**
	 * 根据单位转化数量
	 * Create Time 2015-1-30 下午02:07:55 
	 * @param productId
	 * @param srcUnit
	 * @param srcQuantity
	 * @param desUnit
	 * @param rateMap
	 * @return
	 * @author lipeng
	 */
	private Double changeUnit(String productId,Integer srcUnit,Double srcQuantity,Integer desUnit,Map<String,FUnit> rateMap){
		// 先换算出最小单位数量
		String srcKey = productId + "_" + srcUnit;
		Double srcRate = rateMap.get(srcKey).getXquantity();
		Double minUnitQuantity = ArithDouble.mul(srcQuantity, srcRate);
		// 再换算成目标单位数量
		String desKey = productId + "_" + desUnit;
		Double desRate = rateMap.get(desKey).getXquantity();
		Double desQuantity = ArithDouble.div(minUnitQuantity, desRate);
		return desQuantity;
	}
	
	public static void main(String[] args) {
		System.out.println(ESAPI.encoder().decodeForHTML(""));
		System.out.println(ESAPI.encoder().decodeForHTML(StringUtil.removeNull(null)));
	}
}
