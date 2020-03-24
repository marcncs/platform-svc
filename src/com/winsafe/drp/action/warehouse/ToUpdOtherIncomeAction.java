package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOtherIncome;
import com.winsafe.drp.dao.AppOtherIncomeDetail;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OtherIncome;

public class ToUpdOtherIncomeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);try{
			AppOtherIncome aoi = new AppOtherIncome();

			OtherIncome oi = aoi.getOtherIncomeByID(id);

			if (oi.getIsaudit() == 1) { 
				request.setAttribute("result", "databases.record.approvestatus");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppOtherIncomeDetail apid = new AppOtherIncomeDetail();
			List apils = apid.getOtherIncomeDetailByOiid(id);
			
			AppOrgan appo=new AppOrgan();
			Organ o=appo.getOrganByWarehouseid(oi.getWarehouseid());
			request.setAttribute("o", o);
			request.setAttribute("oif", oi);
			request.setAttribute("als", apils);
			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
