package com.winsafe.erp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.erp.dao.AppProductConfig;
import com.winsafe.erp.pojo.ProductConfig;

public class UpdProductConfigAction extends BaseAction {

	private AppProductConfig appProductConfig = new AppProductConfig();
	private static Logger logger = Logger.getLogger(UpdProductConfigAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request);

		try {
			Integer id = Integer.valueOf(request.getParameter("id"));
			
			String organId = request.getParameter("organid");
			String erpProductId = request.getParameter("productId");
			String mcode = request.getParameter("mcode");
			String productid = request.getParameter("productid");

			ProductConfig productConfig = appProductConfig.getProductConfigByOidAndMid(organId, mcode);
			if (productConfig != null && productConfig.getId() != id) {
				request.setAttribute("result", "该机构下的物料号已经存在！修改失败！");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			productConfig = appProductConfig.getProductConfigByID(id);
			productConfig.setOrganId(organId);
			productConfig.setProductId(productid);
			productConfig.setmCode(mcode);
			productConfig.setErpProductId(erpProductId);
			
			appProductConfig.updProductConfig(productConfig);
			
			DBUserLog.addUserLog(request, "编号：" + id);
			request.setAttribute("result", "更新成功");
		} catch (Exception e) {
			logger.error("UpdProductConfigAction  error:", e);
		}
		return mapping.findForward("success");
	}

}