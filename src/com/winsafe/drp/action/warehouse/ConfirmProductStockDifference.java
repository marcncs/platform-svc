package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AmountInventory;
import com.winsafe.drp.dao.AppAmountInventory;
import com.winsafe.drp.metadata.InventoryConfirmStatus;
import com.winsafe.hbm.util.DateUtil;

/**
 * 处理复核数据盘点
 * 
 * @author mengnan.xie
 * 
 */
public class ConfirmProductStockDifference extends BaseAction {
	private Logger logger = Logger
			.getLogger(ConfirmProductStockDifference.class);
	private AppAmountInventory api = new AppAmountInventory();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);

		try {
			String id = request.getParameter("id");
			String isApprove = request.getParameter("isapprove");
			String reason = request.getParameter("reason");
			AmountInventory ai = api.getAmountInventoryByID(id);
			
			if (!InventoryConfirmStatus.NOT_AUDITED.getValue().equals(ai.getIsapprove()))
			{
				request.setAttribute("result", "审核失败,该单据已审核过");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			ai.setEndcasedate(DateUtil.getCurrentDate());
			ai.setEndcaseid(userid);
			if("1".equals(isApprove)) {
				ai.setIsapprove(InventoryConfirmStatus.APPROVED.getValue());
			} else {
				ai.setIsapprove(InventoryConfirmStatus.DISAPPROVE.getValue());
				ai.setRemark(reason);
			}
			
			api.updAmountInventory(ai);

			request.setAttribute("result", "审核成功");
		} catch (Exception e) {
			logger.error("ConfirmProductStockDifference  error:", e);
		}
		return mapping.findForward("success");
	}
}