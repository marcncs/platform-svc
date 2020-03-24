package com.winsafe.drp.action.sys;

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

import com.winsafe.drp.dao.AppMemberGrade;
import com.winsafe.drp.dao.AppMemberGradeRule;
import com.winsafe.drp.dao.MemberGradeRule;
import com.winsafe.drp.dao.MemberGradeRuleForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListMemberGradeRuleAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    UsersBean users = UserManager.getUser(request);
    int userid = users.getUserid();

    try {

      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      String[] tablename={"MemberGradeRule"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
//      String timeCondition = DbUtil.getTimeCondition(map, tmpMap, " ReferDate"); 
//      String blur = DbUtil.getBlur(map, tmpMap, " ReportContent"); 

      whereSql = DbUtil.getWhereSql(whereSql); 
     

      AppMemberGradeRule awr = new AppMemberGradeRule();
      List wrls = awr.getMemberGradeRule(request, whereSql);

      AppMemberGrade appmg = new AppMemberGrade();
      ArrayList wls = new ArrayList();
      for (int w = 0; w < wrls.size(); w++) {
    	  MemberGradeRuleForm mgrf = new MemberGradeRuleForm();
    	  MemberGradeRule o = (MemberGradeRule) wrls.get(w);
    	  mgrf.setId(o.getId());
    	  mgrf.setStartprice(o.getStartprice());
    	  mgrf.setEndprice(o.getEndprice());
    	  mgrf.setStartintegral(o.getStartintegral());
    	  mgrf.setEndintegral(o.getEndintegral());
    	  mgrf.setMgid(o.getMgid());
    	  mgrf.setGradename(appmg.getMemberGradeName(o.getMgid()));        
        wls.add(mgrf);
      }

      request.setAttribute("wls", wls);

      DBUserLog.addUserLog(userid, 11,"基础设置>>列表会员级别晋级规则");
      return mapping.findForward("list");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
