package com.winsafe.drp.action.assistant;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppIdcodeDetail;
import com.winsafe.drp.dao.AppIdcodeReset;
import com.winsafe.drp.dao.AppIdcodeResetDetail;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.IdcodeDetail;
import com.winsafe.drp.dao.IdcodeReset;
import com.winsafe.drp.dao.IdcodeResetDetail;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class ToAddIdcodeResetIdcodeAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		// purchaseIncomeDetail id
		Long pidid = Long.parseLong(request.getParameter("pidid"));
		String batch = "";
		String piid = request.getParameter("piid");
		AppIdcodeReset apppi = new AppIdcodeReset();
		AppIdcodeResetDetail appdip = new AppIdcodeResetDetail();
		AppIdcodeDetail apppii = new AppIdcodeDetail();
		AppProduct appproduct = new AppProduct();

		try {
			IdcodeReset pi = apppi.getIdcodeResetById(piid);
			if ( pi.getIsaudit() == 1 ){
				request.setAttribute("result", "databases.record.isapprovenooperator");
				return new ActionForward("/sys/lockrecord.jsp");
			}

			IdcodeResetDetail pid = appdip
					.getIdcodeResetDetailById(pidid);
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
			List piilist = apppii.getIdcodeDetailByPidBillid(pid.getProductid(), piid);
			for ( int i=0; i<piilist.size(); i++){
				IdcodeDetail pii = (IdcodeDetail)piilist.get(i);
				idcode[i] = pii.getIdcode();
			}
			String productcode = p.getProductcode();
			if ( p.getProductcode()== null || p.getProductcode().equals("") ){
				productcode = p.getProductcodedef();
			}
			request.setAttribute("pid", pid);
			request.setAttribute("batch", batch);
			request.setAttribute("piid", piid);
			request.setAttribute("idcode", idcode);
			request.setAttribute("productcode", productcode);
			request.setAttribute("startindex", apppii.getIdcodeStartIndex(pid.getProductid(), productcode));
			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
