package com.winsafe.drp.action.machin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOtherShipmentBillAll;
import com.winsafe.drp.dao.AppOtherShipmentBillDAll;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.OtherShipmentBillAll;
import com.winsafe.drp.dao.OtherShipmentBillDAll;
import com.winsafe.drp.dao.OtherShipmentBillDetailForm;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.util.ArithDouble;

public class RedShipmentBillDetailAction extends BaseAction {
	private AppProduct ap = new AppProduct();
	private AppBaseResource appBr = new AppBaseResource();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("ID");
		AppProduct appProduct = new AppProduct();
		AppFUnit appFUnit = new AppFUnit();
		super.initdata(request);
		try {
			AppOtherShipmentBillAll aosb = new AppOtherShipmentBillAll();
			OtherShipmentBillAll osb = aosb.getOtherShipmentBillByID(id);
			if (osb == null) {
				request.setAttribute("result", "databases.record.delete");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppOtherShipmentBillDAll aosbd = new AppOtherShipmentBillDAll();
			List<OtherShipmentBillDAll> sals = aosbd.getOtherShipmentBillDetailBySbID(id);
			List showList = new ArrayList();
			Map<Integer, String> countUnitMap = appBr.getBaseResourceMap("CountUnit");
			for(OtherShipmentBillDAll detail : sals){
				OtherShipmentBillDetailForm oidf = new OtherShipmentBillDetailForm();
				oidf.setBatch(detail.getBatch());
				oidf.setId(Long.valueOf(detail.getId()));
				oidf.setOsid(detail.getOsid());
				oidf.setProductid(detail.getProductid());
				oidf.setProductname(detail.getProductname());
				oidf.setQuantity(detail.getQuantity());
				oidf.setSpecmode(detail.getSpecmode());
				oidf.setUnitid(detail.getUnitid());
				String nccode = ap.getMcodeById(detail.getProductid());
				oidf.setNccode(nccode);
				
				//换算计量单位
				Double cUnitQuantity = 0d;
				Product product = appProduct.loadProductById(detail.getProductid());
				double xQuantity = appFUnit.getXQuantity(detail.getProductid(), detail.getUnitid());
				if(xQuantity != 0d) {
					cUnitQuantity = ArithDouble.mul(detail.getQuantity(), xQuantity);
				}
				if(product != null && product.getBoxquantity() != null) {
					cUnitQuantity = ArithDouble.mul(cUnitQuantity, product.getBoxquantity());
					oidf.setCountUnitName(countUnitMap.get(product.getCountunit()));
				}
				oidf.setcUnitQuantity(cUnitQuantity);
				
				showList.add(oidf);
			}
			request.setAttribute("als", showList);
			request.setAttribute("osbf", osb);

			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
