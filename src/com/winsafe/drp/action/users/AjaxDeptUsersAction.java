package com.winsafe.drp.action.users;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.UsersService;
import com.winsafe.hbm.util.RequestTool;


public class AjaxDeptUsersAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int did =RequestTool.getInt(request, "did");
		try {
//			if ( "".equals(did) ){
//				did = users.getMakedeptid().toString();
//			}
			UsersService applm = new UsersService();
			List<Users> dlist = applm.getUsersByDept(did);
			JSONArray list = new JSONArray();
			for ( Users ub : dlist ){
				list.put(ub);
			}
			
			JSONObject json = new JSONObject();			
			json.put("userslist", list);				
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
