package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCIntegral;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.Customer;
import com.winsafe.hbm.util.Internation;

public class CalledCenterAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		super.initdata(request);
		try {
			String cid = request.getParameter("cid");
			String tel = request.getParameter("tel");
			cid=cid.trim();
			
			
			AppCustomer ac = new AppCustomer();
			Customer customer = ac.getCustomer(cid);
			AppCIntegral appci = new AppCIntegral();
			
			String ratename="";
			String sex = "";
			double integral = 0.00;
			if ( customer != null ){
//				ratename=Internation.getStringByKeyPositionDB("PricePolicy",
//						customer.getRate());
				sex = Internation.getStringByKeyPosition("Sex",
						request, customer.getMembersex(), "global.sys.SystemResource");
				integral = appci.getCIntegralByCID(cid);
			}
			
			request.setAttribute("customer", customer);
			request.setAttribute("sex", sex);
			request.setAttribute("integral", integral);
			request.setAttribute("tel", tel);

			return mapping.findForward("show");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
