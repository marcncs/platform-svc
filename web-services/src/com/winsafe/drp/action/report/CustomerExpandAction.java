package com.winsafe.drp.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.CustomerExpandDeptForm;
import com.winsafe.drp.dao.CustomerExpandForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
/**
 * 该类用于演示最简单的柱状图生成
 * @author Winter Lau
 */
public class CustomerExpandAction extends BaseAction{

	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		initdata(request);
		try {
			String begindate = request.getParameter("b");
			String enddate = request.getParameter("e");
			String uid = request.getParameter("u");

			int bdate = Integer.parseInt(begindate.toString().substring(5, 7));
			int edate = Integer.parseInt(enddate.toString().substring(5, 7));

			String regdate = (DateUtil.getCurrentDateString()).substring(0, 5);
			String dm = "";
			String dy = "";
			AppCustomer ac = new AppCustomer();
			AppUsers au = new AppUsers();
			List cls = null;
			int usize = 0;

			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			if (uid == null || uid == "") {//所有用户
				List dls = ac.getCustomerExpandUsers(users.getMakeorganid());
				usize = dls.size();

				for (int i = 0; i < usize; i++) {// 部门
					CustomerExpandDeptForm cedf = (CustomerExpandDeptForm) dls
							.get(i);
					Users u = au.getUsersByid(Integer.valueOf(cedf.getUserid()));
					String rowkey = (u==null?"":u.getRealname());
					String userid = cedf.getUserid();
					for (int d = bdate; d <= edate; d++) {
						if (d < 10) {
							dm = "0" + d;
						} else {
							dm = "" + d;
						}
						dy = regdate + dm;
						cls = ac.getCustomerExpand(dy, userid);
						String columnKey = "";

						for (int c = 0; c < cls.size(); c++) {
							CustomerExpandForm cef = (CustomerExpandForm) cls
									.get(c);
							columnKey = cef.getRegistdate();
							dataset.setValue(
									Integer.valueOf(cef.getCidcount()), rowkey,
									columnKey);
						}
					}

				}
				
			} else {//单个用户
				Users u = au.getUsersByid(Integer.valueOf(uid));
				String rowkey = (u==null?"":u.getRealname());
				String userid = uid;
				for (int d = bdate; d <= edate; d++) {
					if (d < 10) {
						dm = "0" + d;
					} else {
						dm = "" + d;
					}
					dy = regdate + dm;
					cls = ac.getCustomerExpand(dy, userid);
					String columnKey = "";

					for (int c = 0; c < cls.size(); c++) {
						CustomerExpandForm cef = (CustomerExpandForm) cls
								.get(c);
						columnKey = cef.getRegistdate();
						dataset.setValue(Integer.valueOf(cef.getCidcount()),
								rowkey, columnKey);
					}
				}

			}

			JFreeChart chart = ChartFactory.createBarChart("会员量趋势图", // 图表标题
					"制单人", // 目录轴的显示标签
					"会员数量", // 数值轴的显示标签

					dataset, // 数据集

					PlotOrientation.VERTICAL, // 图表方向：水平、垂直

					true, // 是否显示图例(对于简单的柱状图必须是false)
					false, // 是否生成工具
					false // 是否生成URL链接
					);

			ChartUtilities.writeChartAsJPEG(response.getOutputStream(), 100,
					chart, 700, 400, null);
			UsersBean users = UserManager.getUser(request);
			DBUserLog.addUserLog(users.getUserid(), 10,"报表分析>>会员>>会员量");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

