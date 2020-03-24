package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOtherShipmentBill;
import com.winsafe.drp.dao.AppOtherShipmentBillDetail;
import com.winsafe.drp.dao.AppOtherShipmentBillIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppProductStockpileAll;
import com.winsafe.drp.dao.OtherShipmentBill;
import com.winsafe.drp.dao.OtherShipmentBillDetail;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.server.WarehouseBitDafService;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class DelOtherShipmentBillAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);try{

			String osid = request.getParameter("OSID");
			AppOtherShipmentBill asb = new AppOtherShipmentBill();
			OtherShipmentBill sb = asb.getOtherShipmentBillByID(osid);
			if (sb.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.nodel");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppProduct ap = new AppProduct();
			ProductStockpile ps = null;
			AppOtherShipmentBillDetail asbd = new AppOtherShipmentBillDetail();
			AppOtherShipmentBillIdcode apposi = new AppOtherShipmentBillIdcode();
			AppProductStockpileAll apsa = new AppProductStockpileAll();
			AppProductStockpile aps = new AppProductStockpile();
			List<OtherShipmentBillDetail> ls = asbd
					.getOtherShipmentBillDetailBySbID(osid);
			
			for (OtherShipmentBillDetail osd : ls) {

				ps = new ProductStockpile();
				
				ps.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
						"product_stockpile", 0, "")));
				ps.setProductid(osd.getProductid());
				
				ps.setCountunit(ap.getProductByID(ps.getProductid()).getCountunit());
				ps.setBatch(osd.getBatch());
				ps.setProducedate("");
				ps.setVad("");				
				ps.setWarehouseid(sb.getWarehouseid());
				ps.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
				ps.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
				aps.addProductByPurchaseIncome2(ps);
				
				apsa.freeStockpile(osd.getProductid(), osd.getUnitid(), sb
						.getWarehouseid(), osd.getBatch(), osd.getQuantity());
				aps.freeStockpile(osd.getProductid(), osd.getUnitid(), sb
						.getWarehouseid(), Constants.WAREHOUSE_BIT_DEFAULT, osd.getBatch(), osd.getQuantity());
			}

			asb.delOtherShipmentBill(osid);
			asbd.delOtherShipmentBillDetailBySbID(osid);
			apposi.delOtherShipmentBillIdcodeByTiid(osid);
			WarehouseBitDafService wbds = new WarehouseBitDafService("other_shipment_bill_idcode","osid",sb.getWarehouseid());
			wbds.del(sb.getId());

			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);
			DBUserLog.addUserLog(users.getUserid(), 7, "库存盘点>>删除盘亏单,编号:id", sb);

			return mapping.findForward("del");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

}
