package com.winsafe.drp.action.warehouse;

import java.util.List;

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
import com.winsafe.drp.dao.StockMoveDetail;
import com.winsafe.drp.server.StockMoveService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

public class AuditStockMoveAction extends BaseAction {
	private static Logger logger = Logger.getLogger(AuditStockMoveAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		AppStockMove api = new AppStockMove();
		AppStockMoveDetail asamd = new AppStockMoveDetail();
		StockMoveService smService = new StockMoveService();
		
		
		try {
			String smid = request.getParameter("SMID");
			StockMove sm = api.getStockMoveByID(smid);
			// 单据是否存在
			if(sm == null){
				request.setAttribute("result", "databases.record.delete");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			// 是否复核过
			if (sm.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.audit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			// 是否作废  1为作废
			if (sm.getIsblankout() == 1) {
				request.setAttribute("result",
						"databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
//			// 不是同一制单机构
//			if (!sm.getMakeorganid().equals(users.getMakeorganid())) {
//				request.setAttribute("result", "databases.record.nopurview");
//				return new ActionForward("/sys/lockrecordclose.jsp");
//			}
			List<StockMoveDetail> smdList = asamd.getStockMoveDetailBySmIDNew(smid);
			//判断库存
			StringBuffer errorMsg = new StringBuffer();
			if(!smService.checkStock(sm, smdList,errorMsg)){
				request.setAttribute("result", errorMsg.toString() + "<br/>不能复核!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			//增加检货出库单据
			smService.addTakeTicket(sm, smdList, users);
			//更新单据为复核状态
			sm.setIsaudit(1);
			sm.setAuditid(userid);
			sm.setAuditdate(DateUtil.getCurrentDate());
			api.updstockMove(sm);

			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(request, "编号：" + smid);

			return mapping.findForward("audit");

		} catch (Exception e) {
			logger.error("", e);
			throw e;
		}
	}
}
