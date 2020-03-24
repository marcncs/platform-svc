package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppHarmShipmentBill;
import com.winsafe.drp.dao.AppHarmShipmentBillDetail;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.HarmShipmentBill;
import com.winsafe.drp.dao.HarmShipmentBillDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class CancelEndcaseHarmShipmentBillAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();

		super.initdata(request);try{
			String id = request.getParameter("id");
			AppHarmShipmentBill apb = new AppHarmShipmentBill();
			HarmShipmentBill pb = apb.getHarmShipmentBillByID(id);

			if (pb.getIsblankout() == 1) {
				String result = "databases.record.blankoutnooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			if (pb.getIsendcase() == 0) {
				String result = "databases.record.already";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppHarmShipmentBillDetail aspb = new AppHarmShipmentBillDetail();
			List<HarmShipmentBillDetail> spbls = aspb.getHarmShipmentBillDetailBySbID(id); 
			AppProductStockpile aps = new AppProductStockpile();
			AppFUnit apfu = new AppFUnit();
			for (HarmShipmentBillDetail pid : spbls) {
				

//				aps.inProductStockpile(pid.getProductid(), pid.getBatch(),
//						apfu.getQuantity(pid.getProductid(), pid.getUnitid().intValue(), pid.getQuantity()), 
//						pb.getWarehouseid(),id,"取消报损-入库");

//				double stock = aps.getProductStockpileByProductIDWIDBatch(pid
//						.getProductid(), sb.getWarehouseid(), pid.getBatch());
//				if (pid.getQuantity() - stock > 0) {
//					String result = "databases.makeshipment.nostockpile";
//					request.setAttribute("result", result);
//					return new ActionForward("/sys/lockrecord.jsp");
//				}
			}
//			
//			
//			AppFeeWasteBook apfwb = new AppFeeWasteBook();
//			apfwb.delFeeWasteBookByBillno(pb.getId());

//			 apb.updIsEndCase(id, userid, 0);
//			
//			request.setAttribute("result", "databases.audit.success");
//			DBUserLog.addUserLog(userid, "取消结案报损出库,编号：" + id);
			
			return mapping.findForward("noaudit");
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		return new ActionForward(mapping.getInput());
	}

}
