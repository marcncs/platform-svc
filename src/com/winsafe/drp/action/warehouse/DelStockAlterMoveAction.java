package com.winsafe.drp.action.warehouse;

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
import com.winsafe.drp.util.DBUserLog;

public class DelStockAlterMoveAction extends BaseAction {
	private Logger logger = Logger.getLogger(DelStockAlterMoveAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		AppStockAlterMove asb = new AppStockAlterMove();
		AppStockAlterMoveDetail asbd = new AppStockAlterMoveDetail();
		
		try {

			String smid = request.getParameter("ID");
			StockAlterMove sb = asb.getStockAlterMoveByID(smid);
			// 单据是否存在
			if(sb == null){
				request.setAttribute("result", "databases.record.delete");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			// 单据是否复核
			if (sb.getIsaudit() == 1) {
				String result = "databases.record.nodel";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			asbd.delStockAlterMoveDetailBySamid(smid);
			asb.delStockAlterMove(smid);

			request.setAttribute("result", "databases.del.success");
			DBUserLog.addUserLog(request, "编号：" + smid, sb);

			return mapping.findForward("success");
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		}
	}

}
