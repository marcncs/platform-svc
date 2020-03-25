package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCIntegral;
import com.winsafe.drp.dao.AppCIntegralDeal;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppIdcodeDetail;
import com.winsafe.drp.dao.AppIncomeLog;
import com.winsafe.drp.dao.AppIncomeLogDetail;
import com.winsafe.drp.dao.AppOIntegral;
import com.winsafe.drp.dao.AppOIntegralDeal;
import com.winsafe.drp.dao.AppPeddleOrder;
import com.winsafe.drp.dao.AppPeddleOrderDetail;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.AppReceivableDetail;
import com.winsafe.drp.dao.CIntegral;
import com.winsafe.drp.dao.CIntegralDeal;
import com.winsafe.drp.dao.IdcodeDetail;
import com.winsafe.drp.dao.OIntegral;
import com.winsafe.drp.dao.OIntegralDeal;
import com.winsafe.drp.dao.PeddleOrder;
import com.winsafe.drp.dao.PeddleOrderDetail;
import com.winsafe.hbm.util.DateUtil;

public class BlankoutPeddleOrderAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("id");
		try {
			AppPeddleOrder apb = new AppPeddleOrder();
			PeddleOrder pb = apb.getPeddleOrderByID(id);
			if (pb.getIsdaybalance() == 1) {
				request.setAttribute("result", "databases.record.alreadysettlement");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			AppIncomeLog ail = new AppIncomeLog();
//			IncomeLog il = ail.getIncomeLogByBillnum(id);
//			if ( il != null && il.getIsaudit() == 1 ){
//				request.setAttribute("result", "databases.record.alreadysettlement");
//				return new ActionForward("/sys/lockrecordclose.jsp");
//			}
			
			AppPeddleOrderDetail appsod = new AppPeddleOrderDetail();
			AppProductStockpile aps = new AppProductStockpile();
			List pils = appsod.getPeddleOrderDetailObjectByPOID(id);
			for (int i = 0; i < pils.size(); i++) {
				PeddleOrderDetail pid = (PeddleOrderDetail) pils.get(i);
				
//				aps.inProductStockpile(pid.getProductid(), pid.getBatch(), pid.getQuantity(), pid.getWarehouseid(),id,"作废零售单-入货");
			}
			
			AppReceivable appr = new AppReceivable();
			AppReceivableDetail ard = new AppReceivableDetail();
			
			appr.delReceivableByROIDBILLNO(pb.getCid(), pb.getId());
			//ard.delReceivableDetailByBillNo(id);
			//ard.delReceivableDetailByRid();
			
			AppIncomeLogDetail appild = new AppIncomeLogDetail();
			ail.delIncomeByROIDBILLNO(pb.getCid(), pb.getId());
			appild.delIncomeLogDetailByBillNo(id);
			
			
			AppCIntegralDeal apci = new AppCIntegralDeal();
			AppOIntegralDeal apoi = new AppOIntegralDeal();
			AppCIntegral aci = new AppCIntegral();
			AppOIntegral aoi = new AppOIntegral();
			
			List cidls = apci.getCIntegralDealByBillno(pb.getId());
			for(int cd=0;cd<cidls.size();cd++){
				CIntegral ci = new CIntegral();
				CIntegralDeal o=(CIntegralDeal)cidls.get(cd);
				ci.setOrganid(o.getOrganid());
				ci.setCid(o.getCid());
				ci.setIsort(o.getIsort());
				ci.setCintegral(-o.getDealintegral());
				ci.setIyear(DateUtil.getCurrentYear());
				aci.addCIntegralIsNoExist(ci);
				aci.updCIntegralByIntegral(ci);
			}			
			
			List oidls = apoi.getOIntegralDealByBillno(pb.getId());
			for(int od=0;od<oidls.size();od++){
				OIntegral oi = new OIntegral();
				OIntegralDeal obj = (OIntegralDeal)oidls.get(od);
				oi.setPowerorganid("1");
				oi.setEquiporganid(obj.getOid());
				oi.setIsort(obj.getIsort());
				oi.setOintegral(-obj.getDealintegral());
				oi.setIyear(DateUtil.getCurrentYear());
				aoi.addOIntegralIsNoExist(oi);
				aoi.updOIntegralByIntegral(oi);
	
			}
			
			apci.delCIntegralDeal(pb.getId());			
			
			apoi.delOIntegralDeal(pb.getId());	
			
			
			AppIdcodeDetail apid = new AppIdcodeDetail();
			AppIdcode appidcode = new AppIdcode();
			List<IdcodeDetail> idlist = apid.getIdcodeDetailByBillid(pb.getId());
			for ( IdcodeDetail idetail : idlist ){
//				appidcode.updIsUse(idetail.getProductid(), idetail.getIdcode(), 1);
			}
			apid.delIdcodeDetailByBillid(pb.getId());
			
			
			
//			Long userid = users.getUserid();
//			apb.BlankoutPeddleOrder(id, userid);

			request.setAttribute("result", "databases.del.success");

//			DBUserLog.addUserLog(userid, "作废零售单");
			return mapping.findForward("delresult");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}
}
