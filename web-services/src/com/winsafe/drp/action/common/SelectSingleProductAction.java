package com.winsafe.drp.action.common;

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
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductForm;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.erp.dao.AppUnitInfo;
import com.winsafe.erp.pojo.UnitInfo;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.StringUtil;

public class SelectSingleProductAction extends BaseAction { 
	private static Logger logger = Logger.getLogger(SelectSingleProductAction.class);
	private AppWarehouse aw = new AppWarehouse();
	private AppFUnit appFUnit = new AppFUnit();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 20;
		super.initdata(request);
		try {
			//判断是否获取杭州工厂码申请产品 
			String type = request.getParameter("type");
			if("hz".equals(type)) {
				selectHzProduct(request);
				return mapping.findForward("selecthzproduct");
			}
			
			String isSeparate = request.getParameter("isseparate");
			String proId = request.getParameter("proId");
			String wid = request.getParameter("wid");
			String isin = request.getParameter("isin");
			String oid = request.getParameter("oid");
			String unit = request.getParameter("unit");
			
			String Condition = "";
			if("1".equals(isSeparate)) {
				Condition = " p.id = ps.productid and p.useflag=1 and p.isunify = 1 and ps.warehouseid = '"+wid+"'";
			} else {
				Condition = " p.useflag=1 " ;
			}
			
			if(!StringUtil.isEmpty(oid)){
				if(StringUtil.isEmpty(unit)){
					Condition += " and p.id in (select productId from UnitInfo where organId='"+oid+"' and isactive = 1) " ;
				}
			}
			
			//过滤已选的产品
			if(!StringUtil.isEmpty(proId)) {
				Condition += " and p.productname = (select productname from Product where id = '"+proId+"') and id != '"+proId+"' ";
			}
			if(!StringUtil.isEmpty(wid)) {
				Warehouse w = aw.getWarehouseByID(wid);
				if((w.getIsMinusStock() == null || w.getIsMinusStock() == 0) && StringUtil.isEmpty(isin)) {
					if(!"1".equals(isSeparate)) {
						Condition += " and id in (select productid from ProductStockpile where stockpile > 0 and warehouseid = '"+wid+"') ";
					} 
				}
			}
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Product" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "p.PSID");
			String blur = DbUtil.getOrBlur2(map, tmpMap, "p.id", "p.productname",
					"p.pycode", "p.specmode", "p.mCode");

			whereSql = whereSql + leftblur + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppProduct ap = new AppProduct();
			
			AppBaseResource appBaseResource = new AppBaseResource();
			//标签打印份数
			int cartonLabelCount = Integer.parseInt(appBaseResource.getBaseResourceValue("CartonLabelCount", 1).getTagsubvalue());
			
			if("1".equals(isSeparate)) {
				List pls = ap.getSelectProductAndStockpile(request, pagesize, whereSql);
				ArrayList sls = new ArrayList();

				for (int i = 0; i < pls.size(); i++) {
					ProductForm pf = new ProductForm();
					Map o = (Map) pls.get(i);
					pf.setId((String)o.get("id"));
					pf.setProductname((String)o.get("productname"));
					pf.setSpecmode((String)o.get("specmode"));
					pf.setCountunit(Integer.parseInt((String)o.get("countunit")));
					pf.setCountunitname(HtmlSelect.getResourceName(request,
							"CountUnit",Integer.parseInt((String)o.get("countunit"))));
					pf.setNccode((String)o.get("mcode"));
					pf.setPackSizeName((String)o.get("packsizename"));
					pf.setSunit(Integer.parseInt((String)o.get("sunit")));
					pf.setSunitname(HtmlSelect.getResourceName(request,
							"CountUnit", Integer.parseInt((String)o.get("sunit"))));
					pf.setBoxquantity(Double.parseDouble((String)o.get("boxquantity")));
					pf.setStockpile(Double.parseDouble((String)o.get("stockpile")));
					// pf.setBarcode(String.valueOf(o.getBarcode()));
					//tommy add 增加是否License-in的选项
					if(!StringUtil.isEmpty((String)o.get("wise"))) {
						pf.setWise(Integer.parseInt((String)o.get("wise")));
					} 
					//标签份数不为空并且>0
					if ((Integer)o.get("copys") !=null && (Integer)o.get("copys")>0) {
						//标签份数
						pf.setCopys((Integer)o.get("copys"));
					}else {
						pf.setCopys(cartonLabelCount);
					}
					sls.add(pf);
				}
				request.setAttribute("sls", sls);
				
			} else if(!StringUtil.isEmpty(oid)) {
				List pls = ap.getSelectProduct(request, pagesize, whereSql);
				ArrayList sls = new ArrayList();
				
				String idsStr = "select id from Product as p " + whereSql;
				Map<String,FUnit> funitMap = appFUnit.getAllMapByPidsAndUnitId(idsStr, Constants.DEFAULT_UNIT_ID);
				AppUnitInfo appu = new AppUnitInfo();
				for (int i = 0; i < pls.size(); i++) {
					
					ProductForm pf = new ProductForm();
					Product o = (Product) pls.get(i);
					pf.setId(o.getId());
					pf.setProductname(o.getProductname());
					pf.setSpecmode(o.getSpecmode());
					pf.setCountunit(o.getCountunit());
					pf.setCountunitname(HtmlSelect.getResourceName(request,
							"CountUnit", o.getCountunit()));
					pf.setNccode(o.getmCode());
					pf.setPackSizeName(o.getPackSizeName());
					pf.setSunit(o.getSunit());
					pf.setSunitname(HtmlSelect.getResourceName(request,
							"CountUnit", o.getSunit()));
					pf.setBoxquantity(o.getBoxquantity());
					
					//tommy add 增加是否License-in的选项
					pf.setWise(o.getWise());
					
					FUnit funit = funitMap.get(o.getId());
					if(null!=funit){
						pf.setDefaultUnitQuantity(ArithDouble.mul(o.getBoxquantity(), funit.getXquantity()));
					}
					pf.setDefaultUnit(Constants.DEFAULT_UNIT_ID);
					pf.setDefaultUnitName(HtmlSelect.getResourceName(request,
							"CountUnit", Constants.DEFAULT_UNIT_ID));
					// pf.setBarcode(String.valueOf(o.getBarcode()));
					if(StringUtil.isEmpty(unit)) {
						UnitInfo unitinfo = appu.getUnitInfoByOidAndPid(oid,o.getId());
						pf.setAbcsort(unitinfo.getUnitCount());
						pf.setNeedCovertCode(unitinfo.getNeedCovertCode());
						pf.setCodeSeq(unitinfo.getCodeSeq());
					} 
					//标签份数不为空并且>0
					if (o.getCopys() !=null && o.getCopys()>0) {
						//标签份数
						pf.setCopys(o.getCopys());
					}else {
						pf.setCopys(cartonLabelCount);
					}
					sls.add(pf);
				}
				request.setAttribute("oid", oid);
				request.setAttribute("sls", sls);
			} 
//			else if(!StringUtil.isEmpty(unit)) {
//				List pls = ap.getSelectProduct(request, pagesize, whereSql);
//				ArrayList sls = new ArrayList();
//				
//				String idsStr = "select id from Product as p " + whereSql;
//				Map<String,FUnit> funitMap = appFUnit.getAllMapByPidsAndUnitId(idsStr, Constants.DEFAULT_UNIT_ID);
//				AppUnitInfo appu = new AppUnitInfo();
//				for (int i = 0; i < pls.size(); i++) {
//					
//					ProductForm pf = new ProductForm();
//					Product o = (Product) pls.get(i);
//					pf.setId(o.getId());
//					pf.setProductname(o.getProductname());
//					pf.setSpecmode(o.getSpecmode());
//					pf.setCountunit(o.getCountunit());
//					pf.setCountunitname(HtmlSelect.getResourceName(request,
//							"CountUnit", o.getCountunit()));
//					pf.setNccode(o.getmCode());
//					pf.setPackSizeName(o.getPackSizeName());
//					pf.setSunit(o.getSunit());
//					pf.setSunitname(HtmlSelect.getResourceName(request,
//							"CountUnit", o.getSunit()));
//					pf.setBoxquantity(o.getBoxquantity());
//					
//					FUnit funit = funitMap.get(o.getId());
//					pf.setDefaultUnitQuantity(ArithDouble.mul(o.getBoxquantity(), funit.getXquantity()));
//					pf.setDefaultUnit(Constants.DEFAULT_UNIT_ID);
//					pf.setDefaultUnitName(HtmlSelect.getResourceName(request,
//							"CountUnit", Constants.DEFAULT_UNIT_ID));
//					// pf.setBarcode(String.valueOf(o.getBarcode()));
//					
////					UnitInfo unitinfo = appu.getUnitInfoByOidAndPid(oid,o.getId());
////					pf.setAbcsort(unitinfo.getUnitCount());
//					sls.add(pf);
//				}
//				request.setAttribute("oid", oid);
//				request.setAttribute("sls", sls);
//			} 
			else {
				List pls = ap.getSelectProduct(request, pagesize, whereSql);
				ArrayList sls = new ArrayList();
//				StringBuffer ids = new StringBuffer();
//				for (int i = 0; i < pls.size(); i++) {
//					Product o = (Product) pls.get(i);
//					ids.append(",'"+o.getId()+"'");
//				}
				String idsStr = "select id from Product as p " + whereSql;
				Map<String,FUnit> funitMap = appFUnit.getAllMapByPidsAndUnitId(idsStr, Constants.DEFAULT_UNIT_ID);
				System.out.println("--------------------------------------");
				for (int i = 0; i < pls.size(); i++) {
					ProductForm pf = new ProductForm();
					Product o = (Product) pls.get(i);
					pf.setId(o.getId());
					pf.setProductname(o.getProductname());
					pf.setSpecmode(o.getSpecmode());
					pf.setCountunit(o.getCountunit());
					pf.setCountunitname(HtmlSelect.getResourceName(request,
							"CountUnit", o.getCountunit()));
					pf.setNccode(o.getmCode());
					pf.setPackSizeName(o.getPackSizeName());
					pf.setSunit(o.getSunit());
					pf.setSunitname(HtmlSelect.getResourceName(request,
							"CountUnit", o.getSunit()));
					pf.setBoxquantity(o.getBoxquantity());
					
					//tommy add 增加是否License-in的选项
					pf.setWise(o.getWise());
					System.out.println(o.getId());
					FUnit funit = funitMap.get(o.getId());
					pf.setDefaultUnitQuantity(ArithDouble.mul(o.getBoxquantity(), funit.getXquantity()));
					pf.setDefaultUnit(Constants.DEFAULT_UNIT_ID);
					pf.setDefaultUnitName(HtmlSelect.getResourceName(request,
							"CountUnit", Constants.DEFAULT_UNIT_ID));
					//标签份数不为空并且>0
					if (o.getCopys() !=null && o.getCopys()>0) {
						//标签份数
						pf.setCopys(o.getCopys());
					}else {
						pf.setCopys(cartonLabelCount);
					}
					// pf.setBarcode(String.valueOf(o.getBarcode()));
					sls.add(pf);
				}
				request.setAttribute("sls", sls);
			}
			 

			
			request.setAttribute("KeyWordLeft", request
					.getParameter("KeyWordLeft"));
			return mapping.findForward("selectproduct");
		} catch (Exception e) {
			logger.error("",e);
		}

		return new ActionForward(mapping.getInput());
	}

	private void selectHzProduct(HttpServletRequest request) throws Exception {
		int pagesize = 20;
		String Condition = " isidcode = 1 " ;

		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		String[] tablename = { "Product" };
		String whereSql = EntityManager.getTmpWhereSql(map, tablename);
		String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
		String blur = DbUtil.getOrBlur2(map, tmpMap, "id", "productname",
				"pycode", "specmode", "mCode", "productnameen");

		whereSql = whereSql + leftblur + blur + Condition;
		whereSql = DbUtil.getWhereSql(whereSql);

		AppProduct ap = new AppProduct();
		List<Product> pls = ap.getProduct(request, pagesize, whereSql);
		
		if(pls != null && pls.size() > 0) {
			Map<String,FUnit> funitMap = appFUnit.getAllMapByUnitId(Constants.DEFAULT_UNIT_ID);
			for(Product product : pls) {
				//设置包装比例关系
				FUnit funit = funitMap.get(product.getId());
				if(funit != null) {
					product.setPackingRatio(funit.getXquantity().intValue());
				}
			}
		}
		
		request.setAttribute("sls", pls);
		
		request.setAttribute("KeyWordLeft", request
				.getParameter("KeyWordLeft"));
		
	}
}
