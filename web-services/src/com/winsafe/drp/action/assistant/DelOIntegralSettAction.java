package com.winsafe.drp.action.assistant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOIntegralDeal;
import com.winsafe.drp.dao.AppOIntegralSett;
import com.winsafe.drp.dao.OIntegralSett;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class DelOIntegralSettAction
    extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {


    try {

      UsersBean users = UserManager.getUser(request);
//      Long userid = users.getUserid();

      String id = request.getParameter("id");
      
      AppOIntegralSett ar = new AppOIntegralSett();
      OIntegralSett r = ar.getOIntegralSettByID(id);
      if (r.getIsaudit() == 1 ) {
			String result = "databases.record.isapprovenooperator";
	        request.setAttribute("result",result);
			return new ActionForward("/sys/lockrecordclose.jsp");
		}
      
      AppOIntegralDeal appcid = new AppOIntegralDeal();
      ar.delOIntegralSett(id);
      appcid.delOIntegralDeal(id);

      request.setAttribute("result", "databases.del.success");
//      DBUserLog.addUserLog(userid,"删除机构积分结算单");//日志 

      return mapping.findForward("success");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return mapping.getInputForward();
  }

}
