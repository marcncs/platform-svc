package com.winsafe.drp.action.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFwmCreateCode;
import com.winsafe.drp.dao.FwmCreate;

public class ToMarubiBarcodeLogging extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception {
	super.initdata(request);
	try {
		int pagesize = 10;
		String where = " where f.atype = 1 ";
		String codeSearch = request.getParameter("codeSearch");
		String clientnameSearch = request.getParameter("clientnameSearch");
		String productCodeSearch = request.getParameter("productCodeSearch");
		String fwmnumSearch = request.getParameter("fwmnumSearch");
		if(null != codeSearch && !"".equals(codeSearch)){
			request.setAttribute("codeSearch", codeSearch);
			where += " and f.fwmCodeFile like '%"+codeSearch+"%' ";
		}
		if(null != clientnameSearch && !"".equals(clientnameSearch)){
			request.setAttribute("clientnameSearch", clientnameSearch);
			where += " and f.fwmClientName like '%"+clientnameSearch+"%' ";
		}
		if(null != productCodeSearch && !"".equals(productCodeSearch)){
			request.setAttribute("productCodeSearch", productCodeSearch);
			where += " and f.fwmProductCode like '%"+productCodeSearch+"%' ";
		}
		if(null != fwmnumSearch && !"".equals(fwmnumSearch)){
			request.setAttribute("fwmnumSearch", fwmnumSearch);
			where += " and f.fwmNum like '%"+fwmnumSearch+"%' ";
		}
		AppFwmCreateCode afcc = new AppFwmCreateCode();
		//得到防伪码生成的记录集合, 1代表丸美
		List<FwmCreate> fwmList = afcc.selectFwm(request, pagesize, where);
		request.setAttribute("fwmList", fwmList);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return mapping.findForward("list");
}
}
