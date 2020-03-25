package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.AppShipmentBillDetail;
import com.winsafe.drp.dao.SaleShipmentTotal;
import com.winsafe.drp.dao.ShipmentBillDetail;
import com.winsafe.drp.dao.ShipmentBillDetailForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class SaleShipmentTotalAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try{
			AppCustomer ac = new AppCustomer();
			String Condition=" sb.isaudit=1 " ;
			 Map map = new HashMap(request.getParameterMap());
		      Map tmpMap = EntityManager.scatterMap(map);
		      String[] tablename={"ShipmentBill"};
		      String whereSql = EntityManager.getTmpWhereSql(map, tablename);

		      String timeCondition = DbUtil.getTimeCondition(map, tmpMap," MakeDate");
		      whereSql = whereSql + timeCondition + Condition; 
		      whereSql = DbUtil.getWhereSql(whereSql); 
		      
			ArrayList str= new ArrayList();
			
			
			AppShipmentBill asb = new AppShipmentBill();
			AppShipmentBillDetail asod=new AppShipmentBillDetail(); 
			List pils = asb.getShipmentBillTotal(whereSql);
		      for (int i = 0; i < pils.size(); i++) {
		        //SaleOrderForm sof = new SaleOrderForm();
		    	  SaleShipmentTotal sot = new SaleShipmentTotal();
		        Object[] o = (Object[]) pils.get(i);
		        //sof.setId(o[0].toString());
		        //sof.setCid(Long.valueOf(o[1].toString()));
		        //sof.setCname(String.valueOf(o[2]));
		        //sof.setMakedate((Date)o[3]);
		        //also.add(sof);
		        sot.setCname(ac.getCustomer(o[1].toString()).getCname());
		        
		        List sodls = asod.getShipmentBillDetailBySbID(o[0].toString());
		        ArrayList alsod=new ArrayList();
		        Double subsum=0.00;
		        ShipmentBillDetail sbd = new ShipmentBillDetail();
		        for(int d = 0;d<sodls.size();d++){
		        	ShipmentBillDetailForm sodf = new ShipmentBillDetailForm();
		        	sbd = (ShipmentBillDetail)sodls.get(d);
		        	sodf.setProductid(sbd.getProductid());
		        	sodf.setProductname(sbd.getProductname());
		        	sodf.setSpecmode(sbd.getSpecmode());
		        	sodf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit",		                   
		                    sbd.getUnitid().intValue()));
		        	sodf.setUnitprice(sbd.getUnitprice());
		        	sodf.setQuantity(sbd.getQuantity());
		        	sodf.setSubsum(sbd.getSubsum());
		        	subsum += sodf.getSubsum();
		        	alsod.add(sodf);
		        }
		        sot.setSbdls(alsod);
		        sot.setSubsum(subsum);
		        str.add(sot);
		      }
		      request.setAttribute("str", str);
		      return mapping.findForward("saleshipmenttotal");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
