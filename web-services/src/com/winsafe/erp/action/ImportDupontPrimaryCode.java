package com.winsafe.erp.action;


import java.util.Enumeration;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;


import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.erp.services.DupontCodeService;
import com.winsafe.sap.util.FileUploadUtil;
import com.winsafe.sap.util.SapConfig;

public class ImportDupontPrimaryCode extends BaseAction {


	private Logger logger = Logger.getLogger(ImportDupontPrimaryCode.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			initdata(request);
			
			boolean hasError = false;
			
			IdcodeUploadForm mf = (IdcodeUploadForm) form;
			FormFile codeFile = (FormFile) mf.getIdcodefile();
			if (codeFile != null && !codeFile.equals("") && !hasError && codeFile.getContentType() != null) {
				if (!(codeFile.getFileName().toLowerCase().indexOf("txt") >= 0)
						&& !(codeFile.getFileName().toLowerCase().indexOf("zip") >= 0)) {
					hasError = true;
					logger.error("文件后缀不正确");
				}
			} else {
				hasError = true;
				logger.error("上传文件为空");
			}
			
			if(!hasError) {
				
//				Properties props = SapConfig.getSapConfig();
//				logger.debug("sap.properties文件中的配置：");
//				Enumeration en = props.propertyNames();
//		        while (en.hasMoreElements()) {
//		            String key = (String) en.nextElement();
//		            String Property = props.getProperty(key);
//		            logger.debug("key="+key +" value="+Property);
//		        }
//		        logger.debug("从文件中获取相关配置：");
//		        logger.debug("dupontOrganId："+FileUploadUtil.getDupontOrganId());
//		        logger.debug("dupontCodeFilePath:"+FileUploadUtil.getDupontCodeFilePath());
//		        logger.debug("dupontCodeLogPath:"+FileUploadUtil.getDupontCodeLogFilePath());
				
				AppOrgan appOrgan = new AppOrgan();
				AppWarehouse appWarehouse = new AppWarehouse();
				
				String organId = FileUploadUtil.getDupontOrganId();
				Organ organ = appOrgan.getOrganByID(organId);
				if(organ == null) {
					request.setAttribute("result", "编号为["+organId+"]的机构在系统中不存在");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
				Warehouse warehouse = appWarehouse.getWarehouseByOID(organId);
				if(warehouse == null) {
					request.setAttribute("result", "机构["+organ.getOrganname()+"]不存在可用的仓库");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
				
				DupontCodeService dcs = new DupontCodeService(organ, warehouse, users);
				dcs.deal(codeFile);
				
				// 返回信息
				request.setAttribute("result", "文件上传成功,请通过日志查看处理结果");
				return mapping.findForward("success");
			} else {
				request.setAttribute("result", "上传文件失败,文件格式不对,请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
		} catch (Exception e) {
			logger.error("文件上传发生异常：",e);
			throw e;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
	}
}
