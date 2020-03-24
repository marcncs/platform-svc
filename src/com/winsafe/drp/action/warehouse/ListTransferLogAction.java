package com.winsafe.drp.action.warehouse;

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
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.erp.dao.AppFileTransfer;
import com.winsafe.erp.pojo.TransferLog;
import com.winsafe.hbm.util.DbUtil;

public class ListTransferLogAction extends BaseAction {
	
	private static Logger logger = Logger.getLogger(ListTransferLogAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception { 
		int pagesize = 20;
		super.initdata(request);
		try {
			
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "TransferLog" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "fileName");
			String timeCondition = DbUtil.getTimeCondition4(map, tmpMap,
					" makeDate");
			whereSql = whereSql + timeCondition + blur; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppFileTransfer api = new AppFileTransfer();
			List<TransferLog> pils = api.getTransferLogs(request, pagesize, whereSql);
			request.setAttribute("alpi", pils);

			DBUserLog.addUserLog(request,"列表");
			return mapping.findForward("list");
		} catch (Exception e) { 
			logger.error("", e);
		}
		return new ActionForward(mapping.getInput());
	}
}
