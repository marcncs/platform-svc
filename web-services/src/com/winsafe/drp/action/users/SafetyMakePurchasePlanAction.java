package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganAnnunciator;
import com.winsafe.drp.dao.AppPurchasePlan;
import com.winsafe.drp.dao.AppPurchasePlanDetail;
import com.winsafe.drp.dao.PurchasePlan;
import com.winsafe.drp.dao.PurchasePlanDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class SafetyMakePurchasePlanAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    UsersBean users = UserManager.getUser(request);
    Integer userid = users.getUserid();


    
    String strproductid[] = request.getParameterValues("productid");
    String strproductname[] = request.getParameterValues("productname");
    String strspecmode[]= request.getParameterValues("specmode");
    String strunitid[] = request.getParameterValues("unitid");
    String strquantity[] = request.getParameterValues("quantity");

    
    String productid;
    Integer unitid;
    Double unitprice=0d, quantity;
    String productname,specmode,requiredate,advicedate,requireexplain;

    try {
      PurchasePlan pp = new PurchasePlan();
      String ppid = MakeCode.getExcIDByRandomTableName("purchase_plan",2,"PP");
      pp.setId(ppid);
      pp.setBillno("");
      pp.setPurchasesort(0);
      pp.setPlandate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
      pp.setPlandept(users.getMakedeptid());
      pp.setPlanid(userid);
//      pp.setIsrefer(0);
//      pp.setApprovestatus(0);
      pp.setMakeorganid(users.getMakeorganid());
      pp.setMakeid(userid);
      pp.setIsaudit(0);
      pp.setAuditid(Integer.valueOf(0));
      pp.setIsratify(0);
      pp.setRatifyid(Integer.valueOf(0));
      pp.setIscomplete(0);
      pp.setRemark("报警提醒生成采购计划");
      
      AppPurchasePlan apa = new AppPurchasePlan();
      apa.addPurchasePlan(pp);

      AppPurchasePlanDetail apad = new AppPurchasePlanDetail();
      for (int i = 0; i < strproductid.length; i++) {
        productid = strproductid[i];
        productname = strproductname[i];
        specmode = strspecmode[i];
        unitid = Integer.valueOf(strunitid[i]);
        
        if (DataValidate.IsDouble(strquantity[i])) {
          quantity = Double.valueOf(strquantity[i]);
        }
        else {
          quantity = Double.valueOf(0);
        }


        PurchasePlanDetail ppd = new PurchasePlanDetail();
        ppd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("purchase_plan_detail",0,"")));
        ppd.setPpid(ppid);
        ppd.setProductid(productid);
        ppd.setProductname(productname);
        ppd.setSpecmode(specmode);
        ppd.setUnitid(unitid);
        ppd.setUnitprice(unitprice);
        ppd.setQuantity(quantity);
        ppd.setChangequantity(Double.valueOf(0));
        ppd.setRequiredate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
        ppd.setAdvicedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
        ppd.setRequireexplain("");
        //pad.setSubsum(subsum);   
        if(quantity>0){
        	apad.addPurchasePlanDetail(ppd);
        }
      }
      
      AppOrganAnnunciator awa = new AppOrganAnnunciator();
		awa.updOrganAnnumciator(users.getMakeorganid(), userid);

      request.setAttribute("result", "databases.add.success");
      //DBUserLog.addUserLog(userid,"报警提醒转采购计划");
      
      return mapping.findForward("addresult");
    }
    catch (Exception e) {
      
      e.printStackTrace();
    }

    return new ActionForward(mapping.getInput());
  }
}
