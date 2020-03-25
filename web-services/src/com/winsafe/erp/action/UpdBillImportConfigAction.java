package com.winsafe.erp.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.erp.dao.AppBillImportConfig;
import com.winsafe.erp.pojo.BillImportConfig;

public class UpdBillImportConfigAction extends BaseAction {

	private AppBillImportConfig abic = new AppBillImportConfig();
	private static Logger logger = Logger.getLogger(UpdBillImportConfigAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request);

		try {
			Map map = new HashMap(request.getParameterMap());
			Integer bicid = Integer.parseInt(request.getParameter("bicid"));
			BillImportConfig billconfig = abic.getBillImportConfigByID(bicid);
			
			String[] fieldNames = request.getParameterValues("fieldName");
			
			if(fieldNames != null) {
				for (int i = 0; i < fieldNames.length; i++) {
					BillImportConfig bic = abic.getBillImportConfig(billconfig.getOrganId(), billconfig.getTemplateNo(), fieldNames[i]);
					bic.setColumnName((String)map.get(fieldNames[i]+"_columnName"));
					bic.setDefaultValue((String)map.get(fieldNames[i]+"_defaultValue"));
					abic.updBillImportConfig(bic);
				}
			}
			
			
			DBUserLog.addUserLog(request, "机构编号："+billconfig.getOrganId()+", 模板编号："+billconfig.getTemplateNo());
			request.setAttribute("result", "更新成功");
		} catch (Exception e) {
			logger.error("UpdBillImportConfigAction  error:", e);
		}
		return mapping.findForward("success");
	}

}