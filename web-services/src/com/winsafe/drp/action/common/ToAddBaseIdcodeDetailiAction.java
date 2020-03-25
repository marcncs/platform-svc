package com.winsafe.drp.action.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.BaseBillDetail;
import com.winsafe.drp.dao.Product;


public abstract class ToAddBaseIdcodeDetailiAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String billid = request.getParameter("billid");
		
		int bdid = Integer.valueOf(request.getParameter("bdid"));
		
		String wid = request.getParameter("wid");
		
		String isaudit = request.getParameter("isaudit");
		AppWarehouse aw = new AppWarehouse();
		AppProduct ap = new AppProduct();
		try{
			
			
			BaseBillDetail bbd = getBillDetail(bdid);
			
			List idcodelist = getIdcodeList(bbd, billid);
			
			List bitlist = aw.getWarehouseBitByWid(wid);
			Product p = ap.getProductByID(bbd.getProductid());
			
			request.setAttribute("bitlist", bitlist);
			request.setAttribute("idcodelist", idcodelist);
			request.setAttribute("billid", billid);
			request.setAttribute("bbd", bbd);
			request.setAttribute("wid", wid);
			request.setAttribute("isaudit", isaudit);
			request.setAttribute("isbatch", p.getIsbatch());
			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	protected abstract BaseBillDetail getBillDetail(int bdid) throws Exception;
	
	protected abstract List getIdcodeList(BaseBillDetail bbd, String billid) throws Exception;
}
