package com.winsafe.drp.action.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class ListWarehouseBitAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    UsersBean users = UserManager.getUser(request);
    Integer userid = users.getUserid();
    int pagesize = 10;
    String wid=request.getParameter("WID");
    if (wid == null) {
    	wid = (String) request.getSession().getAttribute("sjwid");
	}
	request.getSession().setAttribute("sjwid", wid);

    try {
    	
    	String condition = " wid='"+wid+"' ";
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      String[] tablename={"WarehouseBit"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

      String blur = DbUtil.getBlur(map, tmpMap, "WBID");
      whereSql = whereSql +blur +condition; 

      whereSql = DbUtil.getWhereSql(whereSql); 
      

      AppWarehouse awr = new AppWarehouse();
      List wrls = awr.getWarehouseBit(request, pagesize, whereSql);


      request.setAttribute("wls", wrls);
      request.setAttribute("WID", wid);

      //DBUserLog.addUserLog(userid,"列表会员级别");
      return mapping.findForward("list");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
