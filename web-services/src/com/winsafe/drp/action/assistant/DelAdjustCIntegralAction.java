package com.winsafe.drp.action.assistant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AdjustCIntegral;
import com.winsafe.drp.dao.AppAdjustCIntegral;
import com.winsafe.drp.dao.AppAdjustCIntegralDetail;
import com.winsafe.drp.dao.AppCIntegralDeal;
import com.winsafe.drp.dao.AppOIntegralDeal;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class DelAdjustCIntegralAction
    extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {


    try {

      UsersBean users = UserManager.getUser(request);
//      Long userid = users.getUserid();

      String id = request.getParameter("id");
      
      AppAdjustCIntegral ar = new AppAdjustCIntegral();
      AdjustCIntegral r = ar.getAdjustCIntegralByID(id);
      if (r.getIsaudit() == 1 ) {
			String result = "databases.record.isapprovenooperator";
	        request.setAttribute("result",result);
			return new ActionForward("/sys/lockrecordclose.jsp");
		}
      
      AppAdjustCIntegralDetail appad = new AppAdjustCIntegralDetail();
      AppCIntegralDeal appcid = new AppCIntegralDeal();
      AppOIntegralDeal appoid = new AppOIntegralDeal();
      ar.delAdjustCIntegral(id);
      appad.delDetailByAccid(id);
      appcid.delCIntegralDeal(id);
      appoid.delOIntegralDeal(id);

      request.setAttribute("result", "databases.del.success");
//      DBUserLog.addUserLog(userid,"删除会员积分调整单");//日志 

      return mapping.findForward("success");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return mapping.getInputForward();
  }

}
