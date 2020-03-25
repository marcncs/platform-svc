package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppRespond;
import com.winsafe.drp.dao.Respond;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.RequestTool;

/**
 * @author : jerry
 * @version : 2009-8-8 下午03:33:49
 * www.winsafe.cn
 */
public class UpdRespondAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		try {
			Integer id = RequestTool.getInt(request, "id");
			AppRespond appRespond = new AppRespond();
			Respond respond = appRespond.findByID(id);
			Respond oldrespond = (Respond) BeanUtils.cloneBean(respond);
			respond.setQid(RequestTool.getInt(request, "qid"));
			respond.setContent(RequestTool.getString(request, "content"));
			respond.setMakedate(DateUtil.getCurrentDate());
			respond.setMakedeptid(users.getMakedeptid());
			respond.setMakeid(userid);
			respond.setMakeorganid(users.getMakeorganid());

			appRespond.update(respond);
			DBUserLog.addUserLog(userid, 0, "我的办公桌>>修改常见问题 回复 ,编号：" + id,oldrespond,respond);
			request.setAttribute("result", "databases.upd.success");
			return mapping.findForward("success");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
