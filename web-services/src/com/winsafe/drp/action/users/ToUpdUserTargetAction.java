package com.winsafe.drp.action.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppOrganTarget;
import com.winsafe.drp.dao.AppRegionTarget;
import com.winsafe.drp.dao.AppUserTarget;
import com.winsafe.drp.dao.OrganTarget;
import com.winsafe.drp.dao.RegionTarget;
import com.winsafe.drp.dao.UserTarget;

public class ToUpdUserTargetAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String objid = request.getParameter("objid");
		String targettype = request.getParameter("TargetType");
		try{
			//主管销售指标、主管有效网点指标检查
        	if(targettype==null || "".equals(targettype) || "0".equals(targettype)||"1".equals(targettype)){
        		AppUserTarget apput = new AppUserTarget();
        		UserTarget ut = apput.getUserTargetDetailById(Integer.parseInt(objid));
    			if(ut==null){
    				ut = new UserTarget();
    			}
    			if(ut.getTargetdate()!=null)
    				ut.setUsefuldate(ut.getTargetdate().substring(0,4) + "-" + ut.getTargetdate().substring(4));
    	       request.setAttribute("ut", ut);
        	}
        	//经销商指标、网点指标检查
        	else if("2".equals(targettype) || "3".equals(targettype)){
        		AppOrganTarget appot = new AppOrganTarget();
        		OrganTarget ut = appot.getOrganTargetDetailById(Integer.parseInt(objid));
    			if(ut==null){
    				ut = new OrganTarget();
    			}
    			if(ut.getTargetdate()!=null)
    				ut.setUsefuldate(ut.getTargetdate().substring(0,4) + "-" + ut.getTargetdate().substring(4));
    	       request.setAttribute("ut", ut);
        	}
        	//办事处、大区
        	else if("4".equals(targettype)||"5".equals(targettype)){
        		AppRegionTarget apprt = new AppRegionTarget();
        		RegionTarget ut = apprt.getRegionTargetDetailById(Integer.parseInt(objid));
    			if(ut==null){
    				ut = new RegionTarget();
    			}
    			if(ut.getTargetdate()!=null)
    				ut.setUsefuldate(ut.getTargetdate().substring(0,4) + "-" + ut.getTargetdate().substring(4));
    	       request.setAttribute("ut", ut);
        	}
			
	       return mapping.findForward("toupdusertarget");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}	
}
