package com.winsafe.drp.action.purchase;

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

import com.winsafe.drp.dao.AppProviderProduct;
import com.winsafe.drp.dao.ProviderProduct;
import com.winsafe.hbm.util.Internation;

public class GetProductByRPIDAjax extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		try{
			String pid = request.getParameter("pid");
			String rpid = request.getParameter("RPID");
			AppProviderProduct ap = new AppProviderProduct();			
			ProviderProduct p = ap.getProvideProductByPPID(pid,rpid);
			
			if(p!=null){
			
			response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			
			
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("proot");
			Element product = null;
			Element productid = null;
			Element productname = null;
			Element specmode = null;
			Element unitid = null;
			Element unitidname = null;
			Element unitprice= null;
				
				product = root.addElement("product");
				productid = product.addElement("productid");
				productid.setText(p.getProductid());
				productname = product.addElement("productname");
				productname.setText(p.getPvproductname());
				specmode = product.addElement("specmode");
				specmode.setText(p.getPvspecmode());
				unitid = product.addElement("unitid");
				unitid.setText(String.valueOf(p.getCountunit()));
				unitidname = product.addElement("unitidname");
				unitidname.setText(Internation.getStringByKeyPositionDB("CountUnit",			         
			            p.getCountunit()));
				unitprice = product.addElement("unitprice");
				unitprice.setText(String.valueOf(p.getPrice()));

			PrintWriter out = response.getWriter();
			String s = root.asXML();
			//System.out.println("a======"+s);
			out.write(s);
			
			out.close();
	}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
