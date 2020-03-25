package com.winsafe.drp.action.purchase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPlinkman;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class ListPlinkmanAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
     int pagesize = 5;
     super.initdata(request);
    String strpid = request.getParameter("pid");
    if (strpid == null) {
    	strpid = (String) request.getSession().getAttribute("pid");
    }
    String pid = strpid;   
    try{
    	String Condition = " pl.pid='"+pid +"' ";
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      //String sql = "select * from provide_linkman ";
      String[] tablename={"Plinkman"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
      String blur = DbUtil.getOrBlur(map, tmpMap, "Name","OfficeTel","Mobile"); 
      whereSql = whereSql + blur + Condition  ; 
      whereSql = DbUtil.getWhereSql(whereSql); 



      AppPlinkman apl = new AppPlinkman();
      List apls = apl.getPlinkManByPId(request, pagesize, whereSql);
      
      request.getSession().setAttribute("pid", strpid);
      request.setAttribute("pid",pid);
      request.setAttribute("alpl",apls);
      
     // DBUserLog.addUserLog(userid,"列表供应商联系人");
      return mapping.findForward("list");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
