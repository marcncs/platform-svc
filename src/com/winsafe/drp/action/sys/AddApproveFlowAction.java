package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppApproveFlow;
import com.winsafe.drp.dao.ApproveFlow;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;

public class AddApproveFlowAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    MakeCode mc = new MakeCode();


    try{
    	ApproveFlow af = new ApproveFlow();
      String afid = mc.getExcIDByRandomTableName("approve_flow",0,"");
      af.setId(afid);
      af.setFlowname(request.getParameter("flowname"));
      af.setMemo(request.getParameter("memo"));

      AppApproveFlow aw = new AppApproveFlow();
     
      aw.addApproveFlow(af);

      
      request.setAttribute("result", "databases.add.success");
      UsersBean users = UserManager.getUser(request);
      Integer userid = users.getUserid();
      DBUserLog.addUserLog(userid,"新增审阅流程");
      
      return mapping.findForward("addresult");
    }catch(Exception e){
      
      e.printStackTrace();
    }finally {
      //
      //  ConnectionEntityManager.close(conn);
    }

    return new ActionForward(mapping.getInput());
  }
}
