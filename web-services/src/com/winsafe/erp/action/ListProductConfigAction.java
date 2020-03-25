package com.winsafe.erp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.erp.dao.AppProductConfig;
import com.winsafe.erp.pojo.ProductConfig;
import com.winsafe.erp.pojo.ProductConfigForm;
import com.winsafe.hbm.util.DbUtil;

public class ListProductConfigAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ListProductConfigAction.class);
	private AppProduct appProduct = new AppProduct();
	private AppProductConfig appProductConfig = new AppProductConfig();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int pagesize = 10;
		initdata(request);
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String Condition = " organid in (select makeorganid from Warehouse where id in (select ruw.warehouseId from RuleUserWh ruw where ruw.userId = "
			+ userid + " and activeFlag = 1))";
		String[] tablename = { "ProductConfig" };
		// 查询条件
		String whereSql = EntityManager.getTmpWhereSql(map, tablename);
		// 关键字查询条件
		String blur = DbUtil.getOrBlur(map, tmpMap, "id", "erpProductId", "mCode");
		whereSql = whereSql + blur + Condition;
		whereSql = DbUtil.getWhereSql(whereSql);

		List<ProductConfig> configs = appProductConfig.getProductConfigs(request, pagesize, whereSql);
		List<ProductConfigForm> productConfigForms = new ArrayList<ProductConfigForm>();
		for (ProductConfig productConfig : configs) {
			ProductConfigForm productConfigForm = new ProductConfigForm();
			productConfigForm.setId(productConfig.getId());
			productConfigForm.setmCode(productConfig.getmCode());
			productConfigForm.setOrganId(productConfig.getOrganId());
			productConfigForm.setProductId(productConfig.getProductId());
			productConfigForm.setErpProductId(productConfig.getErpProductId());
			Product product = appProduct.getProductByID(productConfigForm.getProductId());
			if (product != null) {
				productConfigForm.setSpecmode(product.getSpecmode());
				productConfigForm.setProductName(product.getProductname());
			}
			productConfigForms.add(productConfigForm);
		}

		request.setAttribute("configs", productConfigForms);
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}

}
