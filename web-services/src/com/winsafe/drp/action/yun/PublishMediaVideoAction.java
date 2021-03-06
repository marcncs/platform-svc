package com.winsafe.drp.action.yun;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction; 
import com.winsafe.drp.dao.AppMediaVideo;
import com.winsafe.drp.dao.MediaVideo;

public class PublishMediaVideoAction extends BaseAction {

	private AppMediaVideo amv = new AppMediaVideo();
	
	public ActionForward execute(ActionMapping mapping , ActionForm form , HttpServletRequest request , HttpServletResponse response) throws Exception{
		
		String mvid = request.getParameter("MVID");
		String status = request.getParameter("STATUS");
		MediaVideo mv = amv.getMediaVideoById(mvid);
		mv.setIsPublish(Integer.valueOf(status));
		amv.updMediaVideoById(mv);
		
		return null;
	}
}
