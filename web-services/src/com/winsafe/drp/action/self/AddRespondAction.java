package com.winsafe.drp.action.self;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

/**
 * @author : jerry
 * @version : 2009-8-8 下午03:33:39
 * www.winsafe.cn
 */
public class AddRespondAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		try {
			Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"respond", 0, ""));
			Respond respond = new Respond();

			respond.setId(id);
			Integer qid =Integer.valueOf(request.getSession().getAttribute("QID").toString()) ;
			respond.setQid(qid);
			respond.setContent(RequestTool.getString(request, "content"));
			respond.setMakedate(DateUtil.getCurrentDate());
			respond.setMakedeptid(users.getMakedeptid());
			respond.setMakeid(userid);
			respond.setMakeorganid(users.getMakeorganid());
			
			AppRespond appRespond = new AppRespond();
			
			appRespond.save(respond);
			DBUserLog.addUserLog(userid, 0, "我的办公桌>>新增常见问题  ,编号：" + id);
			request.setAttribute("result", "databases.add.success");
			return mapping.findForward("success");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}

}
