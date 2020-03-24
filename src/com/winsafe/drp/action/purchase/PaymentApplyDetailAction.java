package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppPaymentApply;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PaymentApply;
import com.winsafe.drp.dao.PaymentApplyForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class PaymentApplyDetailAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    Integer id = Integer.valueOf(request.getParameter("ID"));

    try{
      AppPaymentApply apa = new AppPaymentApply();
      PaymentApply pa = new PaymentApply();
      AppUsers au = new AppUsers();
      //AppCustomer apv = new AppCustomer();
      AppDept ad = new AppDept();
      pa = apa.getPaymentApplyByID(id);
      PaymentApplyForm pbf= new PaymentApplyForm();
      pbf.setId(id);
      pbf.setPname(pa.getPname());
      pbf.setPlinkman(pa.getPlinkman());
      pbf.setTel(pa.getTel());
      pbf.setPurchasedeptname(ad.getDeptByID(pa.getPurchasedept()).getDeptname());
      pbf.setBillno(pa.getBillno());
      pbf.setTotalsum(pa.getTotalsum());
      pbf.setPaymentmodename(Internation.getStringByKeyPosition("PaymentMode",
					request, pa.getPaymentmode(), "global.sys.SystemResource"));
      pbf.setPurchaseidname(au.getUsersByid(pa.getPurchaseid()).getRealname());
      pbf.setBankaccount(pa.getBankaccount());
      pbf.setDoorname(pa.getDoorname());
      pbf.setBankname(pa.getBankname());
      pbf.setMakeidname(au.getUsersByid(pa.getMakeid()).getRealname());
      pbf.setMakedate(String.valueOf(pa.getMakedate()));
      pbf.setIsendcase(pa.getIsendcase());
      pbf.setIsendcasename(Internation.getStringByKeyPosition("YesOrNo",
              request,
              pa.getIsendcase(), "global.sys.SystemResource"));
      pbf.setEndcaseid(pa.getEndcaseid());
      if(pa.getEndcaseid()>0){
      pbf.setEndcaseidname(au.getUsersByid(pa.getEndcaseid()).getRealname());
      }
      else{
    	  pbf.setEndcaseidname("");
      }
      pbf.setEndcasedate(String.valueOf(pa.getEndcasedate()));

      request.setAttribute("pbf",pbf);
      UsersBean users = UserManager.getUser(request);
      Integer userid = users.getUserid();
      //DBUserLog.addUserLog(userid,"付款申请详情");
      return mapping.findForward("detail");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
