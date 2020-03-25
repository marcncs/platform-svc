package com.winsafe.drp.action.sales;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleOrder;

public class GetHistoryChenAjax extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		try{
			String cid = request.getParameter("cid");
			String productid = request.getParameter("productid");
			AppSaleOrder adp = new AppSaleOrder();
			List ls = adp.getHistoryChen(cid,productid);

			response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			
			
			
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("proot");
			Element product = null;
			Element hprice = null;
			Element hdate = null;

			for (int i = 0; i < ls.size(); i++) {
				Object[] ob = (Object[]) ls.get(i);
				product = root.addElement("product");
				hprice = product.addElement("hprice");
				hprice.setText(ob[0].toString());
				hdate = product.addElement("hdate");
				hdate.setText(String.valueOf(ob[1]).substring(0,10));

			}
			PrintWriter out = response.getWriter();
			String s = root.asXML();
			//System.out.println("a======"+s);
			out.write(s);
			
			out.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
