package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.DBUserLog;  
import com.winsafe.erp.dao.AppApplyQrCode;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.pojo.ApplyQrCodeHz;

public class AuditApplyQrCodeAction extends BaseAction {

	private AppApplyQrCode appApplyQrCode = new AppApplyQrCode();
	private static Logger logger = Logger.getLogger(AuditApplyQrCodeAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request); 

		try {
			Integer id = Integer.valueOf(request.getParameter("ID"));
			ApplyQrCodeHz aqr = appApplyQrCode.getApplyQrCodeHzByID(Integer.valueOf(id));
			if(YesOrNo.YES.getValue() == aqr.getIsAudit()) {
				String result = "审批失败,该记录已审批过";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			aqr.setIsAudit(YesOrNo.YES.getValue());
			aqr.setAuditDate(DateUtil.getCurrentDate());
			aqr.setAuditId(userid);
			//appApplyQrCode.updApplyQrCodeHz(aqr);
			DBUserLog.addUserLog(request, "编号：" + id); 
			request.setAttribute("result", "databases.operator.success");
		} catch (Exception e) {
			logger.error("AuditApplyQrCodeAction  error:", e);
		}
		return mapping.findForward("success");
	}

}
