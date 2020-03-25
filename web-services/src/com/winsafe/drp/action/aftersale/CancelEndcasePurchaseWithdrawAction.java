package com.winsafe.drp.action.aftersale;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.AppPayableDetail;
import com.winsafe.drp.dao.AppPurchaseWithdraw;
import com.winsafe.drp.dao.AppPurchaseWithdrawDetail;
import com.winsafe.drp.dao.Payable;
import com.winsafe.drp.dao.PurchaseWithdraw;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class CancelEndcasePurchaseWithdrawAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();		

		try {
			String id = request.getParameter("id");
			AppPurchaseWithdraw apb = new AppPurchaseWithdraw();	
			AppPurchaseWithdrawDetail apid = new AppPurchaseWithdrawDetail();		
			PurchaseWithdraw pb = apb.getPurchaseWithdrawByID(id);

			if (pb.getIsendcase() == 0) {
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			AppPayable appr = new AppPayable();
			AppPayableDetail apprd = new AppPayableDetail();
			List rlist = appr.getPayableByBillno(id);			

					
			for ( int i=0; i<rlist.size(); i++ ){
				Payable r = (Payable)rlist.get(i);
				String rid = r.getId();
				appr.delPayable(rid);
				apprd.delPayableDetailByPid(rid);
			}
			
			apb.updIsEndCase(id, userid, 0);
			
			apid.updIsSettlement(id, 0);

			request.setAttribute("result", "databases.audit.success");
			//DBUserLog.addUserLog(userid, "取消结案采购退货单,编号：" + id);

			return mapping.findForward("noaudit");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}
		return new ActionForward(mapping.getInput());
	}

}
