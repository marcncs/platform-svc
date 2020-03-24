package com.winsafe.drp.action.yun;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPopularProduct;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.hbm.util.StringUtil;

public class AuditPopularProductAction extends BaseAction {
	
	private AppPopularProduct app = new AppPopularProduct();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("ID");
		Integer isAudit = Integer.valueOf(request.getParameter("isAudit"));
		try { 
			
			if (StringUtil.isEmpty(id)){
				request.setAttribute("result", "审核失败,编号为空");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			app.auditProductById(id, isAudit);
			DBUserLog.addUserLog(request,"ID:"+id);
			
			request.setAttribute("result", "审核成功");

			return mapping.findForward("success");
		} catch (Exception e) {
			WfLogger.error("",e);
			request.setAttribute("result", "审核失败,网络异常");
		}
		return mapping.findForward("success");
	}
}
