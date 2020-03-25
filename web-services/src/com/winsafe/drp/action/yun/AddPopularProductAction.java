package com.winsafe.drp.action.yun;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.action.common.YunParameter;
import com.winsafe.drp.dao.AppPopularProduct;
import com.winsafe.drp.dao.PopularProduct;
import com.winsafe.drp.exception.ObjectExistedException;
import com.winsafe.drp.metadata.EAuditStatus;
import com.winsafe.drp.metadata.EIsDeleted;
import com.winsafe.drp.metadata.EListedStatus;
import com.winsafe.drp.util.ESAPIUtil;
import com.winsafe.drp.util.WfLogger;

public class AddPopularProductAction extends BaseAction {
	
	private AppPopularProduct app = new AppPopularProduct();

	Logger logger = Logger.getLogger(AddPopularProductAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String id = request.getParameter("ProductID");
		String name = request.getParameter("ProductName");
		String slogan = request.getParameter("slogan");
		String component = request.getParameter("component");
		String certification = request.getParameter("certification");
		String skus = request.getParameter("sku");
//		String relatedProducts = request.getParameter("related_products");
		
		PopularProduct product = new PopularProduct();
		product.setId(id);
		product.setManufacturerId(YunParameter.BayerManufacturerID); //设置产品所属厂家 默认拜耳
		product.setAuditStatus(EAuditStatus.PENDING.getValue()); //默认设置“审核通过”
		product.setListedStatus(EListedStatus.LISTED.getValue());	//默认未上架
		product.setName(name);
		product.setSlogan(ESAPIUtil.decodeForHTML(slogan));
		product.setComponent(ESAPIUtil.decodeForHTML(component));
		product.setCertification(certification);
		product.setContent("");//内容先留空
		product.setCreateTime(new Date());
		product.setLastModifyTime(new Date());
		product.setSku(skus);
		product.setIsDeleted(EIsDeleted.nromal.getValue());
		try {
			PopularProduct pp = app.getPopularProductByID(id);
			if (pp != null) {
				request.setAttribute("result", "已维护过该产品（编号："+id+"）的信息!");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			app.addPopularProduct(product);
			request.setAttribute("result", "databases.add.success");
		} catch (ObjectExistedException e) {
			WfLogger.error("", e);
			throw e;
		}
		return mapping.findForward("success");
	}
}
