package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppWorkReport;
import com.winsafe.drp.dao.WorkReport;

public class ToApproveWorkReportAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String id = request.getParameter("reportid");
    try {

      AppWorkReport awr = new AppWorkReport();
      WorkReport wr = new WorkReport();
      wr = awr.getWorkReportByID(Integer.valueOf(id));
      Integer makeid = wr.getMakeid();
      Integer reportsort = wr.getReportsort();
      String reportcontent = wr.getReportcontent();

      request.setAttribute("reportid",id);
      request.setAttribute("makeid", makeid);
      request.setAttribute("reportsort", reportsort);
      request.setAttribute("reportcontent", reportcontent);

      return mapping.findForward("toapprove");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
