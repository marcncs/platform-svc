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
import com.winsafe.hbm.util.RequestTool;

public class TransPaymentLogAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			String poid = request.getParameter("poid");
			String payee = request.getParameter("payee");
			String paypurpose = request.getParameter("paypurpose");
			int paymode = RequestTool.getInt(request,"hpaymode");
			int fundsrc = RequestTool.getInt(request,"fundsrc");
			// Double paysum = Double.valueOf(request.getParameter("paysum"));
			
			String billnum = request.getParameter("billnum");
			String voucher = request.getParameter("voucher");
			String remark = request.getParameter("remark");

			PaymentLog pl = new PaymentLog();
			String plid = MakeCode.getExcIDByRandomTableName("payment_log", 2,
					"PL");
			pl.setId(plid);
			pl.setPoid(poid);
			pl.setPayee(payee);
			pl.setPaypurpose(paypurpose);
			pl.setFundsrc(fundsrc);
			pl.setPaymode(paymode);
			pl.setBillnum(billnum);
			pl.setVoucher(voucher);
			pl.setRemark(remark);
			pl.setMakeorganid(users.getMakeorganid());
			pl.setMakedeptid(users.getMakedeptid());
			pl.setMakeid(userid);
			pl.setIsaudit(0);
			pl.setIspay(0);
			pl.setMakedate(DateUtil.getCurrentDate());
			
			StringBuffer keyscontent = new StringBuffer();
			keyscontent.append(pl.getId()).append(",").append(poid).append(",")
			.append(pl.getPayee()).append(",");

			
			// AppPayableObject apo = new AppPayableObject();
			// String aupd= apo.paymentLog(paysum,poid);

			String[] pid = request.getParameterValues("pid");
			String[] strpayablesum = request.getParameterValues("payablesum");
			String[] strpaymode = request.getParameterValues("paymentmode");
			String[] billno = request.getParameterValues("billno");
			String[] strthispayablesum = request
					.getParameterValues("thispayablesum");
			AppPayable apr = new AppPayable();
			double paysum = 0.00;
			AppPaymentLogDetail apild = new AppPaymentLogDetail();
			for (int i = 0; i < pid.length; i++) {
				PaymentLogDetail ild = new PaymentLogDetail();
				ild.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"payment_log_detail", 0, "")));
				ild.setPlid(pl.getId());
				ild.setPid(pid[i]);
				ild
						.setPayablesum(DataValidate.IsDouble(strpayablesum[i]) ? Double
								.valueOf(strpayablesum[i])
								: 0d);
				ild.setPaymode(Integer.valueOf(strpaymode[i]));
				ild.setBillno(billno[i]);
				ild.setThispayablesum(DataValidate
						.IsDouble(strthispayablesum[i]) ? Double
						.valueOf(strthispayablesum[i]) : 0d);

				paysum += ild.getThispayablesum();
				apild.addPaymentLogDetail(ild);
				keyscontent.append(ild.getBillno()).append(",");

				apr.updAlreadysumById(ild.getThispayablesum(), ild.getPid());
				apr.updClose(ild.getPid());
			}
			pl.setPaysum(paysum);
			pl.setAlreadyspend(0d);
			pl.setKeyscontent(keyscontent.toString());
			AppPaymentLog apl = new AppPaymentLog();
			apl.addPaymentLog(pl);

			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid, 9,"付款管理>>新增付款记录,编号："+pl.getId());
			return mapping.findForward("addresult");
		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
