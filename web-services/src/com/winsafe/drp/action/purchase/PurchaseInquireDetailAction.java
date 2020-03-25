package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppPurchaseInquire;
import com.winsafe.drp.dao.AppPurchaseInquireDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PurchaseInquire;
import com.winsafe.drp.dao.PurchaseInquireDetailForm;
import com.winsafe.drp.dao.PurchaseInquireForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class PurchaseInquireDetailAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String strid = request.getParameter("ID");
    Integer id = Integer.valueOf(strid);

    try{
      AppPurchaseInquire apb = new AppPurchaseInquire();     
      AppUsers au = new AppUsers();
      AppProvider apv = new AppProvider();
      PurchaseInquire pb = apb.getPurchaseInquireByID(id);
      PurchaseInquireForm pif= new PurchaseInquireForm();
      pif.setId(id);
      pif.setPpid(pb.getPpid());
      pif.setInquiretitle(pb.getInquiretitle());
      pif.setProvidename(apv.getProviderByID(pb.getPid()).getPname());
      pif.setPlinkman(pb.getPlinkman());
      pif.setMakeid(pb.getMakeid());
      pif.setMakedate(pb.getMakedate());
      pif.setIsaudit(pb.getIsaudit());
      pif.setAuditid(pb.getAuditid());
  
      pif.setAuditdate(pb.getAuditdate());
      pif.setValiddate(pb.getValiddate());
      pif.setRemark(pb.getRemark());
      
      AppPurchaseInquireDetail apid = new AppPurchaseInquireDetail();
      List padls = apid.getPurchaseInquireDetailByPiID(id);
      ArrayList als = new ArrayList();
      for(int i=0;i<padls.size();i++){
        PurchaseInquireDetailForm pidf = new PurchaseInquireDetailForm();
        Object[] o = (Object[])padls.get(i);
        pidf.setProductid(o[2].toString());
        //pidf.setProductname(ap.getProductByID(pidf.getProductid()).getProductname());
        pidf.setProductname(o[3].toString());
        pidf.setSpecmode(o[4].toString());
        pidf.setUnitname(Internation.getStringByKeyPositionDB("CountUnit",            
            Integer.parseInt(o[5].toString())));
        pidf.setUnitprice(Double.valueOf(o[6].toString()));
        pidf.setQuantity(Double.valueOf(o[7].toString()));
        pidf.setSubsum(Double.valueOf(o[8].toString()));
        als.add(pidf);
      }

      request.setAttribute("als",als);
      request.setAttribute("pif",pif);
      UsersBean users = UserManager.getUser(request);
      Integer userid = users.getUserid();
      //DBUserLog.addUserLog(userid,"采购询价详情");
      return mapping.findForward("detail");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
