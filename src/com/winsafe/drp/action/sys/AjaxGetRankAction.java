package com.winsafe.drp.action.sys;


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

import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.CountryArea;

public class AjaxGetRankAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		try{
			String rank=request.getParameter("rank");
			Integer intRank=Integer.valueOf(rank);
			AppCountryArea aca=new AppCountryArea();
			List list=null;
			list=aca.getAreasByRank(intRank);
			response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			
			
			
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("allarea");
			Element area = null;
			Element areaid = null;
			Element areaname = null;
			
			for (int i = 0; i < list.size(); i++) {
				CountryArea ob = (CountryArea) list.get(i);
				area = root.addElement("area");
				areaid = area.addElement("areaid");
				areaid.setText(ob.getId().toString());
				areaname = area.addElement("areaname");
				areaname.setText(ob.getAreaname());
				
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
