package com.winsafe.drp.action.yun;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMediaVideo;
import com.winsafe.drp.dao.MediaVideo;

public class EditMediaVideoAction extends BaseAction {

	private AppMediaVideo amv = new AppMediaVideo();
	
	public ActionForward execute(ActionMapping mapping , ActionForm form , HttpServletRequest request , HttpServletResponse response) throws Exception{
		
		try {	
		
			String id = request.getParameter("id"); 
			String title = request.getParameter("title");
			String digest = request.getParameter("digest");
			
			MediaVideo mv = amv.getMediaVideoById(id);
			mv.setTitle(title);
			mv.setDigest(digest);
			mv.setLastModifyTime(new Date());
			
			amv.updMediaVideoById(mv);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("result", "databases.add.success");
		return mapping.findForward("success");
	}
	
}
