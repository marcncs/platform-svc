package com.winsafe.drp.action.assistant;

import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppQuery; 
import com.winsafe.drp.dao.QueryResult;

import common.Logger;

public class TraceAction extends BaseAction {
	private static Logger logger = Logger.getLogger(TraceAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
			//获得查询码
			String code = request.getParameter("code");
			//查询
			TraceService wis = new TraceService();
			AppQuery aq = new AppQuery();
			String ipString = aq.getIpAddr(request);
			QueryResult map = wis.execute(code, ipString, false);
			if(map != null && map.isExist()) {
				request.setAttribute("result", map);
			}
			request.setAttribute("code", request.getParameter("code"));
			return mapping.findForward("success");
		} catch (Exception e) {
			logger.error("",e);
		} 
		return new ActionForward(mapping.getInput());
	}
}
