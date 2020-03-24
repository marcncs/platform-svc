package com.winsafe.drp.action.finance;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIncomeLog;
import com.winsafe.drp.dao.AppOutlay;
import com.winsafe.drp.dao.AppPaymentLog;
import com.winsafe.drp.dao.AppPurchaseBill;
import com.winsafe.drp.dao.AppSaleOrder;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
public class TotalIncomePaymentAction extends BaseAction{

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    super.initdata(request);try{
      double totalincome =Double.parseDouble("0.00");
      double alreadyincome = Double.parseDouble("0.00");
      double balanceincome = Double.parseDouble("0.00");
      double totalpayment = Double.parseDouble("0.00");
      double alreadypayment = Double.parseDouble("0.00");
      double balancepayment = Double.parseDouble("0.00");
      double totalsalary = Double.parseDouble("0.00");
      double totaloutlay = Double.parseDouble("0.00");
      double grossprofit = Double.parseDouble("0.00");

      //String Condition = " sl.approvestatus =1 ";
      Map map = new HashMap(request.getParameterMap());
      Map tmpMap = EntityManager.scatterMap(map);
      //String sql = "select * from sale_log as sl "+Condition;

      //String whereSql = EntityManager.getTmpWhereSql(map, sql);
      String whereSql = " WHERE ";

      String salelogtimeCondition = DbUtil.getTimeCondition(map, tmpMap," CreateDate");
      String salelogwhereSql = whereSql + salelogtimeCondition ;
      String incomelogCondition = DbUtil.getTimeCondition(map, tmpMap," IncomeDate");
      String incomelogwhereSql = whereSql + incomelogCondition ;
      String purchasebillCondition = DbUtil.getTimeCondition(map, tmpMap," ReceiveDate");
      String purchasebillwhereSql = whereSql + purchasebillCondition ;
      String paymentlogCondition = DbUtil.getTimeCondition(map, tmpMap," BillDate");
      String paymentlogwhereSql = whereSql + paymentlogCondition ;
      String outlayCondition = DbUtil.getTimeCondition(map, tmpMap," ApplyDate");
      String outlaywhereSql = whereSql + outlayCondition ;

      AppSaleOrder asl = new AppSaleOrder();
      totalincome = asl.getTotalIncome(salelogwhereSql);
      AppIncomeLog ail = new AppIncomeLog();
      alreadyincome = ail.getAlreadyIncome(incomelogwhereSql);
      balanceincome = totalincome - alreadyincome;
      AppPurchaseBill apb = new AppPurchaseBill();
      totalpayment = apb.getTotalPayment(purchasebillwhereSql);
      AppPaymentLog apl = new AppPaymentLog();
      alreadypayment = apl.getAlreadyPayment(paymentlogwhereSql);
      balancepayment = totalpayment - alreadypayment;
      AppOutlay ao = new AppOutlay();
      totaloutlay = ao.getTotalOutlay(outlaywhereSql);

      request.setAttribute("totalincome",String.valueOf(totalincome));
      request.setAttribute("alreadyincome",String.valueOf(alreadyincome));
      request.setAttribute("balanceincome",String.valueOf(balanceincome));
      request.setAttribute("totalpayment",String.valueOf(totalpayment));
      request.setAttribute("alreadypayment",String.valueOf(alreadypayment));
      request.setAttribute("balancepayment",String.valueOf(balancepayment));
      request.setAttribute("totaloutlay",String.valueOf(totaloutlay));

      return mapping.findForward("total");
    }catch(Exception e){
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
