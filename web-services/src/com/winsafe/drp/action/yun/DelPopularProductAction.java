package com.winsafe.drp.action.yun;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppPopularProduct;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.StringUtil;

public class DelPopularProductAction extends BaseAction {
	
	private AppPopularProduct app = new AppPopularProduct();

	Logger logger = Logger.getLogger(DelPopularProductAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("ID");
		try { 
			if (StringUtil.isEmpty(id)){
				request.setAttribute("result", "删除失败，网络异常");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			app.delProductById(id); 
			DBUserLog.addUserLog(request,"ID:"+id);
			request.setAttribute("result", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "删除失败，网络异常");
		}
		return mapping.findForward("success");
	}
}
