package com.winsafe.drp.keyretailer.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.dao.AppSalesAreaCountry;
import com.winsafe.drp.keyretailer.dao.AppUserCustomer;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class ListUserCustomerAction extends BaseAction{
//	private static Logger logger = Logger.getLogger(ListSBonusSettingAction.class);
	
	private AppUserCustomer appUserCustomer = new AppUserCustomer();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		initdata(request);
		
		Map map = new HashMap(request.getParameterMap());
		Map tmpMap = EntityManager.scatterMap(map);
		
		String whereSql = "";
		String blur = DbUtil.getOrBlur(map, tmpMap, "o.id","o.OECODE","o.ORGANNAME");
		if(!StringUtil.isEmpty(blur)) {
			whereSql = " where " + blur;
		}
		whereSql = DbUtil.getWhereSql(whereSql);
		
		List<Map<String,String>> userCustomer = appUserCustomer.getUserCustomer(request, pagesize, whereSql);
		
		request.setAttribute("userCustomer", userCustomer);
		DBUserLog.addUserLog(request, "列表");
		return mapping.findForward("list");
	}
		
}
