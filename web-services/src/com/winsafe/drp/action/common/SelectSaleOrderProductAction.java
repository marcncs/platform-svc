package com.winsafe.drp.action.common;

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
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppInvoiceConf;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.OrganPrice;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.SaleOrderProductForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

public class SelectSaleOrderProductAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		WarehouseService aw = new WarehouseService();
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
		if (strwid != null) {
			wid = strwid;
		}
		if (wid == null || wid.equals("")) {
			wid = aw.getWarehouseByOID(organid).getId();
		}

		request.getSession().setAttribute("cid", cid);
		request.getSession().setAttribute("organid", organid);
		request.getSession().setAttribute("icid", icid);

		super.initdata(request);
		try{
			AppCustomer ac = new AppCustomer();
			Customer c = ac.getCustomer(cid);
			String strCondition = " p.id=op.productid and p.id=o.productid and op.organid=o.organid and p.sunit=o.unitid "
					+ "and op.organid='"
					+ organid
					+ "' and p.wise=0 and o.policyid=" + c.getPolicyid() + " ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "Product" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String priceCondition = DbUtil.getPriceCondition(map, tmpMap,
					"UnitPrice");
			String blur = DbUtil.getOrBlur(map, tmpMap, "p.id", "ProductName",
					"SpecMode", "PYCode");

			whereSql = whereSql + leftblur + priceCondition + blur
					+ strCondition;
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppProduct ap = new AppProduct();
			AppFUnit appfu = new AppFUnit();
			AppInvoiceConf appic = new AppInvoiceConf();
			AppProductStockpileAll aps = new AppProductStockpileAll();

			List pls = ap
					.getSelectSaleOrderProduct(request, pagesize, whereSql);
			ArrayList sls = new ArrayList();

			Double price = 0d;
			for (int i = 0; i < pls.size(); i++) {
				SaleOrderProductForm pf = new SaleOrderProductForm();
				Object[] o = (Object[]) pls.get(i);
				Product p = (Product)o[0];
				OrganPrice op = (OrganPrice)o[1];
				pf.setProductid(p.getId());
				pf.setPsproductname(p.getProductname());
				pf.setPsspecmode(p.getSpecmode());
				pf.setCountunit(p.getSunit());
				pf.setCountunitname(HtmlSelect.getResourceName(request, "CountUnit", p.getSunit()));
				pf.setWarehouseid(wid);
				pf.setWarehouseidname(aw.getWarehouseName(wid));
				pf.setBatch("");
				pf.setIsidcode(p.getIsidcode());
				pf.setPrice(op.getUnitprice());

				pf.setTaxprice(op.getUnitprice());
				
				pf.setPeddleprice(ap.getProductPriceByOIDPIDRate(users
						.getMakeorganid(), pf.getProductid(),
						pf.getCountunit(), 0));
				pf.setWise(p.getWise());
				pf.setDiscount(c.getDiscount());
				pf.setTaxrate(appic.getInvoiceConfById(Integer.valueOf(icid))
						.getIvrate());
				pf.setStockpile(aps.getProductStockpileAllByPIDUIdWID(pf
						.getProductid(), userid, wid));
				pf.setSquantity(appfu.getStockpileQuantity2(p.getId(), p.getSunit(), pf.getStockpile()));
				sls.add(pf);
			}

			List wls = aw.getWarehouseListByOID(organid);
			ArrayList alw = new ArrayList();
			for (int i = 0; i < wls.size(); i++) {
				Warehouse w = (Warehouse) wls.get(i);
				if (w.getWarehouseproperty().intValue() == 0) {
					alw.add(w);
				}
			}

			request.setAttribute("whls", alw);
			request.setAttribute("sls", sls);
			request.setAttribute("cid", cid);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
