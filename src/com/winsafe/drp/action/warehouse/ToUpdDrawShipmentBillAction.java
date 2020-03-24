package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppDrawShipmentBill;
import com.winsafe.drp.dao.AppDrawShipmentBillDetail;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.DrawShipmentBill;
import com.winsafe.drp.dao.DrawShipmentBillDetail;
import com.winsafe.drp.dao.DrawShipmentBillDetailForm;
import com.winsafe.drp.dao.DrawShipmentBillForm;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class ToUpdDrawShipmentBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);
		try {
			AppDrawShipmentBill aosb = new AppDrawShipmentBill();

			AppWarehouse appWarehouse = new AppWarehouse();
			AppOrgan appOrgan = new AppOrgan();
			DrawShipmentBill osb = aosb.getDrawShipmentBillByID(id);
			if (osb.getIsaudit() == 1) {
				String result = "databases.record.lock";
				request.setAttribute("result", result);
				return mapping.findForward("lock");
			}
			if ( osb.getIsblankout() == 1){
				String result = "databases.record.blankoutnooperator";
				request.setAttribute("result", result);
				return mapping.findForward("lock");
			}
			DrawShipmentBillForm osbf = new DrawShipmentBillForm();
			osbf.setId(id);
			//仓库id
			osbf.setWarehouseid(osb.getWarehouseid());
			//得到仓库
			Warehouse warehouse = appWarehouse.getWarehouseByID(osb.getWarehouseid());
			//得到仓库所在的机构
			Organ organ = appOrgan.getOrganByID(warehouse.getMakeorganid());
			//设置仓库名字
			osbf.setWarehouseidname(warehouse.getWarehousename());
			//设置机构,机构名字
			osbf.setOrganId(organ.getId());
			osbf.setOrganName(organ.getOrganname());
			osbf.setDrawdate(DateUtil.formatDate(osb.getDrawdate()));
			osbf.setTotalsum(osb.getTotalsum());
			osbf.setRemark(osb.getRemark());

			AppDrawShipmentBillDetail asbd = new AppDrawShipmentBillDetail();
			List slls = asbd.getDrawShipmentBillDetailByDsid(id);
			ArrayList als = new ArrayList();

			DrawShipmentBillDetail osbd = null;
			for (int i = 0; i < slls.size(); i++) {
				osbd = (DrawShipmentBillDetail)slls.get(i);
				DrawShipmentBillDetailForm osbdf = new DrawShipmentBillDetailForm();			
				osbdf.setProductid(osbd.getProductid());
				osbdf.setProductname(osbd.getProductname());
				osbdf.setSpecmode(osbd.getSpecmode());
				osbdf.setUnitid(osbd.getUnitid());
				osbdf.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit",
						Integer.parseInt(osbd.getUnitid().toString())));
				//osbdf.setBatch(osbd.getBatch());
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

//		    List aldept = ad.getDeptByOID(usersBean.getMakeorganid());
//			
//			AppUsers au = new AppUsers();
//			List users = au.getIDAndLoginNameByOID2(usersBean.getMakeorganid());
			

//			request.setAttribute("alw",alw);
			request.setAttribute("osbf", osbf);
			request.setAttribute("als", als);
//			request.setAttribute("aldept", aldept);
//			request.setAttribute("users", users);
			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
