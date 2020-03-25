package com.winsafe.drp.action.finance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCashBank;
import com.winsafe.drp.dao.AppReckoning;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Reckoning;
import com.winsafe.drp.dao.ReckoningForm;

public class ToUpdReckoningAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String id = request.getParameter("ID");

    super.initdata(request);try{
      AppReckoning apa = new AppReckoning();
      AppUsers au = new AppUsers();
      Reckoning l = apa.getReckoningByID(id);
      
      if(l.getIsaudit()==1){
          String result = "databases.record.lock";
          request.setAttribute("result", result);
          return new ActionForward("/sys/lockrecordclose.jsp");
        }

      ReckoningForm lf = new ReckoningForm();
		lf.setId(id);
		lf.setUid(l.getUid());
		lf.setUidname(au.getUsersByid(l.getUid()).getRealname());
		lf.setLoandate(String.valueOf(l.getLoandate()));
		lf.setPurpose(l.getPurpose());
		lf.setLoansum(l.getLoansum());
		lf.setBacksum(l.getBacksum());
		lf.setFundattach(l.getFundattach());
		lf.setMemo(l.getMemo());

		AppCashBank acb = new AppCashBank();
		List cbs = acb.getAllCashBank();
		request.setAttribute("cbs", cbs);
      request.setAttribute("lf",lf);
      return mapping.findForward("toupd");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
