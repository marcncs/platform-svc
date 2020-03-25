package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductPriceHistory;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;

public class DelProductPriceHistoryAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			String id = request.getParameter("pphid");
			
			AppProductPriceHistory apph = new AppProductPriceHistory();
			apph.delProductPriceHistoryByID(Long.valueOf(id));
			
			request.setAttribute("result", "databases.del.success");
			UsersBean users = UserManager.getUser(request);
			int userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11, "删除产品历史价格,编号：" + id);

			return mapping.findForward("del");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}
		return null;
	}

}
