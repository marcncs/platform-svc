package com.winsafe.drp.action.yun;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMediaImg;
import com.winsafe.drp.dao.MediaImage;

public class ListMediaImgManagerAction extends BaseAction {

	private AppMediaImg ami = new AppMediaImg();
	private static int PAGE_SIZE = 10;
	
	public ActionForward execute(ActionMapping mapping , ActionForm form , HttpServletRequest request , HttpServletResponse response) throws Exception{
		 
		initdata(request);
		
		List<MediaImage> mediaImages = ami.getMeidaImg(request, PAGE_SIZE);
		
		request.setAttribute("mediaImgs", mediaImages);
		
		return mapping.findForward("list");
	}
}
