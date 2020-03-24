package com.winsafe.drp.action.sales;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleForecast;
import com.winsafe.drp.dao.SaleForecast;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

/**
 * @author : jerry
 * @version : 2009-8-8 下午06:20:25
 * www.winsafe.cn
 */
public class ListSaleForecastAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pageSize = 10;
		initdata(request);
		try{
			Integer objsort = Integer.valueOf(request.getParameter("objsort"));
			
			
			String Condition = " s.makeorganid = '"+users.getMakeorganid()+"'";
			
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "SaleForecast"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"MakeDate");
			
			whereSql = whereSql+timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 
			AppSaleForecast appSaleForecast = new AppSaleForecast();
			List<SaleForecast> list  = appSaleForecast.findAll(request, whereSql, pageSize);
			
			
			request.setAttribute("objsort", objsort);
			DBUserLog.addUserLog(userid, 4, "渠道管理>>列表销售预测  ");
			request.setAttribute("list", list);
			return mapping.findForward("list");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		
		return super.execute(mapping, form, request, response);
	}
	
	
}
