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
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

/**
 * @author : jerry
 * @version : 2009-8-24 上午09:18:06 www.winsafe.cn
 */
public class PrintAlreadyProductAction extends BaseAction {

	@SuppressWarnings("unchecked")
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			UsersBean users = UserManager.getUser(request);

			String oid = users.getMakeorganid();

			String condition = " op.organid='" + oid+ "' and p.id=op.productid ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Product" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String blur = DbUtil.getOrBlur(map, tmpMap, "p.ID", "p.ProductName","p.SpecMode","p.PYCode");
			whereSql = whereSql + leftblur + blur + condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppOrganProduct aop = new AppOrganProduct();
			List menuls = aop.getOrganProductAlready(whereSql);

			
			AppProductStruct appProductStruct = new AppProductStruct();
			List uls = appProductStruct.getProductStructCanUse();
			request.setAttribute("uls", uls);

			request.setAttribute("opls", menuls);
			return mapping.findForward("toprint");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
