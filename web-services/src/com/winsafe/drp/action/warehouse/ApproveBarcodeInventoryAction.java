package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBarcodeInventory;
import com.winsafe.drp.dao.BarcodeInventory;
import com.winsafe.drp.metadata.InventoryConfirmStatus;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;

public class ApproveBarcodeInventoryAction extends BaseAction {
	private Logger logger = Logger.getLogger(ApproveBarcodeInventoryAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		super.initdata(request);
		String id = request.getParameter("id");
		String isApprove = request.getParameter("isapprove");
		String reason = request.getParameter("reason");
		try {
			AppBarcodeInventory abi = new AppBarcodeInventory();
			BarcodeInventory bi = abi.getBarcodeInventoryByID(id);
			
			if (!InventoryConfirmStatus.NOT_AUDITED.getValue().equals(bi.getIsapprove()))
			{
				request.setAttribute("result", "审核失败,该单据已审核过");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			bi.setEndcasedate(DateUtil.getCurrentDate());
			bi.setEndcaseid(userid);
			if("1".equals(isApprove)) {
				bi.setIsapprove(InventoryConfirmStatus.APPROVED.getValue());
			} else {
				bi.setIsapprove(InventoryConfirmStatus.DISAPPROVE.getValue());
				bi.setRemark(reason);
			}
			abi.updBarcodeInventory(bi);
			
			request.setAttribute("result", "审批成功");
			
			DBUserLog.addUserLog(request, "编号：" + id);
			
			HibernateUtil.commitTransaction();
			return mapping.findForward("success");
		} catch (Exception e) {
			logger.error("ApproveBarcodeInventoryAction  error:", e);
			
		}
		return mapping.findForward("success");
	}

}