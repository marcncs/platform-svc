package com.winsafe.drp.action.equip;

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

import com.winsafe.drp.dao.AppLinkMan;
import com.winsafe.drp.dao.Linkman;
import com.winsafe.drp.dao.LinkmanForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectLinkmanAction extends Action{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 5;
    String strcid=request.getParameter("cid");
    if(strcid==null){
    	strcid=(String)request.getSession().getAttribute("cid");
    }
    String cid=strcid;
    request.getSession().setAttribute("cid",strcid);
    String keyword = request.getParameter("KeyWord");
    try {
    	String Condition =" l.cid='"+cid+"' " ;
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      //String sql = "select * from provide ";
      String[] tablename={"Linkman"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename);

      String blur = DbUtil.getBlur(map, tmpMap, "Name");

      whereSql = whereSql + blur + Condition;
      whereSql = DbUtil.getWhereSql(whereSql); 

      Object obj[] = (DbUtil.setPager(request, "Linkman as l", whereSql,
					pagesize));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];
      
      AppLinkMan al = new AppLinkMan();
      List pls = al.searchLinkmanNew(request,pagesize, whereSql);
      ArrayList sls = new ArrayList();

      for(int i=0;i<pls.size();i++){
    	  LinkmanForm lf = new LinkmanForm();
    	  Linkman o=(Linkman)pls.get(i);
        lf.setId(o.getId());
        lf.setName(o.getName());
        lf.setSexname(Internation.getStringByKeyPosition("Sex", request,
                o.getSex(), "global.sys.SystemResource"));
        lf.setOfficetel(o.getOfficetel());
        lf.setDepartment(o.getDepartment());
        lf.setDuty(o.getDuty());
        lf.setAddr(o.getAddr());
        ///lf.setTransit(o.getTransit());
        //lf.setTransitname(Internation.getStringByKeyPositionDB("Transit", o.getTransit()));
        sls.add(lf);
      }

      request.setAttribute("sls",sls);
      return mapping.findForward("selectlinkman");
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return new ActionForward(mapping.getInput());
  }
}
