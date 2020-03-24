package com.winsafe.drp.action.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.BaseBillDetail;

public abstract class ToAddBaseBarcodeDetailiiAction extends Action {

	Logger logger = Logger.getLogger(ToAddBaseBarcodeDetailiiAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		String billid = request.getParameter("billid");
		
		String bdid = request.getParameter("bdid");
		
		String wid = request.getParameter("wid");
		
		String isaudit = request.getParameter("isaudit");	
		
		String batch = request.getParameter("batch");
		
		AppWarehouse aw = new AppWarehouse();
		
		try{
			
			List bitlist = aw.getWarehouseBitByWid(wid);
			
			BaseBillDetail bbd = getBillDetail(billid,bdid,batch);			
			
			request.setAttribute("bbd", bbd);			
			request.setAttribute("bitlist", bitlist);
			request.setAttribute("billid", billid);		
			request.setAttribute("batch", batch);		
			request.setAttribute("wid", wid);
			request.setAttribute("isaudit", isaudit);
			return mapping.findForward("toadd");
		} catch (Exception e) {
			logger.error("", e);
		}
		return new ActionForward(mapping.getInput());
	}
	
	protected abstract BaseBillDetail getBillDetail(String billid,String bdid,String batch) throws Exception;
}

