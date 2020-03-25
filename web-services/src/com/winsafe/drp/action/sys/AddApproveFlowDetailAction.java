package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppApproveFlowDetail;
import com.winsafe.drp.dao.ApproveFlowDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;

public class AddApproveFlowDetailAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    MakeCode mc = new MakeCode();
    //Session 
    ////Connection 
    

    try{
    	ApproveFlowDetail afd = new ApproveFlowDetail();
    	Integer afid = Integer.valueOf(mc.getExcIDByRandomTableName("approve_flow_detail",0,""));
      afd.setId(afid);
      afd.setAfid(request.getParameter("afid"));
      afd.setApproveid(Integer.valueOf(request.getParameter("approveid")));
      afd.setActid(Integer.valueOf(request.getParameter("actid")));
      afd.setApproveorder(Integer.valueOf(request.getParameter("approveorder")));

      AppApproveFlowDetail awd = new AppApproveFlowDetail();
     
      awd.addApproveFlowDetail(afd);

      
      request.setAttribute("result", "databases.add.success");
      UsersBean users = UserManager.getUser(request);
      Integer userid = users.getUserid();
      DBUserLog.addUserLog(userid,"新审阅流程详情");
      
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
