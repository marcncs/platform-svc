package com.winsafe.drp.action.self;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWorkReport;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.WorkReportForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class WaitApproveWorkReportAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 10;
    UsersBean users = UserManager.getUser(request);
    Integer userid = users.getUserid();

    String approveselect = "";
      approveselect = Internation.getSelectTagByKeyAll("YesOrNo", request,
                                                    "Approve", true, null);

    String strreportsort = request.getParameter("ReportSort");
    int reportsort;
    if (strreportsort == null || strreportsort.equals("all")) {
      reportsort = -1;
    }
    else {
      reportsort = Integer.parseInt(strreportsort);
    }

    String keyword = "";
    keyword = request.getParameter("KeyWord");

    String sortselect = "";
    if (strreportsort == null || strreportsort.equals("all")) {
      sortselect = Internation.getSelectTagByKeyAll("ReportSort", request,
                                                    "ReportSort", true, null);
    }
    else {
      sortselect = Internation.getSelectTagByKey("ReportSort", request,
                                                 "ReportSort",
                                                 reportsort, true, null);
    }
    try {
      String condition = "wr.id=wra.reportid and wra.approveid=" + userid;
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);

      String[] tablename={"WorkReport","WorkReportApprove"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename);

      String blur = DbUtil.getBlur(map, tmpMap, " and wr.reportcontent");
      whereSql = whereSql + condition + blur; 
      whereSql = DbUtil.getWhereSql(whereSql);
      Object obj[] = (DbUtil.setPager(request,
                                      "WorkReport as wr,WorkReportApprove as wra ",
                                      whereSql,
                                      pagesize));
      SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
      whereSql = (String) obj[1];
      keyword = request.getParameter("KeyWord");

      AppWorkReport awr = new AppWorkReport();
      List wrls = awr.waitApproveWorkReport(pagesize, whereSql, tmpPgInfo);
      ArrayList arwr = new ArrayList();
      String reportcontent = "";
      AppUsers au = new AppUsers();
      for(int i=0;i<wrls.size();i++){
        WorkReportForm wrf = new WorkReportForm();
        Object[] o = (Object[])wrls.get(i);
        wrf.setId(Integer.valueOf(o[0].toString()));
        wrf.setCreateuser(au.getUsersByID(Integer.valueOf(o[1].toString())).getRealname());
        reportcontent = o[2].toString();
        if (reportcontent.length() > 30) {
          reportcontent = reportcontent.substring(0, 30) + "...";
        }
        wrf.setReportcontent(reportcontent);
        wrf.setReportsort(Internation.getStringByKeyPosition("ReportSort",
            request,
            Integer.parseInt(o[3].toString()), "global.sys.SystemResource"));
        wrf.setReferdate(String.valueOf(o[4]).substring(0,10));
        wrf.setApprovestatus(Internation.getStringByKeyPosition("SubApproveStatus",
            request,
            Integer.parseInt(o[6].toString()), "global.sys.SystemResource"));
        //wrf.setApprovedate((String)o[8]);
        arwr.add(wrf);
      }

      request.setAttribute("approveselect",approveselect);
      request.setAttribute("sortselect", sortselect);
      request.setAttribute("keyword", keyword);
      request.setAttribute("arwr",arwr);

      //DBUserLog.addUserLog(userid,"待审阅工作报告");
      return mapping.findForward("workreport");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
