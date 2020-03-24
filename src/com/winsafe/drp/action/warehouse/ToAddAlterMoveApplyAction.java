package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.InvoiceConf;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.hbm.util.Internation;

public class ToAddAlterMoveApplyAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);try{
			UsersBean users = UserManager.getUser(request);
//			Long userid = users.getUserid();

//			
			AppOrgan ao = new AppOrgan();
//			String organid = users.getMakeorganid();
//			String organname = ao.getOrganByID(users.getMakeorganid())
//					.getOrganname();

			
			AppWarehouse aw = new AppWarehouse();
			List wls = aw.getWarehouseListByOID(users.getMakeorganid());
			ArrayList alw = new ArrayList();
			for (int i = 0; i < wls.size(); i++) {
				// Warehouse w = new Warehouse();
				Warehouse o = (Warehouse) wls.get(i);
				// w.setId(Long.valueOf(o[0].toString()));
				// w.setWarehousename(o[1].toString());
				alw.add(o);
			}

			
			List ols = ao.getAllOrgan();
			ArrayList alos = new ArrayList();
			for (int o = 0; o < ols.size(); o++) {
				OrganForm ub = new OrganForm();
				Organ of = (Organ) ols.get(o);
				ub.setId(of.getId());
				ub.setOrganname(of.getOrganname());
				alos.add(ub);
			}

			request.setAttribute("alos", alos);

			// 
			// List iwls = aw.getEnableWarehouse();
			// ArrayList aliw = new ArrayList();
			// for (int i = 0; i < iwls.size(); i++) {
			// Warehouse iw = new Warehouse();
			// Object[] o = (Object[]) iwls.get(i);
			// iw.setId(Long.valueOf(o[0].toString()));
			// iw.setWarehousename(o[1].toString());
			// aliw.add(iw);
			// }

			AppInvoiceConf aic = new AppInvoiceConf();
			List uls = aic.getAllInvoiceConf();
			ArrayList icls = new ArrayList();
			for (int u = 0; u < uls.size(); u++) {
				InvoiceConf ic = (InvoiceConf) uls.get(u);

				icls.add(ic);
			}
			request.setAttribute("icls", icls);

			String transportmodeselect = Internation.getSelectTagByKeyAllDB(
					"TransportMode", "transportmode", false);
			String paymentmodeselect = Internation.getSelectPayByAllDB(
					"paymentmode", false);
			String ticketselect = Internation.getSelectTagByKeyAll("YesOrNo",
					request, "isneedticket", false, null);
			request.setAttribute("ticketselect", ticketselect);
			request.setAttribute("transportmodeselect", transportmodeselect);
			request.setAttribute("paymentmodeselect", paymentmodeselect);

			request.setAttribute("alw", alw);
			// request.setAttribute("aliw", aliw);
			// request.setAttribute("pid",pid);

			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
