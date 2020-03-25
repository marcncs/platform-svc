package com.winsafe.drp.action.yun;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMediaVideo;

public class DeleteMediaVideoAction extends BaseAction {

	private AppMediaVideo amv = new AppMediaVideo();
	
	public ActionForward execute(ActionMapping mapping , ActionForm form , HttpServletRequest request , HttpServletResponse response) throws Exception{
		
		try { 
			String mvID = request.getParameter("MVID");
			amv.delMediaVideoById(mvID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
