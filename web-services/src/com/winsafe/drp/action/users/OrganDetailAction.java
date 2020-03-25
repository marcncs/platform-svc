package com.winsafe.drp.action.users; 

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.server.CountryAreaService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.StringUtil;

public class OrganDetailAction extends BaseAction {

	private AppOrgan appOrgan = new AppOrgan();
	private CountryAreaService aca = new CountryAreaService();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		try {
			String oid = request.getParameter("ID");
			Organ o = appOrgan.getOrganByID(oid);

			String province = aca.getCountryAreaName(o.getProvince());
			String city = aca.getCountryAreaName(o.getCity());
			String areas = aca.getCountryAreaName(o.getAreas());
			request.setAttribute("of", o);
			request.setAttribute("logo", o.getLogo() == null ? "" : o.getLogo()
					.replace("\\", "/"));
			request.setAttribute("province", province);
			request.setAttribute("city", city);
			request.setAttribute("areas", areas);
			if(!StringUtil.isEmpty(o.getValidateLoaclId())) {
				List<Map<String,String>> vi = appOrgan.getValidateLoaclInfo(o.getValidateLoaclId());
				if(vi != null && vi.size() > 0) {
					request.setAttribute("matchInfo", vi.get(0));
				}
			}
			
			DBUserLog.addUserLog(userid, "系统管理", "机构资料详情" + o.getId());
			return mapping.findForward("info");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}

}
