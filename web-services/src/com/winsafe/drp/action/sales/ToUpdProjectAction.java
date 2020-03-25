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
import com.winsafe.hbm.util.Internation;

public class ToUpdProjectAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        Long id = Long.valueOf(request.getParameter("id"));
        //Long cid = Long.valueOf(request.getParameter("cid"));
        
//        Long userid = users.getUserid();
        AppProject ah = new AppProject();
        AppCustomer ac = new AppCustomer();
        AppUsers au = new AppUsers();

        Project h = new Project();
        try {

        	h = ah.getProjectByID(id);
        	ProjectForm pf = new ProjectForm();
        	pf.setId(h.getId());
        	pf.setCid(h.getCid());
        	pf.setCidname(ac.getCustomer(h.getCid()).getCname());
        	pf.setPcontentname(Internation.getSelectTagByKeyAllDBDef("HapContent", "pcontent",h.getPcontent()));
        	pf.setPstatusname(Internation.getSelectTagByKeyAllDBDef("PStatus", "pstatus",h.getPstatus()));
        	pf.setAmount(h.getAmount());
        	pf.setPbegin(String.valueOf(h.getPbegin()));
        	pf.setPend(String.valueOf(h.getPend()));
        	pf.setMemo(h.getMemo());
            
            
            request.setAttribute("pf", pf);
            return mapping.findForward("toupd");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //HibernateUtil.closeSession();
        }

        return mapping.getInputForward();
    }

}
