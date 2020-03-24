package com.winsafe.drp.action.assistant;

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

import com.winsafe.drp.dao.AdjustCIntegral;
import com.winsafe.drp.dao.AdjustCIntegralForm;
import com.winsafe.drp.dao.AppAdjustCIntegral;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListAdjustCIntegralAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    int pagesize = 20;
    UsersBean users = UserManager.getUser(request);
//	Long userid = users.getUserid();
    try {
    	String visitorgan = "";
		if(users.getVisitorgan()!=null&&users.getVisitorgan().length()>0){
			visitorgan = "or makeorganid in("+users.getVisitorgan()+") ";
		}

//		String Condition = " (makeid="+userid+" "+visitorgan+" )";
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      String[] tablename={"AdjustCIntegral"};
      String whereSql = EntityManager.getTmpWhereSql(map, tablename);  
      String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
		" MakeDate");
      String blur = DbUtil.getOrBlur(map, tmpMap, "KeysContent");

//      whereSql = whereSql + timeCondition + blur +Condition;
      whereSql = DbUtil.getWhereSql(whereSql); 
      
      Object obj[] = (DbUtil.setDynamicPager(request,
              "AdjustCIntegral",
              whereSql,
              pagesize,"AdjustCIntegralCondition"));

      SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0];
      whereSql = (String) obj[1];

      AppAdjustCIntegral ap = new AppAdjustCIntegral();
      AppOrgan ao = new AppOrgan();
      AppUsers au = new AppUsers();
      List apls = ap.getAdjustCIntegral(pagesize, whereSql, tmpPgInfo);
      ArrayList alapls = new ArrayList();

      for (int p = 0; p < apls.size(); p++) {
        AdjustCIntegralForm pf = new AdjustCIntegralForm();
        AdjustCIntegral r = (AdjustCIntegral)apls.get(p);
        pf.setId(r.getId());
        pf.setIsauditname(Internation.getStringByKeyPosition(
						"YesOrNo", request, r.getIsaudit(),
						"global.sys.SystemResource"));
        pf.setRemark(r.getRemark());
        pf.setMakeorganidname(ao.getOrganByID(r.getMakeorganid()).getOrganname());
//        pf.setMakeidname(au.getUsersByid(r.getMakeid()).getRealname());
        pf.setMakedate(DateUtil.formatDateTime(r.getMakedate()));
        alapls.add(pf);
      }
      
		List ols = ao.getOrganToDown(users.getMakeorganid());
		AppDept ad = new AppDept();
		List dls = ad.getDeptByOID(users.getMakeorganid());
		List als = au.getIDAndLoginNameByOID2(users.getMakeorganid());


		String isauditselect = Internation.getSelectTagByKeyAll("YesOrNo",
				request, "IsAudit", true, null);
		
		request.setAttribute("ols", ols);
		request.setAttribute("dls", dls);
		request.setAttribute("als", als);
		request.setAttribute("isauditselect", isauditselect);
		

      request.setAttribute("alapls", alapls);
//      DBUserLog.addUserLog(userid,"查询调整会员积分");//日志 
      return mapping.findForward("list");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
