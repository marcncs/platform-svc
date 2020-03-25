package com.winsafe.drp.action.report;

import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * 该类用于演示最简单的柱状图生成
 * @author Winter Lau
 */
public class BarChartDemo {

	public static void main(String[] args) throws IOException{

		String title = "柱状图测试";
		String domain = "单位比较";
		String range = "数值";
//		CategoryDataset data = DemoDatasetFactory.createCategoryDataset();
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		for (int r = 0; r < 5; r++) {
		String rowKey = "单位 [" + (r + 1)+"]" ;
//		第一层循环：分析对象
		for (int c = 0; c < 6; c++) {
//		第二层循环：分析对象在时间点上的数据
		String columnKey = "2001年" + (c + 1) + "月";
		data.addValue(new Double(r * c + 5), rowKey, columnKey);
		}
		}
System.out.println("-----------"+data.getRowCount());
		JFreeChart chart =
		//ChartFactory.createVerticalBarChart(title,domain,range,data,true,true,false);
		ChartFactory.createBarChart3D(
				title, // 图表标题
				domain, // 目录轴的显示标签
				range, // 数值轴的显示标签
				data, // 数据集
				PlotOrientation.VERTICAL, // 图表方向：水平、垂直
				true, 	// 是否显示图例(对于简单的柱状图必须是false)
				false, 	// 是否生成工具
				false 	// 是否生成URL链接
				);
//		 then customise it a little...
//		chart.setBackgroundPaint(
//		new GradientPaint(0, 0, Color.white, 1000, 0, Color.red));
//		chart.setTitle(new TextTitle(title, new Font("隶书", Font.ITALIC, 15)));
//		CategoryPlot plot = (CategoryPlot)chart.getPlot();
//		plot.setForegroundAlpha(0.9f);
		//plot.setValueLabelFont(new Font("黑体", Font.TRUETYPE_FONT, 12));
//		plot.setSectionLabelFont(new Font("黑体", Font.TRUETYPE_FONT, 12));
//		注意以下代码
//		NumberAxis verticalAxis = (NumberAxis)plot.getRangeAxis();
//		verticalAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//		 输出文件到指定目录
		String rfname = "faavdsb.jpeg";
		String fileName = "D:\\" + rfname;
		try {
		ChartUtilities.saveChartAsJPEG(new File(fileName), 100, chart, 600, 600);
//		 log.info("....Create image File:" + fileName);
		} catch (IOException exz) {
		System.out.print("....Cant't Create image File");
		}
	}
}
