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

import com.winsafe.drp.dao.AppIntegralRule;
import com.winsafe.drp.dao.IntegralRule;
import com.winsafe.drp.dao.IntegralRuleForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ListIntegralRuleAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    UsersBean users = UserManager.getUser(request);
    Integer userid = users.getUserid();
    int pagesize = 10;

    try {

      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      String[] tablename={"IntegralRule"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
      whereSql = whereSql  ; 

      whereSql = DbUtil.getWhereSql(whereSql); 
      

      AppIntegralRule awr = new AppIntegralRule();
      List wrls = awr.getIntegralRule(request,  whereSql);

      ArrayList wls = new ArrayList();
      for (int w = 0; w < wrls.size(); w++) {
    	  IntegralRuleForm irf = new IntegralRuleForm();
    	  IntegralRule o = (IntegralRule) wrls.get(w);
    	  irf.setId(o.getId());
    	  irf.setRmode(o.getRmode());
    	  if(o.getRmode().equals("OM")){
    		  irf.setRmodename("订货方式");
    	  }
    	  if(o.getRmode().equals("PM")){
    		  irf.setRmodename("付款方式");
    	  }
    	  if(o.getRmode().equals("SM")){
    		  irf.setRmodename("送货时间");
    	  }
    	  irf.setRkey(o.getRkey());
    	  irf.setRkeyname(Internation.getStringByKeyPositionDB("RKey",
					o.getRkey()));
    	  irf.setIrate(o.getIrate());
        
        wls.add(irf);
      }

      request.setAttribute("wls", wls);

      DBUserLog.addUserLog(userid, 11,"基础设置>>列表积分规则");
      return mapping.findForward("list");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
