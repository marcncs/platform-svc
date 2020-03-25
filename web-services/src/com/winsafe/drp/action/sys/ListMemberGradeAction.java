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
import com.winsafe.drp.dao.MemberGrade;
import com.winsafe.drp.dao.MemberGradeForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ListMemberGradeAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    UsersBean users = UserManager.getUser(request);
    int userid = users.getUserid();
    int pagesize = 10;

    try {

      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      String[] tablename={"MemberGrade"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
//      String timeCondition = DbUtil.getTimeCondition(map, tmpMap, " ReferDate"); 
//      String blur = DbUtil.getBlur(map, tmpMap, " ReportContent"); 
    //  whereSql = whereSql  ; 

      whereSql = DbUtil.getWhereSql(whereSql); 
      

      AppMemberGrade awr = new AppMemberGrade();
      List wrls = awr.getMemberGrade(request, whereSql);

      ArrayList wls = new ArrayList();
      for (int w = 0; w < wrls.size(); w++) {
    	  MemberGradeForm mgf= new MemberGradeForm();
    	  MemberGrade o = (MemberGrade) wrls.get(w);
    	  mgf.setId(o.getId());
    	  mgf.setGradename(o.getGradename());
    	  mgf.setPolicyid(o.getPolicyid());
    	  mgf.setPolicyidname(Internation.getStringByKeyPositionDB("PricePolicy", 
	                o.getPolicyid()));
    	  mgf.setIntegralrate(o.getIntegralrate());
        
        wls.add(mgf);
      }

      request.setAttribute("wls", wls);

      DBUserLog.addUserLog(userid,11, "基础设置>>列表会员级别");
      return mapping.findForward("list");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
