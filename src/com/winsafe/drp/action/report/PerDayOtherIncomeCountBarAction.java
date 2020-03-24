package com.winsafe.drp.action.report;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
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

import com.winsafe.drp.dao.AppOtherIncomeDetail;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.SaleReportForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;

public class PerDayOtherIncomeCountBarAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String b = request.getParameter("b");
		String e = request.getParameter("e");

		String reportTitle = "其它入库数量图表";
		try {
			String Condition = " oi.id=oid.oiid and oi.isaudit=1 ";
			Map map = new HashMap();
			map.put("BeginDate", b);
			map.put("EndDate", e);

			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = {"OtherIncome","OtherIncomeDetail"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"IncomeDate");
			whereSql = whereSql +  timeCondition +Condition;
			whereSql = DbUtil.getWhereSql(whereSql); 
			
			AppProduct ap = new AppProduct();
			AppOtherIncomeDetail ac = new AppOtherIncomeDetail();
	        List ls = ac.getOtherIncomeCountReport(whereSql);
	        
	        String pn="";
			DefaultPieDataset data = new DefaultPieDataset();
			for(int i=0;i<ls.size();i++){
				SaleReportForm o=(SaleReportForm)ls.get(i);
				pn=ap.getProductByID(o.getProductid()).getProductname();
				data.setValue(pn,o.getCount());
			}
			
			JFreeChart chart = ChartFactory.createPieChart(reportTitle, // 图表标题
					data, true, // 是否显示图例
					false, false);

			PiePlot pie = (PiePlot) chart.getPlot();
			//chart.setBorderVisible(true);
			//pie.setLabelFont(new Font("黑体", Font.TRUETYPE_FONT, 12));
			//pie.setBackgroundAlpha(0.6f);
			//pie.setForegroundAlpha(0.40f);

			PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator(
					"{0} : {1} ({2})", NumberFormat.getNumberInstance(),
					NumberFormat.getPercentInstance());
			pie.setLabelGenerator(generator);

			//写图表对象到文件，参照柱状图生成源码

			ChartUtilities.writeChartAsJPEG(response.getOutputStream(), 100,
					chart, 500, 350, null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

}
