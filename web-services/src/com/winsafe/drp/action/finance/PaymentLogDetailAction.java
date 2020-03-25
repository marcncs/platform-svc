package com.winsafe.drp.action.finance;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppPaymentLog;
import com.winsafe.drp.dao.AppPaymentLogDetail;
import com.winsafe.drp.dao.PaymentLog;
import com.winsafe.drp.dao.PaymentLogDetail;
import com.winsafe.drp.dao.PaymentLogDetailForm;
import com.winsafe.drp.dao.PaymentLogForm;
import com.winsafe.drp.server.PaymentModeService;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;

public class PaymentLogDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);
		try {
			AppPaymentLog apl = new AppPaymentLog();
			AppCashBank apcb = new AppCashBank();
			PaymentModeService pms = new PaymentModeService();

			PaymentLog pl = apl.getPaymentLogByID(id);
			PaymentLogForm plf = new PaymentLogForm();
			plf.setId(pl.getId());
			plf.setPoid(pl.getPoid());
			plf.setPayee(pl.getPayee());
			plf.setPaypurpose(pl.getPaypurpose());
			plf.setPaymodename(pms.getPaymentModeName(pl.getPaymode()));					
			plf.setFundsrcname(apcb.getCBName(pl.getFundsrc()));
			plf.setPaysum(pl.getPaysum());
			plf.setAlreadyspend(pl.getAlreadyspend());
			plf.setBillnum(pl.getBillnum());
			plf.setVoucher(pl.getVoucher());
			plf.setRemark(pl.getRemark());
			plf.setMakeorganid(pl.getMakeorganid());
			plf.setMakedeptid(pl.getMakedeptid());
			plf.setMakeid(pl.getMakeid());
//			plf.setMakeidname(au.getUsersByid(pl.getMakeid()).getRealname());
			plf.setMakedate(pl.getMakedate().toString());
			plf.setIsaudit(pl.getIsaudit());
			plf.setAuditid(pl.getAuditid());
//			plf.setIsauditname(Internation.getStringByKeyPosition(
//						"YesOrNo", request, pl.getIsaudit(), "global.sys.SystemResource"));
//			if ( pl.getAuditid() !=null && pl.getAuditid() > 0 ){
//				plf.setAuditidname(au.getUsersByid(pl.getAuditid()).getRealname());
//			}
			plf.setAuditdate(DateUtil.formatDate(pl.getAuditdate()));
			plf.setIspay(pl.getIspay());
			plf.setPayid(pl.getPayid());
//			plf.setIspayname(Internation.getStringByKeyPosition(
//						"YesOrNo", request, pl.getIspay(), "global.sys.SystemResource"));
//			if ( pl.getPayid() !=null && pl.getPayid() > 0 ){
//				plf.setPayidname(au.getUsersByid(pl.getPayid()).getRealname());
//			}
			plf.setPaydate(DateUtil.formatDate(pl.getPaydate()));
			
			AppPaymentLogDetail aila = new AppPaymentLogDetail();
			List<PaymentLogDetail> aprv = aila.getPaymentLogDetailByPLID(id);
			ArrayList rvls = new ArrayList();
			for (PaymentLogDetail ild : aprv ) {
				PaymentLogDetailForm wdaf = new PaymentLogDetailForm();
				wdaf.setId(ild.getId());
				wdaf.setPid(ild.getPid());
				wdaf.setPaymodename(pms.getPaymentModeName(ild.getPaymode()));					
				wdaf.setPayablesum(ild.getPayablesum());
				wdaf.setBillno(ild.getBillno());
				wdaf.setThispayablesum(ild.getThispayablesum());
				rvls.add(wdaf);
			}


			request.setAttribute("plf", plf);
			request.setAttribute("rvls", rvls);

			DBUserLog.addUserLog(userid,9, "付款管理>>付款记录详情,编号："+id); 
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
