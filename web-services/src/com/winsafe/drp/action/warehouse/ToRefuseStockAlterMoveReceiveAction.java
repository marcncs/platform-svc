package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.StockAlterMove;

public class ToRefuseStockAlterMoveReceiveAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		AppStockAlterMove asm = new AppStockAlterMove();
		AppStockAlterMoveDetail asmd = new AppStockAlterMoveDetail();
		
		try {
			String id = request.getParameter("OMID");
			StockAlterMove sm = asm.getStockAlterMoveByID(id);
			
			request.setAttribute("smid", id);
			request.setAttribute("smf", sm);
			
			return mapping.findForward("tocomplete");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}

}
