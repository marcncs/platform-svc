package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppAwake;
import com.winsafe.drp.dao.CalendarAwake;
import com.winsafe.hbm.entity.HibernateUtil;

public class AwakeAction  extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppAwake appAwake=new AppAwake();
		Integer id = Integer.valueOf(request.getParameter("ID"));

		try{
			HibernateUtil.currentSession();
			
			CalendarAwake ca=null;
			ca=appAwake.findByID(id);
			
			request.setAttribute("ca",ca);
			return mapping.findForward("info");
			
		}catch(Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
		
		}
		return mapping.findForward("");
	}
}

