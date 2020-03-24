package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppObjIntegral;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppShipmentBill;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.ShipmentBill;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class BlankoutShipmentBillAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		super.initdata(request);try{
			AppShipmentBill apb = new AppShipmentBill();
			ShipmentBill pb = apb.getShipmentBillByID(id);

			String blankoutreason = request.getParameter("blankoutreason");

			UsersBean users = UserManager.getUser(request);
			int userid = users.getUserid();

			
			apb.blankout(pb.getBsort(), id, userid, blankoutreason);

			AppTakeTicket apptt = new AppTakeTicket();
			List<TakeTicket> ttlist = apptt.getTakeTicketByBillno(id);
			AppTakeTicketIdcode apidcode = new AppTakeTicketIdcode();
			for (TakeTicket tt : ttlist) {
				
				List<TakeTicketIdcode> idcodelist = apidcode
						.getTakeTicketIdcodeByttid(tt.getId(), 1);
				setIdcodeUse(idcodelist);
				
				List<TakeTicketIdcode> ttilist = apidcode
						.getTakeTicketIdcodeByttid(tt.getId());
				addProductStockpile(ttilist, tt.getWarehouseid());
			}

			
			AppObjIntegral aoi = new AppObjIntegral();
			aoi.delIntegralIByBillNo(id);
			aoi.delIntegralOByBillNo(id);
			aoi.delIntegralDetailByBillNo(id);

			request.setAttribute("result", "databases.del.success");

			DBUserLog.addUserLog(userid, 8, "作废送货清单,编号:" + id);
			return mapping.findForward("result");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}

	
	private void addProductStockpile(List<TakeTicketIdcode> idlist,
			String warehouseid) throws Exception {
		AppProductStockpile aps = new AppProductStockpile();
		for (TakeTicketIdcode idcode : idlist) {
			
			aps.inProductStockpile(idcode.getProductid(), idcode.getUnitid(),
					idcode.getBatch(), idcode.getQuantity(), warehouseid,
					idcode.getWarehousebit(), idcode.getTtid(), "作废送货清单-入库");
		}
	}

	
	private void setIdcodeUse(List<TakeTicketIdcode> idlist) throws Exception {
		AppIdcode appidcode = new AppIdcode();
		for (TakeTicketIdcode idcode : idlist) {
			appidcode.updIsUse(idcode.getIdcode(), 1);
		}
	}
}
