package com.winsafe.drp.action.users;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.drp.server.OrganService;

public class ToUpdOrganAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		OrganService ao = new OrganService();
		CountryAreaService aca = new CountryAreaService();
		AppOlinkMan appol = new AppOlinkMan();
		AppOrgan appOrgan = new AppOrgan();
		AppBaseResource appBaseResource = new AppBaseResource();
		try {
			String id = request.getParameter("ID");

			Organ d = ao.getOrganByID(id);
			if (d.getIsrepeal() == 1) {
				request.setAttribute("result", "该机构已撤消不能修改!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}

			List linkmanlist = appol.getOlinkmanByCid(id);
			if (linkmanlist.size() == 0) {
				linkmanlist = null;
			}
			List list = aca.getProvinceObj();
			//获取当前用户所属机构的级别
			Organ organ = appOrgan.getOrganByID(users.getMakeorganid());
			
			List level = appBaseResource.getBaseResource("CustomerLevel");
			
			request.setAttribute("linkmanlist", linkmanlist);
			request.setAttribute("cals", list);
			request.setAttribute("level", level);
			request.setAttribute("d", d);
			request.setAttribute("organType", organ.getOrganType()==null?0:organ.getOrganType());
			request.setAttribute("organModel", organ.getOrganModel()==null?0:organ.getOrganModel());
			request.setAttribute("isKeyRetailer", d.getIsKeyRetailer()==null?0:d.getIsKeyRetailer());
		} catch (Exception e) {
			e.printStackTrace();

		} finally {

		}
		return mapping.findForward("toupd");
	}

}
