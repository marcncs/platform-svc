package com.winsafe.drp.action.finance;
//package com.winsafe.drp.action.finance;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.struts.action.Action;
//import org.apache.struts.action.ActionForm;
//import org.apache.struts.action.ActionForward;
//import org.apache.struts.action.ActionMapping;
//
//import com.winsafe.drp.dao.AppReceivableObject;
//import com.winsafe.drp.dao.ReceivableObject;
//import com.winsafe.drp.dao.RecevablePayableService;
//import com.winsafe.drp.dao.UserManager;
//import com.winsafe.drp.dao.UsersBean;
//import com.winsafe.drp.util.DbUtil;
//
//public class ListReceivableDetailAction extends BaseAction {
//
//	public ActionForward execute(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		
//		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
//		super.initdata(request);super.initdata(request);try{
//			String rid = request.getParameter("rid");
//			if ( null == rid ){
//				rid = (String)request.getSession().getAttribute("rid");
//				request.getSession().setAttribute("rid", rid);
//			}			
//			String begindate = request.getSession().getAttribute("BeginDate").toString();			
//			String enddate = request.getSession().getAttribute("EndDate").toString();
//		
//			AppReceivableObject aro = new AppReceivableObject();
//			
//			ReceivableObject ro = aro.getReceivableObjectByID(rid);
//			
//			RecevablePayableService aprd = new RecevablePayableService(rid, begindate, enddate);
//			Map<String, Double> detail = new HashMap<String, Double>();
//			
//			double cashprevious = aprd.getPreviousSum(RecevablePayableService.CASH);
//			double cashcurrent = aprd.getCurrentSum(RecevablePayableService.CASH);
//			double cashcurrentalready = aprd.getCurrentAlreadySum(RecevablePayableService.CASH);
//			double cashend = cashprevious +cashcurrent-cashcurrentalready;
//			detail.put("cashprevious", cashprevious);
//			detail.put("cashcurrent", cashcurrent);
//			detail.put("cashcurrentalready", cashcurrentalready);
//			detail.put("cashend",cashend);
//			
//			
//			double proxyprevious = aprd.getPreviousSum(RecevablePayableService.PROXY);
//			double proxycurrent = aprd.getCurrentSum(RecevablePayableService.PROXY);
//			double proxycurrentalready = aprd.getCurrentAlreadySum(RecevablePayableService.PROXY);
//			double proxyend = proxyprevious +proxycurrent-proxycurrentalready;
//			detail.put("proxyprevious", proxyprevious);
//			detail.put("proxycurrent", proxycurrent);
//			detail.put("proxycurrentalready", proxycurrentalready);
//			detail.put("proxyend",proxyend);
//			
//			
//			double budgetprevious = aprd.getPreviousSum(RecevablePayableService.BUDGET);
//			double budgetcurrent = aprd.getCurrentSum(RecevablePayableService.BUDGET);
//			double budgetcurrentalready = aprd.getCurrentAlreadySum(RecevablePayableService.BUDGET);
//			double budgetend = budgetprevious +budgetcurrent-budgetcurrentalready;
//			detail.put("budgetprevious", budgetprevious);
//			detail.put("budgetcurrent", budgetcurrent);
//			detail.put("budgetcurrentalready", budgetcurrentalready);
//			detail.put("budgetend",budgetend);
//			
//			
//			double monthprevious = aprd.getPreviousSum(RecevablePayableService.MONTH);
//			double monthcurrent = aprd.getCurrentSum(RecevablePayableService.MONTH);
//			double monthcurrentalready = aprd.getCurrentAlreadySum(RecevablePayableService.MONTH);
//			double monthend = monthprevious +monthcurrent-monthcurrentalready;
//			detail.put("monthprevious", monthprevious);
//			detail.put("monthcurrent", monthcurrent);
//			detail.put("monthcurrentalready", monthcurrentalready);
//			detail.put("monthend",monthend);
//
//			request.setAttribute("payer", ro.getPayer());
//			request.setAttribute("detail", detail);
//			DBUserLog.addUserLog(userid, "列表应收款对象");
//			return mapping.findForward("list");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return new ActionForward(mapping.getInput());
//	}
//}
