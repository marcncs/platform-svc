package com.winsafe.drp.action.finance;

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
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class DestoryIncomeLogAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		String id = request.getParameter("ilid");
		try {
			AppIncomeLog ail = new AppIncomeLog();
			IncomeLog il = ail.getIncomeLogByID(id);

			String[] rid = request.getParameterValues("rid");
			String[] strreceivablesum = request
					.getParameterValues("receivablesum");
			String[] strpaymentmode = request.getParameterValues("paymentmode");
			String[] billno = request.getParameterValues("billno");
			String[] strthisreceivablesum = request
					.getParameterValues("thisreceivablesum");
			double incomesum = 0.00;
			for ( int i=0; i < strthisreceivablesum.length; i ++){
				incomesum += DataValidate.IsDouble(strthisreceivablesum[i]) ? Double.valueOf(strthisreceivablesum[i]) : 0d;
			}
			if ( (il.getIncomesum()-il.getAlreadyspend()) < incomesum ){
				 String result = "databases.record.nodestoryincomelog";
	             request.setAttribute("result", result);
	             return new ActionForward("/sys/lockrecordclose.jsp");
			}

//			AppCIntegralDeal acid = new AppCIntegralDeal();
//			AppOIntegralDeal aoid = new AppOIntegralDeal();
//			AppCIntegral aci = new AppCIntegral();
//			AppOIntegral aoi = new AppOIntegral();
			AppReceivable apr = new AppReceivable();
			
			AppIntegralI aii = new AppIntegralI();
			AppIntegralO aio = new AppIntegralO();

			AppIncomeLogDetail apild = new AppIncomeLogDetail();
			for (int i = 0; i < rid.length; i++) {
				IncomeLogDetail ild = new IncomeLogDetail();
				ild.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"income_log_detail", 0, "")));
				ild.setIlid(il.getId());
				ild.setRid(rid[i]);
				ild
						.setReceivablesum(DataValidate
								.IsDouble(strreceivablesum[i]) ? Double
								.valueOf(strreceivablesum[i]) : 0d);
				ild.setPaymentmode(Integer.valueOf(strpaymentmode[i]));
				ild.setBillno(billno[i]);
				ild.setThisreceivablesum(DataValidate
						.IsDouble(strthisreceivablesum[i]) ? Double
						.valueOf(strthisreceivablesum[i]) : 0d);				
				apild.addIncomeLogDetail(ild);
				
				apr.updAlreadysumById(ild.getThisreceivablesum(), ild.getRid());
				apr.updClose(ild.getRid());
				

				Double integralrate=ild.getThisreceivablesum()/ild.getReceivablesum();
				if(String.valueOf(integralrate).equals("NaN")){
					integralrate =0d;
				}
				
				
				aii.dealAIncome(billno[i], integralrate);				
				
				aio.dealAOut(billno[i], integralrate);
				
//
//				List cidls = acid.getCIntegralDealByBillno(billno[i]);
//				for(int cd=0;cd<cidls.size();cd++){
//					CIntegral ci = new CIntegral();
//					CIntegralDeal o=(CIntegralDeal)cidls.get(cd);
//					ci.setOrganid(o.getOrganid());
//					ci.setCid(o.getCid());
//					ci.setIsort(o.getIsort());
//					ci.setCintegral(o.getDealintegral()*integralrate);
//					aci.addCIntegralIsNoExist(ci);
//					aci.updCIntegralByIntegral(ci);
//					
//					
//					acid.updCIntegralDealByID(o.getId(), ci.getCintegral());
//					
//				}
//				
//
//				List oidls = aoid.getOIntegralDealByBillno(billno[i]);
//				for(int od=0;od<oidls.size();od++){
//					OIntegral oi = new OIntegral();
//					OIntegralDeal obj = (OIntegralDeal)oidls.get(od);
//					oi.setPowerorganid("1");
//					oi.setEquiporganid(obj.getOid());
//					oi.setIsort(obj.getIsort());
//					oi.setOintegral(obj.getDealintegral()*integralrate);
//					aoi.addOIntegralIsNoExist(oi);
//					aoi.updOIntegralByIntegral(oi);
//					
//
//					aoid.updOIntegralDealByID(obj.getId(), oi.getOintegral());
//				}
				
			}			
			il.setAlreadyspend(il.getAlreadyspend()+incomesum);
			il.setSpenddate(DateUtil.getCurrentDate());
			ail.updIncomeLog(il);

			request.setAttribute("result", "databases.add.success");

			DBUserLog.addUserLog(userid, 9,"收款管理>>新增收款记录,编号："+id);
			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return new ActionForward(mapping.getInput());
	}
}
