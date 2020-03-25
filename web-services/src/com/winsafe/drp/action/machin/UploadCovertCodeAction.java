package com.winsafe.drp.action.machin;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;


import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.RecordDao;
import com.winsafe.drp.dao.UploadCovertCodeForm;
import com.winsafe.drp.dao.UploadPrLog;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.UsersService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.sap.util.FileUploadUtil;

/**
 * Update By ryan.xi 暗码上传处理
 */
public class UploadCovertCodeAction extends BaseAction {


	private Logger logger = Logger.getLogger(UploadCovertCodeAction.class);

	private RecordDao rDao = new RecordDao();
	private AppUsers appUsers = new AppUsers();
	private AppOrgan appOrgan = new AppOrgan();

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			// 初始化
			long time1 = System.currentTimeMillis();
			logger.debug("-------验证用户信息-----");
			String errorMsg = checkOtherInterfacePwd(request);
			if(!StringUtil.isEmpty(errorMsg)){
				ResponseUtil.writeJsonMsg((HttpServletResponse)response,Constants.CODE_LOGIN_FAIL, errorMsg);
				return null;
			}
			users = UserManager.getUser(request);
			
			String username = request.getParameter("Username");
			if (users == null) {
				users = UserManager.getBeanFromUsers(appUsers.getUsers(username));
			}
			long time2 = System.currentTimeMillis();
			logger.debug("-------验证用户信息花时："+(time2-time1)+"ms-----");
			boolean hasError = false;
			
			
			logger.debug("-------开始保存文件-----");
			UploadCovertCodeForm mf = (UploadCovertCodeForm) form;
			FormFile convertCodeFile = (FormFile) mf.getFilestream();
			if (convertCodeFile != null && !convertCodeFile.equals("") && !hasError && convertCodeFile.getContentType() != null) {
				if (!(convertCodeFile.getFileName().toLowerCase().indexOf("txt") >= 0)) {
					hasError = true;
					logger.error("文件后缀不正确");
				}
			} else {
				hasError = true;
				logger.error("上传文件为空");
			}
			
			if(!hasError) {
				// 保存文件
				String fileName = convertCodeFile.getFileName();
				String saveFileName = DateUtil.getCurrentDateTimeString() + "_" + fileName;
				String savePath = FileUploadUtil.getCovertCodeFilePath() + DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM") + "/";
				FileUploadUtil.saveUplodedFile(convertCodeFile.getInputStream(), savePath, saveFileName);
				
				logger.debug("-------结束保存文件-----");
				long time3 = System.currentTimeMillis();
				logger.debug("-------保存文件花时："+(time3-time2)+"ms-----");
				logger.debug("-------开始添加日志-----");
				//添加文件上传日志
				UploadPrLog record = new UploadPrLog();
				Integer id = Integer.valueOf(MakeCode
						.getExcIDByRandomTableName("Record", 0, ""));
				record.setId(id);
				record.setFilename(fileName);
				record.setFilepath(savePath + saveFileName);
				record.setMakeid(users.getUserid());
				record.setMakeorganid(users.getMakeorganid());
				record.setMakedeptid(users.getMakedeptid());
				record.setMakedate(DateUtil.getCurrentDate());
				record.setIsdeal(0);
				rDao.save(record);
				logger.debug("-------结束添加日志-----");
				long time4 = System.currentTimeMillis();
				logger.debug("-------添加日志花时："+(time4-time3)+"ms-----");
				// 返回信息
				ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS,
						Constants.CODE_SUCCESS_MSG);
			} else {
				ResponseUtil.writeJsonMsg(response, "-2", "失败");
			}
		} catch (Exception e) {
			logger.error("文件上传发生异常：",e);
			try {
				ResponseUtil.writeJsonMsg(response, "-2", "失败");
			} catch (Exception e1) {
				logger.error(e1);
			}
		}
		return null;
	}
	
	private String checkOtherInterfacePwd(HttpServletRequest request) throws Exception{
		String username = request.getParameter("Username");
		String password = request.getParameter("Password");
		UsersService usServices = new UsersService(); 
		UsersBean usersBean = usServices.CheckUsersNamePassword(username, password);
		String result = "";
		if(	usersBean == null ){
			return Constants.CODE_LOGIN_FAIL_MSG;
		}
		// 判断用户是否可用
		if( usersBean.getStatus() != 1){
			return "用户不可用!";
		}
		// 判断用户所属机构是否撤销
		Organ organ = appOrgan.getOrganByID_Isrepeal(usersBean.getMakeorganid());
		if(organ == null){
			return "用户不可用!";
		}
		return result ;
	}
	
}
