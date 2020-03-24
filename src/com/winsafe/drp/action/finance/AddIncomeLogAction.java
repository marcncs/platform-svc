package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIncomeLog;
import com.winsafe.drp.dao.IncomeLog;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddIncomeLogAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String roid = request.getParameter("roid");
	
		super.initdata(request);
		try {
			IncomeLog il = new IncomeLog();
			il.setId(MakeCode.getExcIDByRandomTableName("income_log",2,"IL"));
			il.setRoid(roid);
			il.setDrawee(request.getParameter("drawee"));
			il.setFundattach(RequestTool.getInt(request,"fundattach"));
			il.setPaymentmode(3);
			String strincomesum = request.getParameter("incomesum");
			il.setIncomesum(DataValidate.IsDouble(strincomesum) ? Double
					.valueOf(strincomesum)
					: 0d);
			il.setAlreadyspend(0d);
			il.setBillnum(request.getParameter("billnum"));			
			il.setRemark(request.getParameter("remark"));
			il.setMakeorganid(request.getParameter("orgid"));
			il.setMakedeptid(users.getMakedeptid());
			il.setMakeid(userid);
			il.setMakedate(DateUtil.getCurrentDate());
			il.setIsaudit(0);
			il.setIsreceive(0);			
			
			AppIncomeLog ail = new AppIncomeLog();
			ail.addIncomeLog(il);

			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid,9,"收款管理>>新增收款记录,编号:"+il.getId()); 
			return mapping.findForward("addresult");
		} catch (Exception e) {
			request.setAttribute("result", "databases.add.fail");
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
