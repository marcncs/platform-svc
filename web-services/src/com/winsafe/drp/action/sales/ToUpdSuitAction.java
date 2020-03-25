package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSuit;
import com.winsafe.drp.dao.Suit;
import com.winsafe.drp.dao.SuitForm;
import com.winsafe.hbm.util.DateUtil;

public class ToUpdSuitAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        
        initdata(request);
        AppSuit ah = new AppSuit();

        Suit h = new Suit();
        try {
        	h = ah.getSuitByID(id);
        	if (h.getIsdeal() == 1) {
				String result = "databases.record.isapprovenooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
        	SuitForm pf = new SuitForm();
        	pf.setId(h.getId());
        	pf.setObjsort(h.getObjsort());
        	pf.setCid(h.getCid());
        	pf.setCidname(h.getCname());
        	pf.setSuitcontent(h.getSuitcontent());
        	pf.setSuitway(h.getSuitway());
        	pf.setSuitdate(DateUtil.formatDate(h.getSuitdate()));
        	pf.setSuittools(h.getSuittools());
        	pf.setSuitstatus(h.getSuitstatus());
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
