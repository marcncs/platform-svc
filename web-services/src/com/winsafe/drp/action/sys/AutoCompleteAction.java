package com.winsafe.drp.action.sys;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppAutoComplete;
import com.winsafe.drp.dao.AutoCompleteForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class AutoCompleteAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String tblname=request.getParameter("tblname");
		String keyvalue=request.getParameter("keyvalue");
		keyvalue=new String(keyvalue.getBytes("iso-8859-1"), "UTF-8");
		
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		try{
			AppAutoComplete aac=new AppAutoComplete();

			List autols = aac.getAutoComplete(userid,tblname,keyvalue);

			List rs=new ArrayList();

			for(int i=0;i<autols.size();i++){
				AutoCompleteForm acf = new AutoCompleteForm();
				Object o=(Object)autols.get(i);
				acf.setAutovalue((String)o);
				rs.add(acf);
			}

			//System.out.println("-----------"+rs.size());
			request.setAttribute("rs",rs);
			return mapping.findForward("autocomplete");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
