package com.winsafe.drp.action.equip;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCar;
import com.winsafe.drp.dao.AppEquip;
import com.winsafe.drp.dao.AppEquipDetail;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.Equip;
import com.winsafe.drp.dao.EquipDetail;
import com.winsafe.drp.dao.EquipDetailForm;
import com.winsafe.hbm.util.Internation;

public class EquipDetailAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		try {
			AppEquip aso = new AppEquip();
			AppInvoiceConf aic = new AppInvoiceConf();
			Equip so = aso.getEquipByID(id);
			AppCar car = new AppCar();
			so.setCarbrand(car.getCarByID(so.getCarbrand()).getCarbrand());

			AppEquipDetail asld = new AppEquipDetail();
			List sals = asld.getEquipDetailByEID(id);
			ArrayList als = new ArrayList();		

			for (int i = 0; i < sals.size(); i++) {
				EquipDetailForm sldf = new EquipDetailForm();
				EquipDetail o = (EquipDetail) sals.get(i);
				sldf.setId(o.getId());
				sldf.setEid(o.getEid());
				sldf.setSbid(o.getSbid());
				sldf.setPaymentmodename(Internation.getStringByPayPositionDB(
						o.getPaymentmode()));	
				sldf.setInvmsgname(aic.getInvoiceConfById(o.getInvmsg()).getIvname());
				sldf.setBillsum(o.getBillsum());
				sldf.setErasum(o.getErasum());

				als.add(sldf);
			}

			request.setAttribute("als", als);
			request.setAttribute("sof", so);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
