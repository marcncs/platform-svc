package com.winsafe.drp.action.purchase;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppBorrowIncome;
import com.winsafe.drp.dao.AppBorrowIncomeDetail;
import com.winsafe.drp.dao.AppBorrowIncomeIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.BorrowIncome;
import com.winsafe.drp.dao.BorrowIncomeDetail;
import com.winsafe.drp.dao.BorrowIncomeIdcode;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class ToAddBorrowIncomeIdcodeAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		// purchaseIncomeDetail id
		Long pidid = Long.parseLong(request.getParameter("pidid"));
		String batch = "";
		String piid = request.getParameter("piid");
		AppBorrowIncome apppi = new AppBorrowIncome();
		AppBorrowIncomeDetail appdip = new AppBorrowIncomeDetail();
		AppBorrowIncomeIdcode apppii = new AppBorrowIncomeIdcode();
		AppProduct appproduct = new AppProduct();

		try {
			BorrowIncome pi = apppi.getBorrowIncomeByID(piid);
			if ( pi.getIsaudit() == 0 ){
				String result = "databases.record.noaudit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			
			if ( pi.getIsbacktrack() == 1 ){
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			

			BorrowIncomeDetail pid = appdip.getBorrowIncomeDetailByID(pidid);
			Product p = appproduct.getProductByID(pid.getProductid());
			if (p.getIsidcode() == 0) {
				String result = "databases.record.noidcode";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			double quantity = pid.getQuantity();
			String[] idcode = new String[(int)quantity];
			for (int i=0; i<idcode.length; i ++){
				idcode[i]="";
			}
			List piilist = apppii.getBorrowIncomeIdcodeByPidBatch(pid.getProductid(), piid, batch);
			for ( int i=0; i<piilist.size(); i++){
				BorrowIncomeIdcode pii = (BorrowIncomeIdcode)piilist.get(i);
				idcode[i] = pii.getIdcode();
			}
			request.setAttribute("pid", pid);
			request.setAttribute("batch", batch);
			request.setAttribute("piid", piid);
			request.setAttribute("idcode", idcode);
			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
