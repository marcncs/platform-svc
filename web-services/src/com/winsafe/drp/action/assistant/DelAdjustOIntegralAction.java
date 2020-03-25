package com.winsafe.drp.action.assistant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AdjustOIntegral;
import com.winsafe.drp.dao.AppAdjustOIntegral;
import com.winsafe.drp.dao.AppAdjustOIntegralDetail;
import com.winsafe.drp.dao.AppOIntegralDeal;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class DelAdjustOIntegralAction
    extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {


    try {

      UsersBean users = UserManager.getUser(request);
//      Long userid = users.getUserid();

      String id = request.getParameter("id");
      
      AppAdjustOIntegral ar = new AppAdjustOIntegral();
      AdjustOIntegral r = ar.getAdjustOIntegralByID(id);
      if (r.getIsaudit() == 1 ) {
			String result = "databases.record.isapprovenooperator";
	        request.setAttribute("result",result);
			return new ActionForward("/sys/lockrecordclose.jsp");
		}
      
      AppAdjustOIntegralDetail appad = new AppAdjustOIntegralDetail();
      AppOIntegralDeal appcid = new AppOIntegralDeal();
      ar.delAdjustOIntegral(id);
      appad.delDetailByAocid(id);
      appcid.delOIntegralDeal(id);

      request.setAttribute("result", "databases.del.success");
//      DBUserLog.addUserLog(userid,"删除机构积分调整单");//日志 

      return mapping.findForward("success");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return mapping.getInputForward();
  }

}
