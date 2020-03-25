package com.winsafe.drp.action.phone;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppTrackApply;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.TrackApply;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;

public class ApplyWlmIdcodeAction extends Action {
	private Logger logger = Logger.getLogger(QueryFWIdcodeAction.class);
	private AppTrackApply ata = new AppTrackApply();
	private AppUsers au = new AppUsers();

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		String username = request.getParameter("Username"); // 登陆名
		String password = request.getParameter("Password"); // 密码
		String imeinumber = request.getParameter("IMEI_number"); // 手机IMEI号
		String wlmidcode = request.getParameter("WlmIdcode"); // 防伪码
		int i = 1;
		try {
			Users u = au.getUsers(username);
			if (u != null) {
				TrackApply ta = ata.getTrackapplyByIdcodeAndOrg(wlmidcode, u.getMakeorganid());
				// 该机构已提交申请
				if (ta != null) {
					ResponseUtil.writeJsonMsg(response, Constants.APPLYWLIDCODE_ERROR_CODE2, Constants.APPLYWLIDCODE_ERROR_CODE2_DATA_MSG, "", u.getUserid(), "手机", "IMEI:[" + imeinumber + "]", true);
				}else {
					// 获取编号
					// ta.setId(Integer.valueOf(ata.getMaxTrackApplyId()));
					// 获取申请人编号
					ta = new TrackApply();
					ta.setApplyUserId(u.getUserid());
					// 获取申机构编号
					ta.setApplyOrgId(u.getMakeorganid());
					
					// 获取查询码
					ta.setIdCode(wlmidcode);
					// 获取申请日期
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					ta.setCreateDate(new Date());
					// 获取状态
					ta.setStatus(i);
					ata.addTrackApply(ta);
					ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, "", u.getUserid(), "手机", "IMEI:[" + imeinumber + "]", true);
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
}

