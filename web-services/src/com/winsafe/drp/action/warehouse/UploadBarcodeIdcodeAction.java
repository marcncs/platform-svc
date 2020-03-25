package com.winsafe.drp.action.warehouse;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBarcodeInventory;
import com.winsafe.drp.dao.AppBarcodeUpload;
import com.winsafe.drp.dao.BarcodeInventory;
import com.winsafe.drp.dao.BarcodeUpload;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.metadata.InventoryConfirmStatus;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.sap.util.FileUploadUtil;

public class UploadBarcodeIdcodeAction extends BaseAction {
	private static Logger logger = Logger.getLogger(UploadBarcodeIdcodeAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		AppBarcodeInventory api = new AppBarcodeInventory();
		AppBarcodeUpload abu = new AppBarcodeUpload();
		// 初始化
		initdata(request);
		// 机构编号
		String billNo = request.getParameter("billNo");
		// 保存报错信息
		StringBuffer errMsg = new StringBuffer();
		int CCount = 0, SCount = 0;
		String realPath = request.getSession().getServletContext().getRealPath("/");
		try {
			
			BarcodeInventory bi = api.getBarcodeInventoryByID(billNo);
			
			if(bi == null) {
				request.setAttribute("result", "上传文件失败,单据编号为"+billNo+"的盘点单据不存在");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			
			if(!InventoryConfirmStatus.APPROVED.getValue().equals(bi.getIsapprove())) {
				request.setAttribute("result", "上传文件失败,单据编号为"+billNo+"的盘点单据还未审批通过");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			
			if(InventoryConfirmStatus.DISAPPROVE.getValue().equals(bi.getIsapprove())) {
				request.setAttribute("result", "上传文件失败,单据编号为"+billNo+"的盘点单据审批未通过");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			
			IdcodeUploadForm mf = (IdcodeUploadForm) form;
			FormFile idcodefile = (FormFile) mf.getIdcodefile();
			boolean bool = false;
			if (idcodefile != null && !idcodefile.equals("")) {
				if (idcodefile.getContentType() != null) {
					if (idcodefile.getFileName().indexOf("xls") >= 0) {
						bool = true;
					}
				}
			}
			if (bool) {
				Workbook wb = Workbook.getWorkbook(idcodefile.getInputStream());
				Sheet sheet = wb.getSheet(0);
				int row = sheet.getRows();
				Set<String> codesSet = new HashSet<String>();
				String dateString = DateUtil.getCurrentDateTimeString();
				StringBuffer codes = new StringBuffer();
				for (int i = 1; i < row; i++) {
					SCount++;
					try {
						// 获取单据号
						String code = sheet.getCell(0, i).getContents() == null ? null : sheet.getCell(0, i).getContents().trim();

						// 判断单据号是否为空
						if (StringUtil.isEmpty(code)) {
							errMsg.append("第[" + (i + 1) + "]行条码为空,导入失败<br />");
							continue;
						}
						if(codesSet.contains(code)) {
							errMsg.append("第[" + (i + 1) + "]行条码重复,导入失败<br />");
							continue;
						} else {
							codesSet.add(code);
						}
						codes.append("{\"count\":\"1\",\"fromWHID\":\"\",\"pUnitId\":\"\",\"scannerFlag\":\"B\",\"sum\":\"\",\"scannerType\":\"99\",\"userID\":\""+users.getLoginname()+"\",\"ScannerName\":\"000\",\"lCode\":\"\",\"scannerDate\":\""+dateString+"\",\"batchNumber\":\"\",\"billNo\":\""+billNo+"\",\"barCode\":\""+code+"\",\"toWHID\":\""+bi.getWarehouseid()+"\",\"productDate\":\"\"}").append("\r\n");
						
						CCount++;
					} catch (Exception e) {
						errMsg.append("第[" + (i + 1) + "]行导入失败<br />");
						logger.error("第[" + (i + 1) + "]行导入失败", e);
						HibernateUtil.rollbackTransaction();
					}
				}
				wb.close();
				
				String fileName = users.getLoginname() + "_" + dateString +"_manul_In.txt";
				
				String uploadPath = Constants.IDCODE_UPLOAD_PATH[22]
				                    + DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM") + "/";
				FileUploadUtil.CreateFileWithMessage(codes.toString(), realPath+uploadPath, fileName);
				
				String iuId = MakeCode.getExcIDByRandomTableName("idcode_upload", 0, "");
				BarcodeUpload iu = new BarcodeUpload();
				iu.setMakeorganid(users.getMakeorganid());
				iu.setMakedeptid(users.getMakedeptid());
				iu.setMakeid(users.getUserid());
				iu.setUpusername(users.getLoginname());
				iu.setId(Integer.valueOf(iuId));
				iu.setFilename(fileName);
				iu.setBillsort(99);
				iu.setIsdeal(0);
				iu.setValinum(0);
				iu.setFailnum(0);
				iu.setFilepath(uploadPath+fileName);
				iu.setFailfilepath("");
				iu.setMakedate(DateUtil.getCurrentDate());
				iu.setIsupload(0);
				iu.setPhysicalpath(realPath);
				iu.setIsticket(0); // 0为有单1为无单
				abu.addBarcodeUpload(iu);
			} else {
				HibernateUtil.rollbackTransaction();
				request.setAttribute("result", "上传文件失败,请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			String resultMsg = "文件上传成功,请通过日志查看处理结果。";
			request.setAttribute("result", resultMsg);
			return mapping.findForward("success");
		} catch (Exception e) {
			request.setAttribute("result", "上传文件失败,文件格式不对,请重试");
			logger.error("文件上传失败", e);
			return new ActionForward("/sys/lockrecord2.jsp");
		} 
	}
}