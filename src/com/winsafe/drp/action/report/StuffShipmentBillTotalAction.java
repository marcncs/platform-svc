package com.winsafe.drp.action.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppStuffShipmentBill;
import com.winsafe.drp.dao.AppStuffShipmentBillDetail;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.PurchaseBillTotal;
import com.winsafe.drp.dao.StuffShipmentBill;
import com.winsafe.drp.dao.StuffShipmentBillDetailForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class StuffShipmentBillTotalAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String Condition = " so.isaudit=1 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "StuffShipmentBill" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			whereSql = whereSql + timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			ArrayList str = new ArrayList();

			AppStuffShipmentBill aso = new AppStuffShipmentBill();
			AppStuffShipmentBillDetail asod = new AppStuffShipmentBillDetail();
			AppWarehouse ap = new AppWarehouse();
			List pils = aso.getStuffShipementBillTotal(whereSql);
			for (Iterator it = pils.iterator(); it.hasNext();) {
				PurchaseBillTotal pbt = new PurchaseBillTotal();
				StuffShipmentBill pb = (StuffShipmentBill) it.next();
				
				List sodls = asod.getStuffShipmentBillDetailBySbID(pb.getId());
				ArrayList alsod = new ArrayList();
				Double subsum = 0.00;
				for (int d = 0; d < sodls.size(); d++) {
					StuffShipmentBillDetailForm sodf = new StuffShipmentBillDetailForm();
					Object[] od = (Object[]) sodls.get(d);
					sodf.setProductid(od[2].toString());
					sodf.setProductname(String.valueOf(od[3]));
					sodf.setSpecmode(String.valueOf(od[4]));
					sodf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit", Integer.parseInt(od[5]
									.toString())));
					sodf.setUnitprice(Double.valueOf(od[7].toString()));
					sodf.setQuantity(Double.valueOf(od[8].toString()));
					sodf.setSubsum(Double.valueOf(od[9].toString()));
					subsum += sodf.getSubsum();
					alsod.add(sodf);
				}
//				pbt.setCname(ap.getWarehouseByID(pb.getWarehouseid())
//						.getWarehousename());
				pbt.setSubsum(subsum);
				pbt.setSodls(alsod);
				str.add(pbt);
			}
			UsersBean users = UserManager.getUser(request);
//			Long userid = users.getUserid();
			
			AppWarehouse aw = new AppWarehouse();
//			List wls = aw.getEnableWarehouseByVisit(userid);
//			ArrayList alw = new ArrayList();
//			for (int i = 0; i < wls.size(); i++) {
//				Warehouse w = new Warehouse();
//				Object[] o = (Object[]) wls.get(i);
//				w.setId(Long.valueOf(o[0].toString()));
//				w.setWarehousename(o[1].toString());
//				alw.add(w);
//			}
//			request.setAttribute("alw", alw);
			request.setAttribute("str", str);
			return mapping.findForward("stuffshipmentbilltotal");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
