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
 
public class UpdApplyScratchCodeAction extends BaseAction {

	private static Logger logger = Logger.getLogger(UpdApplyScratchCodeAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request); 
		AppApplyScratchCode appAqc = new AppApplyScratchCode();
		try {
			String id = request.getParameter("ID");
			String organid = request.getParameter("organid");
			Integer quantity = Integer.parseInt(request.getParameter("quantity"));
			Integer redundancy = Integer.parseInt(request.getParameter("redundancy"));
			String pono = request.getParameter("pono");

			ApplyScratchCode aqr = appAqc.getApplyScratchCodeByID(Integer.valueOf(id));
			if(YesOrNo.YES.getValue() == aqr.getIsAudit()) {
				String result = "修改失败,该记录已审核";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			aqr.setOrganId(organid);
			aqr.setQuantity(quantity);
			aqr.setRedundance(redundancy);
			aqr.setPono(pono);
			appAqc.updApplyScratchCode(aqr);
			
			DBUserLog.addUserLog(request, "编号：" + id); 
			request.setAttribute("result", "databases.operator.success");
		} catch (Exception e) {
			logger.error("UpdApplyScratchCodeAction  error:", e);
		}
		return mapping.findForward("success");
	}
}