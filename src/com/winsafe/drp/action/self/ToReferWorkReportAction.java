package com.winsafe.drp.action.self;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWorkReportApprove;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DbUtil;

public class ToReferWorkReportAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String reportid = request.getParameter("ReportID");

    try{
    	if (DbUtil.judgeApproveStatusToRefer("WorkReport", reportid)) {
			String result = "databases.record.approvestatus";
			request.setAttribute("result", result);
			return new ActionForward("/sys/lockrecordclose.jsp");//;mapping.findForward("lock");
		}
      AppUsers au = new AppUsers();
      List uls = au.getIDAndLoginName();
      ArrayList auls = new ArrayList();
      for(int i=0;i<uls.size();i++){
        UsersBean ub = new UsersBean();
        Object[] o = (Object[])uls.get(i);
        ub.setUserid(Integer.valueOf(o[0].toString()));
        ub.setLoginname(o[1].toString());
        ub.setRealname(o[2].toString());
        auls.add(ub);
      }

      AppWorkReportApprove awre = new AppWorkReportApprove();
      List alrd = awre.getWorkReportApprove(Integer.valueOf(reportid));
      ArrayList alls = new ArrayList();
      for(int l=0;l<alrd.size();l++){
        UsersBean alub = new UsersBean();
        Object[] ob = (Object[])alrd.get(l);
        alub.setUserid(Integer.valueOf(ob[1].toString()));
        alub.setLoginname(au.getUsersByID(alub.getUserid()).getLoginname());
        alub.setRealname(au.getUsersByID(alub.getUserid()).getRealname());
        alls.add(alub);
      }

      request.setAttribute("reportid",reportid);
      request.setAttribute("alls",alls);
      request.setAttribute("auls",auls);

      return mapping.findForward("toselect");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
