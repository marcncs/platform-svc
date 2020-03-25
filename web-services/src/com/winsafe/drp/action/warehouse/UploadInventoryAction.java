package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppInventoryUploadLog;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.InventoryUploadLog;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.metadata.SapUploadLogStatus;
import com.winsafe.sap.util.FileUploadUtil;

public class UploadInventoryAction extends BaseAction{
	
	private Logger logger = Logger.getLogger(UploadInventoryAction.class);
	
	private AppInventoryUploadLog appInventoryUploadLog = new AppInventoryUploadLog();
	private AppOrgan appOrgan = new AppOrgan();
	private AppWarehouse appWarehouse = new AppWarehouse();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String organId = request.getParameter("receiveorganid");
			String warehouseId = request.getParameter("inwarehouseid");
			if(StringUtil.isEmpty(organId)) {
				request.setAttribute("result", "未选择机构");
				return mapping.findForward("result");
			}
			if(StringUtil.isEmpty(warehouseId)) {
				request.setAttribute("result", "未选择仓库");
				return mapping.findForward("result");
			}
			Organ organ = appOrgan.getOrganByID(organId);
			Warehouse warehouse = appWarehouse.getWarehouseByID(warehouseId);
			super.initdata(request);
			boolean hasError = false;
			StringBuffer resultMsg = new StringBuffer();
			IdcodeUploadForm mf = (IdcodeUploadForm) form;
			FormFile file = (FormFile) mf.getIdcodefile();
			if (file != null && !file.equals("") && file.getContentType() != null) {
				if (!(file.getFileName().toLowerCase().indexOf("txt") >= 0)) {
					hasError = true;
					resultMsg.append("文件后缀名不正确");
				}
			} else {
				resultMsg.append("文件上传失败");
				hasError = true;
			}
			if(!hasError) {
				String saveName = DateUtil.getCurrentDateTimeString() + "_" + organId + "_" + warehouseId+ "_" + organ.getOecode() + "_" + warehouse.getNccode() + "_" + file.getFileName().replaceAll("_", "");
				String savePath = FileUploadUtil.getInventoryUpdateFilePath() + DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM") + "/";
				FileUploadUtil.saveUplodedFile(file.getInputStream(), savePath, saveName);

				// 生成SAP文件上传日志记录
				InventoryUploadLog inventoryUploadLog = new InventoryUploadLog();
				inventoryUploadLog.setFileName(file.getFileName());
				inventoryUploadLog.setFilePath(savePath + saveName);
				inventoryUploadLog.setMakeId(users.getUserid());
				inventoryUploadLog.setMakeOrganId(users.getMakeorganid());
				inventoryUploadLog.setMakeDeptId(users.getMakedeptid());
				inventoryUploadLog.setMakeDate(Dateutil.getCurrentDate());
				inventoryUploadLog.setIsDeal(SapUploadLogStatus.NOT_PROCESS
						.getDatabaseValue());
				appInventoryUploadLog.addInventoryUploadLog(inventoryUploadLog);
				
				resultMsg.append("文件导入成功, 请通过日志查看处理结果");
				request.setAttribute("result", resultMsg);
			} else {
				request.setAttribute("result", resultMsg);
				return mapping.findForward("result");
			}
		} catch (Exception e) {
			logger.error("初始盘库文件导入", e);
			request.setAttribute("result", "文件导入失败");
		}
		DBUserLog.addUserLog(request, "初始盘库文件");
		return mapping.findForward("result");
	}
}
