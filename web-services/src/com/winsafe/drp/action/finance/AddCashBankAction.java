package com.winsafe.drp.action.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.CashBank;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddCashBankAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
	  super.initdata(request);
    try{
    	
    	String cbname = request.getParameter("cbname");
    	Double totalsum = Double.valueOf(request.getParameter("totalsum"));

    	CashBank cb = new CashBank();
    	Integer id=Integer.valueOf(MakeCode.getExcIDByRandomTableName("cash_bank",0,""));
    	cb.setId(id);
    	cb.setCbname(cbname);
    	cb.setTotalsum(totalsum);
    	cb.setMakeorganid(users.getMakeorganid());
    	cb.setMakedeptid(users.getMakedeptid());
    	cb.setMakeid(userid);
    	cb.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
    	
      AppCashBank aro = new AppCashBank();
      aro.addCashBank(cb);
     
      request.setAttribute("result", "databases.add.success");

      DBUserLog.addUserLog(userid,9,"新增现金银行,编号："+id); 
      return mapping.findForward("addresult");
    }catch(Exception e){
      String result = "databases.add.fail";
      request.setAttribute("result", result);
      e.printStackTrace();
    }finally {
    }

    return mapping.findForward("addresult");
  }
}
