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

public class UpdSBonusConfigAction extends BaseAction {
	private static Logger logger = Logger.getLogger(UpdSBonusConfigAction.class);
	
	private AppSBonusConfig appSBonusConfig = new AppSBonusConfig();
	
	@SuppressWarnings("static-access")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		try {
			String id = request.getParameter("id");
			SBonusConfig sbs = appSBonusConfig.getSBonusConfigById(id);
			
			if(sbs.getIsCounted() != null && sbs.getIsCounted() == 1) {
				request.setAttribute("result", "修改失败,该年度的积分已统计!");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			
			//将Map中对应的值赋值给实例
			BeanCopy bc = new BeanCopy();
			bc.copy(sbs, request);
			sbs.setMakeDate(DateUtil.getCurrentDate());
			sbs.setIsCounted(0);
			
			appSBonusConfig.updSBonusConfig(sbs);
			
			request.setAttribute("result", "databases.upd.success");
			DBUserLog.addUserLog(request, "修改,编号" + sbs.getId());
			return mapping.findForward("updresult");

		} catch (Exception e) {
			logger.error("",e);
			throw e;
		}
	}
}
