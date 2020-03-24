/**
 * 
 */
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

import com.winsafe.drp.dao.AppRole;
import com.winsafe.drp.dao.OperateForm;

public class ListRoleMenuAjax extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		try{
			int moduleid = Integer.parseInt(request.getParameter("moduleid"));
			Integer roleid = Integer.valueOf(request.getParameter("roleid"));
	
			AppRole av = new AppRole();
			List mls = av.getMenuByRidMid(roleid,moduleid);

			
			
			response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("alloperate");

			
			Element operate = null;
			Element oname = null; 
			Element oid = null;  
			Element ispd = null;  
			
			for(int j=0;j<mls.size();j++){
				OperateForm of = (OperateForm)mls.get(j);
				operate = root.addElement("operate");
				oid = operate.addElement("oid");
				oid.setText(String.valueOf(of.getId()));
				oname = operate.addElement("oname");
				oname.setText(of.getOperatename());
				ispd = operate.addElement("ispd");
				ispd.setText(String.valueOf(of.getIspd()));


			}
			PrintWriter out = response.getWriter();
			String s = root.asXML();

			out.write(s);
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
