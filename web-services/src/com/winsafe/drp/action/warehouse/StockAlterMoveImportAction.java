package com.winsafe.drp.action.warehouse;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.common.util.FileUtil;
import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.server.StockAlterMoveImportService;
import com.winsafe.drp.util.ReturnPage;
import com.winsafe.hbm.util.DateUtil;

public class StockAlterMoveImportAction extends BaseAction {
	
	private static final Logger logger = Logger.getLogger(StockAlterMoveImportAction.class);
	private final String UPLOAD_PATH = "/upload/StockAlterMove/";
	private AppOrgan appOrgan = new AppOrgan();
	private AppWarehouse appWarehouse = new AppWarehouse();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			super.initdata(request);
			String organId = request.getParameter("organid");
//			String organName = request.getParameter("orgname");
			String warehouseId = request.getParameter("outwarehouseid");
//			String outWarehouseName = request.getParameter("owname");
			String templateNo = request.getParameter("templateNo");
//			Integer dataRowNo = Integer.parseInt(request.getParameter("dataRowNo")) - 1;
//			Integer titleRowNo = Integer.parseInt(request.getParameter("titleRowNo")) - 1;
			
			Organ organ = appOrgan.getOrganByID(organId);
			Warehouse warehouse = appWarehouse.getWarehouseByID(warehouseId);
			
			IdcodeUploadForm mf = (IdcodeUploadForm) form;

			// 如果上传文件不为空，则在本地备份上传的文件
			FormFile file = (FormFile) mf.getIdcodefile();
			if (file == null ) {
				return ReturnPage.getReturn(request, "1", "文件不存在", false);
			}
			// 文件名
			String fileRealName = file.getFileName();

			if (StringUtil.isEmpty(fileRealName) && !fileRealName.endsWith(".xls") && !fileRealName.endsWith(".xlsx")) {
				return ReturnPage.getReturn(request, "1", "文件类型不正确", false);
			}
			
			// 如果上传的文件类型正确，则在本地备份
			InputStream fis = file.getInputStream();
			String saveFileName = DateUtil.getCurrentDateTimeString() + "_"+ fileRealName;
			String uploadPath = UPLOAD_PATH + DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM")+ "/";

			String realPath = request.getRealPath("/");
			FileUtil.createDir(realPath + uploadPath);

			// 保存原文件
			String fileAddress = realPath + uploadPath + saveFileName;
			FileUtil.copyFile(fis, fileAddress);
			
//			StockAlterMoveImportService samis = new StockAlterMoveImportService();
			StockAlterMoveImportService samis = new StockAlterMoveImportService(organ, warehouse,templateNo,users);
			String result = samis.dealFile(fileAddress, users.getUserid());
			
			return ReturnPage.getReturn(request, "1", result, true);
		}
		catch (Exception e) {
			logger.error("单据导入发生异常", e);
			throw e;
		}
	}
	
	
	/*public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			super.initdata(request);
			
			IdcodeUploadForm mf = (IdcodeUploadForm) form;

			FormFile file = (FormFile) mf.getIdcodefile();
			// 文件扩展名
			String extName = null;
			// 文件名
			String filerealname = "";
			String prifixname="";
			// 备份上传文件路径
			String fileAddress = "";
			// 备份上传文件路径2
			String fileAddressBak = "";
			String realPath = request.getRealPath("/");
			//上传文件类型
			Integer billsort = mf.getBillsort();

			
			 * 判断文件格式
			 
			// 如果上传文件不为空，则在本地备份上传的文件
			if (file == null ) {
				throw new CustomException("文件不存在！");
			}
			filerealname = file.getFileName();

			if (!StringUtil.isEmpty(filerealname)&& filerealname.endsWith(".xls")) {
				// do nothing;
				prifixname = filerealname.substring(0, filerealname.indexOf("."));
			} else {
				throw new CustomException("文件类型不正确！");
			}
			
			 * 备份文件
			 
			// 如果上传的文件类型正确，则在本地备份
			InputStream fis = file.getInputStream();
			String saveFileName = DateUtil.getCurrentDateTimeString() + "_"+ filerealname;
			String uploadPath = "/upload/StockAlterMove/"+ DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM")+ "/";
			String uploadBakPath = "../upload/StockAlterMove/"+ DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM")+ "/";

			FileUtil.createDir(realPath + uploadPath);
			FileUtil.createDir(realPath + uploadBakPath);

			// 保存原文件(2处备份)
			fileAddress = realPath + uploadPath + saveFileName;
			fileAddressBak = realPath + uploadBakPath + saveFileName;
			FileUtil.copyFile(fis, fileAddress,fileAddressBak);
			*//**
			 * 处理主数据
			 *//*
		  String result = null;
	      switch (billsort) {
		  case 5:
			  //销售折扣单
			    StockAlterMoveImportRebateService stockAlterMoveImportRebateService =new StockAlterMoveImportRebateService();
			  //  result=  stockAlterMoveImportRebateService.dealFile(fileAddress, users, prifixname);
			    DBUserLog.addUserLog(userid, 4, "渠道管理>>订购导入!");
			break;
		  case 6:
			   //配货出库单
			    StockAlterMoveImportMatchService stockAlterMoveImportMatchService =new StockAlterMoveImportMatchService();
			 //   result=  stockAlterMoveImportMatchService.dealFile(fileAddress, users, prifixname);
			    DBUserLog.addUserLog(userid, 4, "渠道管理>>订购导入!");
				break;
		  case 7:
			   //销售退货折扣单
			    StockAlterMoveImportRebackOrderService stockAlterMoveImportRebackOrderService =new StockAlterMoveImportRebackOrderService();
			  //  result=  stockAlterMoveImportRebackOrderService.dealFile(fileAddress, users, prifixname);
			    DBUserLog.addUserLog(userid, 4, "渠道管理>>订购导入!");
				break;
		  case 8:
			  //赠送单
			    StockAlterMoveImportPresentService stockAlterMoveImportPresentService =new StockAlterMoveImportPresentService();
			    // result=  stockAlterMoveImportPresentService.dealFile(fileAddress, users, prifixname);
			    DBUserLog.addUserLog(userid, 4, "渠道管理>>订购导入!");
				break;
		  default:
			break;
	    	}
	      
	      StockAlterMoveImportAllService stockAlterMoveImportPresentService =new  StockAlterMoveImportAllService();
	      result=  stockAlterMoveImportPresentService.dealFile(fileAddress, users, prifixname, billsort);
	      DBUserLog.addUserLog(userid, 4, "渠道管理>>订购导入!");
	      
	      //StockAlterMoveImportService service = new StockAlterMoveImportService();
		  //String result = service.dealFile(fileAddress,users,prifixname);
			return ReturnPage.getReturn(request, "1", result, false);
		} catch (CustomException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}*/

}
