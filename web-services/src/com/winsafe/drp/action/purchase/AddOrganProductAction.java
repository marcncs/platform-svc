package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Hibernate;

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.OrganProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.PYCode;
import com.winsafe.hbm.util.RequestTool;

/**
 * 批量增加产品上架功能
 * @author RichieYu
 *
 */
public class AddOrganProductAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String productIds = request.getParameter("organProductId");
		String[] pIds = productIds.split(",");
		//所选择的所有id
		String[] organStr = request.getParameterValues("array");
		String[] organIds = organStr[0].split(",");
		AppOrganProduct appOrganProduct = new AppOrganProduct();
		//添加上架机构
		appOrganProduct.addProductsOrgans(pIds,organIds);
		request.setAttribute("result", "databases.add.success");
		return mapping.findForward("success");
		
	}
}
