package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBankAdjust;
import com.winsafe.drp.dao.CashBankAdjust;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddCashBankAdjustAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
	  super.initdata(request);
    try{
    	
    	int cbid = RequestTool.getInt(request,"cbid");
    	Double adjustsum = Double.valueOf(request.getParameter("adjustsum"));

    	CashBankAdjust cba = new CashBankAdjust();
    	cba.setId(MakeCode.getExcIDByRandomTableName("cash_bank_adjust",2,"CA"));
    	cba.setCbid(cbid);
    	cba.setAdjustsum(adjustsum);
    	cba.setMemo(request.getParameter("memo"));
    	cba.setIsaudit(0);
    	cba.setAuditid(0);
    	cba.setMakeorganid(users.getMakeorganid());
    	cba.setMakedeptid(users.getMakedeptid());
    	cba.setMakeid(userid);
    	cba.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
    	
      AppCashBankAdjust aro = new AppCashBankAdjust();
      aro.addCashBankAdjust(cba);

      request.setAttribute("result", "databases.add.success");

      DBUserLog.addUserLog(userid,9,"现金银行>>新增现金银行调整单,编号："+cbid); 

      return mapping.findForward("addresult");
    }catch(Exception e){
    	request.setAttribute("result", "databases.add.fail");
      e.printStackTrace();
    }finally {

    }

    return mapping.findForward("addresult");
  }
}
