package com.winsafe.drp.action.assistant;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppTrackApply;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.TrackApply;
import com.winsafe.drp.dao.Users;

public class ToAddTrackApplyAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TrackApply ta = new TrackApply();
		AppTrackApply ata = new AppTrackApply();
		Users u = new Users();
		AppUsers au = new AppUsers();
		int i = 1;
		super.initdata(request);
		try {
			String idCode = request.getParameter("idCode");
			ta = ata.getTrackapplyByIdcodeAndOrg(idCode, users.getMakeorganid());
			// 该机构已提交申请
			if (ta != null) {
				request.setAttribute("result", "databases.record.repeat");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

			// 获取编号
			// ta.setId(Integer.valueOf(ata.getMaxTrackApplyId()));
			// 获取申请人编号
			ta = new TrackApply();
			ta.setApplyUserId(userid);
			// 通过申请人编号获取申机构编号
			u = au.getUsersByid(userid);
			ta.setApplyOrgId(u.getMakeorganid());

			// 获取查询码
			ta.setIdCode(idCode);
			// 获取申请日期
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ta.setCreateDate(new Date());
			// 获取状态
			ta.setStatus(i);
			ata.addTrackApply(ta);
			request.setAttribute("result", "databases.add.success");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
