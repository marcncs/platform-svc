package com.winsafe.drp.action.self;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.winsafe.drp.dao.AppAwake;
import com.winsafe.drp.dao.CalendarAwake;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.entity.HibernateUtil;

public class XMLAwakeAction  extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppAwake appAwake=new AppAwake();
		UsersBean users = UserManager.getUser(request);
	    Integer userid = users.getUserid();
		try{
			HibernateUtil.currentSession();
			
			CalendarAwake ca=null;
			ca=appAwake.findByUserID(userid);
			response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			

			if(ca!=null)
			{
				Document document = DocumentHelper.createDocument();
				Element root = document.addElement("rootawake");
				Element awake=null;
				Element awakeid= null;
				Element awakecontent = null;
		
				awake = root.addElement("awake");
				awakeid = awake.addElement("awakeid");
				awakeid.setText(ca.getId().toString());
				awakecontent = awake.addElement("awakecontent");
				awakecontent.setText(ca.getAwakecontent());
			
				PrintWriter out = response.getWriter();
				String s = root.asXML();
				//System.out.println("a======"+s);
				out.write(s);
				out.close();
			}
			
		}catch(Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			//HibernateUtil.closeSession();
			
		}
		return null;
	}
}
