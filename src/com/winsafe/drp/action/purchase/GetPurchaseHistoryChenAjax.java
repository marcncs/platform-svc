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

import com.winsafe.drp.dao.AppPurchaseBill;

public class GetPurchaseHistoryChenAjax extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		try{
			String pid = request.getParameter("pid");
			String productid = request.getParameter("productid");
			AppPurchaseBill adp = new AppPurchaseBill();
			List ls = adp.getPurchaseHistoryChen(pid,productid);
			
			JSONArray fulist = new JSONArray();
			JSONObject unitobj; 
			for (int i = 0; i < ls.size(); i++) {
				Object[] ob = (Object[]) ls.get(i);
				unitobj = new JSONObject();
				unitobj.put("price", ob[0].toString());
				unitobj.put("date", String.valueOf(ob[1]).substring(0,10));
				fulist.put(unitobj);
			}

//			response.setContentType("text/xml;charset=utf-8");
//			response.setHeader("Cache-Control", "no-cache");
//			
//			
//			
//			Document document = DocumentHelper.createDocument();
//			Element root = document.addElement("proot");
//			Element product = null;
//			Element hprice = null;
//			Element hdate = null;
//
//			for (int i = 0; i < ls.size(); i++) {
//				Object[] ob = (Object[]) ls.get(i);
//				product = root.addElement("product");
//				hprice = product.addElement("hprice");
//				hprice.setText(ob[0].toString());
//				hdate = product.addElement("hdate");
//				hdate.setText(String.valueOf(ob[1]).substring(0,10));
//
//			}
//			PrintWriter out = response.getWriter();
//			String s = root.asXML();
//			//System.out.println("a======"+s);
//			out.write(s);
//			
//			out.close();
			
			JSONObject json = new JSONObject();			
			json.put("pricelist", fulist);				
			response.setContentType("text/html; charset=UTF-8");
		    response.setHeader("Cache-Control", "no-cache");
		    PrintWriter out = response.getWriter();
		    out.print(json.toString());
		    out.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
