package com.winsafe.drp.packseparate.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.packseparate.dao.AppPackSeparate;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListPackSeparateAction extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 10;
    super.initdata(request);
    try {
		String Condition="";
		if(DbUtil.isDealer(users)) {
			Condition="  warehouseid in (select ruw.warehouse_Id from Rule_User_Wh ruw where ruw.user_Id = "
				+ userid + ")";
		} else {  
			Condition = "("+DbUtil.getWhereCondition(users, "o")+") and ";
		}
//    	String Condition=" (makeid="+userid+" "+getOrVisitOrgan("makeorganid")+") ";
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      String[] tablename={"PackSeparate"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename);

      String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
          "makeDate");
      String blur = DbUtil.getOrBlur(map, tmpMap, "p.keyContent", "p.id");
      whereSql = whereSql +  timeCondition+blur+Condition; 
      whereSql = whereSql.replace("isaudit", "p.isaudit");
      whereSql = DbUtil.getWhereSql(whereSql); 
        
      
      AppPackSeparate aps = new AppPackSeparate();
      List pils = aps.getPackSeparatesList(request, pagesize, whereSql);
      

      request.setAttribute("ps", pils);

      DBUserLog.addUserLog(request, "列表");
      return mapping.findForward("list");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
