package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppStockPileAgeing;
import com.winsafe.drp.dao.StockPileAgeing;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.RequestTool;

public class UpdStockPileAgeingAction extends Action {
	AppStockPileAgeing aspa = new AppStockPileAgeing();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			
			StockPileAgeing w = aspa.getStockPileAgeingById(RequestTool.getInt(request, "id"));		
			StockPileAgeing oldor = (StockPileAgeing)BeanUtils.cloneBean(w);
			w.setTagColor(request.getParameter("tagColor"));
			w.setTagMinValue(RequestTool.getInt(request, "tagMinValue"));
			w.setTagMaxValue(RequestTool.getInt(request, "tagMaxValue"));
			aspa.updStockPileAgeing(w);

			request.setAttribute("result", "databases.upd.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid,11, "基础设置>>修改库龄参数,编号:"+w.getId(), oldor, w);

			return mapping.findForward("updResult");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return mapping.findForward("updResult");
	}
}
