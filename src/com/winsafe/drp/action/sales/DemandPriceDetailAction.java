package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppDemandPrice;
import com.winsafe.drp.dao.AppDemandPriceDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.DemandPrice;
import com.winsafe.drp.dao.DemandPriceDetailForm;
import com.winsafe.drp.dao.DemandPriceForm;
import com.winsafe.hbm.util.Internation;

public class DemandPriceDetailAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    Long id = Long.valueOf(request.getParameter("ID"));

    try{
      AppDemandPrice aso = new AppDemandPrice();    
      AppUsers au = new AppUsers();
      AppCustomer ac = new AppCustomer();
      DemandPrice so = aso.getDemandPriceByID(id);
      DemandPriceForm sof= new DemandPriceForm();
     
      sof.setId(id);
      sof.setCid(so.getCid());
      sof.setCidname(ac.getCustomer(so.getCid()).getCname());
      sof.setLinkman(so.getLinkman());
      sof.setTel(so.getTel());
      sof.setDemandname(so.getDemandname());
//      sof.setMakeidname(au.getUsersByid(so.getMakeid()).getRealname());
      sof.setMakedate(so.getMakedate().toString());
      sof.setTotalsum(so.getTotalsum());
      sof.setRemark(so.getRemark());
      sof.setIsaudit(so.getIsaudit());
      sof.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
            request,
            so.getIsaudit(), "global.sys.SystemResource"));

      if(so.getAuditid()>0){
//    	 sof.setAuditidname(au.getUsersByid(so.getAuditid()).getRealname());
      }else{
    	  sof.setAuditidname("");
      }
      sof.setAuditdate(so.getAuditdate());
      sof.setIsorvername(Internation.getStringByKeyPosition("YesOrNo",
            request,
            so.getIsover(), "global.sys.SystemResource"));


      AppDemandPriceDetail asld = new AppDemandPriceDetail();
      List sals = asld.getDemandPriceDetailByDpID(id);
      ArrayList als = new ArrayList();
      for(int i=0;i<sals.size();i++){
    	  DemandPriceDetailForm sldf = new DemandPriceDetailForm();
        Object[] o = (Object[])sals.get(i);
        sldf.setProductid(o[2].toString());
        sldf.setProductname(String.valueOf(o[3]));
        sldf.setSpecmode(String.valueOf(o[4]));
        //padf.setUnitid(Integer.valueOf(o[3].toString()));
        sldf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit",           
            Integer.parseInt(o[5].toString())));
        sldf.setUnitprice(Double.valueOf(o[6].toString()));
        sldf.setQuantity(Double.valueOf(o[7].toString()));
        sldf.setSubsum(Double.valueOf(o[8].toString()));
        sldf.setDiscount(Double.valueOf(o[9].toString()));
        sldf.setTaxrate(Double.valueOf(o[10].toString()));
        als.add(sldf);
      }

      request.setAttribute("als",als);
      request.setAttribute("sof",sof);
      
//      Long userid = users.getUserid();
//      DBUserLog.addUserLog(userid,"销售报价详细"); 
      return mapping.findForward("detail");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
