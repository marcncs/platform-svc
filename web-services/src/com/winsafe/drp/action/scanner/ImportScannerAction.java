package com.winsafe.drp.action.scanner;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppScanner;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.Scanner;
import com.winsafe.hbm.entity.HibernateUtil;

public class ImportScannerAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppScanner as = new AppScanner();
		//保存报错信息
		StringBuffer errMsg = new StringBuffer();
		int CCount =0,SCount=0;
		try {
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
				
				Scanner s = null;
				for (int i = 1; i < row; i++) {
					SCount ++;
					try {
						
						//采集器编号
						String id = sheet.getCell(0, i).getContents()==null ? null : sheet.getCell(0, i).getContents().trim();
						//判断采集器编号是否为空
						if(StringUtil.isEmpty(id)){
							errMsg.append("第[" + (i+1) + "]行采集器编号不能为空<br />");
							continue;
						}
						//查找采集器编号是否存在
						Scanner scannerById = as.findScannerById(id);
						//判断采集器编号是否存在
						if(scannerById!=null){
							errMsg.append("第[" + (i+1) + "]行采集器编号已经存在<br />");
							continue;
						}
						
						//采集器型号
						String model = sheet.getCell(1, i).getContents()==null ? null : sheet.getCell(1, i).getContents().trim();
						//判断采集器型号是否为空
						if(StringUtil.isEmpty(model)){
							errMsg.append("第[" + (i+1) + "]行采集器型号不能为空<br />");
							continue;
						}
						//采集器系统版本
						String osVersion = sheet.getCell(2, i).getContents()==null ? null : sheet.getCell(2, i).getContents().trim();
						//判断采集器系统版本是否为空
						if(StringUtil.isEmpty(osVersion)){
							errMsg.append("第[" + (i+1) + "]行采集器系统版本不能为空<br />");
							continue;
						}
						//采集器编号
						String scannerImeiN = sheet.getCell(3, i).getContents()==null ? null : sheet.getCell(3, i).getContents().trim();
						//采集器编号是否为空
						if(StringUtil.isEmpty(scannerImeiN)){
							errMsg.append("第[" + (i+1) + "]行采集器编号不能为空<br />");
							continue;
						}
						
						//采集器状态
						String status = sheet.getCell(4, i).getContents()==null ? null : sheet.getCell(4, i).getContents().trim();
						//采集器状态是否为空
						if(StringUtil.isEmpty(status)){
							errMsg.append("第[" + (i+1) + "]行采集器状态不能为空<br />");
							continue;
						}
						//采集器安装日期
						String installDate = sheet.getCell(5, i).getContents()==null ? null : sheet.getCell(5, i).getContents().trim();
						//判断采集器安装日期是否为空
						if(StringUtil.isEmpty(installDate)){
							errMsg.append("第[" + (i+1) + "]行采集器安装日期不能为空<br />");
							continue;
						}
						//采集器软件版本
						String appVersion = sheet.getCell(6, i).getContents()==null ? null : sheet.getCell(6, i).getContents().trim();
						//判断采集器软件版本是否为空
						if(StringUtil.isEmpty(appVersion)){
							errMsg.append("第[" + (i+1) + "]行采集器软件版本不能为空<br />");
							continue;
						}
						
						//采集器更新日期
						String appVerUpDate = sheet.getCell(7, i).getContents()==null ? null : sheet.getCell(7, i).getContents().trim();
						//判断采集器更新日期是否为空
						if(StringUtil.isEmpty(appVerUpDate)){
							errMsg.append("第[" + (i+1) + "]行判断采集器更新日期不能为空<br />");
							continue;
						}
						
						//采集器仓库代码
						String wHCode = sheet.getCell(8, i).getContents()==null ? null : sheet.getCell(8, i).getContents().trim();
						//判断采集器仓库代码是否为空
						if(StringUtil.isEmpty(wHCode)){
							errMsg.append("第[" + (i+1) + "]行采集器仓库代码不能为空<br />");
							continue;
						}
						
						//采集器名字
						String scannerName = sheet.getCell(9, i).getContents()==null ? null : sheet.getCell(9, i).getContents().trim();
						//判断采集器采集器名字是否为空
						if(StringUtil.isEmpty(scannerName)){
							errMsg.append("第[" + (i+1) + "]行采集器名字不能为空<br />");
							continue;
						}
						
						//采集器最后更新日期
						String lastUpDate = sheet.getCell(10, i).getContents()==null ? null : sheet.getCell(10, i).getContents().trim();
						//判断采集器最后更新日期是否为空
						if(StringUtil.isEmpty(lastUpDate)){
							errMsg.append("第[" + (i+1) + "]行采集器最后更新日期不能为空<br />");
							continue;
						}
						HibernateUtil.currentSession();
						s = new Scanner();
//						s.setId(MakeCode.getExcIDByRandomTableName("product", 4, ""));
						s.setId(Long.parseLong(id));
						s.setModel(model);
						s.setOsVersion(osVersion);
						s.setScannerImeiN(scannerImeiN);
						s.setStatus(Long.valueOf(status));
						s.setInstallDate(Date.valueOf(installDate));
						s.setAppVersion(appVersion);
						s.setAppVerUpDate(Date.valueOf(appVerUpDate));
						s.setwHCode(Long.valueOf(wHCode));
						s.setScannerName(scannerName);
						s.setLastUpDate(Date.valueOf(lastUpDate));
						as.addNewScanner(s);
						CCount++;
						//每行数据手动提交事务,防止事务事件过长
						HibernateUtil.commitTransaction();
					} catch (Exception e) {
						errMsg.append("第[" + (i+1) + "]行导入失败<br />");
						e.printStackTrace();
						HibernateUtil.rollbackTransaction();
					}
				}
				wb.close();
			} else {
				HibernateUtil.rollbackTransaction();
				request.setAttribute("result", "上传文件失败,请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			String resultMsg = "上传采集器资料成功,本次总共添加 :"+(SCount)+"条! 成功:"+CCount+"条! 失败："+(SCount-CCount)+"条!";
			if (SCount-CCount > 0) {
				resultMsg = resultMsg
						+ "<br/>&nbsp;&nbsp;&nbsp;&nbsp;失败原因如下：<br/>"
						+ "<div >"
						+ 	errMsg.toString()
						+ "</div>";
			}
			request.setAttribute("result", resultMsg);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "上传文件失败,文件格式不对,请重试");
			System.out.println("异常出现在第 " + CCount + "行");
			return new ActionForward("/sys/lockrecord2.jsp");
		}
	}
}
