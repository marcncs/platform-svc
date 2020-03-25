package com.winsafe.drp.action.finance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIncomeLog;
import com.winsafe.drp.dao.AppIncomeLogDetail;
import com.winsafe.drp.dao.AppIntegralI;
import com.winsafe.drp.dao.AppIntegralO;
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.IncomeLog;
import com.winsafe.drp.dao.IncomeLogDetail;
import com.winsafe.drp.util.DBUserLog;

public class DelIncomeLogAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String ilid = request.getParameter("ILID");
			AppIncomeLog ail = new AppIncomeLog();
			AppIncomeLogDetail apid = new AppIncomeLogDetail();
			IncomeLog il = ail.getIncomeLogByID(ilid);

			if (il.getIsaudit() == 1) {
				String result = "databases.record.lock";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
//			AppCIntegralDeal acid = new AppCIntegralDeal();
//			AppOIntegralDeal aoid = new AppOIntegralDeal();
//			AppCIntegral aci = new AppCIntegral();
//			AppOIntegral aoi = new AppOIntegral();
			
			AppIntegralI aii = new AppIntegralI();
			AppIntegralO aio = new AppIntegralO();

			
			List<IncomeLogDetail> ildlist = apid.getIncomeLogDetail(ilid);
			AppReceivable apr = new AppReceivable();
			for (IncomeLogDetail ild : ildlist) {
				apr.backAlreadysumById(ild.getThisreceivablesum(), ild
								.getRid());
				
				Double integralrate=ild.getThisreceivablesum()/ild.getReceivablesum();
				if(String.valueOf(integralrate).equals("NaN")){
					integralrate =0d;
				}
				
				
				aii.dealAIncome(ild.getBillno(), -integralrate);	
				
				
				aio.dealAOut(ild.getBillno(), -integralrate);
				
				
//				List cidls = acid.getCIntegralDealByBillno(ild.getBillno());
//				for(int cd=0;cd<cidls.size();cd++){
//					CIntegral ci = new CIntegral();
//					CIntegralDeal o=(CIntegralDeal)cidls.get(cd);
//					ci.setOrganid(o.getOrganid());
//					ci.setCid(o.getCid());
//					ci.setIsort(o.getIsort());
//					ci.setCintegral(-(o.getDealintegral()*integralrate));
//					//aci.addCIntegralIsNoExist(ci);
//					aci.updCIntegralByIntegral(ci);
//					

//					acid.updCIntegralDealByID(o.getId(), ci.getCintegral());
//				}
//				
//				
//				List oidls = aoid.getOIntegralDealByBillno(ild.getBillno());
//				for(int od=0;od<oidls.size();od++){
//					OIntegral oi = new OIntegral();
//					OIntegralDeal obj = (OIntegralDeal)oidls.get(od);
//					oi.setPowerorganid("1");
//					oi.setEquiporganid(obj.getOid());
//					oi.setIsort(obj.getIsort());
//					oi.setOintegral(-(obj.getDealintegral()*integralrate));
//					//aoi.addOIntegralIsNoExist(oi);
//					aoi.updOIntegralByIntegral(oi);
//					
//
//					aoid.updOIntegralDealByID(obj.getId(), oi.getOintegral());
//				}
			}
			apid.delIncomeLogDetailByIlID(ilid);
			ail.delIncomeLog(ilid);

			request.setAttribute("result", "databases.del.success");

			DBUserLog.addUserLog(userid, 9,"收款>>删除收款记录,编号："+ilid,il);

			return mapping.findForward("del");
		} catch (Exception e) {

			e.printStackTrace();
		} 
		return null;
	}

}
