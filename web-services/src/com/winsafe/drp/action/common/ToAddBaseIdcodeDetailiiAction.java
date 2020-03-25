package com.winsafe.drp.action.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.BaseBillDetail;

/**
 * 录入序号
 * @author jelli
 * @copy www.winsafe.cn
 */
public abstract class ToAddBaseIdcodeDetailiiAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		String billid = request.getParameter("billid");
		
		int bdid = Integer.valueOf(request.getParameter("bdid"));
		
		String wid = request.getParameter("wid");
		
		String isaudit = request.getParameter("isaudit");	
		
		String batch = request.getParameter("batch");
		
		AppWarehouse aw = new AppWarehouse();
		
		try{
			
			List bitlist = aw.getWarehouseBitByWid(wid);
			
			BaseBillDetail bbd = getBillDetail(bdid);			
			
			request.setAttribute("bbd", bbd);			
			request.setAttribute("bitlist", bitlist);
			request.setAttribute("billid", billid);		
			request.setAttribute("batch", batch);		
			request.setAttribute("wid", wid);
			request.setAttribute("isaudit", isaudit);
			request.setAttribute("flag", request.getParameter("flag"));
			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	protected abstract BaseBillDetail getBillDetail(int bdid) throws Exception;
}
