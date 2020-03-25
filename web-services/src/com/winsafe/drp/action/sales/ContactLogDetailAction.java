package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppContactLog;
import com.winsafe.drp.dao.ContactLog;

public class ContactLogDetailAction extends BaseAction {
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        Integer id = Integer.valueOf(request.getParameter("id"));
        
        initdata(request);
        AppContactLog acl = new AppContactLog();

        try {
             
            ContactLog cl = acl.getContactLog(id);
           
            request.setAttribute("clf", cl);

            //DBUserLog.addUserLog(userid,"联系记录详情"); 
            return mapping.findForward("detail");

        } catch (Exception e) {
            e.printStackTrace();
        } 

        return mapping.getInputForward();
    }

}
