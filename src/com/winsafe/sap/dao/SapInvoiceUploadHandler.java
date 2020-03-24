package com.winsafe.sap.dao;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;

import com.winsafe.drp.dao.AppSalesConsumHistory;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.report.pojo.SalesConsumHistory;
import com.winsafe.drp.server.SalesConsumeInventoryReportService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.drp.util.UploadErrorMsg;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.metadata.SapFileErrorType;
import com.winsafe.sap.metadata.SapFileType;
import com.winsafe.sap.pojo.Invoice;
/*******************************************************************************************  
 * SAP发票文件处理类
 * @author: ryan.xi	  
 * @date：2014-12-17  
 * @version 1.0  
 *  
 *  
 * Version    Date       ModifiedBy                 Content  
 * -------- ---------    ----------         ------------------------  
 * 1.0      2014-12-17   ryan.xi  
 * 1.1      2015-10-10   ryan.xi            添加对销售消耗历史的修改      
 * 1.2      2016-04-01   ryan.xi            修复报错问题
 *******************************************************************************************  
 */ 
public class SapInvoiceUploadHandler extends SapUploadHandler{
	
	private static Logger logger = Logger.getLogger(SapInvoiceUploadHandler.class);
	
	private List<Invoice> invoices = new ArrayList<Invoice>();
	
	private Set<String> notExistsDelivery = new HashSet<String>();
	private Map<String,StockAlterMove> existsDelivery = new HashMap<String,StockAlterMove>();
	
	private AppInvoice appInvoice = new AppInvoice();
	private AppStockAlterMove appStockAlterMove = new AppStockAlterMove();
	private SalesConsumeInventoryReportService scirs = new SalesConsumeInventoryReportService();
	private AppSalesConsumHistory appSalesConsumHistory = new AppSalesConsumHistory();
	
	public SapInvoiceUploadHandler(SapFileType fileType) throws Exception {
		super(fileType);
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	/**
	 * 添加发票信息
	 * @param invoice
	 */
	public void addInvoice(Invoice invoice) {
		doValidate(invoice);
		if(!hasError) {
			invoices.add(invoice);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.winsafe.sap.dao.SapUploadHandler#handle()
	 */
	@Override
	public void handle() throws Exception {
		logger.debug("start add sap uploaded invoice.");
		//tommy aa  物料号取后8位，批号10位，不足补0,客户号前面去掉0
		for(Invoice invoice :invoices) {
			if (null!=invoice.getMaterialCode()) {
				if (invoice.getMaterialCode().length()>8) {
					invoice.setMaterialCode(invoice.getMaterialCode().substring(invoice.getMaterialCode().length()-8));
				}
			}
			if (null!=invoice.getBatchNumber()) {
				if (invoice.getBatchNumber().length()<10) {
					invoice.setBatchNumber(Constants.ZERO_PREFIX[10 - invoice.getBatchNumber().length()] + invoice.getBatchNumber());
				}
			}
			if (null!=invoice.getPartnShip()) {
				if (invoice.getPartnShip().startsWith("0")) {
					invoice.setPartnShip(invoice.getPartnShip().replaceFirst("^0*", ""));
				}
			}
			if (null!=invoice.getPartnSold()) {
				if (invoice.getPartnSold().startsWith("0")) {
					invoice.setPartnSold(invoice.getPartnSold().replaceFirst("^0*", ""));
				}
			}
		}	
		//获取已统计的销售消耗历史日期
		BaseResource salesConsumDate = appBaseResource.getBaseResourceValueWithLock("SalesConsumDate", 1);
		Set<String> invoiceNumbers  = new HashSet<String>();
		appInvoice.addInvoices(invoices);
		if(salesConsumDate != null) {
			for(Invoice invoice : invoices) {
				if(!invoiceNumbers.contains(invoice.getInvoiceNumber())) {
					//获取单据
					StockAlterMove sam = existsDelivery.get(invoice.getDeliveryNumber());
					//若无收货仓库
					if(StringUtil.isEmpty(sam.getInwarehouseid())) {
						continue;
					}
					//获取发票对应的机构,仓库,产品,发货日期信息
					List<Map<String, String>> salesDatas = appSalesConsumHistory.getByBillNoAndInvoiceNo(sam.getId(), invoice.getInvoiceNumber());
					for(Map<String, String> salesData : salesDatas) {
						//获取销售消耗历史记录
						SalesConsumHistory sch = appSalesConsumHistory.getSalesConsumHistory(salesData.get("year"),salesData.get("month"),salesData.get("organid"),salesData.get("warehouseid"),salesData.get("productid"));
						if(sch != null) {//若存在,则更新
							//更新销售金额
							sch.setSalesValue(sch.getSalesValue() + getDoubleValue(salesData.get("salesvalue")));
							//更新价格
							sch.setPrice(getPrice(salesData.get("year")+salesData.get("month"), sch));
							appSalesConsumHistory.updSalesConsumHistory(sch);
						} else { //若不存在,则新增
							addSalesConsumHistory(salesData);
						}
						//查看该发票之后有没有统计好的价格
						List<SalesConsumHistory> schs = appSalesConsumHistory.getNewerSalesConsumHistory(salesData.get("year"),salesData.get("month"),salesData.get("organid"),salesData.get("warehouseid"),salesData.get("productid"));
						if(schs != null && schs.size() > 0) {
							for(SalesConsumHistory newerSch : schs) {
							    String yearMonth = newerSch.getYear() + (newerSch.getMonth() < 10 ? "0" : "") + newerSch.getMonth();
								newerSch.setPrice(getPrice(yearMonth, newerSch));
								appSalesConsumHistory.updSalesConsumHistory(newerSch);
							}
						}
					}
					invoiceNumbers.add(invoice.getInvoiceNumber());
				}
			}
		}
		
	}
	
	/**
	 * 字符串转为Double类型
	 * @param strValue
	 * @return
	 */
	private double getDoubleValue(String strValue) {
		if(strValue == null) {
			return 0d;
		}
		if(StringUtil.isEmpty(StringUtil.removeNull(strValue))) {
			return 0d;
		}
		return Double.parseDouble(strValue);
	}

	/**
	 * 获取价格
	 * @param yearMonth
	 * @param sch
	 * @return
	 * @throws Exception
	 */
	private Double getPrice(String yearMonth, SalesConsumHistory sch) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.StringToDate(yearMonth, "yyyyMM"));
		return scirs.getPrice(calendar, sch.getProductId(), sch.getOrganId());
	}
	
	/**
	 * 新增销售消耗历史
	 * @param salesData
	 * @throws Exception
	 */
	private void addSalesConsumHistory(Map<String, String> salesData) throws Exception {
		SalesConsumHistory sch = new SalesConsumHistory();
		MapUtil.mapToObject(salesData, sch);
		sch.setConsumValue(0d);
		sch.setConsumVolume(0d);
		sch.setHasInvoice(0);
		sch.setMakeDate(DateUtil.getCurrentDate());
		sch.setMonthBeginInventory(0d);
		sch.setMonthEndInventory(0d);
		sch.setOtherConsumVolume(0d);
		sch.setSalesValue(0d);
		sch.setSalesVolume(0d);
		//更新价格
		sch.setPrice(getPrice(salesData.get("year")+salesData.get("month"), sch));
		appSalesConsumHistory.addSalesConsumHistory(sch);
	}

	/* (non-Javadoc)
	 * @see com.winsafe.sap.dao.SapUploadHandler#checkResult()
	 */
	@Override
	public void checkResult() {
		if(invoices.size() == 0 && !hasError) {
			hasError = true;
			errMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00211));
		}
	}

	/**
	 * 验证发票文件信息完整性
	 * @param dlvHeader
	 * @return
	 */
	protected boolean doValidate(Invoice dlvHeader) {
		sapUploadLog.setBillNo(dlvHeader.getDeliveryNumber());
		if(!isStockMoveExists(dlvHeader.getDeliveryNumber())) {
			sapUploadLog.setErrorType(SapFileErrorType.DELIVERY_NOT_EXISTS.getDbValue());
			errMsg.append("发票对应的单据["+dlvHeader.getDeliveryNumber()+"]不存在").append("\r\n");
			invoices.clear();
			hasError = true;
		}
		return hasError;
	}
	
	/**
	 * 检查发票对应的发货单是否已存在
	 * @param deliveryNumber
	 * @return
	 */
	private boolean isStockMoveExists(String deliveryNumber) {
		try {
			if(notExistsDelivery.contains(deliveryNumber)) {
				return false;
			}
			if(!existsDelivery.containsKey(deliveryNumber)) {
				StockAlterMove sam = appStockAlterMove.getStockAlterMoveByNCcode(deliveryNumber);
				if(sam == null) {
					notExistsDelivery.add(deliveryNumber);
					return false;
				} else {
					existsDelivery.put(deliveryNumber, sam);
				}
			} 		
		} catch (Exception e) {
			logger.error("获取发票对应的单据时发生异常",e);
		}
		return true;
	}
	

	/* (non-Javadoc)
	 * @see com.winsafe.sap.dao.SapUploadHandler#addRule(org.apache.commons.digester.Digester)
	 */
	public void addRule(Digester digester) {
		digester.setValidating(false);  
		// 指明匹配模式和要创建的类   
		digester.addObjectCreate("ns1:InvoiceIDOC/data/INVOICE_H", Invoice.class);  
		digester.addBeanPropertySetter("ns1:InvoiceIDOC/data/INVOICE_H/VBELN", "invoiceNumber");  
		digester.addBeanPropertySetter("ns1:InvoiceIDOC/data/INVOICE_H/FKTYP", "invoiceType");  
		digester.addBeanPropertySetter("ns1:InvoiceIDOC/data/INVOICE_H/FKDAT", "invoiceDate");  
		digester.addBeanPropertySetter("ns1:InvoiceIDOC/data/INVOICE_H/VGBEL", "deliveryNumber");  
		digester.addBeanPropertySetter("ns1:InvoiceIDOC/data/INVOICE_H/PARTN_SOLD", "partnSold");  
		digester.addBeanPropertySetter("ns1:InvoiceIDOC/data/INVOICE_H/PARTN_SHIP", "partnShip");  
		digester.addBeanPropertySetter("ns1:InvoiceIDOC/data/INVOICE_H/POSNR", "invoiceLineItem");  
		digester.addBeanPropertySetter("ns1:InvoiceIDOC/data/INVOICE_H/MATNR", "materialCode");
		digester.addBeanPropertySetter("ns1:InvoiceIDOC/data/INVOICE_H/CHARG", "batchNumber");
		digester.addBeanPropertySetter("ns1:InvoiceIDOC/data/INVOICE_H/FKIMG", "invoiceQty");
		digester.addBeanPropertySetter("ns1:InvoiceIDOC/data/INVOICE_H/NETWR", "netVal");
		
		digester.addSetNext("ns1:InvoiceIDOC/data/INVOICE_H", "addInvoice");
	}

}
