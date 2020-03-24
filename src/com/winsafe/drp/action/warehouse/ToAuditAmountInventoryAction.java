package com.winsafe.drp.action.warehouse;

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
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpileForm;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.StringUtil;

public class ToAuditAmountInventoryAction extends BaseAction {
	Logger logger = Logger.getLogger(ToAuditAmountInventoryAction.class);
	private AppFUnit af = new AppFUnit();
	private AppProductStockpileAll aps = new AppProductStockpileAll();
	private WarehouseService aw = new WarehouseService();
	private AppWarehouse appw = new AppWarehouse();

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 初始化
		initdata(request);
		try {
			String keyword = request.getParameter("KeyWord");
			String MakeOrganID = request.getParameter("MakeOrganID");
			String warehouseId = request.getParameter("WarehouseID");

			String strCondition = "  (abs(ps.stockpile)+abs(ps.prepareout))<>0  and p.id=ps.productid "
					+ " and pstr.structcode=p.psid and rw.warehouseId=ps.warehouseid and rw.activeFlag=1 and rw.userId= "
					+ userid;
			strCondition = " p.id=ps.productid and pstr.structcode=p.psid ";
			// 选择了机构才进行查询
			if (MakeOrganID != null && !"".equals(MakeOrganID)) {
				AppOrgan appo = new AppOrgan();
				Organ organ = appo.getOrganByID(MakeOrganID);
				request.setAttribute("oname", organ.getOrganname());
				Warehouse warehouse = appw.getWarehouseByID(warehouseId);
				request.setAttribute("wname", warehouse.getWarehousename());
//				String ss = getWarehouseId(aw.getWarehouseListByOID(MakeOrganID));
				strCondition += " and ps.warehouseid in('" + warehouseId + "') and ps.stockpile <> 0";
				Map map = new HashMap(request.getParameterMap());
				Map tmpMap = EntityManager.scatterMap(map);
				String[] tablename = { "ProductStockpileAll", "WarehouseVisit", "Product" };
				String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
				String blur = DbUtil.getOrBlur(map, tmpMap, "p.id", "ProductName", "PYCode",
						"SpecMode", "p.nccode");
				String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");

				whereSql = whereSql + blur + leftblur + strCondition;
				whereSql = DbUtil.getWhereSql(whereSql);

				List<Map> pls = aps.getProductStockpile(request, whereSql);
				List alp = new ArrayList();
				for (Map map2 : pls) {
					ProductStockpileForm psf = new ProductStockpileForm();
					AppProduct ap = new AppProduct();
					Product p = new Product();
					for (Object key : map2.keySet()) {
						if (key.equals("id")) {
							psf.setWarehouseid(map2.get(key) == null ? null : (String) map2
									.get(key));
						}
						if (key.equals("productid")) {
							if (map2.get(key) != null) {
								psf.setProductid((String) map2.get(key));
								p = ap.getProductByID(psf.getProductid());
								if (p != null) {
									psf.setPsproductname(p.getProductname());
								}
							}
						}
						if (key.equals("specmode")) {
							psf.setSpecmode(map2.get(key) == null ? "" : map2.get(key).toString());
						}
						if (key.equals("boxquantity")) {
							psf.setAssistBoxStockpile(map2.get(key) == null ? null : Double
									.valueOf(map2.get(key).toString()));
						}
						if (key.equals("stockpile")) {
							// psf.setStockpile(Double.valueOf(map2.get(key).toString()));
							psf.setStockpile(map2.get(key) == null ? null : Double.valueOf(map2
									.get(key).toString()));
						}
						if (key.equals("mcode")) {
							// psf.setStockpile(Double.valueOf(map2.get(key).toString()));
							psf.setNccode(map2.get(key) == null ? null : map2.get(key).toString());
						}
						if (key.equals("batch")) {
							// psf.setStockpile(Double.valueOf(map2.get(key).toString()));
							
							psf.setBatch(StringUtil.removeNull(map2.get(key).toString()));
						}
					}
					// 数量换算
					psf.setCountunit(Constants.DEFAULT_UNIT_ID);
					Double xquantity = af.getXQuantity(psf.getProductid(), psf.getCountunit());
					psf.setStockpile(psf.getStockpile() / xquantity);
					alp.add(psf);
					// 根据产品id获取物料号
				}

				List wls = aw.getEnableWarehouseByVisit(userid);
				String brandselect = Internation.getSelectTagByKeyAllDB("Brand", "Brand", true);
				request.setAttribute("brandselect", brandselect);
				request.setAttribute("alw", wls);
				request.setAttribute("keyword", keyword);
				request.setAttribute("alp", alp);
				DBUserLog.addUserLog(request, "[列表]");
				// request.setAttribute("prompt", "r");
				return mapping.findForward("success");
			} else {
				logger.debug("展开默认的盘点机构和仓库");
				// 页面初始化时，默认机构和仓库
				request.setAttribute("MakeOrganID", users.getMakeorganid());
				request.setAttribute("oname", users.getMakeorganname());
				appw.getEnableWarehouseByvisit(request,users.getUserid(), users.getMakeorganid());
				String ss = getWarehouseId(aw.getWarehouseListByOID(users.getMakeorganid()));
				strCondition += " and ps.warehouseid in(" + ss + ") and ps.stockpile <> 0";
				Map map = new HashMap(request.getParameterMap());
				Map tmpMap = EntityManager.scatterMap(map);
				String[] tablename = { "ProductStockpileAll", "WarehouseVisit", "Product" };
				String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
				whereSql += " WarehouseID = '"+request.getAttribute("WarehouseID")+"' and ";
				String blur = DbUtil.getOrBlur(map, tmpMap, "p.id", "ProductName", "PYCode",
						"SpecMode", "p.nccode");
				String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");

				whereSql = whereSql + blur + leftblur + strCondition ;
				whereSql = DbUtil.getWhereSql(whereSql);

				List<Map> pls = aps.getProductStockpile(request, whereSql);
				List alp = new ArrayList();
				for (Map map2 : pls) {
					ProductStockpileForm psf = new ProductStockpileForm();
					AppProduct ap = new AppProduct();
					Product p = new Product();
					for (Object key : map2.keySet()) {
						if (key.equals("id")) {
							psf.setWarehouseid(map2.get(key) == null ? null : (String) map2
									.get(key));
						}
						if (key.equals("productid")) {
							if (map2.get(key) != null) {
								psf.setProductid((String) map2.get(key));
								p = ap.getProductByID(psf.getProductid());
								if (p != null) {
									psf.setPsproductname(p.getProductname());
								}
							}
						}
						if (key.equals("specmode")) {
							psf.setSpecmode(map2.get(key) == null ? "" : map2.get(key).toString());
						}
						if (key.equals("boxquantity")) {
							psf.setAssistBoxStockpile(map2.get(key) == null ? null : Double
									.valueOf(map2.get(key).toString()));
						}
						if (key.equals("stockpile")) {
							// psf.setStockpile(Double.valueOf(map2.get(key).toString()));
							psf.setStockpile(map2.get(key) == null ? null : Double.valueOf(map2
									.get(key).toString()));
						}
						if (key.equals("mcode")) {
							// psf.setStockpile(Double.valueOf(map2.get(key).toString()));
							psf.setNccode(StringUtil.removeNull(map2.get(key).toString()));
						}
						if (key.equals("batch")) {
							// psf.setStockpile(Double.valueOf(map2.get(key).toString()));
							
							psf.setBatch(StringUtil.removeNull(map2.get(key).toString()));
						}
					}
					// 数量换算
					psf.setCountunit(Constants.DEFAULT_UNIT_ID);
					Double xquantity = af.getXQuantity(psf.getProductid(), psf.getCountunit());
					psf.setStockpile(psf.getStockpile() / xquantity);
					alp.add(psf);
				}

				List wls = aw.getEnableWarehouseByVisit(userid);
				String brandselect = Internation.getSelectTagByKeyAllDB("Brand", "Brand", true);
				request.setAttribute("brandselect", brandselect);
				request.setAttribute("alw", wls);
				request.setAttribute("keyword", keyword);
				request.setAttribute("alp", alp);
				DBUserLog.addUserLog(request, "[列表]");
				// request.setAttribute("prompt", "r");
				return mapping.findForward("success");
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return new ActionForward(mapping.getInput());
	}

	private String getWarehouseId(List wlist) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < wlist.size(); i++) {
			Warehouse w = (Warehouse) wlist.get(i);
			if (i == 0) {
				sb.append("'").append(w.getId()).append("'");
			} else {
				sb.append(",'").append(w.getId()).append("'");
			}
		}
		return sb.toString();
	}

}
