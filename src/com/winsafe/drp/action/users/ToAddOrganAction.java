package com.winsafe.drp.action.users;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.MakeConf;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.hbm.util.MakeCode;

public class ToAddOrganAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 初始化
		initdata(request);
		CountryAreaService aca = new CountryAreaService();
		AppMakeConf amc = new AppMakeConf();
		AppOrgan appOrgan = new AppOrgan();
		AppBaseResource appBaseResource = new AppBaseResource();
		try {
			List level = appBaseResource.getBaseResource("CustomerLevel");
			List list = aca.getProvinceObj();
			MakeConf mc = amc.getMakeConfByID("organ");
			String oid = "";
			String isread = "";
			if (mc.getRunmode() == 1) {
				oid = MakeCode.getExcIDByRandomTableName("organ", 4, "");
				isread = "readonly";
			}
			// 获取当前用户所属机构的级别
			Organ organ = appOrgan.getOrganByID(users.getMakeorganid());
			
			request.setAttribute("mc", mc);
			request.setAttribute("oid", oid.trim());
			request.setAttribute("isread", isread);
			request.setAttribute("cals", list);
			request.setAttribute("level", level);
			request.setAttribute("parentid", users.getMakeorganid());
			request.setAttribute("organType", organ.getOrganType()==null?0:organ.getOrganType());
			request.setAttribute("organModel", organ.getOrganModel()==null?0:organ.getOrganModel());
			return mapping.findForward("toadd");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
