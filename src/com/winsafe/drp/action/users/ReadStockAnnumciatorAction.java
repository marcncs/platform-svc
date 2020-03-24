package com.winsafe.drp.action.users;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.winsafe.drp.dao.AppOrganAnnunciator;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class ReadStockAnnumciatorAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
	    int userid = users.getUserid();
	    try{
	    	AppOrganAnnunciator awa = new AppOrganAnnunciator();
	    	List ls = awa.stockAnnumciator(userid);
	    	
	    	response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("rootstock");
			Element messages=null;
			Element id = null;
			Element organid = null;
			for(int i=0;i<ls.size();i++){
				Object []ob=(Object[])ls.get(i);
				messages=root.addElement("messages");
				id=messages.addElement("id");
				id.setText(ob[0].toString());
				organid=messages.addElement("organid");
				organid.setText(ob[1].toString());
			}
			PrintWriter out = response.getWriter();
			String s = root.asXML();
			//System.out.println("a======"+s);
			out.write(s);
			out.close();
			
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		
		return null;
	}

}
