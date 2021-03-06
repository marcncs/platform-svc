package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppBarcodeInventory;
import com.winsafe.drp.dao.AppBarcodeInventoryDetail;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppOtherShipmentBill;
import com.winsafe.drp.dao.AppOtherShipmentBillDetail;
import com.winsafe.drp.dao.AppOtherShipmentBillIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.BarcodeInventory;
import com.winsafe.drp.dao.BarcodeInventoryDetail;
import com.winsafe.drp.dao.OtherShipmentBill;
import com.winsafe.drp.dao.OtherShipmentBillDetail;
import com.winsafe.drp.dao.OtherShipmentBillIdcode;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class CancelAuditBarcodeInventoryAction extends BaseAction {
	private AppIdcode appidcode = new AppIdcode();
	private AppOtherShipmentBillIdcode aptti = new AppOtherShipmentBillIdcode();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		super.initdata(request);
		try {
			// 获取取消复核id
			String id = request.getParameter("ID");

			AppBarcodeInventory abi = new AppBarcodeInventory();
			BarcodeInventory bi = abi.getBarcodeInventoryByID(id);
			if (bi != null) {
				// 如果已经作废
				if (bi.getIsblankout() != null && bi.getIsblankout() == 1) {
					request.setAttribute("result", "databases.record.blankoutnooperator");
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
				// 如果已经复核
				if (bi.getIsaudit() == 0) {
					request.setAttribute("result", "databases.record.noaudit");
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}

			// List<OtherShipmentBillIdcode> ttilist =
			// aptti.getOtherShipmentBillIdcodeByosid(id);
			// returnOutProductStockpile(ttilist, tt.getWarehouseid());
			// List<OtherShipmentBillIdcode> idcodelist =
			// aptti.getOtherShipmentBillIdcodeByosid(id, 1);
			// setIdcodeUse(idcodelist);

			AppProduct ap = new AppProduct();
			ProductStockpile ps = null;
			AppProductStockpile appps = new AppProductStockpile();
			AppProductStockpileAll apppsa = new AppProductStockpileAll();
			AppBarcodeInventoryDetail abid = new AppBarcodeInventoryDetail();
			List<BarcodeInventoryDetail> labid = abid.getBarcodeInventoryDetailByID(id);
			for (BarcodeInventoryDetail ttd : labid) {

				ps = new ProductStockpile();
				ps.setId(Long.valueOf(MakeCode
						.getExcIDByRandomTableName("product_stockpile", 0, "")));
				ps.setProductid(ttd.getProductid());
				ps.setCountunit(ap.getProductByID(ps.getProductid()).getCountunit());
				ps.setBatch(ttd.getBatch());
				ps.setProducedate("");
				ps.setVad("");
				ps.setWarehouseid(bi.getWarehouseid());
				ps.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
				ps.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
				appps.addProductByPurchaseIncome2(ps);
//				appps.returnPrepareout(bi.getWarehouseid(), Constants.WAREHOUSE_BIT_DEFAULT, ttd
//						.getProductid(), ttd.getUnitid(), ttd.getBatch(), ttd.getQuantity(), bi
//						.getId(), "复核其他出库单-出货");
//				apppsa.returnPrepareout(bi.getWarehouseid(), ttd.getProductid(), ttd.getUnitid(),
//						ttd.getBatch(), ttd.getQuantity());
				appps.returnoutProductStockpile(ttd.getProductid(), ttd.getUnitid(), ttd.getBatch(), ttd.getQuantity(),
						bi.getWarehouseid(), Constants.WAREHOUSE_BIT_DEFAULT, bi.getId(), "取消复核其他出库单");
			}
			// 修改复核标志
			abi.updIsNotAudit(id);
			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(request, "编号：" + id);
			HibernateUtil.commitTransaction();
			return mapping.findForward("noaudit");
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

	private void returnOutProductStockpile(List<OtherShipmentBillIdcode> idlist, String warehouseid)
			throws Exception {
		AppProductStockpile aps = new AppProductStockpile();
		for (OtherShipmentBillIdcode idcode : idlist) {
			aps.returnOutProductStockpile(idcode.getProductid(), idcode.getUnitid(), idcode
					.getBatch(), idcode.getQuantity(), warehouseid, idcode.getWarehousebit(),
					idcode.getOsid(), "取消复核其他出库单-入货");
		}
	}

	private void setIdcodeUse(List<OtherShipmentBillIdcode> idlist) throws Exception {
		for (OtherShipmentBillIdcode idcode : idlist) {
			idcode.setIssplit(0);
			aptti.updOtherShipmentBillIdcode(idcode);
			appidcode.updIsUseOut(idcode.getIdcode(), 1, 0);
		}
	}

}
