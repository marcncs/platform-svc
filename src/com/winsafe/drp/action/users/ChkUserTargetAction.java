package com.winsafe.drp.action.users;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.server.ChkUserTargetService;


public class ChkUserTargetAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ChkUserTargetService uts = new ChkUserTargetService();
		String targettype = request.getParameter("TargetType");
		try {
			String result = "";
			//主管销售指标、主管有效网点指标检查
        	if(targettype==null || "".equals(targettype) || "0".equals(targettype)||"1".equals(targettype)){
        		if(targettype==null || "".equals(targettype)){
        			targettype = "0";
        		}
        		result = uts.dealChkUserTarget(request,targettype);
        	}
        	//经销商指标、网点指标检查
        	else if("2".equals(targettype) || "3".equals(targettype)){
        		result = uts.dealChkOrganTarget(request,targettype);
        	}
        	//办事处、大区
        	else if("4".equals(targettype)||"5".equals(targettype)){
        		result = uts.dealChkRegionTarget(request,targettype);
        	}
			JSONObject json = new JSONObject();			
			json.put("result", result);				
			response.setContentType("text/html; charset=UTF-8");
		    response.setHeader("Cache-Control", "no-cache");
		    PrintWriter out = response.getWriter();
		    out.print(json.toString());
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
