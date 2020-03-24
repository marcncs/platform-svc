package com.winsafe.drp.action.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class SelectProviderAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 10;
    super.initdata(request);
    try {

    	String Condition = " (p.makeid="+userid+" "+this.getOrVisitOrgan("p.makeorganid")+") and p.isdel=0 ";
    	
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      String[] tablename={"Provider"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename);

      String blur = DbUtil.getOrBlur(map, tmpMap, "PID","PName","Mobile","NCcode");

      whereSql = whereSql + blur + Condition;
      whereSql = DbUtil.getWhereSql(whereSql); 
      
      AppProvider ap = new AppProvider();
      List pls = ap.getProvider(request, pagesize, whereSql);


      request.setAttribute("sls",pls);
      return mapping.findForward("selectprovide");
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return new ActionForward(mapping.getInput());
  }
}
