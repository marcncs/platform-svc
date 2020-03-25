package com.winsafe.drp.action.sales;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleForecast;
import com.winsafe.drp.dao.AppSaleForecastDetail;
import com.winsafe.drp.dao.SaleForecast;
import com.winsafe.drp.dao.SaleForecastDetail;

/**
 * @author : jerry
 * @version : 2009-8-8 下午06:21:46
 * www.winsafe.cn
 */
public class DetailSaleForecastAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer id = Integer.valueOf(request.getParameter("ID"));
		AppSaleForecastDetail appd = new AppSaleForecastDetail();
		AppSaleForecast app = new AppSaleForecast();
		
		SaleForecast saleForecast =app.findByID(id);
		List<SaleForecastDetail> list = appd.findBySID(id);
		
		request.setAttribute("saleForecast", saleForecast);
		request.setAttribute("list", list);
		return mapping.findForward("detail");
	}
}
