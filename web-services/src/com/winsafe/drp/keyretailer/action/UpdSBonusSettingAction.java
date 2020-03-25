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

public class UpdSBonusSettingAction extends BaseAction {
	private static Logger logger = Logger.getLogger(UpdSBonusSettingAction.class);
	
	private AppSBonusSetting appSBonusSetting = new AppSBonusSetting();
	private SBonusService sBonusService = new SBonusService();
	
	@SuppressWarnings("static-access")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		try {
			String id = request.getParameter("id");
			SBonusSetting sbs = appSBonusSetting.getSBonusSettingById(id);
			String[] date = request.getParameter("endDate").split("-");
			
			Integer year = Integer.parseInt(date[0]);
			Integer month = Integer.parseInt(date[1]);
			
			//将Map中对应的值赋值给实例
			BeanCopy bc = new BeanCopy();
			bc.copy(sbs, request);
			sbs.setModifiedDate(DateUtil.getCurrentDate());
			sbs.setYear(Integer.parseInt(date[0]));
			sbs.setMonth(Integer.parseInt(date[1]));
			
			if(sBonusService.isBonusAlreadyExists(sbs, id,year,month)) {
				request.setAttribute("result", "修改失败,该日期范围内已存在相关配置!");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			
			appSBonusSetting.updSBonusSetting(sbs);
			
			request.setAttribute("result", "databases.upd.success");
			DBUserLog.addUserLog(request, "修改,编号" + sbs.getId());
			return mapping.findForward("updresult");

		} catch (Exception e) {
			logger.error("",e);
			throw e;
		}
	}
}
