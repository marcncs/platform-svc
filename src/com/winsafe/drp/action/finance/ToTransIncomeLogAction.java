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
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.dao.ReceivableForm;
import com.winsafe.drp.dao.ReceivableObject;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ToTransIncomeLogAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		String rids = request.getParameter("rids");

		super.initdata(request);super.initdata(request);try{
			
			rids = rids.substring(0,rids.lastIndexOf(","));
			AppReceivable ara = new AppReceivable();
			List aprv = ara.getTransIncomeLogById(rids);
			ArrayList als = new ArrayList();
			Receivable rd = null;
			for (int i = 0; i < aprv.size(); i++) {
				rd = (Receivable) aprv.get(i);
				ReceivableForm rdf = new ReceivableForm();
				rdf.setId(rd.getId());
				rdf.setRoid(rd.getRoid());
				rdf.setReceivablesum(rd.getReceivablesum());
				rdf.setPaymentmode(rd.getPaymentmode());
				rdf.setPaymentmodename(Internation.getStringByPayPositionDB(rd.getPaymentmode()));
				rdf.setBillno(rd.getBillno());				
				rdf.setReceivabledescribe(rd.getReceivabledescribe());
				rdf.setAlreadysum(rd.getReceivablesum()-rd.getAlreadysum());				
				als.add(rdf);
			}
			
			String roid = (String)request.getSession().getAttribute("roid");
			AppReceivableObject appro = new AppReceivableObject();
			ReceivableObject ro = appro.getReceivableObjectByIDOrgID(roid,users.getMakeorganid());
			if(ro==null){
		          request.setAttribute("result","databases.record.nopurview");
		          return new ActionForward("/sys/lockrecordclose.jsp");
			}
			AppCashBank appcb = new AppCashBank();
		    List cblist = appcb.getAllCashBankByOID(users.getMakeorganid());
			
//		    String paymodeselect =Internation.getSelectTagByKeyAll("PaymentMode",
//		              request,
//		              "paymentmode", false,null);

//		    request.setAttribute("paymodeselect", paymodeselect);
			request.setAttribute("als", als);
			request.setAttribute("roid", roid);
			request.setAttribute("drawee", ro.getPayer());
			
			request.setAttribute("cblist", cblist);
			return mapping.findForward("totrans");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
