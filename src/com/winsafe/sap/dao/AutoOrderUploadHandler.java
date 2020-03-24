package com.winsafe.sap.dao;



import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;

import com.winsafe.sap.metadata.SapFileType;
import com.winsafe.sap.pojo.PrintJob;

public class AutoOrderUploadHandler extends SapOrderUploadHandler{
	
	private static Logger logger = Logger.getLogger(AutoOrderUploadHandler.class);

	public AutoOrderUploadHandler(SapFileType fileType) throws Exception {
		super(fileType);
	}

	public void addRule(Digester digester) {
		digester.setValidating(false);  
		// 指明匹配模式和要创建的类   
//		digester.addObjectCreate("ns0:BAY0_ED_WMTOID02", PlantTransferOrder.class);  
		digester.addObjectCreate("ns1:ProcessOrderIDOC/ns0:BAY0_ED_WMTOID02/data", PrintJob.class);  
		digester.addBeanPropertySetter("ns1:ProcessOrderIDOC/ns0:BAY0_ED_WMTOID02/data/TANUM", "transOrder");  
		digester.addBeanPropertySetter("ns1:ProcessOrderIDOC/ns0:BAY0_ED_WMTOID02/data/STDATE", "transOrderDate");  
//		digester.addBeanPropertySetter("ns1:ProcessOrderIDOC/ns0:BAY0_ED_WMTOID02/data/TAPOS", "transOrderItem");  
		digester.addBeanPropertySetter("ns1:ProcessOrderIDOC/ns0:BAY0_ED_WMTOID02/data/BENUM", "processOrderNumber");  
		digester.addBeanPropertySetter("ns1:ProcessOrderIDOC/ns0:BAY0_ED_WMTOID02/data/WERKS", "plantCode");  
		digester.addBeanPropertySetter("ns1:ProcessOrderIDOC/ns0:BAY0_ED_WMTOID02/data/NLENR", "palletSSCC");  
		digester.addBeanPropertySetter("ns1:ProcessOrderIDOC/ns0:BAY0_ED_WMTOID02/data/EXIDV", "cartonSSCC");  
		digester.addBeanPropertySetter("ns1:ProcessOrderIDOC/ns0:BAY0_ED_WMTOID02/data/MATNR", "materialCode");
		digester.addBeanPropertySetter("ns1:ProcessOrderIDOC/ns0:BAY0_ED_WMTOID02/data/CHARG", "batchNumber");
		digester.addBeanPropertySetter("ns1:ProcessOrderIDOC/ns0:BAY0_ED_WMTOID02/data/VEMNG", "shipperQuantity");
		digester.addBeanPropertySetter("ns1:ProcessOrderIDOC/ns0:BAY0_ED_WMTOID02/data/HSDAT", "productionDate");
		digester.addBeanPropertySetter("ns1:ProcessOrderIDOC/ns0:BAY0_ED_WMTOID02/data/VFDAT", "expiryDate");
		digester.addBeanPropertySetter("ns1:ProcessOrderIDOC/ns0:BAY0_ED_WMTOID02/data/FSDAT", "packagingDate");
		digester.addBeanPropertySetter("ns1:ProcessOrderIDOC/ns0:BAY0_ED_WMTOID02/data/EAN11", "GTINNumber");
		digester.addSetNext("ns1:ProcessOrderIDOC/ns0:BAY0_ED_WMTOID02/data", "addPrintJob");  
		
	}

}
