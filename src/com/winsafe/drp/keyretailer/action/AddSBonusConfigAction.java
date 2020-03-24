package com.winsafe.drp.keyretailer.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.keyretailer.dao.AppSBonusConfig;
import com.winsafe.drp.keyretailer.pojo.SBonusConfig;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.BeanCopy;
import com.winsafe.hbm.util.DateUtil;

public class AddSBonusConfigAction extends BaseAction {
	private static Logger logger = Logger.getLogger(AddSBonusConfigAction.class);
	
	private AppSBonusConfig appSBonusConfig = new AppSBonusConfig();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		try {
			SBonusConfig sbs = new SBonusConfig();
			//将Map中对应的值赋值给实例
			BeanCopy bc = new BeanCopy();
			bc.copy(sbs, request);
			sbs.setIsCounted(0);
			sbs.setMakeDate(DateUtil.getCurrentDate());
			
			if(appSBonusConfig.isBonusConfigAlreadyExists(sbs)) {
				request.setAttribute("result", "新增失败,该参数配置已存在!");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			
			appSBonusConfig.addSBonusConfig(sbs);
			
			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(request, "新增,编号" + sbs.getId());
			return mapping.findForward("addresult");

		} catch (Exception e) {
			logger.error("",e);
			throw e;
		}
	}
	

}
