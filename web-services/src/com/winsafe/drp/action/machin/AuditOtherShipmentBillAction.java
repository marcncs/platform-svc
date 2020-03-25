package com.winsafe.drp.action.machin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOtherShipmentBillAll;
import com.winsafe.drp.dao.AppOtherShipmentBillDAll;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.OtherShipmentBillAll;
import com.winsafe.drp.dao.OtherShipmentBillDAll;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AuditOtherShipmentBillAction extends BaseAction {
	private AppProductStockpile appps = new AppProductStockpile();
	private AppProductStockpileAll apppsa = new AppProductStockpileAll();
	private AppFUnit af = new AppFUnit();
	private AppWarehouse aw = new AppWarehouse();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();

		super.initdata(request);
		try {

			String id = request.getParameter("ID");
			AppOtherShipmentBillAll apb = new AppOtherShipmentBillAll();
			OtherShipmentBillAll tt = apb.getOtherShipmentBillByID(id);

			if (tt.getIsblankout() == 1) {
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (tt.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.audit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppOtherShipmentBillDAll apid = new AppOtherShipmentBillDAll();
			List<OtherShipmentBillDAll> pils = apid.getOtherShipmentBillDetailBySbID(id);

			// 如果仓库属性[是否负库存]为0,则检查库存
			Warehouse outWarehouse = aw.getWarehouseByID(tt.getWarehouseid());
			StringBuffer errorMsg = new StringBuffer();
			if(outWarehouse.getIsMinusStock() == null || outWarehouse.getIsMinusStock() == 0){
				for (OtherShipmentBillDAll ttd : pils)
				{
					double q = af.getQuantity(ttd.getProductid(), ttd.getUnitid(), ttd.getQuantity());
					double stock = apppsa.getProductStockpileAllByPIDWID(ttd.getProductid(), tt.getWarehouseid());
					if (q > stock)
					{
						errorMsg.append("产品 [ " + ttd.getProductid() + " " + ttd.getProductname() + " ] 库存不足<br/>");
					}
				}
			}
			if(errorMsg.length() > 0){
				request.setAttribute("result", errorMsg.toString() + "<br/>不能复核!");
				return new ActionForward("/sys/lockrecordclose2.jsp");
			}
			
			
			
			
			AppProduct ap = new AppProduct();
			ProductStockpile ps = null;
			for (OtherShipmentBillDAll ttd : pils) {
				ps = new ProductStockpile();
				ps.setId(Long.valueOf(MakeCode
						.getExcIDByRandomTableName("product_stockpile", 0, "")));
				ps.setProductid(ttd.getProductid());
				Product p = ap.getProductByID(ttd.getProductid());
				ps.setCountunit(p.getSunit());
				ps.setBatch(ttd.getBatch());
				ps.setProducedate("");
				ps.setVad("");
				ps.setWarehouseid(tt.getWarehouseid());
				ps.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
				ps.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
				appps.addProductByPurchaseIncome2(ps);
				// 原复核操作
				// if (tt.getIsaccount() != null && tt.getIsaccount() == 1) {
				// // //对Product_Stockpile的更新
				// appps.outPrepareout(tt.getWarehouseid(),
				// Constants.WAREHOUSE_BIT_DEFAULT, ttd
				// .getProductid(), ttd.getUnitid(), ttd.getBatch(),
				// ttd.getQuantity(), tt
				// .getId(), "其他出入库-其他出库", true);
				// } else {
				// appps.outPrepareout(tt.getWarehouseid(),
				// Constants.WAREHOUSE_BIT_DEFAULT, ttd
				// .getProductid(), ttd.getUnitid(), ttd.getBatch(),
				// ttd.getQuantity(), tt
				// .getId(), "其他出入库-其他出库", false);
				// }
				// 对Product_Stockpile_All的更新
				// apppsa.outPrepareout(tt.getWarehouseid(), ttd.getProductid(),
				// ttd.getUnitid(), ttd
				// .getBatch(), ttd.getQuantity());
				// 新复核操作
				Double quantity = af.getQuantity(ttd.getProductid(), ttd.getUnitid(), ttd
						.getQuantity());
				
				if (tt.getIsaccount() != null && tt.getIsaccount() == 1) {
					// 记台账
					appps.outProductStockpile(ttd.getProductid(), p.getSunit(), ttd.getBatch(),
							quantity, tt.getWarehouseid(), ps.getWarehousebit(), tt
									.getId(), "出库-其他出库");
				} else {
					appps.outProductStockpileWithOutAccount(ttd.getProductid(), p.getSunit(),
							ttd.getBatch(), quantity, tt.getWarehouseid(), ps
									.getWarehousebit(), tt.getId(), "出库-其他出库");
				}

			}

			apb.updIsAudit(id, userid, 1);

			request.setAttribute("result", "databases.audit.success");
			DBUserLog.addUserLog(request, "编号：" + id);
			HibernateUtil.commitTransaction();
			return mapping.findForward("audit");
		} catch (Exception e) {
			e.printStackTrace();
			HibernateUtil.rollbackTransaction();
		}
		return new ActionForward(mapping.getInput());
	}
}
