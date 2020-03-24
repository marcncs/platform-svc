package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleForecast;
import com.winsafe.drp.dao.AppSaleForecastDetail;
import com.winsafe.drp.dao.SaleForecast;
import com.winsafe.drp.util.DBUserLog;

/**
 * @author : jerry
 * @version : 2009-8-8 下午06:21:27
 * www.winsafe.cn
 */
public class DelSaleForecastAction  extends BaseAction {
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Integer id =Integer.valueOf(request.getParameter("ID"));
		try {
			AppSaleForecast app = new AppSaleForecast();
			SaleForecast pp = app.findByID(id);
			AppSaleForecastDetail apad = new AppSaleForecastDetail();
			apad.deleteBySID(id);
			app.delete(pp);
			request.setAttribute("result", "databases.del.success");
			
			initdata(request);
			DBUserLog.addUserLog(userid,5, "渠道管理>>删除销售预测,编号："+id,pp);

			return mapping.findForward("success");

		} catch (Exception e) {

			e.printStackTrace();
		}
		return mapping.getInputForward();
	}

}
