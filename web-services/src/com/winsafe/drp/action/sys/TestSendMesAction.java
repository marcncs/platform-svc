package com.winsafe.drp.action.sys;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;

public class TestSendMesAction   extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String str=request.getParameter("acceptdid");
		Long acceptdid=null;
		String message=request.getParameter("message");
		//message = new String(message.getBytes("iso-8859-1"), "utf-8");
		Session session=null;

		try
		{

//			UsersBean tu=(UsersBean)request.getSession().getAttribute("users");
//			AppTest at=new AppTest();
//			TestMessage tm=new TestMessage();
//			tm.setId(MakeCode.getExcIDByRandomTableName("test_message",0));
//			tm.setSenduserid(tu.getUserid());
//			if(str!=null){
//				acceptdid=Long.valueOf(str);
//				tm.setAcceptuserid(acceptdid);
//			}else{
//				tm.setAcceptuserid(Long.valueOf(0));
//			}
//			tm.setMessage(message);
//			tm.setSendtime(DateUtil.getCurrentDate());
//			tm.setIsread(0);
//			
//			at.addMessage(tm);
//			
//			//
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{

			////
		}
		return null;
	}
}
