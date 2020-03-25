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
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.SaleOrderProductForm;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;

public class SelectSaleOrderPresentAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

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
		String wid = "";
		if(strwid !=null){
		wid = strwid;
		}
		if (wid == null||wid.equals("")) {
			AppWarehouse aw = new AppWarehouse();
			wid = aw.getWarehouseByOID(organid).getId();
		}

		request.getSession().setAttribute("cid", cid);
		request.getSession().setAttribute("organid", organid);
		request.getSession().setAttribute("icid", icid);

		try {
			String strCondition = " ps.warehouseid=w.id and w.warehouseproperty=2 " +
			" and ps.productid=p.id and w.makeorganid='"+organid+"' and ps.stockpile>0 "; 
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "ProductStockpile", "Product"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String blur = DbUtil.getOrBlur(map, tmpMap, "ProductID", "ProductName",
					"SpecMode", "PYCode");

			whereSql = whereSql + leftblur + blur + strCondition;
			whereSql = DbUtil.getWhereSql(whereSql); 

//			AppProduct ap = new AppProduct();
			AppCustomer ac = new AppCustomer();
			AppWarehouse aw = new AppWarehouse();
			AppInvoiceConf appic = new AppInvoiceConf();
			AppProductStockpile aps = new AppProductStockpile();

			List pls = aps.getProductStockpilePresent(request, pagesize, whereSql);
			ArrayList sls = new ArrayList();
			Customer c = ac.getCustomer(cid);

			for (int i = 0; i < pls.size(); i++) {
				SaleOrderProductForm pf = new SaleOrderProductForm();
				Object[] o = (Object[]) pls.get(i);
				pf.setProductid(String.valueOf(o[0]));
				pf.setPsproductname(String.valueOf(o[1]));
				pf.setPsspecmode(String.valueOf(o[2]));
				pf.setCountunit(Integer.valueOf(String.valueOf(o[3])));
				pf.setCountunitname(Internation.getStringByKeyPositionDB(
						"CountUnit", pf.getCountunit()));
				pf.setWarehouseid(String.valueOf(o[4].toString()));
				pf.setWarehouseidname(aw.getWarehouseByID(pf.getWarehouseid()).getWarehousename());
				pf.setBatch("");
				// pf.setStockpile(Double.valueOf(o[6].toString()));
				// pf.setStandardpurchase(Double.valueOf(o[4].toString()));
//				price =ap.getProductPriceByOIDPIDRate(users
//						.getMakeorganid(), pf.getProductid(),
//						pf.getCountunit(), Long.valueOf(c.getPolicyid()));
				pf.setPrice(0d);
//				strprice = DataFormat.dataFormatStr(price);
//			if(Integer.valueOf(icid)>0){
				pf.setTaxprice(0d);
//				}else{
//					pf.setTaxprice(price);
//				}
				
				pf.setWise(0);
				pf.setDiscount(c.getDiscount());
				pf.setTaxrate(appic.getInvoiceConfById(Integer.valueOf(icid))
						.getIvrate());
				pf.setStockpile(Double.valueOf(o[6].toString()));
				sls.add(pf);
			}

			List wls = aw.getWarehouseListByOID(organid);
			ArrayList alw = new ArrayList();
			for (int i = 0; i < wls.size(); i++) {
				//Warehouse w = new Warehouse();
				Warehouse w = (Warehouse) wls.get(i);
				//w.setId(Long.valueOf(o[0].toString()));
				//w.setWarehousename(o[1].toString());
				if ( w.getWarehouseproperty().intValue() == 2 ){
					alw.add(w);
				}				
			}
			// 产品结构
//			AppProductStruct appProductStruct = new AppProductStruct();
//			List uls = appProductStruct.getProductStructCanUse();
			String brandselect = Internation.getSelectTagByKeyAllDB("Brand",
					"Brand", true);

			request.setAttribute("brandselect", brandselect);
//			request.setAttribute("uls", uls);
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
