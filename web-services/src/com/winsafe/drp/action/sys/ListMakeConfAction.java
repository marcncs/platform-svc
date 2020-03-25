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
import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class ListMakeConfAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
	  super.initdata(request);
    int pagesize = 10;

    try {
    	String Condition = " mc.isdis =1 ";
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      //String sql = "select * from country_area ";
      String[] tablename={"MakeConf"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
//      String timeCondition = DbUtil.getTimeCondition(map, tmpMap, " ReferDate"); 
      String blur = DbUtil.getBlur(map, tmpMap, " CHName"); 
      whereSql = whereSql + blur + Condition; 

      whereSql = DbUtil.getWhereSql(whereSql); 
//      Object obj[] = (DbUtil.setPager(request,
//                                      "MakeConf",
//                                      whereSql,
//                                      pagesize));
//      SimplePageInfo tmpPgInfo = (SimplePageInfo)obj[0]; 
//      whereSql = (String)obj[1];

      AppMakeConf aca = new AppMakeConf();
      List mcls = aca.getMakeConf(request, pagesize, whereSql);

//      ArrayList cls = new ArrayList();
//      for (int w = 0; w < cals.size(); w++) {
//        CountryAreaForm caf = new CountryAreaForm();
//        Object[] o = (Object[]) cals.get(w);
//        caf.setId(Long.valueOf(o[0].toString()));
//        caf.setAreaname(o[1].toString());
//        if(Integer.parseInt(o[2].toString())>0){
//          caf.setParentname(aca.getAreaByID(Integer.valueOf(o[2].toString())).
//                            getAreaname());
//        }else{
//          caf.setParentname("无");
//        }
//        cls.add(caf);
//      }

      request.setAttribute("mcls", mcls);

      //DBUserLog.addUserLog(userid,"列表编号规则");
      return mapping.findForward("list");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
