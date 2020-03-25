package com.winsafe.drp.action.users;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.server.UserTargetService;
import com.winsafe.drp.util.DBUserLog;

public class ListUserTargetAction extends BaseAction {

    @SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws
            IOException, ServletException {
    	super.initdata(request);
    	UserTargetService us = new UserTargetService();
    	AppBaseResource appbr = new AppBaseResource();
    	List utList = null;
    	String targettype = request.getParameter("TargetType");
        try {
        	//查询指标类型
		    List<BaseResource> typelist = appbr.getBaseResource("TargetType");
	        request.setAttribute("typelist", typelist);
	        
        	//主管销售指标
        	if(targettype==null || "".equals(targettype) || "0".equals(targettype)){
        		targettype = "0";
            	utList = us.dealUser(request,targettype);
        	}
        	//主管有效网点指标
        	else if("1".equals(targettype)){
        		utList = us.dealUser(request,targettype);
        	}
        	//经销商指标、网点指标
        	else if("2".equals(targettype) || "3".equals(targettype)){
        		utList = us.dealOrgan(request,targettype);
        	}
        	//办事处指标、大区指标
        	else if("4".equals(targettype) || "5".equals(targettype)){
        		utList = us.dealRegion(request, targettype);
        	}
            request.setAttribute("utList", utList);
            request.setAttribute("TargetType", targettype);
            request.setAttribute("KeyWord", request.getParameter("KeyWord"));
            DBUserLog.addUserLog(userid,11,"对象指标设置>>对象指标类表");
            return mapping.findForward("utList");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
