package com.winsafe.drp.action.assistant;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppRepository;
import com.winsafe.drp.dao.AppRepositoryFile;
import com.winsafe.drp.dao.AppRepositoryProduct;
import com.winsafe.drp.dao.AppRepositoryType;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Repository;
import com.winsafe.drp.dao.RepositoryForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;

public class RepositoryDetailAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("id");
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		try {
			AppRepository appr = new AppRepository();
			AppRepositoryType art = new AppRepositoryType();
			AppOrgan ao = new AppOrgan();
			AppDept ad = new AppDept();
			AppUsers au = new AppUsers();

			Repository r = appr.getRepositoryByID(id);
			RepositoryForm rf = new RepositoryForm();
			rf.setTitle(r.getTitle());
			rf.setRtidname(art.getRepositoryTypeById(r.getRtid().toString()).getSortname());
			rf.setContent(r.getContent());
			rf.setMakeorganidname(ao.getOrganByID(r.getMakeorganid()).getOrganname());
//			rf.setMakedeptidname(ad.getDeptByID(r.getMakedeptid()).getDeptname());
//			rf.setMakeidname(au.getUsersByid(r.getMakeid()).getRealname());			
			rf.setMakedate(DateUtil.formatDateTime(r.getMakedate()));
			
			AppRepositoryFile arf = new AppRepositoryFile();
			List rflist = arf.getRepositoryFileByRID(id);
			
			AppRepositoryProduct aprp = new AppRepositoryProduct();
			List rplist = aprp.getRepositoryProductByRID(id);
			
			String severpath = request.getRequestURL().toString();
			severpath = severpath.substring(0, severpath.indexOf("assistant"));			

			request.setAttribute("rf", rf);
			request.setAttribute("rflist", rflist);
			request.setAttribute("rplist", rplist);
			request.setAttribute("severpath", severpath);
//			DBUserLog.addUserLog(userid, "知识库详情");// 日志
			return mapping.findForward("info");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}

}
