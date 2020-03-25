package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppLargess;
import com.winsafe.drp.dao.Largess;

public class DetailLargessAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        Integer id = Integer.valueOf(request.getParameter("id"));
        AppLargess ah = new AppLargess();
        AppCustomer ac = new AppCustomer();

        Largess h = new Largess();
        try {

        	h = ah.getLargessByID(id);
            request.setAttribute("lf", h);
            return mapping.findForward("detail");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //HibernateUtil.closeSession();
        }

        return mapping.getInputForward();
    }

}
