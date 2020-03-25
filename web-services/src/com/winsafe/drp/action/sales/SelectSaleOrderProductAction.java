package com.winsafe.drp.action.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectSaleOrderProductAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);
		int pagesize = 20;
		String cid = request.getParameter("cid");
		if (null == cid) {
			cid = (String) request.getSession().getAttribute("cid");
		}
		String organid = request.getParameter("organid");
		if (null == organid) {
			organid = (String) request.getSession().getAttribute("organid");
		}
		if (null == organid) {
			organid = users.getMakeorganid();
		}
		String icid = request.getParameter("icid");
		if (null == icid) {
			icid = (String) request.getSession().getAttribute("icid");
		}

		String strwid = request.getParameter("warehouseid");
//		Integer wid = null;
//		if(strwid !=null){
//		wid = Integer.valueOf(strwid);
//		}
//		if (wid == null) {
//			AppWarehouse aw = new AppWarehouse();
//			wid = aw.getWarehouseByOID(organid).getId();
//		}

		request.getSession().setAttribute("cid", cid);
		request.getSession().setAttribute("organid", organid);
		request.getSession().setAttribute("icid", icid);

		try {
			AppCustomer ac = new AppCustomer();
			Customer c = ac.getCustomer(cid);
			String strCondition = " p.id=op.productid and p.id=o.productid and op.organid=o.organid and p.countunit=o.unitid "+ 
			"and op.organid='"+organid+"' and p.wise=0 and o.policyid="+c.getPolicyid()+" ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "Product"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String priceCondition = DbUtil.getPriceCondition(map, tmpMap,
			"UnitPrice");
			String blur = DbUtil.getOrBlur(map, tmpMap, "p.id", "ProductName",
					"SpecMode", "PYCode");

			whereSql = whereSql + leftblur + priceCondition + blur + strCondition;
			whereSql = DbUtil.getWhereSql(whereSql); 
			// System.out.println("==11=sql="+whereSql);

			Object obj[] = (DbUtil.setDynamicPager(request,
					"Product as p,OrganProduct as op, OrganPrice as o",
					whereSql, pagesize, " ppsCondition "));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppProduct ap = new AppProduct();
			
			AppWarehouse aw = new AppWarehouse();
			AppInvoiceConf appic = new AppInvoiceConf();
			AppProductStockpile aps = new AppProductStockpile();

//			List pls = ap.getSelectSaleOrderProduct(pagesize, whereSql,
//					tmpPgInfo);
			ArrayList sls = new ArrayList();
			
			Double price=0d;
			//String strprice="";
			// System.out.println("===============================size="+pls.size());
//			for (int i = 0; i < pls.size(); i++) {
//				SaleOrderProductForm pf = new SaleOrderProductForm();
//				Object[] o = (Object[]) pls.get(i);
//				pf.setProductid(String.valueOf(o[0]));
//				pf.setPsproductname(String.valueOf(o[1]));
//				pf.setPsspecmode(String.valueOf(o[2]));
//				pf.setCountunit(Integer.valueOf(String.valueOf(o[3])));
//				pf.setCountunitname(Internation.getStringByKeyPositionDB(
//						"CountUnit", pf.getCountunit()));
//				pf.setWarehouseid(strwid);
//				//pf.setWarehouseidname(aw.getWarehouseByID(wid).getWarehousename());
//				pf.setBatch("");
//				pf.setIsidcode(Integer.valueOf(String.valueOf(o[6])));
//				// pf.setStockpile(Double.valueOf(o[6].toString()));
//				// pf.setStandardpurchase(Double.valueOf(o[4].toString()));
////				price =ap.getProductPriceByOIDPIDRate(users
////						.getMakeorganid(), pf.getProductid(),
////						pf.getCountunit(), Integer.valueOf(c.getPolicyid()));
//				price = o[5]==null?0:Double.valueOf(o[5].toString());
//				pf.setPrice(price);
////				strprice = DataFormat.dataFormatStr(price);
////			if(Integer.valueOf(icid)>0){
//				pf.setTaxprice(price);
////				}else{
////					pf.setTaxprice(price);
////				}
//				//非会员价
//				pf.setPeddleprice(ap.getProductPriceByOIDPIDRate(users
//						.getMakeorganid(), pf.getProductid(),
//						pf.getCountunit(), Integer.valueOf(0)));
//				pf.setWise(Integer.valueOf(o[4].toString()));
//				pf.setDiscount(c.getDiscount());
//				pf.setTaxrate(appic.getInvoiceConfById(Integer.valueOf(icid))
//						.getIvrate());
//				pf.setStockpile(aps.getProductStockpileByPIDUIdWID(pf.getProductid(), userid, strwid));
//				sls.add(pf);
//			}

			List wls = aw.getWarehouseListByOID(organid);
			ArrayList alw = new ArrayList();
			for (int i = 0; i < wls.size(); i++) {
				Warehouse w = (Warehouse) wls.get(i);
				if ( w.getWarehouseproperty().intValue() == 0 ){
					alw.add(w);
				}				
			}
			// 产品结构
			AppProductStruct appProductStruct = new AppProductStruct();
			List uls = appProductStruct.getProductStructCanUse();
			String brandselect = Internation.getSelectTagByKeyAllDB("Brand",
					"Brand", true);

			request.setAttribute("brandselect", brandselect);
			request.setAttribute("uls", uls);
			request.setAttribute("whls", alw);
			request.setAttribute("sls", sls);
			request.setAttribute("cid", cid);
			return mapping.findForward("selectproduct");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
