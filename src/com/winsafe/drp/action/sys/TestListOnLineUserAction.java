package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.hbm.entity.HibernateUtil;

public class TestListOnLineUserAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//AppTest at=new AppTest();
		
		try{
			HibernateUtil.currentSession();
			//TestUser tu=(TestUser)(request.getSession().getAttribute("testUser"));
//			List list=at.getUser();
//			
//			response.setContentType("text/xml;charset=utf-8");
//			response.setHeader("Cache-Control", "no-cache");
//			Document document = DocumentHelper.createDocument();
//			Element root = document.addElement("rootuser");
//			Element user=null;
//			Element id = null;
//			Element username = null;
//			Element isonline=null;
//			for(int i=0;i<list.size();i++){
//				Object []ob=(Object[])list.get(i);
//				user=root.addElement("user");
//				id=user.addElement("id");
//				id.setText(ob[0].toString());
//				username=user.addElement("username");
//				username.setText(ob[1].toString());
//				isonline=user.addElement("isonline");
//				isonline.setText(ob[2].toString());
//			}
//			
//			PrintWriter out = response.getWriter();
//			String s = root.asXML();
//			out.write(s);
//			out.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//HibernateUtil.closeSession();
			
		}
		
		return null;
	}

}
