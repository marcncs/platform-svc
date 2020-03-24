package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppApproveFlowLog;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppSuit;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.ApproveFlowLog;
import com.winsafe.drp.dao.ApproveFlowLogForm;
import com.winsafe.drp.dao.Suit;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class SuitDetailAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        
        initdata(request);
        AppUsers au = new AppUsers();
        AppOrgan ao = new AppOrgan();
        Suit p = new Suit(); 
        AppSuit ap = new AppSuit();

        try {
             
             p = ap.getSuitByID(id);
             
           
             
             AppApproveFlowLog apba = new AppApproveFlowLog();             
             ApproveFlowLog flow = apba.getApproveFlowLogByUserid(userid, id);
             
 			List aprv = apba.getApproveFlowLog(id);
 			ArrayList rvls = new ArrayList();
 			for (int r = 0; r < aprv.size(); r++) {
 				ApproveFlowLog afl = (ApproveFlowLog) aprv.get(r);
 				ApproveFlowLogForm pbaf = new ApproveFlowLogForm();
 				pbaf.setApproveidname(au.getUsersByID(afl.getApproveid())
 						.getRealname());
 				pbaf.setActidname(Internation.getStringByKeyPositionDB("ActID",
 						afl.getActid()));
 				pbaf.setApprovecontent(afl.getApprovecontent());
 				pbaf.setApprovename(Internation.getStringByKeyPosition(
 						"SubApproveStatus", request, afl.getApprove(),
 						"global.sys.SystemResource"));
 				pbaf.setApprovedate(DateUtil.formatDateTime(afl.getApprovedate()));
 				rvls.add(pbaf);
 			}

 			request.setAttribute("flow", flow);
            request.setAttribute("pf", p);
            request.setAttribute("rvls", rvls);

            return mapping.findForward("detail");

        } catch (Exception e) {
            e.printStackTrace();
        } 
        return mapping.getInputForward();
    }

}
