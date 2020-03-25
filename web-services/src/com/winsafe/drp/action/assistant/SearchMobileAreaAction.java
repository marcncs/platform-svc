package com.winsafe.drp.action.assistant;

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

import com.winsafe.drp.dao.AppMobileNum;
import com.winsafe.drp.dao.MobileArea;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class SearchMobileAreaAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String mobilenum=request.getParameter("mno").substring(0,7);
		AppMobileNum appMobileNum=new AppMobileNum();
		try{
			MobileArea ma=null;
			ma=appMobileNum.findMobileArea(mobilenum);
			response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			

			if(ma!=null)
			{
				Document document = DocumentHelper.createDocument();
				Element root = document.addElement("rootmobilenum");
				Element mobile=null;
				Element id= null;
				Element num = null;
				Element areas = null;
				Element cardtype = null;
		
				mobile = root.addElement("mobile");
				id = mobile.addElement("id");
				id.setText(ma.getId().toString());
				num = mobile.addElement("num");
				num.setText(ma.getMobilenum());
				areas=mobile.addElement("areas");
				areas.setText(ma.getAreas());
				cardtype=mobile.addElement("cardtype");
				cardtype.setText(ma.getCardtype());
			
				PrintWriter out = response.getWriter();
				String s = root.asXML();
				out.write(s);
				out.close();
				
				 
			}
			UsersBean users = UserManager.getUser(request);
//		      Long userid = users.getUserid();
//			DBUserLog.addUserLog(userid,"查询手机");//日志
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
