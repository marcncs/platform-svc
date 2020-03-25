package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleIndent;
import com.winsafe.drp.dao.AppSaleIndentDetail;
import com.winsafe.drp.dao.SaleIndent;
import com.winsafe.drp.dao.SaleIndentDetail;
import com.winsafe.drp.dao.SaleIndentDetailForm;
import com.winsafe.hbm.util.Internation;

public class SaleIndentDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("ID");

		try {
			AppSaleIndent aso = new AppSaleIndent();
			SaleIndent so = aso.getSaleIndentByID(id);
			AppSaleIndentDetail asld = new AppSaleIndentDetail();
			List sals = asld.getSaleIndentDetailBySIID(id);
			ArrayList als = new ArrayList();
			for (int i = 0; i < sals.size(); i++) {
				SaleIndentDetailForm sldf = new SaleIndentDetailForm();
				SaleIndentDetail o = (SaleIndentDetail) sals.get(i);
				sldf.setProductid(o.getProductid());
				sldf.setProductname(o.getProductname());
				sldf.setSpecmode(o.getSpecmode());
				// padf.setUnitid(Integer.valueOf(o[3].toString()));
				sldf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", (Integer.parseInt(String.valueOf(o
								.getUnitid())))));
				sldf.setUnitprice(Double.valueOf(o.getUnitprice()));
				sldf.setQuantity(Double.valueOf(o.getQuantity()));
				sldf.setSubsum(Double.valueOf(o.getSubsum()));
				sldf.setCost(o.getCost());
				sldf.setDiscount(o.getDiscount());
				sldf.setId(o.getId());
				sldf.setTaxrate(o.getTaxrate());
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
