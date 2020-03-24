package com.winsafe.drp.action.finance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOutlay;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class ListOutlayAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);
		try {

		      String Condition=" (o.makeid ="+userid+" " +getOrVisitOrgan("o.makeorganid") +") ";
		      Map map = new HashMap(request.getParameterMap());
		      Map tmpMap = EntityManager.scatterMap(map);
		      String[] tablename={"Outlay"};
		      String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
		      String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
		          "MakeDate"); 
		      //String blur = DbUtil.getBlur(map, tmpMap, "OutlayUse"); 
		      whereSql = whereSql + timeCondition +  Condition; 
		      whereSql = DbUtil.getWhereSql(whereSql); 

//		      AppDept ad = new AppDept();
//		      AppUsers au = new AppUsers();
		      AppOutlay ao = new AppOutlay();
		      List usList = ao.getOutLay(request,pagesize, whereSql);
//		      ArrayList outlayList = new ArrayList();
//		      for (int t = 0; t < usList.size(); t++) {
//		    	Outlay ol = (Outlay)usList.get(t);
//		    	OutlayForm of = new OutlayForm();       
//		        of.setId(ol.getId());
//		        of.setOutlayidname(au.getUsersByid(ol.getOutlayid()).getRealname());
//		        of.setCastdeptname(ad.getDeptByID(ol.getCastdept()).getDeptname());
//		        of.setTotaloutlay(ol.getTotaloutlay());
//		        of.setIsauditname(Internation.getStringByKeyPosition("YesOrNo", request,
//		            ol.getIsaudit(), "global.sys.SystemResource"));       
//		        of.setIsendcasename(Internation.getStringByKeyPosition("YesOrNo", request,
//		            ol.getIsendcase(), "global.sys.SystemResource")); 
//
//		        outlayList.add(of);
//		      }      
//		      String isauditselect = Internation.getSelectTagByKeyAll(
//		          "YesOrNo", request, "IsAudit", true, null);
//		      String isendcaseselect = Internation.getSelectTagByKeyAll(
//		              "YesOrNo", request, "IsEndcase", true, null);
//		      
//		      	AppOrgan aor = new AppOrgan();
//				List ols = aor.getOrganToDown(users.getMakeorganid());		    
//			    List als = au.getIDAndLoginNameByOID2(users.getMakeorganid());
//	
//				request.setAttribute("als", als);
//				request.setAttribute("ols", ols);
//		     
//		      request.setAttribute("isauditselect", isauditselect);
//		      request.setAttribute("isendcaseselect", isendcaseselect);
		      request.setAttribute("usList", usList);

//		      DBUserLog.addUserLog(userid,"列表费用"); 
		      return mapping.findForward("list");
		    } catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
