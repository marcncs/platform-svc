package com.winsafe.drp.keyretailer.action;

import java.util.ArrayList;
import java.util.List;
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
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.keyretailer.dao.AppSBonusArea;
import com.winsafe.drp.keyretailer.dao.AppSCustomerArea;
import com.winsafe.hbm.entity.HibernateUtil;

public class ImportSCustomerAreaAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ImportSCustomerAreaAction.class);
	private AppSCustomerArea appSCustomerArea = new AppSCustomerArea();
	private AppSBonusArea appSBonusArea = new AppSBonusArea();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 初始化
		initdata(request);
		// 保存报错信息
		StringBuffer errMsg = new StringBuffer();
		int CCount = 0, SCount = 0;
		
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
				List<String[]> dataList = new ArrayList<String[]>();
				StringBuffer areaIds = new StringBuffer();
				StringBuffer organIds = new StringBuffer();
				for (int i = 1; i < row; i++) {
					SCount++;
					// 获取区域编号
					String areaId = sheet.getCell(0, i).getContents() == null ? null : sheet
							.getCell(0, i).getContents().trim();
					if (StringUtil.isEmpty(areaId)) {
						errMsg.append("第[" + (i + 1) + "]行区域编号不能为空<br />");
						continue;
					}
					// 获取机构编号
					String organId = sheet.getCell(1, i).getContents() == null ? null : sheet
							.getCell(1, i).getContents().trim();
					if (StringUtil.isEmpty(organId)) {
						errMsg.append("第[" + (i + 1) + "]行机构编号不能为空<br />");
						continue;
					}
					areaIds.append(","+areaId);
					organIds.append(",'"+organId+"'");
					dataList.add(new String[]{String.valueOf(i + 1), areaId, organId});
				}
				
				if(areaIds.length() > 0) {
					List<String> sqls = new ArrayList<String>();
					Set<String> areaIdSet = appSBonusArea.getAreaIdSet(areaIds.substring(1));
					Set<String> userIdSet = appSCustomerArea.getOrganIdSet(organIds.substring(1));
					for(String[] data : dataList) {
						if(!areaIdSet.contains(data[1])) {
							errMsg.append("第[" + data[0] + "]行区域编号"+data[1]+"在系统中不存在<br />");
							continue;
						}
						if(!userIdSet.contains(data[2])) {
							errMsg.append("第[" + data[0] + "]行机构编号"+data[2]+"在系统中不存在<br />");
							continue;
						}
						sqls.add("delete from S_CUSTOMER_AREA where organId='"+data[2]+"'");
						sqls.add("INSERT INTO S_CUSTOMER_AREA VALUES (S_CUSTOMER_AREA_SEQ.nextval,"+data[2]+","+data[1]+",null,sysdate,0)");
						CCount++;
					}
					appSCustomerArea.updSCustomerAreaByBatch(sqls);
				}
				wb.close();
			} else {
				HibernateUtil.rollbackTransaction();
				request.setAttribute("result", "上传文件失败,请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			String resultMsg = "上传积分设置成功,本次总共添加 :" + (SCount) + "条! 成功:" + CCount + "条! 失败："
					+ (SCount - CCount) + "条!";
			if (SCount - CCount > 0) {
				resultMsg = resultMsg + "<br/>&nbsp;&nbsp;&nbsp;&nbsp;失败原因如下：<br/>" + "<div >"
						+ errMsg.toString() + "</div>";
			}
			request.setAttribute("result", resultMsg);
			return mapping.findForward("success");
		} catch (Exception e) {
			logger.error("文件上传失败", e);
			request.setAttribute("result", "上传文件失败,文件格式不对,请重试");
			return new ActionForward("/sys/lockrecord2.jsp");
		} 
	}
	
}
