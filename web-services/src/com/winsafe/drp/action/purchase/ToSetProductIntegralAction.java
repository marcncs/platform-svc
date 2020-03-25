package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductIntegral;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.ProductIntegral;
import com.winsafe.drp.dao.ProductIntegralForm;
import com.winsafe.drp.dao.SetProductIntegral;
import com.winsafe.hbm.util.Internation;

public class ToSetProductIntegralAction extends Action {

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
			
			AppBaseResource abr = new AppBaseResource();
			List<BaseResource> brls = abr.getBaseResource("SaleSort");
			
			AppProductIntegral api = new AppProductIntegral();
			
			ArrayList spals = new ArrayList();
			AppFUnit af = new AppFUnit();
			List fuls = af.getFUnitByProductID(pid);

			for (int i = 0; i < fuls.size(); i++) {
				FUnit ob = (FUnit) fuls.get(i);
				SetProductIntegral spi = new SetProductIntegral();
				ArrayList apls = new ArrayList();

				for (BaseResource br : brls ) {
					ProductIntegralForm ppf = new ProductIntegralForm();
					ppf.setSalesort(br.getTagsubkey());
					ppf.setSalesortname(br.getTagsubvalue());
					Double pintegral = 0d;
					Double pintegralrate = 1d;
					ProductIntegral pp = api.getProductIntegralByPIDUIDSID(pid,
							 ob.getFunitid(),ppf.getSalesort());
					if(pp!=null){
						pintegral =pp.getIntegral();
						pintegralrate = pp.getIntegralrate();
					}
					ppf.setIntegral(pintegral);
					ppf.setIntegralrate(pintegralrate*100);
					apls.add(ppf);
				}
				spi.setPpls(apls);
				//spi.setSalesortid();
				spi.setUnitid(ob.getFunitid());
				spi.setUnitidname(Internation.getStringByKeyPositionDB(
					 "CountUnit", ob.getFunitid()));

				spals.add(spi);
			}


			request.setAttribute("spals", spals);

			return mapping.findForward("toadd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
