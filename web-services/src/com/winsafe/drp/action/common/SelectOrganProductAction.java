package com.winsafe.drp.action.common;

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
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DbUtil;

/**
 * @author : ivan
 * @version : 2009-8-10 上午10:35:02 www.winsafe.cn
 */
public class SelectOrganProductAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			int pagesize = 20;
			String Condition = " op.organid='" + users.getMakeorganid() + "' and op.productid = p.id and p.useflag=1   ";
			Map map=new HashMap(request.getParameterMap());
	        Map tmpMap = EntityManager.scatterMap(map);
	        String[] tablename={"Product"};
	        String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

	        String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
	        String blur = DbUtil.getOrBlur(map, tmpMap,"p.id","p.ProductName","p.PYCode","p.SpecMode","p.nccode"); 
	        whereSql = whereSql + leftblur + blur +Condition ; 
	        whereSql = DbUtil.getWhereSql(whereSql); 
			AppOrganProduct appOrganProduct = new AppOrganProduct();
			List list = appOrganProduct.getOrganProductAlready(request,
					pagesize, whereSql);

			request.setAttribute("unit", Constants.DEFAULT_UNIT_ID);
			request.setAttribute("list", list);
			return mapping.findForward("success");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
