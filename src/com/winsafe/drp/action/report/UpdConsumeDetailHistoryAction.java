package com.winsafe.drp.action.report;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.report.pojo.ReportForm;
import com.winsafe.drp.server.ReportHistoryService;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.hbm.entity.HibernateUtil;

public class UpdConsumeDetailHistoryAction extends BaseAction{
	
	private static Logger logger = Logger.getLogger(UpdConsumeDetailHistoryAction.class);
	private ReportHistoryService rhs = new ReportHistoryService();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			// 获取条件
			Map map = new HashMap(request.getParameterMap());
			ReportForm scrForm = new ReportForm();
			MapUtil.mapToObject(MapUtil.changeKeyLow(map), scrForm);
			rhs.updConsumeDetailHistory(scrForm);
			rhs.updInventoryDetailHistory(scrForm);
			request.setAttribute("result", "更新成功");
			HibernateUtil.commitTransaction();
			return mapping.findForward("updResult");
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			logger.error("",e);
			request.setAttribute("result", "更新失败："+e.getMessage());
		}
		return mapping.findForward("updResult");
	}
	
}
