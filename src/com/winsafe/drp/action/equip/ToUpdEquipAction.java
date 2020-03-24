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
import com.winsafe.drp.dao.Car;
import com.winsafe.drp.dao.Equip;
import com.winsafe.drp.dao.EquipDetail;
import com.winsafe.drp.dao.EquipDetailForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.Internation;

public class ToUpdEquipAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");

		try {
			// if(DbUtil.judgeApproveStatusToRefer("SaleOrder", id)){
			// String result = "databases.record.approvestatus";
			// request.setAttribute("result", result);
			// return new
			// ActionForward("/sys/lockrecord.jsp");//;mapping.findForward("lock");
			// }
			AppEquip aso = new AppEquip();
			Equip so = aso.getEquipByID(id);

				
			AppInvoiceConf aic = new AppInvoiceConf();
			AppEquipDetail asld = new AppEquipDetail();
			List slls = asld.getEquipDetailByEID(id);
			ArrayList edls = new ArrayList();

			for (int i = 0; i < slls.size(); i++) {
				EquipDetailForm sodf = new EquipDetailForm();
				EquipDetail o = (EquipDetail) slls.get(i);
				sodf.setId(o.getId());
				sodf.setEid(o.getEid());
				sodf.setErasum(o.getErasum());
				sodf.setSbid(o.getSbid());
				sodf.setPaymentmode(o.getPaymentmode());
				sodf.setPaymentmodename(Internation.getStringByPayPositionDB(
						o.getPaymentmode()));
				sodf.setInvmsg(o.getInvmsg());
				sodf.setInvmsgname(aic.getInvoiceConfById(o.getInvmsg()).getIvname());
				sodf.setBillsum(o.getBillsum());
				edls.add(sodf);
			}
			 UsersBean users = UserManager.getUser(request);
			String makeorganid = users.getMakeorganid();
			AppCar ac=new AppCar();
			String Condition = " c.makeorganid = '" + makeorganid + "' ";
			List<Car> cls= ac.getAllCars(Condition);

			request.setAttribute("cls", cls);
			request.setAttribute("sof", so);
			request.setAttribute("edls", edls);

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
