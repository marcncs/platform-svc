package com.winsafe.drp.action.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppAfficheBrowse;
import com.winsafe.drp.dao.AppServiceExecute;
import com.winsafe.drp.dao.AppTaskExecute;
import com.winsafe.drp.dao.AppWorkReportApprove;
import com.winsafe.drp.dao.Dept;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.DeptService;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.UsersService;

/**
 * @author : ivan
 * @version : 2009-8-12 上午09:31:22 www.winsafe.cn
 */
@SuppressWarnings("unchecked")
public class ReferInfoAction extends Action {

	private OrganService appOrgan;

	private DeptService appDept;

	private UsersService appUsers;

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		 UsersBean users = UserManager.getUser(request);
		 
	
		List<Dept> listDept = getDept(users.getMakeorganid());
		List<Users> listUsers = getUsers(users.getMakeorganid());
		List<Users> existUsers = null;
		String ReferType  = (String) request.getSession().getAttribute("ReferType");
		Integer ID = Integer.valueOf(request.getSession().getAttribute("ID").toString());
		
		if (ReferType.equalsIgnoreCase("WorkReport")) {
			existUsers= getWorkReportUsers(ID);
		} else if (ReferType.equalsIgnoreCase("ServiceAgreement")) {
			existUsers= getServiceExecuteUsers(ID);
		} else if (ReferType.equalsIgnoreCase("Task")) {
			existUsers= getTaskExecuteUsers(ID);
		} else if (ReferType.equalsIgnoreCase("Affiche")) {
			existUsers= getAfficheBrowseUsers(ID);
		}

		OrganService organ = new OrganService();
		request.setAttribute("organid", users.getMakeorganid());
		request.setAttribute("organname", organ.getOrganName(users.getMakeorganid()));
		request.setAttribute("listDept", listDept);
		request.setAttribute("listUsers", listUsers);
		request.setAttribute("existUsers", existUsers);
		existUsers= null;
		return mapping.findForward("list");
	}

	

	private List<Organ> getOrgan(String parentid) throws Exception {
		appOrgan = new OrganService();
		return appOrgan.getTreeByCate(parentid);
	}

	private List<Dept> getDept(String organid) throws Exception {
		appDept = new DeptService();
		return appDept.getDeptByOID(organid);
	}

	private List<Users> getUsers(String organid) throws Exception {
		appUsers = new UsersService();
		return appUsers.getUsersByOrgan(organid);
	}
	
	
	private List<Users> getAfficheBrowseUsers(Integer id) throws Exception{
		AppAfficheBrowse app = new AppAfficheBrowse();
		String whereSql = " where ab.afficheid = " +id;
		List list  = app.getAfficheBrowse(whereSql);
		List<Users> listuser = new ArrayList<Users>();
		for (int i = 0 ; i < list.size() ; i++) {
			Object[] obj = (Object[]) list.get(i);
			listuser.add(appUsers.getUsersByid((Integer)obj[2]));
		}
		return listuser;
	}
	
	private List<Users> getTaskExecuteUsers(Integer id)throws Exception{
		AppTaskExecute app = new AppTaskExecute();
		
		List list  = app.getExecuteIDByTaskPlanID(id);
		List<Users> listuser = new ArrayList<Users>();
		for (int i = 0 ; i < list.size() ; i++) {
			listuser.add(appUsers.getUsersByid((Integer)list.get(i)));
		}
		return listuser;
	}
	
	private List<Users> getServiceExecuteUsers(Integer id) throws Exception {
		AppServiceExecute app = new AppServiceExecute();
		List list  = app.getServiceExecuteUsers(id);
		List<Users> listuser = new ArrayList<Users>();
		for (int i = 0 ; i < list.size() ; i++) {

			listuser.add(appUsers.getUsersByid((Integer)list.get(i)));

		}
		return listuser;
	}

	private List<Users> getWorkReportUsers(Integer id) throws Exception {
		AppWorkReportApprove app = new AppWorkReportApprove();
		List list  = app.getWorkReportUsers(id);
		List<Users> listuser = new ArrayList<Users>();
		for (int i = 0 ; i < list.size() ; i++) {
			listuser.add(appUsers.getUsersByid((Integer)list.get(i)));
		}
		return listuser;
	}
}
