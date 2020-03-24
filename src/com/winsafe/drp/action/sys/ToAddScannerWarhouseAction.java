package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppScanner;
import com.winsafe.drp.dao.AppScannerWarehouse;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.ScannerWarehouse;

/**
 * 为仓库配置采集器
* @Title: ToAddScannerWarhouseAction.java
* @Description: TODO
* @author: wenping 
* @CreateTime: Jul 16, 2012 8:40:31 AM
* @version:
 */
public class ToAddScannerWarhouseAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		AppWarehouse aw = new AppWarehouse();
		AppScannerWarehouse asw = new AppScannerWarehouse();
		AppScanner appScanner = new AppScanner();
		
		try {
			String warhouseid= request.getParameter("wid");
			String type= request.getParameter("type");
			String swid= request.getParameter("swid");
			
			
			request.setAttribute("warhouseid", warhouseid);
			if(type.equals("ADD")){
				request.setAttribute("type", "ADD");
				return mapping.findForward("toadd");
			}else if(type.equals("EDIT")){
				ScannerWarehouse sw = asw.getSWBWAS(swid,warhouseid);
				request.setAttribute("sw", sw);
				request.setAttribute("type", "EDIT");
				return mapping.findForward("toadd");
			}else if(type.equals("DELETE")){
				ScannerWarehouse sw = asw.getSWBWAS(swid,warhouseid);
				asw.delScannerWarehouseBySW(sw.getScannerid(),sw.getWarehouseid());
				request.setAttribute("result", "databases.del.success");
				return mapping.findForward("todel");
			}
			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
