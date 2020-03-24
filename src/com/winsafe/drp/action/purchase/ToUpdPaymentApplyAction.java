package com.winsafe.drp.action.purchase;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppPaymentApply;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PaymentApply;
import com.winsafe.drp.dao.PaymentApplyForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ToUpdPaymentApplyAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    Integer id = Integer.valueOf(request.getParameter("ID"));
    UsersBean users = UserManager.getUser(request);
    Integer userid = users.getUserid();
    try{
    	
    	
    	AppPaymentApply apa = new AppPaymentApply();
        PaymentApply pa = new PaymentApply();
        AppUsers au = new AppUsers();
        AppCustomer apv = new AppCustomer();
        AppDept ad = new AppDept();
        pa = apa.getPaymentApplyByID(id);
        
        if (pa.getIsendcase() == 1) { 
            String result = "databases.record.lock";
            request.setAttribute("result", result);
            return mapping.findForward("lock");
          }
        
        PaymentApplyForm pbf= new PaymentApplyForm();
        pbf.setId(id);
        pbf.setPid(pa.getPid());
        pbf.setPname(pa.getPname());
        pbf.setPlinkman(pa.getPlinkman());
        pbf.setTel(pa.getTel());
        pbf.setPurchasedept(pa.getPurchasedept());
        pbf.setPurchasedeptname(ad.getDeptByID(pa.getPurchasedept()).getDeptname());
        pbf.setBillno(pa.getBillno());
        pbf.setTotalsum(pa.getTotalsum());
        pbf.setPaymentmodename(Internation.getSelectPayAllDBDef("paymentmode",pa.getPaymentmode()));
        pbf.setPurchaseid(pa.getPurchaseid());
        pbf.setBankaccount(pa.getBankaccount());
        pbf.setBankname(pa.getBankname());
        pbf.setDoorname(pa.getDoorname());
        pbf.setMakeidname(au.getUsersByid(pa.getMakeid()).getRealname());
        pbf.setMakedate(String.valueOf(pa.getMakedate()));

        request.setAttribute("pbf",pbf);
        
        List aldept = ad.getDeptByOID(users.getMakeorganid());
		
		
		// AppUsers au = new AppUsers();
			List als = au.getIDAndLoginNameByOID2(users.getMakeorganid());
			
		request.setAttribute("aldept", aldept);
		request.setAttribute("als", als);

      return mapping.findForward("toupd");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
