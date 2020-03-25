package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppPurchaseIncome;
import com.winsafe.drp.dao.AppPurchaseIncomeIdcode;
import com.winsafe.drp.dao.PurchaseIncome;
import com.winsafe.drp.dao.PurchaseIncomeIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class CancelAuditPurchaseIncomeAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();

		super.initdata(request);try{
			String piid = request.getParameter("PIID");
			AppPurchaseIncome apb = new AppPurchaseIncome();
			AppPurchaseIncomeIdcode apidcode = new AppPurchaseIncomeIdcode();
			PurchaseIncome pb = apb.getPurchaseIncomeByID(piid);

			if (pb.getIsaudit() == 0) {
				request.setAttribute("result", "datebases.record.returnoperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			if (pb.getIstally() == 1) {
				request.setAttribute("result", "对不起，该单据已记帐不能取消！");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}

			
			
			List<PurchaseIncomeIdcode> idcodelist = apidcode.getPurchaseIncomeIdcodeByPiid(piid, 1);
			AppIdcode appidcode = new AppIdcode();
			for ( PurchaseIncomeIdcode ic : idcodelist ){
				if ( appidcode.getIdcodeById(ic.getIdcode(), 0) != null ){
					request.setAttribute("result", "databases.recode.idcodehasuse");
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}
			
			for ( PurchaseIncomeIdcode ic : idcodelist ){				
				appidcode.delIdcodeByid(ic.getIdcode());
			}

			AppProductStockpile aps = new AppProductStockpile();
			List<PurchaseIncomeIdcode> piilist = apidcode.getPurchaseIncomeIdcodeByPiid(piid);
			for ( PurchaseIncomeIdcode pii : piilist) {
				
				aps.returninProductStockpile(pii.getProductid(),pii.getUnitid(),pii.getBatch(),pii.getQuantity(),pb.getWarehouseid(),pii.getWarehousebit(), piid, "取消采购入库-出货");
			}

			 apb.updIsAudit(piid, userid, 0);
			
			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 7,"入库>>采购入库>>取消复核采购入库,编号：" + piid);
			return mapping.findForward("noaudit");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return new ActionForward(mapping.getInput());
	}

}
