package com.winsafe.drp.action.machin;


import java.util.Date;
import java.util.Properties; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger; 
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;


import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.JProperties;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.RequestTool;
import com.winsafe.sap.dao.AppQualityInspection;
import com.winsafe.sap.pojo.QualityInspection;
import com.winsafe.sap.util.FileUploadUtil;
/**
 * Update By ryan.xi 生产数据文件上传处理
 */
public class AddQualityInspectionAction extends BaseAction {

	private Logger logger = Logger.getLogger(AddQualityInspectionAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AppQualityInspection appQI = new AppQualityInspection();
		initdata(request);
		try {
			String mCode = request.getParameter("mCode");
			String batch = request.getParameter("batch");
			String inspector = request.getParameter("inspector");
			Date inspectDate = DateUtil.StringToDate(request.getParameter("inspectDate"));
			Integer isQualified = RequestTool.getInt(request, "isQualified");
			
			if(appQI.isQualityInspectionExists(mCode, batch, null)) {
				request.setAttribute("result", "该物料与批次的质量检验信息已经存在!");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			
			QualityInspection qi = new QualityInspection();
			qi.setBatch(batch);
			qi.setInspectDate(inspectDate);
			qi.setInspector(inspector);
			qi.setIsQualified(isQualified);
			qi.setMakeDate(DateUtil.getCurrentDate());
			qi.setMakeId(userid);
			qi.setmCode(mCode);
			
			IdcodeUploadForm mf = (IdcodeUploadForm) form;
			FormFile convertCodeFile = (FormFile) mf.getIdcodefile();
			if (convertCodeFile != null && !convertCodeFile.equals("") && convertCodeFile.getContentType() != null) {
				if (!(convertCodeFile.getFileName().toLowerCase().indexOf("pdf") >= 0)) {
					request.setAttribute("result", "文件后缀名不正确");
//					return new ActionForward("/sys/lockrecord2.jsp");
				} else {
					//保存文件
					Properties pro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
					String fileName = convertCodeFile.getFileName();
					String saveFileName = DateUtil.getCurrentDateTimeString() + "_" + fileName;
					String savePath = pro.getProperty("qiFilePath") + DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM") + "/";
					FileUploadUtil.saveUplodedFile(convertCodeFile.getInputStream(), savePath, saveFileName);
					qi.setFilePath(savePath+saveFileName);
				}
			}
			
			appQI.addQualityInspection(qi);
			request.setAttribute("result", "新增成功");
			
			DBUserLog.addUserLog(request,"编号" +qi.getId());
		} catch (Exception e) {
			logger.error(e);
			request.setAttribute("result", "新增失败,网络异常");
		}
		
		return mapping.findForward("success");
	}
	
}
