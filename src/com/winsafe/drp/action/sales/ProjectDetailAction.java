package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppProject;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Project;
import com.winsafe.drp.dao.ProjectForm;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.Internation;

public class ProjectDetailAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        Long id = Long.valueOf(request.getParameter("id"));
        //Long cid = Long.valueOf((String) request.getSession().getAttribute("cid"));
        
//        Long userid = users.getUserid();
        AppUsers au = new AppUsers();
        AppCustomer ac = new AppCustomer();       

        AppProject ap = new AppProject();

        try {
             HibernateUtil.currentSession();
             
             Project p = ap.getProjectByID(id);
             ProjectForm pf = new ProjectForm();
             pf.setId(p.getId());
             pf.setCidname(ac.getCustomer(p.getCid()).getCname());
             pf.setPcontentname(Internation.getStringByKeyPositionDB("HapContent", p.getPcontent()));
             pf.setPstatusname(Internation.getStringByKeyPositionDB("PStatus",p.getPstatus()));
             pf.setAmount(p.getAmount());
             pf.setPbegin(String.valueOf(p.getPbegin()));
             pf.setPend(String.valueOf(p.getPend()));
             pf.setMemo(p.getMemo());
//             pf.setMakeidname(au.getUsersByid(p.getMakeid()).getRealname());
             pf.setMakedate(String.valueOf(p.getMakedate()));

            request.setAttribute("pf", pf);

//            DBUserLog.addUserLog(userid,"项目详情,编号："+id); 
            return mapping.findForward("detail");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapping.getInputForward();
    }

}
