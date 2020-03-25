package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppLoanOut;
import com.winsafe.drp.dao.AppLoanOutDetail;
import com.winsafe.drp.dao.AppLoanOutIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.LoanOut;
import com.winsafe.drp.dao.LoanOutDetail;
import com.winsafe.drp.dao.LoanOutIdcode;
import com.winsafe.drp.dao.Product;

public class ToAddLoanOutIdcodeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
//		Long userid = users.getUserid();
		// purchaseIncomeDetail id
		Long pidid = Long.parseLong(request.getParameter("pidid"));
		String batch = "";
		String piid = request.getParameter("piid");
		AppLoanOut apppi = new AppLoanOut();
		AppLoanOutDetail appdip = new AppLoanOutDetail();
		AppLoanOutIdcode apppii = new AppLoanOutIdcode();
		AppProduct appproduct = new AppProduct();

		try {
			LoanOut pi = apppi.getLoanOutByID(piid);
			if ( pi.getIsaudit() == 0 ){
				String result = "databases.record.noaudit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			
			if ( pi.getIsreceive() == 1 ){
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			

			LoanOutDetail pid = appdip.getLoanOutDetailByID(pidid);
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
			List piilist = apppii.getLoanOutIdcodeByPidBatch(pid.getProductid(), piid, batch);
			for ( int i=0; i<piilist.size(); i++){
				LoanOutIdcode pii = (LoanOutIdcode)piilist.get(i);
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
