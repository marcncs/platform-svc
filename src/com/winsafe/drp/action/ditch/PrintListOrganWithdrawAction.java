package com.winsafe.drp.action.ditch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;

/**
 * @author : jerry
 * @version : 2009-8-26 下午03:45:59 www.winsafe.cn
 */
public class PrintListOrganWithdrawAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		super.initdata(request);try{
			String isshow = request.getParameter("isshow");
			String Condition = "";
			if (isshow.equals("yes")) {
				Condition = "ow.isaudit = 1 and ow.porganid = '"
						+ users.getMakeorganid() + "'";
			} else {
			 Condition = "ow.makeorganid = '" + users.getMakeorganid()
					+ "'";
			}

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "OrganWithdraw" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "ID","KeysContent");
			whereSql = whereSql + timeCondition + blur+Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppOrganWithdraw appS = new AppOrganWithdraw();
			List lists = appS.getOrganWithdrawAll(whereSql);
			request.setAttribute("list", lists);
			if (isshow.equals("yes")) {
				DBUserLog.addUserLog(userid, 4, "渠道管理>>打印渠道退货审核!");
			} else {
				DBUserLog.addUserLog(userid, 4, "渠道管理>>打印渠道退货!");
			}
			request.setAttribute("isshow", isshow);
			return mapping.findForward("toprint");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}