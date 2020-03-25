package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPayable;
import com.winsafe.drp.dao.AppPaymentLog;
import com.winsafe.drp.dao.AppPaymentLogDetail;
import com.winsafe.drp.dao.PaymentLog;
import com.winsafe.drp.dao.PaymentLogDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class DestoryPaymentLogAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		String id = request.getParameter("ilid");
		try {
			AppPaymentLog ail = new AppPaymentLog();
			PaymentLog il = ail.getPaymentLogByID(id);

			String[] rid = request.getParameterValues("rid");
			String[] strreceivablesum = request
					.getParameterValues("receivablesum");
			String[] strpaymode = request.getParameterValues("paymode");
			String[] billno = request.getParameterValues("billno");
			String[] strthisreceivablesum = request
					.getParameterValues("thisreceivablesum");

			double incomesum = 0.00;
			for ( int i=0; i < strthisreceivablesum.length; i ++){
				incomesum += DataValidate.IsDouble(strthisreceivablesum[i]) ? Double.valueOf(strthisreceivablesum[i]) : 0d;
			}
			if ( (il.getPaysum()-il.getAlreadyspend()) < incomesum ){
				 String result = "databases.record.nodestorypaymentlog";
	             request.setAttribute("result", result);
	             return new ActionForward("/sys/lockrecordclose.jsp");
			}
			AppPayable apr = new AppPayable();
			AppPaymentLogDetail apild = new AppPaymentLogDetail();
			for (int i = 0; i < rid.length; i++) {
				PaymentLogDetail ild = new PaymentLogDetail();
				ild.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"payment_log_detail", 0, "")));
				ild.setPlid(il.getId());
				ild.setPid(rid[i]);
				ild.setPayablesum(DataValidate
								.IsDouble(strreceivablesum[i]) ? Double
								.valueOf(strreceivablesum[i]) : 0d);
				ild.setPaymode(Integer.valueOf(strpaymode[i]));
				ild.setBillno(billno[i]);
				ild.setThispayablesum(DataValidate
						.IsDouble(strthisreceivablesum[i]) ? Double
						.valueOf(strthisreceivablesum[i]) : 0d);				
				apild.addPaymentLogDetail(ild);
				
				apr.updAlreadysumById(ild.getThispayablesum(), ild.getPid());
				apr.updClose(ild.getPid());
			}
			il.setAlreadyspend(il.getAlreadyspend()+incomesum);
			il.setSpenddate(DateUtil.getCurrentDate());
			ail.updPaymentLog(il);

			request.setAttribute("result", "databases.add.success");

			DBUserLog.addUserLog(userid, 9,"付款管理>>核销付款记录,编号："+id);
			return mapping.findForward("addresult");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
