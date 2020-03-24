package com.winsafe.drp.action.finance;

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
import com.winsafe.drp.dao.AppPurchaseInvoice;
import com.winsafe.hbm.util.Internation;


public class SelectPurchaseInvoiceAjax extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		super.initdata(request);try{
			String pid = request.getParameter("PID");
			String begindate = request.getParameter("BeginDate");
			String enddate = request.getParameter("EndDate");
			String whereSql = "";
			if(pid!=null && !pid.equals("")){
				whereSql +=" and pi.provideid="+pid;
			}
			if(begindate !=null && !begindate.equals("")){
				whereSql +=" and pi.invoicedate>='"+begindate+"'";
			}
			if(enddate !=null && !enddate.equals("")){
				whereSql +=" and pi.invoicedate<='"+enddate+"'";
			}

			AppPurchaseInvoice asld = new AppPurchaseInvoice();
			List ls = asld.selectPurchaseInvoice(whereSql);

			response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			
			
			
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("iroot");
			Element invoice=null;
			Element iid = null;
			Element idate = null;
			Element iprovideid = null;
			Element iproductid= null;
			Element iproductname= null;
			Element ispecmode= null;
			Element iquantity= null;
			Element iunitpirce=null;
			Element iunitidname= null;
			Element isubsum= null;

			for (int i = 0; i < ls.size(); i++) {
				Object[] ob = (Object[]) ls.get(i);
				invoice = root.addElement("invoice");
				iid = invoice.addElement("iid");
				iid.setText(ob[0].toString());
				idate= invoice.addElement("idate");
				idate.setText(ob[1].toString().substring(0,10));
				iprovideid = invoice.addElement("iprovideid");
				iprovideid.setText(ob[2].toString());
				iproductid = invoice.addElement("iproductid");
				iproductid.setText(ob[3].toString());
				iproductname = invoice.addElement("iproductname");
				iproductname.setText(ob[4].toString());
				ispecmode = invoice.addElement("ispecmode");
				ispecmode.setText(ob[5].toString());
				iquantity = invoice.addElement("iquantity");
				iquantity.setText(ob[7].toString());
				iunitidname = invoice.addElement("iunitidname");
				iunitidname.setText(Internation.getStringByKeyPositionDB("CountUnit", Integer.parseInt(ob[6]
						       								.toString())));
				iunitpirce = invoice.addElement("iunitpirce");
				iunitpirce.setText(ob[8].toString());
				isubsum = invoice.addElement("isubsum");
				isubsum.setText(ob[9].toString());

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
