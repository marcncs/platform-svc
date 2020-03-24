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
import com.winsafe.drp.dao.AppProductPrice;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.ProductPrice;
import com.winsafe.drp.dao.ProductPriceForm;
import com.winsafe.drp.dao.SetProductPrice;
import com.winsafe.hbm.util.Internation;

public class ToSetProductPriceAction extends Action {

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

			AppProductPrice app = new AppProductPrice();
			AppBaseResource abr = new AppBaseResource();
			List<BaseResource> plcyls = abr.getBaseResource("PricePolicy");
			ArrayList spals = new ArrayList();
			

			AppFUnit af = new AppFUnit();
			List fuls = af.getFUnitByProductID(pid);

			for (int i = 0; i < fuls.size(); i++) {
				FUnit ob = (FUnit) fuls.get(i);
				SetProductPrice spp = new SetProductPrice();
				ArrayList apls = new ArrayList();

				for (BaseResource br : plcyls) {
					ProductPriceForm ppf = new ProductPriceForm();
					ppf.setPolicyid(Integer.valueOf(br.getTagsubkey()));
					ppf.setPolicyidname(br.getTagsubvalue());
					Double punitprice = 0d;
					ProductPrice pp = app.getProdutPriceByPlcyPIDUnitID(
							ppf.getPolicyid(), pid, ob.getFunitid());
					if(pp!=null){
					punitprice =pp.getUnitprice();
					}
					ppf.setUnitprice(punitprice);
					apls.add(ppf);
				}
				spp.setPpls(apls);
				spp.setUnitid(ob.getFunitid());
				spp.setUnitidname(Internation.getStringByKeyPositionDB(
					 "CountUnit", ob.getFunitid()));

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
