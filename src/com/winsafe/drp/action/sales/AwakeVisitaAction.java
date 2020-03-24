package com.winsafe.drp.action.sales;
//package com.winsafe.drp.action.sales;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.struts.action.Action;
//import org.apache.struts.action.ActionForm;
//import org.apache.struts.action.ActionForward;
//import org.apache.struts.action.ActionMapping;
//
//import com.winsafe.drp.dao.UserManager;
//import com.winsafe.drp.dao.UsersBean;
//import com.winsafe.drp.util.DateUtil;
//import com.winsafe.drp.util.DbUtil;
//
////import java.text.SimpleDateFormat;
//
//public class AwakeVisitaAction extends BaseAction {
//	public ActionForward execute(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws IOException, ServletException {
//
//		try {
//			
////		    Long userid = users.getUserid();
//			int pagesize = 1;
//			String CurrTab = " Customer as c ";
//			String currDate = DateUtil.getCurrentDateString();
//			//String nextDate = DateUtil.calculatedays(DateUtil.getCurrentDate(),1).toLocaleString();
//			 //System.out.println("----"+currDate+"==="+nextDate);
//			// 今天需联系
////			String todaynCondition = " where c.nextcontact='" + currDate
////					+ "' and c.isdel=0 and c.specializeid="+userid;
////			int todaynCont = DbUtil.getRecordCount(pagesize, todaynCondition,
////					CurrTab);
//
//			// 今天已联系
////			String todayaCondition = " where c.lastcontact='" + currDate
////					+ "' and c.isdel=0 and c.specializeid="+userid;
////			int todayaCont = DbUtil.getRecordCount(pagesize, todayaCondition,
////					CurrTab);
//
//			// 今天登记
////			String todayrCondition = " where c.registdate ='" + currDate
////					+ "'  and c.isdel=0 and c.specializeid="+userid;
////			int todayrCont = DbUtil.getRecordCount(pagesize, todayrCondition,
////					CurrTab);
//
//			String weekbDate = DateUtil.formatDate(DateUtil.calculatedays(
//					DateUtil.getCurrentDate(),
//					-DateUtil.DateTimeToWeekNum(DateUtil.getCurrentDate())));
//			String weekeDate = DateUtil.formatDate(DateUtil.calculatedays(
//					DateUtil.getCurrentDate(),
//					7 - DateUtil.DateTimeToWeekNum(DateUtil.getCurrentDate())));
//			// System.out.println("----8-"+DateUtil.getMonthsDay(2008,8));
//			// System.out.println("----2-"+DateUtil.getMonthsDay(2006,2));
//			// System.out.println("----11-"+DateUtil.getMonthsDay(2008,11));
//
//			// 本周需联系
////			String weeknCondition = " where c.nextcontact>='" + weekbDate
////					+ "' and c.nextcontact<'" + weekeDate + "' and c.isdel=0 and c.specializeid="+userid;
////			int weeknCont = DbUtil.getRecordCount(pagesize, weeknCondition,
////					CurrTab);
//
////			// 本周已联系
////			String weekaCondition = " where c.lastcontact>='" + weekbDate
////					+ "' and c.lastcontact<'" + weekeDate + "' and c.isdel=0 and c.specializeid="+userid;
////			int weekaCont = DbUtil.getRecordCount(pagesize, weekaCondition,
////					CurrTab);
//
//			// 本周登记
////			String weekrCondition = " where c.registdate>='" + weekbDate
////					+ "' and c.registdate<'" + weekeDate + "' and c.isdel=0 and c.specializeid="+userid;
////			int weekrCont = DbUtil.getRecordCount(pagesize, weekrCondition,
////					CurrTab);
//			
//			//本月
//			int year=DateUtil.getBirthYear(DateUtil
//					.getCurrentTimestamp());
//			int month=DateUtil.getBirthMonth(DateUtil.getCurrentTimestamp());
//
//			String monthbDate = year+"-"+month+ "-01";
//			String montheDate = year+"-"+month+ "-"+DateUtil.getMonthsDay(year, month);
//			//System.out.println("---"+ monthbDate);
//			// 本月需联系
////			String monthnCondition = " where c.nextcontact>='" + monthbDate
////			+ "' and c.nextcontact<='" + montheDate + "' and c.isdel=0 and c.specializeid="+userid;
////			int monthnCont = DbUtil.getRecordCount(pagesize, monthnCondition,
////			CurrTab);
////			
//			//本月已联系
//			String monthaCondition = " where c.lastcontact>='" + monthbDate
//			+ "' and c.lastcontact<='" + montheDate + "' and c.isdel=0 and c.specializeid="+userid;
//			int monthaCont = DbUtil.getRecordCount(pagesize, monthaCondition,
//			CurrTab);
//			
//			//本月登记
//			String monthrCondition = " where c.registdate>='" + monthbDate
//			+ "' and c.registdate<='" + montheDate + "' and c.isdel=0 and c.specializeid="+userid;
//			int monthrCont = DbUtil.getRecordCount(pagesize, monthrCondition,
//			CurrTab);
//
//			request.setAttribute("todaynCont", todaynCont);
//			request.setAttribute("todayaCont", todayaCont);
//			request.setAttribute("todayrCont", todayrCont);
//			request.setAttribute("weeknCont", weeknCont);
//			request.setAttribute("weekaCont", weekaCont);
//			request.setAttribute("weekrCont", weekrCont);
//			request.setAttribute("monthnCont", monthnCont);
//			request.setAttribute("monthaCont", monthaCont);
//			request.setAttribute("monthrCont", monthrCont);
//
//			return mapping.findForward("awakevisita");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return null;
//
//	}
//
//}
