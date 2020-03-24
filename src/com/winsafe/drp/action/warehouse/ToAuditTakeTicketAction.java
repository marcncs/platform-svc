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
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.Warehouse;

public class ToAuditTakeTicketAction extends BaseAction
{
	private static Logger logger = Logger.getLogger(ToAuditTakeTicketAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//初始化
		String id = request.getParameter("id");
		initdata(request);
		AppTakeTicket aso = new AppTakeTicket();
		AppProduct ap = new AppProduct();
		AppTakeTicketDetail asld = new AppTakeTicketDetail();
		AppProductStockpileAll appPsp = new AppProductStockpileAll();
		AppFUnit appFunit = new AppFUnit();
		AppWarehouse aw = new AppWarehouse();
		
		try{
			
			TakeTicket so = aso.getTakeTicketById(id);
			if ( so == null ){
				request.setAttribute("result", "databases.record.delete");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			List<TakeTicketDetail> sals = asld.getTakeTicketDetailByTtid(id);
			// 用户页面显示物料号
			for(TakeTicketDetail ttd : sals){
				ttd.setNccode(ap.getProductByID(ttd.getProductid()).getmCode());
			}
			Warehouse outWarehouse = aw.getWarehouseByID(so.getWarehouseid());
			// 单据类型为渠道退货的不查检库存
			if(!so.getBillno().startsWith("OW")){
				// 如果仓库属性[是否负库存]为0,则检查库存
				if(outWarehouse.getIsMinusStock() == null || outWarehouse.getIsMinusStock() == 0){
					//查询库存
					for(TakeTicketDetail ttd : sals){
						Double stockQuantity = appPsp.getProductStockpileAllByProductID(ttd.getProductid(), Long.valueOf(so.getWarehouseid()));
						Double quantity = appFunit.getStockpileQuantity2(ttd.getProductid(), ttd.getUnitid(), stockQuantity);
						ttd.setStockQuantity(quantity);
					}
				}
			}
			// 负库存标识
			Integer isMinusStock = outWarehouse.getIsMinusStock();
			// 单据为渠道退货单据,则不检查库存
			if(so.getBillno().startsWith("OW")){
				isMinusStock = 1;
			}
            request.setAttribute("IsRead", so.getIsread());
			request.setAttribute("als", sals);
			request.setAttribute("sof", so);
			request.setAttribute("type", request.getParameter("type"));
			request.setAttribute("isMinusStock", isMinusStock);
			
			saveToken(request);
			
			return mapping.findForward("audit");
		} catch (Exception e) {
			logger.error("", e);
		}
		return new ActionForward(mapping.getInput());
	}
}
