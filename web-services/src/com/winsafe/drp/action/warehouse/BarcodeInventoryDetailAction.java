package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBarcodeInventory;
import com.winsafe.drp.dao.AppBarcodeInventoryDAll;
import com.winsafe.drp.dao.AppBarcodeInventoryDetail;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.BarcodeInventory;
import com.winsafe.drp.dao.BarcodeInventoryDAll;
import com.winsafe.drp.dao.BarcodeInventoryDetail;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpileAll;
import com.winsafe.drp.dao.ProductStockpileForm;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.StringUtil;

public class BarcodeInventoryDetailAction extends BaseAction {
	private WarehouseService aw = new WarehouseService();
	private AppFUnit af = new AppFUnit();
	private AppProductStruct appPS = new AppProductStruct();
	private AppBarcodeInventoryDAll abida = new AppBarcodeInventoryDAll();
	private AppProduct ap = new AppProduct();
	Logger logger = Logger.getLogger(BarcodeInventory.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("ID");
		String warehouseid = request.getParameter("Warehouseid");

		super.initdata(request);
		try {
			AppBarcodeInventory abi = new AppBarcodeInventory();
			BarcodeInventory bi = abi.getBarcodeInventoryByID(id);
			if (StringUtil.isEmpty(warehouseid)) {
				warehouseid = bi.getWarehouseid();
			}
			if (bi == null) {
				request.setAttribute("result", "databases.record.delete");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (bi.getIsaudit() == 0) {
				AppBarcodeInventoryDetail abid = new AppBarcodeInventoryDetail();
				logger.debug("获取barcodeinventorydetail");
				// 遍历barcodeinventorydetail，找出product_stock_pile中不存在的产品，并将其加入到其中
				List<BarcodeInventoryDetail> pspList = abid.getBarcodeInventoryDetailByID(id);
				for (BarcodeInventoryDetail bDetail : pspList) {
					Product product = ap.loadProductById(bDetail.getProductid());
					abid.insProductStockPileByNone(bDetail.getProductid(), bDetail.getBatch(),
							warehouseid, product.getSunit());
				}

				String sql = " where p.id = ps.productid and pstr.structcode = p.psid and warehouseid='"
						+ warehouseid
						+ "' AND ps.warehouseid IN ( SELECT wv.warehouseId FROM RuleUserWh AS wv WHERE wv.userId = '"
						+ userid + "')";
				// 获取所有产品库存
				List pls = abid.getAllBarcodeDetailByW(request, sql);
				List alp = new ArrayList();
				for (int i = 0; i < pls.size(); i++) {
					ProductStockpileForm psf = new ProductStockpileForm();
					Object[] o = (Object[]) pls.get(i);
					ProductStockpileAll ps = (ProductStockpileAll) o[0];
					Product p = (Product) o[1];

					psf.setId(ps.getId());
					psf.setProductid(ps.getProductid());
					psf.setPsproductname(p.getProductname());
					psf.setPsspecmode(p.getSpecmode());
					psf.setBatch(ps.getBatch());
					psf.setSpecmode(p.getSpecmode());
					psf.setPrepareout(ps.getPrepareout());
					psf.setWarehouseid(ps.getWarehouseid());
					psf.setWarehourseidname(aw.getWarehouseName(ps.getWarehouseid()));
					psf.setNccode(p.getmCode());
					psf.setRemark(ps.getRemark());
					psf.setVerifydate(ps.getVerifydate());
					psf.setCountunit(Constants.DEFAULT_UNIT_ID);
					Double xquantity = af.getXQuantity(psf.getProductid(), psf.getCountunit());
					if (xquantity != 0) {
						psf.setAllstockpile((ps.getStockpile() + ps.getPrepareout()) / xquantity);
					} else {
						psf.setAllstockpile(0.0);
					}
					List<Map> als = abid.getNumByProductid(request, psf.getProductid(), id, psf
							.getBatch());
					if (als.size() > 0) {
						for (Map map : als) {
							psf.setStockpile(Double.valueOf(map.get("quantity").toString()));
						}
					}
					if (psf.getStockpile() != null && psf.getAllstockpile() != null) {
						psf.setSquantity(Math.abs(psf.getStockpile() - psf.getAllstockpile()));
					}
					psf.setVerifyStatus(ps.getVerifyStatus());
					psf.setSortName(appPS.getName(p.getPsid()));
					alp.add(psf);
				}
				request.setAttribute("als", alp);
			} else {
				List<BarcodeInventoryDAll> pls = abida.getBarcodeInventoryDAllByID(bi.getId());
				List alp = new ArrayList();
				for (int i = 0; i < pls.size(); i++) {
					ProductStockpileForm psf = new ProductStockpileForm();
					BarcodeInventoryDAll bida = pls.get(i);
					Product p = ap.getProductByID(bida.getProductid());
					if (p != null) {
						psf.setNccode(p.getmCode());
					}
					psf.setProductid(bida.getProductid());
					psf.setPsproductname(bida.getProductname());
					psf.setSpecmode(bida.getSpecmode());
					psf.setCountunit(bida.getUnitid());
					psf.setBatch(bida.getBatch());
					psf.setAllstockpile(bida.getUnitprice());
					psf.setStockpile(bida.getQuantity());
					psf.setSquantity(bida.getTakequantity());
					alp.add(psf);
				}
				request.setAttribute("als", alp);
			}
			request.setAttribute("osbf", bi);
			return mapping.findForward("detail");
		} catch (Exception e) {
			logger.error("", e);
		}
		return new ActionForward(mapping.getInput());
	}
}
