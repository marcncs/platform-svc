package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.Customer;
import com.winsafe.hbm.util.Internation;

public class ToAddLinkManAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			AppCustomer appCustomer = new AppCustomer();
			String cid = "";
			String cname="";
			cid = request.getParameter("cid");
			if(cid==null){
			cid=(String) request.getSession().getAttribute("cid");
			}

			if(cid!=null&& !cid.equals("")&&!cid.equals("null")){
			Customer customer = appCustomer.getCustomer(cid);
			cname = customer.getCname();
			}
			
			String sexselect = Internation.getSelectTagByKeyAll("Sex",request,"sex",false,null );
			String ismanselect=Internation.getSelectTagByKeyAll("YesOrNo",request,"ismain",false,null );
			
			request.getSession().setAttribute("cid", cid);
			request.setAttribute("sexselect", sexselect);
			request.setAttribute("ismanselect", ismanselect);
			request.setAttribute("cid", cid);
			request.setAttribute("cname", cname);
			return mapping.findForward("toadd");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return mapping.getInputForward();
	}

}
