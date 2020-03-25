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
import com.winsafe.hbm.util.Internation;

public class ToUpdIncomeLogAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
//    String oid = request.getParameter("OID");
//    String orgid = request.getParameter("ORGID");
	  String id = request.getParameter("ID");

    super.initdata(request);try{
      AppIncomeLog ail = new AppIncomeLog();
      AppIncomeLogDetail apild = new AppIncomeLogDetail();

//      IncomeLog il = ail.getIncomeLogByIDOrgID(oid,orgid);
      IncomeLog il = ail.getIncomeLogByID(id);
      
      if ( il.getIsaudit() == 1 ){    	 
    	  String result = "databases.record.lock";
          request.setAttribute("result", result);
          return new ActionForward("/sys/lockrecordclose.jsp");
      }
      
      if ( il.getPaymentmode() != 3 ){
    	  String result = "databases.incomelog.yushou";
          request.setAttribute("result", result);
          return new ActionForward("/sys/lockrecordclose.jsp");
      }
      
      if ( apild.getIncomeLogDetail(il.getId()).size() > 0 ){
    	  String result = "databases.record.lock";
          request.setAttribute("result", result);
          return new ActionForward("/sys/lockrecordclose.jsp");
      }
      
      //AppReceivableObject aro=new AppReceivableObject();
      //ReceivableObject ro = aro.getReceivableObjectByIDOrgID(il.getRoid(),orgid);
      
      IncomeLogForm ilf = new IncomeLogForm();
      ilf.setId(il.getId());
      ilf.setRoid(il.getRoid());
      ilf.setDrawee(il.getDrawee());     
      ilf.setFundattach(il.getFundattach());
     
      ilf.setIncomesum(il.getIncomesum());
      ilf.setBillnum(il.getBillnum());    
      ilf.setRemark(il.getRemark());

      List idf = new ArrayList();
      List<IncomeLogDetail> ildlist = apild.getIncomeLogDetail(il.getId());
      for ( IncomeLogDetail ild : ildlist ){
    	  IncomeLogDetailForm ildf = new IncomeLogDetailForm();
    	  ildf.setId(ild.getId());
    	  ildf.setRid(ild.getRid());
    	  ildf.setReceivablesum(ild.getReceivablesum());
    	  ildf.setPaymentmode(ild.getPaymentmode());
    	  ildf.setPaymentmodename(Internation.getStringByKeyPosition("PaymentMode",
            request,
            ild.getPaymentmode(), "global.sys.SystemResource"));
    	  ildf.setBillno(ild.getBillno());
    	  ildf.setThisreceivablesum(ild.getThisreceivablesum());
    	  idf.add(ildf);
      }
      AppCashBank apcb = new AppCashBank();
      List cblist = apcb.getAllCashBank();
      request.setAttribute("cblist", cblist);
      request.setAttribute("ilf",ilf);
      request.setAttribute("idf",idf);
      return mapping.findForward("toupd");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
