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
import com.winsafe.drp.dao.AppCodeUnit;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListCodeUnitAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    super.initdata(request);
    try {

      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      String[] tablename={"CodeUnit"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
//      String timeCondition = DbUtil.getTimeCondition(map, tmpMap, " ReferDate"); 
//      String blur = DbUtil.getBlur(map, tmpMap, " ReportContent"); 
      //whereSql = whereSql  ; 

      whereSql = DbUtil.getWhereSql(whereSql); 
      

      AppCodeUnit awr = new AppCodeUnit();
      List culist = awr.getCodeUnitList(request, 10, whereSql);


      request.setAttribute("culist", culist);

      DBUserLog.addUserLog(userid,"系统管理","条码规则设置>>列表标识位规则");
      return mapping.findForward("success");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
