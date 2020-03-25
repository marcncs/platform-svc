package com.winsafe.drp.action.sales;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Product;

/**
 * 通过产品单位的得到对应关系
* @Title: AjaxFunitAction.java
* @Description: TODO
* @author: wenping 
* @CreateTime: Aug 29, 2012 2:00:51 PM
* @version:
 */
public class AjaxFunitAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String uid = request.getParameter("unitid");
		String pid = request.getParameter("pid");
		
		AppFUnit af =new AppFUnit();
		AppProduct ap =new AppProduct();
		
		Product p = ap.getProductByID(pid);
		
		try {
			Integer unitid = Integer.parseInt(uid);
			Double trquantity;
			if(unitid == 18){//kg
				trquantity = af.getXQuantity(pid, 2);
			}else if(unitid == 2){
				trquantity=1.0;
			}else{
				trquantity = p.getBoxquantity();
			}
			
			JSONObject json = new JSONObject();			
			json.put("trquantity", trquantity);				
			response.setContentType("text/html; charset=UTF-8");
		    response.setHeader("Cache-Control", "no-cache");
		    PrintWriter out = response.getWriter();
		    out.print(json.toString());
		    out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
