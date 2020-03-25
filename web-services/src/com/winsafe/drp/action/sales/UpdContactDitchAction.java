package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppContactDitch;
import com.winsafe.drp.dao.ContactDitch;

public class UpdContactDitchAction extends BaseAction {
  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    //String did = (String) request.getSession().getAttribute("did");
    
//    Long userid = users.getUserid();

   AppContactDitch appContactLog = new AppContactDitch();
    //Session session = null;
    //Connection con = null;
    try {
      
      //con = session.connection();
      //con.setAutoCommit(false);
      
      ContactDitch cl = new ContactDitch();
      	cl.setId(Long.valueOf(request.getParameter("id")));
		cl.setDid(request.getParameter("did"));
		String contactdate = request.getParameter("contactdate").replace('-', '/');
		cl.setContactmode(Integer.valueOf(request.getParameter("contactmode")));
		cl.setContactproperty(Integer.valueOf(request.getParameter("contactproperty")));
		cl.setContactcontent(request.getParameter("contactcontent"));
		cl.setFeedback(request.getParameter("feedback"));
		cl.setLinkman(request.getParameter("linkman"));
		String nextcontact = request.getParameter("nextcontact").replace('-', '/');
		cl.setNextgoal(request.getParameter("nextgoal"));
//		cl.setUserid(userid);
      

      appContactLog.updateContactDitch(cl,contactdate,nextcontact) ;
        

//        DBUserLog.addUserLog(userid,"修改渠道联系记录,编号："+cl.getId()); 
      //con.commit();
      request.setAttribute("result", "databases.upd.success");
      return mapping.findForward("success");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      ////
       // ConnectionEntityManager.close(con);

    }


    return mapping.getInputForward();
  }

}
