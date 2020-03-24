package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.server.StockAlterMoveService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

public class AuditStockAlterMoveAction extends BaseAction
{
	private Logger logger = Logger.getLogger(AuditStockAlterMoveAction.class);

	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//初始化
		initdata(request);
		StockAlterMoveService sams = new StockAlterMoveService();
		AppStockAlterMove api = new AppStockAlterMove();
		AppStockAlterMoveDetail asamd = new AppStockAlterMoveDetail();
		AppProductStockpileAll appps = new AppProductStockpileAll();
		AppFUnit appfu = new AppFUnit();
		AppWarehouse aw = new AppWarehouse();
		
		try
		{
			String smid = request.getParameter("SMID");
			StockAlterMove sam = api.getStockAlterMoveByID(smid);
			// 单据是否存在
			if(sam == null){
				request.setAttribute("result", "databases.record.delete");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			// 单据是否复核
			if (sam.getIsaudit() == 1)
			{
				request.setAttribute("result", "databases.record.audit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			// 单据是作废
			if (sam.getIsblankout() == 1)
			{
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

//			if (!sam.getMakeorganid().equals(users.getMakeorganid()))
//			{
//				request.setAttribute("result", "databases.record.nopurview");
//				return new ActionForward("/sys/lockrecordclose.jsp");
//			}

			List<StockAlterMoveDetail> samsList = asamd.getStockAlterMoveDetailBySamID(smid);
			
			// 如果仓库属性[是否负库存]为0,则检查库存
			Warehouse outWarehouse = aw.getWarehouseByID(sam.getOutwarehouseid());
			StringBuffer errorMsg = new StringBuffer();
			if(outWarehouse.getIsMinusStock() == null || outWarehouse.getIsMinusStock() == 0){
				for (StockAlterMoveDetail sod : samsList)
				{
					double q = appfu.getQuantity(sod.getProductid(), sod.getUnitid(), sod.getQuantity());
					double stock = appps.getProductStockpileAllByPIDWID(sod.getProductid(), sam.getOutwarehouseid());
					if (q > stock)
					{
						errorMsg.append("产品 [ " + sod.getProductid() + " " + sod.getProductname() + " ] 库存不足<br/>");
					}
				}
			}
			if(errorMsg.length() > 0){
				request.setAttribute("result", errorMsg.toString() + "<br/>不能复核!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			//新增tt表
			sams.addTakeTicket(sam, samsList, users);

			sam.setAuditdate(DateUtil.getCurrentDate());
			sam.setAuditid(userid);
			sam.setIsaudit(1);
			sam.setIsmove(0);
			api.updstockAlterMove(sam);
			
			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(request, "编号:" + smid);

			return mapping.findForward("success");
		}
		catch(Exception e)
		{
			logger.error("", e);
			throw e;
		}
	}

	
}