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
import com.winsafe.erp.metadata.QrStatus;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.metadata.SyncStatus;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.pojo.ApplyQrCodeHz;

public class AddApplyQrCodeAction extends BaseAction {

	private static Logger logger = Logger.getLogger(AddApplyQrCodeAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppApplyQrCode appAqc = new AppApplyQrCode();
		initdata(request);
		try {
			String organid = request.getParameter("organid");
			String productid = request.getParameter("ProductID");
			Integer quantity = Integer.parseInt(request.getParameter("quantity"));
			Integer redundancy = Integer.parseInt(request.getParameter("redundancy"));
			String pono = request.getParameter("pono");
			Integer needCovertCode = Integer.parseInt(request.getParameter("needCovertCode"));
			
//			int totalQty = quantity + redundancy*redundancy/100; 
			
			ApplyQrCodeHz aqr = new ApplyQrCodeHz();
			
			aqr.setOrganId(organid); 
			aqr.setProductId(productid);
			aqr.setQuantity(quantity);
			aqr.setRedundance(redundancy);
			aqr.setIsAudit(YesOrNo.YES.getValue());
			aqr.setMakeId(userid);
			aqr.setMakeDate(DateUtil.getCurrentDate());
			aqr.setStatus(QrStatus.NOT_GENERATED.getValue());
			aqr.setNeedCovertCode(needCovertCode);  
			aqr.setPono(pono);
			aqr.setSyncStatus(SyncStatus.NOT_UPLOADED.getValue());
			
			appAqc.addApplyQrCodeHz(aqr);
			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(request, "编号：" + aqr.getId());
		} catch (Exception e) { 
			logger.error("", e);
		}
		return mapping.findForward("success");
	}

}
