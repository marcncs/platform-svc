package com.winsafe.drp.action.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class SelectSafetyProductAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);
		String oid = request.getParameter("oid");


		try {
			String strCondition = " op.organid='" + oid + "' and op.productid = p.id and p.useflag=1 and p.wise=0 " +
			" and not exists (select oi.productid from OrganSafetyIntercalate as oi where oi.productid=p.id and oi.organid='"+oid+"') and p.wise=0 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "Product"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String blur = DbUtil.getOrBlur(map, tmpMap, "ProductName",
					"SpecMode", "PYCode");

			whereSql = whereSql + leftblur + blur + strCondition;
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppProduct ap = new AppProduct();
			List pls = ap.getOrganProductProduct(request, pagesize, whereSql);
			

			
			AppProductStruct appProductStruct = new AppProductStruct();
			List uls = appProductStruct.getProductStructCanUse();

			request.setAttribute("uls", uls);
			request.setAttribute("sls", pls);
			return mapping.findForward("select");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
