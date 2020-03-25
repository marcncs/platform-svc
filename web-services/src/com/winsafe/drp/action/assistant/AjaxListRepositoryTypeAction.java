package com.winsafe.drp.action.assistant;

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

import com.winsafe.drp.dao.AppRepositoryType;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;


public class AjaxListRepositoryTypeAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String strparentid = request.getParameter("parentid");
		try {
			AppRepositoryType aa = new AppRepositoryType();
			List als = aa.getTreeByCate(strparentid);
			response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");

			
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("alltree");
			Element tree = null;
			Element id = null;
			Element acode = null;
			Element areaname=null;
			for (int i = 0; i < als.size(); i++) {
				Object[] ob = (Object[]) als.get(i);
				tree = root.addElement("tree");
				id = tree.addElement("id");
				id.setText(ob[0].toString());
				acode=tree.addElement("acode");
				acode.setText(ob[1].toString());
				areaname = tree.addElement("areaname");
				areaname.setText(ob[2].toString());
			}
			PrintWriter out = response.getWriter();
			String s = root.asXML();
			out.write(s);
			out.close();
			UsersBean users = UserManager.getUser(request);
//			Long userid = users.getUserid();
//			DBUserLog.addUserLog(userid,"查询知识库类别");//日志 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
