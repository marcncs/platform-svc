package com.winsafe.drp.action.purchase;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppPurchasePlan;
import com.winsafe.drp.dao.AppPurchasePlanDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.PurchasePlan;
import com.winsafe.drp.dao.PurchasePlanDetailForm;
import com.winsafe.drp.dao.PurchasePlanForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ToApprovePurchasePlanAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String id = request.getParameter("ID");
    String actid = request.getParameter("actid");

    try {
    	if(DbUtil.judgeApproveStatusToApprover("PurchasePlan", id)){
      	  String result = "databases.record.isapprove";
  			request.setAttribute("result", result);
  			return new ActionForward("/sys/lockrecord.jsp");
        }
        if(DbUtil.judgeApproveStatusToApproverVeto("PurchasePlan", id)){
      	  String result = "databases.record.isveto";
  			request.setAttribute("result", result);
  			return new ActionForward("/sys/lockrecord.jsp");
        }
      AppPurchasePlan apa = new AppPurchasePlan();
      PurchasePlan pp = new PurchasePlan();
      AppDept ad = new AppDept();
      AppUsers au = new AppUsers();
      pp = apa.getPurchasePlanByID(id);
      PurchasePlanForm ppf = new PurchasePlanForm();
      ppf.setId(id);
      ppf.setPlandate(pp.getPlandate());
      ppf.setPlandeptname(ad.getDeptByID(pp.getPlandept()).getDeptname());
      ppf.setPlanidname(au.getUsersByid(pp.getPlanid()).getRealname());
//      ppf.setIsrefername(Internation.getStringByKeyPosition("IsRefer",
//            request,
//            pp.getIsrefer(), "global.sys.SystemResource"));
//      ppf.setApprovestatusname(Internation.getStringByKeyPosition("ApproveStatus",
//            request,
//            pp.getApprovestatus(), "global.sys.SystemResource"));
//      ppf.setApprovedate(pp.getApprovedate());
      ppf.setIscompletename(Internation.getStringByKeyPosition("YesOrNo",
            request,
            pp.getIscomplete(), "global.sys.SystemResource"));
      ppf.setRemark(pp.getRemark());
      ppf.setIsaudit(pp.getIsaudit());
      ppf.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
            request,
            pp.getIsaudit(), "global.sys.SystemResource"));

      if(pp.getAuditid()>0){
    	  ppf.setAuditidname(au.getUsersByid(pp.getAuditid()).getRealname());
      }else{
    	  ppf.setAuditidname("");
      }
      ppf.setAuditdate(pp.getAuditdate());
      ppf.setIsratify(pp.getIsratify());
      ppf.setIsratifyname(Internation.getStringByKeyPosition("YesOrNo",
              request,
              pp.getIsratify(), "global.sys.SystemResource"));

        if(pp.getRatifyid()>0){
        	ppf.setRatifyidname(au.getUsersByid(pp.getRatifyid()).getRealname());
        }else{
        	ppf.setRatifyidname("");
        }
        ppf.setRatifydate(pp.getRatifydate());

      AppPurchasePlanDetail apad = new AppPurchasePlanDetail();
      List padls = apad.getPurchasePlanDetailByPaID(id);
      ArrayList als = new ArrayList();
      AppProduct ap = new AppProduct();

      for(int i=0;i<padls.size();i++){
          PurchasePlanDetailForm ppdf = new PurchasePlanDetailForm();
          Object[] o = (Object[])padls.get(i);
          ppdf.setProductid(o[2].toString());
          ppdf.setProductname(o[3].toString());
          ppdf.setSpecmode(o[4].toString());
          //padf.setUnitid(Integer.valueOf(o[3].toString()));
          ppdf.setUnitname(Internation.getStringByKeyPositionDB("CountUnit",             
              Integer.parseInt(o[5].toString())));
          ppdf.setUnitprice(Double.valueOf(o[6].toString()));
          ppdf.setQuantity(Double.valueOf(o[7].toString()));
          ppdf.setRequiredate(DateUtil.formatDate((Date)o[9]));
          ppdf.setAdvicedate(DateUtil.formatDate((Date)o[10]));
          ppdf.setRequireexplain(o[11].toString());
          als.add(ppdf);
        }

      String approvestatus = Internation.getSelectTagByKeyAll("SubApproveStatus", request,
            "ApproveStatus", "1", null);
      String stractid=Internation.getStringByKeyPositionDB("ActID",
              Integer.valueOf(actid));

      request.setAttribute("approvestatus",approvestatus);
      request.setAttribute("ppid",id);
      request.setAttribute("actid", actid);
      request.setAttribute("stractid", stractid);
      request.setAttribute("als",als);
      request.setAttribute("ppf",ppf);

      return mapping.findForward("toapprove");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
