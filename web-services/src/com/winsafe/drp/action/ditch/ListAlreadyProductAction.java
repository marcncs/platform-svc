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
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

/**
 * @author : jerry
 * @version : 2009-8-24 上午09:18:06 www.winsafe.cn
 */
public class ListAlreadyProductAction extends BaseAction {

	@SuppressWarnings("unchecked")
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);
		try {
			Map<String,Object> param = new HashMap<>(); 
			String oid = users.getMakeorganid();
			String condition = " op.organid=:organid and p.id=op.productid ";
			 param.put("organid", oid);
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Product" };
			String whereSql = EntityManager.getTmpWhereSqlForHql(map, tablename, param);

			String leftblur = DbUtil.getBlurLeftForHql(map, tmpMap, param, "PSID");
			String blur = DbUtil.getOrBlurForHql(map, tmpMap, param, "p.ID",
					"p.ProductName", "p.SpecMode", "p.PYCode");
			whereSql = whereSql + leftblur + blur + condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppOrganProduct aop = new AppOrganProduct();
			List menuls = aop.getOrganProductAlready(request, pagesize,
					whereSql, param);

			AppProductStruct appProductStruct = new AppProductStruct();
			List uls = appProductStruct.getProductStructCanUse();
			request.setAttribute("uls", uls);

			request.setAttribute("opls", menuls);
			request.setAttribute("KeyWordLeft", request
					.getParameter("KeyWordLeft"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			return mapping.findForward("list");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
