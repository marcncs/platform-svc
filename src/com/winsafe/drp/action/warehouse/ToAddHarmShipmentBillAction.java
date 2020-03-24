package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;

public class ToAddHarmShipmentBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

//			
//			AppWarehouse aw = new AppWarehouse();
//			List wls = aw.getEnableWarehouseByVisit(userid);
//			ArrayList alw = new ArrayList();
//			for (int i = 0; i < wls.size(); i++) {
//				Warehouse w = new Warehouse();
//				Object[] o = (Object[]) wls.get(i);
//				w.setId(Long.valueOf(o[0].toString()));
//				w.setWarehousename(o[1].toString());
//				alw.add(w);
//			}
			
//			AppDept ad = new AppDept();
//			List dls = ad.getDeptByOID(users.getMakeorganid());
//			ArrayList aldept = new ArrayList();
//			for (int i = 0; i < dls.size(); i++) {
//				//Dept d = new Dept();
//				Dept ob = (Dept) dls.get(i);
////				d.setId(Long.valueOf(ob[0].toString()));
////				d.setDeptname(ob[1].toString());
//				aldept.add(ob);
//			}
//			
//			request.setAttribute("alw", alw);
//			request.setAttribute("aldept", aldept);
			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
