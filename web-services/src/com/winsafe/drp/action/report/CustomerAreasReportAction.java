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
import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.CustomerReportForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
/**
 * 用于演示饼图的生成
 * @author Winter Lau
 */
public class CustomerAreasReportAction extends BaseAction{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		String rp =request.getParameter("rp");
		String b= request.getParameter("b");
		String e= request.getParameter("e");
		String u = request.getParameter("u");
		
		String srcDoc="";
		String reportTitle="";
		if(rp.equals("province")){
			srcDoc="Province";
			reportTitle="省份";
		}
		if(rp.equals("city")){
			srcDoc="City";
			reportTitle="城市";
		}
		if(rp.equals("areas")){
			srcDoc="Areas";
			reportTitle="地区";
		}

		try{
			initdata(request);
			//String Condition =" c.cid=cu.cid and ";
		Map map = new HashMap();
		map.put("BeginDate",b);
		map.put("EndDate",e);
		map.put("SpecializeID",u);
		
		String Condition = " c.isdel =0 and makeorganid = '"+users.getMakeorganid()+"'";
		Map tmpMap = EntityManager.scatterMap(map);
		String[] tablename={"Customer"};
        String whereSql = EntityManager.getTmpWhereSql(map, tablename); 
        String timeCondition = DbUtil.getTimeCondition(map, tmpMap, " MakeDate");
        whereSql = whereSql +  timeCondition + Condition; 
        whereSql = DbUtil.getWhereSql(whereSql); 
        
        AppCountryArea aca = new AppCountryArea();
        AppCustomer ac = new AppCustomer();
        List ls = ac.getCustomerReport(whereSql,rp);

		String sp="未知";
		DefaultPieDataset data = new DefaultPieDataset();
		for(int i=0;i<ls.size();i++){
			CustomerReportForm o=(CustomerReportForm)ls.get(i);
			if(o.getReportid()>0){
			sp=sp=aca.getAreaByID(Integer.valueOf(o.getReportid())).getAreaname();
			}
			data.setValue(sp,o.getCidcount());
		}
		
		JFreeChart chart = ChartFactory.createPieChart(reportTitle,  // 图表标题
		data, 
		true, // 是否显示图例
		false,
		false
		);
		

		PiePlot pie = (PiePlot)chart.getPlot();
		//chart.setBorderVisible(true);
	    //pie.setLabelFont(new Font("黑体", Font.TRUETYPE_FONT, 12));
	    //pie.setBackgroundAlpha(0.6f);
	    //pie.setForegroundAlpha(0.40f);

        PieSectionLabelGenerator generator=
                new StandardPieSectionLabelGenerator(
                         "{0} : {1} ({2})",
                         NumberFormat.getNumberInstance(), 
                         NumberFormat.getPercentInstance());
        pie.setLabelGenerator(generator);

	

		ChartUtilities.writeChartAsJPEG(response.getOutputStream(),
				100,chart,700,400,null);

		}catch(Exception ex){
			ex.printStackTrace();
		}
		DBUserLog.addUserLog(userid, 10,"报表分析>>会员>>会员区域分析");
		return null;
	}
}
