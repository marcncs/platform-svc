package com.winsafe.drp.action.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.CountryArea;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.UsersForm;
import com.winsafe.drp.metadata.UserCategary;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.StringUtil;

public class ToUpdUsersAction extends Action {

	private CountryAreaService aca = new CountryAreaService();
	private AppUsers au = new AppUsers();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String uid = request.getParameter("uid");
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		try {
			Users u = au.getUsersByid(Integer.valueOf(uid));

			UsersForm uf = new UsersForm();
			uf.setUserid(u.getUserid());
			uf.setLoginname(u.getLoginname());
			uf.setRealname(u.getRealname());
			uf.setNameen(u.getNameen());
			uf.setSexname(Internation.getSelectTagByKeyAll("Sex", request,
					"sex", String.valueOf(u.getSex()), null));
			uf.setBirthday(u.getBirthday());
			uf.setIdcard(u.getIdcard());
			uf.setMobile(u.getMobile());
			uf.setOfficetel(u.getOfficetel());
			uf.setHometel(u.getHometel());
			uf.setEmail(u.getEmail());
			uf.setQq(u.getQq());
			uf.setMsn(u.getMsn());
			if (u.getProvince() != null) {
				uf.setProvince(u.getProvince());
				uf.setProvincename(aca.getCountryAreaName(u.getProvince()));
			}
			if (u.getCity() != null) {
				uf.setCity(u.getCity());
				uf.setCityname(aca.getCountryAreaName(u.getCity()));
			}

			if (u.getAreas() != null) {
				uf.setAreas(u.getAreas());
				uf.setAreasname(aca.getCountryAreaName(u.getAreas()));
			}
			uf.setAddr(u.getAddr());
			uf.setMakeorganid(u.getMakeorganid());
			uf.setMakedeptid(u.getMakedeptid());
			uf.setStatusname(Internation.getSelectTagByKeyAll("YesOrNo",
					request, "status", String.valueOf(u.getStatus()), null));
			uf.setIsloginname(Internation.getSelectTagByKeyAll("YesOrNo",
					request, "islogin", String.valueOf(u.getIslogin()), null));
			uf.setIscallname(Internation.getSelectTagByKeyAll("YesOrNo",
					request, "iscall", String.valueOf(u.getIscall()), null));
			uf.setValidate(DateUtil.formatDate(u.getValidate()));
			uf.setUserType(u.getUserType());
			uf.setIsCwid(Internation.getSelectTagByKeyAll("YesOrNo",
					request, "isCwid", String.valueOf(u.getIsCwid()), null));
			
			List list0 = aca.getProvince();
			ArrayList cals = new ArrayList();

			for (int i = 0; i < list0.size(); i++) {
				CountryArea ca = new CountryArea();
				Object ob[] = (Object[]) list0.get(i);
				ca.setId(Integer.valueOf(ob[0].toString()));
				ca.setAreaname(ob[1].toString());
				ca.setParentid(Integer.valueOf(ob[2].toString()));
				ca.setRank(Integer.valueOf(ob[3].toString()));
				cals.add(ca);
			}

			request.setAttribute("cals", cals);

			String Province = String.valueOf(uf.getProvince()); // 省
			String City = String.valueOf(uf.getCity()); // 市

			List citysList = new ArrayList();
			List areasList = new ArrayList();
			if (!StringUtil.isEmpty(Province)) {
				citysList = aca.getCountryAreaByParentid(Integer
						.valueOf(Province));
			}
			if (!StringUtil.isEmpty(City)) {
				areasList = aca.getCountryAreaByParentid(Integer.valueOf(City));
			}
			request.setAttribute("Citys", citysList);
			request.setAttribute("Areass", areasList);

			request.setAttribute("us", uf);
			request.setAttribute("UserType", u.getUserType());
			AppBaseResource appBaseResource = new AppBaseResource();
			List salesUserType = appBaseResource.getBaseResource("SalesUserType");
		     request.setAttribute("salesUserType",salesUserType);
		     
		    Set<Integer> cSet = au.getUserCategarySet(Integer.valueOf(uid));
		    if(cSet.size()>0) {
		    	StringBuffer cids= new StringBuffer();
		    	for(Integer cid : cSet) {
		    		cids.append(","+cid);
		    	}
		    	request.setAttribute("ucids", cids.substring(1));
		    }
		    request.setAttribute("ucategory",UserCategary.values());
			return mapping.findForward("toupdusers");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
