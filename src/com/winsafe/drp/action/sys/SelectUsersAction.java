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

import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectUsersAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 5;
    try {
    	String Condition =" u.status=1 ";
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      String[] tablename={"Users"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename);

      String blur = DbUtil.getBlur(map, tmpMap, "RealName");

      whereSql = whereSql + blur + Condition;
      whereSql = DbUtil.getWhereSql(whereSql); 

      Object obj[] = (DbUtil.setPager(request, "Users as u", whereSql,
					pagesize));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];
      
      AppUsers ap = new AppUsers();
      List pls = ap.getUsers(request, pagesize, whereSql);
      ArrayList sls = new ArrayList();

      for(int i=0;i<pls.size();i++){
    	  UsersForm uf = new UsersForm();
    	  Users o=(Users)pls.get(i);
        uf.setUserid(o.getUserid());
        uf.setRealname(o.getRealname());
        uf.setSexname(Internation.getStringByKeyPosition("Sex", request,
                o.getSex(), "global.sys.SystemResource"));

        sls.add(uf);
      }

      request.setAttribute("sls",sls);
      return mapping.findForward("selectusers");
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return new ActionForward(mapping.getInput());
  }
}
