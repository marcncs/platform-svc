package com.winsafe.drp.action.sales;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.PeddleOrderProductForm;
import com.winsafe.drp.dao.Product;
import com.winsafe.hbm.util.Internation;

public class GetProductByRPIDAjax extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			
			String rpid = request.getParameter("RPID");
			String cid = request.getParameter("CID");
			AppProduct ap = new AppProduct();
			AppCustomer ac = new AppCustomer();
			Product p = ap.getProductByIdcode(rpid);
			Customer c = ac.getCustomer(cid);
			AppWarehouse aw = new AppWarehouse();
			AppInvoiceConf appic = new AppInvoiceConf();
			String icid = request.getParameter("icid");


			if (p != null) {
//				System.out.println("--1aa=="+p.getId());
//				System.out.println("--1ab=="+c.getCid());
//				System.out.println("--1ac=="+icid);
//				System.out.println("--oid=="+users.getMakeorganid());
//				System.out.println("-----isidcode---"+p.getIsidcode());				
				PeddleOrderProductForm pf = new PeddleOrderProductForm();
				pf.setProductid(p.getId());
				pf.setProductname(p.getProductname());
				pf.setSpecmode(p.getSpecmode());
				pf.setUnitid(p.getCountunit());				
				pf.setCountunit(Internation.getStringByKeyPositionDB(
						"CountUnit", p.getCountunit()));
//				pf.setWarehouseid(aw.getWarehouseByOID(users.getMakeorganid()).getId());
//				pf.setWarehousename(aw.getWarehouseByID(pf.getWarehouseid())
//						.getWarehousename());
				//System.out.println("--wid=="+pf.getWarehousename());
				pf.setBatch("");
//				pf.setPrice(ap.getProductPriceByOIDPIDRate(users
//						.getMakeorganid(), pf.getProductid(), pf.getUnitid(), Long.valueOf(c.getRate())));
				pf.setTaxprice(pf.getPrice());
				pf.setWise(p.getWise());
				pf.setDiscount(c.getDiscount());
				pf.setTaxrate(appic.getInvoiceConfById(Integer.valueOf(icid)).getIvrate());
				pf.setIsidcode(p.getIsidcode());				
				
				JSONObject json = new JSONObject();			
				json.put("product", pf);				
				response.setContentType("text/html; charset=UTF-8");
			    response.setHeader("Cache-Control", "no-cache");
			    PrintWriter out = response.getWriter();
			    out.print(json.toString());
			    out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
