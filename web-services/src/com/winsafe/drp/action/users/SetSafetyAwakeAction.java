package com.winsafe.drp.action.users;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganAwake;
import com.winsafe.drp.dao.OrganAwake;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;

public class SetSafetyAwakeAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String oid=request.getParameter("oid");

    String strspeed = request.getParameter("speedstr");
    int count = Integer.parseInt(request.getParameter("uscount"));

    try{
      AppOrganAwake awv = new AppOrganAwake();
      awv.delOrganAwakeByOID(oid);

      StringTokenizer st = new StringTokenizer(strspeed, ",");      
      for(int p=0;p<count;p++){
    	  OrganAwake wv = new OrganAwake();
    	  wv.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("organ_awake",0,"")));
    	  wv.setOrganid(oid);
    	  wv.setUserid(Integer.valueOf(st.nextToken().trim()));

        awv.addOrganAwake(wv);
        
      }
     
      request.setAttribute("result", "databases.add.success");

      UsersBean users = UserManager.getUser(request);
      Integer userid = users.getUserid();
      DBUserLog.addUserLog(userid,11,"机构设置>>设置报警提醒人,机构编号"+oid);
      return mapping.findForward("set");
    }catch(Exception e){
      e.printStackTrace();
    }finally {

    }

    return new ActionForward(mapping.getInput());
  }
}
