package com.winsafe.drp.keyretailer.action;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.keyretailer.dao.AppSCustomerArea;
import com.winsafe.drp.keyretailer.dao.AppSalesAreaCountry;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class AddSalesAreaCountryAction extends BaseAction {
	private static Logger logger = Logger.getLogger(AddSalesAreaCountryAction.class);
	
	private AppSalesAreaCountry appSalesAreaCountry = new AppSalesAreaCountry();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		try {
			
			String areaIds = request.getParameter("areaIds");
			String areaid = request.getParameter("pid");
			
			if(StringUtil.isEmpty(areaIds)) {
				request.setAttribute("result", "区/县参数为空,添加失败");
				return new ActionForward("/sys/operatorclose3.jsp");
			}
			if(StringUtil.isEmpty(areaid)) {
				request.setAttribute("result", "销售区域编号参数为空,添加失败");
				return new ActionForward("/sys/operatorclose3.jsp");
			}
			areaIds = areaIds.substring(0, areaIds.length() - 1);
			
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			
			String blur = DbUtil.getOrBlur(map, tmpMap, "CA.AREANAME","CA2.AREANAME","CA3.AREANAME");
			if(!StringUtil.isEmpty(blur)) {
				blur = " and " + blur;
			}
			blur = DbUtil.getWhereSql(blur);
			
			if("true".equals(request.getParameter("addAll"))) {
				appSalesAreaCountry.addAllSalesAreaCountry(request, areaid, blur);
			} else {
				appSalesAreaCountry.addSalesAreaCountry(areaIds,areaid);
			}
			
			request.setAttribute("result", "新增成功");
			DBUserLog.addUserLog(request, "新增,行政机构编号" + areaIds+" 销售区域编号"+areaid); 
			return mapping.findForward("addresult");
		} catch (Exception e) {
			logger.error("",e);
			throw e;
		}
	}
	

}
