package com.winsafe.drp.keyretailer.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.keyretailer.dao.AppSBonusSetting;
import com.winsafe.drp.keyretailer.pojo.SBonusSetting;
import com.winsafe.drp.keyretailer.service.SBonusService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.BeanCopy;
import com.winsafe.hbm.util.DateUtil;

public class AddSBonusSettingAction extends BaseAction {
	private static Logger logger = Logger.getLogger(AddSBonusSettingAction.class);
	
	private SBonusService sBonusService = new SBonusService();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		try {
			SBonusSetting sbs = new SBonusSetting();
			//将Map中对应的值赋值给实例
			BeanCopy bc = new BeanCopy();
			bc.copy(sbs, request);
			sbs.setModifiedDate(DateUtil.getCurrentDate());
			String[] date = request.getParameter("endDate").split("-");
			sbs.setYear(Integer.parseInt(date[0]));
			sbs.setMonth(Integer.parseInt(date[1]));
			
			if(sBonusService.isBonusAlreadyExists(sbs)) {
				request.setAttribute("result", "新增失败,该日期范围内已存在相关配置!");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			
			sBonusService.addSBonusSetting(sbs);
			
			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(request, "新增,编号" + sbs.getId());
			return mapping.findForward("addresult");

		} catch (Exception e) {
			logger.error("",e);
			throw e;
		}
	}
	

}
