package com.winsafe.drp.action.self;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppTask;
import com.winsafe.drp.dao.AppTaskExecute;
import com.winsafe.drp.dao.TaskExecute;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.MakeCode;

public class AllotTaskAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    MakeCode mc = new MakeCode();
    String strtaskid = request.getParameter("taskid");
    Integer taskid = Integer.valueOf(strtaskid);
    String strspeed = request.getParameter("speedstr");
    int count = Integer.parseInt(request.getParameter("uscount"));

    try{
      AppTaskExecute atpe = new AppTaskExecute();
     atpe.delTaskPlanExecuteByTPID(taskid);

      StringTokenizer st = new StringTokenizer(strspeed, ",");
      AppTask atp= new AppTask();
      
      for(int p=0;p<count;p++){
        TaskExecute tpe = new TaskExecute();
        tpe.setId(Integer.valueOf(mc.getExcIDByRandomTableName("task_execute",0,"")));
        tpe.setTpid(taskid);
        tpe.setUserid(Integer.valueOf(st.nextToken().trim()));
        tpe.setIsaffirm(Integer.valueOf(0));
        //paa.setApprovedate(null);

        atpe.addExecute(tpe);
        
        atp.updIsAllot(taskid);
      }
      
      request.setAttribute("result", "databases.add.success");

      UsersBean users = UserManager.getUser(request);
      Integer userid = users.getUserid();
     // DBUserLog.addUserLog(userid,"分配任务");

      return mapping.findForward("allot");
    }catch(Exception e){

      e.printStackTrace();
    }finally {

    }

    return new ActionForward(mapping.getInput());
  }
}
