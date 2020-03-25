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
import com.winsafe.drp.dao.IncomeLog;
import com.winsafe.drp.dao.IncomeLogForm;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class PrintListIncomeLogAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);

		try {
			String[] tablename = { "IncomeLog" };
			String whereSql = getWhereSql2(tablename); 

			String timeCondition = getTimeCondition("MakeDate");
			String blur = getKeyWordCondition("KeysContent");
			whereSql = whereSql + blur + timeCondition;
			whereSql = DbUtil.getWhereSql(whereSql); 
			AppIncomeLog ail = new AppIncomeLog();

			List slls = ail.getIncomeLog(whereSql);
			List<IncomeLogForm> arls = new ArrayList<IncomeLogForm>();
			AppCashBank apcb = new AppCashBank();
			for (int i = 0; i < slls.size(); i++) {
				IncomeLog il = (IncomeLog)slls.get(i);
				IncomeLogForm ilf = new IncomeLogForm();			
				ilf.setId(il.getId());
				ilf.setDrawee(il.getDrawee());
				ilf.setIncomesum(il.getIncomesum());	
				ilf.setPaymentmode(il.getPaymentmode());
				if(il.getFundattach()>0){
					ilf.setFundattachname(apcb.getCashBankById(il.getFundattach()).getCbname());
				}else{
					ilf.setFundattachname("");
				}
				ilf.setMakeorganid(il.getMakeorganid());
				ilf.setMakedate(DateUtil.formatDate(il.getMakedate()));
				arls.add(ilf);
			}

			request.setAttribute("arls", arls);
			
			
			DBUserLog.addUserLog(userid,9,"账务管理>>打印收款"); 
			return mapping.findForward("toprint");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
