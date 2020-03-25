package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppHarmShipmentBill;
import com.winsafe.drp.dao.AppHarmShipmentBillDetail;
import com.winsafe.drp.dao.HarmShipmentBill;
import com.winsafe.drp.dao.HarmShipmentBillDetail;
import com.winsafe.drp.dao.HarmShipmentBillDetailForm;
import com.winsafe.hbm.util.Internation;

public class ToUpdHarmShipmentBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);
		try {
			AppHarmShipmentBill aosb = new AppHarmShipmentBill();

//			AppWarehouse aw = new AppWarehouse();
//			AppDept ad = new AppDept();
			HarmShipmentBill osb = aosb.getHarmShipmentBillByID(id);
			if (osb.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.lock");
				return mapping.findForward("lock");
			}
			if ( osb.getIsblankout() == 1){
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return mapping.findForward("lock");
			}
			
//			HarmShipmentBillForm osbf = new HarmShipmentBillForm();
//			osbf.setId(id);
//			osbf.setWarehouseid(osb.getWarehouseid());	
//			osbf.setShipmentdept(osb.getShipmentdept());			
//			osbf.setHarmdate(DateUtil.formatDate(osb.getHarmdate()));
//			osbf.setTotalsum(osb.getTotalsum());
//			osbf.setRemark(osb.getRemark());

			AppHarmShipmentBillDetail asbd = new AppHarmShipmentBillDetail();
			List slls = asbd.getHarmShipmentBillDetailBySbID(id);
			ArrayList als = new ArrayList();

			HarmShipmentBillDetail osbd = null;
			for (int i = 0; i < slls.size(); i++) {
				osbd = (HarmShipmentBillDetail)slls.get(i);
				HarmShipmentBillDetailForm osbdf = new HarmShipmentBillDetailForm();			
				osbdf.setProductid(osbd.getProductid());
				osbdf.setProductname(osbd.getProductname());
				osbdf.setSpecmode(osbd.getSpecmode());
				osbdf.setUnitid(osbd.getUnitid());
				osbdf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit", 
						Integer.parseInt(osbd.getUnitid().toString())));
				osbdf.setBatch(osbd.getBatch());
				osbdf.setUnitprice(osbd.getUnitprice());
				osbdf.setQuantity(osbd.getQuantity());
				osbdf.setSubsum(osbd.getSubsum());
				als.add(osbdf);
			}
			
//
//		      List wls = aw.getEnableWarehouseByVisit(userid);
//		      ArrayList alw = new ArrayList();
//		      for(int i=0;i<wls.size();i++){
//		        Warehouse w = new Warehouse();
//		        Object[] o = (Object[])wls.get(i);
//		        w.setId(Long.valueOf(o[0].toString()));
//		        w.setWarehousename(o[1].toString());
//		        alw.add(w);
//		      }
//
//		    List aldept = ad.getDeptByOID(usersBean.getMakeorganid());
			

//			request.setAttribute("alw",alw);
			request.setAttribute("osbf", osb);
			request.setAttribute("als", als);
//			request.setAttribute("aldept", aldept);
			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
