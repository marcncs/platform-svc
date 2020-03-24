package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.util.DBUserLog;

public class ToTransferStockAlterMoveAction extends BaseAction {
	
	private static Logger logger = Logger.getLogger(ToTransferStockAlterMoveAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		AppStockAlterMove asm = new AppStockAlterMove();
		AppStockAlterMoveDetail asmd = new AppStockAlterMoveDetail();
		
		try {
			String id = request.getParameter("samid");
			StockAlterMove sm = asm.getStockAlterMoveByID(id);
			List<StockAlterMoveDetail> spils = asmd.getUsefulStockAlterMoveDetailBySamID(id);
			if(sm.getInwarehouseid() == null) {
				request.setAttribute("isSeedCustomer", 1);
			}
			request.setAttribute("smid", id);
			request.setAttribute("als", spils);
			request.setAttribute("smf", sm);
			request.setAttribute("type", request.getParameter("type"));
			DBUserLog.addUserLog(request, "列表");
			return mapping.findForward("result");
		} catch (Exception e) {
			logger.error("", e);
		}

		return new ActionForward(mapping.getInput());
	}

}
