package com.winsafe.erp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.erp.dao.AppBillImportConfig;
import com.winsafe.erp.pojo.BillImportConfig;
import com.winsafe.erp.util.Field;

public class SelectProductConfigAction extends BaseAction {

	private AppBillImportConfig abic = new AppBillImportConfig();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			String organId = request.getParameter("oid");
			String templateNo = request.getParameter("tno");
			List<BillImportConfig> bics = abic.getAllBillImportConfig(organId, templateNo);
			List<Field> fields = new ArrayList<Field>();
			if(bics != null && bics.size() > 0) {
				Set<String> bicSet = new HashSet<String>();
				for(BillImportConfig bic : bics) {
					bicSet.add(bic.getFieldName());
				}
				for(Field field : Field.values()) {
					if(!bicSet.contains(field.getFieldName())) {
						fields.add(field);
					}
				}
			} else {
				for(Field field : Field.values()) {
				    fields.add(field);
				}
			}
			request.setAttribute("fields", fields);
			return mapping.findForward("selectfields");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
