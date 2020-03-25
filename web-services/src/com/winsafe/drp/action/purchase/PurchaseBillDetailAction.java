package com.winsafe.drp.action.purchase;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppPurchaseBill;
import com.winsafe.drp.dao.AppPurchaseBillDetail;
import com.winsafe.drp.dao.PurchaseBill;
import com.winsafe.drp.dao.PurchaseBillForm;

public class PurchaseBillDetailAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String id = request.getParameter("ID");

    try{
    	AppInvoiceConf aic = new AppInvoiceConf();
      AppPurchaseBill apb = new AppPurchaseBill();     

      PurchaseBill pb = apb.getPurchaseBillByID(id);
      PurchaseBillForm pbf= new PurchaseBillForm();
      pbf.setId(id);
      pbf.setPpid(pb.getPpid());
      pbf.setPid(pb.getPid());
      pbf.setPname(pb.getPname());
      pbf.setPlinkman(pb.getPlinkman());
      pbf.setTel(pb.getTel());
      pbf.setPurchasedept(pb.getPurchasedept());
      pbf.setPurchaseid(pb.getPurchaseid()); 
      pbf.setTotalsum(pb.getTotalsum());
      pbf.setReceivedate(pb.getReceivedate());
      pbf.setReceiveaddr(pb.getReceiveaddr());     
      pbf.setPaymode(pb.getPaymode());

      pbf.setInvmsgname(aic.getInvoiceConfById(pb.getInvmsg().intValue())
					.getIvname());
      pbf.setMakeid(pb.getMakeid());
      pbf.setMakedate(pb.getMakedate());
      pbf.setRemark(pb.getRemark());
      pbf.setIstransferadsum(pb.getIstransferadsum());
      pbf.setIsaudit(pb.getIsaudit());
      pbf.setAuditid(pb.getAuditid());
      pbf.setAuditdate(pb.getAuditdate());
      pbf.setIsratify(pb.getIsratify());
      pbf.setRatifyid(pb.getRatifyid());
       
      pbf.setRatifydate(pb.getRatifydate());     
      

      AppPurchaseBillDetail apbd = new AppPurchaseBillDetail();
      List padls = apbd.getPurchaseBillDetailByPbID(id);
     


      request.setAttribute("als",padls);
      request.setAttribute("pbf",pbf);
      return mapping.findForward("detail");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
