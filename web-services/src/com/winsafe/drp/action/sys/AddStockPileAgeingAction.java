package com.winsafe.drp.action.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppStockPileAgeing;
import com.winsafe.drp.dao.StockPileAgeing;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddStockPileAgeingAction extends Action {
	AppStockPileAgeing aspa = new AppStockPileAgeing();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			StockPileAgeing spa = new StockPileAgeing();
			spa.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"stockpile_ageing", 0, "")));
			spa.setTagMinValue(RequestTool.getInt(request, "tagMinValue"));
			spa.setTagMaxValue(RequestTool.getInt(request, "tagMaxValue"));
			spa.setTagColor(request.getParameter("tagColor"));
			
			aspa.saveStockPileAgeing(spa);
			request.setAttribute("result", "databases.add.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11, "基础设置>>新增库龄参数,编号:"+spa.getId());

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("addResult");
	}
}
