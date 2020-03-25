package com.winsafe.drp.keyretailer.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.keyretailer.dao.AppSCustomerArea;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.StringUtil;

public class AddSCustomerAreaAction extends BaseAction {
	private static Logger logger = Logger.getLogger(AddSCustomerAreaAction.class);
	
	private AppSCustomerArea appSCustomerArea = new AppSCustomerArea();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//初始化
		initdata(request);
		try {
			
			String organIds = request.getParameter("organIds");
			String areaid = request.getParameter("pid");
			
			if(StringUtil.isEmpty(organIds)) {
				request.setAttribute("result", "机构参数为空,添加失败");
				return new ActionForward("/sys/operatorclose3.jsp");
			}
			if(StringUtil.isEmpty(areaid)) {
				request.setAttribute("result", "区域编号参数为空,添加失败");
				return new ActionForward("/sys/operatorclose3.jsp");
			}
			organIds = organIds.substring(0, organIds.length() - 1);
			
			if("true".equals(request.getParameter("addAll"))) {
				appSCustomerArea.addAllSCustomerAreas(areaid);
			} else {
				appSCustomerArea.addSCustomerAreas(organIds,areaid);
			}
			
			request.setAttribute("result", "新增成功");
			DBUserLog.addUserLog(request, "新增,机构编号" + organIds.replace("'", "")+" 区域编号"+areaid); 
			return mapping.findForward("addresult");

		} catch (Exception e) {
			logger.error("",e);
			throw e;
		}
	}
	

}
