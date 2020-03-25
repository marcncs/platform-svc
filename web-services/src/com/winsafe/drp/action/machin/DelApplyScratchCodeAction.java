package com.winsafe.drp.action.machin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger; 
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.util.DBUserLog; 
import com.winsafe.erp.dao.AppApplyScratchCode;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.pojo.ApplyScratchCode;

public class DelApplyScratchCodeAction extends BaseAction {

	private AppApplyScratchCode appApplyQrCode = new AppApplyScratchCode();
	private static Logger logger = Logger.getLogger(DelApplyScratchCodeAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request); 

		try {
			Integer id = Integer.valueOf(request.getParameter("ID"));
			ApplyScratchCode aqr = appApplyQrCode.getApplyScratchCodeByID(Integer.valueOf(id));
			if(YesOrNo.YES.getValue() == aqr.getIsAudit()) {
				String result = "删除失败,该记录已审核";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			appApplyQrCode.deleteApplyScratchCode(id);
			DBUserLog.addUserLog(request, "编号：" + id);
			request.setAttribute("result", "databases.operator.success");
		} catch (Exception e) { 
			logger.error("DelUnitInfoAction  error:", e);
		}
		return mapping.findForward("success");
	}

}
