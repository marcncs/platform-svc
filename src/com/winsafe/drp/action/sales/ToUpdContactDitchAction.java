package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppContactDitch;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppDitch;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.ContactDitch;
import com.winsafe.drp.dao.ContactDitchForm;
import com.winsafe.hbm.util.Internation;

public class ToUpdContactDitchAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        Long id = Long.valueOf(request.getParameter("id"));
        //Long cid = Long.valueOf(request.getParameter("cid"));
        
//        Long userid = users.getUserid();
        AppContactDitch appContactLog = new AppContactDitch();
        AppUsers au = new AppUsers();

        ContactDitch cl = new ContactDitch();
        AppCustomer ac = new AppCustomer();
        AppDitch ad = new AppDitch();
        try {

        	cl = appContactLog.getContactDitch(id);
            ContactDitchForm clf = new ContactDitchForm();
            clf.setId(cl.getId());
            clf.setDid(cl.getDid());
            clf.setDidname(ad.getDitchByID(Long.valueOf(cl.getDid())).getDname());
            clf.setContactdate(String.valueOf(cl.getContactdate()));
            clf.setContactmodename(Internation.getSelectTagByKeyAll("ContactMode",request,"contactmode",String.valueOf(cl.getContactmode()),null));
            clf.setContactpropertyname(Internation.getSelectTagByKeyAll("ContactProperty",request,"contactproperty",String.valueOf(cl.getContactproperty()),null));
            clf.setContactcontent(cl.getContactcontent());
            clf.setFeedback(cl.getFeedback());
            clf.setLinkman(cl.getLinkman());
            clf.setNextcontact(String.valueOf(cl.getNextcontact()));
            clf.setNextgoal(cl.getNextgoal());
            AppBaseResource abr = new AppBaseResource();
  	      List<BaseResource> uls = abr.getBaseResource("SelectContact");
  	      
  	    request.setAttribute("als", uls);
            request.setAttribute("clf", clf);
            return mapping.findForward("edit");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //HibernateUtil.closeSession();
        }

        return mapping.getInputForward();
    }

}
