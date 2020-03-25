package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;

public class TestReadMessageAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Session session=null;

		try
		{

//			UsersBean usersBean = UserManager.getUser(request);
//		    Long userid = usersBean.getUserid();
//			AppTest at=new AppTest();
//			//System.out.println("con.setAutoCommit111111111============"+conn.getAutoCommit());
//			List list=at.getSameUserMes(userid);  
//			//
//			at.doReadMes(userid);
//			//System.out.println("con.setAutoCommit============"+conn.getAutoCommit()+"++++++++"+session);
//			response.setContentType("text/xml;charset=utf-8");
//			response.setHeader("Cache-Control", "no-cache");
//			Document document = DocumentHelper.createDocument();
//			Element root = document.addElement("rootmessage");
//			Element messages=null;
//			Element id = null;
//			Element message = null;
//			Element sendusername = null;
//			Element acceptusername=null;
//			Element sendtime=null;
//			for(int i=0;i<list.size();i++){
//				Object []ob=(Object[])list.get(i);
//				messages=root.addElement("messages");
//				id=messages.addElement("id");
//				id.setText(ob[0].toString());
//				message=messages.addElement("message");
//				message.setText(ob[1].toString());
//				sendusername=messages.addElement("sendusername");
//				sendusername.setText(ob[2].toString());
//				acceptusername=messages.addElement("acceptusername");
//				acceptusername.setText(ob[3].toString());
//				sendtime=messages.addElement("sendtime");
//				sendtime.setText(ob[4].toString());
//			}
//			
//			PrintWriter out = response.getWriter();
//			String s = root.asXML();
////			System.out.println("a======"+s);
//			out.write(s);
//			out.close();
//			//修改消息已读状态//
//			////
//			
////			
//			//
//			return null;
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
//			ConnectionEntityManager.close(conn);
			////
		}
		return null;

	}
}