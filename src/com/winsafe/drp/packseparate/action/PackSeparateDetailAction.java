package com.winsafe.drp.packseparate.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.packseparate.dao.AppPackSeparate;
import com.winsafe.drp.packseparate.dao.AppPackSeparateDetail;
import com.winsafe.drp.packseparate.pojo.PackSeparate;
import com.winsafe.drp.packseparate.pojo.PackSeparateDetail;
import com.winsafe.drp.util.ArithDouble;

public class PackSeparateDetailAction extends BaseAction {

	private static Logger logger = Logger.getLogger(PackSeparateDetailAction.class);
	private AppPackSeparateDetail apsd = new AppPackSeparateDetail();
	private AppPackSeparate aps = new AppPackSeparate();
	private AppProduct ap = new AppProduct();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String psid = request.getParameter("ID");

		super.initdata(request);
		try {

			PackSeparate ps = aps.getPackSeparateById(psid);
			List<PackSeparateDetail> psdList = apsd
					.getPackSeparateDetailsByPsid(psid);
			List<PackSeparateDetail> psdfListNew = new ArrayList<PackSeparateDetail>();
			
			for(PackSeparateDetail psd : psdList) {
				PackSeparateDetail psdNew = (PackSeparateDetail) BeanUtils.cloneBean(psd);
				Product outProduct = ap.loadProductById(psd.getOutProductId());
				psdNew.setOutQuantity(ArithDouble.mul(psd.getOutQuantity(), outProduct.getBoxquantity()));
				psdNew.setOutUnitId(outProduct.getCountunit());
				psdNew.setWastage(ArithDouble.mul(psd.getWastage(), outProduct.getBoxquantity()));
				psdNew.setProductName(outProduct.getProductname());
				psdfListNew.add(psdNew);
			}

			request.setAttribute("als", psdfListNew);
			request.setAttribute("oif", ps);

			return mapping.findForward("detail");
		} catch (Exception e) {
			logger.error("",e);
		}
		return new ActionForward(mapping.getInput());
	}
}
