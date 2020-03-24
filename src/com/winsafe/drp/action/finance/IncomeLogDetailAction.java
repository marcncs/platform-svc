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
import com.winsafe.drp.dao.AppIncomeLog;
import com.winsafe.drp.dao.AppIncomeLogDetail;
import com.winsafe.drp.dao.IncomeLog;
import com.winsafe.drp.dao.IncomeLogDetail;
import com.winsafe.drp.dao.IncomeLogDetailForm;
import com.winsafe.drp.dao.IncomeLogForm;
import com.winsafe.drp.server.PaymentModeService;
import com.winsafe.drp.server.UsersService;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class IncomeLogDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ID");
		super.initdata(request);
		try {
			AppIncomeLog ail = new AppIncomeLog();
			AppCashBank apcb = new AppCashBank();
			UsersService au = new UsersService();
			PaymentModeService mps = new PaymentModeService();
			
			IncomeLog il = ail.getIncomeLogByID(id);
			IncomeLogForm ilf = new IncomeLogForm();
			ilf.setId(il.getId());
			ilf.setRoid(il.getRoid());
			ilf.setDrawee(il.getDrawee());
			ilf.setFundattachname(apcb.getCBName(il.getFundattach()));
			ilf.setPaymentmodename(mps.getPaymentModeName(il.getPaymentmode()));
			ilf.setIncomesum(il.getIncomesum());
			ilf.setAlreadyspend(il.getAlreadyspend());
			ilf.setBillnum(il.getBillnum());	
			ilf.setVoucher(il.getVoucher());
			ilf.setRemark(il.getRemark());
			ilf.setMakeorganid(il.getMakeorganid());
			ilf.setMakedeptid(il.getMakedeptid());
			ilf.setMakeidname(au.getUsersByid(il.getMakeid()).getRealname());
			ilf.setMakedate(DateUtil.formatDate(il.getMakedate()));
			ilf.setIsaudit(il.getIsaudit());
			ilf.setIsauditname(Internation.getStringByKeyPosition(
						"YesOrNo", request, il.getIsaudit(), "global.sys.SystemResource"));
			if ( il.getAuditid() !=null && il.getAuditid() > 0 ){
				ilf.setAuditidname(au.getUsersName(il.getAuditid()));
			}
			ilf.setAuditdate(DateUtil.formatDate(il.getAuditdate()));
			ilf.setIsreceive(il.getIsreceive());
			ilf.setIsreceivename(Internation.getStringByKeyPosition(
						"YesOrNo", request, il.getIsreceive(), "global.sys.SystemResource"));
			if ( il.getReceiveid() !=null && il.getReceiveid() > 0 ){
				ilf.setReceiveidname(au.getUsersByid(il.getReceiveid()).getRealname());
			}
			ilf.setReceivedate(DateUtil.formatDate(il.getReceivedate()));
			
			AppIncomeLogDetail aila = new AppIncomeLogDetail();
			List<IncomeLogDetail> aprv = aila.getIncomeLogDetail(id);
			ArrayList rvls = new ArrayList();
			for (IncomeLogDetail ild : aprv ) {
				IncomeLogDetailForm wdaf = new IncomeLogDetailForm();
				wdaf.setId(ild.getId());
				wdaf.setRid(ild.getRid());
				wdaf.setPaymentmodename(mps.getPaymentModeName(ild.getPaymentmode()));
				wdaf.setReceivablesum(ild.getReceivablesum());
				wdaf.setBillno(ild.getBillno());
				wdaf.setThisreceivablesum(ild.getThisreceivablesum());
				rvls.add(wdaf);
			}



			request.setAttribute("rvls", rvls);
			request.setAttribute("ilf", ilf);

			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
