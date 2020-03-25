package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppSupplySaleMove;
import com.winsafe.drp.dao.AppSupplySaleMoveIdcode;
import com.winsafe.drp.dao.Payable;
import com.winsafe.drp.dao.SupplySaleMove;
import com.winsafe.drp.dao.SupplySaleMoveIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class CancelCompleteSupplySaleMoveReceiveAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		String smid = request.getParameter("ID");
		super.initdata(request);try{
			AppSupplySaleMove asm = new AppSupplySaleMove();
			SupplySaleMove sm = asm.getSupplySaleMoveByID(smid);

			
			if (sm.getIscomplete() == 0) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			if (!sm.getInorganid().equals(users.getMakeorganid())) { 
				request.setAttribute("result", "databases.record.nopurview");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			
			AppPayable appr = new AppPayable();
			List<Payable> rlist = appr.getPayableByBillno(smid, sm.getInorganid());
			for ( Payable r : rlist ){
				if ( r.getAlreadysum() > 0 ){
					request.setAttribute("result", "对不起，该单据已经付款不能取消！");
					return new ActionForward("/sys/lockrecordclose2.jsp");
				}
			}
			
			AppSupplySaleMoveIdcode appmi = new AppSupplySaleMoveIdcode();
			List<SupplySaleMoveIdcode> idcodelist = appmi.getSupplySaleMoveIdcodeByssmid(smid, 1);
			AppIdcode appidcode = new AppIdcode();
			for ( SupplySaleMoveIdcode ic : idcodelist ){
				if ( appidcode.getIdcodeById(ic.getIdcode(), 0) != null ){
					request.setAttribute("result", "databases.recode.idcodehasuse");
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}


			List<SupplySaleMoveIdcode> alllist = appmi.getSupplySaleMoveIdcodeByssmid(smid);
			
			returnProductStockpile(alllist, sm.getInwarehouseid());
			
			asm.updSupplySaleMoveIsComplete(smid, 0, userid);
						
			for ( SupplySaleMoveIdcode ic : idcodelist ){
				appidcode.updIsUse(ic.getIdcode(), 0);
			}
			
			for ( Payable r : rlist ){				
				appr.delPayable(r.getId());
			}

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 7,"入库>>代销签收>>取消签收代销单,编号：" + smid);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	private void returnProductStockpile(List<SupplySaleMoveIdcode> idlist, String warehouseid) throws Exception{
		AppProductStockpile aps = new AppProductStockpile();	
		for (SupplySaleMoveIdcode ic : idlist ) {
			
			aps.returninProductStockpile(ic.getProductid(), ic.getUnitid(), ic.getBatch(), ic.getQuantity(),
					warehouseid, ic.getWarehousebit(), ic.getSsmid(), "取消签收代销单");
		}
	}

}
