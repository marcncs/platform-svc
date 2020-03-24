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
import com.winsafe.hbm.util.Internation;

public class AssembleDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("ID");

		try {
			AppAssemble aso = new AppAssemble();
			
			Assemble ar = aso.getAssembleByID(id);
//			AssembleForm arf = new AssembleForm();
//
//			arf.setId(ar.getId());
//			arf.setAproductid(ar.getAproductid());
//			arf.setAproductname(ar.getAproductname());
//			arf.setAspecmode(ar.getAspecmode());
//			arf.setAunitid(ar.getAunitid());
//			arf.setAunitidname(Internation.getStringByKeyPositionDB(
//					"CountUnit", ar.getAunitid(),request));
//			arf.setAquantity(ar.getAquantity());
//			arf.setCquantity(ar.getCquantity());
//			arf.setAdept(ar.getAdept());
//			arf.setAdeptname(ad.getDeptName(ar.getAdept(),getLan(request)));
//			arf.setCompleteintenddate(String.valueOf(ar.getCompleteintenddate()));
//			arf.setRemark(ar.getRemark());
//			arf.setMakeid(ar.getMakeid());
//			arf.setMakeidname(au.getUsersAllByID(ar.getMakeid()).getRealname());
//			arf.setMakedate(ar.getMakedate());
//			arf.setIsaudit(ar.getIsaudit());
//			arf.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
//					request, ar.getIsaudit(), "global.sys.SystemResource"));
//			arf.setAuditdate(ar.getAuditdate());
//			if (ar.getAuditid() > 0) {
//				arf.setAuditidname(au.getUsersAllByID(ar.getAuditid())
//						.getRealname());
//			} else {
//				arf.setAuditidname("");
//			}
//			arf.setIsendcase(ar.getIsendcase());
//			arf.setIsendcasename(Internation.getStringByKeyPosition("YesOrNo",
//					request, ar.getIsendcase(), "global.sys.SystemResource"));

			AppProductStockpile aps = new AppProductStockpile();
			AppAssembleDetail asld = new AppAssembleDetail();
			List sals = asld.getAssembleDetailByAid(id);
			ArrayList als = new ArrayList();
			AssembleDetail ard = null;
			for (int i = 0; i < sals.size(); i++) {
				AssembleDetailForm sldf = new AssembleDetailForm();
				ard = (AssembleDetail) sals.get(i);
				sldf.setProductid(ard.getProductid());
				sldf.setProductname(ard.getProductname());
				sldf.setSpecmode(ard.getSpecmode());
				sldf.setUnitid(ard.getUnitid());
				sldf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", ard.getUnitid()));
				//sldf.setUnitprice(ard.getUnitprice());
				sldf.setQuantity(ard.getQuantity());
				sldf.setStockpile(aps.getSumByProductID(ard.getProductid()));
				sldf.setAlreadyquantity(ard.getAlreadyquantity());
				//sldf.setSubsum(ard.getSubsum());
				sldf.setId(ard.getId());
				als.add(sldf);
			}

			request.setAttribute("als", als);
			request.setAttribute("arf", ar);
			
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
