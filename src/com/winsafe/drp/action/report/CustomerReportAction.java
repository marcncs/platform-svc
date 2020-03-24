package com.winsafe.drp.action.report;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppMemberGrade;
import com.winsafe.drp.dao.CustomerReportForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;

/**
 * 用于演示饼图的生成
 * 
 * @author Winter Lau
 */
public class CustomerReportAction extends BaseAction {

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String rp = request.getParameter("rp");
		String b = request.getParameter("b");
		String e = request.getParameter("e");
		String u = request.getParameter("u");

		String srcDoc = "";
		String reportTitle = "";
		if (rp.equals("customertype")) {
			srcDoc = "CustomerType";
			reportTitle = "会员类别";
		}
		if (rp.equals("customerstatus")) {
			srcDoc = "CustomerStatus";
			reportTitle = "会员状态";
		}
		if (rp.equals("rate")) {
			srcDoc = "PricePolicy";
			reportTitle = "会员级别";
		}
		if (rp.equals("source")) {
			srcDoc = "Source";
			reportTitle = "会员来源";
		}

		try {
			initdata(request);
			Map map = new HashMap();
			map.put("BeginDate", b);
			map.put("EndDate", e);
			map.put("SpecializeID", u);

			String Condition = " c.isdel =0 and makeorganid = '"+users.getMakeorganid()+"'";
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Customer" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"MakeDate");
			whereSql = whereSql + timeCondition + Condition;
			whereSql = DbUtil.getWhereSql(whereSql); 
			AppCustomer ac = new AppCustomer();
			List ls = ac.getCustomerReport(whereSql, rp);

			String sp = "";
			DefaultPieDataset data = new DefaultPieDataset();
			for (int i = 0; i < ls.size(); i++) {
				CustomerReportForm o = (CustomerReportForm) ls.get(i);
				
				if(srcDoc.equals("PricePolicy")){
					AppMemberGrade appMemberGrade = new AppMemberGrade();
					sp = appMemberGrade.getMemberGradeName(o.getReportid());
				}else{
					
			
					
					sp = HtmlSelect.getResourceName(request, srcDoc, o
							.getReportid());
					
				}
				data.setValue(sp, o.getCidcount());
			}

			JFreeChart chart = ChartFactory.createPieChart(reportTitle, // 图表标题
					data, true, // 是否显示图例
					false, false);

			PiePlot pie = (PiePlot) chart.getPlot();

			PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator(
					"{0} : {1} ({2})", NumberFormat.getNumberInstance(),
					NumberFormat.getPercentInstance());
			pie.setLabelGenerator(generator);

			// 写图表对象到文件，参照柱状图生成源码

			ChartUtilities.writeChartAsJPEG(response.getOutputStream(), 100,
					chart, 700, 400, null);
			DBUserLog.addUserLog(userid, 10,"报表分析>>会员>>会员分析");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
}
