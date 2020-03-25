package com.winsafe.drp.action.assistant;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRepository;
import com.winsafe.drp.dao.AppRepositoryFile;
import com.winsafe.drp.dao.AppRepositoryProduct;
import com.winsafe.drp.dao.AppRepositoryType;
import com.winsafe.drp.dao.Repository;

public class ToUpdRepositoryAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("id");
		try {
			AppRepository appr = new AppRepository();
			Repository r = appr.getRepositoryByID(id);
			AppRepositoryFile arf = new AppRepositoryFile();
			List rflist = arf.getRepositoryFileByRID(id);

			AppRepositoryType art = new AppRepositoryType();
			List uls = art.getRepositoryTypeCanUse();
			
			AppRepositoryProduct aprp = new AppRepositoryProduct();
			List rplist = aprp.getRepositoryProductByRID(id);
			
			String severpath = request.getRequestURL().toString();
			severpath = severpath.substring(0, severpath.indexOf("assistant"));

			request.setAttribute("r", r);
			request.setAttribute("uls", uls);
			request.setAttribute("rflist", rflist);
			request.setAttribute("rplist", rplist);
			request.setAttribute("severpath", severpath);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("toupd");
	}

}
