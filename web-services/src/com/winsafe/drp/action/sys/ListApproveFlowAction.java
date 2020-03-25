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

import com.winsafe.drp.dao.AppApproveFlow;
import com.winsafe.drp.dao.ApproveFlowForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class ListApproveFlowAction extends Action{

	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		int pagesize = 5;
		try{

	      Map map = new HashMap(request.getParameterMap());
	      Map tmpMap = EntityManager.scatterMap(map);
	      String[] tablename={"ApproveFlow"};
	      String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

	      whereSql = DbUtil.getWhereSql(whereSql); 
	    
	      AppApproveFlow apl = new AppApproveFlow();
	      List apls = apl.getApproveFlow(request, pagesize, whereSql);
	      ArrayList alpl = new ArrayList();
	      for(int i=0;i<apls.size();i++){
	    	  ApproveFlowForm aff = new ApproveFlowForm();
	        Object[] o = (Object[])apls.get(i);
	        aff.setId(o[0].toString());
	        aff.setFlowname(String.valueOf(o[1]));
	        aff.setMemo(String.valueOf(o[2]));
	        
	        alpl.add(aff);
	      }
	      UsersBean users = UserManager.getUser(request);
	      int userid = users.getUserid();
	      DBUserLog.addUserLog(userid, 11, "基础设置>>列表审阅流程");
	      request.setAttribute("alpl",alpl);
	      return mapping.findForward("list");
	    }catch(Exception e){
	      e.printStackTrace();
	    }
		return new ActionForward(mapping.getInput());
	}
}
