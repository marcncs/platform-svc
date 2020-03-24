package com.winsafe.drp.action.machin;


import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.task.ProductInComeTask;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.StringUtil;

/**
 * 直接生成入库单，调用生成入库单任务
 * @author Andy.liu
 *
 */
public class ProduceProductIncomeAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int userid = users.getUserid();
		super.initdata(request);try{
			
			String date = this.getLastInComeDate();
			// 没有获取到时间则数据库没有数据。直接退出
			if (StringUtil.isEmpty(date) || "null".equals(date) ) {
				request.setAttribute("result", "没有数据不能生成入库单！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			
			ProductInComeTask pit = new ProductInComeTask();
			pit.run();

			request.setAttribute("result", "databases.operator.success");
			DBUserLog.addUserLog(userid, 7,"直接生成入库单!");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "databases.operator.fail");
		}
		return new ActionForward(mapping.getInput());
	}
	
	/**
	 * 获取最后入库时间
	 */
	private String getLastInComeDate() {
		//String idCodeSql = "SELECT MAX(MAKEDATE) FROM PRODUCT_INCOME ";
		String reportSql = "SELECT MIN(MAKEDATE) FROM UPLOAD_PRODUCE_REPORT WHERE ISINCOME = 0";
		String resultDate = "";
		try {
//			ResultSet rs = EntityManager.query2(idCodeSql);
//			if (rs.next()) {
//				resultDate = rs.getString(1);
//			}
//			rs.close();
			//if (StringUtil.isEmpty(resultDate)) {
				ResultSet rs = EntityManager.query2(reportSql);
				if (rs.next()) {
					resultDate = rs.getString(1);
				}
				rs.close();
			//}
		} catch (Exception e) {
			System.err.println("error: found LastIncomeDate error");
			e.printStackTrace();
		}
		return resultDate;
	}
	
}
