package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.AppDitch;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Ditch;
import com.winsafe.drp.dao.DitchForm;
import com.winsafe.hbm.util.Internation;

public class DitchDetailAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Long id = Long.valueOf(request.getParameter("id"));
		
//		Long userid = users.getUserid();
		AppDitch ad = new AppDitch();
		AppCountryArea aca = new AppCountryArea();
		AppUsers au = new AppUsers();

		try {

			Ditch d = ad.getDitchByID(id);
			DitchForm df = new DitchForm();
			df.setId(id);
			df.setDname(d.getDname());
			df.setTelone(d.getTelone());
			df.setTeltwo(d.getTeltwo());
			df.setFax(d.getFax());
			df.setProvince(d.getProvince());
			if (d.getProvince() > 0) {
//				df.setProvincename(aca.getAreaByID(
//						Long.valueOf(d.getProvince())).getAreaname());
			} else {
				df.setProvincename("");
			}
			df.setCity(d.getCity());
			if (d.getCity() > 0) {
//				df.setCityname(aca.getAreaByID(Long.valueOf(d.getCity()))
//						.getAreaname());
			} else {
				df.setCityname("");
			}
			df.setAreas(d.getAreas());
			if (d.getAreas() > 0) {
//				df.setAreasname(aca.getAreaByID(Long.valueOf(d.getAreas()))
//						.getAreaname());
			} else {
				df.setAreasname("");
			}
			df.setDetailaddr(d.getDetailaddr());
			df.setPostcode(d.getPostcode());
			df.setEmail(d.getEmail());
			df.setHomepage(d.getHomepage());
			// df.setDitchrank(d.getDitchrank());
			df.setDitchrankname(Internation.getStringByKeyPositionDB(
					"DitchRank", d.getDitchrank()));
			// df.setPrestige(d.getPrestige());
			df.setPrestigename(Internation.getStringByKeyPositionDB("Prestige",
					d.getPrestige()));
			df.setScale(d.getScale());
			// df.setVocation(d.getVocation());
			df.setVocationname(Internation.getStringByKeyPositionDB("Vocation",
					d.getVocation()));
			df.setTaxcode(d.getTaxcode());
			df.setBankname(d.getBankname());
			df.setDoorname(d.getDoorname());
			df.setBankaccount(d.getBankaccount());
			df.setMemo(d.getMemo());
//			df.setUseridname(au.getUsersByid(d.getUserid()).getRealname());
//			df.setMakeidname(au.getUsersByid(d.getMakeid()).getRealname());
			df.setMakedate(String.valueOf(d.getMakedate()));

			request.setAttribute("d", df);
//			DBUserLog.addUserLog(userid, "渠道资料详情,编号:" + id);
			return mapping.findForward("info");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}

}
