package com.winsafe.erp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger; 
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.DBUserLog; 
import com.winsafe.erp.dao.AppApplyQrCode;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.pojo.ApplyQrCode;

public class DelApplyQrCodeAction extends BaseAction {

	private AppApplyQrCode appApplyQrCode = new AppApplyQrCode();
	private static Logger logger = Logger.getLogger(DelApplyQrCodeAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request);

		try {
			Integer id = Integer.valueOf(request.getParameter("ID"));
			ApplyQrCode aqr = appApplyQrCode.getApplyQrCodeByID(Integer.valueOf(id));
			if(YesOrNo.YES.getValue() == aqr.getIsAudit()) {
				String result = "删除失败,该记录已审核";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			appApplyQrCode.deleteApplyQrCode(id);
			DBUserLog.addUserLog(request, "编号：" + id);
			request.setAttribute("result", "databases.operator.success");
		} catch (Exception e) { 
			logger.error("DelUnitInfoAction  error:", e);
		}
		return mapping.findForward("success");
	}

}
