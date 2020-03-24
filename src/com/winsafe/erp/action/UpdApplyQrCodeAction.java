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
 
public class UpdApplyQrCodeAction extends BaseAction {

	private static Logger logger = Logger.getLogger(UpdApplyQrCodeAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		initdata(request);
		AppApplyQrCode appAqc = new AppApplyQrCode();
		try {
			String id = request.getParameter("ID");
			String organid = request.getParameter("organid");
			String productid = request.getParameter("ProductID");
			Integer quantity = Integer.parseInt(request.getParameter("quantity"));
			Integer redundancy = Integer.parseInt(request.getParameter("redundancy"));
			String pono = request.getParameter("pono");
			Integer needCovertCode = Integer.parseInt(request.getParameter("needCovertCode"));

			ApplyQrCode aqr = appAqc.getApplyQrCodeByID(Integer.valueOf(id));
			if(YesOrNo.YES.getValue() == aqr.getIsAudit()) {
				String result = "修改失败,该记录已审核";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			aqr.setOrganId(organid);
			aqr.setProductId(productid);
			aqr.setQuantity(quantity);
			aqr.setRedundance(redundancy);
			aqr.setPono(pono);
			aqr.setNeedCovertCode(needCovertCode);
			appAqc.updApplyQrCode(aqr);
			
			DBUserLog.addUserLog(request, "编号：" + id); 
			request.setAttribute("result", "databases.operator.success");
		} catch (Exception e) {
			logger.error("UpdApplyQrCodeAction  error:", e);
		}
		return mapping.findForward("success");
	}
}