package com.winsafe.drp.action.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCodeRule;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListCodeRuleAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    super.initdata(request);
    try {

    	String condition = " exists (select cr.id from CodeRule as cr where cr.ucode=cu.ucode)";
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      String[] tablename={"CodeUnit"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
//      String timeCondition = DbUtil.getTimeCondition(map, tmpMap, " ReferDate"); 
//      String blur = DbUtil.getBlur(map, tmpMap, " ReportContent"); 
      whereSql = whereSql + condition ; 

      whereSql = DbUtil.getWhereSql(whereSql); 
      

      AppCodeRule awr = new AppCodeRule();
      List culist = awr.getCodeUnitList(request, 10, whereSql);


      request.setAttribute("culist", culist);

      DBUserLog.addUserLog(userid,11,"条码规则设置>>列表条码规则");
      return mapping.findForward("success");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
