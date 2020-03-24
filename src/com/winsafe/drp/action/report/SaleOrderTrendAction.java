package com.winsafe.drp.action.report;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;

import com.winsafe.drp.dao.AppTakeTicket;

public class SaleOrderTrendAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			int beginyear = Integer.valueOf(request.getParameter("b"));
			int endyear = Integer.valueOf(request.getParameter("e"));
			String uid = request.getParameter("u");
			String oid = request.getParameter("o");
			String uConditon = "";
			if (uid != null && uid != "") {
				uConditon = " and so.makeid='" + uid + "' ";
			}
			
			if (oid != null && oid != "") {
				uConditon = " and so.makeorganid='" + oid + "' ";
			}

			String title = "营业额趋势分析";

			String domain = "月份走势";

			String range = "金额(元)";

			AppTakeTicket aso = new AppTakeTicket();
			double Saletotal = 0.00;
			double Salecost = 0.00;
			double Saleprofit = 0.00;
			String yearmon = "";
			String dm = "";

			TimeSeries sale = new TimeSeries("销售额");
			for (int i = beginyear; i < endyear; i++) {
				for (int mon = 0; mon < 12; mon++) {
					// ca.add(new Month(mon + 1, i), new Double(500 +
					// Math.random() * 100));
					// TimeSeriesDataPair是一个时间点的数值体现
					if (mon < 10) {
						dm = "0" + mon;
					} else {
						dm = "" + mon;
					}
					yearmon = i + "-" + dm;
					
					Saletotal = aso.getTakeTicketTotalSum(yearmon, uConditon);
					
					sale.add(new TimeSeriesDataItem(new Day(1, mon + 1, i),
							new Double(Saletotal)));
				}
			}
			TimeSeries cost = new TimeSeries("成本");
			for (int i = beginyear; i < endyear; i++) {
				for (int mon = 0; mon < 12; mon++) {
					if (mon < 10) {
						dm = "0" + mon;
					} else {
						dm = "" + mon;
					}
					yearmon = i + "-" + dm;
					Salecost = aso.getTakeTicketCostSum(yearmon, uConditon);

					cost.add(new TimeSeriesDataItem(new Day(1, mon + 1, i),
							new Double(Salecost)));
				}
			}
			TimeSeries king = new TimeSeries("毛利");
			for (int i = beginyear; i < endyear; i++) {
				for (int mon = 0; mon < 12; mon++) {
					if (mon < 10) {
						dm = "0" + mon;
					} else {
						dm = "" + mon;
					}
					yearmon = i + "-" + dm;
					
					Saleprofit = aso.getTakeTicketProfitSum(yearmon, uConditon);
					king.add(new TimeSeriesDataItem(new Day(1, mon + 1, i),
							new Double(Saleprofit)));
				}
			}
			// 时间曲线数据集合
			TimeSeriesCollection dataset = new TimeSeriesCollection();
			dataset.addSeries(sale);
			dataset.addSeries(cost);
			dataset.addSeries(king);

			JFreeChart chart = ChartFactory.createTimeSeriesChart(title,
					domain, range, dataset, true, true, false);

			ChartUtilities.writeChartAsJPEG(response.getOutputStream(), 100,
					chart, 900, 560, null);
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

}
