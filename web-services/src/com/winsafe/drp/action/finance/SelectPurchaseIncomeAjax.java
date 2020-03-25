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
import com.winsafe.drp.dao.AppPurchaseIncome;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.hbm.util.Internation;


public class SelectPurchaseIncomeAjax extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		super.initdata(request);try{
			String bpid = request.getParameter("BPID");
			String warehouseid = request.getParameter("WarehouseID");
			String obtaincode = request.getParameter("ObtainCode");
			String bbegindate = request.getParameter("BBeginDate");
			String benddate = request.getParameter("BEndDate");
			String whereSql = "";
			if(bpid!=null && !bpid.equals("")){
				whereSql +=" and pi.provideid="+bpid;
			}
			if(warehouseid !=null && !warehouseid.equals("")){
				whereSql +=" and pi.warehouseid="+warehouseid;
			}
			if(obtaincode !=null && !obtaincode.equals("")){
				whereSql +=" and pi.obtaincode like '"+obtaincode+"'";
			}
			if(bbegindate !=null && !bbegindate.equals("")){
				whereSql +=" and pi.incomedate>='"+bbegindate+"'";
			}
			if(benddate !=null && !benddate.equals("")){
				whereSql +=" and pi.incomedate<='"+benddate+"'";
			}

			AppPurchaseIncome asld = new AppPurchaseIncome();
			List ls = asld.selectPurchaseIncome(whereSql);

			response.setContentType("text/xml;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			
			
			AppWarehouse aw = new AppWarehouse();
			
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("broot");
			Element bill=null;
			Element bid = null;
			Element bincomedate = null;
			Element bwarehouse = null;
			Element bproductid= null;
			Element bproductname= null;
			Element bspecmode= null;
			Element bunitidname= null;
			Element bquantity= null;

			for (int i = 0; i < ls.size(); i++) {
				Object[] ob = (Object[]) ls.get(i);
				bill = root.addElement("bill");
				bid = bill.addElement("bid");
				bid.setText(ob[0].toString());
				bincomedate= bill.addElement("bincomedate");
				bincomedate.setText(ob[1].toString().substring(0,10));
				bwarehouse = bill.addElement("bwarehouse");
				bwarehouse.setText(aw.getWarehouseByID(ob[2].toString()).getWarehousename());
				bproductid = bill.addElement("bproductid");
				bproductid.setText(ob[3].toString());
				bproductname = bill.addElement("bproductname");
				bproductname.setText(ob[4].toString());
				bspecmode = bill.addElement("bspecmode");
				bspecmode.setText(ob[5].toString());
				bquantity = bill.addElement("bquantity");
				bquantity.setText(ob[7].toString());
				bunitidname = bill.addElement("bunitidname");
				bunitidname.setText(Internation.getStringByKeyPositionDB("CountUnit", Integer.parseInt(ob[6]
						       								.toString())));

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
