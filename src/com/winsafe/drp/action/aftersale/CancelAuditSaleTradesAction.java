package com.winsafe.drp.action.aftersale;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppSaleTrades;
import com.winsafe.drp.dao.AppSaleTradesIdcode;
import com.winsafe.drp.dao.SaleTrades;
import com.winsafe.drp.dao.SaleTradesIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class CancelAuditSaleTradesAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();		

		try {
			String id = request.getParameter("id");
			AppSaleTrades apb = new AppSaleTrades();
			SaleTrades pb = apb.getSaleTradesByID(id);

			if (pb.getIsblankout() == 1) {
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (pb.getIsaudit() == 0) {
				request.setAttribute("result", "databases.record.noaudit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			
			AppSaleTradesIdcode appmi = new AppSaleTradesIdcode();
			List<SaleTradesIdcode> idcodelist = appmi.getSaleTradesIdcodeBystid(id, 1);
			AppIdcode appidcode = new AppIdcode();
			for ( SaleTradesIdcode ic : idcodelist ){
				if ( appidcode.getIdcodeById(ic.getIdcode(), 0) != null ){
					request.setAttribute("result", "databases.recode.idcodehasuse");
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}


			List<SaleTradesIdcode> alllist = appmi.getSaleTradesIdcodeBystid(id);
			
			returnProductStockpile(alllist, pb.getWarehouseinid());
						
			for ( SaleTradesIdcode ic : idcodelist ){
				appidcode.updIsUse(ic.getIdcode(), 0);
			}
			
			apb.updIsAudit(id, userid, 0);
			
			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, 6, "销售退货>>取消复核销售退货,编号：" + id);

			return mapping.findForward("noaudit");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return new ActionForward(mapping.getInput());
	}
	
	private void returnProductStockpile(List<SaleTradesIdcode> idlist, String warehouseid) throws Exception{
		AppProductStockpile aps = new AppProductStockpile();	
		for (SaleTradesIdcode ic : idlist ) {
			
			aps.returninProductStockpile(ic.getProductid(), ic.getUnitid(), ic.getBatch(), ic.getQuantity(),
					warehouseid, ic.getWarehousebit(), ic.getStid(), "取消签收销售换货-出库");
		}
	}

}
