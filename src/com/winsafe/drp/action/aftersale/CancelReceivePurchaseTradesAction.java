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
import com.winsafe.drp.dao.AppPurchaseTrades;
import com.winsafe.drp.dao.AppPurchaseTradesIdcode;
import com.winsafe.drp.dao.PurchaseTrades;
import com.winsafe.drp.dao.PurchaseTradesIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class CancelReceivePurchaseTradesAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		try {
			String id = request.getParameter("id");
			AppPurchaseTrades apb = new AppPurchaseTrades();
			PurchaseTrades pb = apb.getPurchaseTradesByID(id);

			if (pb.getReceiveid() == 0) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if ( pb.getIsblankout() == 1 ){
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			
			AppPurchaseTradesIdcode appmi = new AppPurchaseTradesIdcode();
			List<PurchaseTradesIdcode> idcodelist = appmi.getPurchaseTradesIdcodeByptid(id, 1);
			AppIdcode appidcode = new AppIdcode();
			for ( PurchaseTradesIdcode ic : idcodelist ){
				if ( appidcode.getIdcodeById(ic.getIdcode(), 0) != null ){
					request.setAttribute("result", "databases.recode.idcodehasuse");
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}

			List<PurchaseTradesIdcode> alllist = appmi.getPurchaseTradesIdcodeByptid(id);
			
			returnProductStockpile(alllist, pb.getWarehouseinid());
			
			apb.updIsReceive(id, userid, 0);
			
						
			for ( PurchaseTradesIdcode ic : idcodelist ){
				appidcode.updIsUse(ic.getIdcode(), 0);
			}

			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(userid, 2, "采购换货>>取消回收采购换货,编号：" + id);

			return mapping.findForward("noaudit");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return new ActionForward(mapping.getInput());
	}
	
	private void returnProductStockpile(List<PurchaseTradesIdcode> idlist, String warehouseid) throws Exception{
		AppProductStockpile aps = new AppProductStockpile();	
		for (PurchaseTradesIdcode ic : idlist ) {
			
			aps.returninProductStockpile(ic.getProductid(), ic.getUnitid(), ic.getBatch(), ic.getQuantity(),
					warehouseid, ic.getWarehousebit(), ic.getPtid(), "取消回收采购换货-出库");
		}
	}


}
