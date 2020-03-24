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
import com.winsafe.erp.dao.AppProductPlan;
import com.winsafe.erp.pojo.ProductPlan;
import com.winsafe.erp.pojo.ProductPlanForm;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ListProductPlanAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ListProductPlanAction.class);
	private AppProduct appProduct = new AppProduct();
	private AppProductPlan appProductPlan = new AppProductPlan();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int pagesize = 10;
		initdata(request);
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String Condition = " organId in (select makeorganid from Warehouse where id in (select ruw.warehouseId from RuleUserWh ruw where ruw.userId = "
			+ userid + " and activeFlag = 1))";
		String[] tablename = { "ProductPlan" };
		// 查询条件
		String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
		// 关键字查询条件
		String blur = DbUtil.getOrBlur(map, tmpMap, "id","PONO","mbatch", "pbatch", "productId");
		
		String proTimeCondition = getProTimeCondition(map, tmpMap," proDate");
		String packTimeCondition = getPackTimeCondition(map, tmpMap," packDate");
		
		
		whereSql = whereSql + blur +proTimeCondition + packTimeCondition + Condition;
		whereSql = DbUtil.getWhereSql(whereSql);

		List<ProductPlan> configs = appProductPlan.getProductPlans(request, pagesize, whereSql);
		List<ProductPlanForm> ProductPlanForms = new ArrayList<ProductPlanForm>();
		for (ProductPlan ProductPlan : configs) {
			ProductPlanForm ProductPlanForm = new ProductPlanForm();
			ProductPlanForm.setId(ProductPlan.getId());
			ProductPlanForm.setPONO(ProductPlan.getPONO());
			ProductPlanForm.setTemp(ProductPlan.getTemp());
			ProductPlanForm.setOrganId(ProductPlan.getOrganId());
			ProductPlanForm.setProductId(ProductPlan.getProductId());
			ProductPlanForm.setMbatch(ProductPlan.getMbatch());
			ProductPlanForm.setPbatch(ProductPlan.getPbatch());
			ProductPlanForm.setProDate(ProductPlan.getProDate());
			ProductPlanForm.setPackDate(ProductPlan.getPackDate());
			ProductPlanForm.setBoxnum(ProductPlan.getBoxnum());
			ProductPlanForm.setCopys(ProductPlan.getCopys());
			ProductPlanForm.setApprovalMan(ProductPlan.getApprovalMan());
			ProductPlanForm.setApprovalFlag(ProductPlan.getApprovalFlag());
			ProductPlanForm.setCloseFlag(ProductPlan.getCloseFlag());
			ProductPlanForm.setCloseMan(ProductPlan.getCloseMan());
			if(ProductPlan.getIsUpload()!=null){
				ProductPlanForm.setIsUpload(ProductPlan.getIsUpload());
			}else{
				ProductPlanForm.setIsUpload(0);
			}
			Product product = appProduct.getProductByID(ProductPlanForm.getProductId());
			if (product != null) {
				ProductPlanForm.setProductname(product.getProductname());
				ProductPlanForm.setPacksize(product.getPackSizeName());
				ProductPlanForm.setMcode(product.getmCode());
				ProductPlanForm.setLinkMode(String.valueOf(product.getLinkMode()));
			}else{
				ProductPlanForm.setProductname("");
				ProductPlanForm.setPacksize("");
				ProductPlanForm.setMcode("");
				ProductPlanForm.setLinkMode("");
			}
			ProductPlanForms.add(ProductPlanForm);
		}

		request.setAttribute("configs", ProductPlanForms);
//		request.setAttribute("organId",users.getMakeorganid());
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}
	
	  public static String getProTimeCondition(Map map, Map tmpMap, String field) {
		    StringBuffer buf = new StringBuffer();
		    if (map.containsKey("proBeginDate")) {
		      String timeField = (String) tmpMap.get("proBeginDate");
		      if (timeField != null && !timeField.equals("")) {

		        buf.append(field + ">=to_date('" + timeField + "','yyyy-MM-dd hh24:mi:ss')");
		        buf.append(" and ");
		      }
		    }
		    if (map.containsKey("proEndDate")) {
		      String timeField = (String) tmpMap.get("proEndDate");
		      if (timeField != null && !timeField.equals("")) {

		        buf.append(field + "<=to_date('" + timeField + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
		        buf.append(" and ");
		      }
		    }
		    return buf.toString();
		  }
	  public static String getPackTimeCondition(Map map, Map tmpMap, String field) {
		    StringBuffer buf = new StringBuffer();
		    if (map.containsKey("packBeginDate")) {
		      String timeField = (String) tmpMap.get("packBeginDate");
		      if (timeField != null && !timeField.equals("")) {

		        buf.append(field + ">=to_date('" + timeField + "','yyyy-MM-dd hh24:mi:ss')");
		        buf.append(" and ");
		      }
		    }
		    if (map.containsKey("packEndDate")) {
		      String timeField = (String) tmpMap.get("packEndDate");
		      if (timeField != null && !timeField.equals("")) {

		        buf.append(field + "<=to_date('" + timeField + " 23:59:59','yyyy-MM-dd hh24:mi:ss')");
		        buf.append(" and ");
		      }
		    }
		    return buf.toString();
		  }

}
