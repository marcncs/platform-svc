package com.winsafe.drp.action.self;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppRespond;
import com.winsafe.drp.dao.Respond;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.RequestTool;

/**
 * @author : jerry
 * @version : 2009-8-8 下午03:33:10 www.winsafe.cn
 */
public class ListRespondAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pageSize = 10;
		initdata(request);
		try {
			Integer qid = RequestTool.getInt(request, "QID");
			if (qid == null) {
				qid = Integer.valueOf(request.getSession().getAttribute("QID")
						.toString());
			}
			String Condition = " r.qid = " + qid;
			String[] tablename = { "Respond" };
			String whereSql = getWhereSql2(tablename);
			String timeCondition = getTimeCondition("MakeDate");

			String blur = getKeyWordCondition("Content");
			whereSql = whereSql + timeCondition + blur + Condition;
			whereSql = DbUtil.getWhereSql(whereSql);
			AppRespond appRespond = new AppRespond();
			List<Respond> list = appRespond
					.findAll(request, whereSql, pageSize);
			request.getSession().setAttribute("QID", qid);
			DBUserLog.addUserLog(userid, 0, "我的办公桌>>列表常见问题回复  ");
			request.setAttribute("list", list);
			return mapping.findForward("list");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return super.execute(mapping, form, request, response);
	}

}
