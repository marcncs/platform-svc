package com.winsafe.drp.action.warehouse;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBarcodeInventoryIdcode;
import com.winsafe.drp.dao.AppTakeTicketIdcode;

public class GetBarcodeQuantityAjax extends BaseAction{
	AppBarcodeInventoryIdcode abii = new AppBarcodeInventoryIdcode();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		super.initdata(request);
		try{

			String productId = request.getParameter("productId");
			String billNo = request.getParameter("billNo");
			String batch = request.getParameter("batchNo");
			Integer codeQty = abii.getBarcodeQuantity(billNo, productId,batch);
						
			JSONObject json = new JSONObject();			
			json.put("codeQty", codeQty);				
			response.setContentType("text/html; charset=UTF-8");
		    response.setHeader("Cache-Control", "no-cache");
		    PrintWriter out = response.getWriter();
		    out.print(json.toString());
		    out.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
