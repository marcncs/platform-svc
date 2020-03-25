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
import com.winsafe.drp.dao.AmountInventory;
import com.winsafe.drp.dao.AmountInventoryDetail;
import com.winsafe.drp.dao.AmountInventoryForm;
import com.winsafe.drp.dao.AppAmountInventory;
import com.winsafe.drp.dao.AppAmountInventoryDetail;
import com.winsafe.drp.dao.AppBarcodeInventory;
import com.winsafe.drp.dao.AppBarcodeInventoryDetail;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.BarcodeInventory;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpileAll;
import com.winsafe.drp.dao.ProductStockpileForm;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppPrintJob;

public class AmountInventoryDetailAction extends BaseAction {
	private WarehouseService aw = new WarehouseService();
	private AppFUnit af = new AppFUnit();
	private AppProductStruct appPS = new AppProductStruct();
	private AppProductStockpileAll aps = new AppProductStockpileAll();
	private AppPrintJob apj = new AppPrintJob();
	private AppProduct ap = new AppProduct();
	Logger logger = Logger.getLogger(AmountInventoryDetailAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("ID");
		String warehouseid = request.getParameter("Warehouseid");

		super.initdata(request);
		try {
			AppAmountInventory aai = new AppAmountInventory();
			AmountInventory ai = aai.getAmountInventoryByID(id);
			if (ai == null) {
				request.setAttribute("result", "databases.record.delete");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			AppAmountInventoryDetail aaid = new AppAmountInventoryDetail();
			List calp = new ArrayList();
			List<AmountInventoryDetail> alp = aaid.getAmountInventoryDetailByID(id);
			
			for (AmountInventoryDetail amountInventoryDetail : alp) {
				AmountInventoryForm aif = new AmountInventoryForm();
				aif.setProductid(amountInventoryDetail.getProductid());
				aif.setProductname(amountInventoryDetail.getProductname());
				aif.setSpecmode(amountInventoryDetail.getSpecmode());
				aif.setUnitid(amountInventoryDetail.getUnitid());
				//用于显示
				if(!StringUtil.isEmpty(amountInventoryDetail.getProductid())){
					Product p = ap.getProductByID(amountInventoryDetail.getProductid());
					if (p != null) {
						aif.setNccode(p.getmCode());
					}
				}
				aif.setUnitprice(amountInventoryDetail.getUnitprice());
				aif.setQuantity(amountInventoryDetail.getQuantity());
				aif.setTakequantity(amountInventoryDetail.getTakequantity());
				aif.setRemark(amountInventoryDetail.getRemark());
				aif.setBatch(amountInventoryDetail.getBatch());
				calp.add(aif);
			}
			
//			logger.debug("获取AmountInventoryDetail");
//			String sql = " where p.id = ps.productid and pstr.structcode = p.psid and warehouseid='" + warehouseid + "' AND ps.warehouseid IN ( SELECT wv.warehouseId FROM RuleUserWh AS wv WHERE wv.userId = '"+userid+"')";
//			List pls = aaid.getAllAmountDetailByW(request, sql);
//			List alp = new ArrayList();
//			for (int i = 0; i < pls.size(); i++) {
//				ProductStockpileForm psf = new ProductStockpileForm();
//				Object[] o = (Object[]) pls.get(i);
//				ProductStockpileAll ps = (ProductStockpileAll) o[0];
//				Product p = (Product) o[1];
//				psf.setId(ps.getId());
//				psf.setProductid(ps.getProductid());
//				psf.setPsproductname(p.getProductname());
//				psf.setPsspecmode(p.getSpecmode());
//				psf.setBatch(ps.getBatch());
//				psf.setSpecmode(p.getSpecmode());
//				psf.setPrepareout(ps.getPrepareout());
//				psf.setWarehouseid(ps.getWarehouseid());
//				psf.setWarehourseidname(aw.getWarehouseName(ps.getWarehouseid()));
//				psf.setNccode(p.getmCode());
//				psf.setRemark(ps.getRemark());
//				psf.setVerifydate(ps.getVerifydate());
//				// psf.setCountunit(p.getSunit());
//				psf.setCountunit(Constants.DEFAULT_UNIT_ID);
//				Double xquantity = af.getXQuantity(psf.getProductid(), psf.getCountunit());
//				if (xquantity != 0) {
//					psf.setAllstockpile((ps.getStockpile() + ps.getPrepareout()) / xquantity);
//				} else {
//					psf.setAllstockpile(0.0);
//				}
//				List<Map> als = abid.getNumByProductid(request, psf.getProductid(),id,psf.getBatch());
//				if (als.size() > 0) {
//					for (Map map : als) {
//						for (Object key : map.keySet()) {
//							if (key.equals("quantity")) {
//								psf.setStockpile(Double.valueOf(map.get(key).toString()));
//							}
//						}
//					}
//				}
//				psf.setVerifyStatus(ps.getVerifyStatus());
//				psf.setSortName(appPS.getName(p.getPsid()));
//				alp.add(psf);
//			}
			
			request.setAttribute("als", calp);
			request.setAttribute("osbf", ai);

			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
