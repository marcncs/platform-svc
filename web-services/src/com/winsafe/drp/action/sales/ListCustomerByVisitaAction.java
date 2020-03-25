package com.winsafe.drp.action.sales;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.pager.SimplePageInfo;

//import java.text.SimpleDateFormat;

public class ListCustomerByVisitaAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int pagesize = 10;
		String strP = request.getParameter("P");
		if (strP == null) {
			strP = (String) request.getSession().getAttribute("p");
		}
		String p = strP;
		request.getSession().setAttribute("p", strP);
		try {

			String Condition = "";

			if (p.equals("DN")) {// 今天需联系
				String currDate = DateUtil.getCurrentDateString();
				// String nextDate =
				// DateUtil.calculatedays(DateUtil.getCurrentDate(),1).toLocaleString();
				Condition = " c.nextcontact='" + currDate
						+ "' and c.isdel=0 and c.specializeid=" + userid;
			}
			if (p.equals("WN")) {// 本周需联系
				String weekbDate = DateUtil.formatDate(DateUtil.calculatedays(
						DateUtil.getCurrentDate(), -DateUtil
								.DateTimeToWeekNum(DateUtil.getCurrentDate())));
				String weekeDate = DateUtil.formatDate(DateUtil.calculatedays(
						DateUtil.getCurrentDate(), 7 - DateUtil
								.DateTimeToWeekNum(DateUtil.getCurrentDate())));
				Condition = " c.nextcontact>='" + weekbDate
						+ "' and c.nextcontact<'" + weekeDate
						+ "' and c.isdel=0 and c.specializeid=" + userid;
			}
			if (p.equals("MN")) {// 本月需联系
				int year = DateUtil
						.getBirthYear(DateUtil.getCurrentTimestamp());
				int month = DateUtil.getBirthMonth(DateUtil
						.getCurrentTimestamp());
				String monthbDate = year + "-" + month + "-01";
				String montheDate = year + "-" + month + "-"
						+ DateUtil.getMonthsDay(year, month);
				Condition = " c.nextcontact>='" + monthbDate
						+ "' and c.nextcontact<='" + montheDate
						+ "' and c.isdel=0 and c.specializeid=" + userid;
			}
			if (p.equals("DA")) {// 今天已联系
				String currDate = DateUtil.getCurrentDateString();
				String nextDate = DateUtil.calculatedays(
						DateUtil.getCurrentDate(), 1).toLocaleString();
				Condition = " c.lastcontact='" + currDate
						+ "' and c.isdel=0 and c.specializeid=" + userid;
			}
			if (p.equals("WA")) {// 本周已联系
				String weekbDate = DateUtil.formatDate(DateUtil.calculatedays(
						DateUtil.getCurrentDate(), -DateUtil
								.DateTimeToWeekNum(DateUtil.getCurrentDate())));
				String weekeDate = DateUtil.formatDate(DateUtil.calculatedays(
						DateUtil.getCurrentDate(), 7 - DateUtil
								.DateTimeToWeekNum(DateUtil.getCurrentDate())));
				Condition = " c.lastcontact>='" + weekbDate
						+ "' and c.lastcontact<'" + weekeDate
						+ "' and c.isdel=0 and c.specializeid=" + userid;
			}
			if (p.equals("MA")) {// 本月已联系
				int year = DateUtil
						.getBirthYear(DateUtil.getCurrentTimestamp());
				int month = DateUtil.getBirthMonth(DateUtil
						.getCurrentTimestamp());
				String monthbDate = year + "-" + month + "-01";
				String montheDate = year + "-" + month + "-"
						+ DateUtil.getMonthsDay(year, month);
				Condition = " c.lastcontact>='" + monthbDate
						+ "' and c.lastcontact<='" + montheDate
						+ "' and c.isdel=0 and c.specializeid=" + userid;
			}
			if (p.equals("DR")) {// 今天登记
				String currDate = DateUtil.getCurrentDateString();
				String nextDate = DateUtil.calculatedays(
						DateUtil.getCurrentDate(), 1).toLocaleString();
				Condition = " c.registdate='" + currDate
						+ "' and c.isdel=0 and c.specializeid=" + userid;
			}
			if (p.equals("WR")) {// 本周登记
				String weekbDate = DateUtil.formatDate(DateUtil.calculatedays(
						DateUtil.getCurrentDate(), -DateUtil
								.DateTimeToWeekNum(DateUtil.getCurrentDate())));
				String weekeDate = DateUtil.formatDate(DateUtil.calculatedays(
						DateUtil.getCurrentDate(), 7 - DateUtil
								.DateTimeToWeekNum(DateUtil.getCurrentDate())));
				Condition = " c.registdate>='" + weekbDate
						+ "' and c.registdate<'" + weekeDate
						+ "' and c.isdel=0 and c.specializeid=" + userid;
			}
			if (p.equals("MR")) {// 本月登记
				int year = DateUtil
						.getBirthYear(DateUtil.getCurrentTimestamp());
				int month = DateUtil.getBirthMonth(DateUtil
						.getCurrentTimestamp());
				String monthbDate = year + "-" + month + "-01";
				String montheDate = year + "-" + month + "-"
						+ DateUtil.getMonthsDay(year, month);
				Condition = " c.registdate>='" + monthbDate
						+ "' and c.registdate<='" + montheDate
						+ "' and c.isdel=0 and c.specializeid=" + userid;
			}

			// String Condition=" c.specializeid like '"+userid+"%' and
			// c.isdel=0 and ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Customer" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

			// String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
			// "RegistDate"); 
			// String blur = DbUtil.getBlur(map, tmpMap, "CName");
			// 
			whereSql = whereSql + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			Object obj[] = DbUtil.setDynamicPager(request, "Customer as c",
					whereSql, pagesize, "CustomerCondition");
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

//			List usList = AppCustomer.searchCustomer(pagesize, whereSql,
//					tmpPgInfo);
			ArrayList customerList = new ArrayList();
//			for (int t = 0; t < usList.size(); t++) {
//				CustomerForm cf = new CustomerForm();
//				// customer=(Customer)usList.get(t);
//				Object[] ob = (Object[]) usList.get(t);
//				cf.setCid(String.valueOf(ob[0]));
//				cf.setCname(String.valueOf(ob[1]));
//				cf.setYauldname(Internation.getStringByKeyPosition("Yauld",
//						request, Integer.parseInt(ob[2].toString()),
//						"global.sys.SystemResource"));
//				cf.setOfficetel(String.valueOf(ob[3]));
//				cf.setCustomertypename(Internation.getStringByKeyPosition(
//						"CustomerType", request, Integer.parseInt(ob[4]
//								.toString()), "global.sys.SystemResource"));
//				cf.setCustomerstatusname(Internation.getStringByKeyPosition(
//						"CustomerStatus", request, Integer.parseInt(ob[5]
//								.toString()), "global.sys.SystemResource"));
//				cf.setSourcename(Internation.getStringByKeyPositionDB("Source",
//						Integer.valueOf(ob[6].toString())));
//				cf.setMakedate(ob[7].toString().substring(0, 10));
//
//				customerList.add(cf);
//
//			}

			request.setAttribute("usList", customerList);

			DBUserLog.addUserLog(userid, "列表客户");
			return mapping.findForward("visita");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
