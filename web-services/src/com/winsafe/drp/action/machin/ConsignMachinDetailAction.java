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
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.ConsignMachin;
import com.winsafe.drp.dao.ConsignMachinDetail;
import com.winsafe.drp.dao.ConsignMachinDetailForm;
import com.winsafe.drp.dao.ConsignMachinForm;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.Internation;

public class ConsignMachinDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("ID");
		try {
			AppConsignMachin aso = new AppConsignMachin();
			AppUsers au = new AppUsers();
			AppProvider ap = new AppProvider();
			AppProductStockpile aps = new AppProductStockpile();
			ConsignMachin ar = aso.getConsignMachinByID(id);
			ConsignMachinForm arf = new ConsignMachinForm();
			arf.setId(ar.getId());
			arf.setPid(ar.getPid());
			arf.setPidname(ap.getProviderByID(ar.getPid()).getPname());
			arf.setPlinkman(ar.getPlinkman());
			arf.setTel(ar.getTel());
			arf.setPaymodename(Internation.getStringByKeyPosition("PayMode",
		            request,
		            ar.getPaymode(), "global.sys.SystemResource"));
			arf.setCtotalsum(ar.getCtotalsum());
			arf.setCproductid(ar.getCproductid());
			arf.setCproductname(ar.getCproductname());
			arf.setCspecmode(ar.getCspecmode());
			arf.setCunitidname(HtmlSelect.getResourceName(request, "CountUnit", ar.getCunitid()));
			arf.setCquantity(ar.getCquantity());
			arf.setCunitprice(ar.getCunitprice());
			arf.setCompletequantity(ar.getCompletequantity());
			arf.setCompleteintenddate(String.valueOf(ar.getCompleteintenddate()));
			arf.setRemark(ar.getRemark());
			arf.setMakeorganid(ar.getMakeorganid());
			arf.setMakedeptid(ar.getMakedeptid());
			arf.setMakeid(ar.getMakeid());
			arf.setMakedate(String.valueOf(ar.getMakedate()));
			arf.setIsaudit(ar.getIsaudit());
			arf.setIsauditname(Internation.getStringByKeyPosition("YesOrNo",
					request, ar.getIsaudit(), "global.sys.SystemResource"));
			arf.setAuditdate(ar.getAuditdate());
			if (ar.getAuditid() > 0) {
				arf.setAuditidname(au.getUsersByID(ar.getAuditid())
						.getRealname());
			} else {
				arf.setAuditidname("");
			}
			arf.setIsendcase(ar.getIsendcase());
			arf.setIsendcasename(Internation.getStringByKeyPosition("YesOrNo",
					request, ar.getIsendcase(), "global.sys.SystemResource"));

			AppConsignMachinDetail asld = new AppConsignMachinDetail();
			List sals = asld.getConsignMachinDetailBySIID(id);
			ArrayList als = new ArrayList();
			ConsignMachinDetail ard = null;
			for (int i = 0; i < sals.size(); i++) {
				ConsignMachinDetailForm sldf = new ConsignMachinDetailForm();
				ard = (ConsignMachinDetail) sals.get(i);
				sldf.setProductid(ard.getProductid());
				sldf.setProductname(ard.getProductname());
				sldf.setSpecmode(ard.getSpecmode());
				// padf.setUnitid(Integer.valueOf(o[3].toString()));
				sldf.setUnitidname(HtmlSelect.getResourceName(request, "CountUnit", ard.getUnitid()));
				sldf.setUnitprice(ard.getUnitprice());
				sldf.setQuantity(ard.getQuantity());
				sldf.setAlreadyquantity(ard.getAlreadyquantity());
				sldf.setStockpile(aps.getSumByProductID(ard.getProductid()));
				sldf.setSubsum(ard.getSubsum());
				sldf.setId(ard.getId());
				als.add(sldf);
			}

			request.setAttribute("als", als);
			request.setAttribute("arf", arf);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
