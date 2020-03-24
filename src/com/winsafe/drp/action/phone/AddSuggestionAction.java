package com.winsafe.drp.action.phone;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppQuery;
import com.winsafe.drp.dao.AppSuggestionBox;
import com.winsafe.drp.dao.SuggestionBox;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.StringUtil;

public class AddSuggestionAction extends Action {
	private Logger logger = Logger.getLogger(AddSuggestionAction.class);
	private AppSuggestionBox appSuggestionBox = new AppSuggestionBox();
	private AppQuery appQuery = new AppQuery();
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		String imeiNumber = request.getParameter("IMEI_number"); // 手机IMEI号
		String suggestionMsg = request.getParameter("SuggestionMsg"); // 投诉信息

		try {
			String ipString = appQuery.getIpAddr(request);
			//增加上传上来的数据
			if (!StringUtil.isEmpty(suggestionMsg)) {
				SuggestionBox suggestionBox = new SuggestionBox();
				suggestionBox.setSuggestionMsg(suggestionMsg);
				suggestionBox.setMakeDate(new Date());
				suggestionBox.setImeiNumber(imeiNumber);
				suggestionBox.setIp(ipString);
				appSuggestionBox.addSuggestionBox(suggestionBox);
			}
			ResponseUtil.writeJsonMsg(response, Constants.CODE_SUCCESS, Constants.CODE_SUCCESS_MSG, "", null, "手机", "IMEI:[" + imeiNumber + "]", false);
		} catch (Exception e) {
			logger.error("", e);
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, Constants.CODE_ERROR_MSG, "", null, "手机", "IMEI:[" + imeiNumber + "]", false);
		}
		return null;
	}
}
