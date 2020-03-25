package com.winsafe.drp.action.yun;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMediaVideo;
import com.winsafe.drp.dao.MediaVideo;

public class ListMediaVideoManagerAction extends BaseAction {

	private AppMediaVideo amv = new AppMediaVideo();
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		initdata(request);
		
		try { 
			
			int pagesize ;
			if (request.getParameter("currentPage") != null) {
				pagesize = Integer.valueOf(request.getParameter("currentPage"));
			}else {
				pagesize = 15;
			}
			
			Integer currentPage = null;
			if (request.getParameter("currentPage") != null) {
				currentPage = Integer.valueOf(request.getParameter("currentPage"));
			}
			if (currentPage == null || currentPage.intValue() < 1) {
				currentPage = 1;
			}
			
			List<MediaVideo> list = new ArrayList<MediaVideo>();
			list = amv.getMediaVideo(request, pagesize, currentPage);
			request.setAttribute("mediaVideos", list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return mapping.findForward("list");
	}
}
