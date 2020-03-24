package com.winsafe.drp.action.finance;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.AppPayableObject;
import com.winsafe.drp.dao.Payable;
import com.winsafe.drp.dao.PayableForm;
import com.winsafe.drp.dao.PayableObject;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.PaymentModeService;

public class ToTransPaymentLogAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		String rids = request.getParameter("rids");

		super.initdata(request);super.initdata(request);try{
			
			rids = rids.substring(0,rids.lastIndexOf(","));
			
			AppPayable ara = new AppPayable();
			List aprv = ara.getTransPaymentLogById(rids);
			ArrayList als = new ArrayList();
			PaymentModeService pms = new PaymentModeService();
			Payable rd = null;
			for (int i = 0; i < aprv.size(); i++) {
				rd = (Payable) aprv.get(i);
				PayableForm rdf = new PayableForm();
				rdf.setId(rd.getId());
				rdf.setPoid(rd.getPoid());
				rdf.setPayablesum(rd.getPayablesum());
				rdf.setPaymode(rd.getPaymode());
				rdf.setPaymodename(pms.getPaymentModeName(rd.getPaymode()));						
				rdf.setBillno(rd.getBillno());				
				rdf.setPayabledescribe(rd.getPayabledescribe());
				rdf.setAlreadysum(rd.getPayablesum()-rd.getAlreadysum());				
				als.add(rdf);
			}
			
			String poid = (String)request.getSession().getAttribute("poid");
			AppPayableObject appro = new AppPayableObject();
			PayableObject ro = appro.getPayableObjectByOIDOrgID(poid,users.getMakeorganid());
			if(ro==null){
		          request.setAttribute("result","databases.record.nopurview");
		          return new ActionForward("/sys/lockrecordclose.jsp");
			}
			AppCashBank appcb = new AppCashBank();
		    List cblist = appcb.getAllCashBank();
			

			request.setAttribute("als", als);
			request.setAttribute("poid", poid);
			request.setAttribute("payee", ro.getPayee());
			
			request.setAttribute("cblist", cblist);
			return mapping.findForward("totrans");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
