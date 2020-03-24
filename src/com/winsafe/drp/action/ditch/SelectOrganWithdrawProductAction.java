package com.winsafe.drp.action.ditch;

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
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganProductForm;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.dao.WarehouseProductForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.metadata.DeliveryType; 
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.StringUtil;

public class SelectOrganWithdrawProductAction extends BaseAction {
	
	private static Logger logger = Logger.getLogger(SelectOrganWithdrawProductAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 初始化
		int pagesize = 20;
		initdata(request);
		AppProductStockpileAll aps = new AppProductStockpileAll();
		AppFUnit af = new AppFUnit();
		AppProduct ap = new AppProduct();
		AppFUnit appFUnit = new AppFUnit();
		AppWarehouse aw = new AppWarehouse();
		AppOrganProduct appOrganProduct = new AppOrganProduct();
		
		AppOrgan ao = new AppOrgan();//tommy add for lincence in
		

		try {
			String dtype = request.getParameter("dtype");
			String strwid = request.getParameter("wid");
			String oid = users.getMakeorganid();
			if (strwid == null) {
				strwid = (String) request.getSession().getAttribute("wid");
			}
			request.getSession().setAttribute("wid", strwid);
			
			String condition = "";
			
			Map map = new HashMap(request.getParameterMap());
			String productname = (String) map.remove("ProductName");
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "ProductStockpileAll", "Product" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String blur = DbUtil.getOrBlur2(map, tmpMap, "p.id", "p.productname", "p.specmode", "p.mCode");

			
			//获取机构信息， 如果是分装厂，由于没有库存， 需要按允许负库存的方式
			//先获取机构信息， 判断是否分装厂。
			//设置为负库存， 切记。增加unitinfo中的产品
			// 根据仓库是否为负库存来选择产品
			Warehouse w = aw.getWarehouseByID(strwid);
			
			Organ organ=ao.getOrganByID(w.getMakeorganid());  //tommy  获取机构
			
			ArrayList sls = new ArrayList();
			if (w.getIsMinusStock() == 0) {
				if(!StringUtil.isEmpty(dtype) 
						&& DeliveryType.BONUS.getValue().toString().equals(dtype)) {
					condition = " p.id not in (select tagsubvalue from Base_Resource where isuse=1 and tagname='NoBonusProduct') and ";
				}
				String strCondition = " ps.productid=p.id and ps.warehouseid='" + strwid + "' and p.useflag=1 ";
				whereSql = whereSql+ condition+ blur + leftblur + strCondition;
				whereSql = DbUtil.getWhereSql(whereSql);
				if (!StringUtil.isEmpty(productname)) {
					whereSql += " and p.productname = '" + productname + "'";
				}
				
				//分装厂
				if (!(null==organ.getOrganModel()) ) {
					if(2==organ.getOrganModel()){
						whereSql += " and p.id in (select productId from UnitInfo where organId='"+organ.getId()+"' and isactive = 1) " ;
					}
				}
				
				List pls = aps.getProductStockpileOrderbyPy(request, pagesize, whereSql);

				for (int i = 0; i < pls.size(); i++) {
					WarehouseProductForm psf = new WarehouseProductForm();
					Map mapTmp = (Map) pls.get(i);
					psf.setProductid((String) mapTmp.get("productid"));
					psf.setProductname((String) mapTmp.get("productname"));
					psf.setSpecmode((String) mapTmp.get("specmode"));
					psf.setPackSizeName((String) mapTmp.get("packsizename"));
					// psf.setUnitid(Integer.valueOf((String)mapTmp.get("unitid")));
					// psf.setUnitidname(HtmlSelect.getResourceName(request,"CountUnit",
					// Integer.valueOf((String)mapTmp.get("unitid"))));
					psf.setStockpile(Double.valueOf((String) mapTmp.get("stockpile")));
					// 默认单位只能选择件
					psf.setUnitid(Constants.DEFAULT_UNIT_ID);
					psf.setUnitidname(HtmlSelect.getResourceName(request, "CountUnit", Constants.DEFAULT_UNIT_ID));
					// 总数量
					Double quantity = af.getDivQuantity(psf.getProductid(), Constants.DEFAULT_UNIT_ID, psf.getStockpile());

					psf.setPrice(0d);
					psf.setSquantity(quantity);
					psf.setNccode((String) mapTmp.get("mcode"));
					// 设置产品的计量单位
					Product p = ap.getProductByID(psf.getProductid());
					psf.setCountUnitName(HtmlSelect.getResourceName(request, "CountUnit", p.getCountunit()));
					// 获取产品的所有单位
					List<FUnit> unitList = appFUnit.getFUnitByProductID(p.getId());
					List<Map> uMapList = new ArrayList<Map>();
					for (FUnit fUnit : unitList) {
						Map uMap = new HashMap<String, String>();
						uMap.put("unitId", fUnit.getFunitid());
						uMap.put("xQuantity", fUnit.getXquantity());
						uMapList.add(uMap);
					}
					// 增加计量单位
					Map uLMap = new HashMap<String, String>();
					uLMap.put("unitId", 0);
					uLMap.put("xQuantity", p.getBoxquantity());
					uMapList.add(uLMap);

					psf.setUnitList(uMapList.toString());
					sls.add(psf);
				}
				request.setAttribute("wid", strwid);
				request.setAttribute("oid", oid);
				request.setAttribute("list", sls);
				return mapping.findForward("selectproduct");
			} else if (w.getIsMinusStock() == 1) {
				if(!StringUtil.isEmpty(dtype) 
						&& DeliveryType.BONUS.getValue().toString().equals(dtype)) {
					condition = " p.id not in (select tagsubvalue from BaseResource where isuse=1 and tagname='NoBonusProduct') and ";
				}
				String Condition = " op.organid='" + oid + "' and op.productid = p.id and p.useflag=1 ";
				whereSql = whereSql +condition+ blur + leftblur + Condition;
				whereSql = DbUtil.getWhereSql(whereSql);
				if (!StringUtil.isEmpty(productname)) {
					whereSql += " and p.productname = '" + productname + "'";
				}
				
				//分装厂
				if (!(null==organ.getOrganModel()) ) {
					if(2==organ.getOrganModel()){
						whereSql += " and p.id in (select productId from UnitInfo where organId='"+organ.getId()+"' and isactive = 1) " ;
					}
				}
				List<Product> list = appOrganProduct.getOrganProductObj(request, pagesize, whereSql);
				for (Product p : list) {
					OrganProductForm pf = new OrganProductForm();
					pf.setProductid(p.getId());
					pf.setProductname(p.getProductname());
					pf.setSpecmode(p.getSpecmode());
					// 默认单位只能选择件
					// pf.setUnitid(p.getSunit());
					// pf.setUnitidname(HtmlSelect.getResourceName(request,"CountUnit",
					// p.getSunit()));
					pf.setUnitid(Constants.DEFAULT_UNIT_ID);
					pf.setUnitidname(HtmlSelect.getResourceName(request, "CountUnit", Constants.DEFAULT_UNIT_ID));
					pf.setPrice(0d);
					pf.setNccode(p.getmCode());
					// 设置产品的计量单位
					pf.setCountUnitName(HtmlSelect.getResourceName(request, "CountUnit", p.getCountunit()));

					// 获取产品的所有单位
					List<FUnit> unitList = appFUnit.getFUnitByProductID(p.getId());
					List<Map> uMapList = new ArrayList<Map>();
					for (FUnit fUnit : unitList) {
						Map uMap = new HashMap<String, String>();
						uMap.put("unitId", fUnit.getFunitid());
						uMap.put("xQuantity", fUnit.getXquantity());
						uMapList.add(uMap);
					}
					// 增加计量单位
					Map uLMap = new HashMap<String, String>();
					uLMap.put("unitId", 0);
					uLMap.put("xQuantity", p.getBoxquantity());
					uMapList.add(uLMap);
					pf.setUnitList(uMapList.toString());
					sls.add(pf);
				}
				request.setAttribute("wid", strwid);
				request.setAttribute("OID", oid);
				request.setAttribute("list", sls);
				return mapping.findForward("selectproduct");
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return new ActionForward(mapping.getInput());
	}
}
