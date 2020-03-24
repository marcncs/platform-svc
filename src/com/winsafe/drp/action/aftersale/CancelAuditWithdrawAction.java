package com.winsafe.drp.action.aftersale;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCIntegralDeal;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppOIntegralDeal;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.AppWithdraw;
import com.winsafe.drp.dao.AppWithdrawIdcode;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Withdraw;
import com.winsafe.drp.dao.WithdrawIdcode;
import com.winsafe.drp.util.DBUserLog;

public class CancelAuditWithdrawAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		String smid = request.getParameter("ID");
		try {
			AppWithdraw asm = new AppWithdraw();
			Withdraw sm = asm.getWithdrawByID(smid);

			
			if (sm.getIsaudit() == 0) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if ( sm.getIsblankout() == 1 ){
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			
			AppReceivable appr = new AppReceivable();
			List<Receivable> rlist = appr.getReceivableByBillno(sm.getId());
			
			for ( Receivable r : rlist ){
				if ( r.getAlreadysum() > 0 ){
					request.setAttribute("result", "对不起，该单据已经收款不能取消！");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}		
			
			AppWithdrawIdcode appmi = new AppWithdrawIdcode();
			List<WithdrawIdcode> idcodelist = appmi.getWithdrawIdcodeBywid(smid, 1);
			AppIdcode appidcode = new AppIdcode();
			for ( WithdrawIdcode ic : idcodelist ){
				if ( appidcode.getIdcodeById(ic.getIdcode(), 0) != null ){
					request.setAttribute("result", "databases.recode.idcodehasuse");
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}


			List<WithdrawIdcode> alllist = appmi.getWithdrawIdcodeBywid(smid);
			
			returnProductStockpile(alllist, sm.getWarehouseid());
			
			asm.updIsAudit(smid, userid, 0);
						
			for ( WithdrawIdcode ic : idcodelist ){
				appidcode.updIsUse(ic.getIdcode(), 0);
			}
			
			for ( Receivable r : rlist ){				
				appr.delReceivable(r.getId());
			}
			
			AppCIntegralDeal apci = new AppCIntegralDeal();
			AppOIntegralDeal apoi = new AppOIntegralDeal();
			
			apci.delCIntegralDeal(sm.getId());			
			
			apoi.delOIntegralDeal(sm.getId());	

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 6,"销售退货>>取消复核销售退货,编号：" + smid);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	private void returnProductStockpile(List<WithdrawIdcode> idlist, String warehouseid) throws Exception{
		AppProductStockpile aps = new AppProductStockpile();	
		for (WithdrawIdcode ic : idlist ) {
			
			aps.returninProductStockpile(ic.getProductid(), ic.getUnitid(), ic.getBatch(), ic.getQuantity(),
					warehouseid, ic.getWarehousebit(), ic.getWid(), "取消复核销售退货-出库");
		}
	}

}
