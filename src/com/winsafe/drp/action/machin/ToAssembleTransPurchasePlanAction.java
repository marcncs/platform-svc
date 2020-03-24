package com.winsafe.drp.action.machin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppAssemble;
import com.winsafe.drp.dao.AppAssembleDetail;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.Assemble;
import com.winsafe.drp.dao.AssembleDetail;
import com.winsafe.drp.dao.AssembleDetailForm;
import com.winsafe.drp.dao.AssembleForm;
import com.winsafe.drp.server.DeptService;
import com.winsafe.hbm.util.Internation;

public class ToAssembleTransPurchasePlanAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		try {
			DeptService ad = new DeptService();
			AppAssemble aso = new AppAssemble();			
			Assemble so = aso.getAssembleByID(id);
			if (so.getIsaudit() == 0) {
				String result = "databases.record.noaudit";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			AssembleForm sof = new AssembleForm();
			sof.setId(so.getId());
			sof.setAproductid(so.getAproductid());
			sof.setAproductname(so.getAproductname());
			sof.setAspecmode(so.getAspecmode());
			sof.setAunitid(so.getAunitid());
			sof.setAunitidname(Internation.getStringByKeyPositionDB(
					"CountUnit", so.getAunitid()));
			sof.setAquantity(so.getAquantity());
			sof.setAdept(so.getAdept());
			sof.setAdeptname(ad.getDeptName(so.getAdept()));
			sof.setCompleteintenddate(so.getCompleteintenddate());
			sof.setRemark(so.getRemark());

			AppAssembleDetail asld = new AppAssembleDetail();
			List slls = asld.getAssembleDetailByAid(id);
			ArrayList als = new ArrayList();
			AssembleDetail ard = null;
			AppProductStockpile aps = new AppProductStockpile();
			Double advicequantity = 0d;
			
			for (int i = 0; i < slls.size(); i++) {
				AssembleDetailForm sodf = new AssembleDetailForm();
				ard = (AssembleDetail) slls.get(i);
				sodf.setProductid(ard.getProductid());
				sodf.setProductname(ard.getProductname());
				sodf.setSpecmode(ard.getSpecmode());
				sodf.setUnitid(ard.getUnitid());
				sodf.setUnitidname(Internation.getStringByKeyPositionDB(
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
