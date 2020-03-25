package com.winsafe.drp.action.machin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppConsignMachin;
import com.winsafe.drp.dao.AppConsignMachinDetail;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.ConsignMachin;
import com.winsafe.drp.dao.ConsignMachinDetail;
import com.winsafe.drp.dao.ConsignMachinDetailForm;
import com.winsafe.drp.dao.ConsignMachinForm;
import com.winsafe.hbm.util.HtmlSelect;

public class ToConsignMachinTransPurchasePlanAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		try {

			AppConsignMachin aso = new AppConsignMachin();			
			ConsignMachin so = aso.getConsignMachinByID(id);
			if (so.getIsaudit() == 0) {
				String result = "databases.record.noapprove";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			if (so.getIsendcase() == 1) {
				String result = "databases.record.iscomplete";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			ConsignMachinForm sof = new ConsignMachinForm();
			sof.setId(so.getId());
			sof.setCproductid(so.getCproductid());
			sof.setCproductname(so.getCproductname());
			sof.setCspecmode(so.getCspecmode());
			sof.setCunitid(so.getCunitid());
			sof.setCunitidname(HtmlSelect.getResourceName(request, "CountUnit", so.getCunitid()));
			sof.setCquantity(so.getCquantity());
//			sof.setCdept(so.getCdept());
//			sof.setAdeptname(ad.getDeptByID(so.getAdept()).getDeptname());
			sof.setCompleteintenddate(String.valueOf(so.getCompleteintenddate()));
			sof.setRemark(so.getRemark());

			AppConsignMachinDetail asld = new AppConsignMachinDetail();
			List slls = asld.getConsignMachinDetailBySIID(id);
			ArrayList als = new ArrayList();
			ConsignMachinDetail ard = null;
			AppProductStockpile aps = new AppProductStockpile();
			Double advicequantity = 0d;
			
			for (int i = 0; i < slls.size(); i++) {
				ConsignMachinDetailForm sodf = new ConsignMachinDetailForm();
				ard = (ConsignMachinDetail) slls.get(i);
				sodf.setProductid(ard.getProductid());
				sodf.setProductname(ard.getProductname());
				sodf.setSpecmode(ard.getSpecmode());
				sodf.setUnitid(ard.getUnitid());
				sodf.setUnitidname(HtmlSelect.getResourceName(request,
						"CountUnit", ard.getUnitid()));
				//sodf.setUnitprice(ard.getUnitprice());
				sodf.setQuantity(Double.valueOf(ard.getQuantity()));
				sodf.setStockpile(aps.getSumByProductID(ard.getProductid()));
				advicequantity = sodf.getQuantity() - sodf.getStockpile();
				if(advicequantity <0.0){
					advicequantity =0d;
				}
				sodf.setAdvicequantity(advicequantity);
				als.add(sodf);
			}

			request.setAttribute("sof", sof);
			request.setAttribute("als", als);
			return mapping.findForward("toupd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
