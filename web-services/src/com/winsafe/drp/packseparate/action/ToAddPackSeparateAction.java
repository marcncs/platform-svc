package com.winsafe.drp.packseparate.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppWarehouse;

public class ToAddPackSeparateAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ToAddPackSeparateAction.class);
	private AppWarehouse appw = new AppWarehouse();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception { 
		
		super.initdata(request);
		try {
			//页面初始化时，默认机构和仓库
			request.setAttribute("MakeOrganID", users.getMakeorganid());
			request.setAttribute("oname", users.getMakeorganname());
			appw.getEnableWarehouseByvisit(request,users.getUserid(),users.getMakeorganid());
			return mapping.findForward("toadd");
		} catch (Exception e) {
			logger.error(e);
		}
		return new ActionForward(mapping.getInput());
	}
}
