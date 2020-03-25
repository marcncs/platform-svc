package com.winsafe.sap.dao;

import org.apache.commons.digester.Digester;

import com.winsafe.drp.util.UploadErrorMsg;
import com.winsafe.sap.metadata.CartonSeqStatus;
import com.winsafe.sap.metadata.SapFileType;
import com.winsafe.sap.pojo.PrintJob;

/*******************************************************************************************  
 * 手工上传的杭州工厂生产计划处理类
 * @author: ryan.xi	  
 * @date：2014-12-17  
 * @version 1.0  
 *  
 *  
 * Version    Date       ModifiedBy                 Content  
 * -------- ---------    ----------         ------------------------  
 * 1.0      2014-12-17   ryan.xi                
 *******************************************************************************************  
 */ 
public class ManualPlantUploadHandler extends SapOrderUploadHandler{
	//是否是首行
	private boolean isHeader = true;
	//工厂代码
	private String plantCode;
	//工厂名称
	private String plantName;
	
	public ManualPlantUploadHandler(SapFileType fileType) throws Exception {
		super(fileType);
	}
	
	/* (non-Javadoc)
	 * @see com.winsafe.sap.dao.SapOrderUploadHandler#addPrintJob(com.winsafe.sap.pojo.PrintJob)
	 */
	@Override
	public void addPrintJob(PrintJob printJob) throws Exception {
		//检查文档格式是否正确
		if(isFileTypeCorrect) {
			//杭州工厂xml文档第一行为表头数据，通过表头检查上传文件类型是否选对
			if(isHeader) {
				if(!(printJob.getProcessOrderNumber() != null && printJob.getPlantIndex() != 9)) {
					isFileTypeCorrect = false;
					hasError = true;
					errMsg.append(UploadErrorMsg.getError(UploadErrorMsg.E00210, "杭州工厂","9")).append("\r\n");
				}
				isHeader = false;
				String[] codeAndNames = sapUploadLog.getFilePath().split("_");
				plantCode = codeAndNames[codeAndNames.length - 3];
				plantName = codeAndNames[codeAndNames.length - 2];
				return;
			}
			printJob.setPlantCode(plantCode);
			printJob.setPlantName(plantName);
			printJob.setCartonSeqStatus(CartonSeqStatus.NO_DEED.getValue());
			super.addPrintJob(printJob);
		} 
	}
	
	/* (non-Javadoc)
	 * @see com.winsafe.sap.dao.SapOrderUploadHandler#addRule(org.apache.commons.digester.Digester)
	 */
	@Override
	public void addRule(Digester digester) {
		digester.setValidating(false);  
		// 指明匹配模式和要创建的类   
		digester.addObjectCreate("Workbook/Worksheet/Table/Row", PrintJob.class);  
		digester.addCallMethod( "Workbook/Worksheet/Table/Row/Cell/Data", "setPlantProperty", 0 );
		digester.addSetNext("Workbook/Worksheet/Table/Row", "addPrintJob");  
	}
}
