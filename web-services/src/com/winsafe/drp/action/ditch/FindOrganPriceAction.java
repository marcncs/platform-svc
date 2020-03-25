package com.winsafe.drp.action.ditch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganPriceii;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

/**
 * @author : jerry
 * @version : 2009-9-3 下午04:18:31 www.winsafe.cn
 */
public class FindOrganPriceAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 30;
		super.initdata(request);
		try {

			String organsql = " and op.organid=o.id and o.sysid like '"
					+ users.getOrgansysid() + "%' ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Product", "OrganPriceii" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String priceCondition = DbUtil.getPriceCondition(map, tmpMap,
					"UnitPrice");
			String blur = DbUtil.getOrBlur(map, tmpMap, "ProductName",
					"PYCode", "SpecMode", "p.id");
			String strCondition = "( p.id=op.productid and p.useflag=1 "
					+ organsql + ")";
			whereSql = whereSql + leftblur + blur + priceCondition
					+ strCondition;
			whereSql = DbUtil.getWhereSql(whereSql);
			AppOrganPriceii appOpii = new AppOrganPriceii();
			List list = appOpii.findOrganPriceii(request, pagesize, whereSql);
			request.setAttribute("list", list);

			return mapping.findForward("find");
		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
