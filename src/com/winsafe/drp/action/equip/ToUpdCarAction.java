package com.winsafe.drp.action.equip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCar;
import com.winsafe.drp.dao.Car;
import com.winsafe.drp.dao.CarForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class ToUpdCarAction extends Action {

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
			AppCar aso = new AppCar();
			 
			Car o = aso.getCarByID(id);
		 
			o = aso.getCarByID(id);

			CarForm sof = new CarForm();
			sof.setId(o.getId());
			sof.setCarsortname(Internation.getSelectTagByKeyAllDBDef("CarSort",
					"carsort", o.getCarsort()));
			sof.setCarbrand(o.getCarbrand());
			sof.setWorth(o.getWorth());
			sof.setPurchasedatename(DateUtil.formatDate(o.getPurchasedate()));

			request.setAttribute("sof", sof);

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
