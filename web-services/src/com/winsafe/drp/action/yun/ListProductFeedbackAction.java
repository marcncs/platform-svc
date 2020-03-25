package com.winsafe.drp.action.yun;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductFeedback;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.hbm.util.DbUtil;

public class ListProductFeedbackAction extends BaseAction {

	private AppProductFeedback apf = new AppProductFeedback();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 初始化
		int pagesize = 15;
		initdata(request);
		try {
			// 获取查询条件
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "ProductFeedback" };
			// 查询条件
			String whereSql = EntityManager.getTmpWhereSql2(map, tablename);
			// 关键字查询条件
			String blur = DbUtil.getOrBlur(map, tmpMap, "productName","content","reply");

			whereSql = whereSql + blur;
			whereSql = DbUtil.getWhereSql(whereSql);
			// 获取产品列表
			List apls = apf.getProductFeedback(request, pagesize, whereSql);
			// 记录操作日志
			DBUserLog.addUserLog(request, "列表");
			// 返回页面值
			request.setAttribute("productFeedbacks", apls);

			return mapping.findForward("list");
		} catch (Exception e) {
			WfLogger.error("", e);
		}
		return mapping.findForward("list");
	}
}
