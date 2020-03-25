package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIntegralOrder;
import com.winsafe.drp.dao.AppTakeService;
import com.winsafe.drp.dao.IntegralOrder;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.util.DBUserLog;

public class CancelAuditIntegralOrderAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		int userid = users.getUserid();

		try {
			String soid = request.getParameter("SOID");
			AppIntegralOrder aso = new AppIntegralOrder();
			IntegralOrder so = aso.getIntegralOrderByID(soid);

			if (so.getIsaudit() == 0) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			if (so.getIsblankout() == 1) {
				request.setAttribute("result", "databases.record.blankoutnooperator");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			AppTakeService appts = new AppTakeService();
			List<TakeTicket> ticketlist = appts.getTakeTicketByBillno(soid);
			
			if (appts.isAuditTakeTicket(ticketlist)) {
				request.setAttribute("result", "databases.record.already");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			// 删除检货
			appts.deleteTake(ticketlist);
			aso.updIsAudit(soid, userid, 0);
			
			
			//---------------------积分预处理表写入积分表-------------------------
			
//			AppCIntegralDeal acid = new AppCIntegralDeal();
//			AppCIntegral aci = new AppCIntegral();
//			CIntegral ci = new CIntegral();
//			CIntegralDeal o=(CIntegralDeal)acid.getCIntegralDealByBillNo(soid, 9);
//			ci.setOrganid(o.getOrganid());
//			ci.setCid(o.getCid());
//			ci.setIsort(o.getIsort());
//			ci.setCintegral(-o.getDealintegral());
//			ci.setIyear(DateUtil.getCurrentYear());
//			aci.updCIntegralByIntegral(ci);
//			
//			acid.updCIntegralDealByID(o.getId(), ci.getCintegral());
//			
//			
//			AppOIntegralDeal aoid = new AppOIntegralDeal();
//			AppOIntegral aoi = new AppOIntegral();
//			OIntegral oi = new OIntegral();
//			OIntegralDeal obj = (OIntegralDeal)aoid.getOIntegralDealByBillNo(soid, 9);
//			oi.setPowerorganid("1");
//			oi.setEquiporganid(obj.getOid());
//			oi.setIsort(obj.getIsort());
//			oi.setOintegral(-obj.getDealintegral());
//			oi.setIyear(DateUtil.getCurrentYear());
//			aoi.updOIntegralByIntegral(oi);			
//			
//			aoid.updOIntegralDealByID(obj.getId(), oi.getOintegral());
			//-------------------------------------------------------------------

			 

			request.setAttribute("result", "databases.cancel.success");
			DBUserLog.addUserLog(userid, 6,"积分换购>>取消复核积分换购单,编号:"+soid);

			return mapping.findForward("noaudit");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
