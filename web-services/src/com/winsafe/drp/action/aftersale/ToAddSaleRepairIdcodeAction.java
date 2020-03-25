package com.winsafe.drp.action.aftersale;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppSaleRepair;
import com.winsafe.drp.dao.AppSaleRepairDetail;
import com.winsafe.drp.dao.AppSaleRepairIdcode;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.SaleRepair;
import com.winsafe.drp.dao.SaleRepairDetail;
import com.winsafe.drp.dao.SaleRepairIdcode;

public class ToAddSaleRepairIdcodeAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// purchaseIncomeDetail id
		Long pidid = Long.parseLong(request.getParameter("pidid"));
		String batch = "";
		String piid = request.getParameter("piid");
		AppSaleRepair apppi = new AppSaleRepair();
		AppSaleRepairDetail appdip = new AppSaleRepairDetail();
		AppSaleRepairIdcode apppii = new AppSaleRepairIdcode();
		AppProduct appproduct = new AppProduct();

		try {
			SaleRepair pi = apppi.getSaleRepairByID(piid);
			if ( pi.getIsaudit() == 1 ){
				String result = "databases.record.isapprovenooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			
			if ( pi.getIsblankout() == 1 ){
				String result = "databases.record.isapprovenooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}

			SaleRepairDetail pid = appdip.getSaleRepairDetailByID(pidid);
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
			List piilist = apppii.getSaleRepairIdcodeByPidBatch(pid.getProductid(), piid, batch);
			for ( int i=0; i<piilist.size(); i++){
				SaleRepairIdcode pii = (SaleRepairIdcode)piilist.get(i);
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
