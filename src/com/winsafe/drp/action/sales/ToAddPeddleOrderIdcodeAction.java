package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Product;

public class ToAddPeddleOrderIdcodeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
//		Long userid = users.getUserid();
		String pid = request.getParameter("pid");
		String strquantity = request.getParameter("qt");
		String codes = request.getParameter("codes");
		AppProduct appproduct = new AppProduct();

		try {
			
			Product p = appproduct.getProductByID(pid);
			if (p.getIsidcode() == 0) {
				String result = "databases.record.noidcode";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			double quantity = Double.valueOf(strquantity);
			String[] idcode = new String[(int)quantity];
			for (int i=0; i<idcode.length; i ++){
				idcode[i]="";
			}
			String[] strcodes = codes.split(",");
			for ( int i=0; i<strcodes.length; i++){
				if ( i+1 > idcode.length ){
					break;
				}
				idcode[i] = strcodes[i];
			}
			request.setAttribute("pid", p);
			request.setAttribute("idcode", idcode);
			request.setAttribute("productcode", p.getProductcode());
			request.setAttribute("rowx", request.getParameter("rowx"));
			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
