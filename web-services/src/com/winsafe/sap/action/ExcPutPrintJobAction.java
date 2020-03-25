package com.winsafe.sap.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.pojo.CartonCode;
import com.winsafe.sap.pojo.PrintJob;

public class ExcPutPrintJobAction extends BaseAction {
	
	private AppCartonCode appCartonCode = new AppCartonCode();
	private AppPrintJob appPrintJob = new AppPrintJob();
	private AppProduct appProduct = new AppProduct();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		Integer printJobId = Integer.parseInt(request.getParameter("printJobId"));
		
		List<CartonCode> cartonCodes = appCartonCode.getCartonCodeForExcPut(printJobId);
		PrintJob printJob = appPrintJob.getPrintJobByID(printJobId);
		Product product = appProduct.getProductByID(printJob.getProductId());
		
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setHeader("content-disposition",
				"attachment; filename=ListPrintJob.xls");
		response.setContentType("application/msexcel");
		writeXls(cartonCodes,printJob,product, os, request);
		os.flush();
		os.close();
		DBUserLog.addUserLog(userid,"标签打印","标签打印>>打印任务 导出编号为"+printJobId+"的打印任务");
		
		return null;
	}

	private void writeXls(List<CartonCode> cartonCodes, PrintJob printJob,
			Product product, OutputStream os, HttpServletRequest request) throws IOException, RowsExceededException, WriteException {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		int snum = 1;
		snum = cartonCodes.size() / 40000;
		if (cartonCodes.size() % 40000 >= 0) {
			snum++;
		}
		
		WritableSheet[] sheets = new WritableSheet[snum];
		
		for (int j = 0; j < snum; j++) {
			sheets[j] = workbook.createSheet("sheet" + j, j);
			int currentnum = (j + 1) * 40000;
			if (currentnum >= cartonCodes.size()) {
				currentnum = cartonCodes.size();
			}
			int start = j * 40000;
			          
			sheets[j].addCell(new Label(0, start, "",wchT));
			sheets[j].addCell(new Label(1, start, "OutermPIN",wchT));
			sheets[j].addCell(new Label(2, start, "InnermPIN",wchT));
			sheets[j].addCell(new Label(3, start, "SSCCCode",wchT));
			sheets[j].addCell(new Label(4, start, "PalletCode",wchT));
			sheets[j].addCell(new Label(5, start, "Product",wchT));
			sheets[j].addCell(new Label(6, start, "Pack",wchT));
			sheets[j].addCell(new Label(7, start, "GTIN",wchT));
			sheets[j].addCell(new Label(8, start, "ManufacturingDate",wchT));
			sheets[j].addCell(new Label(9, start, "DateOfExpiry",wchT));
			sheets[j].addCell(new Label(10, start, "LabelSequence",wchT));
			sheets[j].addCell(new Label(11, start, "MaterialDescription",wchT));
			sheets[j].addCell(new Label(12, start, "MaterialCode",wchT));
			sheets[j].addCell(new Label(13, start, "ProcessOrder",wchT));
			sheets[j].addCell(new Label(14, start, "printingDate",wchT));
			sheets[j].addCell(new Label(15, start, "FillingDate",wchT));
			sheets[j].addCell(new Label(16, start, "Description",wchT));
			int row = 0;
			for (int i = start; i < currentnum; i++) {
				row = i + 1;
				CartonCode cartonCode = cartonCodes.get(i);
				sheets[j].addCell(new Label(0, row, printJob.getPrintJobId().toString()));
				sheets[j].addCell(new Label(1, row, cartonCode.getOutPinCode()));
				sheets[j].addCell(new Label(2, row, ""));
				sheets[j].addCell(new Label(3, row, cartonCode.getCartonCode()));
				sheets[j].addCell(new Label(4, row, cartonCode.getPalletCode()));
				sheets[j].addCell(new Label(5, row, printJob.getMaterialName()));
				sheets[j].addCell(new Label(6, row, product.getSpecmode()));
				sheets[j].addCell(new Label(7, row, printJob.getGTINNumber()));
				sheets[j].addCell(new Label(8, row, printJob.getProductionDate()));
				sheets[j].addCell(new Label(9, row, printJob.getExpiryDate()));
				sheets[j].addCell(new Label(10, row, cartonCode.getPrintSeq().toString()));
				sheets[j].addCell(new Label(11, row, product.getProductname()));
				sheets[j].addCell(new Label(12, row, product.getmCode()));
				sheets[j].addCell(new Label(13, row, printJob.getProcessOrderNumber()));
				sheets[j].addCell(new Label(14, row, DateUtil.formatDate(printJob.getPrintingDate(), "yyyy-MM-dd")));
				sheets[j].addCell(new Label(15, row, printJob.getPackagingDate()));
				sheets[j].addCell(new Label(16, row, product.getProductnameen()));
			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
}
