package com.winsafe.drp.action.aftersale;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppSaleRepair;
import com.winsafe.drp.dao.AppSaleRepairDetail;
import com.winsafe.drp.dao.AppSaleRepairIdcode;
import com.winsafe.drp.dao.SaleRepair;
import com.winsafe.drp.dao.SaleRepairDetail;
import com.winsafe.drp.dao.SaleRepairIdcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;

public class BackTrackSaleRepairAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("id");
		try {
			AppSaleRepair apb = new AppSaleRepair();
			SaleRepair pb = apb.getSaleRepairByID(id);
			if (pb.getIsaudit() == 0) {
				request.setAttribute("result", "databases.record.nooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (pb.getIsblankout() == 1) {
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppProductStockpile appst = new AppProductStockpile();
			AppSaleRepairDetail appsrd = new AppSaleRepairDetail();
			List srdlist = appsrd.getSaleRepairDetailBySrid(id);
			SaleRepairDetail srd = null;
			
			for (int i = 0; i < srdlist.size(); i++) {
				srd = (SaleRepairDetail) srdlist.get(i);
//				double stock = appst
//						.getProductStockpileByProductIDWIDBatch(srd
//								.getProductid(), pb.getWarehouseoutid(), srd
//								.getBatch());
//				if (stock < srd.getQuantity()) {
//					request.setAttribute("result", "databases.makeshipment.nostockpile");
//					return new ActionForward("/sys/lockrecordclose.jsp");
//				}
			}


			
			for (int i = 0; i < srdlist.size(); i++) {
				srd = (SaleRepairDetail) srdlist.get(i);
//				appst.returninProductStockpile(srd.getProductid(), srd.getBatch(),
//						srd.getQuantity(), pb.getWarehouseoutid(), id,
//						"销售返修返还-出货");
			}
			
						
			AppIdcode appidcode = new AppIdcode();
			SaleRepairIdcode wi =null;
			AppSaleRepairIdcode appwi = new AppSaleRepairIdcode();
			List idcodelist = appwi.getSaleRepairIdcodeByStid(id);
			for ( int i=0; i<idcodelist.size(); i++){
				wi = (SaleRepairIdcode)idcodelist.get(i);	
//				appidcode.updIsUse(wi.getProductid(), wi.getIdcode(), 0);
			}

			UsersBean users = UserManager.getUser(request);
//			Long userid = users.getUserid();
//			apb.updIsBackTrack(id, userid,1);
			
			request.setAttribute("result", "databases.operator.success");

//			DBUserLog.addUserLog(userid, "返还销售返修");

			return mapping.findForward("backresult");

		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}
		return mapping.getInputForward();
	}
}
