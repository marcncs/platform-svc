package com.winsafe.drp.keyretailer.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.keyretailer.dao.AppSBonusSetting;
import com.winsafe.drp.keyretailer.dao.AppSBonusTarget;
import com.winsafe.drp.keyretailer.pojo.SBonusSetting;
import com.winsafe.drp.keyretailer.pojo.SBonusTarget;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.BeanCopy;
import com.winsafe.hbm.util.DateUtil;

public class DelSBonusTargetAction extends BaseAction {
	private static Logger logger = Logger.getLogger(DelSBonusTargetAction.class);
	
	private AppSBonusTarget app = new AppSBonusTarget();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		try {
			String id = request.getParameter("id");
			app.delSBonusTargetByid(id);
			
			request.setAttribute("result", "databases.del.success");
			return mapping.findForward("del");
		} catch (Exception e) {
			logger.error("",e);
			throw e;
		}
	}
	

}
