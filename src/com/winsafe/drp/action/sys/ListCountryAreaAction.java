package com.winsafe.drp.action.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.CountryAreaForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListCountryAreaAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    super.initdata(request);
    int pagesize = 10;

    try {
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      //String sql = "select * from country_area ";
      String[] tablename={"CountryArea"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
//      String timeCondition = DbUtil.getTimeCondition(map, tmpMap, " ReferDate"); 
      String blur = DbUtil.getBlur(map, tmpMap, " AreaName"); 
      whereSql = whereSql + blur ; 

      whereSql = DbUtil.getWhereSql(whereSql); 
      Object obj[] = (DbUtil.setPager(request,
                                      "CountryArea",
                                      whereSql,
                                      pagesize));
      SimplePageInfo tmpPgInfo = (SimplePageInfo)obj[0]; 
      whereSql = (String)obj[1];

      AppCountryArea aca = new AppCountryArea();
      List cals = aca.getAllCountryArea(request, pagesize, whereSql);

      ArrayList cls = new ArrayList();
      for (int w = 0; w < cals.size(); w++) {
        CountryAreaForm caf = new CountryAreaForm();
        Object[] o = (Object[]) cals.get(w);
        caf.setId(Long.valueOf(o[0].toString()));
        caf.setAreaname(o[1].toString());
        if(Integer.parseInt(o[2].toString())>0){
          caf.setParentname(aca.getAreaByID(Integer.valueOf(o[2].toString())).
                            getAreaname());
        }else{
          caf.setParentname("无");
        }
        cls.add(caf);
      }

      request.setAttribute("cls", cls);

      DBUserLog.addUserLog(userid,11,"基础设置>>列表地区");
      return mapping.findForward("list");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
