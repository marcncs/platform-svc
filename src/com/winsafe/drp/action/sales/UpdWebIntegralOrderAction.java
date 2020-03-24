package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIntegralOrder;
import com.winsafe.drp.dao.AppIntegralOrderDetail;
import com.winsafe.drp.dao.IntegralOrder;

public class UpdWebIntegralOrderAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
//		Long userid = users.getUserid();

		try {
			String soid = request.getParameter("SOID");
			AppIntegralOrder aso = new AppIntegralOrder();
			IntegralOrder so = aso.getIntegralOrderByID(soid);	

			AppIntegralOrderDetail appsod = new AppIntegralOrderDetail();
			
			
			String[] wdid = request.getParameterValues("wdid");
			String[] wid = request.getParameterValues("warehouseid");
			for ( int i=0; i<wdid.length; i ++ ){
				if ( wid[i].equals("") ){
					request.setAttribute("result", "databases.approve.fail");
					return new ActionForward("/sys/lockrecordclose.jsp");
				}
			}
			so.setEquiporganid(request.getParameter("equiporganid"));
			aso.updIntegralOrder(so);
			for ( int i=0; i<wdid.length; i ++ ){
				appsod.updWarehourseId(Long.valueOf(wdid[i]), Long.valueOf(wid[i]));
			}


			request.setAttribute("result", "databases.upd.success");
//			DBUserLog.addUserLog(userid, "修改积分换购单");

			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
