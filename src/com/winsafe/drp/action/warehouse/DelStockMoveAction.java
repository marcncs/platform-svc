package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.AppStockMoveDetail;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.util.DBUserLog;

public class DelStockMoveAction extends BaseAction {
	private static Logger logger = Logger.getLogger(DelStockMoveAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		AppStockMove asb = new AppStockMove();
		AppStockMoveDetail asbd = new AppStockMoveDetail();
		try {

			String smid = request.getParameter("ID");
			StockMove sb = asb.getStockMoveByID(smid);
			// 单据是否存在
			if(sb == null){
				request.setAttribute("result", "databases.record.delete");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			// 单据是否复核
			if (sb.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.nodel");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			asb.delStockMove(smid);
			asbd.delStockMoveDetailBySmID(smid);

			request.setAttribute("result", "databases.del.success");
			DBUserLog.addUserLog(request,"编号:" + smid);

			return mapping.findForward("del");
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		} finally {

		}
	}

}
