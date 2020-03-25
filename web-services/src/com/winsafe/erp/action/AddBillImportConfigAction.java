package com.winsafe.erp.action;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.erp.dao.AppBillImportConfig;
import com.winsafe.erp.pojo.BillImportConfig;

public class AddBillImportConfigAction extends BaseAction{
	
	private AppBillImportConfig abic = new AppBillImportConfig();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		
		Map map = new HashMap(request.getParameterMap());
		String organId = request.getParameter("organid");
		String templateNo = request.getParameter("templateno");
		
		String[] fieldNames = request.getParameterValues("fieldName");
		
		if(fieldNames != null) {
			for (int i = 0; i < fieldNames.length; i++) {
				BillImportConfig bic = new BillImportConfig();
				bic.setOrganId(organId);
				bic.setTemplateNo(templateNo);
				bic.setFieldName(fieldNames[i]);
				bic.setColumnName((String)map.get(fieldNames[i]+"_columnName"));
				bic.setDefaultValue((String)map.get(fieldNames[i]+"_defaultValue"));
				abic.AddBillImportConfig(bic);
			}
		}
		request.setAttribute("result", "新增成功！");
		return mapping.findForward("success");
	}
		
}
