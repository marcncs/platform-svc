package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Product; 
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.dao.AppQualityInspection;
import com.winsafe.sap.pojo.QualityInspection;

/**
 * @author : jerry
 * @version : 2009-9-4 下午03:09:40 www.winsafe.cn
 */
public class ToUpdQualityInspectionAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppQualityInspection appQI = new AppQualityInspection();
		AppProduct appProduct = new AppProduct();
		Integer id = Integer.valueOf(request.getParameter("id"));
		QualityInspection qi = appQI.getQualityInspectionById(id);
		Product product = appProduct.getByMCode(qi.getmCode());
		request.setAttribute("qi", qi);
		request.setAttribute("idate", DateUtil.formatDate(qi.getInspectDate()));
		request.setAttribute("p", product);
		return mapping.findForward("upload");
	}
}
