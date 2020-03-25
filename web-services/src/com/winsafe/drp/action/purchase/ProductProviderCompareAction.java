package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.AppProviderProduct;
import com.winsafe.drp.dao.ProviderProductCompareForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class ProductProviderCompareAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 5;
		super.initdata(request);
		try {
			String strPDID = request.getParameter("PDID");
			if (strPDID == null) {
				strPDID = (String) request.getSession().getAttribute("pdid");
			}
			String PDID = strPDID;
			request.getSession().setAttribute("pdid", strPDID);

			String Condition = " p.pid=pp.providerid and pp.productid='" + PDID+ "' ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Provider", "ProviderProduct" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

			whereSql = whereSql + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			AppCountryArea aca = new AppCountryArea();
			AppProviderProduct apl = new AppProviderProduct();
			List apls = apl.getProductProviderCompare(request, pagesize, whereSql);
			ArrayList alpl = new ArrayList();
			for (int i = 0; i < apls.size(); i++) {
				ProviderProductCompareForm ppcf = new ProviderProductCompareForm();
				Object[] o = (Object[]) apls.get(i);
				ppcf.setId(Long.valueOf(o[0].toString()));
				ppcf.setPid(String.valueOf(o[1]));
				ppcf.setProvidername(String.valueOf(o[2]));
				ppcf.setUnitprice(Double.valueOf(o[3].toString()));
				ppcf.setVocationname(Internation.getStringByKeyPositionDB(
						"Vocation", Integer.parseInt(o[4].toString())));
				ppcf.setAbcsortname(Internation.getStringByKeyPosition(
						"AbcSort", request, Integer.parseInt(o[5].toString()),
						"global.sys.SystemResource"));
				Integer province = Integer.valueOf(o[6].toString());
				if (province > 0) {
					ppcf.setProvincename(aca.getAreaByID(province)
							.getAreaname());
				} else {
					ppcf.setProvincename("");
				}
				Integer city = Integer.valueOf(o[7].toString());
				if (city > 0) {
					ppcf.setCityname(aca.getAreaByID(city).getAreaname());
				} else {
					ppcf.setCityname("");
				}
				Integer areas = Integer.valueOf(o[8].toString());
				if (areas > 0) {
					ppcf.setAreasname(aca.getAreaByID(areas).getAreaname());
				} else {
					ppcf.setAreasname("");
				}

				alpl.add(ppcf);
			}

			
			String abesortselect = Internation.getSelectTagByKeyAll("AbcSort",
					request, "AbcSort", true, null);

			request.setAttribute("abesortselect", abesortselect);
			request.setAttribute("PDID", PDID);
			request.setAttribute("alpl", alpl);
			
			//DBUserLog.addUserLog(userid, "存货供应商比较");
			return mapping.findForward("list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
