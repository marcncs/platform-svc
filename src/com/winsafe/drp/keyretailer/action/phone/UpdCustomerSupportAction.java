package com.winsafe.drp.keyretailer.action.phone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.dao.AppSBonusConfig;
import com.winsafe.drp.keyretailer.dao.AppSBonusTarget;
import com.winsafe.drp.keyretailer.pojo.SBonusConfig;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.DateUtil;

public class UpdCustomerSupportAction extends BaseAction {
	private Logger logger = Logger.getLogger(UpdCustomerSupportAction.class);
	
	private AppUsers appUsers = new AppUsers();
//	private AppSBonusAppraise appSBonusAppraise = new AppSBonusAppraise();
	private AppSBonusConfig appSBonusConfig = new AppSBonusConfig();
	private AppSBonusTarget appSBonusTarget = new AppSBonusTarget();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean loginUsers = null;
		try {
			SBonusConfig sbc = appSBonusConfig.getCurrentPeriodConfig(DateUtil.getCurrentDateString(), 2);
			if(sbc == null) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "更新失败,目前不在评价周期内");
				return null;
			}
			
			String username = request.getParameter("Username"); //登陆名
			
			String[] organIds = request.getParameter("organId").substring(1).split(",");
			String year = request.getParameter("year");
			String[] isSupported = request.getParameter("isSupported").substring(1).split(",");
			
			// 判断用户是否存在
			loginUsers = appUsers.getUsersBeanByLoginname(username);
//			appSBonusAppraise.updCustomerSupportAction(loginUsers.getMakeorganid(), organId, year, isSupported);
			for(int i=0;i<organIds.length;i++) {
				appSBonusTarget.updCustomerSupportAction(loginUsers.getMakeorganid(), organIds[i], year, isSupported[i]);
			}
			
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG,"organId"+request.getParameter("organId")+" year"+year+" isSupported"+request.getParameter("isSupported"), loginUsers.getUserid(),"APP_RI","下级客户业务支持度评价更新",true);
		} catch (Exception e) {
			logger.error("", e);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "失败,系统异常");
		}
		return null;
	}
}
