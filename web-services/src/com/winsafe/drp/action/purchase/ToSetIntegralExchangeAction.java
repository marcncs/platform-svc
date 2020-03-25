package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIntegralExchange;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.IntegralExchange;
import com.winsafe.drp.dao.IntegralExchangeForm;
import com.winsafe.hbm.util.Internation;

public class ToSetIntegralExchangeAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pid = (String) request.getSession().getAttribute("pid");
		try {
			//AppFUnit afu = new AppFUnit();
			AppProduct ap = new AppProduct();
			String productname = ap.getProductByID(pid).getProductname();

			request.setAttribute("pid", pid);
			request.setAttribute("productname", productname);

			AppIntegralExchange app = new AppIntegralExchange();

			ArrayList spals = new ArrayList();
			

			AppFUnit af = new AppFUnit();
			List fuls = af.getFUnitByProductID(pid);

			for (int i = 0; i < fuls.size(); i++) {
				FUnit ob = (FUnit) fuls.get(i);
				IntegralExchangeForm spp = new IntegralExchangeForm();

				spp.setUnitid(ob.getFunitid());
				spp.setUnitidname(Internation.getStringByKeyPositionDB(
					 "CountUnit", ob.getFunitid()));
				Double unitintegral=0d;
				IntegralExchange ie = app.getIntegralExchangeByPIDUnitID(pid, spp.getUnitid());
				if(ie!=null){
					unitintegral= ie.getUnitintegral();
				}
				spp.setUnitintegral(unitintegral);

				spals.add(spp);
			}


			request.setAttribute("spals", spals);
			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
