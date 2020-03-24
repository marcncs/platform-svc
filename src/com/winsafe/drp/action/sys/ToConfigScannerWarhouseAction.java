package com.winsafe.drp.action.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppScannerWarehouse;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.util.DBUserLog;

/**
 * 为仓库配置采集器
* @Title: ToConfigScannerWarhouseAction.java
* @author: wenping 
* @CreateTime: Jul 16, 2012 8:37:48 AM
* @version:
 */
public class ToConfigScannerWarhouseAction extends BaseAction {
	
	private AppWarehouse aw = new AppWarehouse();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String warhouseid= request.getParameter("ID");
			String wname= "";
			AppScannerWarehouse asw = new AppScannerWarehouse();
			List scanwarhouses = asw.getScannerWarehouse();
			request.setAttribute("warhouseid", warhouseid);
			request.setAttribute("scanwarhouses", scanwarhouses);
			request.setAttribute("wname", wname);

			DBUserLog.addUserLog(userid, 11, "机构设置>>采集器配置");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
