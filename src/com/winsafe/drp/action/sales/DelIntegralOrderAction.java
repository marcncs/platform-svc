package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIntegralOrder;
import com.winsafe.drp.dao.AppIntegralOrderDetail;
import com.winsafe.drp.dao.AppObjIntegral;
import com.winsafe.drp.dao.IntegralOrder;
import com.winsafe.drp.util.DBUserLog;

public class DelIntegralOrderAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String soid = request.getParameter("SOID");
			AppIntegralOrder aso = new AppIntegralOrder();
			AppIntegralOrderDetail appsod = new AppIntegralOrderDetail();
			IntegralOrder so = aso.getIntegralOrderByID(soid);
			if (so.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.nodel");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			aso.delIntegralOrder(soid);
			appsod.delIntegralOrderByioid(soid);
			
//			 删除积分
			AppObjIntegral aoi = new AppObjIntegral();
			aoi.delIntegralIByBillNo(soid);
			aoi.delIntegralOByBillNo(soid);
			aoi.delIntegralDetailByBillNo(soid);
			
			request.setAttribute("result", "databases.del.success");

			DBUserLog.addUserLog(userid, 6,"删除积分换购单,编号："+soid,so);

			return mapping.findForward("del");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
