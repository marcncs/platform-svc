package com.winsafe.drp.action.warehouse;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.hbm.util.Internation;

public class GetProductByRPIDWarehouseIDAjax extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		super.initdata(request);try{
			String rpid = request.getParameter("RPID");
			Long wid = Long.valueOf(request.getParameter("WID"));
			
			String strCondition = " where ps.productid='"+rpid+"' and ps.warehouseid=" + wid;
			AppProductStockpile aps = new AppProductStockpile();
			ProductStockpile ps = new ProductStockpile();
			ps = aps.getProductStockpileByPIDWID(strCondition);
			
			if(ps!=null){
			
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
			Element reckonquantity=null;
				
				product = root.addElement("product");
				productid = product.addElement("productid");
				productid.setText(ps.getProductid());
				productname = product.addElement("productname");
//				productname.setText(ps.getPsproductname());
				specmode = product.addElement("specmode");
//				specmode.setText(ps.getPsspecmode());
				unitid = product.addElement("unitid");
				unitid.setText(String.valueOf(ps.getCountunit()));
				unitidname = product.addElement("unitidname");
				unitidname.setText(Internation.getStringByKeyPositionDB("CountUnit",			          
			            ps.getCountunit()));
				unitprice = product.addElement("unitprice");
				unitprice.setText("0");
				reckonquantity = product.addElement("reckonquantity");
				reckonquantity.setText(String.valueOf(ps.getStockpile()+ps.getPrepareout()));

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
