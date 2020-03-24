package com.winsafe.drp.action.purchase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

public class RemoveOrganProductListAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 15;
		String pid = request.getParameter("OtherKey");
		initdata(request);
		try {
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Product" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			StringBuffer buf = new StringBuffer();
			if (pid != null && pid.length() > 0) {
				buf.append("(");
				buf.append("psid like '" + pid + "%')");
				whereSql = whereSql + buf;

			}
			String blur = DbUtil.getOrBlur(map, tmpMap, "ID", "ProductName",
					"SpecMode", "PYCode");
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					"MakeDate");

			whereSql = whereSql + blur + timeCondition;
			whereSql = DbUtil.getWhereSql(whereSql);

			AppProduct ap = new AppProduct();
			List apls = ap.getProduct(request, pagesize, whereSql);

			request.setAttribute("alapls", apls);
			request.setAttribute("pid", pid);
			UsersBean users = UserManager.getUser(request);
			int userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11, "列表商品资料");

			request.setAttribute("Wise", request.getParameter("Wise"));
			request.setAttribute("KeyWord", request.getParameter("KeyWord"));
			request
					.setAttribute("BeginDate", request
							.getParameter("BeginDate"));
			request.setAttribute("EndDate", request.getParameter("EndDate"));
			request.setAttribute("UseFlag", request.getParameter("UseFlag"));
			request.setAttribute("OtherKey", request.getParameter("OtherKey"));

			return mapping.findForward("listproduct");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
