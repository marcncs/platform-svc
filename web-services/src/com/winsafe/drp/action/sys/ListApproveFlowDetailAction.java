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

import com.winsafe.drp.dao.AppApproveFlowDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.ApproveFlowDetail;
import com.winsafe.drp.dao.ApproveFlowDetailForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class ListApproveFlowDetailAction extends Action{

	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		int pagesize = 15;
		try{
			String strAFID = request.getParameter("afid");
			if (strAFID == null) {
				strAFID = (String) request.getSession().getAttribute("afid");
		      }
	
		      request.getSession().setAttribute("afid",strAFID);
		      
		      String Condition = " aff.afid='"+strAFID +"'";
	      Map map = new HashMap(request.getParameterMap());
	      Map tmpMap = EntityManager.scatterMap(map);
	      String[] tablename={"ApproveFlowDetail"};
	      String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

	      whereSql = whereSql +Condition; 
	      whereSql = DbUtil.getWhereSql(whereSql); 
	      Object obj[] = (DbUtil.setDynamicPager(request,
	                                      "ApproveFlowDetail as aff ",
	                                      whereSql,
	                                      pagesize,"detailCondition"));
	      SimplePageInfo tmpPgInfo = (SimplePageInfo)obj[0]; 
	      whereSql = (String)obj[1];

	      AppUsers au = new AppUsers();
	      AppApproveFlowDetail aafd = new AppApproveFlowDetail();
	      List apls = aafd.searchApproveFlowDetail(pagesize, whereSql, tmpPgInfo);
	      ArrayList alpl = new ArrayList();
	      for(int i=0;i<apls.size();i++){
	    	  ApproveFlowDetailForm afdf = new ApproveFlowDetailForm();
	    	  ApproveFlowDetail o = (ApproveFlowDetail)apls.get(i);
	        afdf.setId(o.getId());
	        afdf.setApproveidname(au.getUsersByid(o.getApproveid()).getRealname());
	        afdf.setActidname(Internation.getStringByKeyPositionDB("ActID", o.getActid()));
	        afdf.setApproveorder(o.getApproveorder());
	        alpl.add(afdf);
	      }

	      request.setAttribute("AFID", strAFID);
	      request.setAttribute("alpl",alpl);
	      UsersBean users = UserManager.getUser(request);
	      int userid = users.getUserid();
	      DBUserLog.addUserLog(userid,11, "基础设置>>列表审阅流详情");
	      return mapping.findForward("list");
	    }catch(Exception e){
	      e.printStackTrace();
	    }
		return new ActionForward(mapping.getInput());
	}
}
