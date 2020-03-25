package com.winsafe.drp.action.purchase;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProductStruct;


public class AjaxListProductStructAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String strparentid = request.getParameter("parentid");
		try {
			AppProductStruct aa = new AppProductStruct();
			List als = aa.getTreeByCate(strparentid);
			
			JSONArray fulist = new JSONArray();
			JSONObject unitobj; 
			for (int i = 0; i < als.size(); i++) {
				Object[] ob = (Object[]) als.get(i);
				unitobj = new JSONObject();
				unitobj.put("id", ob[0].toString());
				unitobj.put("acode", ob[1].toString());
				unitobj.put("areaname", ob[2].toString());				
				fulist.put(unitobj);
			}
			
			JSONObject json = new JSONObject();			
			json.put("pslist", fulist);				
			response.setContentType("text/html; charset=UTF-8");
		    response.setHeader("Cache-Control", "no-cache");
		    PrintWriter out = response.getWriter();
		    out.print(json.toString());
		    out.close();
			
			
			
			
			
//			response.setContentType("text/xml;charset=utf-8");
//			response.setHeader("Cache-Control", "no-cache");
//
//			
//			Document document = DocumentHelper.createDocument();
//			Element root = document.addElement("alltree");
//			Element tree = null;
//			Element id = null;
//			Element acode = null;
//			Element areaname=null;
//			for (int i = 0; i < als.size(); i++) {
//				Object[] ob = (Object[]) als.get(i);
//				tree = root.addElement("tree");
//				id = tree.addElement("id");
//				id.setText(ob[0].toString());
//				acode=tree.addElement("acode");
//				acode.setText(ob[1].toString());
//				areaname = tree.addElement("areaname");
//				areaname.setText(ob[2].toString());
//			}
//			PrintWriter out = response.getWriter();
//			String s = root.asXML();
//			// System.out.println("a======"+s);
//			out.write(s);
//			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return new ActionForward(mapping.getInput());
		return null;
	}

}
