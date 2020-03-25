package com.winsafe.drp.action.machin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppAssembleRelation;
import com.winsafe.drp.dao.AppAssembleRelationDetail;
import com.winsafe.drp.dao.AssembleRelation;
import com.winsafe.drp.dao.AssembleRelationDetail;
import com.winsafe.drp.dao.AssembleRelationDetailForm;
import com.winsafe.drp.dao.AssembleRelationForm;
import com.winsafe.hbm.util.Internation;

public class ToUpdAssembleRelationAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		try {
			
			AppAssembleRelation aso = new AppAssembleRelation();			
			AssembleRelation so = aso.getAssembleRelationByID(id);
			if (so.getIsaudit() == 1) {
				String result = "databases.record.lock";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			AssembleRelationForm sof = new AssembleRelationForm();
			sof.setId(so.getId());
			sof.setArproductid(so.getArproductid());
			sof.setArproductname(so.getArproductname());
			sof.setArspecmode(so.getArspecmode());
			sof.setArunitid(so.getArunitid());
			sof.setArunitidname(Internation.getStringByKeyPositionDB(
					"CountUnit", so.getArunitid()));
			sof.setArquantity(so.getArquantity());
			sof.setRemark(so.getRemark());

			AppAssembleRelationDetail asld = new AppAssembleRelationDetail();
			List slls = asld.getAssembleRelationDetailBySIID(id);
			ArrayList als = new ArrayList();
			AssembleRelationDetail ard = null;
			
			for (int i = 0; i < slls.size(); i++) {
				AssembleRelationDetailForm sodf = new AssembleRelationDetailForm();
				ard = (AssembleRelationDetail) slls.get(i);
				sodf.setProductid(ard.getProductid());
				sodf.setProductname(ard.getProductname());
				sodf.setSpecmode(ard.getSpecmode());
				sodf.setUnitid(ard.getUnitid());
				sodf.setUnitidname(Internation.getStringByKeyPositionDB(
						"CountUnit", ard.getUnitid()));
				sodf.setUnitprice(Double.valueOf(ard.getUnitprice()));
				sodf.setQuantity(Double.valueOf(ard.getQuantity()));
				sodf.setSubsum(Double.valueOf(ard.getSubsum()));
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
