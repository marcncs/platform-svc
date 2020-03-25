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
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppSaleOrderDetail;

public class GetCurrentRequireAjax extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		super.initdata(request);try{
			String soid = request.getParameter("soid");
			Long wid = Long.valueOf(request.getParameter("wid"));
			AppSaleOrderDetail asld = new AppSaleOrderDetail();
//			List ls = asld.getSaleOrderDetailObjectBySOID(soid);
			AppProduct ap = new AppProduct();
			AppProductStockpile aps = new AppProductStockpile();
//System.out.println("-----soid------"+soid+"---"+wid);
			response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			
			
			
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("proot");
			Element product = null;
			Element productname = null;
			Element productcount = null;
			Element stockcount= null;

//			for (int i = 0; i < ls.size(); i++) {
//				SaleOrderDetail ob = (SaleOrderDetail) ls.get(i);
//				product = root.addElement("product");
//				productname = product.addElement("productname");
//				productname.setText(ob.getProductname());
//				productcount = product.addElement("productcount");
//				productcount.setText(ob.getQuantity().toString());
//				stockcount = product.addElement("stockcount");
//				stockcount.setText(String.valueOf(aps.getProductStockpileByProductID(ob.getProductid(),wid)));
//				
//			}
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
