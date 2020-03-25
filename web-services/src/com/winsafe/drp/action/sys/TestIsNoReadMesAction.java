package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.entity.HibernateUtil;

public class TestIsNoReadMesAction  extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean tu=(UsersBean)request.getSession().getAttribute("users");
		
		try
		{
			HibernateUtil.currentSession();
//			//AppTest at=new AppTest();
//			//TestMessage tm=at.getNoReadMes(tu.getUserid());
//			response.setContentType("text/xml;charset=utf-8");
//			response.setHeader("Cache-Control", "no-cache");
//			//String s="";
//			if(tm!=null){
//				System.out.println("tm is not null==============="+tm);
//				Document document = DocumentHelper.createDocument();
//				Element root = document.addElement("rootmessage");
//				Element message=null;
//				Element messageid= null;
//				Element sendid=null;
//				Element messagecontent = null;
//		
//				message = root.addElement("message");
//				messageid = message.addElement("messageid");
//				messageid.setText(tm.getId().toString());
//				sendid=message.addElement("sendid");
//				sendid.setText(tm.getSenduserid().toString());
//				messagecontent = message.addElement("messagecontent");
//				messagecontent.setText(tm.getMessage());
//				
//				PrintWriter out = response.getWriter();
//				String s = root.asXML();
//				System.out.println("a======"+s);
//				out.write(s);
//				out.close();
				
//			s="<a href='javascript:void(0)' onClick='showMessage()'><img src='../images/laba-10b.gif' border=0></a>";
//				
//			}else{
//				s="<img src='../images/laba-10.gif'/>";
			//}
			
			
//			PrintWriter out = response.getWriter();
//			//String s = root.asXML();
//			System.out.println("a======"+s);
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
