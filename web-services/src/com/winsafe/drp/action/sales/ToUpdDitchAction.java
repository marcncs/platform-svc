package com.winsafe.drp.action.sales;
//package com.winsafe.drp.action.sales;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.struts.action.Action;
//import org.apache.struts.action.ActionForm;
//import org.apache.struts.action.ActionForward;
//import org.apache.struts.action.ActionMapping;
//
//import com.winsafe.drp.dao.AppCountryArea;
//import com.winsafe.drp.dao.AppDitch;
//import com.winsafe.drp.dao.AppUsers;
//import com.winsafe.drp.dao.CountryArea;
//import com.winsafe.drp.dao.Ditch;
//import com.winsafe.drp.dao.DitchForm;
//import com.winsafe.drp.dao.UserManager;
//import com.winsafe.drp.dao.UsersBean;
//import com.winsafe.drp.util.Internation;
//
//public class ToUpdDitchAction extends BaseAction {
//	public ActionForward execute(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//
//		Long id = Long.valueOf(request.getParameter("id"));
//		
////		Long userid = users.getUserid();
//		AppDitch ad = new AppDitch();
//		Ditch d = new Ditch();
//		AppCountryArea aca = new AppCountryArea();
//
//		try {
//			d = ad.getDitchByID(id);
//
//			List list0 = aca.getProvince();
//
//			ArrayList cals = new ArrayList();
//
//			for (int i = 0; i < list0.size(); i++) {
//				CountryArea ca = new CountryArea();
//				Object ob[] = (Object[]) list0.get(i);
//				ca.setId(Long.valueOf(ob[0].toString()));
//				ca.setAreaname(ob[1].toString());
//				ca.setParentid(Long.valueOf(ob[2].toString()));
//				ca.setRank(Integer.valueOf(ob[3].toString()));
//				cals.add(ca);
//			}
//
//			AppUsers au = new AppUsers();
//			DitchForm df = new DitchForm();
//			df.setId(id);
//			df.setDname(d.getDname());
//			df.setTelone(d.getTelone());
//			df.setTeltwo(d.getTeltwo());
//			df.setFax(d.getFax());
//			df.setProvince(d.getProvince());
//			if (d.getProvince() > 0) {
//				df.setProvincename(aca.getAreaByID(
//						Long.valueOf(d.getProvince())).getAreaname());
//			} else {
//				df.setProvincename("");
//			}
//			df.setCity(d.getCity());
//			if (d.getCity() > 0) {
//				df.setCityname(aca.getAreaByID(Long.valueOf(d.getCity()))
//						.getAreaname());
//			} else {
//				df.setCityname("");
//			}
//			df.setAreas(d.getAreas());
//			if (d.getAreas() > 0) {
//				df.setAreasname(aca.getAreaByID(Long.valueOf(d.getAreas()))
//						.getAreaname());
//			} else {
//				df.setAreasname("");
//			}
//			df.setDetailaddr(d.getDetailaddr());
//			df.setPostcode(d.getPostcode());
//			df.setEmail(d.getEmail());
//			df.setHomepage(d.getHomepage());
//			//df.setDitchrank(d.getDitchrank());
//			df.setDitchrankname(Internation.getSelectTagByKeyAllDBDef(
//					"DitchRank", "ditchrank", d.getDitchrank()));
//			//df.setPrestige(d.getPrestige());
//			df.setPrestigename(Internation.getSelectTagByKeyAllDBDef(
//					"Prestige", "prestige", d.getPrestige()));
//			df.setScale(d.getScale());
//			//df.setVocation(d.getVocation());
//			df.setVocationname(Internation.getSelectTagByKeyAllDBDef(
//					"Vocation", "vocation", d.getPrestige()));
//			df.setTaxcode(d.getTaxcode());
//			df.setBankname(d.getBankname());
//			df.setDoorname(d.getDoorname());
//			df.setBankaccount(d.getBankaccount());
//			df.setMemo(d.getMemo());
//			//df.setUserid(userid);
//			//df.setMakeidname(au.getUsersByid(d.getMakeid()).getRealname());
//			// df.setMakedate(String.valueOf(d.getMakedate()));
//
//			List uls = au.getIDAndLoginName();
//			ArrayList als = new ArrayList();
//			for (int u = 0; u < uls.size(); u++) {
//				UsersBean ubs = new UsersBean();
//				Object[] ub = (Object[]) uls.get(u);
//				ubs.setUserid(Long.valueOf(ub[0].toString()));
//				ubs.setRealname(ub[2].toString());
//				als.add(ubs);
//			}
//
//			request.setAttribute("d", df);
//			request.setAttribute("als", als);
//			request.setAttribute("cals", cals);
//
//			return mapping.findForward("toupd");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mapping.getInputForward();
//	}
//
//}
